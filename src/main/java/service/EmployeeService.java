package idb2camp.b2campjufrin.service;


import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.EmailSenderRequest;
import idb2camp.b2campjufrin.dto.request.EmpRegisReq;
import idb2camp.b2campjufrin.repository.EmployeeRepository;
import idb2camp.b2campjufrin.repository.MRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmailService emailService;
    private final EmployeeRepository employeeRepo;
    private final MRoleRepository roleRepo;

    public boolean register(HeaderRequest headers, EmpRegisReq request) {
        HashMap<String, String> cardMap = new HashMap<>();
        cardMap.put("alamat terkonfirmasi", "http//jufruni=pinnguns.com");
        cardMap.put("Username", request.getFirstName() + " " + request.getLastName());
        EmailSenderRequest emailSenderRequest = EmailSenderRequest.builder()
                .subject("Email Konfirmasi")
                .recipientName(request.getFirstName() + "" + request.getLastName())
                .destinationMail(request.getEmail())
                .message("Testinngg email")
                .cardMap(cardMap)
                .build();
        emailService.sendCommonHtmlMessage(emailSenderRequest);
        return true;
    }
}

//    @Transactional
//    public Integer register(Headers headers, EmpRegisReq request) {
//        Optional<Employee> sameEmployeeByEmail = employeeRepo.findByEmail(request.getEmail());
//        if (sameEmployeeByEmail.isPresent())
//            throw BusinessException.dataWithSamePropertyAlreadyExist("email", request.getEmail());
//
//        MRole role;
//        if (request.isRegisterAsEmployee()) {
//            role = roleRepo.findById(UserRole.EMPLOYEE.id)
//                .orElseThrow(() -> BusinessException.dataRelationNotFound("Role"));
//        } else {
//            role = roleRepo.findById(UserRole.MANAGER.id)
//                .orElseThrow(() -> BusinessException.dataRelationNotFound("Role"));
//        }
//
//        String hashpw = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
//        Employee employee = Employee.builder()
//            .email(request.getEmail())
//            .firstName(request.getFirstName())
//            .lastName(request.getLastName())
//            .password(hashpw)
//            .roles(Set.of(role))
//            .build();
//        Employee savedEmployee = employeeRepo.save(employee);
//
//        return sendEmailConfirmation(request, hashpw, savedEmployee);
//    }
//
//    private Integer sendEmailConfirmation(EmpRegisReq request, String hashpw, Employee savedEmployee) {
//        Map<String, String> cardMap = new HashMap<>();
//        cardMap.put("link", "wwww.crowdfunding.com" + "/api/v1/employee/confirmation?email=" + request.getEmail() + "&hash=" + hashpw);
//
//        String message = "Congratulation, your account has been made, please accept the link confirmation below";
//        emailService.sendCommonHtmlMessage(EmailSenderRequest.builder()
//            .destinationMail(request.getEmail())
//            .subject("Email confirmation")
//            .recipientName(request.getFirstName())
//            .message(message)
//            .cardMap(cardMap)
//            .build());
//        return savedEmployee.getEmployeeId();
//    }
//
//    public Integer confirm(Headers headers, String email, String hash) {
//        Employee employee = employeeRepo.findByEmail(email)
//            .orElseThrow(BusinessException::dataNotFound);
//
//        if (employee.getPassword().equals("{bcrypt}" + hash))
//            throw new BusinessException(GlobalMessage.UNAUTHORIZED);
//        employee.setActive(true);
//        employeeRepo.save(employee);
//        return employee.getEmployeeId();
//    }


