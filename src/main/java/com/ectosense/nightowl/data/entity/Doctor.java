package com.ectosense.nightowl.data.entity;

import com.ectosense.nightowl.utils.CustomClinicListDeSerializer;
import com.ectosense.nightowl.utils.CustomClinicListSerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_doctor", schema = "public")
@Slf4j
public class Doctor
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

    @Column(name = "speciality")
    private String speciality;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "doctorList")
    @JsonSerialize(using = CustomClinicListSerializer.class)
    @JsonDeserialize(using = CustomClinicListDeSerializer.class)
    Set<Clinic> clinicList = new HashSet<>();

    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    @ApiModelProperty(hidden = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Doctor.class))
            return false;
        Doctor clinic = (Doctor) obj;
        return id.equals(clinic.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
