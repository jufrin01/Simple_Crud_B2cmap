package idb2camp.b2campjufrin.repository;


import idb2camp.b2campjufrin.dto.request.DepartmentLocationDto;
import idb2camp.b2campjufrin.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @EntityGraph(attributePaths = {"account"})
    Optional<Department> findWithAccountNoLocationByDepartmentId(Integer deptId);

    @EntityGraph(attributePaths = {"account", "location", "location.country", "location.country.region"})
    Optional<Department> findWithAccountAndLocationByDepartmentId(Integer deptId);

    @EntityGraph(attributePaths = {"location", "location.country", "location.country.region"})
    Optional<Department> findWithLocationByDepartmentId(Integer deptId);

    Page<Department> findAll(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE department SET department_name = :departmentName\n" +
            " WHERE department_id = :departmentId", nativeQuery = true)
    int updateDepartmentName(@Param("departmentId") Integer departmentId, @Param("departmentName") String departmentName);

    boolean existsByDepartmentName(String departmentName);

    @Query(value = "SELECT new idb2camp.b2campjufrin.dto.request.DepartmentLocationDto(d.departmentId,\n" +
            "d.departmentType, \n" +
            "d.departmentName, \n" +
            "l.locationId, \n" +
            "l.city, \n" +
            "l.postalCode, \n" +
            "l.stateProvince, \n" +
            "l.streetAddress)\n" +
            " FROM Department d \n" +
            " LEFT JOIN d.location l")
    Page<DepartmentLocationDto> findAllDepartmentsWithLocations(Pageable pageable);
}
