insert into tbl_route_plan(description, route_status, date) values('Plan A', 'NOT_STARTED', current_date);
insert into tbl_route_plan(description, route_status, date) values('Plan B', 'NOT_STARTED', current_date);

insert into tbl_coordinate_delivery (description , lat , lgn , id_route_plan, delivery_status, delivery_radius, address ) values ('Iguatemi', -3.756666, -38.4860087, 1, 'NOT_ANSWER', 50, 'rua iguatemi 948');
insert into tbl_coordinate_delivery (description , lat , lgn , id_route_plan, delivery_status, delivery_radius, address) values ('Del Paseo', -3.736223,  -38.49676, 1, 'NOT_ANSWER', 100, 'rua del passeo 999');
insert into tbl_coordinate_delivery (description , lat , lgn , id_route_plan, delivery_status, delivery_radius, address ) values ('Benfica', -3.7390903,  -38.5402134, 1, 'NOT_ANSWER', 150, 'rua benfica 838');


insert into tbl_coordinate_delivery (description , lat , lgn , id_route_plan, delivery_status, delivery_radius, address ) values ('McDonald', -3.772009, -38.4825836, 2, 'NOT_ANSWER', 60, 'Rua macdonald 1010');
insert into tbl_coordinate_delivery (description , lat , lgn , id_route_plan, delivery_status, delivery_radius, address) values ('El Chancho - Aldeota', -3.7390974,  -38.4968968, 2, 'NOT_ANSWER', 70, 'Rua el chancho 2102');
insert into tbl_coordinate_delivery (description , lat , lgn , id_route_plan, delivery_status, delivery_radius, address ) values ('El Chancho - Abolição', -3.7266861,  -38.4925013, 2, 'NOT_ANSWER', 50, 'Rua elchando abolição 33');


