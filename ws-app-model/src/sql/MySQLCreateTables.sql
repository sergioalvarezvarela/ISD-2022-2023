-- ----------------------------------------------------------------------------
-- Model-------------------------------------------------------------------------------
DROP TABLE Response;
DROP TABLE Events;


-- --------------------------------- Events ------------------------------------
CREATE TABLE Events (eventId BIGINT NOT NULL AUTO_INCREMENT,
                     event_name VARCHAR(255) COLLATE latin1_bin NOT NULL,
                     description VARCHAR(1024) COLLATE latin1_bin NOT NULL,
                     celebrationDate DATETIME NOT NULL,
                     creationDate DATETIME NOT NULL,
                     runtime INT NOT NULL,
                     event_state bit NOT NULL,
                     attendance SMALLINT NOT NULL,
                     not_attendance SMALLINT NOT NULL,
                     CONSTRAINT eventPK PRIMARY KEY(eventId),
                     CONSTRAINT isvalidRuntime CHECK ( runtime >= 0 )) ENGINE = InnoDB;

CREATE TABLE Response (responseId BIGINT NOT NULL AUTO_INCREMENT,
                       eventId BIGINT NOT NULL,
                       workerEmail VARCHAR (255),
                       responseDate DATETIME NOT NULL,
                       attendance bit not null,
                       CONSTRAINT ResponsePK PRIMARY KEY(responseId),
                       CONSTRAINT ResponseEventIdFK FOREIGN KEY(eventId)
                           REFERENCES Events(eventId) ON DELETE CASCADE ) ENGINE = InnoDB;