CREATE TABLE lecture (
  id IDENTITY,
  name VARCHAR,
  message VARCHAR,
  password VARCHAR NOT NULL DEFAULT '合言葉'
);

CREATE TABLE attend (
    id IDENTITY,
    lecture_id INT,
    name VARCHAR,
    FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

CREATE TABLE question(
  id IDENTITY,
  lecture_id INT,
  question VARCHAR,
  answer VARCHAR,
  FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);
