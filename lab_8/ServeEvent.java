import java.util.function.Supplier;

class ServeEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;
    private final Boolean serveFromQueue;

    ServeEvent(Customer customer, int serverNumber, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue, boolean readyToExecute) {
        super(customer, serverNumber, false, HIGH_PRIORITY, eventTime, readyToExecute);
        // System.out.println(eventTime);
        this.serveFromQueue = serveFromQueue;
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        // For self-check, just make one single server. Can set the serverNumber in here
        // when ready to serve
        AbstractServer server = serverBalancer.getServer(this.serverNumber);
        ServerBalancer newServerBalancer = serverBalancer;
        Event event;
        if (this.isReadyToExecute()) {
            // System.out.println(1);
            double serviceTime = serviceTimeSupplier.get();
            server = server.startServing(this.getCustomer(),
                    serviceTime, this.serveFromQueue, this.getEventTime());
            newServerBalancer = serverBalancer.updateServer(server);
            event = new DoneEvent(this.getCustomer(),
                    this.getServerNumber(), this.serviceTimeSupplier,
                    server.getNextAvailableAt());
        } else if (server.isAvailableAt(this.getEventTime())) {
            // System.out.println(2);
            double servingTime = Math.max(customer.getArrivalTime(), server.getNextAvailableAt());
            event = new ServeEvent(this.getCustomer(),
                    this.getServerNumber(), servingTime,
                    this.serviceTimeSupplier, this.serveFromQueue, true);
        } else {
            // Otherwise, return a new ServeEvent with event time as the availableServer's
            // next available time
            // System.out.println(3);
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
