import java.util.Optional;

class ServeEvent extends Event {
    ServeEvent(Customer customer, Server server) {
        super(customer, server, true);
    }

    @Override
    Optional<Event> getNextEvent() {
        return Optional.of(new DoneEvent(customer, server));
    }

    @Override
    public String toString() {
        return String.format("%s served by %s", this.getCustomer(), this.server);
    }
}
