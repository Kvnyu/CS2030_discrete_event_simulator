import java.util.function.Supplier;

class DoneEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    DoneEvent(String serverName, Customer customer, int serverNumber,
            Supplier<Double> serviceTimeSupplier, double eventTime) {
        super(serverName, customer, serverNumber, false, HIGH_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(serverNumber);
        Pair<Server, Double> serverWithRestTime = server.finishServing();
        server = serverWithRestTime.first();
        double restTime = serverWithRestTime.second();
        Event event = new ServerRestEvent(this.serverName, this.customer, this.serverNumber,
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
