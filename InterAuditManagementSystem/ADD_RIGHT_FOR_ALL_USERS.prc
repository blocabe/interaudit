CREATE OR REPLACE FUNCTION addRightToAll(nameRight character varying(255),description character varying(500),code character varying(255),typeRight character varying(255)) AS $$
DECLARE
 v_next_right_id NUMBER;
BEGIN

	 --INSERTION dans la table des droit du nouveau droit
	INSERT INTO interaudit.rights (name, description, code,type) VALUES(nameRight,description,code,typeRight);
	
	--Recuperer le max id da la table rights
	SELECT MAX(id) INTO v_next_right_id from interaudit.rights;
	
	--AJOUTER une entrée par utilisateur
	FOR v_user_id IN SELECT id  from interaudit.employees where active= true LOOP		
	 INSERT INTO interaudit.access_rights (active, fk_employee, fk_right)VALUES(FALSE,v_user_id,v_next_right_id);
	END LOOP;
	
	COMMIT;
	
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
       NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
	   ROLLBACK;
       RAISE;
END addRightToAll;

$$ LANGUAGE plpgsql;





