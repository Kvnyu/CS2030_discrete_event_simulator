class LeaveEvent extends Event {

    LeaveEvent(Customer customer, Server server) {
        super(customer, server, false);
    }

    @Override
    public String toString() {
        return String.format("%s %s leaves",
                this.getCustomer().getArrivalTime(),
                this.getCustomer());
    }
}
