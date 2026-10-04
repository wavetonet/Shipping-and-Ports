import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class Port implements IPort {

    private int ID;
    private double X;
    private double Y;
    private ArrayList<Container> containers;
    private ArrayList<Ship> history;
    private ArrayList<Ship> current;

    public Port() {
        this(0, 0.0, 0.0);
    }

    public Port(int ID, double X, double Y) {
        this.ID = ID;
        this.X = X;
        this.Y = Y;
        this.containers = new ArrayList<>();
        this.history = new ArrayList<>();
        this.current = new ArrayList<>();
    }

    public double getDistance(Port other) {
        double dx = this.X - other.X;
        double dy = this.Y - other.Y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public double getX() { return X; }
    public void setX(double X) { this.X = X; }

    public double getY() { return Y; }
    public void setY(double Y) { this.Y = Y; }

    public ArrayList<Container> getContainers() { return containers; }

    @Override
    public void incomingShip(Ship s) {
        current.add(s);
        if (!history.contains(s)) history.add(s);
    }

    @Override
    public void outgoingShip(Ship s) {
        current.remove(s);
    }

    private static String idList(String name, ArrayList<Container> all, Class<?> type, String indent) {
        ArrayList<Container> list = new ArrayList<>();
        for (Container c : all) if (c.getClass() == type) list.add(c);
        list.sort(Comparator.comparingInt(Container::getID));
        StringBuilder sb = new StringBuilder(indent + name + ":");
        for (Container c : list) sb.append(" ").append(c.getID());
        return sb.append("\n").toString();
    }

    private static String contents(ArrayList<Container> all, String indent) {
        return idList("BasicContainer", all, BasicContainer.class, indent)
                + idList("HeavyContainer", all, HeavyContainer.class, indent)
                + idList("RefrigeratedContainer", all, RefrigeratedContainer.class, indent)
                + idList("LiquidContainer", all, LiquidContainer.class, indent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.US, "Port %d: (%.2f, %.2f)\n", ID, X, Y));
        sb.append(contents(containers, "  "));
        ArrayList<Ship> ships = new ArrayList<>(current);
        ships.sort(Comparator.comparingInt(Ship::getID));
        for (Ship sh : ships) {
            sb.append(String.format(Locale.US, "  Ship %d: %.2f\n", sh.getID(), sh.getFuel()));
            sb.append(contents(sh.getContainers(), "    "));
        }
        return sb.toString();
    }
}