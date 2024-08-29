package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.request.DepartmentDto;
import idb2camp.b2campjufrin.dto.request.DeptlistDto;
import idb2camp.b2campjufrin.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/department")
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<DeptlistDto> getAllDepartments(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        DeptlistDto deptlistDto = departmentService.getAllDepartments(pageable);
        return new ResponseEntity<>(deptlistDto, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody @Valid DepartmentDto newDepartmentDto) {
        return departmentService.createDepartment(newDepartmentDto);
    }

    @PutMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto updateDepartment(@PathVariable Integer departmentId,
                                          @RequestBody DepartmentDto updatedDepartmentDto) {
        return departmentService.updateDepartment(departmentId, updatedDepartmentDto);
    }

    @GetMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto getDepartmentById(@PathVariable Integer departmentId,
                                           @RequestParam(defaultValue = "true") boolean includeEmployees,
                                           @RequestParam(defaultValue = "true") boolean includeLocation) {
        return departmentService.getDepartmentById(departmentId, includeEmployees, includeLocation);
    }

    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}

//    {createdd dpt
//    "departmentName": "PT. Batman ",
//    "departmentType" : "AKUNTASI",
//    "locationId" : 1
//    }
