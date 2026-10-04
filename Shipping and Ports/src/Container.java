public abstract class Container {
    protected int ID;
    protected int weight;

    public Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
    }

    public abstract double consumption();

    public int getID() { return ID; }

    public int getWeight() { return weight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Container other = (Container) o;
        return this.ID == other.ID && this.weight == other.weight;
    }

    @Override
    public int hashCode() { return 31 * ID + weight; }
}
