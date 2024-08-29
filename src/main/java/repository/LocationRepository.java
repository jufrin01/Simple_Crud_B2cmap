package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}

