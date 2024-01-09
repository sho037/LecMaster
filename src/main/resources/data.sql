INSERT INTO lecture (name) VALUES ('国語');
INSERT INTO lecture (name) VALUES ('数学');
INSERT INTO eachLecture (lecture_id,number,start_date,start_time,end_time) VALUES (1,1,'2024-01-09','13:30:00','15:10:00');
INSERT INTO eachLecture (lecture_id,number,start_date,start_time,end_time) VALUES (2,1,'2024-01-09','15:20:00','17:00:00');
INSERT INTO attend (each_lecture_id,name) VALUES (1,'田中 太郎');
INSERT INTO attend (each_lecture_id,name) VALUES (1,'山田 花子');
INSERT INTO attend (each_lecture_id,name) VALUES (2,'佐藤 次郎');
