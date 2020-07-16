DROP TABLE IF EXISTS ceusers;

CREATE TABLE ceusers (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(128) NOT NULL,
    lastName VARCHAR(128) NOT NULL,
    address VARCHAR(128) NOT NULL,
    occupation VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO ceusers (id, firstName, lastName, address, occupation) VALUES
  (100, 'Harry', 'Wit, de', 'Straat 2', 'TAE'),
  (101, 'Jos', 'Lelijk', 'Weg 400', 'TAE');