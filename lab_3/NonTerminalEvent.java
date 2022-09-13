abstract class NonTerminalEvent extends Event {
    NonTerminalEvent(Customer customer, Server server) {
        super(customer, server, true);
    }

    NonTerminalEvent(Customer customer, Server server, int priority) {
        super(customer, server, true, priority);
    }

    abstract Event getNextEvent();
}
