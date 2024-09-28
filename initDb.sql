CREATE SCHEMA `rsc_ts`;
USE `rsc_ts`;

INSERT INTO `rsc_ts`.`rsc_ts_users` (`id`, `added_at`, `is_active`, `name`, `password`, `root_user`, `username`) VALUES ('9999', NOW(), b'1', 'admin', '$2a$10$XZWGvC2ZPYr0XInjXJM/ae8SwaqO4Kg/iBLys8o.ULQuwgwJoRqzG', b'1', 'admin');
INSERT INTO `rsc_ts`.`rsc_ts_roles` (`id`, `role`) VALUES ('1', 'Admin');
INSERT INTO `rsc_ts`.`rsc_ts_roles` (`id`, `role`) VALUES ('2', 'User');
INSERT INTO `rsc_ts`.`rsc_ts_user_roles` (`user_id`, `role_id`) VALUES ('9999', '1');
