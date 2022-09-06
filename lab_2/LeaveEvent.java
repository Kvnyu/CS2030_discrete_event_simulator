class LeaveEvent extends Event {

    LeaveEvent(Customer customer, Server server) {
        super(customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s leaves", this.getCustomer());
    }
}
