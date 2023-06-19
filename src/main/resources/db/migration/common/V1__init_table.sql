--CREATE SEQUENCE IF NOT EXISTS recipe_seq;
CREATE SEQUENCE recipe_seq start with 1 increment by 1;

CREATE TABLE  recipe (
	id bigserial NOT NULL,
	name varchar(255) NULL,
	instructions varchar(255) NULL,
	num_serving int NULL,
	is_vegetarian boolean,
	CONSTRAINT pk_recipe PRIMARY KEY (id)
);

CREATE TABLE recipe_ingredients (
    recipe_id bigint not null,
    ingredients varchar(255),
    CONSTRAINT fk_recipe_ingredients FOREIGN KEY (recipe_id) REFERENCES recipe(id)
);



