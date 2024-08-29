package idb2camp.b2campjufrin.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "m_role")
@Where(clause = "is_deleted = false")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MRole extends BaseEntity {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "role_name", length = 50, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees;


}
