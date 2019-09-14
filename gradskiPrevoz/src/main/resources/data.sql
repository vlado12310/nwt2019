
insert into ticket_type(id, name, duration_in_hours, required_status) values ('1', 'mesecna', '720', 0);
insert into ticket_type(id, name, duration_in_hours, required_status) values ('2', 'godisnja', '8760', 0);
insert into ticket_type(id, name, duration_in_hours, required_status) values ('3', 'studentska mesecna', '720', 1);
insert into pricelist (id, start, end) values ('1', '2018.12.20', '2019.12.05');
insert into pricelist (id, start, end) values ('2', '2016.12.20', '2017.12.05');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('1', '1', '1000');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('2', '1', '2400');
insert into pricelist_item(ticket_type_id, pricelist_id, price) values ('3', '1', '700');
insert into user(user_id, name, l_name, email, password, status, balance) values ('1', 'pera', 'peric', 'student', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 1, 5000);
insert into user(user_id, name, l_name, email, password, status, balance) values ('2', 'pera2', 'peric2', 'pera@gmail.com2', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 1, 20);
insert into user(user_id, name, l_name, email, password, status, balance) values ('3', 'admin', 'admin', 'admin', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 3, 20);
insert into user(user_id, name, l_name, email, password, status, balance) values ('4', 'conducter', 'conducter', 'conducter', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 4, 20);
insert into user(user_id, name, l_name, email, password, status, balance) values ('5', 'pera', 'peric', 'user', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 0, 6000);


insert into ticket(id, purchase_date, time_of_activation, user_user_id, ticket_type_id) values ('1', '2019.01.01', null, '1', '1');
insert into authority(id, name) values ('1', 'ADMIN');
insert into authority(id, name) values ('2', 'CONDUCTER');
insert into authority(id, name) values ('3', 'USER');
insert into user_authority(id, authority_id, user_user_id) values ('1', '3', '1');
insert into user_authority(id, authority_id, user_user_id) values ('2', '2', '2');
insert into user_authority(id, authority_id, user_user_id) values ('3', '1', '3');
insert into user_authority(id, authority_id, user_user_id) values ('4', '3', '5');





insert into zone(id, name) values ('1', 'gradska');
insert into zone(id, name) values ('2', 'prigradska');

insert into ticket_type_zones(ticket_type_id, zones_id) values ('1', '1');
insert into ticket_type_zones(ticket_type_id, zones_id) values ('2', '1');
insert into ticket_type_zones(ticket_type_id, zones_id) values ('3', '2');

insert into line (id, name, zone_id) values ('1', '11A', '1');
insert into line (id, name, zone_id) values ('2', '11B', '1');
insert into line (id, name, zone_id) values ('3', '7A', '1');
insert into line (id, name, zone_id) values ('4', '71A', '2');
insert into line (id, name, zone_id) values ('5', '74', '2');

insert into location(id, lat, lng) values ('1', '45.240947', '19.80747');
insert into location(id, lat, lng) values ('2', '45.240448', '19.811879');
insert into location(id, lat, lng) values ('3', '45.239972', '19.816622');
insert into location(id, lat, lng) values ('4', '45.239443', '19.825226');
insert into location(id, lat, lng) values ('5', '45.237109', '19.827501');
insert into location(id, lat, lng) values ('6', '45.238628', '19.832972');
insert into location(id, lat, lng) values ('7', '45.241914', '19.842896');
insert into location(id, lat, lng) values ('8', '45.244271', '19.841759');
insert into location(id, lat, lng) values ('9', '45.248335', '19.839571');
insert into location(id, lat, lng) values ('10', '45.252549', '19.837232');
insert into station(id, name, location_id ) values ('1', 'stanica1', '1');
insert into station(id, name, location_id ) values ('2', 'stanica2', '2');
insert into station(id, name, location_id ) values ('3', 'stanica3', '3');
insert into station(id, name, location_id ) values ('4', 'stanica4', '4');
insert into station(id, name, location_id ) values ('5', 'stanica5', '5');
insert into station(id, name, location_id ) values ('6', 'stanica6', '6');
insert into station(id, name, location_id ) values ('7', 'stanica7', '7');
insert into station(id, name, location_id ) values ('8', 'stanica8', '8');
insert into station(id, name, location_id ) values ('9', 'stanica9', '9');
insert into station(id, name, location_id ) values ('10', 'stanica10', '10');