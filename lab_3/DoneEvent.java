class DoneEvent extends Event {

    DoneEvent(Customer customer, Server server) {
        super(customer, server, false);
    }

    @Override
    public String toString() {
        return String.format("%s customer %d done serving by %s", this.getCustomer().getFormattedDoneTime(),
                this.getCustomer().getCustomerNumber(),
                this.getServer());
    }
}
