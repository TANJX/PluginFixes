create table if not exists %table_name%
(
   id                   int not null auto_increment,
   player_name          varchar(16),
   server_name          varchar(20),
   primary key (id)
);