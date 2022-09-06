class ArriveEvent extends Event {
    ArriveEvent(Customer customer) {
        super(customer);
    }

    @Override
    public String toString() {
        return String.format("%s arrives", this.getCustomer());
    }
}
