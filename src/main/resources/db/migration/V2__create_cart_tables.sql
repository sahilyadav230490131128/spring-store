CREATE TABLE `store_api`.`cart` (
  `id` BINARY(16) NOT NULL
  `date_created` DATE NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`id`));

  CREATE TABLE `cart_items` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `cart_id`BINARY(16) NOT NULL,
    `product_id` bigint NOT NULL,
    `quantity` int NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    UNIQUE KEY `cart_items_cart_product` (`cart_id`,`product_id`),
    KEY `cart_items_product_id_fk_idx` (`product_id`),
    CONSTRAINT `cart_items_cart_id_fk` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE,
    CONSTRAINT `cart_items_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
  )
