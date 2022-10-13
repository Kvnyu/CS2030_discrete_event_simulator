import java.util.function.Supplier;

class ServeEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;
    private final Boolean serveFromQueue;

    ServeEvent(Customer customer, int serverNumber, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue) {
        super(customer, serverNumber, false, MID_PRIORITY, eventTime);
        this.serveFromQueue = serveFromQueue;
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server availableServer = serverBalancer.getServer(this.serverNumber);

        double serviceTime = serviceTimeSupplier.get();
        // System.out.println(String.format("customer %d %f",
        // this.customer.getCustomerNumber(), serviceTime));
        availableServer = availableServer.startServing(this.getCustomer(),
                serviceTime, this.serveFromQueue);
        // System.out.println(availableServer.getNextAvailableAt());
        ServerBalancer newServerBalancer = serverBalancer.updateServer(availableServer);
        // Get server
        // Update server to available
        // Remove the customer from the list
        DoneEvent doneEvent = new DoneEvent(this.getCustomer(),
                this.getServerNumber(), this.serviceTimeSupplier,
                availableServer.getNextAvailableAt());
        return new Pair<Event, ServerBalancer>(doneEvent, newServerBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %s serves by %s",
                this.getFormattedEventTime(),
                this.getCustomer(),
                this.getServerNumber());
    }
}
