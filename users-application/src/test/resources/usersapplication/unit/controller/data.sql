DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    isActive BOOLEAN NOT NULL,
    firstName VARCHAR(128) NOT NULL,
    lastName VARCHAR(128) NOT NULL,
    address VARCHAR(128) NOT NULL,
    occupation VARCHAR(128) NOT NULL,
    workingConditionsId INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO users (id, isActive, firstName, lastName, address, occupation, workingConditionsId) VALUES
  (100, true, 'Harry', 'Wit, de', 'Straat 2', 'TAE', 100)