-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema superheroSightingTestDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `superheroSightingTestDB` ;

-- -----------------------------------------------------
-- Schema superheroSightingTestDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superheroSightingTestDB` DEFAULT CHARACTER SET utf8 ;
USE `superheroSightingTestDB` ;

-- -----------------------------------------------------
-- Table `superheroSightingTestDB`.`superpower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingTestDB`.`superpower` ;

CREATE TABLE IF NOT EXISTS `superheroSightingTestDB`.`superpower` (
  `superpowerId` INT NOT NULL AUTO_INCREMENT,
  `superpowerName` VARCHAR(45) NULL,
  PRIMARY KEY (`superpowerId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingTestDB`.`hero`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingTestDB`.`hero` ;

CREATE TABLE IF NOT EXISTS `superheroSightingTestDB`.`hero` (
  `heroId` INT NOT NULL AUTO_INCREMENT,
  `heroName` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `superpowerId` INT NOT NULL,
  PRIMARY KEY (`heroId`),
  INDEX `fk_hero_superpower_idx` (`superpowerId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_superpower`
    FOREIGN KEY (`superpowerId`)
    REFERENCES `superheroSightingTestDB`.`superpower` (`superpowerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingTestDB`.`organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingTestDB`.`organization` ;

CREATE TABLE IF NOT EXISTS `superheroSightingTestDB`.`organization` (
  `organizationId` INT NOT NULL AUTO_INCREMENT,
  `organizationName` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `organizationAddress` VARCHAR(45) NULL,
  PRIMARY KEY (`organizationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingTestDB`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingTestDB`.`location` ;

CREATE TABLE IF NOT EXISTS `superheroSightingTestDB`.`location` (
  `locationId` INT NOT NULL AUTO_INCREMENT,
  `locationName` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(45) NULL,
  `latitude` DOUBLE NULL,
  `longitude` DOUBLE NULL,
  PRIMARY KEY (`locationId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingTestDB`.`sighting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingTestDB`.`sighting` ;

CREATE TABLE IF NOT EXISTS `superheroSightingTestDB`.`sighting` (
  `sightingId` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL,
  `locationId` INT NOT NULL,
  `heroId` INT NOT NULL,
  PRIMARY KEY (`sightingId`),
  INDEX `fk_sighting_location1_idx` (`locationId` ASC) VISIBLE,
  INDEX `fk_sighting_hero1_idx` (`heroId` ASC) VISIBLE,
  CONSTRAINT `fk_sighting_location1`
    FOREIGN KEY (`locationId`)
    REFERENCES `superheroSightingTestDB`.`location` (`locationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sighting_hero1`
    FOREIGN KEY (`heroId`)
    REFERENCES `superheroSightingTestDB`.`hero` (`heroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroSightingTestDB`.`hero_organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroSightingTestDB`.`hero_organization` ;

CREATE TABLE IF NOT EXISTS `superheroSightingTestDB`.`hero_organization` (
  `heroId` INT NOT NULL,
  `organizationId` INT NOT NULL,
  PRIMARY KEY (`heroId`, `organizationId`),
  INDEX `fk_hero_has_organization_organization1_idx` (`organizationId` ASC) VISIBLE,
  INDEX `fk_hero_has_organization_hero1_idx` (`heroId` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_organization_hero1`
    FOREIGN KEY (`heroId`)
    REFERENCES `superheroSightingTestDB`.`hero` (`heroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_organization_organization1`
    FOREIGN KEY (`organizationId`)
    REFERENCES `superheroSightingTestDB`.`organization` (`organizationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
