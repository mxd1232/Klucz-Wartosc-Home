import models.AgencyTravel;

import java.util.List;
import java.util.Scanner;

public class ArangoAgencyTravelService {

    private ArangoAgencyTravelRepoAdapter arangoAgencyTravelRepoAdapter;

    public ArangoAgencyTravelService(ArangoAgencyTravelRepoAdapter arangoAgencyTravelRepoAdapter) {
        this.arangoAgencyTravelRepoAdapter = arangoAgencyTravelRepoAdapter;
    }

    public void save() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        System.out.println("Podaj lokalizacje:");
        String location = scanner.nextLine();

        System.out.println("Podaj nazwe:");
        String name = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Podaj liczbe pracownikow:");
        int workersNumber = scanner.nextInt();

        AgencyTravel agencyTravel = new AgencyTravel(location, name, workersNumber);
        arangoAgencyTravelRepoAdapter.addDocument(key, agencyTravel);

    }

    public Double findAverageWorkersNumberByLocation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj lokalizacje:");
        String location = scanner.nextLine();

        return arangoAgencyTravelRepoAdapter.findAverageWorkersNumberByLocation(location);
    }

    public AgencyTravel getByKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        return arangoAgencyTravelRepoAdapter.getByKey(key);
    }

    public List<AgencyTravel> getByLocation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj lokalizacje:");
        String location = scanner.nextLine();

        return arangoAgencyTravelRepoAdapter.getByLocation(location);
    }

    public void deleteByKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        arangoAgencyTravelRepoAdapter.deleteDocument(key);
    }

    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        System.out.println("Podaj lokalizacje:");
        String location = scanner.nextLine();

        System.out.println("Podaj nazwe:");
        String name = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Podaj liczbe pracownikow:");
        int workersNumber = scanner.nextInt();

        AgencyTravel agencyTravel = new AgencyTravel(location, name, workersNumber);
        arangoAgencyTravelRepoAdapter.updateDocument(key, agencyTravel);
    }
}
