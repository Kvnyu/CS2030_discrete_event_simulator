class Customer {
    private final double arrivalTime;
    private final double serviceTime;
    private final int customerNumber;

    Customer(double arrivalTime, double serviceTime, int customerNumber) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.customerNumber = customerNumber;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getServiceTime() {
        return this.serviceTime;
    }

    int getCustomerNumber() {
        return this.customerNumber;
    }

    double getLeaveTime() {
        return this.arrivalTime + this.serviceTime;
    }

    @Override
    public String toString() {
        return String.format("customer %d", this.customerNumber);
    }
}
