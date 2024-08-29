package idb2camp.b2campjufrin.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "m_account_officer")
@SuperBuilder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MAccountOfficer  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name= "account_officer_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String accountOfficerId;

    @Column(name = "account_officer_code" , length = 6 , unique = true)
    private  String accountOfficerCode;

    @Column(name = "account_officer_name" , length = 100)
    private String accountOfficerName;

    @Column(name = "account_officer_telephone_number" ,length = 20)
    private String accountOfficerTelephoneNumber;

    @Column(name = "authorized_by")
    private String authorzedBy;

    @Column(name = "authorized_date")
    private Timestamp authorizedDate;

    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    @ManyToOne
    @JoinColumn(name = "status_id" , referencedColumnName = "status_id")
    private  RStatus rStatus;

    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "office_id")
    private MOffice mOffice;

    @Column(name = "nik")
    private String nik;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "isDefault")
    @Builder.Default
    private Boolean isDefault = false;
}
