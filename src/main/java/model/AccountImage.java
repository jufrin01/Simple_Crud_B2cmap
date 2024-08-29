package idb2camp.b2campjufrin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "account_image")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountImage extends BaseEntity  implements Serializable {

    @Id
    @Column(name = "account_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountImageId;

    @Column(name = "file_path", length = 200)
    private String filePath;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
}
