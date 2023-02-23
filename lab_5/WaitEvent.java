import java.rmi.ServerError;
import java.util.function.Supplier;

class WaitEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    WaitEvent(Customer customer, int serverNumber, double eventTime,
            Supplier<Double> serviceTimeSupplier,
            boolean serveFromQueue) {
        super(customer, serverNumber, false, MID_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(this.getServerNumber());
        // System.out.println(server.getCustomers());
        server = server.addCustomerToQueue(this.getCustomer());
        ServerBalancer newServerBalancer = serverBalancer.updateServer(server);
        ServeEvent serveEvent = new ServeEvent(this.customer, this.serverNumber,
                server.getNextAvailableAt(),
                serviceTimeSupplier, true, false);

        return new Pair<Event, ServerBalancer>(serveEvent, newServerBalancer);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %d", this.eventTime,
                this.customer.getCustomerNumber(),
                this.serverNumber);
    }
}
