package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.MRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MRoleRepository extends JpaRepository<MRole, Integer> {

}
