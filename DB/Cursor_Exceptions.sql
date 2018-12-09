-- noinspection SpellCheckingInspectionForFile

CREATE OR REPLACE PACKAGE manager_functions IS
  FUNCTION f_salary_update (v_empno IN NUMBER, v_sal IN NUMBER) return NUMBER;
  FUNCTION f_raise_salary (v_emono IN NUMBER) return NUMBER;
  PROCEDURE salary_update (v_empno IN NUMBER, v_sal IN NUMBER);
  PROCEDURE raise_salary ;
END manager_functions;


CREATE OR REPLACE PACKAGE BODY manager_functions IS
-- Task 1
FUNCTION f_salary_update (v_empno IN NUMBER, v_sal IN NUMBER)
  RETURN NUMBER
IS
  v_time NUMBER;
BEGIN
  UPDATE EMP
    SET SAL = v_sal
    WHERE EMPNO = v_empno;
  COMMIT;
  v_time := DBMS_UTILITY.GET_TIME;
  RETURN v_time;
  EXCEPTION
    WHEN OTHERS
    THEN DBMS_OUTPUT.PUT_LINE('Error on execute stmt ' || v_empno || ' ' || SQLERRM);
    v_time := DBMS_UTILITY.GET_TIME;
    RETURN v_time;
END;

FUNCTION f_raise_salary (v_empno IN NUMBER)
  RETURN NUMBER
IS
  v_ename    EMP.ENAME%TYPE;
  v_sal      EMP.SAL%TYPE;
  v_hiredate EMP.HIREDATE%TYPE;
  v_time NUMBER;
BEGIN
  SELECT ENAME,
         SAL,
         HIREDATE
         INTO v_ename, v_sal, v_hiredate
  FROM EMP
  WHERE EMPNO = v_empno;
  IF FLOOR(ROUND(MONTHS_BETWEEN(SYSDATE, v_hiredate)) / 12) >= 15 THEN
    UPDATE EMP
      SET SAL = v_sal * 1.15
      WHERE EMPNO = v_empno;
  ELSIF FLOOR(ROUND(MONTHS_BETWEEN(SYSDATE, v_hiredate)) / 12) < 10 THEN
    UPDATE EMP
      SET SAL = v_sal * 1.05
      WHERE EMPNO = v_empno;
  ELSE
    UPDATE EMP
      SET SAL = v_sal * 1.10
      WHERE EMPNO = v_empno;
  end if;
  COMMIT;
  v_time := DBMS_UTILITY.GET_TIME;
  RETURN v_time;

  EXCEPTION
    WHEN OTHERS
      THEN DBMS_OUTPUT.PUT_LINE('Error on execute stmt ' || v_empno || ' ' || SQLERRM);
      v_time := DBMS_UTILITY.GET_TIME;
      RETURN v_time;
END;

-- Task 2
PROCEDURE salary_update (v_empno IN NUMBER, v_sal IN NUMBER)
IS
  v_row EMP%ROWTYPE;
BEGIN
  SELECT *
    INTO v_row
    FROM EMP
    WHERE EMPNO = v_empno;
  IF NOT SQL%FOUND
    THEN RAISE NO_DATA_FOUND;
  END IF;
  v_row.SAL := v_sal;
  UPDATE EMP
    SET EMP.SAL = v_row.SAL
    WHERE EMP.EMPNO = v_row.EMPNO;
  COMMIT;
  EXCEPTION
    WHEN NO_DATA_FOUND
    THEN DBMS_OUTPUT.PUT_LINE('Error on execute stmt ' || v_empno || ' ' || SQLERRM);
end;

-- Task 3
PROCEDURE raise_salary
IS
  v_row EMP%ROWTYPE;
  CURSOR emp_cursor IS
  SELECT * FROM EMP
  FOR UPDATE OF SAL;
BEGIN
  FOR v_row IN emp_cursor
  LOOP
    IF MONTHS_BETWEEN(SYSDATE, v_row.HIREDATE) >= 120
      THEN
      UPDATE EMP
        SET SAL = SAL + 5000
        WHERE CURRENT OF emp_cursor;
    end if;
  end loop;
  COMMIT;
END;
END manager_functions;

BEGIN
  manager_functions.raise_salary();
end;

-- Task 4
CREATE TABLE emp_change(
  CHANGE_NAME VARCHAR2(20),
  CHANGE_DATE DATE,
  CHANGE_SALARY NUMBER
);

CREATE OR REPLACE TRIGGER emp_trigger
  AFTER INSERT OR UPDATE OR DELETE
  ON EMP
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    INSERT INTO emp_change
      VALUES ('INSERT', SYSDATE, :NEW.SAL);
  ELSIF UPDATING THEN
    INSERT INTO emp_change
      VALUES ('UPDATE', SYSDATE, :OLD.SAL);
  ELSIF DELETING THEN
    INSERT INTO emp_change
      VALUES ('DELETE', SYSDATE, :OLD.SAL);
  end if;
end;

BEGIN
  UPDATE EMP
    SET SAL = 100
    WHERE ENAME = 'KING';
  COMMIT;
end;
