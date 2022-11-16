import java.util.function.Supplier;

class Server extends AbstractServer {
    Server(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, 0.0);
    }

    Server(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, 0, nextAvailableAt);
    }

    Server(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes,
                totalCustomersServed, 0.0, nextAvailableAt);
    }

    Server(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed,
            double totalCustomerWaitTime, double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, totalCustomersServed,
                0.0, true, nextAvailableAt);
    }

    Server(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, totalCustomersServed,
                totalCustomerWaitTime, isAvailable, nextAvailableAt,
                new ImList<Customer>());
    }

    Server(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers) {
        super(isSelfCheckServer, serverNumber, maxQSize, restTimes, totalCustomersServed, totalCustomerWaitTime,
                isAvailable,
                nextAvailableAt,
                customers);
    }

    boolean isAvailableAt(double eventTime) {
        return this.isAvailable() && this.getNextAvailableAt() <= eventTime;
    }

    boolean hasSpaceInQueueAt(double eventTime) {
        if (this.getNextAvailableAt() <= eventTime) {
            return this.getMaxQSize() - this.getQueueSize() > 0;
        } else {
            return this.getMaxQSize() - this.getQueueSize() + 1 > 0;
        }
    }

    Server startServing(Customer customer, int serverNumber, double serviceTime,
            boolean serveFromQueue, double eventTime) {
        double startServingTime;
        double currentCustomerWaitTime = eventTime - customer.getArrivalTime();
        double newTotalCustomerWaitTime = this.getTotalCustomerWaitTime() + currentCustomerWaitTime;
        if (serveFromQueue) {
            startServingTime = this.getNextAvailableAt();
        } else {
            startServingTime = customer.getArrivalTime();
        }
        ImList<Customer> customers = this.getCustomers();
        if (!serveFromQueue) {
            customers = this.getCustomers().add(customer);
        }
        if (this.getTotalCustomersServed() == 0) {
            return new Server(this.isSelfCheckServer(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                    this.getTotalCustomersServed(), newTotalCustomerWaitTime,
                    false, customer.getArrivalTime() + serviceTime, customers);
        }

        return new Server(this.isSelfCheckServer(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                this.getTotalCustomersServed(), newTotalCustomerWaitTime,
                false, startServingTime + serviceTime, customers);
    }

    Server addCustomerToQueue(Customer customer) {
        ImList<Customer> customers = this.getCustomers().add(customer);
        return new Server(this.isSelfCheckServer(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(),
                this.isAvailable(), this.getNextAvailableAt(), customers);
    }

    Pair<AbstractServer, Double> finishServing() {
        double restTime = this.isSelfCheckServer() ? 0.0 : this.getRestTimes().get();
        double newNextAvailableAt = restTime + this.getNextAvailableAt();

        return new Pair<AbstractServer, Double>(
                new Server(this.isSelfCheckServer(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                        this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(),
                        false, newNextAvailableAt, this.getCustomers()),
                restTime);
    }

    Customer getNextCustomerInQueue() {
        return this.getCustomers().get(0);
    }

    Server returnFromRest(int serverNumber) {
        ImList<Customer> newCustomers = this.getCustomers().remove(0);
        int newTotalCustomersServed = this.getTotalCustomersServed() + 1;

        return new Server(this.isSelfCheckServer(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                newTotalCustomersServed,
                this.getTotalCustomerWaitTime(), true,
                this.getNextAvailableAt(), newCustomers);

    }

    Server updateServerNextAvailableAt(double nextAvailableAt) {
        return new Server(this.isSelfCheckServer(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(), this.isAvailable(), nextAvailableAt,
                this.getCustomers());
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.getServerNumber(), this.getCustomers().toString());
    }
}
