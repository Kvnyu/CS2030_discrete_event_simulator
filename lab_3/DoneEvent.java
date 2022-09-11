class DoneEvent extends Event {

    DoneEvent(Customer customer, Server server) {
        super(customer, server, false);
    }

    @Override
    public String toString() {
        return String.format("%d customer %s done serving by %s", this.getCustomer().getLeaveTime(),
                this.getCustomer().getCustomerNumber(),
                this.getServer());
    }
}
