# Products microservice

## Local development

Start a PostgreSQL database in a Docker container. The database username, password and database name must mach the
configuration in `api/src/main/resources/config.yaml`.

```bash
docker run  -d --name pg-stores
            -e POSTGRES_USER=dbuser
            -e POSTGRES_PASSWORD=postgres
            -e POSTGRES_DB=stores
            -p 5432:5432
            postgres:13
```

Then compile the project using Maven and run the generated JAR file using the following commands.

```bash
mvn clean package
java -jar .\api\target\store-catalog-api-1.0.0-SNAPSHOT.jar
```

Available at: [localhost:8080/v1/stores](http://localhost:8080/v1/stores)

## Docker

```bash
# Compile the project to jar compressed file and build a Docker image named store-catalog.
docker build -t store-catalog .

# Create a new network
docker network create --driver bridge price-comparison-network

# Run a postgres database in container named pg-stores in defined network.
# Since we have defined both the container name and network, we can connect to this instance
# using the following connection string "jdbc:postgresql://pg-stores:5432/stores"
docker run  -d --name pg-stores
            -e POSTGRES_USER=dbuser
            -e POSTGRES_PASSWORD=postgres
            -e POSTGRES_DB=stores
            -p 5432:5432
            --network price-comparison-network
            postgres:13

# Run a Docker container from image named store-catalog.
# Define database server, username and password as environment variables.
docker run  -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-stores:5432/stores
            -e KUMULUZEE_DATASOURCES0_USERNAME=dbuser
            -e KUMULUZEE_DATASOURCES0_PASSWORD=postgres
            --name store-catalog
            -p 8080:8080
            --network price-comparison-network
            store-catalog
```

We can use the [ElephantSQL](https://www.elephantsql.com/) PostgreSQL database as a Service.
