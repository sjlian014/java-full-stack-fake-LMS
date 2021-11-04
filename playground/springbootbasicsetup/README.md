# spring boot basic setup

goal:

1. learn how to create a simple spring boot project with maven following [this guide](https://www.tutorialspoint.com/spring_boot/spring_boot_bootstrapping.htm) on tutorialspoint.com
2. learn how to configure and inject beans

## also see

[this demo](https://github.com/wuyouzhuguli/SpringAll/blob/master/01.Start-Spring-Boot/src/main/java/com/springboot/demo/DemoApplication.java) from [wuyouzhuguli's repo](https://github.com/wuyouzhuguli/SpringAll)

[What is a Spring Bean?](https://www.baeldung.com/spring-bean)

## setup

make sure maven is install and is executable from command line

## usage

package source into jar using:
`mvn clean package` at the project root directory

then, to start the server:
`java -jar target/springbootbasicsetup-0.0.1-SNAPSHOT.jar`

go to *localhost:8080/another_custom_path/* and *another_custom_path/* to see the results
