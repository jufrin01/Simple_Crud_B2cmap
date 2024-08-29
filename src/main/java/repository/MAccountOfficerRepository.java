package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.MAccountOfficer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MAccountOfficerRepository extends CrudRepository<MAccountOfficer , String> {

    Optional<MAccountOfficer>findByAccountOfficerIdAndIsDeleteIsFalse(String id);

    List<MAccountOfficer> findAll();
}
