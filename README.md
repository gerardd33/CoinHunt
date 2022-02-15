# Coin Hunt
Coin Hunt is a maze-based game that lets you compete with your friends and other players.


## Technologies

- Kotlin, Spring Boot, Java, Angular
- MongoDB, Docker, Varnish
- MapStruct, JUnit, MockK


## Contributors

- [Gerard Dróżdż](https://github.com/gerardd33)
- [Jakub Organa](https://github.com/djakob991)


## Running locally

### Setup

To run it locally, first make sure you have ``docker`` and ``docker-compose`` installed and the [Docker daemon running](https://docs.docker.com/config/daemon/systemd/#start-manually). Once you do this, clone this repository and enter the *CoinHunt* folder:

```
git clone https://github.com/gerardd33/CoinHunt.git
cd CoinHunt
```

Then run the following command:

```
sudo docker-compose build
sudo docker-compose up
```

If you run it for the first time, the application will take several minutes to build and run. Running it the next time using the same command will not require building from scratch so it will be considerably faster. Once it is up and running, it can be accessed by typing ``localhost:9133`` in your browser.

### Cleanup

If you have run CoinHunt locally and don't need it anymore, remember to clean up your machine by removing the created Docker containers and images, for example using the following commands:

```
sudo docker rm $(docker ps -aq --filter "name=coinhunt*")
sudo docker rmi $(docker images -q "coinhunt*")
sudo docker image prune
sudo docker network prune
```
