
CREATE TABLE hibernate_unique_key (
    next_hi integer NOT NULL
);


INSERT INTO hibernate_unique_key(
            next_hi)
    VALUES (2);


CREATE TABLE organization
(
  id integer,
  organizationname character varying(200),
  address character(500),
  status integer,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT organization_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE organization
  OWNER TO postgres;
  
 
  
  CREATE TABLE practice
(
  id integer,
  practicename character varying(300),  
  status integer,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT practice_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE practice
  OWNER TO postgres;
  
  
  

  
  
  CREATE TABLE userinformation (
    id integer NOT NULL,
    user_name character varying(128) DEFAULT NULL::bpchar,
    password character varying(128) DEFAULT NULL::bpchar,
    email_id character varying(1000) DEFAULT NULL::bpchar,
    security_question character varying(500) DEFAULT NULL::bpchar,
    security_answer character varying(500) DEFAULT NULL::bpchar,
    role_id character varying(500) DEFAULT NULL::bpchar,
    createdon timestamp without time zone,
    createdby integer,
    updatedon timestamp without time zone,
    updatedby integer,
    status integer
);


ALTER TABLE public.userinformation OWNER TO postgres;

ALTER TABLE ONLY userinformation
    ADD CONSTRAINT userinformation_pkey PRIMARY KEY (id);
  
    
    INSERT INTO userinformation(
            id, user_name, password, email_id, security_question, security_answer, 
            role_id, createdon, createdby, updatedon, updatedby, status)
    VALUES (1, 'admin@gmail.com', '21232f297a57a5a743894a0e4a801fc3', 'admin@gmail.com', '', '', 
            '', '2014-06-18 10:39:43.014', 1, '2014-06-18 10:39:43.014', 1, 1);

            
            
            
             CREATE TABLE userroles (
    userid integer,
    roleid character varying(100) NOT NULL
  
);

            
  
    insert into userroles (userid,roleid) values (1,'Admin'); //for admin userroles
  
    
    CREATE TABLE roles (
    roleid character varying(100) NOT NULL,
    rolename character varying(100) NOT NULL,
    groupid character varying(100) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('Admin', 'Admin', 'Admin');
    
    INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('Patient', 'Patient' ,'Patient');
    
    INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('Physician', 'Physician' ,'Physician');
    
  
  CREATE TABLE user_add_info
(
  id integer NOT NULL,
  userid integer,
  firstname character varying(100),
  middlename character varying(100),
  lastname character varying(100),  
  ssn character(500),
  dob date,
  address character varying(500),
  policy_number character varying(20),
  insurance_eff_date date,
  insurance_exp_date date,
  contact_number character varying(50),
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT user_add_info_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_add_info
  OWNER TO postgres;
  
   ALTER TABLE user_add_info ADD CONSTRAINT fk_userinformation FOREIGN KEY(userid)
	REFERENCES userinformation(id);
  
  CREATE TABLE usermapping
(
  id integer NOT NULL,
  userid integer,
  organizationid integer,
  practiceid integer,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT usermapping_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usermapping
  OWNER TO postgres;
  
  ALTER TABLE usermapping ADD CONSTRAINT fk_userinformation FOREIGN KEY(userid)
	REFERENCES userinformation(id);
  
  
  CREATE TABLE visitinformation
(
  id integer NOT NULL,
  patientid integer,
  physicianid integer,
  organizationid integer,
  practiceid integer,
  dateofvisit date,
  reasonforvisit character varying(100),
  tag character varying(100),
  prescription text DEFAULT NULL::character varying,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT visitinformation_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE visitinformation
  OWNER TO postgres;
  
   ALTER TABLE visitinformation ADD CONSTRAINT fk_userinformation FOREIGN KEY(patientid)
	REFERENCES userinformation(id);
  
	ALTER TABLE visitinformation ADD CONSTRAINT fk_userinformation FOREIGN KEY(physicianid)
	REFERENCES userinformation(id);
	
	ALTER TABLE visitinformation ADD CONSTRAINT fk_organization FOREIGN KEY(organizationid)
	REFERENCES organization(id);
	
	ALTER TABLE visitinformation ADD CONSTRAINT fk_practice FOREIGN KEY(practiceid)
	REFERENCES practice(id);
  
  CREATE TABLE patientrequest
(
  id integer NOT NULL,
  patientid integer,
  physicianid integer,
  emailid character varying(100),
  mailstatus integer,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT patientrequest_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE patientrequest
  OWNER TO postgres;
  
  ALTER TABLE patientrequest ADD CONSTRAINT fk_userinformation FOREIGN KEY(patientid)
	REFERENCES userinformation(id);
  
	ALTER TABLE patientrequest ADD CONSTRAINT fk_userinformation FOREIGN KEY(physicianid)
	REFERENCES userinformation(id);
  
CREATE TABLE patientdocument
(
  id integer NOT NULL,
  patientid integer,
  docname character varying(100),
  doctype character varying(50),
  docpath character varying(500),
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT patientdocument_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE patientdocument
  OWNER TO postgres;
  
  ALTER TABLE patientdocument ADD CONSTRAINT fk_userinformation FOREIGN KEY(patientid)
	REFERENCES userinformation(id);
 
	
CREATE TABLE audittrailrecord
(
  id integer NOT NULL,
  actiontype integer,
  entityid integer,
  entityname character varying(255),
  logtext text DEFAULT NULL::character varying,
  ipaddress character varying(32),
   userid integer,
   createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT audittrailrecord_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE audittrailrecord
  OWNER TO postgres;
  
  
  
  CREATE TABLE efax
(
  id integer NOT NULL,
  fromfax character varying(200),
  tofax character varying(200),
  patientid integer,
  sendfaxqueueid character varying(200),
  organizationid integer,
  status integer,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT efax_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE efax
  OWNER TO postgres;
   
	 
	 
 
