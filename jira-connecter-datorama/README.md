# A Jira custom connector to datorama production
This connector allows users to describe by JQL what issues does they need, it will export data from Jira platform to Datorama.

#Set up
* AWS
    * Need to set up user with .aws/credentials default profile follow this [Guide](https://cloud.spring.io/spring-cloud-static/spring-cloud-aws/2.2.0.M2/reference/html/#_sdk_credentials_configuration)
    * you can get user from dev Vault datomation-common.properties automation.s3 property
#**Swagger** 
you can debug with swagger locally or when server running with as example http://localhost:8080/swagger-ui.html

#Deploy
use maven and run spring-boot:build-image to create docker image