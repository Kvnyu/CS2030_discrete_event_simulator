class LeaveEvent extends Event {
    LeaveEvent(Customer customer) {
        super(customer);
    }

    @Override
    public String toString() {
        return String.format("%s leaves", this.getCustomer());
    }
}
