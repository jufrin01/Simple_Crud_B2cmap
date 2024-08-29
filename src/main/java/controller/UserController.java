package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.EmpRegisReq;
import idb2camp.b2campjufrin.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final EmployeeService employeeService;

    @PostMapping("/registration")
    public boolean register(HeaderRequest headers) {
        EmpRegisReq request = EmpRegisReq.builder()
                .email("jufrinaha111@mailinator.com")
                .firstName("jufrin")
                .lastName("Pingungs.")
                .password("12345")
                .build();

        return employeeService.register(headers, request);
    }

//    @PostMapping("/confirmation")
//    public Long confirmation(Headers headers,
//                             @RequestParam String email,
//                             @RequestParam String hash) {
//        return userService.confirm(headers, email, hash);
//    }
}
