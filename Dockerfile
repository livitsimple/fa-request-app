# 1) Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
RUN ./mvnw -B -DskipTests dependency:go-offline
COPY src src
RUN ./mvnw -B -DskipTests clean package

# 2) Run stage (smaller image)
FROM eclipse-temurin:21-jre
WORKDIR /app
# copy the built jar
COPY --from=build /app/target/*.jar app.jar
# expose (Render will pass PORT env var)
ENV PORT=8080
CMD ["java","-jar","/app/app.jar"]