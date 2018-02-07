CREATE TABLE `doctor` (
  `id`                       INT  NOT NULL,
  `specialty_id`             INT  NOT NULL,
  `joining_date`             DATE NOT NULL,
  `daily_schedule_start`     TIME NOT NULL,
  `daily_schedule_end`       TIME NOT NULL,
  `estimated_visits_per_day` INT  NOT NULL,
  `visiting_amount`          INT  NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `doctor_id_fk`
  FOREIGN KEY (`id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `specialty_id_fk`
  FOREIGN KEY (`specialty_id`)
  REFERENCES `specialty` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
);