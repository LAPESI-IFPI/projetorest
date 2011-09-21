PROJETO REST
============

Importing this project on eclipse
---------------------------------

In order to import this project on Eclipse, you'll need:

- choose menu file/import existent projects
- choose the directory that contains projetorest (the directory in which you decompressed it)
- after importing, if there is  a classpath problem it means you dont have Apache Tomcat 6 configured. You
     can just configure a new server in window/preferences/servers, creating a new instance, and add the Server Runtime
     as a library inside the project build path
- just right click on the project, run as, run on server. choose a servlet container or configure a new one
- done! check the log files. access http://localhost:8080/projetorest/ (or change 8080 to the appropriate port).

Importing this project on netbeans 6.8
--------------------------------------

- choose menu File >> Open Project
- choose the directory that contains projetorest
- right click on project and choose Run
- There might be a "It Works! VRaptor" message on the browser that has just opened. 

Running this project on apache tomcat
-------------------------------------

- Run "ant" command to generate "projetorest.war"
- Copy this file to webapps directory on tomcat
- Copy the "opala.conf" file to tomcat root directory
- Run apache and check the url: http://localhost:8080/projetorest/
