create table if not exists label_list (
    label_id varchar not null,
    primary key (label_id)
);

create table if not exists project_list (
    id uuid default uuid_generate_v4 (),
    project_name varchar not null,
     primary key (id)
);

create table if not exists subtask_list (
    subtask_id uuid default uuid_generate_v4 (),
    subtask_name varchar not null,
    subtask_task_id uuid,
    subtask_time_start timestamp with time zone,
    subtask_time_end timestamp with time zone,
    primary key (subtask_id)
);

create table if not exists task_list (
    task_id uuid default uuid_generate_v4 (),
    task_name varchar not null,
    project_task_id uuid,
    user_task_id uuid,
    task_status_id varchar not null,
    task_time_start timestamp with time zone,
    task_time_end timestamp with time zone,
    label_task_id varchar not null,
    priority_task_id varchar not null,
    primary key (task_id)
);

create table if not exists transaction_list (
    id uuid default uuid_generate_v4 (),
    transaction_name varchar not null,
    transaction_money_flow numeric(38, 2),
    task_transaction_id uuid,
    primary key (id)
);

create table if not exists user_list(
    id uuid default uuid_generate_v4 (),
    user_name varchar not null,
    user_password varchar not null,
    user_position varchar not null,
    status_id varchar not null,
    account_score_id varchar not null,
    user_status_id varchar not null,
    primary key (id)
);

create table if not exists user_project_list(
    user_id uuid,
    project_id uuid,
    primary key (user_id, project_id)
);