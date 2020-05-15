# SpringbootBackendChallenge
My solution of a 48h back-end hiring challenge developed using Spring Boot and MySQL, both of which I had never experienced before.

-------------------------------------------------------------------------------------------------------------

# The problem

The main objective of this challenge is to implement a service to manage a specific resource: Calls. The Call resource represents a phone call between two numbers with the following attributes:

* Caller Number: the phone number of the caller. 
* Callee Number: the phone number of the callee.
* Start Timestamp: start timestamp of the call.
* End Timestamp: end timestamp of the call.
* Type: Inbound or Outbound

This challenge should have two components: a Web Service and a Client.

### Web Service

This Web Service should be able to manage and persist the Call resource, providing the following operations:

* Create Calls (one or more).
* Delete Call.
* Get all Calls using pagination and be able to filter by Type.
* Get statistics (the response to this operation should have the values aggregate by day, returning all days with calls):
  * Total call duration by type.
  * Total number of calls.
  * Number of calls by Caller Number.
  * Number of calls by Callee Number.
  * Total call cost using the following rules:
    * Outbound calls cost 0.05 per minute after the first 5 minutes. The first 5 minutes cost 0.10. 
    * Inbound calls are free. 
    * To persist the calls you should use any database that you feel comfortable with.
    
### Client

The Client should allow the programmer to use all the operations of the Web Service without having to handle the connection by himself.

# The solution

### Web Service

The web service was implemented following a three layer architectural pattern composed by an api controller, a service middleware and a Data Acess Object (DAO).

* The CallController class defines and implements the application API and maps each request to a service layer routine. 
* The CallService class implements all of the business logic of the application and executes actions on the DAO. 
* The CallDataAccessService implements the methods specified by the CallDAO interface and directly interacts with the MySQL repository.
* The Call class contains the model structure of each call and its persistence moduling

### Client

The Client was implemented as a simple python script that communicates with the web service API and its underlying components. 

### Deployment

The deployment of the MySQL database and the creation of the Spring Boot user profile is automated through the build_db.sh script.
