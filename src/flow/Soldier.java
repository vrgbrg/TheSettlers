package flow;

public class Soldier extends Cell{
    private String name;
    private int power;

    public Soldier(String value, String name, int power) {
        super(value);
        this.name = name;
        this.power = power;
    }

    public Soldier(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }

    public String toString() {
        return super.toString();
    }

}
