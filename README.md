# A hobby assignment for Ectosense Role

Steps to Run - 
1. Docker is installed
2. Clone this Repository
3. In Terminal Run Below command
4. ./gradlew clean && ./gradlew build && docker-compose build &&  docker-compose up -d
 
 `System Design Info: High Level`
 
**User Management and Authentication**
 
A generic User entity contains common info like, Email, Password, Name, Profile Pic etc. Which will be utilised by other entities.  
 
I use, Spring Security and JWT based Authentication and Authorization. All endpoints, except Login and Registered are 
secured by JWT based authentication. Each request will contain A JWT Bearer token and will be sent to Server as Header 
on each request. Token Expiry is 1 Day.

**User Role**

There are 6 Roles available and Each User can have multiple Roles at a time - 

    ROLE_SUPER_ADMIN
    ROLE_ADMIN
    ROLE_DOCTOR
    ROLE_CLINIC
    ROLE_ASSISTANT
    ROLE_PATIENT
    
 
**Entities**

There are 6 Entities are created and used for specific purpose. 

    1. Clinic
    2. Doctor
    3. Assistant
    4. Patient
    5. Document
    6. Appointment

Clinic and Doctor has Many to Many relationship. Meaning, One Doctor can belong to multiple Clinics and at the same time, 
Clinic an have multiple Doctors.

Assistant can belong to one clinic at a time, while A Clinic can have multiple Assistants.

Appointment Will contain, Clinic, Doctor, Patient, Assistant information as will some other Meta information for a real
life appointment. 



**Medical Files**    
    
For now Only Patients, can upload documents. He has two options to upload, He can upload and he can only
see the documents. He can also upload documents for a Clinic. And Doctors who belong to that Clinic, Can only
see those documents. 

Patient has capability to revoke access of Clinic from individual document at any time. 

Download of Document is very much restricted, Only Users who are given access by patient, can only
access the document. 

