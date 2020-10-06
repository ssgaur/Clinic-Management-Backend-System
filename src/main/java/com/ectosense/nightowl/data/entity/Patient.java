package com.ectosense.nightowl.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_patient", schema = "public")
@Slf4j
public class Patient
{
    @ApiModelProperty(hidden = true)
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    @Type(type="pg-uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User accountDetails;

    @Column(name = "age")
    private int age;

    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    @ApiModelProperty(hidden = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Patient.class))
            return false;
        Patient patient = (Patient)obj;
        return id.equals(patient.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
