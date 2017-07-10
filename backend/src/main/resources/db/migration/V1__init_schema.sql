CREATE TABLE IF NOT EXISTS `expenses` (
  `entity_id` SERIAL PRIMARY KEY,
  `amount` DECIMAL(16,2) NOT NULL,
  `date` DATE NOT NULL,
  `reason` VARCHAR(500) NOT NULL
);