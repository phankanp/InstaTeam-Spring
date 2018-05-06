A project management software. This application uses Spring boot, Hibernate, Apache DBCP, and H2 database to create a Java web application. The application lets users create projects, add roles to a project (e.g. developer or designer), assign collaborators, and manage the project.

# Steps

- Create a Gradle project. Add dependencies for Spring Boot with Thymeleaf, Spring ORM, Hibernate, Apache DBCP, and H2. Create the directory and package structure of the application. Save all static assets into the proper project directory.
- Create the configuration files for Hibernate and an H2 database connection.
- Create a Java class for starting the application as a Spring Application and a Spring configuration class with two @Bean methods:
  - Method for initializing a LocalSessionFactoryBean
  - Method for initializing a DataSource
- Create the Role model class, which represents the roles each project could contain, and that need to be filled. Each role will have the following pieces of information associated with it:
  - id: auto-generated numeric identifier to serve as the table&#39;s primary key
  - name: alphanumeric, reader-friendly name to be displayed. Example role names might be &quot;developer&quot;, &quot;designer&quot;, or &quot;QA engineer&quot;. This is a required field for data validation.
  - Getters and setters for all fields
  - Default constructor
- Create the Collaborator model class, which represents a person who is a candidate for working on any given project. Each collaborator should contain the following:
  - id: auto-generated numeric identifier to serve as the table&#39;s primary key
  - name: first and last name of the collaborator. This is a required field for data validation.
  - role: the single Role object that represents this collaborator&#39;s skill. For proper data association, it&#39;s important to keep in mind that there could be _many_ collaborators associated with any _one_ role. This is a required field for data validation.
  - Getters and setters for all fields
  - Default constructor
- Create the Project model class, which represents a project for which a project manager is seeking collaborators. Each project should contain the following:
  - id: auto-generated numeric identifier to serve as the table&#39;s primary key
  - name: alphanumeric, reader-friendly name to be displayed. This is a required field for data validation.
  - description: longer description of the project. This is a required field for data validation.
  - status: alphanumeric status of the project, for example &quot;recruiting&quot; or &quot;on hold&quot;
  - rolesNeeded: collection of Role objects representing all roles needed for this project, regardless of whether or not they&#39;ve been filled. For proper data association, keep in mind that there could be _many_ projects that contain _many_ Role objects. That is, each project can have many roles that it needs, and each role can be needed by many projects.
  - collaborators: collection of Collaborator objects representing any collaborators that have been assigned to this project. For data association, use the fact that there could be _many_ projects that contain _many_ Collaborator objects. That is, each project can have many collaborators, and each collaborator can work on many projects.
  - Getters and setters for all fields
  - Default constructor
- Add JPA annotations to all model classes.
- Create a DAO interface and implementation for each model class.
- Create a service interface and implementation for each model class.
- Create the Role Controller and Thymeleaf views necessary for viewing, adding, and editing roles.
- Create the Collaborator Controller and Thymeleaf views necessary for viewing, adding, and editing collaborators.
- Create the Project Controller and Thymeleaf views necessary for viewing, adding, and editing projects, without including the ability to assign each role to a specific collaborator.
- Add the methods to Project Controller, and the Thymeleaf views necessary for assigning and unassigning collaborators to and from a project&#39;s needed roles.
- Extract the common code of each DAO implementation to an abstract class that the DAO implementations extend.
- Add the ability to delete projects, roles, and contractors and ensure data integrity for all relationships. For example, when a collaborator is deleted, make sure that all roles previously assigned to this collaborator become unassigned.
- Include a start date on projects, and sort chronologically by start date on the project index view.
