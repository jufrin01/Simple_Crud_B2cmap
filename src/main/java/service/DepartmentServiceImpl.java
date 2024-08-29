package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.request.AccountDto;
import idb2camp.b2campjufrin.dto.request.DepartmentDto;
import idb2camp.b2campjufrin.dto.request.DeptlistDto;
import idb2camp.b2campjufrin.dto.request.LocationDto;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.model.Account;
import idb2camp.b2campjufrin.model.Department;
import idb2camp.b2campjufrin.model.Location;
import idb2camp.b2campjufrin.repository.Accountdatarepository;
import idb2camp.b2campjufrin.repository.DepartmentRepository;
import idb2camp.b2campjufrin.repository.LocationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private ModelMapper modelMapper;
    Accountdatarepository accountrepo;
    private DepartmentRepository deptRepo;
    private LocationRepository locationRepo;

    public DeptlistDto getAllDepartments(Pageable pageable) {
        Page<Department> departmentPage = deptRepo.findAll(pageable);

        List<DepartmentDto> departmentDtoList = departmentPage.getContent().stream()
                .map(department -> {
                    DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);

                    Integer departmentId = department.getDepartmentId();
                    List<AccountDto> accounts = accountrepo.findByDepartmentDepartmentId(departmentId).stream()
                            .map(account -> modelMapper.map(account, AccountDto.class))
                            .collect(Collectors.toList());

                    Location location = department.getLocation();
                    LocationDto locationDto = (location != null) ? modelMapper.map(location, LocationDto.class) : null;
                    departmentDto.setAccount(accounts);
                    departmentDto.setLocation(locationDto);
                    return departmentDto;
                })
                .collect(Collectors.toList());

        return DeptlistDto.builder()
                .deptlistDto(departmentDtoList)
                .currentPage(departmentPage.getNumber())
                .totalItems(departmentPage.getTotalElements())
                .totalPages(departmentPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartment(Integer departmentId, DepartmentDto updatedDepartmentDTO) {
        Optional<Department> departmentOptional = deptRepo.findById(departmentId);

        if (departmentOptional.isPresent()) {
            Department existingDepartment = departmentOptional.get();
            existingDepartment.setDepartmentName(updatedDepartmentDTO.getDepartmentName());

            if (updatedDepartmentDTO.getLocation() != null) {
                LocationDto updatedLocationDto = updatedDepartmentDTO.getLocation();
                if (existingDepartment.getLocation() == null) {
                    existingDepartment.setLocation(new Location());
                }
                existingDepartment.getLocation().setLocationId(updatedLocationDto.getLocationId());
            }
            deptRepo.save(existingDepartment);
            DepartmentDto updatedDepartmentDto = modelMapper.map(existingDepartment, DepartmentDto.class);
            updatedDepartmentDto.setAccount(getAccountsForDepartment(departmentId));
            updatedDepartmentDto.setLocation(getLocationForDepartment(departmentId));

            return updatedDepartmentDto;
        } else {
            throw new BusinessException(GlobalMessage.DATA_NOT_FOUND);
        }
    }

    private List<AccountDto> getAccountsForDepartment(Integer departmentId) {
        return accountrepo.findByDepartmentDepartmentId(departmentId).stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    private LocationDto getLocationForDepartment(Integer departmentId) {
        Optional<Department> departmentOptional = deptRepo.findById(departmentId);
        return departmentOptional.map(department -> {
            Location location = department.getLocation();
            return (location != null) ? modelMapper.map(location, LocationDto.class) : null;
        }).orElse(null);
    }

    @Override
    public CustomResponse<Void> deleteDepartment(Integer departmentId) {
        CustomResponse<Void> response = new CustomResponse<>();
        try {
            Optional<Department> departmentOptional = deptRepo.findById(departmentId);
            if (departmentOptional.isPresent()) {
                Department existingDepartment = departmentOptional.get();
                deptRepo.delete(existingDepartment);
                response.setMessage("Department deleted successfully");
            } else {
                response.setMessage("Department not found");
            }
        } catch (Exception e) {
            response.setMessage("Internal Server Error");
        }
        return response;
    }

    @Override
    public DepartmentDto getDepartment(Integer departmentId, boolean includeEmployees, boolean includeLocation) {
        if (includeEmployees && !includeLocation) {
            return deptRepo.findWithAccountNoLocationByDepartmentId(departmentId)
                    .map(dept -> modelMapper.map(dept, DepartmentDto.class))
                    .orElseThrow(resourceNotFound(departmentId));
        } else if (includeLocation) {
            Optional<Department> deptEntity;
            if (includeEmployees) {
                deptEntity = deptRepo.findWithAccountAndLocationByDepartmentId(departmentId);
            } else {
                deptEntity = deptRepo.findWithLocationByDepartmentId(departmentId);
            }
            return deptEntity
                    .map(dept -> DepartmentDto.builder()
                            .departmentName(dept.getDepartmentName())
                            .account(dept.getAccount().stream()
                                    .map(emp -> modelMapper.map(emp, AccountDto.class))
                                    .collect(Collectors.toList()))
                            .location(modelMapper.map(dept.getLocation(), LocationDto.class))
                            .build())
                    .orElseThrow(resourceNotFound(departmentId));

        } else {
            return deptRepo.findById(departmentId)
                    .map(dept -> modelMapper.map(dept, DepartmentDto.class))
                    .orElseThrow(resourceNotFound(departmentId));
        }
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto newDepartmentDto) {
        validateDepartmentDto(newDepartmentDto);

        if (deptRepo.existsByDepartmentName(newDepartmentDto.getDepartmentName())) {
            throw new BusinessException(GlobalMessage.DATA_ALREADY_EXIST);
        }
        Department newDepartmentEntity = modelMapper.map(newDepartmentDto, Department.class);
        newDepartmentEntity.setDepartmentName(newDepartmentDto.getDepartmentName());
        newDepartmentEntity.setDepartmentType(newDepartmentDto.getDepartmentType());
        Set<Account> accounts = createAccountsForDepartment(newDepartmentDto.getAccount());
        newDepartmentEntity.setAccount(accounts);
        Location location = locationRepo.findById(newDepartmentDto.getLocationId())
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
        newDepartmentEntity.setLocation(location);

        Department savedDepartment = deptRepo.save(newDepartmentEntity);
        DepartmentDto createdDepartment = modelMapper.map(savedDepartment, DepartmentDto.class);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED).getBody();
    }

    @Override
    public DepartmentDto getDepartmentById(Integer departmentId, boolean includeEmployees, boolean includeLocation) {
        Department department = deptRepo.findById(departmentId)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DEPARTMENT_NOT_FOUND));
        DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
        if (includeEmployees) {
            List<AccountDto> accounts = accountrepo.findByDepartmentDepartmentId(departmentId).stream()
                    .map(account -> modelMapper.map(account, AccountDto.class))
                    .collect(Collectors.toList());
            departmentDto.setAccount(accounts);
        }
        if (includeLocation) {
            Location location = department.getLocation();
            LocationDto locationDto = (location != null) ? modelMapper.map(location, LocationDto.class) : null;
            departmentDto.setLocation(locationDto);
        }
        return departmentDto;
    }

    private Set<Account> createAccountsForDepartment(List<AccountDto> accountDtos) {
        if (accountDtos != null && !accountDtos.isEmpty()) {
            return accountDtos.stream()
                    .map(accountDto -> {
                        Account accountEntity = modelMapper.map(accountDto, Account.class);
                        accountEntity.setFirstName(accountEntity.getFirstName());
                        accountEntity.setLastName(accountEntity.getLastName());
                        accountEntity.setEmail(accountEntity.getEmail());
                        return accountrepo.save(accountEntity);
                    })
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    private void validateDepartmentDto(DepartmentDto departmentDto) {
        if (departmentDto.getDepartmentName() == null || departmentDto.getDepartmentName().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty");
        }
        if (departmentDto.getDepartmentType() == null || departmentDto.getDepartmentType().toString().isEmpty()) {
            throw new IllegalArgumentException("type name is null");
        }
    }

    private Supplier<IllegalArgumentException> resourceNotFound(Integer departmentId) {
        return () ->
                new IllegalArgumentException(String.format("The DepartmentId: %d is not found!", departmentId));
    }
}
