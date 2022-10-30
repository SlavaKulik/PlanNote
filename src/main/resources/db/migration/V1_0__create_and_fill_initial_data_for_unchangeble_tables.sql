create extension if not exists "uuid-ossp";

create table if not exists account_status (
    account_status_id varchar not null,
    primary key (account_status_id)
);

insert into account_status (account_status_id) values
    ('free_user'),
    ('premium_user');

create table if not exists priority_list (
    priority_id varchar not null,
    primary key (priority_id)
);

insert into priority_list (priority_id) values
    ('Low'),
    ('Mid'),
    ('High');

create table if not exists score_list (
    score_id varchar not null,
    primary key (score_id)
);

insert into score_list (score_id) values
    ('Newbie'),
    ('Beginner'),
    ('Office plankton'),
    ('Medium-level worker'),
    ('Advanced-level worker'),
    ('Conveyor');

create table if not exists status_list (
    status_id varchar not null,
    primary key (status_id)
);

insert into status_list (status_id) values
    ('To do'),
    ('In-progress'),
    ('Complete');

create table if not exists user_status_list (
    user_status_id varchar not null,
    primary key (user_status_id)
);

insert into user_status_list (user_status_id) values
    ('Project owner'),
    ('Project member');