package com.ectosense.nightowl.data.entity;

import com.ectosense.nightowl.utils.CustomAppointmentDeSerializer;
import com.ectosense.nightowl.utils.CustomAppointmentSerializer;
import com.ectosense.nightowl.utils.CustomClinicListDeSerializer;
import com.ectosense.nightowl.utils.CustomClinicListSerializer;
import com.ectosense.nightowl.utils.CustomPatientSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_document", schema = "public")
@Slf4j
public class FileInfo
{
    @ApiModelProperty(hidden = true)
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    @Type(type="pg-uuid")
    private UUID id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tbl_document_clinic_mapping", joinColumns = { @JoinColumn(name = "document_id") },
            inverseJoinColumns = { @JoinColumn(name = "clinic_id") })
    @JsonSerialize(using = CustomClinicListSerializer.class)
    @JsonDeserialize(using = CustomClinicListDeSerializer.class)
    Set<Clinic> clinics = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id" )
    @JsonSerialize(using = CustomPatientSerializer.class)
    private Patient patient;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_url")
    String fileUrl;

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != FileInfo.class))
            return false;
        FileInfo fileInfo = (FileInfo)obj;
        return id.equals(fileInfo.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
