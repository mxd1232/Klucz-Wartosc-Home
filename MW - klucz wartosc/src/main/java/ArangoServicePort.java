import models.AgencyTravel;

import java.util.List;

public interface ArangoServicePort<T, K> {

    void addDocument(K key, T document);

    AgencyTravel getByKey(String key);

    void updateDocument(K key, T document);

    void deleteDocument(K key);

    List<AgencyTravel> getByLocation(String location);

    Double findAverageWorkersNumberByLocation(String location);
}
