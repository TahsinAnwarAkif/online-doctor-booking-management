CREATE TABLE `specialty` (
  `id`          INT          NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL UNIQUE,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);