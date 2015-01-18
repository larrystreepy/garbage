ALTER TABLE user_add_info ALTER COLUMN lastname TYPE character varying(128);

ALTER TABLE user_add_info ALTER COLUMN ssn TYPE character varying(128);

ALTER TABLE usermapping ADD COLUMN status integer;

ALTER TABLE patientdocument ADD COLUMN uploadtype character varying(100);

ALTER TABLE patientdocument ADD COLUMN key character varying(100);

ALTER TABLE patientdocument ADD COLUMN keyid integer;
 

ALTER TABLE organization ADD COLUMN city character varying(25);

ALTER TABLE organization ADD COLUMN state character varying(25);

ALTER TABLE organization ADD COLUMN zipcode character varying(25);



ALTER TABLE practice ADD COLUMN address character varying(100);

ALTER TABLE practice ADD COLUMN city character varying(25);

ALTER TABLE practice ADD COLUMN state character varying(25);

ALTER TABLE practice ADD COLUMN zipcode character varying(25);


ALTER TABLE user_add_info ADD COLUMN degree character varying(100);

ALTER TABLE user_add_info ADD COLUMN email character varying(70);

ALTER TABLE user_add_info ADD COLUMN language character varying(50);

ALTER TABLE user_add_info ADD COLUMN office_phone character varying(40);

ALTER TABLE user_add_info ADD COLUMN fax character varying(40);


ALTER TABLE user_add_info RENAME COLUMN address to street;

ALTER TABLE user_add_info ADD COLUMN city character varying(50);

ALTER TABLE user_add_info ADD COLUMN zip character varying(15);



ALTER TABLE patientdocument ADD COLUMN receiveddate character varying(25);

ALTER TABLE patientdocument ADD COLUMN associateddate character varying(25);

ALTER TABLE organization ADD COLUMN fax character varying(25);

ALTER TABLE privatenote ADD COLUMN method character varying(25);

ALTER TABLE privatenote ADD COLUMN type character varying(25);

ALTER TABLE user_add_info ADD COLUMN signature character varying(10);