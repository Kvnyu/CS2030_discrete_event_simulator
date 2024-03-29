import java.util.function.Supplier;

class DoneEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    DoneEvent(Customer customer, int serverNumber,
            Supplier<Double> serviceTimeSupplier, double eventTime) {
        super(customer, serverNumber, false, HIGH_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(serverNumber);
        server = server.finishServing();
        Event event = new TerminalEvent(this.customer);
        serverBalancer = serverBalancer.updateServer(server);
        return new Pair<Event, ServerBalancer>(event, serverBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %d done serving by %s",
                this.getFormattedEventTime(),
                this.getCustomer().getCustomerNumber(),
                this.getServerNumber());
    }
}
