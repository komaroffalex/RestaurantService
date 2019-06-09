-- MySQL Script generated by MySQL Workbench
-- Fri Jun  1 15:44:01 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema rservicedb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rservicedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rservicedb` DEFAULT CHARACTER SET utf8 ;
USE `rservicedb` ;

-- -----------------------------------------------------
-- Table `rservicedb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rservicedb`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(100) NOT NULL,
  `password` CHAR(40) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `role` INT NOT NULL COMMENT 'Три роли:\n0 administrator\n1 client \n2 worker',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rservicedb`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rservicedb`.`food` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `food_name` VARCHAR(200) NOT NULL,
  `cost` FLOAT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `rservicedb`.`tables`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rservicedb`.`tables` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `seats` INT UNSIGNED NOT NULL,
  `location` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rservicedb`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rservicedb`.`orders` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `deltime` VARCHAR(200) NOT NULL,
  `food_ids` VARCHAR(200) NOT NULL,
  `cost` FLOAT NOT NULL,
  `address` VARCHAR(200) NULL,
  `status` INT UNSIGNED NOT NULL,
  `worker_id` INT UNSIGNED NOT NULL,
  `client_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Orders_User1_idx` (`worker_id` ASC),
  INDEX `fk_Orders_User2_idx` (`client_id` ASC),
  CONSTRAINT `fk_Orders_User1`
    FOREIGN KEY (`worker_id`)
    REFERENCES `rservicedb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Orders_User2`
    FOREIGN KEY (`client_id`)
    REFERENCES `rservicedb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rservicedb`.`reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rservicedb`.`reservations` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `restime` VARCHAR(200) NOT NULL,
  `persons` INT UNSIGNED NOT NULL,
  `table_id` INT UNSIGNED NOT NULL,
  `status` INT UNSIGNED NOT NULL,
  `client_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Reservations_Table1_idx` (`table_id` ASC),
  INDEX `fk_Reservations_User1_idx` (`client_id` ASC),
  CONSTRAINT `fk_Reservations_Tables1`
    FOREIGN KEY (`table_id`)
    REFERENCES `rservicedb`.`tables` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reservations_User1`
    FOREIGN KEY (`client_id`)
    REFERENCES `rservicedb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `rservicedb`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `rservicedb`;
INSERT INTO `rservicedb`.`user` (`id`, `login`, `password`, `name`, `role`) VALUES (DEFAULT, 'admin', '011c945f30ce2cbafc452f39840f025693339c42', 'Alex Kom', 0);
INSERT INTO `rservicedb`.`user` (`id`, `login`, `password`, `name`, `role`) VALUES (DEFAULT, 'billy', '011c945f30ce2cbafc452f39840f025693339c42', 'Bill Gates', 1);
INSERT INTO `rservicedb`.`user` (`id`, `login`, `password`, `name`, `role`) VALUES (DEFAULT, 'joe', '011c945f30ce2cbafc452f39840f025693339c42', 'Joe Devil', 2);
INSERT INTO `rservicedb`.`user` (`id`, `login`, `password`, `name`, `role`) VALUES (DEFAULT, 'sweet', '011c945f30ce2cbafc452f39840f025693339c42', 'Sweet Dave', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `rservicedb`.`food`
-- -----------------------------------------------------
START TRANSACTION;
USE `rservicedb`;
INSERT INTO `rservicedb`.`food` (`id`, `food_name`, `cost`) VALUES (DEFAULT, 'Borsch', 200.50);
INSERT INTO `rservicedb`.`food` (`id`, `food_name`, `cost`) VALUES (DEFAULT, 'French Fries', 150.00);
INSERT INTO `rservicedb`.`food` (`id`, `food_name`, `cost`) VALUES (DEFAULT, 'Salad Ceasar', 175.00);
INSERT INTO `rservicedb`.`food` (`id`, `food_name`, `cost`) VALUES (DEFAULT, 'Sweet Cake', 55.00);

COMMIT;


-- -----------------------------------------------------
-- Data for table `rservicedb`.`tables`
-- -----------------------------------------------------
START TRANSACTION;
USE `rservicedb`;
INSERT INTO `rservicedb`.`tables` (`id`, `seats`, `location`) VALUES (DEFAULT, 6, 0);
INSERT INTO `rservicedb`.`tables` (`id`, `seats`, `location`) VALUES (DEFAULT, 4, 1);
INSERT INTO `rservicedb`.`tables` (`id`, `seats`, `location`) VALUES (DEFAULT, 4, 2);
INSERT INTO `rservicedb`.`tables` (`id`, `seats`, `location`) VALUES (DEFAULT, 4, 3);
INSERT INTO `rservicedb`.`tables` (`id`, `seats`, `location`) VALUES (DEFAULT, 4, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `rservicedb`.`orders`
-- -----------------------------------------------------
START TRANSACTION;
USE `rservicedb`;
INSERT INTO `rservicedb`.`orders` (`id`, `deltime`, `food_ids`, `cost`, `address`, `status`, `worker_id`, `client_id`) VALUES (DEFAULT, '2018-05-20 15:38:00', '1', 200.50, 'SPB', 0, 3, 2);
INSERT INTO `rservicedb`.`orders` (`id`, `deltime`, `food_ids`, `cost`, `address`, `status`, `worker_id`, `client_id`) VALUES (DEFAULT, '2018-05-21 18:35:00', '2', 150.00, 'SPB', 1, 4, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `rservicedb`.`reservations`
-- -----------------------------------------------------
START TRANSACTION;
USE `rservicedb`;
INSERT INTO `rservicedb`.`reservations` (`id`, `restime`, `persons`, `table_id`, `status`, `client_id`) VALUES (DEFAULT, '2018-05-22 20:30:00', 4, 2, 2, 2);

COMMIT;