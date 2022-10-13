import java.util.Scanner;
import java.util.function.Supplier;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ImList<Pair<Double, Supplier<Double>>> inputTimes = new ImList<
            Pair<Double, Supplier<Double>>>();

        int numOfServers = sc.nextInt();
        int qmax = sc.nextInt();
        int numOfCustomers = sc.nextInt();

        for (int i = 0; i < numOfCustomers; i++) {
            double arrivalTime = sc.nextDouble();
            Supplier<Double> serviceTime = () -> sc.nextDouble();
            inputTimes = inputTimes.add(
                    new Pair<Double, Supplier<Double>>(arrivalTime, serviceTime));
        }

        Simulator sim = new Simulator(numOfServers, qmax, inputTimes);
        System.out.println(sim.simulate());
        sc.close();
    }
}