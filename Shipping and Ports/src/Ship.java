import java.util.ArrayList;
import java.util.Comparator;

public class Ship implements IShip {

    private int ID;
    private double fuel;
    Port currentPort;
    private int totalWeightCapacity;
    private int maxNumberOfAllContainers;
    private int maxNumberOfHeavyContainers;
    private int maxNumberOfRefrigeratedContainers;
    private int maxNumberOfLiquidContainers;
    private double fuelConsumptionPerKM;

    private ArrayList<Container> containers;

    public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
        this.ID = ID;
        this.fuel = 0;
        this.currentPort = p;
        this.totalWeightCapacity = totalWeightCapacity;
        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
        this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
        this.containers = new ArrayList<>();
        p.incomingShip(this);
    }

    @Override
    public boolean sailTo(Port p) {
        containers.sort(new Comparator<Container>() {
            @Override
            public int compare(Container c1, Container c2) {
                return Integer.compare(c1.getID(), c2.getID());
            }
        });

        double distance = currentPort.getDistance(p);
        double perKM = fuelConsumptionPerKM;
        for (Container c : containers) perKM += c.consumption();
        double requiredFuel = distance * perKM;

        if (fuel >= requiredFuel) {
            currentPort.outgoingShip(this);
            fuel -= requiredFuel;
            currentPort = p;
            currentPort.incomingShip(this);
            return true;
        }

        return false; // not enough fuel
    }

    public int getID() { return ID; }

    public double getFuel() { return fuel; }

    public ArrayList<Container> getContainers() { return containers; }

    @Override
    public void reFuel(double newFuel) {
        this.fuel += newFuel;
    }

    @Override
    public boolean load(Container cont) {
        if (containers.contains(cont)) return false;
        if (!currentPort.getContainers().contains(cont)) return false; // must be waiting at this port
        if (containers.size() + 1 > maxNumberOfAllContainers) return false;

        int weight = cont.getWeight();
        int heavy = 0, refrig = 0, liquid = 0;
        for (Container c : containers) {
            weight += c.getWeight();
            if (c instanceof HeavyContainer) heavy++;
            if (c instanceof RefrigeratedContainer) refrig++;
            if (c instanceof LiquidContainer) liquid++;
        }
        if (weight > totalWeightCapacity) return false;
        if (cont instanceof HeavyContainer && heavy + 1 > maxNumberOfHeavyContainers) return false;
        if (cont instanceof RefrigeratedContainer && refrig + 1 > maxNumberOfRefrigeratedContainers) return false;
        if (cont instanceof LiquidContainer && liquid + 1 > maxNumberOfLiquidContainers) return false;

        currentPort.getContainers().remove(cont);
        containers.add(cont);
        return true;
    }

    @Override
    public boolean unLoad(Container cont) {
        if (!containers.remove(cont)) return false;
        currentPort.getContainers().add(cont);
        return true;
    }
}