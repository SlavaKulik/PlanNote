create extension if not exists pgcrypto;

insert into user_list (user_name, user_password, user_position, status_id, account_score_id, user_role) values
('SlavaAdmin', crypt('Slava123', gen_salt('bf')), 'Back-end developer', 'premium_user', 'Conveyor','ROLE_ADMIN'),
('Slava', crypt('slava123', gen_salt('bf')), 'Back-end developer', 'free_user', 'Newbie','ROLE_USER');