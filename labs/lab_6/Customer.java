import java.text.DecimalFormat;
import java.text.NumberFormat;

class Customer {
    private final double arrivalTime;
    private final int customerNumber;

    Customer(double arrivalTime, int customerNumber) {
        this.arrivalTime = arrivalTime;
        this.customerNumber = customerNumber;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    String getFormattedArrivalTime() {
        NumberFormat formatter = new DecimalFormat("#0.0");
        return formatter.format(this.getArrivalTime());
    }

    int getCustomerNumber() {
        return this.customerNumber;
    }

    @Override
    public String toString() {
        return String.format("%d", this.customerNumber);
    }
}
