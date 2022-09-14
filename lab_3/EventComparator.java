import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        if (e1.getEventTime() == e2.getEventTime()) {
            return e2.getPriority() - e1.getPriority() > 0 ? 1 : -1;
        } else {
            return e2.getCustomer().getArrivalTime() - e1.getCustomer().getArrivalTime() > 0
                    ? -1
                    : 1;
        }
    }
}
