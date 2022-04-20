# JShop
Microservice based E-shop

Run microservices:
docker-compose -f .\docker-compose.yml -f .\docker-compose.override.yml up -d
Stop all docker images:  docker stop $(docker ps -aq)
Remove all docker images:  docker rm $(docker ps -aq)
Remove unused images: docker system prune

<repositories>
    <repository>
        <id>local-maven-repo</id>
        <url>file:///${project.basedir}/local-maven-repo</url>
    </repository>
</repositories>
Then for each external jar you want to install, go at the root of your project and execute:

mvn deploy:deploy-file -DgroupId=[GROUP] -DartifactId=[ARTIFACT] -Dversion=[VERS] -Durl=file:./local-maven-repo/ -DrepositoryId=local-maven-repo -DupdateReleaseInfo=true -Dfile=[FILE_PATH]


Swagger:
http://localhost:8080/swagger-ui.html just an example

Actuator endpoints example:
http://localhost:8080/actuator/health