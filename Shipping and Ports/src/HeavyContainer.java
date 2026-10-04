public class HeavyContainer extends Container {
    public HeavyContainer(int ID, int weight) {
        super(ID, weight);
        if (getClass() == HeavyContainer.class && weight <= 3000) {
            throw new IllegalArgumentException("Weight must be less than or equal to 3000");
        }
    }

    @Override
    public double consumption() {
        return 3.00 * weight;
    }
}