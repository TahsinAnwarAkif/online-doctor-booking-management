CREATE TABLE `patient` (
  `id`               INT          NOT NULL,
  `ssn`              VARCHAR(255) NOT NULL UNIQUE,
  `problem_overview` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `patient_id_fk`
  FOREIGN KEY (`id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);