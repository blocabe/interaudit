create or replace PROCEDURE PROCEDURE2 IS

 
 v_id NUMBER := 0;
 CURSOR C_EMP is SELECT id  from EMPLOYEES;
 CURSOR C_RIGHT is SELECT id  from RIGHTS;
 v_user_id C_RIGHT%ROWTYPE;
 v_right_id C_EMP%ROWTYPE;

	BEGIN 
  dbms_output.enable(1000000);
	OPEN C_EMP;
	LOOP
	FETCH C_EMP INTO v_user_id; 
  EXIT WHEN  C_EMP%NOTFOUND;
	   OPEN C_RIGHT;
      LOOP
      v_id  :=  v_id  + 1;
      FETCH C_RIGHT INTO v_right_id; 
      EXIT WHEN  C_RIGHT%NOTFOUND;
        INSERT INTO ACCESS_RIGHTS (ID,ACTIVE, FK_EMPLOYEE, FK_RIGHT) VALUES(v_id,1,v_user_id.id,v_right_id.id);
        dbms_output.put_line( ' v_id  : ' || v_id ||' v_user_id  : ' ||  v_user_id.id || ' v_right_id  : ' || v_right_id.id) ;
      END LOOP;
      
   CLOSE C_RIGHT;
	   
	 END LOOP; 
   CLOSE C_EMP;

	dbms_output.put_line( 'Total rows : ' || v_id) ;
	dbms_output.put_line( 'OK 4 ALL ') ;
	COMMIT;
  
   EXCEPTION
     WHEN NO_DATA_FOUND THEN
       NULL;
     WHEN OTHERS THEN
       -- Consider logging the error and then re-raise
	   dbms_output.put_line( 'FAILED ') ;
	   ROLLBACK;
   RAISE;

END PROCEDURE2;
