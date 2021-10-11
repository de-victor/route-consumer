FROM openjdk:11
COPY rotas_fast_food.xml /usr/src/desafio/
COPY rotas_shopping_1.xml /usr/src/desafio/
COPY /target/xmlreader-1.0.0-all.jar /usr/src/desafio/
WORKDIR /usr/src/desafio
CMD ["java", "-jar", "xmlreader-1.0.0-all.jar"]
EXPOSE 8080