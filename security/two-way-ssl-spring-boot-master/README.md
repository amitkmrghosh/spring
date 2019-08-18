2 way ssl with Spring Boot microservices — Part 1
Niral Trivedi
Niral Trivedi
Follow
Feb 19 · 7 min read
In recent years, microservice has changed the landscape of application development. It has increased speed of development, deployment and time to market drastically. While microservice has lots of virtues for application developers, there are certain key items that we need to think about when it comes to how two or more microservices communicate amongst themselves securely.
Securing a web application has become the most critical part of development. In fact, many organizations are going with Security by design principle and forcing application development teams to come up with architecture built around end to end security of their applications. Today, I am going to talk about one such approach — 2 way SSL — with regards to microservices built using Spring Boot.
What is 2-way SSL?
Traditionally, most of us are familiar with 1 way SSL. In this form, the server presents its certificate to the client and the client adds it to its list of trusted certificate. And so, the client can talk to the server.
2-way SSL is the same principle but both ways. i.e. both the client and the server has to establish trust between themselves using a trusted certificate. In this way of a digital handshake, the server needs to present a certificate to authenticate itself to client and client has to present its certificate to server. The diagram below can help you understand it little better.

2-way ssl handshake
What we are using:
Java 1.8
Spring Boot 2.1.2
keytool — this comes already with jdk installation.
We will create 2 Spring Boot applications. Ideally, we can call it client and server, but just because we are using spring boot and as per microservice principle, it is better to have a gateway application fronting all your underlying microservices. We will refer to the client as gateway and the server as ms — for microservice obviously.
Everyone is well familiar with setting up simple Spring Boot application so, I will skip that part here and go straight to what we need for making it communicate using 2 way SSL.
Create A Self Signed Client Cert
We will use key tool command for this.
keytool -genkeypair -alias nt-gateway -keyalg RSA -keysize 2048 -storetype JKS -keystore nt-gateway.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1 
That last part in key tool command is very critical as self signed cert created without SAN entries won’t work with Chrome and Safari.

Create Self Signed Server Cert:
keytool -genkeypair -alias nt-ms -keyalg RSA -keysize 2048 -storetype JKS -keystore nt-ms.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1

Create public certificate file from client cert:
Now that we’ve client and server certs created, we need to set up trust between both. To do that, we’ll import client cert in to the server’s trusted certificates and vice versa. But before we can do that, we need to extract public certificate of each jks file.
keytool -export -alias nt-gateway -file nt-gateway.crt -keystore nt-gateway.jks
Enter keystore password:
Certificate stored in file <nt-gateway.crt>
Create Public Certificate File From Server Cert:
keytool -export -alias nt-ms -file nt-ms.crt -keystore nt-ms.jks
Enter keystore password:
Certificate stored in file <nt-ms.crt>
Now, we will have to import client’s cert to server’s keystore and server’s cert to client’s keystore file.
Import Client Cert to Server jks File:
keytool -import -alias nt-gateway -file nt-gateway.crt -keystore nt-ms.jks
Import Server Cert to Client jks File:
keytool -import -alias nt-ms -file nt-ms.crt -keystore nt-gateway.jks
Configure Server For 2 Way SSL:
Copy final server jks file (in my case, nt-ms.jks) to the src/main/resources/ folder of nt-ms application.
Add the entries shown below in application.yml (or application.properties. But I prefer .yml)

ms server application.yml
Create a controller class with REST endpoint to serve the incoming request

ms-controller method
And that’s pretty much it from server side.
Configure Client for 2 way SSL:
Now, this requires some more changes than the server side as https communication is going to be initiated from here. But don’t worry. We’ll go step by step.
First, copy final client jks (in my case nt-gateway.jks) to src/main/resources/ folder
Next, add the entries shown below in application.yml

gateway-application.yml
We will need to add the below dependency in our pom. Don’t worry, we will know the use of these in next step.

gateway-pom-dependency
Luckily, Spring Boot comes with a cool RestTemplate class for http communication. We will use this class for our https call from the client application to the server. And because we are going with 2 way SSL, we need to configure this RestTemplate to use the trust store with server certificate.

gatewy-resttemplate-https
Once we’re done with that, let’s add a property in application.yml to tell the url for the server application endpoint to call.

After that, we will create a controller class with 2 methods :

And that’s pretty much it from client application configuration.
Running the Application:
Compile both the client and server code and start the application. You will see that both applications will start on defined port in application.yml with https

gateway application started with https

ms application started with https
But, there is still one problem. How do you test this? If you try to access the application with https://localhost url, your browser will complain about the client certificate being needed!!! Why? We’ve accessed all these https applications in the world so far without any problem. So, what’s so special about our application?
Because it’s 2 way SSL. When we access gateway url in browser, our browser becomes the client to our gateway application and so, our gateway web app will ask the browser to present a cert for authentication.
To overcome this, we will have to import a cert to our browser. But our browser can’t understand a .jks file. Instead, it understands PKCS12 format. So, how do we convert .jks file to PKCS12 format? Again, keytool command to the rescue!!
keytool -importkeystore -srckeystore nt-ms.jks -destkeystore nt-ms.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass nt-service -deststorepass nt-service -srcalias nt-ms -destalias nt-ms -srckeypass nt-service -destkeypass nt-service -noprompt
To import this .p12 file on mac, you will need to import this on login keychain.
Open keychain access
Click on login under “keychains” and “Certificates” under Category
Drag and drop the .p12 file here. It will prompt for the .p12 file password. Enter it and add.
Double click the cert you just uploaded and under “Trust” and select the “Always Trust” option. This will ask you for your login keychain password. Enter it and proceed.
Please close your browser windows, open and clear your cookies/cache and then hit https://localhost:9001/nt-gw/ms-data and you will be given a warning for “Connection not private” error.

Click “Show Details”

Click on “visit this website” and you will get below screen.

One final step. You will need to enter your mac login password.

And that’s it. You will be able to load the method. And if you check logs of your gateway and ms application, you will see appropriate debug entries.
That’s it folks. You can find entire source code on github