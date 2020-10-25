# Weather Application

### Assumptions and conventions

   - Assuming that pressure and temperature are both double type, instead of int type.

### How to run tests and application locally

  To run all tests from command line, just issue the following command.
  
```
mvn test
```

  To start the application, run the following command, assuming maven and jdk8+ are installed.
    
```
mvn spring-boot:run 
```

  In order to query data from the running application, you can send HTTP request to the server by CURL like below to get current weather data for the city specified. 
  
```
curl --location --request GET 'localhost:8080/current?location=Berlin'
```

  Or get historical weather data like this.
  
```
curl --location --request GET 'localhost:8080/history?location=Berlin'
```


### Tasks besides development

- Give us an indication of what you think a production ready micro service should look like.

> In order to deploy our application to production environment. There are a couple of things we need to do for it.
>   
>   - Make sure we have a dedicated configuration file, say `application-production.properties` for it, which could be used to configure third party services URLs, and make sure things like API keys are configured through environment variables, instead of putting them in the configuration file.
>   - We also need a production database configured for our application, database url and port could be put into `application-production.properties` file, but for credentials, make sure them all configured through environment variables.
>   - Also need to disable all development related services, like h2-console and so on.
>
- Describe how you would deploy the application in an AWS environment (we are using Terraform).

>  - For deploying the application to AWS environment, because I have some experience with Docker, so I'd like use Docker for deploying the application, which need me to add a Dockerfile which specifys the packaged jar for running our application and dependencies which need to be included in the container.
>  - And for scaling up our application, we can either use AWS Elastic Beanstalk or maybe Kubernetes.



