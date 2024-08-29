package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Override
    Optional<Country> findById(Integer integer);

//    @Query("SELECT c FROM Country c JOIN FETCH c.location")
//    List<Country> findAllWithLocations();
}
