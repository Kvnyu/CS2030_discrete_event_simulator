import java.util.function.Supplier;

class ArriveEvent extends Event {
    private final Supplier<Double> arrivalTimeSupplier;
    private final int customerNumberMagix = 5;

    // TODO: change arrivalTimeSupplier to serviceTimeSupplier
    ArriveEvent(Customer customer, Supplier<Double> arrivalTimeSupplier) {
        super(customer, LOW_PRIORITY);
        this.arrivalTimeSupplier = arrivalTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        if (serverBalancer.isThereAServerFreeAt(this.customer.getArrivalTime())) {
            Customer customerWithServiceTime = this.customer
                    .cloneWithServiceTime(this.arrivalTimeSupplier.get());
            Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer
                    .serve(customerWithServiceTime);

            return new Pair<Event, ServerBalancer>(
                    new ServeEvent(customerWithServiceTime, serverWithBalancer.first(),
                            customerWithServiceTime.getArrivalTime(), false),
                    serverWithBalancer.second());
        } else if (serverBalancer.isThereServerWithSpaceInQueue()) {
            Customer customerWithServiceTime = this.customer
                    .cloneWithServiceTime(this.arrivalTimeSupplier.get());
            Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer
                    .addToQueue(customerWithServiceTime);
            return new Pair<Event, ServerBalancer>(new WaitEvent(
                    customerWithServiceTime, serverWithBalancer.first(),
                    serverWithBalancer.first().getAvailableTime() -
                            customerWithServiceTime.getServiceTime()),
                    serverWithBalancer.second());
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
