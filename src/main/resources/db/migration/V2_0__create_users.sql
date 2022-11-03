create extension if not exists pgcrypto;

insert into user_list (user_name, user_password, user_position, status_id, account_score_id, user_status_id) values
('Slava', crypt('slava_password', gen_salt('bf')), 'Back-end developer', 'premium_user', 'Conveyor','Project member'),
('Jim', crypt('jim_password', gen_salt('bf')), 'Front-end developer', 'free_user', 'Newbie','Project member'),
('Austin', crypt('austin_password', gen_salt('bf')), 'UI/UX designer', 'premium_user', 'Office plankton','Project owner');