
public class ClassToy {

    protected int id;
    protected String name;
    protected float weight;

    public ClassToy(int id, String name, float weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Toy: " + this.name + ";  ID: " + this.id + ";  Weight: " + this.weight + "%.";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

}
