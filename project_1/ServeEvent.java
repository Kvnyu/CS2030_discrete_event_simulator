import java.text.DecimalFormat;
import java.text.NumberFormat;

class ServeEvent extends AssignedEvent {
    private final double eventTime;

    ServeEvent(Customer customer, Server server, double eventTime) {
        super(customer, server, false, LOW_PRIORITY);
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return String.format("%s %s served by %s",
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
        return new Pair<Event, ServerBalancer>(
                new DoneEvent(customer, this.getServer(), this.eventTime + this.customer.getServiceTime()),
                serverBalancer);
    }
}
