CREATE TABLE `schedule_generator` (
  `id`        INT  NOT NULL,
  `next_date` DATE NOT NULL,
  `next_time` TIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `doctor_id_in_generator_fk`
  FOREIGN KEY (`id`)
  REFERENCES `doctor` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);