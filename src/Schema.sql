CREATE TABLE Student (
  sid INTEGER CONSTRAINT stuID CHECK (sid > 0),
  sname VARCHAR(100) NOT NULL,
  major VARCHAR(50),
  s_level VARCHAR(10),
  age INTEGER CONSTRAINT stuAge CHECK (age > 0 AND age < 130),
  CONSTRAINT student_level CHECK (s_level IN ('freshman','sophomore','junior','senior','master','phd')),
  CONSTRAINT student_pk PRIMARY KEY (sid)
);

CREATE TABLE Department (
  did INTEGER CONSTRAINT depID CHECK (did > 0),
  dname VARCHAR(100) NOT NULL,
  CONSTRAINT dept_pk PRIMARY KEY (did)
);

CREATE TABLE Faculty (
  fid INTEGER CONSTRAINT facID CHECK (fid > 0),
  fname VARCHAR(100) NOT NULL,
  deptid INTEGER CONSTRAINT facDep_fk REFERENCES Department (did),
  CONSTRAINT faculty_pk PRIMARY KEY (fid)
);

CREATE TABLE Courses (
  cid VARCHAR(10),
  cname VARCHAR(50),
  meets_at VARCHAR(30),
  room VARCHAR(20),
  fid INTEGER CONSTRAINT corFac_fk REFERENCES Faculty (fid),
  limit INTEGER CONSTRAINT corSize CHECK (limit > 0),
  CONSTRAINT courses_pk PRIMARY KEY (cid)
);

CREATE TABLE Enrolled (
  sid INTEGER CONSTRAINT enrlStu_fk REFERENCES Student (sid),
  cid VARCHAR(10) CONSTRAINT enrlCor_fk REFERENCES Courses (cid),
  exam1 INTEGER CHECK (exam1 >= 0 AND exam1 <= 100),
  exam2 INTEGER CHECK (exam2 >= 0 AND exam2 <= 100),
  final INTEGER CHECK (final >= 0 AND final <= 100),
  CONSTRAINT enrolled_pk PRIMARY KEY (sid, cid)
);

CREATE TABLE Staff (
  sid INTEGER CONSTRAINT staID CHECK (sid > 0),
  sname VARCHAR(100) NOT NULL,
  deptid INTEGER CONSTRAINT staDep_fk REFERENCES Department (did),
  CONSTRAINT staff_pk PRIMARY KEY (sid)
);