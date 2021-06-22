import com.arangodb.ArangoDB;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        ArangoDB arango = ArangoDBInitializer.getDefaultDB();
        ArangoDBInitializer.createDataBase("agencyTravel");
        ArangoDBInitializer.createCollection("agencyTravel", "agencyTravel");
        ArangoAgencyTravelRepoAdapter serviceRepo = new ArangoAgencyTravelRepoAdapter(arango, "agencyTravel", "agencyTravel");
        ArangoAgencyTravelService agencyTravelService = new ArangoAgencyTravelService(serviceRepo);
        Scanner scanner = new Scanner(System.in);
        int select;
        do {
            System.out.println("[0]wyjscie\t\t[1]]dodaj biuro\t\t[2]usun biuro\t\t[3]znajdz po kluczu\t\t[4]aktualizuj biuro\t\t[5]zwroc po lokalizacji(query)\t\t" +
                    "[6]srednia liczba pracownikow dla lokalizacji\\t\"");
            select = scanner.nextInt();
            switch (select) {
                case 0:
                    break;
                case 1:
                    agencyTravelService.save();
                    break;
                case 2:
                    agencyTravelService.deleteByKey();
                    break;
                case 3:
                    agencyTravelService.getByKey();
                    break;
                case 4:
                    agencyTravelService.update();
                    break;
                case 5:
                    agencyTravelService.getByLocation();
                    break;
                case 6:
                    agencyTravelService.findAverageWorkersNumberByLocation();
                    break;
                default:
                    throw new IllegalStateException("Nie ma takiej opcji pod tym numerem: " + select);
            }
        } while (select != 0);

    }
}
