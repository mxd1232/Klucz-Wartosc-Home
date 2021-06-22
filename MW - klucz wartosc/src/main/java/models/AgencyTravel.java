package models;

public class AgencyTravel {
    private final String location;
    private final String name;
    private final int numberOfWorkers;

    public AgencyTravel(String location, String name, int numberOfWorkers) {
        this.location = location;
        this.name = name;
        this.numberOfWorkers = numberOfWorkers;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    @Override
    public String toString() {
        return "AgencyTravel{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", numberOfWorkers=" + numberOfWorkers +
                '}';
    }
}
