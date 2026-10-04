public class BasicContainer extends Container {
    public BasicContainer(int ID, int weight) {
        super(ID, weight);

        if (weight > 3000) {
            throw new IllegalArgumentException("Weight must be <= 3000.");
        }
    }

    @Override
    public double consumption() {
        return 2.50 * weight;
    }
}