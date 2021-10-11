# END POINT COORDENADAS

Projeto proposto para consumo de coordenadas enviadas por dispositivos com fim de atender as entregas de rotas pré-estabelecidas.

## Instalação

Utilize o [maven](https://maven.apache.org/) para gerar o build

```bash
mv clean compile package -Pprod
```

## Uso

Este projeto esta configurado com dois planos de rotas que sempre será registrado na inicialização. Estas rotas já configuradas servem para atender uma simulação de envio de um projeto secundário que pode ser acessado [aqui](https://github.com/de-victor/xmlreader).
O projeto será iniciado utilizando a porta 9090. é possível se conectar ao banco h2 utilizado neste projeto através da url http://localhost:9090/h2-console. O acesso ao banco esta na uri jdbc:h2:mem:delivery

## End points(GET)
* Num dado instante, quais paradas foram atendidas para cada rota? -> http://localhost:9090/Route/DeliveryStatus
* Num dado instante, qual é o status de cada rota (Pendente, Em progresso, Concluída)? -> http://localhost:9090/Route
* Quais foram as paradas mais demoradas para cada rota? -> http://localhost:9090/Route/LongerStops

## End point Route(http://localhost:9090/Route)
Um crude foi desenvolvido para o end point com as verbose PUT,DELETE,POST,GET

## Uso simulado
O projeto contem um docker-compose para a execução simulada do consumo das coordenadas. Na saída do terminal será possível ver a aproximação das coordenadas com as paradas de destino. Nas configurações do container coordinate-sender é importante informar o ip da maquina host para que as coordenadas sejam enviadas ao backend

```bash
docker-compose up
```

## Etapa Bônus

#### Uma vez que você realizou ao menos a etapa 1, como sua aplicação vai para produção (deployment)?
Necessário criar uma imagem docker para entregar a produção, para isso será necessário criar o jar informado na seção de Instalação junto com a configuração do dockerfile. Para este container foi utilizado a imagem base do openjdk11.

```dockerfile
FROM openjdk:11
COPY . /usr/src/desafio
WORKDIR /usr/src/desafio
CMD ["java", "-jar", "desafio-1.0.0.0-all.jar"]
EXPOSE 9090
```

```bash
docker build -t [nome-imagem] .
```

#### Se uma falha ocorrer, como podemos visualizar os logs da sua aplicação em produção?
Para acessar os logs se deve entrar no container fazendo uma execução de bash para dentro do mesmo, assim navegar até a pasta /logs. Uma outra alternativa é criar um volume da pasta logs para fora do container.

#### Que métricas você considera importantes existirem na sua aplicação.
Análise de desempenho da aplicação e erros com base nos logs para otimizar o serviço e corrigir possíveis falhas que possam surgir.

#### Caso eu não conheça seu projeto, como posso fazê-lo funcionar sem ter que te perguntar pessoalmente?
Os passos de execução deste projeto foi abordado neste documento, mas recomendo a utilização do docker-compose.yml para visualização da aplicação em execução.