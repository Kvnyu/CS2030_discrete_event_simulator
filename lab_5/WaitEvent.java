import java.util.function.Supplier;

public class WaitEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    WaitEvent(Customer customer, Server server, Supplier<Double> serviceTimeSupplier) {
        super(customer, server, false, HIGH_PRIORITY);
        this.serviceTimeSupplier = serviceTimeSupplier;
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
                        customer, serverWithBalancer.first(),
                        this.getServer().getAvailableTime() + this.serviceTimeSupplier.get(),
                        true),
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
