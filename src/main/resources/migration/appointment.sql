CREATE TABLE `appointment` (
  `id`         INT          NOT NULL AUTO_INCREMENT,
  `doctor_id`  INT          NOT NULL,
  `patient_id` INT          NOT NULL,
  `date`       DATE         NOT NULL,
  `time`       TIME         NOT NULL,
  `status`     VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `unique_doctor_schedule`
  UNIQUE (doctor_id, date, time),
  CONSTRAINT `unique_patient_schedule`
  UNIQUE (patient_id, date, time),
  CONSTRAINT `doctor_id_in_appointment_fk`
  FOREIGN KEY (`doctor_id`)
  REFERENCES `doctor` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `patient_id_in_appointment_fk`
  FOREIGN KEY (`patient_id`)
  REFERENCES `patient` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);