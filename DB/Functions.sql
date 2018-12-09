-- noinspection SpellCheckingInspectionForFile

-- Task 1
DECLARE
  v_employee VARCHAR2(5) := 'KING';
  v_new_salary NUMBER := 9999;
BEGIN
  UPDATE EMP
    SET SAL = v_new_salary
    WHERE ENAME = v_employee;
END;
COMMIT;

SELECT * FROM EMP;

-- Task 2
DECLARE
  v_empno NUMBER := 7654;
  v_ename EMP.ENAME%TYPE;
  v_sal EMP.SAL%TYPE;
  v_hiredate EMP.HIREDATE%TYPE;
BEGIN
  SELECT ENAME, SAL, HIREDATE
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
END;
COMMIT;


-- Task 3
CREATE SEQUENCE dept_deptno
  INCREMENT BY 1
  START WITH 41
  MAXVALUE 666
  NOCACHE
  NOCYCLE;

DECLARE
  v_count NUMBER := 3;
  v_base DEPT.DNAME%TYPE := 'Hell';
  v_current NUMBER := dept_deptno.currval;
BEGIN
  FOR i IN 1..v_count LOOP
    INSERT INTO DEPT(DEPTNO, DNAME, LOC) VALUES (v_current, CONCAT(v_base, v_current), v_base);
    v_current := dept_deptno.nextval;
    end loop;
end;
commit;


-- TAS