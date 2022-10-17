import java.util.function.Supplier;

class ServeEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;
    private final Boolean serveFromQueue;

    ServeEvent(Customer customer, int serverNumber, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue, boolean readyToPrint) {
        super(customer, serverNumber, false, HIGH_PRIORITY, eventTime, readyToPrint);
        this.serveFromQueue = serveFromQueue;
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(this.serverNumber);
        ServerBalancer newServerBalancer = serverBalancer;
        Event event;
        if (this.isReadyToExecute()) {
            double serviceTime = serviceTimeSupplier.get();
            server = server.startServing(this.getCustomer(),
                    serviceTime, this.serveFromQueue);
            newServerBalancer = serverBalancer.updateServer(server);
            event = new DoneEvent(this.getCustomer(),
                    this.getServerNumber(), this.serviceTimeSupplier,
                    server.getNextAvailableAt());
        } else if (server.isAvailable()) {
            event = new ServeEvent(this.getCustomer(),
                    this.getServerNumber(), this.eventTime,
                    this.serviceTimeSupplier, this.serveFromQueue, true);
        } else {
            // Otherwise, return a new ServeEvent with event time as the availableServer's
            // next available time
            event = new ServeEvent(this.customer, this.serverNumber, server.getNextAvailableAt(),
                    this.serviceTimeSupplier, this.serveFromQueue, false);

        }
        return new Pair<Event, ServerBalancer>(event, newServerBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %s serves by %s",
                this.getFormattedEventTime(),
                this.getCustomer(),
                this.getServerNumber());
    }
}
