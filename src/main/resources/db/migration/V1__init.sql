CREATE TABLE usr (
  id SERIAL PRIMARY KEY,
  login VARCHAR(250) NOT NULL,
  token VARCHAR(250) NOT NULL,
  balance BIGINT NOT NULL,
  UNIQUE(login)
);

CREATE INDEX usr_login_idx ON usr (login);