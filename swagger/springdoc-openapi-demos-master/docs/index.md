---
layout: default
---
![Octocat](https://springdoc.github.io/springdoc-openapi-demos/images/springdoc-openapi.png)

# **Introduction**

springdoc-openapi java library helps automating the generation of API documentation using spring boot projects.
springdoc-openapi works by examining an application at runtime to infer API semantics based on spring configurations, class structure and various annotations.

Automatically generates documentation in JSON/YAML and HTML format APIs. 
This documentation can be completed by comments using swagger-api annotations.

This library supports:
*  OpenAPI 3
*  Spring-boot (v1 and v2)
*  JSR-303, specifically for @NotNull, @Min, @Max, and @Size.
*  Swagger-ui
*  Oauth 2

# **Getting Started**

## Library for springdoc-openapi integration with spring-boot and swagger-ui 
*   Automatically deploys swagger-ui to a spring-boot 2 application
*   Documentation will be available in HTML format, using the official [swagger-ui jars](https://github.com/swagger-api/swagger-ui.git).
*   The Swagger UI page should then be available at http://server:port/context-path/swagger-ui.html and the OpenAPI description will be available at the following url for json format: http://server:port/context-path/v3/api-docs
    * server: The server name or IP
    * port: The server port
    * context-path: The context path of the application
*   Documentation can be available in yaml format as well, on the following path : /v3/api-docs.yml
*   Add the library to the list of your project dependencies (No additional configuration is needed)

```xml
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.1.41</version>
   </dependency>
```
*   This step is optional: For custom path of the swagger documentation in HTML format, add a custom springdoc property, in your spring-boot configuration file:

```properties
# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html
```

## [Demo Spring Boot 2 webmvc with OpenAPI 3](https://springdoc-openapi-test-app2-silly-numbat.eu-de.mybluemix.net/).
## [Demo Spring Boot 2 webflux with OpenAPI 3](https://springdoc-openapi-test-app3-terrific-rabbit.eu-de.mybluemix.net/swagger-ui.html).
## [Demo Spring Boot 1 webmvc with OpenAPI 3](https://springdoc-openapi-test-app1-courteous-puku.eu-de.mybluemix.net/).

![Branching](https://springdoc.github.io/springdoc-openapi-demos/images/pets.png)

## Source code of the Demo Applications
*   [https://github.com/springdoc/springdoc-openapi-demos.git](https://github.com/springdoc/springdoc-openapi-demos.git)

## Integration of the libray in a spring-boot 2 project without the swagger-ui:
*   Documentation will be available at the following url for json format: http://server:port/context-path/v3/api-docs
    * server: The server name or IP
    * port: The server port
    * context-path: The context path of the application
*   Documentation will be available in yaml format as well, on the following path : /v3/api-docs.yml
*   Add the library to the list of your project dependencies. (No additional configuration is needed)

```xml
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-core</artifactId>
      <version>1.1.41</version>
   </dependency>
```
*   This step is optional: For custom path of the OpenAPI documentation in Json format, add a custom springdoc property, in your spring-boot configuration file:

```properties
# /api-docs endpoint custom path
springdoc.api-docs.path=/api-docs
```

## Adding API Information and Security documentation
  The library uses spring-boot application auto-configured packages to scan for the following annotations in spring beans: OpenAPIDefinition and Info.
  These annotations declare, API Information: Title, version, licence, security, servers, tags, security and externalDocs.
  For better performance of documentation generation, declare @OpenAPIDefinition and @SecurityScheme annotations within a spring managed bean.  
 
## Error Handling for REST using @ControllerAdvice
To generate documentation automatically, make sure all the methods declare the HTTP Code responses using the annotation: @ResponseStatus

## spring-weblfux support with Annotated Controllers
*   Documentation can be available in yaml format as well, on the following path : /v3/api-docs.yml
*   Add the library to the list of your project dependencies (No additional configuration is needed)

```xml
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-webflux-ui</artifactId>
      <version>1.1.41</version>
   </dependency>
```
*   This step is optional: For custom path of the swagger documentation in HTML format, add a custom springdoc property, in your spring-boot configuration file:

```properties
# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html
```

## **Introduction to springdoc-openapi-maven-plugin**

The aim of springdoc-openapi-maven-plugin is to generate json and yaml OpenAPI description  during build time. 
The plugin works during integration-tests phase, and generate the OpenAPI description. 
The plugin works in conjunction with spring-boot-maven plugin. 

You can test it during the integration tests phase using the maven command:

```properties
mvn verify
```

In order to use this functionality, you need to add the plugin declaration on the plugins section of your pom.xml:

```xml
<plugin>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-maven-plugin</artifactId>
 <version>2.1.8.RELEASE</version>
 <executions>
  <execution>
   <id>pre-integration-test</id>
   <goals>
    <goal>start</goal>
   </goals>
  </execution>
  <execution>
   <id>post-integration-test</id>
   <goals>
    <goal>stop</goal>
   </goals>
  </execution>
 </executions>
</plugin>
<plugin>
 <groupId>org.springdoc</groupId>
 <artifactId>springdoc-openapi-maven-plugin</artifactId>
 <version>0.2</version>
 <executions>
  <execution>
   <id>integration-test</id>
   <goals>
    <goal>generate</goal>
   </goals>
  </execution>
 </executions>
<plugin>
```
			
## **Custom settings of the springdoc-openapi-maven-plugin**

It possible to customise the following plugin properties:
*   apiDocsUrl: The local url of your (json or yaml). 
    * The default value is: http://localhost:8080/v3/api-docs
*  outputDir: The output directory, where to generate the OpenAPI description.
    * The default value is: ${project.build.directory}
*   outputFileName: The file name that contains the OpenAPI description.  
    * The default value is: openapi.json

```xml
<plugin>
 <groupId>org.springdoc</groupId>
 <artifactId>springdoc-openapi-maven-plugin</artifactId>
 <version>0.2</version>
 <executions>
  <execution>
   <id>integration-test</id>
   <goals>
    <goal>generate</goal>
   </goals>
  </execution>
 </executions>
 <configuration>
  <apiDocsUrl>http://localhost:8080/v3/api-docs</apiDocsUrl>
  <outputFileName>openapi.json</outputFileName>
  <outputDir>/home/springdoc/maven-output</outputDir>
 </configuration>
</plugin>
```

## Dependencies repository

The springdoc-openapi libraries are hosted on maven central repository. 
The artifacts can be viewed accessed at the following locations:

Releases:
* [https://oss.sonatype.org/content/groups/public/org/springdoc/](https://oss.sonatype.org/content/groups/public/org/springdoc/).

Snapshots:
* [https://oss.sonatype.org/content/repositories/snapshots/org/springdoc/](https://oss.sonatype.org/content/repositories/snapshots/org/springdoc/).

## spring-weblfux with Functional Endpoints, will be available in the future release





