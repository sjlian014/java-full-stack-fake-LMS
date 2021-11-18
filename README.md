# java-full-stack-fake-lms

repo for my uni CMPSC course project, a fake LMS stack from database to web server to GUI frontend implemented in pure java.

## project plan

  * [ ] three parts will make up the application:

1. database (Derby embedded)
2. server (Spring Boot)
3. GUI client (JavaFX)

## current progress

Just finished learning the basic concepts of a full stack application design and ready to try out what i have learned. 
I planned to work on the server side first - modeling data, building business logic, and designing the api - and followed by the UI part. 
I successfully deployed an in-memory derby db, a few http enpoints for testing out object serialization/deserialization, and started modeling some pojos that will be mapped into the database using JPA.
The server side is not expected to take long using the spring boot framework (for a basic skeleton that statified the project anyway), and after this I will start working on the UI code for db admin.
And depending on how much time I have after I've done the two aforementioned parts I might move on to implement some of the advanced features detailed in my initial report.

The source code in this repo is usually up-to-date and feel free to clone it and check it out on your local machine at any time.
