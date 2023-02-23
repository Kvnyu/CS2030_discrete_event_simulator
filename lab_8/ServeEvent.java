import java.util.function.Supplier;

class ServeEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;
    private final Boolean serveFromQueue;

    ServeEvent(String serverName, Customer customer, int serverNumber, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue, boolean readyToExecute) {
        super(serverName, customer, serverNumber, false, HIGH_PRIORITY, eventTime, readyToExecute);
        // System.out.println(eventTime);
        this.serveFromQueue = serveFromQueue;
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(this.serverNumber);
        ServerBalancer newServerBalancer = serverBalancer;
        Event event;
        if (this.isReadyToExecute()) {
            // System.out.println(1);
            double serviceTime = serviceTimeSupplier.get();
            // System.out.println(String.format("server %s", server));
            if (server.getIsSc() && this.serveFromQueue) {
                newServerBalancer = serverBalancer.popScQueue();
                server = server.startServing(this.getCustomer(),
                        serviceTime, false, this.getEventTime());
                newServerBalancer = newServerBalancer.updateServer(server);
                event = new DoneEvent(server.toString(), this.getCustomer(),
                        this.getServerNumber(), this.serviceTimeSupplier,
                        server.getNextAvailableAt());
            } else {
                server = server.startServing(this.getCustomer(),
                        serviceTime, this.serveFromQueue, this.getEventTime());
                newServerBalancer = serverBalancer.updateServer(server);
                event = new DoneEvent(server.toString(), this.getCustomer(),
                        this.getServerNumber(), this.serviceTimeSupplier,
                        server.getNextAvailableAt());
            }
        } else if (server.isAvailableAt(this.getEventTime())) {
            // System.out.println(2);
            double servingTime = Math.max(customer.getArrivalTime(), server.getNextAvailableAt());
            server = server.getIfAvailable(this.getEventTime());
            // Need to remove first customer from queue in ScServer and add to queue in
            // Server
            event = new ServeEvent(server.toString(), this.getCustomer(),
                    server.getServerNumber(), servingTime,
                    this.serviceTimeSupplier, this.serveFromQueue, true);
        } else {
            // Otherwise, return a new ServeEvent with event time as the availableServer's
            // next available time
            // System.out.println(3);
            // System.out.println(server);
            // System.out.println(server.getNextAvailableAt());
            event = new ServeEvent(server.toString(), this.customer,
                    this.serverNumber, server.getNextAvailableAt(),
                    this.serviceTimeSupplier, this.serveFromQueue, false);

        }
        return new Pair<Event, ServerBalancer>(event, newServerBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %s serves by %s",
                this.getFormattedEventTime(),
                this.getCustomer(),
                this.getServerName());
    }
}
