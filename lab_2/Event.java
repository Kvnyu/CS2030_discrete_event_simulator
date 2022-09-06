class Event {
    private final Customer customer;

    Event(Customer customer) {
        this.customer = customer;
    }

    protected Customer getCustomer() {
        return customer;
    }
}
