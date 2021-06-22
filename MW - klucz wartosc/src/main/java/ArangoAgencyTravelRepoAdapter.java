import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import models.AgencyTravel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class ArangoAgencyTravelRepoAdapter implements ArangoServicePort<AgencyTravel, String> {

    private final ArangoDB arangoDB;
    private final String databaseName;
    private final String collectionName;

    public ArangoAgencyTravelRepoAdapter(ArangoDB arangoDB, String databaseName, String collectionName) {
        this.arangoDB = arangoDB;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @Override
    public void addDocument(String key, AgencyTravel agencyTravel) {
        BaseDocument documentObject = new BaseDocument(key);
        documentObject.addAttribute("location", agencyTravel.getLocation());
        documentObject.addAttribute("name", agencyTravel.getName());
        documentObject.addAttribute("numberOfWorkers", agencyTravel.getNumberOfWorkers());

        try {
            arangoDB.db(databaseName).collection(collectionName).insertDocument(documentObject);
            System.out.println("\tDocument created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }
    }

    @Override
    public AgencyTravel getByKey(String key) {
        try {
            BaseDocument myDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);

            String location = (String) myDocument.getAttribute("location");
            String name = (String) myDocument.getAttribute("name");
            int numberOfWorkers = Integer.parseInt(myDocument.getAttribute("numberOfWorkers").toString());
            AgencyTravel agencyTravel = new AgencyTravel(location, name, numberOfWorkers);
            System.out.println(agencyTravel);
            return agencyTravel;
        } catch (NullPointerException e) {
            System.out.println("brak biura o danym id");
            return null;
        }
    }

    @Override
    public void updateDocument(String key, AgencyTravel agencyTravel) {
        try {

            BaseDocument updatedDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);
            updatedDocument.updateAttribute("location", agencyTravel.getLocation());
            updatedDocument.updateAttribute("name", agencyTravel.getName());
            updatedDocument.updateAttribute("numberOfWorkers", agencyTravel.getNumberOfWorkers());
            arangoDB.db(databaseName).collection(collectionName).updateDocument(key, updatedDocument);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    @Override
    public void deleteDocument(String key) {
        try {
            arangoDB.db(databaseName).collection(collectionName).deleteDocument(key);
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete document. " + e.getMessage());
        }
    }

    @Override
    public List<AgencyTravel> getByLocation(String location) {
        List<AgencyTravel> agencyTravels = new LinkedList<>();
        try {
            String query = "FOR t IN agencyTravel FILTER t.location == @location RETURN t";
            Map<String, Object> bindVars = Collections.singletonMap("location", location);
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindVars, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String location2 = aDocument.getAttribute("location").toString();
                String name = (String)aDocument.getAttribute("name");
                int numberOfWorkers =  Integer.parseInt(aDocument.getAttribute("numberOfWorkers").toString());
                AgencyTravel agencyTravel = new AgencyTravel(location2, name, numberOfWorkers);
                agencyTravels.add(agencyTravel);
            });
            System.out.println("Biura podrozy po lokacjach...\t");
            for (AgencyTravel agencyTravel : agencyTravels) {
                System.out.println(agencyTravel);
            }
            return agencyTravels;
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Double findAverageWorkersNumberByLocation(String location) {
        List<AgencyTravel> agencyTravels = new LinkedList<>();
        try {
            String query = "FOR t IN agencyTravel FILTER t.location == @location RETURN t";
            Map<String, Object> bindVars = Collections.singletonMap("location", location);
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindVars, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String location2 = aDocument.getAttribute("location").toString();
                String name = (String)aDocument.getAttribute("name");
                int numberOfWorkers = Integer.parseInt(aDocument.getAttribute("numberOfWorkers").toString());
                AgencyTravel agencyTravel = new AgencyTravel(location2, name, numberOfWorkers);
                agencyTravels.add(agencyTravel);
            });
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }

        AtomicReference<Double> workersNumbersum = new AtomicReference<>(0.0);
        agencyTravels.forEach(agencyTravel -> workersNumbersum.updateAndGet(v -> v + agencyTravel.getNumberOfWorkers()));
        double average = workersNumbersum.get() / agencyTravels.size();
        System.out.println("Srednia liczba pracownikow dla danej lokalizacji[ " + location + " ]" + " wynosi " + average + ".");
        return workersNumbersum.get() / agencyTravels.size();
    }
}
