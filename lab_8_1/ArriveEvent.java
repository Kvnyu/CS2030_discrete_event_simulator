import java.util.function.Supplier;

class ArriveEvent extends Event {
    private final Supplier<Double> serviceTimeSupplier;

    ArriveEvent(Customer customer, Supplier<Double> serviceTimeSupplier) {
        super(customer, LOW_PRIORITY);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    ArriveEvent(Customer customer, Supplier<Double> serviceTimeSupplier, double eventTime) {
        super(customer, false, LOW_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        if (serverBalancer.isThereAServerAvailableAt(this.getEventTime())) {
            // Get server
            Server availableServer = serverBalancer.getAvailableServerAt(this.getEventTime());
            // Create new serveEvent with server and customer
            ServeEvent serveEvent = new ServeEvent(this.getCustomer(),
                    availableServer.getServerNumber(),
                    this.getCustomer().getArrivalTime(), this.serviceTimeSupplier,
                    false, false);
            return new Pair<Event, ServerBalancer>(serveEvent, serverBalancer);
        } else if (serverBalancer.isThereServerWithSpaceInQueueAt(this.getEventTime())) {
            // Create new serveEvent with server and customer
            Server serverWithSpaceInQueue = serverBalancer
                    .getServerWithSpaceInQueueAt(this.getEventTime());
            WaitEvent waitEvent = new WaitEvent(this.customer,
                    serverWithSpaceInQueue.getServerNumber(), this.eventTime,
                    this.serviceTimeSupplier,
                    false);
            return new Pair<Event, ServerBalancer>(waitEvent, serverBalancer);
        }
        return new Pair<Event, ServerBalancer>(new LeaveEvent(
                this.customer, this.customer.getArrivalTime()),
                serverBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %s arrives", this.getFormattedEventTime(),
                this.getCustomer());
    }

    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }
}
