
CREATE TABLE hibernate_unique_key
(
  next_hi integer NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hibernate_unique_key
  OWNER TO postgres;

  
  
  
CREATE TABLE userinformation
(
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
  status integer,
  CONSTRAINT userinformation_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userinformation
  OWNER TO postgres;

  
CREATE TABLE organization
(
  id integer NOT NULL,
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
  
  
CREATE TABLE practice
(
  id integer NOT NULL,
  organizationid integer,
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
  
  
CREATE TABLE roles
(
  roleid character varying(100) NOT NULL,
  rolename character varying(100) NOT NULL,
  groupid character varying(100) NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE roles
  OWNER TO postgres;
  
  
  
CREATE TABLE user_add_info
(
  id integer NOT NULL,
  userid integer,
  firstname character varying(128),
  middlename character(128),
  lastname character(128),
  ssn character varying(500),
  dob date,
  address character varying(500),
  policy_number character varying(20),
  insurance_eff_date date,
  insurance_exp_date date,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  contact_number character varying(50),
  CONSTRAINT user_add_info_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_add_info
  OWNER TO postgres;
  
  
  
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
  
  
CREATE TABLE userroles
(
  userid integer,
  roleid character varying(100) NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userroles
  OWNER TO postgres;
  
  
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
  
  
 CREATE TABLE shareclinicalinfo (
    id integer NOT NULL,
    createdby integer,
    createdon timestamp without time zone,
    fromdate timestamp without time zone,
    requestdate timestamp without time zone,
    requeststatus character varying(100),
    status integer,
    updatedby integer,
    updatedon timestamp without time zone,
    sharefrom integer,
    shareto integer,
    visitid integer
);

ALTER TABLE public.shareclinicalinfo OWNER TO postgres;

ALTER TABLE ONLY shareclinicalinfo
    ADD CONSTRAINT shareclinicalinfo_pkey PRIMARY KEY (id);

ALTER TABLE ONLY shareclinicalinfo
    ADD CONSTRAINT fk7eca3a062c05a0 FOREIGN KEY (visitid) REFERENCES visitinformation(id);

ALTER TABLE ONLY shareclinicalinfo
    ADD CONSTRAINT fk7eca3a0b9c2354c FOREIGN KEY (shareto) REFERENCES userinformation(id);

ALTER TABLE ONLY shareclinicalinfo
    ADD CONSTRAINT fk7eca3a0e10db2bb FOREIGN KEY (sharefrom) REFERENCES userinformation(id);
    
    
    
    
    
    
    CREATE TABLE privatenote
(
  id integer NOT NULL,
  visitid integer,
  patientid integer,
  note character varying(255),
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT privatenote_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE privatenote
  OWNER TO postgres;
  
  
 CREATE TABLE encounter
(
  id integer NOT NULL,
  visitid integer,
  userid integer,
  encounter character varying(500),
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  status integer,
  CONSTRAINT encounter_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE encounter
  OWNER TO postgres;
    
  
  
  
  
  
  
  
  
   CREATE TABLE requestinfobehalfofpatient
(
  id integer NOT NULL,
  requestfrom integer,
   requesttopatient integer,
    requesttophysician integer,
    status integer,
     requestdate timestamp without time zone,
    requeststatus character varying(100),
   createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  CONSTRAINT requestinfobehalfofpatient_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE requestinfobehalfofpatient
  OWNER TO postgres;
  
  
  
    CREATE TABLE practiceadmin_userrole
(
  id integer,
  userid integer,
  roleid character varying(100) NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE practiceadmin_userrole
  OWNER TO postgres;
  
  
  
  
  
  CREATE TABLE patientmapping
(
  id integer NOT NULL,
  userid integer,
  organizationid integer,
  createdby integer,
  createdon timestamp without time zone,
  updatedby integer,
  updatedon timestamp without time zone,
  status integer,
  CONSTRAINT patientmapping_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE patientmapping
  OWNER TO postgres;