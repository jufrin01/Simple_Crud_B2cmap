package idb2camp.b2campjufrin.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "employee")
@Where(clause = "is_deleted = false")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "active", columnDefinition = "boolean default false")
    private boolean active = false;

    @Builder.Default
    @Column(name = "is_verified", columnDefinition = "boolean default false")
    private boolean isVerified = false;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "registration_code", length = 50)
    private String registrationCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_role",
        joinColumns = {@JoinColumn(name = "employee_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<MRole> roles;

}

