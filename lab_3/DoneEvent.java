class DoneEvent extends Event {

    DoneEvent(Customer customer, Server server) {
        super(customer, server, false);
    }

    @Override
    public String toString() {
        return String.format("%f customer %d done serving by %s", this.getCustomer().getLeaveTime(),
                this.getCustomer().getCustomerNumber(),
                this.getServer());
    }
}
