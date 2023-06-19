insert into recipe (name, instructions, num_serving, is_vegetarian) values ('tes1', 'asdasd', 5, true);
insert into recipe (name, instructions, num_serving, is_vegetarian) values ('tes2', 'sdfsdf', 5, true);
insert into recipe (name, instructions, num_serving, is_vegetarian) values ('tes3', 'asdas', 6, false);

alter sequence recipe_seq restart with 4;

insert into recipe_ingredients (recipe_id,ingredients) values (1,'aaa');
insert into recipe_ingredients (recipe_id,ingredients) values (1,'bbb');
insert into recipe_ingredients (recipe_id,ingredients) values (2,'ccc');
insert into recipe_ingredients (recipe_id,ingredients) values (3,'ddd');