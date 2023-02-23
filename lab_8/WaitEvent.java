import java.util.function.Supplier;

class WaitEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    WaitEvent(String serverName, Customer customer, int serverNumber, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue) {
        super(serverName, customer, serverNumber, false, MID_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(this.getServerNumber());
        server = server.addCustomerToQueue(this.getCustomer());
        ServerBalancer newServerBalancer = serverBalancer.updateServer(server);
        double serviceTime = Math.max(server.getNextAvailableAt(), this.customer.getArrivalTime());
        ServeEvent serveEvent = new ServeEvent(server.toString(), this.customer, this.serverNumber,
                serviceTime,
                serviceTimeSupplier, true, false);

        return new Pair<Event, ServerBalancer>(serveEvent, newServerBalancer);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %s", this.eventTime,
                this.customer.getCustomerNumber(),
                this.getServerName());
    }
}
