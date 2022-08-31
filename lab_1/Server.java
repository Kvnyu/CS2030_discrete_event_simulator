class Server {
    private final String name;
    private final Double availableTime;
    private static final double THRESHOLD = 1E-15;
    Server(String name) {
        this.name = name;
        this.availableTime = 1.0;
    }

    Server(String name, double availableTime){
        this.name = name;
        this.availableTime = availableTime;
    }

    boolean isFreeAt(double arrivalTime){
        return availableTime - arrivalTime < THRESHOLD;
    }

    Server serve(Customer customer){
        return new Server(this.name, this.availableTime + customer.getServiceTime());
    }

    @Override
    public String toString(){
        return this.name;
    }
}
