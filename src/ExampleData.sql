INSERT ALL
  INTO Student VALUES (1,'Kimberley Roach','IST','freshman',18)
  INTO Student VALUES (2,'Duke Vernon','EST','sophomore',19)
  INTO Student VALUES (3,'Elnora Martins','CS','junior',20)
  INTO Student VALUES (4,'Randell Nicholson','MAT','senior',21)
  INTO Student VALUES (5,'Herbie Emmett','MBA','master',23)
  INTO Student VALUES (6,'Noelle Evelyn','PHL','phd',30)
  INTO Student VALUES (7,'Lindsay Alvey','CS','freshman',19)
  INTO Student VALUES (8,'Pacey Womack','KIN','sophomore',20)
  INTO Student VALUES (9,'Melantha Cross','MCMA','junior',21)
  INTO Student VALUES (10,'Miles Bass','ENG','senior',22)
SELECT * FROM dual;

INSERT ALL
  INTO Department VALUES (1,'Psychology')
  INTO Department VALUES (2,'Computer Science')
  INTO Department VALUES (3,'Philosophy')
  INTO Department VALUES (4,'Math')
  INTO Department VALUES (5,'Physics')
  INTO Department VALUES (6,'Cinema and Photography')
  INTO Department VALUES (7,'Information Technology')
  INTO Department VALUES (8,'Bursar')
  INTO Department VALUES (9,'Admissions')
  INTO Department VALUES (10,'Chemistry')
SELECT * FROM dual;

INSERT ALL
  INTO Faculty VALUES (11,'Anna Whinery',1)
  INTO Faculty VALUES (12,'Royale Purcell',2)
  INTO Faculty VALUES (13,'Alysha Watkins',3)
  INTO Faculty VALUES (14,'Meghan Wilmer',4)
  INTO Faculty VALUES (15,'Nina Turner',5)
  INTO Faculty VALUES (16,'Fredrick Mottershead',6)
  INTO Faculty VALUES (17,'Angus Aitken',10)
  INTO Faculty VALUES (18,'Andrew Turnbull',1)
  INTO Faculty VALUES (19,'Lenore Proudfoot',2)
  INTO Faculty VALUES (20,'Beatrice Phillips',3)
SELECT * FROM dual;

INSERT ALL
  INTO Courses VALUES ('CS430','Databases','TTh 2-3:15pm','Faner 1111',11,30)
  INTO Courses VALUES ('IST225','Networking','MWF 9-9:50am','ASA 125',12,35)
  INTO Courses VALUES ('ISAT415','Advanced Networking','TWTh 10-10:50am','ASA 220',13,100)
  INTO Courses VALUES ('CS101','Intro to Computer Science','TTh 1-2:15pm','Lindegren 5',14,50)
  INTO Courses VALUES ('CP101','Intro to Films','MWF 10-10:50am','Parkinson 101',15,200)
  INTO Courses VALUES ('ENG222','Creative Writing','MWF 11-11:50am','Wham 315',16,32)
  INTO Courses VALUES ('CP330','Film as Literary Art','MW 2-4pm','Engineering D128',17,30)
  INTO Courses VALUES ('MAT108','College Algebra','TTh 4-5:15pm','Neckers 229',18,34)
  INTO Courses VALUES ('MAT125','Technical Math','MWF 12-12:50pm','Lawson 185',19,15)
  INTO Courses VALUES ('UC101','University Experience','MWF 1-1:50pm','Morris 601',20,27)
SELECT * FROM dual;

INSERT ALL
  INTO Enrolled VALUES (1,'CS430',90,85,97)
  INTO Enrolled VALUES (2,'IST225',50,65,70)
  INTO Enrolled VALUES (3,'ISAT415',100,100,100)
  INTO Enrolled VALUES (4,'CS101',0,0,0)
  INTO Enrolled VALUES (5,'CP101',23,18,35)
  INTO Enrolled VALUES (6,'ENG222',82,77,75)
  INTO Enrolled VALUES (7,'CP330',50,51,52)
  INTO Enrolled VALUES (8,'MAT108',63,18,23)
  INTO Enrolled VALUES (9,'MAT125',53,19,68)
  INTO Enrolled VALUES (10,'UC101',90,97,95)
SELECT * FROM dual;

INSERT ALL
  INTO Staff VALUES (21,'Hubert Davidson',7)
  INTO Staff VALUES (22,'Iona Overton',7)
  INTO Staff VALUES (23,'Raphael Tifft',7)
  INTO Staff VALUES (24,'Anneka Irvine',8)
  INTO Staff VALUES (25,'Carley Revie',9)
  INTO Staff VALUES (26,'Aric Samson',8)
  INTO Staff VALUES (27,'Charnette Gardyner',7)
  INTO Staff VALUES (28,'Reynold Stacy',8)
  INTO Staff VALUES (29,'Vince Hanley',9)
  INTO Staff VALUES (30,'Cynthia Coel',7)
SELECT * FROM dual;