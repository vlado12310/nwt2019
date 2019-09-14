insert into pricelist (id, start, end) values ('1', '2018.05.10', '2018.05.20');
insert into pricelist (id, start, end) values ('2', '2018.10.06', '2018.06.20');
insert into pricelist (id, start, end) values ('3', '2019.10.06', '2019.06.20');
insert into pricelist (id, start, end) values ('4', '2040.10.06', '2040.06.20');
insert into pricelist (id, start, end) values ('5', '2019.01.25', '2019.05.20');
insert into ticket_type(id, name, duration_in_hours, required_status) values ('1', 'mesecna', '720', 0);
insert into ticket_type(id, name, duration_in_hours, required_status) values ('2', 'godisnja', '876000', 0);
insert into ticket_type(id, name, duration_in_hours, required_status) values ('3', 'studentska mesecna', '720', 1);
insert into ticket_type(id, name, duration_in_hours, required_status) values ('4', 'penzionerska', '720', 2);
insert into ticket_type_zones(ticket_type_id, zones_id) values ('1', '1');
insert into user(user_id, name, l_name, email, password, status, balance) values ('1', 'pera', 'peric', 'USER', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 1, 5000);
insert into user(user_id, name, l_name, email, password, status, balance) values ('2', 'pera', 'peric', 'USERELDERLY', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 1, 5000);


insert into ticket(id, purchase_date, time_of_activation, user_user_id, ticket_type_id) values ('1', '2018.05.12', null, '1', '1');
insert into ticket(id, purchase_date, time_of_activation, user_user_id, ticket_type_id) values ('2', '2018.05.12', '2018.05.13', '1', '1');
insert into ticket(id, purchase_date, time_of_activation, user_user_id, ticket_type_id) values ('3', '2018.05.12', null, '1', '1');
insert into ticket(id, purchase_date, time_of_activation, user_user_id, ticket_type_id) values ('4', '2018.05.12', '2019.01.13', '1', '2');
insert into ticket(id, purchase_date, time_of_activation, user_user_id, ticket_type_id) values ('5', '2018.05.12', '2019.01.27', '1', '1');


insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('1', '1', '1000');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('1', '3', '1000');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('2', '1', '900');

insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('1', '5', '500000');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('3', '5', '900');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('2', '5', '900');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('2', '4', '900');

insert into authority(id, name) values ('1', 'ADMIN');
insert into authority(id, name) values ('2', 'CONDUCTER');
insert into authority(id, name) values ('3', 'USER');
insert into user_authority(id, authority_id, user_user_id) values ('1', '3', '1');

insert into line(id, name, zone_id) values ('1', '7A', '1');

insert into zone(id, name) values ('1', 'gradska');

