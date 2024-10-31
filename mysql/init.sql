-- Adminer 4.8.1 MySQL 9.1.0 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `project_members`;
CREATE TABLE `project_members` (
  `role` enum('CONTRIBUTOR','OBSERVATOR','OWNER') DEFAULT NULL,
  `project_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `FKl0pa19vhdb97iodagnxhhghc2` (`user_id`),
  CONSTRAINT `FKdki1sp2homqsdcvqm9yrix31g` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
  CONSTRAINT `FKl0pa19vhdb97iodagnxhhghc2` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `project_members`;
INSERT INTO `project_members` (`role`, `project_id`, `user_id`) VALUES
('OWNER',	1,	5),
('CONTRIBUTOR',	1,	6),
('OBSERVATOR',	1,	7),
('OWNER',	2,	5),
('OBSERVATOR',	2,	6),
('CONTRIBUTOR',	2,	9),
('CONTRIBUTOR',	3,	5),
('OWNER',	3,	6),
('OBSERVATOR',	4,	5),
('OWNER',	4,	6);

DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `project_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) NOT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `UK3dmh8vd8wetp0dvgcgb059jjn` (`project_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `projects`;
INSERT INTO `projects` (`project_id`, `description`, `project_name`, `start_date`) VALUES
(1,	'le projet créé par le user 1',	'Projet 1',	'2024-10-23 00:00:00.000000'),
(2,	'Autre projet créé par le user 1',	'Projet 2',	NULL),
(3,	'projet du user 2',	'Projet 3',	'2024-10-29 00:00:00.000000'),
(4,	'Date de début à préciser',	'Projet 4',	NULL);

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` enum('HIGH','LOW','MEDIUM') DEFAULT NULL,
  `status` enum('DONE','IN_PROGRESS','TODO') DEFAULT NULL,
  `project_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK59mygkh6yxl8yj6j8ocg1k73a` (`project_id`),
  CONSTRAINT `FK59mygkh6yxl8yj6j8ocg1k73a` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `task`;
INSERT INTO `task` (`id`, `description`, `due_date`, `name`, `priority`, `status`, `project_id`) VALUES
(1,	'Ca se précise',	'2024-11-15',	'Tâche du projet 1',	'HIGH',	'IN_PROGRESS',	1),
(2,	'Tâche facile',	NULL,	'Tâche 2 du projet 1',	'MEDIUM',	'TODO',	1),
(3,	'',	NULL,	'Tâche à définir',	'LOW',	'TODO',	2);

DROP TABLE IF EXISTS `task_members`;
CREATE TABLE `task_members` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKn4gm527h0x2c4mui0s3jybol3` (`task_id`),
  KEY `FK55item1er1fu9ghdq8n37o2v4` (`user_id`),
  CONSTRAINT `FK55item1er1fu9ghdq8n37o2v4` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`user_id`),
  CONSTRAINT `FKiq0x504sri05cb3jy7rg6p3yc` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `task_members`;
INSERT INTO `task_members` (`id`, `task_id`, `user_id`) VALUES
(1,	2,	6),
(2,	1,	5);

DROP TABLE IF EXISTS `tasks_history`;
CREATE TABLE `tasks_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_modified` datetime(6) NOT NULL,
  `modification_type` varchar(20) NOT NULL,
  `modified_by` bigint NOT NULL,
  `new_value` varchar(1000) DEFAULT NULL,
  `old_value` varchar(1000) DEFAULT NULL,
  `task_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjnm758jtr715virbcqwm8mb50` (`task_id`),
  CONSTRAINT `FKjnm758jtr715virbcqwm8mb50` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `tasks_history`;
INSERT INTO `tasks_history` (`id`, `date_modified`, `modification_type`, `modified_by`, `new_value`, `old_value`, `task_id`) VALUES
(1,	'2024-10-31 16:15:28.624000',	'CREATE',	5,	'Task{id=0, name=\'Tâche du projet 1\', description=\'Tâche du projet 1\', dueDate=Thu Nov 14 00:00:00 GMT 2024, priority=HIGH, status=TODO}',	NULL,	1),
(2,	'2024-10-31 16:15:52.629000',	'CREATE',	5,	'Task{id=0, name=\'Tâche 2 du projet 1\', description=\'Tâche facile\', dueDate=null, priority=MEDIUM, status=TODO}',	NULL,	2),
(3,	'2024-10-31 16:15:57.667000',	'ASSIGN',	5,	'Assigned to User2',	'Not assigned',	2),
(4,	'2024-10-31 16:16:42.305000',	'CREATE',	5,	'Task{id=0, name=\'Tâche à définir\', description=\'\', dueDate=null, priority=LOW, status=TODO}',	NULL,	3),
(5,	'2024-10-31 16:16:58.533000',	'UPDATE',	5,	'Task{id=1, name=\'Tâche du projet 1\', description=\'Ca se précise\', dueDate=Thu Nov 14 00:00:00 GMT 2024, priority=HIGH, status=TODO}',	'Task{id=1, name=\'Tâche du projet 1\', description=\'Tâche du projet 1\', dueDate=2024-11-14, priority=HIGH, status=TODO}',	1),
(6,	'2024-10-31 16:17:08.442000',	'UPDATE',	5,	'Task{id=1, name=\'Tâche du projet 1\', description=\'Ca se précise\', dueDate=Thu Nov 14 00:00:00 GMT 2024, priority=HIGH, status=IN_PROGRESS}',	'Task{id=1, name=\'Tâche du projet 1\', description=\'Ca se précise\', dueDate=2024-11-14, priority=HIGH, status=TODO}',	1),
(7,	'2024-10-31 16:18:22.826000',	'UPDATE',	6,	'Task{id=1, name=\'Tâche du projet 1\', description=\'Ca se précise\', dueDate=Fri Nov 15 00:00:00 GMT 2024, priority=HIGH, status=IN_PROGRESS}',	'Task{id=1, name=\'Tâche du projet 1\', description=\'Ca se précise\', dueDate=2024-11-14, priority=HIGH, status=IN_PROGRESS}',	1),
(8,	'2024-10-31 16:18:27.075000',	'ASSIGN',	6,	'Assigned to User1',	'Not assigned',	1);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `user`;

DROP TABLE IF EXISTS `user_entity`;
CREATE TABLE `user_entity` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `notifications` bit(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK4xad1enskw4j1t2866f7sodrx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE `user_entity`;
INSERT INTO `user_entity` (`user_id`, `email`, `password`, `user_name`, `notifications`) VALUES
(5,	'user1@mail.fr',	'User1',	'User1',	CONV('0', 2, 10) + 0),
(6,	'user2@mail.fr',	'User2',	'User2',	CONV('0', 2, 10) + 0),
(7,	'user3@mail.fr',	'User3',	'User3',	CONV('0', 2, 10) + 0),
(8,	'user4@mail.fr',	'User4',	'User4',	CONV('0', 2, 10) + 0),
(9,	'user5@mail.fr',	'User5',	'User5',	CONV('0', 2, 10) + 0);


