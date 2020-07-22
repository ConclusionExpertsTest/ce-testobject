DROP TABLE IF EXISTS ceusers;

CREATE TABLE ceusers (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    isActive BOOLEAN NOT NULL,
    firstName VARCHAR(128) NOT NULL,
    lastName VARCHAR(128) NOT NULL,
    address VARCHAR(128) NOT NULL,
    occupation VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO ceusers (id, isActive, firstName, lastName, address, occupation) VALUES
  (100, true, 'Harry', 'Wit, de', 'Straat 2', 'TAE'),
  (101, true, 'Jos', 'Lelijk', 'Weg 400', 'TAE'),
  (102, false, 'Pietje', 'Petersen', 'Steegje 2', 'FAB');