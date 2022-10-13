import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        if (e1.getEventTime() == e2.getEventTime()) {
            if (e2.getPriority() == e1.getPriority()) {
                return e1.getCustomer().getCustomerNumber()
                        - e2.getCustomer().getCustomerNumber() > 0 ? 1 : -1;
            }
            return e2.getPriority() - e1.getPriority() > 0 ? 1 : -1;
        } else {
            return e2.getEventTime() - e1.getEventTime() > 0
                    ? -1
                    : 1;
        }
    }
}
