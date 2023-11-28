CREATE TABLE lecture (
  id IDENTITY,
  name VARCHAR,
  message VARCHAR,
  password VARCHAR NOT NULL DEFAULT '合言葉'
);

CREATE TABLE eachLecture(
  id IDENTITY,
  number INT, --授業回
  lecture_id INT,
  start_date DATE,
  FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

CREATE TABLE attend (
    id IDENTITY,
    lecture_id INT,
    name VARCHAR unique,
    FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

CREATE TABLE question(
  id IDENTITY,
  lecture_id INT,
  question VARCHAR,
  answer VARCHAR,
  FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

CREATE TABLE reply(
  id IDENTITY,
  name VARCHAR,
  question_id INT,
  lecture_id INT,
  reply VARCHAR,
  FOREIGN KEY (question_id) REFERENCES question(id),
  FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);
