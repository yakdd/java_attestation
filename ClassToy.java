
public class ClassToy implements Comparable<ClassToy> {

    protected int id;
    protected String name;
    protected int weight;

    public ClassToy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.name + ";  id: " + this.id + ";  вес: " + this.weight + "%.";
    }

    @Override
    public int compareTo(ClassToy other) {
        return other.getWeight() - this.getWeight();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
