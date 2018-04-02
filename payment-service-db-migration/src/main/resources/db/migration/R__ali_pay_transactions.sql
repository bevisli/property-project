CREATE TABLE IF NOT EXISTS `ali_pay_transactions`(
  `id` VARCHAR(50) NOT NULL,
  `order_id` VARCHAR(50) NOT NULL,
  `payment_id` VARCHAR(50) NOT NULL,
  `user_id` VARCHAR(50) NOT NULL,
  `trade_no` VARCHAR(100) NULL,
  `total_amount` NUMERIC(11,2) NOT NULL,
  `request_status` VARCHAR(50) NOT NULL,
  `trade_status` VARCHAR(50) NULL,
  `app_id` VARCHAR(50) NULL,
  `buyer_id` VARCHAR(50) NULL,
  `seller_id` VARCHAR(50) NULL,
  `completed_time` DATETIME NULL,
  `created_time` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `updated_time` DATETIME NULL,
  `updated_by` VARCHAR(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `ix_ali_pay_transactions_order_id` (`order_id` ASC),
  INDEX `ix_ali_pay_transactions_user_id` (`user_id` ASC),
  INDEX `ix_ali_pay_transactions_payment_id` (`payment_id` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;