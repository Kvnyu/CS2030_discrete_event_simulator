import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.function.Supplier;

public class WaitEvent extends AssignedEvent {
    private final Supplier<Double> serviceTimeSupplier;

    WaitEvent(Customer customer, Server server, Supplier<Double> serviceTimeSupplier) {
        super(customer, server, false, HIGH_PRIORITY, false);
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    @Override
    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }

    String getFormattedEventTime(double eventTime) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(eventTime);
    }

    @Override
    boolean canExecute(ImList<Server> servers) {
        // System.out.print(String.format("Checking if %d can be served by %d: ",
        // this.getCustomer().getCustomerNumber(),
        // this.getServer().getServerNumber()));
        // if (servers.get(this.getServer().getServerIndexNumber()).isAvailable()) {
        // System.out.println("Server is available");
        // } else {
        // System.out.println("Server is not available");

        // }

        return servers.get(this.getServer().getServerIndexNumber()).isAvailable();
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        double eventTime = serverBalancer.getServerWithIndex(this.getServer().getServerIndexNumber())
                .getAvailableTime();
        System.out.println(String.format("Called for customer %d", this.getCustomer().getCustomerNumber()));
        Customer customer = this.customer.cloneWithServiceTime(serviceTimeSupplier.get());
        Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer.serve(
                customer, this.getServer());
        Server server = serverWithBalancer.first();

        // System.out.println(String.format("%f %f %f", eventTime,
        // server.getAvailableTime(),
        // customer.getServiceTime()));

        // System.out.println(String.format("%s %s serves by %s",
        // this.getFormattedEventTime(eventTime),
        // customer,
        // server));

        return new Pair<Event, ServerBalancer>(
                new ServeEvent(
                        customer, server,
                        eventTime,
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
