create table if not exists account_status_list (
    account_status_id varchar not null
);

insert into account_status_list (account_status_id) values
                                        ('free'),
                                        ('premium');