  INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('PracticeAdmin', 'PracticeAdmin' ,'PracticeAdmin');
    
  
    CREATE TABLE practiceadmin_userrole
(
  id integer NOT NULL,
  userid integer,
  roleid character(100),
  CONSTRAINT practiceadmin_userrole_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE practiceadmin_userrole
  OWNER TO postgres;
