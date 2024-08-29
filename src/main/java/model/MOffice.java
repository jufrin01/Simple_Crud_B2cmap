package idb2camp.b2campjufrin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity(name = "m_office")
@SuperBuilder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MOffice  extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id" , length =  5)
    private String officeId;
    @Column(name = "office_code", nullable = false, length = 5)
    private String officeCode;
    @Column(name = "office_name", nullable = true, length = 40)
    private String officeName;
    @Column(name = "office_address", nullable = true, length = 100)
    private String officeAddress;
    @Column(name = "village_id", nullable = false, length = 50)
    private String villageId;
    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;
    @Column(name = "telephon_number", nullable = true, length = 20)
    private String telephoneNumber;
    @Column(name = "rak_account_number", nullable = true, length = 20)
    private String rakAccountNumber;
    @Column(name = "kpno_account_number", nullable = true, length = 20)
    private String kpnoAccountNumber;
    @Column(name = "customer_sequence", nullable = true, precision = 6)
    private String custumeSequence;
    @Column(name = "area_code", nullable = true, length = 3)
    private String areaCode;
    @Column(name = "office_signon", nullable = true, length = 1)
    private String officeSignon;

    @Column(name = "authorized_by", nullable = true, length = 50)
    private String authorizedBy;

    @Column(name = "authorized_date", nullable = true)
    private Timestamp authorizedDate;

    @Column(name = "office_status", nullable = true, length = 1)
    private String officeStatus;

    @Column(name = "sandi_bank_id", nullable = true, length = 6)
    private String sandiBankId;

    @Column(name = "sandi_office_id", nullable = true, length = 3)
    private String sandiOfficeId;

    @Column(name = "office_head_name", nullable = true, length = 100)
    private String officeHeadName;

    @Column(name = "nil_score", nullable = true, precision = 3)
    private Integer nilScore;

    @Column(name = "office_head_position", nullable = true, length = 30)
    private String officeHeadPosition;

    @Column(name = "admin_sign_name", nullable = true, length = 50)
    private String adminSignName;

    @Column(name = "office_head_address", nullable = true, length = 200)
    private String officeHeadAddress;

    @Column(name = "office_head_city_id", nullable = true, length = 50)
    private String officeHeadCityId;

    @Column(name = "faximile_number", nullable = true, length = 50)
    private String faximileNumber;

    @Column(name = "urut_fas", nullable = true, precision = 6)
    private Integer urutFas;

    @Column(name = "cab_induk", nullable = true, length = 4)
    private String cabInduk;

    @Column(name = "vice_head_office_name", nullable = true, length = 40)
    private String viceHeadOfficeName;

    @Column(name = "vice_head_office_idcard", nullable = true, length = 20)
    private String viceHeadOfficeIdcard;

    @Column(name = "head_office_telephone_number", nullable = true, length = 15)
    private String headOfficeTelephoneNumber;

    @Column(name = "vice_head_office_telephone_number", nullable = true, length = 15)
    private String viceHeadOfficeTelephoneNumber;

    @Column(name = "admin_office_telephone_number", nullable = true, length = 15)
    private String adminOfficeTelephoneNumber;

    @Column(name = "office_sms_number", nullable = true, length = 15)
    private String officeSmsNumber;

    @Column(name = "closing_by", nullable = true, length = 50)
    private String closingBy;

    @Column(name = "closing_date", nullable = true)
    private Timestamp closingDate;

    @Column(name = "office_parent_id", nullable = true, length = 5)
    private String officeParentId;
}
