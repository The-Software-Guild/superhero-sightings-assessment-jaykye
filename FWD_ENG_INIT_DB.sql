-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema superheroSightingDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `superheroSightingDB` ;

-- -----------------------------------------------------
-- Schema superheroSightingDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superheroSightingDB` DEFAULT CHARACTER SET utf8 ;
USE `superheroSightingDB` ;

-- -----------------------------------------------------
-- Table `superheroSightingDB`.`superpower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingDB`.`superpower` ;

CREATE TABLE IF NOT EXISTS `superheroSightingDB`.`superpower` (
  `superpowerId` INT NOT NULL AUTO_INCREMENT,
  `superpowerName` VARCHAR(45) NULL,
  PRIMARY KEY (`superpowerId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingDB`.`hero`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingDB`.`hero` ;

CREATE TABLE IF NOT EXISTS `superheroSightingDB`.`hero` (
  `heroId` INT NOT NULL AUTO_INCREMENT,
  `heroName` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `superpowerId` INT NOT NULL,
  PRIMARY KEY (`heroId`),
  INDEX `fk_hero_superpower_idx` (`superpowerId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_superpower`
    FOREIGN KEY (`superpowerId`)
    REFERENCES `superheroSightingDB`.`superpower` (`superpowerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingDB`.`organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingDB`.`organization` ;

CREATE TABLE IF NOT EXISTS `superheroSightingDB`.`organization` (
  `organizationId` INT NOT NULL AUTO_INCREMENT,
  `organizationName` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `organizationAddress` VARCHAR(45) NULL,
  PRIMARY KEY (`organizationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingDB`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingDB`.`location` ;

CREATE TABLE IF NOT EXISTS `superheroSightingDB`.`location` (
  `locationId` INT NOT NULL AUTO_INCREMENT,
  `locationName` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(45) NULL,
  `latitude` DOUBLE NULL,
  `longitude` DOUBLE NULL,
  PRIMARY KEY (`locationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingDB`.`sighting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingDB`.`sighting` ;

CREATE TABLE IF NOT EXISTS `superheroSightingDB`.`sighting` (
  `sightingId` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL,
  `locationId` INT NOT NULL,
  `heroId` INT NOT NULL,
  PRIMARY KEY (`sightingId`),
  INDEX `fk_sighting_location1_idx` (`locationId` ASC) VISIBLE,
  INDEX `fk_sighting_hero1_idx` (`heroId` ASC) VISIBLE,
  CONSTRAINT `fk_sighting_location1`
    FOREIGN KEY (`locationId`)
    REFERENCES `superheroSightingDB`.`location` (`locationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sighting_hero1`
    FOREIGN KEY (`heroId`)
    REFERENCES `superheroSightingDB`.`hero` (`heroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingDB`.`hero_organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingDB`.`hero_organization` ;

CREATE TABLE IF NOT EXISTS `superheroSightingDB`.`hero_organization` (
  `heroId` INT NOT NULL,
  `organizationId` INT NOT NULL,
  PRIMARY KEY (`heroId`, `organizationId`),
  INDEX `fk_hero_has_organization_organization1_idx` (`organizationId` ASC) VISIBLE,
  INDEX `fk_hero_has_organization_hero1_idx` (`heroId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_organization_hero1`
    FOREIGN KEY (`heroId`)
    REFERENCES `superheroSightingDB`.`hero` (`heroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_organization_organization1`
    FOREIGN KEY (`organizationId`)
    REFERENCES `superheroSightingDB`.`organization` (`organizationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
