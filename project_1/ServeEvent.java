import java.text.DecimalFormat;
import java.text.NumberFormat;

class ServeEvent extends AssignedEvent {
    private final double eventTime;
    private final boolean serveFromQueue;

    ServeEvent(Customer customer, Server server, double eventTime, boolean serveFromQueue) {
        super(customer, server, false, HIGH_PRIORITY);
        this.eventTime = eventTime;
        this.serveFromQueue = serveFromQueue;
    }

    @Override
    public String toString() {
        return String.format("%s %s serves by %s",
                this.getFormattedEventTime(),
                this.getCustomer(),
                this.getServer());
    }

    double getEventTime() {
        return this.eventTime;
    }

    String getFormattedEventTime() {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(this.eventTime);
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        ServerBalancer newServerBalancer = serverBalancer;
        if (this.serveFromQueue) {
            newServerBalancer = serverBalancer.decrementServerQueue(this.getServer());
        }
        return new Pair<Event, ServerBalancer>(
                new DoneEvent(customer, this.getServer(),
                        this.eventTime + this.customer.getServiceTime()),
                newServerBalancer);
    }
}
