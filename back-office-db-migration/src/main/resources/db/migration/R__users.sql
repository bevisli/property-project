CREATE TABLE IF NOT EXISTS `users`(
  `id` VARCHAR(50) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_time` DATETIME NOT NULL,
  `updated_by` VARCHAR(50) NOT NULL,
  `updated_time` DATETIME NOT NULL,
  PRIMARY KEY (`id` ASC),
  CONSTRAINT UQ_users UNIQUE (phone_number)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT IGNORE INTO users (id, phone_number, `password`,`name`,`status`, created_by, created_time, updated_by, updated_time)
VALUES('admin', '13333333333', '96e79218965eb72c92a549dd5a330112', 'admin','ACTIVE', 'init', now(), 'init', now())