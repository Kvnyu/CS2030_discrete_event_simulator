import java.util.Optional;

class ServeEvent extends Event {
    ServeEvent(Customer customer, Server server) {
        super(customer, server, true);
    }

    @Override
    Optional<Event> getNextEvent() {
        if (this.server.isFreeAt(customer.getArrivalTime())) {
            Server server = this.server.serve(customer);
            return Optional.of(new ServeEvent(customer, server));
        }
        return Optional.of(new LeaveEvent(this.customer, server));
    }

    @Override
    public String toString() {
        return String.format("%s served by %s", this.getCustomer(), this.server);
    }
}
