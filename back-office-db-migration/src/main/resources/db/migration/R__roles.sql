CREATE TABLE IF NOT EXISTS `roles` (
  `code` VARCHAR(50) NOT NULL ,
  `name` VARCHAR(50) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_time` DATETIME NOT NULL,
  `updated_by` VARCHAR(50) NOT NULL,
  `updated_time` DATETIME NOT NULL,
  CONSTRAINT PK_roles_code PRIMARY KEY (`code` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `user_role_mappings`(
  `id` VARCHAR(50) NOT NULL,
  `user_id` VARCHAR(50) NOT NULL,
  `role_code` VARCHAR(20) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;