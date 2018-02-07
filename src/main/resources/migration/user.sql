CREATE TABLE `user` (
  `id`        INT          NOT NULL AUTO_INCREMENT,
  `name`      VARCHAR(255) NOT NULL,
  `username`  VARCHAR(255) NOT NULL UNIQUE,
  `password`  VARCHAR(255) NOT NULL,
  `address`   VARCHAR(255) NOT NULL,
  `phone`     VARCHAR(255) NOT NULL UNIQUE,
  `email`     VARCHAR(255) NOT NULL UNIQUE,
  `role_type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `role_type_fk`
  FOREIGN KEY (`role_type`)
  REFERENCES `role` (`type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO `user` (`name`, `username`, `password`, `address`, `phone`, `email`, `role_type`)
VALUES
  ('admin', 'admin', 'sMsmTvRG76QSJE0I1V5vIdCuciLFHGPyriz6Drq6Is4=$gZBXq2g+0ZIXKY5MQaxQk+HN58JBeSMZl67HpdJmkiI=',
   'hatirpool', '01710912970', 'admin@gmail.com', 'SUPER_ADMIN');