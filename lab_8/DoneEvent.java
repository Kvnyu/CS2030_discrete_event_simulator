import java.util.function.Supplier;

class DoneEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    DoneEvent(Customer customer, int serverNumber, String serverName,
            Supplier<Double> serviceTimeSupplier, double eventTime) {
        super(customer, serverNumber, serverName, false, HIGH_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        AbstractServer server = serverBalancer.getServer(serverNumber);
        Pair<AbstractServer, Double> serverWithRestTime = server.finishServing();
        server = serverWithRestTime.first();
        double restTime = serverWithRestTime.second();
        Event event = new ServerRestEvent(this.customer, this.serverNumber, this.getServerName(),
                this.eventTime + restTime);
        serverBalancer = serverBalancer.updateServer(server);
        return new Pair<Event, ServerBalancer>(event, serverBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %d done serving by %s",
                this.getFormattedEventTime(),
                this.getCustomer().getCustomerNumber(),
                this.getServerName());
    }
}
