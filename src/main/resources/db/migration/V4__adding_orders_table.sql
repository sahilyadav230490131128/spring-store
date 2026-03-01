CREATE TABLE `store_api`.`orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
  `total_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `order_customer_id_fk_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `order_customer_id_fk`
    FOREIGN KEY (`customer_id`)
    REFERENCES `store_api`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `store_api`.`order_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `unit_price` DECIMAL(10,2) NOT NULL,
  `quantity` INT NOT NULL,
  `total_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `order_items_product_id_fk_idx` (`product_id` ASC) VISIBLE,
  INDEX `order_items_order_id_fk_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `order_items_order_id_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `store_api`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `order_items_product_id_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `store_api`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);