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
import javax.persistence.OneToMany;
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
@Table(name = "tbl_appointment", schema = "public")
@Slf4j
public class Appointment
{
    @ApiModelProperty(hidden = true)
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    @Type(type="pg-uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name="assistant_id")
    private Assistant assistant;

    @OneToOne
    @JoinColumn(name="doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name="clinic_id")
    private Clinic clinic;

    @OneToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime ;

    @OneToMany(mappedBy="appointment")
    private Set<FileInfo> documents = new HashSet<>();

    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    @ApiModelProperty(hidden = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Appointment.class))
            return false;
        Appointment appointment = (Appointment)obj;
        return id.equals(appointment.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
