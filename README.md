# Getting Started

# ABN-Assignment

## Objective

Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:
1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.


For example, the API should be able to handle the following search requests:
1. All vegetarian recipes
2. Recipes that can serve 4 persons and have “potatoes” as an ingredient
3. Recipes without “salmon” as an ingredient that has “oven” in the instructions.

-----------------------------------------

## Required Setup

#### Minimum Requirements

- Java 17

#### Install the application

1. Make sure you have Java 17 installed

2. Open the command line in the source code folder

3. Build application and run all tests

  ```
  $ mvn clean install
  ```


Run the project

  ```
  $ java -jar target/abn-0.0.1-SNAPSHOT.jar
  
  ```

4. Open the swagger-ui with the link below

```text
http://localhost:8080/abn-api
```

-----------------------------------------
## Implementation

The application is production ready within the context of this assignment.

I added extensive documentation for all the APIs their parameters as well as the API response models and the most common status codes expected from the APIs.

My framework of choice was `spring boot` because of ease of setup, various integrations with popular tools and extensive documentation.

I'm using `H2` with `hibernate` and `JPA` as my database and ORM framework of choice. Mostly because with `H2` you don't need a docker container to run the db container.

I'm using `Flyway migration` because it is highly beneficial for production environments as it provides version control, ensures reproducible deployments, maintains data integrity, allows for easy rollback, facilitates collaboration among team members, and benefits from a strong and active community for support and resources.

In the tables the name of recipe and the instructions are stored as a `String`, the number of servings as an `int`, whether dish is vegetarian or not as a `boolean` and the list of ingredients as a `set of Strings` to avoid duplication. 
All the recipes also get an auto-generated ID  of type `Long` using `Sequence` on data base.

My initial idea was to use CQRS (Command Query Responsibility Segregation) because is beneficial for the following reasons:

1. Scalability: It enables independent scaling of read and write operations.
2. Performance: Allows optimization of read and write models separately, improving performance.
3. Flexibility: Easily evolve and modify the application without impacting existing functionality.
4. Maintainability: Clear separation of concerns improves code organization and understandability.
5. Consistency Models: Provides the ability to choose different consistency models for reads and writes.
6. DDD Alignment: CQRS aligns well with Domain-Driven Design principles, enhancing domain modeling.

But it has not been fully implemented. 
The initial implementation was applied just to the `Controller layer`
where I have class `RecipeCommandController` just responsible to perform operation like: save, update and delete
and the class `RecipeQueryController` juste responsible to perform operarion to retrieve information

I also added Integration Tests for our Endpoints, and also Unit tests for Services.
And I am also filtering and sanitizing the errors thrown by the application

