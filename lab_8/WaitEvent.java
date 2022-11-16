import java.util.function.Supplier;

class WaitEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    WaitEvent(Customer customer, int serverNumber, String serverName, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue) {
        super(customer, serverNumber, serverName, false, MID_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        AbstractServer server = serverBalancer.getServer(this.getServerNumber());
        // System.out.println(server.getCustomers());
        server = server.addCustomerToQueue(this.getCustomer());
        ServerBalancer newServerBalancer = serverBalancer.updateServer(server);
        double serviceTime = Math.max(server.getNextAvailableAt(), this.customer.getArrivalTime());
        // System.out.println("The next available time is: " +
        // server.getNextAvailableAt());
        // System.out.println("The customer's arriaval time is " +
        // this.customer.getArrivalTime());
        // System.out.println("The service time is " + serviceTime);
        ServeEvent serveEvent = new ServeEvent(this.customer, this.serverNumber, this.getServerName(),
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
