import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;

    Simulator(int numOfServers, int qmax, ImList<Pair<Double, Supplier<Double>>> inputTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
    }

    String simulate() {
        int customerNumber = 1;
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        ServerBalancer serverBalancer = new ServerBalancer(this.numOfServers, this.qmax);

        for (Pair<Double, Supplier<Double>> customerPair : this.inputTimes) {

            double arrivalTime = customerPair.first();

            Customer customer = new Customer(arrivalTime, customerNumber);
            queue = queue.add(new ArriveEvent(customer, customerPair.second()));
            customerNumber += 1;
        }

        while (!queue.isEmpty()) {
            System.out.println("--------new iter--------");
            // Get the current highest priority event, print it
            // Get the event that follows from this event, add it to the queue
            // Arrive -> Serve -> Done -> Terminal
            // Arrive -> Wait -> Serve -> Done -> Terminal
            // Arrive -> Leave -> Terminal

            Pair<Event, PQ<Event>> pair = queue.poll();
            Event event = pair.first();
            queue = pair.second();

            // TODO: Extract to function
            ImList<Event> unexecutableEvents = new ImList<Event>();
            while (!serverBalancer.canExecute(event)) {
                unexecutableEvents = unexecutableEvents.add(event);
                pair = queue.poll();
                event = pair.first();
                queue = pair.second();
            }

            for (Event unexecutableEvent : unexecutableEvents) {
                queue = queue.add(unexecutableEvent);
            }

            if (!event.isTerminalEvent() && event.canOutput()) {
                System.out.println(event);
            }

            // Special queue
            // specialQueue.poll() logic:
            // If there is a free server, then check if there are any serveEvents for that
            // server
            // Get the serveEvent with the lowest customer number and return it.
            // Can only mutate servers when getting the next event
            // Can't change events once you add them to the queue...
            // Serve events are different to other events in that we don't know what time
            // they will execute
            // TODO: Make wait event time Math.inf
            // TODO: At start of each iteration, check if there is any server that is free
            // If server is free, make event =
            // this.getServerQueue(server).serveNextWaitEvent()
            // DoneEvent.getNextEvent() returns a new ServeEvent?
            // WaitEvent are stored in Server only

            // TODO: Change the class signature of getNextEvent to require servers list
            Pair<Event, ServerBalancer> eventServers = event.getNextEvent(serverBalancer);
            Event nextEvent = eventServers.first();
            serverBalancer = eventServers.second();
            // serverBalancer.checkWhichServersCanExecute();
            // TODO: Create a new Terminal event attribute on Event, and TerminalEvent class
            // that all terminal events return as "getNextEvent"

            if (!nextEvent.isTerminalEvent()) {
                queue = queue.add(nextEvent);
            }

        }

        int customerCount = customerNumber - 1;
        ImList<Double> returnArray = new ImList<Double>();
        int totalCustomersServed = serverBalancer.getTotalCustomersServed();
        int totalCustomersLeft = customerCount - totalCustomersServed;
        double averageWaitingTime = serverBalancer.getTotalCustomerWaitTime()
                / totalCustomersServed;
        returnArray = returnArray.add(averageWaitingTime);

        return String.format("[%.3f %d %d]", averageWaitingTime,
                totalCustomersServed, totalCustomersLeft);
    }
}
