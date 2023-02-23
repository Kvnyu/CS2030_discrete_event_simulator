import java.util.Comparator;

class CustomerComparator implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getCustomerNumber() - c2.getCustomerNumber() > 0
                ? -1
                : 1;
    }
}
