class Customer {
    private final double arrivalTime;
    private final double serviceTime;

    Customer(double arrivalTime, double serviceTime){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    double getArrivalTime(){
        return this.arrivalTime;
    }

    double getServiceTime() {
        return this.serviceTime;
    }
}
