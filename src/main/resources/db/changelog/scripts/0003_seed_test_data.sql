
SET search_path TO public;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";




INSERT INTO tbl_user (id, phone_number, first_name, last_name, gender, email, password, user_meta, is_verified, created_at, updated_at)
VALUES
    ('84788169-05fb-4d25-bda3-a0e9bb1b4a87',	'+9999999997',	'Patient1',	'Patient1',	    'Male',	'patient1@gmail.com',	'$2a$10$8/CPsZnAV1SEAXE264ze5OXCebGlUsT.YhjsbYKmC3TwxpuKjpOvq',	'{"userRoles": ["ROLE_PATIENT"], "authProvider": null, "profileImage": null}',	    'f',	'2020-10-06 16:03:10.877',	'2020-10-06 16:03:10.876'),
    ('9c0a5d7c-db1a-4d58-8643-7e581d09ba8e',    '+9999999998',	'Patient2',	'Patient2',	    'Male',	'patient2@gmail.com',	'$2a$10$HpBMuUsog/RoRWdK3hcUve3cLF84SatQbEDTRyxE07b4O3T6QEnMG',	'{"userRoles": ["ROLE_PATIENT"], "authProvider": null, "profileImage": null}',	    'f',	'2020-10-06 16:03:18.209',	'2020-10-06 16:03:18.209'),
    ('e47a0588-28e2-46da-bff7-7fdb9ce03ff5',	'+9999999990',	'SUPER' ,	'ADMIN'	,       'Male',	'superadmin@gmail.com',	'$2a$10$EIr5WZUdtF6oR8PoPQyN/eW38lmGnN9KXt7uEuYm.obi6MgwE8Gka',	'{"userRoles": ["ROLE_SUPER_ADMIN"], "authProvider": null, "profileImage": null}',	'f',	'2020-10-06 16:03:45.019',	'2020-10-06 16:03:45.018'),
    ('7899dcfa-cccf-4422-a70d-7640f83ec844',	'+9999999991',	'Clinic1',	'Clinic1',	    'Male',	'clinic1@gmail.com',	'$2a$10$Mw6cTqxlHVjpCcpJslzskeMMaGUsWRCAtxDp6hv1W0CNOIFKz1phu',	'{"userRoles": ["ROLE_CLINIC"], "authProvider": null, "profileImage": null}',	    'f',	'2020-10-06 15:59:42.896',	'2020-10-06 15:59:42.855'),
    ('35694409-8bc4-4b8f-8420-5c5b3af4ab74',	'+9999999992',	'Clinic2',	'Clinic2',	    'Male',	'clinic2@gmail.com',	'$2a$10$JQeBMPs6AUN/lVCLyS387uW41lRV1efBbPk10cX5fjZWKJ.o3eYNu',	'{"userRoles": ["ROLE_CLINIC"], "authProvider": null, "profileImage": null}',	    'f',	'2020-10-06 15:59:57.507',	'2020-10-06 15:59:57.507'),
    ('c9671853-a5aa-4836-b410-1e4706c83353',	'+9999999993',	'Doctor1',	'Doctor1',	    'Male',	'doctor1@gmail.com',	'$2a$10$lF8tyPfp3bTQ4I9wohpm/.0MDGkddp04lOqW6TgbKGwoAI1A/oaEK',	'{"userRoles": ["ROLE_DOCTOR"], "authProvider": null, "profileImage": null}',	    'f',	'2020-10-06 16:00:33.865',	'2020-10-06 16:00:33.864'),
    ('fed71d82-a8d1-4a9f-a748-e533cef81776',	'+9999999994',	'Doctor2',	'Doctor2',	    'Male',	'doctor2@gmail.com',	'$2a$10$67qAWU8rlIB5zZ7iGLYqxOo35qqwy6xij3GOZ/iZrey3vW/yMBoe6',	'{"userRoles": ["ROLE_DOCTOR"], "authProvider": null, "profileImage": null}',	    'f',	'2020-10-06 16:00:42.708',	'2020-10-06 16:00:42.707'),
    ('95f56c98-9b70-475c-8460-0f5a325146f1',	'+9999999995',	'Assistant1','Assistant1',	'Male',	'assistant1@gmail.com',	'$2a$10$tsjed6eAwYu/OcYSvavJf.LjVk3bwXVjG8oqE/fW/lZ4ew.SCqiIS',	'{"userRoles": ["ROLE_ASSISTANT"], "authProvider": null, "profileImage": null}',	'f',	'2020-10-06 16:01:06.26',	'2020-10-06 16:01:06.259'),
    ('e71dbcf4-507c-49bb-9f29-1e9179018c7a',	'+9999999996',	'Assistant2', 'Assistant2',	'Male',	'assistant2@gmail.com',	'$2a$10$drSb7eF4qU8IgS2uu89avOfqf5nxUfkm.Pz48gDDGMu5wv9qtL7uW',	'{"userRoles": ["ROLE_ASSISTANT"], "authProvider": null, "profileImage": null}',	'f',	'2020-10-06 16:01:16.525',	'2020-10-06 16:01:16.525')
    ;



INSERT INTO tbl_patient  (id, user_id, age, created_at, updated_at)
VALUES
    ('b0d2996b-4eb3-4289-a929-33e6c72f7d50',	'84788169-05fb-4d25-bda3-a0e9bb1b4a87',	31,	'2020-10-06 16:10:31.656',	'2020-10-06 16:10:31.631'),
    ('5143fd10-4c8c-46ad-a8a6-1a0e28544ca6',	'9c0a5d7c-db1a-4d58-8643-7e581d09ba8e',	32,	'2020-10-06 16:10:46.774',	'2020-10-06 16:10:46.753')
    ;


INSERT INTO tbl_doctor (id, user_id, speciality, created_at, updated_at)
VALUES
    ('669c41ee-d2dd-47e5-9fcd-e36424dcbdb5',	'c9671853-a5aa-4836-b410-1e4706c83353',	'Doctor 1',	'2020-10-06 16:09:06.696',	'2020-10-06 16:09:06.663'),
    ('56856a1a-62b2-4e54-970e-16bd0d25d2d4',	'fed71d82-a8d1-4a9f-a748-e533cef81776',	'Doctor 2',	'2020-10-06 16:09:22.416',	'2020-10-06 16:09:22.397')
;


INSERT INTO tbl_clinic (id, user_id, description, created_at, updated_at)
VALUES
    ('a348ea97-2808-4652-b63e-d41a5efc7357',	'7899dcfa-cccf-4422-a70d-7640f83ec844',	'Clinic 1',	'2020-10-06 16:07:32.16',	'2020-10-06 16:07:32.12'),
    ('8577c25b-94dc-40d4-88d4-c2c540f1821e',	'35694409-8bc4-4b8f-8420-5c5b3af4ab74',	'Clinic 2',	'2020-10-06 16:08:04.299',	'2020-10-06 16:08:04.276')
;

INSERT INTO tbl_assistant (id, user_id, assistant_type, clinic_id, created_at, updated_at)
VALUES
    ('1ba5e140-b024-483a-95b0-393162cf9096',	'95f56c98-9b70-475c-8460-0f5a325146f1',	'Assistant 1', NULL,    '2020-10-06 16:09:54.87',	'2020-10-06 16:09:54.844'),
    ('f34eb9f9-316d-4356-8892-c05e606e65d1',	'e71dbcf4-507c-49bb-9f29-1e9179018c7a',	'Assistant 2', NULL,	'2020-10-06 16:10:08.037',	'2020-10-06 16:10:08.016')
;