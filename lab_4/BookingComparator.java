import java.util.Comparator;

public class BookingComparator implements Comparator<Booking> {
    public int compare(Booking b1, Booking b2) {
        return b1.compareTo(b2);
    }
}
