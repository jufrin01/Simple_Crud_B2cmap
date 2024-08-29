package idb2camp.b2campjufrin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity(name = "r_status")
@ToString
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RStatus {
    @Id
    @Column(name = "status_id" , nullable = false , length = 2)
    private String statusId;
    @Column(name = "status_code" , nullable = true , length = 4)
    private String statusCode;
    @Column(name = "status_name" , nullable = true , length = 50)
    private String statusName;
}
