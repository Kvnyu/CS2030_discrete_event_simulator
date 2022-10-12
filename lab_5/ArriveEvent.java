import java.util.function.Supplier;

class ArriveEvent extends Event {
        private final Supplier<Double> serviceTimeSupplier;

        // TODO: change serviceTimeSupplier to serviceTimeSupplier
        ArriveEvent(Customer customer, Supplier<Double> serviceTimeSupplier) {
                super(customer, LOW_PRIORITY);
                this.serviceTimeSupplier = serviceTimeSupplier;
        }

        @Override
        Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
                if (serverBalancer.isThereAServerFreeAt(this.customer.getArrivalTime())) {
                        Customer customerWithServiceTime = this.customer
                                        .cloneWithServiceTime(this.serviceTimeSupplier.get());
                        Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer
                                        .serve(customerWithServiceTime);

                        return new Pair<Event, ServerBalancer>(
                                        new ServeEvent(customerWithServiceTime, serverWithBalancer.first(),
                                                        customerWithServiceTime.getArrivalTime(), false),
                                        serverWithBalancer.second());
                } else if (serverBalancer.isThereServerWithSpaceInQueue()) {
                        Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer
                                        .addToQueue(customer);
                        return new Pair<Event, ServerBalancer>(new WaitEvent(
                                        customer, serverWithBalancer.first(),
                                        serviceTimeSupplier), serverWithBalancer.second());
                }
                return new Pair<Event, ServerBalancer>(new LeaveEvent(this.customer), serverBalancer);
        }

        @Override
        public String toString() {
                return String.format("%s %s arrives", this.getCustomer()
                                .getFormattedArrivalTime(),
                                this.getCustomer());
        }

        double getEventTime() {
                return this.getCustomer().getArrivalTime();
        }
}
