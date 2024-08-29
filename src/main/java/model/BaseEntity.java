package idb2camp.b2campjufrin.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseEntity {

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @CreationTimestamp
    protected Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "updated_by", length = 50)
    protected String updatedBy;

    @Builder.Default
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    protected boolean isDeleted = false;
}