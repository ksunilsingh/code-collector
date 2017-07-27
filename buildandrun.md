Steps for building and running the projects are as below. I am compiling the project on Windows machine. 


1. Download the code-collector and code-collector-test projects and import them into your eclipse workspace as existing maven projects.

2. The code-collector-test project is the project A referred to in the readme.md. Please check the App.java class that uses apache lang3 Maths library. Also, the pom.xml file has a dependency on commons-lang3 as expected. Compile the code-collector-test porject by right click -> run as -> Maven clean followed by right click -> run as -> Maven Install. 

OR use the maven commnad line as below: 

While in code-collector-test project's root directory, run from command line
"C:\Program Files\Apache\apache-maven-3.2.2\bin\mvn" clean install
where C:\Program Files\Apache\apache-maven-3.2.2 is the directory where maven is installed.

3. Move the output of step 2 above from the <maven root .m2>\repository\code-collector\code-collector-test\0.0.1-SNAPSHOT\code-collector-test-0.0.1-SNAPSHOT.jar to another directory (say, directory dir1)

4.  Generate a single jar file with all the dependencies included:

"C:\Program Files\Apache\apache-maven-3.2.2\bin\mvn" clean compile assembly:single

where C:\Program Files\Apache\apache-maven-3.2.2 is the directory where maven is installed.

5. Check the test file in the code-collector project under src/test/java and modify the three static variables (pathToProjectToBeCollectedJar, pathToProjectRoot, and pathToProjectToBeCollectedJarWithDependencies) to point them to the correct directory.

6. Compile the code-collector porject by right click -> run as -> Maven clean followed by right click -> run as -> Maven Install. 

OR use the maven commnad line as below: 

While in code-collector-test project's root directory, run
"C:\Program Files\Apache\apache-maven-3.2.2\bin\mvn" clean install
where C:\Program Files\Apache\apache-maven-3.2.2 is the directory where maven is installed.

This will run the test and download the required source code for apache math library into the code-collector-test project's src/main/java directory.

7. Comment out the commons-lang3 dependency from code-collector-test's pom.xml and then run from command line
"C:\Program Files\Apache\apache-maven-3.2.2\bin\mvn" clean install
where C:\Program Files\Apache\apache-maven-3.2.2 is the directory where maven is installed.

8. Compare the jar generated in step 7 (166 KB) with the one in step 4 (480 KB).


Thanks for following the steps. I know it is somewhat cumbersome but that is why it is a proof of concept. 

Please let me know if it didn't work or you have questions by sending an email to ksunilsingh attt gmail dottt commm.


