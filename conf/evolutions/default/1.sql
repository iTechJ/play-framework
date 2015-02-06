# --- First database schema

# --- !Ups

create table book (
  id                        bigint not null AUTO_INCREMENT,
  name                      varchar(255),
  price                     integer not null,
  constraint pk_book primary key (id));


  INSERT INTO book (name, price) VALUES
    ('Things Fall Apart', 39),('The Divine Comedy', 29),('One Thousand and One Nights', 45), ('Pride and Prejudice', 29),
    ('Ficciones', 19),('The Canterbury Tales', 33);

# --- !Downs

drop table if exists book;

