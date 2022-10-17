import java.text.DecimalFormat;
import java.text.NumberFormat;

class DoneEvent extends AssignedEvent {
    private final double eventTime;

    DoneEvent(Customer customer, Server server, double eventTime) {
        super(customer, server, false, HIGH_PRIORITY);
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return String.format("%s %d done serving by %s",
                this.getFormattedEventTime(),
                this.getCustomer().getCustomerNumber(),
                this.getServer());
    }

    String getFormattedEventTime() {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(this.eventTime);
    }

    double getEventTime() {
        return this.eventTime;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        ServerBalancer newServerBalancer = serverBalancer.finishServingCustomer(this.getCustomer(), this.getServer());
        // if server has waiting customer, return new serve event
        // else, return a terminal event??
        // Make each server store the customer queue in a list in the server
        return new Pair<Event, ServerBalancer>(
                new TerminalEvent(this.getCustomer()), newServerBalancer);
    }
}
