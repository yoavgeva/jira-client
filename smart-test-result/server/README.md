#Server 
##Set up locally
* install OpenJDK 11
* create ~/elasticsearch/data -> persistent data over time for ES
* install Docker and docker-compose

##Working locally
* compile all of smart-test-result for server to be compiled
* ```docker-compose up --build``` --> to raise environment (install docker and docker-compose)! 
* ```docker-compose down --rmi 'local'``` -> when stop working (deleting the created image).
* ```docker image prune --filter "dangling=true"``` -> if forgot to delete created images 

* Adding Configurations in .run for ease of working/debugging
    * ```setup-smart-test-result-server``` - is a pipeline made of: compile -> docker build -> docker-compose up
    * ```teardown-smart-test-result``` - is a pipeline made of: docker-compose down --rmi 'local'
    * ```remote-local-smart-test-result``` - attach to running docker of smart-test-result with dev tools

* Intellij - use remote to 8000 after docker deploy

* Swagger - you can debug with swagger locally or when server running with as example http://localhost:8080/api/swagger-ui.html



##Troubleshooting 
* ElasticSearch with Docker compose
    * Configuration for local elasticsearch:
    
      macOS with Docker for Mac
      
      The vm.max_map_count setting must be set within the xhyve virtual machine:
      
      From the command line, run:
      
      screen ~/Library/Containers/com.docker.docker/Data/vms/0/tty
      Press enter and use`sysctl` to configure vm.max_map_count:
      
      sysctl -w vm.max_map_count=262144
      To exit the screen session, type Ctrl a d.
      Windows and macOS with Docker Desktop
      
      The vm.max_map_count setting must be set via docker-machine:
      
      docker-machine ssh
      sudo sysctl -w vm.max_map_count=262144




