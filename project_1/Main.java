import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ImList<Pair<Double, Double>> inputTimes = new ImList<Pair<Double, Double>>();

        int numOfServers = sc.nextInt();
        int qmax = sc.nextInt();
        while (sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            inputTimes = inputTimes.add(new Pair<Double, Double>(arrivalTime, serviceTime));
        }

        Simulator sim = new Simulator(numOfServers, qmax, inputTimes);
        System.out.println(sim.simulate());
        sc.close();
    }
}