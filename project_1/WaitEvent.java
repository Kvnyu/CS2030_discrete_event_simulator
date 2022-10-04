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
        return new Pair<Event, ServerBalancer>(
                new ServeEvent(customer, this.getServer(), this.serviceTime), serverBalancer);
    }

    @Override
    public String toString() {
        return String.format("%s %s waits at %s",
                this.getCustomer().getFormattedArrivalTime(),
                this.getCustomer(),
                this.getServer());
    }

}
