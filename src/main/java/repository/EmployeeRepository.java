package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select e.* \n" +
        "from employee e\n" +
        "where e.email = :email", nativeQuery = true)
    Optional<Employee> findByEmail(String email);
}

