# Hangman
### Enviroment
java -version
>java version "11.0.12" 2021-07-20 LTS

>Java(TM) SE Runtime Environment 18.9 (build 11.0.12+8-LTS-237)

>Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.12+8-LTS-237, mixed mode)

javac -version
>javac 11.0.12

mvn -version
>Apache Maven 3.8.2

TomCat
>apache-tomcat-9.0.52

### How to run the game
+ Copy the "file.xml" to your Desktop
+ Open the project in Eclipse
+ Certify that servlet's path is correct
+ + right click on project's name > Build Path > Configure Build Path... > Libraries > Add External JARs... > go to TomCat folder > lib > servlet-api.jar > apply and close
+ Go to Web Content file 
+ Right click on index.jsp > Run As > Run on Server
+ If you don't have a TomCat server created you'll need to create one (https://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/)
