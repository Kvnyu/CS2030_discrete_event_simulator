import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.function.Supplier;

class ArriveEvent extends Event {
    private final Supplier<Double> serviceTimeSupplier;

    ArriveEvent(Customer customer, Supplier<Double> serviceTimeSupplier) {
        super(customer, LOW_PRIORITY);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    ArriveEvent(Customer customer, Supplier<Double> serviceTimeSupplier, double eventTime) {
        super(customer, false, LOW_PRIORITY, eventTime);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        if (serverBalancer.isThereAServerAvailable()) {
            // Get server
            Server availableServer = serverBalancer.getAvailableServer();
            // Create new serveEvent with server and customer
            ServeEvent serveEvent = new ServeEvent(this.getCustomer(), availableServer.getServerNumber(),
                    this.getCustomer().getArrivalTime(), serviceTimeSupplier);
            return new Pair<Event, ServerBalancer>(serveEvent, serverBalancer);
        } else if (serverBalancer.isThereServerWithSpaceInQueue()) {
            Server serverWithSpaceInQueue = serverBalancer.getServerWithSpaceInQueue();
            // Create new serveEvent with server and customer
            serverWithSpaceInQueue = serverWithSpaceInQueue.addCustomerToQueue(this.getCustomer());
            System.out.println(
                    String.format("%.3f %d waits at %d", this.eventTime, this.customer.getCustomerNumber(),
                            serverWithSpaceInQueue.getServerNumber()));
            ServerBalancer newServerBalancer = serverBalancer.updateServer(serverWithSpaceInQueue);
            TerminalEvent terminalEvent = new TerminalEvent(customer);
            return new Pair<Event, ServerBalancer>(terminalEvent, newServerBalancer);
        }
        return new Pair<Event, ServerBalancer>(new LeaveEvent(this.customer), serverBalancer);
    }

    // // TODO: Change this name
    // String formatDouble(Double number) {
    // NumberFormat formatter = new DecimalFormat("#0.0");
    // return formatter.format(number);
    // }

    @Override
    public String toString() {
        return String.format("%s %s arrives", this.getFormattedEventTime(),
                this.getCustomer());
    }

    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }
}
