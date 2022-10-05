public class WaitEvent extends AssignedEvent {
    private final double serviceTime;

    WaitEvent(Customer customer, Server server, double serviceTime) {
        super(customer, server, false, HIGH_PRIORITY);
        this.serviceTime = serviceTime;
    }

    @Override
    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer.serve(
                this.getCustomer(), this.getServer());
        return new Pair<Event, ServerBalancer>(
                new ServeEvent(
                        customer, serverWithBalancer.first(), this.serviceTime, true),
                serverWithBalancer.second());
    }

    @Override
    public String toString() {
        return String.format("%s %s waits at %s",
                this.getCustomer().getFormattedArrivalTime(),
                this.getCustomer(),
                this.getServer());
    }

}
