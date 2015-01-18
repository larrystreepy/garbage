INSERT INTO hibernate_unique_key(
            next_hi)
    VALUES (2);
    
    INSERT INTO userinformation(
            id, user_name, password, email_id, security_question, security_answer, 
            role_id, createdon, createdby, updatedon, updatedby, status)
    VALUES (1, 'admin@gmail.com', '21232f297a57a5a743894a0e4a801fc3', 'admin@gmail.com', '', '', 
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
    