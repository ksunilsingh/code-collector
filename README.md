# code-collector
This project is a proof of concept for an idea on improving inclusion of open source code referenced by java projects. Currently when we use a java build system (such as maven or gradle), the build system downloads the jar files referenced by a java project. What I am proposing here is a replacement for that: 
1. Find out all the classes referenced by the classes in a java project (say, project A).
2. Download the source code for these dependencies recursively and include the downloaded source code in the source tree of project A.
3. Remove the dependency(ies) from project build configuration file (e.g. pom.xml) and build the project.

The resulting jar file would be smaller in size than if the full jar for the dependency is included as is.

These steps can be performed after coding and testing is complete for the project and we are ready to release. I have automated the steps 1 and 2 only in this proof of concept. Steps for building and running the projects are in the buildandrun.md file.

This could be a feature that can be included in build systems such as maven and gradle.

Please let me know what you think by sending an email to: ksunilsingh  aaaaaat gmail dot com
