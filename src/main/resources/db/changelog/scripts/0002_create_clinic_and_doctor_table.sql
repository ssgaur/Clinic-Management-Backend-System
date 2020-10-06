

--liquibase formatted sql
--changeset shailendra.singh:1.2

SET search_path TO public;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tbl_clinic(
    id              UUID  PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id         UUID    NOT NULL,
    description     TEXT,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT NOW(),

    CONSTRAINT clinic_account_info FOREIGN KEY (user_id) REFERENCES tbl_user (id)
);


CREATE TABLE tbl_doctor(
    id              UUID  PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id         UUID    NOT NULL,
    speciality      VARCHAR(255),
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT NOW(),

    CONSTRAINT doctor_account_info FOREIGN KEY (user_id) REFERENCES tbl_user (id)
);

CREATE TABLE tbl_doctor_clinic_mapping(
    clinic_id       UUID    NOT NULL,
    doctor_id       UUID    NOT NULL,
    PRIMARY KEY (clinic_id, doctor_id),
    CONSTRAINT doctor_clinic_mapping_clinic FOREIGN KEY (clinic_id) REFERENCES tbl_clinic(id),
    CONSTRAINT doctor_clinic_mapping_doctor FOREIGN KEY (doctor_id) REFERENCES tbl_doctor(id)
);

CREATE TABLE tbl_assistant(
    id              UUID  PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id         UUID    NOT NULL,
    assistant_type  VARCHAR(255),
    clinic_id       UUID    ,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT NOW(),

    CONSTRAINT assistant_account_info FOREIGN KEY (user_id)   REFERENCES tbl_user   (id),
    CONSTRAINT assistant_clinic_info  FOREIGN KEY (clinic_id) REFERENCES tbl_clinic (id)
);

CREATE TABLE tbl_patient(
    id            UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id       UUID          NOT NULL,
    age           INTEGER,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP     DEFAULT NOW(),
    CONSTRAINT    patient_account_info FOREIGN KEY (user_id) REFERENCES tbl_user (id)
);

CREATE TABLE tbl_appointment(
    id            UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    doctor_id     UUID          NOT NULL,
    clinic_id     UUID          NOT NULL,
    assistant_id  UUID          NOT NULL,
    patient_id    UUID          NOT NULL,
    start_time    TIMESTAMP     NOT NULL,
    end_time      TIMESTAMP     NOT NULL,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP     DEFAULT NOW(),

    CONSTRAINT appointment_doctor_fk     FOREIGN KEY (doctor_id)      REFERENCES tbl_doctor    (id),
    CONSTRAINT appointment_clinic_fk     FOREIGN KEY (clinic_id)      REFERENCES tbl_clinic    (id),
    CONSTRAINT appointment_assistant_fk  FOREIGN KEY (assistant_id)   REFERENCES tbl_assistant (id),
    CONSTRAINT appointment_patient_fk    FOREIGN KEY (patient_id)     REFERENCES tbl_patient   (id)
);


CREATE TABLE tbl_document(
     id               UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
     appointment_id   UUID          NOT NULL,
     file_name        TEXT          NOT NULL,
     file_url         TEXT          NOT NULL,
     CONSTRAINT document_appointment_fk     FOREIGN KEY (appointment_id)      REFERENCES tbl_appointment  (id)
);

--rollback DROP TABLE tbl_document;
--rollback DROP TABLE tbl_appointment;
--rollback DROP TABLE tbl_patient;
--rollback DROP TABLE tbl_assistant;
--rollback DROP TABLE tbl_doctor_clinic_mapping;
--rollback DROP TABLE tbl_doctor;
--rollback DROP TABLE tbl_clinic;