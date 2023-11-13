CREATE TABLE attend (
    id IDENTITY,
    name VARCHAR
);

CREATE TABLE lecture (
  id IDENTITY,
  name VARCHAR,
  message VARCHAR,
  password VARCHAR NOT NULL DEFAULT '合言葉'
);
