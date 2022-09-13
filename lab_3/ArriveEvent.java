import java.util.Optional;

class ArriveEvent extends Event {
    ArriveEvent(Customer customer, Server server) {
        super(customer, server, true, LOW_PRIORITY);
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
        return String.format("%s arrives", this.getCustomer());
    }
}
