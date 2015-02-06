# --- Adding users

# --- !Ups

create table user (
  id                        bigint not null AUTO_INCREMENT,
  name                      varchar(255) not null,
  password                  varchar(255) not null,
  role                      varchar(100) not null,
  constraint pk_user primary key (id));


  INSERT INTO user (name, password, role) VALUES
    ('Admin', '$2a$10$DMH95r91mgDmHqEra.4XS.0ZdKgVD7gGco4Qz5vQQlidlkN3saV.K', 'ADMIN'),
    ('User', '$2a$10$EZhJbjhgPj6QmFRsIH1WaeJ08UkZENvJLTVCrYcgUt1P9Sbni5p7y', 'USER');

# --- !Downs

drop table if exists user;

