import java.text.DecimalFormat;
import java.text.NumberFormat;

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

    String getFormattedArrivalTime() {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(this.getArrivalTime());
    }

    double getServiceTime() {
        return this.serviceTime;
    }

    int getCustomerNumber() {
        return this.customerNumber;
    }

    double getDoneTime() {
        return this.arrivalTime + this.serviceTime;
    }

    String getFormattedDoneTime() {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(this.getDoneTime());
    }

    @Override
    public String toString() {
        return String.format("%d", this.customerNumber);
    }
}
