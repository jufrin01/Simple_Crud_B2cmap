package idb2camp.b2campjufrin.repository;

import idb2camp.b2campjufrin.model.Salary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query(value = "SELECT \n" +
        "a.account_id, s.salary_id, s.amount, s.salary_type,\n " +
        "a.first_name, a.last_name, a.email " +
        "FROM public.account a " +
        "JOIN public.salary s ON a.account_id = s.account_id " +
        "WHERE a.account_id = :accountId", nativeQuery = true)
    List<Salary> findSalariesByAccountId(@Param("accountId") List<Integer> accountId);

//    @Query("SELECT s.salaryId AS salary_id, a.first_name AS account_name, s.paymentDate, s.amount " +
//        "FROM Salary s " +
//        "JOIN s.account a " +
//        "WHERE LOWER(a.first_name) LIKE LOWER(:accountName)")
//    @Query("SELECT s.salaryId AS salary_id, a.first_name AS account_name, s.paymentDate, s.amount \n" +
//        "FROM Salary s \n" +
//        "JOIN s.account a \n" +
//        "WHERE LOWER(a.first_name) LIKE LOWER(:accountName)")
//    Page<Map<String, Object>> findAllSalaries(
//        @Param("accountName") String accountName,
//        Pageable pageable);
@Query(value = "SELECT s.salary_id, a.first_name AS account_name, s.payment_date, s.amount, s.salary_type \n" +
    "FROM salary s \n" +
    "JOIN account a ON s.account_id = a.account_id \n" +
    "WHERE LOWER(a.first_name) LIKE LOWER(:accountName)", nativeQuery = true)
Page<Map<String, Object>> findAllSalaries(
    @Param("accountName") String accountName,
    Pageable pageable);
    @Query(value = "select s.*\n" +
        "from salary s \n" +
        "where s.salary_id in (:salaryIds)", nativeQuery = true)
    List<Salary> findBySalaryIds(List<Integer> salaryIds);

}