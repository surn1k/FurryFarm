# SSAD Assignment 1: Farm WEB-Service

--------------
### Contributors team №26:
 - Surnachev Nikita
 - Gilvanov Ruslan
 - Lo Chichero Vaina Sofia Maria
 - Vyacheslav Sinii
 - Mark Esaian
 
-------------

 ## Description:
This is a SSAD course Assignment 1 project describing a Farmer WEB-service,
this application provides an opportunity for Farmers to have a direct communication with Dealers of Supplies via notifications. Production team have decided not to separate suppliers and retaillors as there is no principal difference between them from architecture point of view, so they was joided under Dealer class.

Link to UML: https://drive.google.com/file/d/1vA20Sz3uEuJsKUL96jJYjl4gEl0-OcUc/view?usp=sharing

Source code was designed with usage of the following patterns:

1. Singleton, class DataBase. This pattern was used for the unification of the database connections and the allownes of getting access to database from any point of the project, as the DataBase have only one instance of itself, this pattern provide and opportunity to bettter control requests to the inner tables which is also helpful for controlling multi thread accesses.
 
 2. Additional patterns:
      - Decorator, classes of Handler directory. Decorators of Hadler are used for changing the behaviour of handling the incoming requests. This pattern cahnges properties of mein handler during rentime which helps to make compact code for the various functionality of request handling.


## Backlog of work:
 1. For the work we created an UML diagram in the Intellijia idea, which was helpful for improting the code intrerface for the development. For UML diagram we have started for the DB planning and all the suclasses and entites. Than we decided to build diagrams for Server Backend, adding Handlers and decorator pattern to diagram. Than our team creted all the side planning for tools and utils, which will finished the plannig. For the code creation we follow the same order. We created and tested all the needed components of database, than we have swithced to Server realisation. When handlers and utils were done, we have created frontend part to make our peoject more presentanble. after this part we have created a report and submited the project. 

 ### Dependencies:
 - JAVA 8th generation  
 - JDBC 3.7.2  
 - GSON v.2.8.8  
 - open-GDK v.17  
 - jinjava v2.5.10
 
 ### Description of the code:
1. **Main** is a class that configures  and runs  server modules
2. **Util** [Do not need to be checked: contains helper functions to simplify business logic] tools for rendering and work with frontend of the WEB-service, requests, additional tools which are not included in main code logic.
3. **Handlers** backend classes for work with incoming requests. 
4. **DB** module organizing work with SQLite. **db.entity** describes entities that database keeps (Farmer, Dealer, Account, Subscription) and serialization for database objects.
5. **Notifications** implements Observer Pattern. It is required for sending messages via several channels: TelegramSubscriber, ConsoleSubscriber, and DBSubscriber  

------------
# Build and setup guide
 ## Preparation for run
 0. You can run test configuration using jar file in the directory or go to http://35.208.18.5:8000/login, where our prject is running
 1. clone git [repository](https://github.com/surn1k/FurryFarm.git "repo link") or dearchive zip archive that was provided in moodle
 2. The architecture have been tested **only** in **IntelliJ IDEA CE**, if you have troubles with build please use GeeekBrain software.
 3. After project have been cloned/downloaded and opened in intelliJ you may start the server with running Main.java file and open http://localhost:8000 in your browser.


## Test cases for checking:

 1. Try to register a new users in the service: At least one **Farmer** and **Dealer**, As **login for Farmer** use **your telegram alias**, be sure that you have send `/start` to `@furry_farm_bot` before start. 
 2. After registration you may enter to accounts of **Dealer** and **Farmer** with respective login data.
 3. Open **Farmer** account and choose the **Dealer** you have just created, then push subcribe button
 4. Login under Dealer account and create a new advertizment, then push button **Publish**, your telegram account will receive a notification with advertizment information.
 5. Login under **Farmer** one more time, and choose you **Dealer** again, push button "Stop Subcribing".
 6. Change you account to **Dealer** and publish new message, you should not receive the notificaton now.

7. Change your account to **Farmer** again and write your complaint. Than choose the **Dealer** and push send button, The chosen dealer and the admin of the server will recieve your complaint.(You may check this by making your login the same as your second telegram alias)

8. Great!!! You have finished checking, we believe that you found theese project good :)
 
