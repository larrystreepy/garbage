INSERT INTO hibernate_unique_key(
            next_hi)
    VALUES (2);
    
    INSERT INTO userinformation(
            id, user_name, password, email_id, security_question, security_answer, 
            role_id, createdon, createdby, updatedon, updatedby, status)
    VALUES (1, 'admin@bluehubhealth.com', '0cb76ad8ba9ac843d795ee1bf0299c39', 'admin@bluehubhealth.com', '', '', 
            '', '2014-06-18 10:39:43.014', 1, '2014-06-18 10:39:43.014', 1, 1);
            
 insert into userroles (userid,roleid) values (1,'Admin'); 
 
 
INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('Admin', 'Admin', 'Admin');
    
    INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('Patient', 'Patient' ,'Patient');
    
    INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('Physician', 'Physician' ,'Physician');
      INSERT INTO roles(
            roleid, rolename, groupid)
    VALUES ('PracticeAdmin', 'PracticeAdmin' ,'PracticeAdmin');