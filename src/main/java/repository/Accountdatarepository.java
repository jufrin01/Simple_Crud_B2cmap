package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface Accountdatarepository extends JpaRepository<Account, Integer> {
    @EntityGraph(attributePaths = "department")
    @Query("SELECT a FROM Account a WHERE a.department.departmentId\n = :departmentId")
    Optional<Account> findByDepartmentDepartmentId(Integer departmentId);
    @Override
    Page<Account> findAll(Pageable pageable);
    @Query(value = "SELECT * FROM account\n", nativeQuery = true)
    Page<Account> findAllAccountsNativeQuery(Pageable pageable);
    @Query("SELECT a FROM Account a WHERE a.department.id = :departmentId\n")
    Page<Account> findByDepartmentIdPaged(@Param("departmentId") Integer departmentId, Pageable pageable);
    @Query(value = "SELECT * FROM account WHERE department_id = :departmentId", nativeQuery = true)
    Optional<Account> findByDepartmentId(@Param("departmentId") Integer departmentId);

    List<Account>findAll();
}
