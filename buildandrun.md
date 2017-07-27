Steps for building and running the projects are as below. I am compiling the project on Windows machine. 


1. Download the code-collector and code-collector-test projects and import them into your eclipse workspace as existing maven projects.

2. The code-collector-test project is the project A referred to in the readme.md. Please check the App.java class that uses apache lang3 Maths library. Also, the pom.xml file has a dependency on commons-lang3 as expected. Compile the code-collector-test porject by right click -> run as -> Maven clean followed by right click -> run as -> Maven Install. 

OR use the maven commnad line as below: 

While in code-collector-test project's root directory, run
"C:\Program Files\Apache\apache-maven-3.2.2\bin\mvn" clean install
where C:\Program Files\Apache\apache-maven-3.2.2 is the directory where maven is installed.

3. Move the output of step 2 above from the <maven root .m2>\repository\code-collector\code-collector-test\0.0.1-SNAPSHOT\code-collector-test-0.0.1-SNAPSHOT.jar to another directory (say, directory A)
4. 

