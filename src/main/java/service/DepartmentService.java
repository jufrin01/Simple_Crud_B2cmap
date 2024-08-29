package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.dto.request.DepartmentDto;
import idb2camp.b2campjufrin.dto.request.DeptlistDto;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    DeptlistDto getAllDepartments(Pageable pageable);

    DepartmentDto updateDepartment(Integer departmentId, DepartmentDto updatedDepartmentDTO);

    CustomResponse<Void> deleteDepartment(Integer departmentId);

    DepartmentDto getDepartment(Integer departmentId, boolean includeEmployees, boolean includeLocation);

    DepartmentDto createDepartment(DepartmentDto newDepartmentDto);

    DepartmentDto getDepartmentById(Integer departmentId, boolean includeEmployees, boolean includeLocation);
}

