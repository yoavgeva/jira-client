# Smart Test Result Platform
Smart Test Result allow you do analytic and actions on your current tests run based on set of rules and run history.


## The Requirements
[Jira Link](https://jira.datorama.net/browse/DAT-57915)

## pre build 
* JDK 11 (AdoptOpenJDK)
* Docker and docker


## Project structure

* [common](common/README.md) - modules, utils, etc (that need to be used by both client and server).
    * [server](server/README.md) - Spring boot with tomcat embedded
    * [client](client/README.md) - Java client 
        * [reportportal-adapter](reportportal-adapter/README.md) - ReportPortal java implementation
    
