//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Read input.
        Scanner in = new Scanner(new File(args[0]));

        final int N = in.nextInt(); // N : Number of lines the input file has.

        ArrayList<Container> conts = new ArrayList<Container>();
        ArrayList<Ship> ships = new ArrayList<Ship>();
        ArrayList<Port> ports = new ArrayList<Port>();

        for (int i = 0; i < N; i++) {
            final int operation_type = in.nextInt();
            switch (operation_type) {
                case 1: { // Create a container.
                    final int cont_ID = conts.size();
                    final int port_ID = in.nextInt();
                    final int weight = in.nextInt();
                    Container cont;

                    if (!in.hasNext() || in.hasNextInt()) { // Heavy or Basic.
                        if (weight > 3000)
                            cont = new HeavyContainer(cont_ID, weight);
                        else
                            cont = new BasicContainer(cont_ID, weight);
                    } else { // Liquid or Refrigerated.
                        final char special_type = in.next().charAt(0);
                        if (special_type == 'L')
                            cont = new LiquidContainer(cont_ID, weight);
                        else
                            cont = new RefrigeratedContainer(cont_ID, weight);
                    }

                    ports.get(port_ID).getContainers().add(cont);
                    conts.add(cont);
                    break; }

                case 2: { // Create a ship.
                    final int ship_ID = ships.size();
                    final int port_ID = in.nextInt();
                    final int totalWeightCapacity = in.nextInt();
                    final int maxNumberOfAllContainers = in.nextInt();
                    final int maxNumberOfHeavyContainers = in.nextInt();
                    final int maxNumberOfRefrigeratedContainers = in.nextInt();
                    final int maxNumberOfLiquidContainers = in.nextInt();
                    final double fuelConsumptionPerKM = in.nextDouble();

                    ships.add(new Ship(ship_ID, ports.get(port_ID), totalWeightCapacity,
                            maxNumberOfAllContainers, maxNumberOfHeavyContainers, maxNumberOfRefrigeratedContainers,
                            maxNumberOfLiquidContainers, fuelConsumptionPerKM));
                    break; }

                case 3: { // Create a port.
                    final int port_ID = ports.size();
                    final double X = in.nextDouble();
                    final double Y = in.nextDouble();

                    ports.add(new Port(port_ID, X, Y));
                    break; }

                case 4: { // Load a container to a ship.
                    final int ship_ID = in.nextInt();
                    final int cont_ID = in.nextInt();
                    ships.get(ship_ID).load(conts.get(cont_ID));
                    break; }

                case 5: { // Unload a container from a ship.
                    final int ship_ID = in.nextInt();
                    final int cont_ID = in.nextInt();
                    ships.get(ship_ID).unLoad(conts.get(cont_ID));
                    break; }

                case 6: { // Sail ship to another port.
                    final int ship_ID = in.nextInt();
                    final int port_ID = in.nextInt();
                    ships.get(ship_ID).sailTo(ports.get(port_ID));
                    break; }

                case 7: { // Refuel ship.
                    final int ship_ID = in.nextInt();
                    final double fuel = in.nextDouble();
                    ships.get(ship_ID).reFuel(fuel);
                    break; }

                default: // Invalid operation.
                    System.out.println("Invalid operation.");
            }
        }

        in.close();

        // Write output.
        PrintStream out = new PrintStream(new File(args[1]));

        for (Port port : ports)
            out.print(port.toString());

        out.close();
    }
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE