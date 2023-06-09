## Github Resume

This project is a Java Spring Boot application that uses the Github API to fetch the details of a github account The application retrieves the github account details by the accountName and also in required media formats (JSON,XML).
### Requirements
To run this project, you need:

* Java 8 or later
* Maven 3.2 or later
* Git

## Getting Started

### Clone the repository
```bash
 git clone https://github.com/MeghaMehrotra/github-resume.git
```

### Navigate to project directory

```bash
cd github-resume
```

### Build the application using Maven

```bash
mvn clean install
```
### Run the application 

Either run 
```bash
mvn spring-boot:run
```
OR 

```bash
java -jar target/github-resume-0.0.1-SNAPSHOT.jar
```
## Usage

Once the application is running, you can access the following endpoint http://localhost:8080/resume?name={accountName}&mediaType={mediaType}
to fetch details of accountName with response type as mediaType:


The endpoint accepts the following query parameters:

* **name** (required ) - the github accountName for which details are expected
*  **mediaType** (optional, default: "json") - the response format expected for the details,There are two options only XML or JSON

## Example Request

```bash
http://localhost:8080/resume?name=test&mediaType=xml
```
## Example Response
```bash
{
    "responseMessage": "Github Resume fetch success!!",
    "status": true,
    "body": {
        "login": "test",
        "url": "https://api.github.com/users/test",
        "repos_url": "https://api.github.com/users/test/repos",
        "repos": [
            {
                "name": "HelloWorld",
                "html_url": "https://github.com/test/HelloWorld",
                "description": "Create hello world",
                "language": null,
                "size": 136
            },
            {
                "name": "rokehan",
                "html_url": "https://github.com/test/rokehan",
                "description": null,
                "language": "PHP",
                "size": 9035
            },
            {
                "name": "SDWebImage",
                "html_url": "https://github.com/test/SDWebImage",
                "description": "Asynchronous image downloader with cache support with an UIImageView category",
                "language": "Objective-C",
                "size": 3379
            },
            {
                "name": "sNews",
                "html_url": "https://github.com/test/sNews",
                "description": null,
                "language": null,
                "size": 48
            },
            {
                "name": "Test--01",
                "html_url": "https://github.com/test/Test--01",
                "description": null,
                "language": "Matlab",
                "size": 120
            }
        ],
        "public_repos": 5,
        "languagePercentages": {
            "Objective-C": 26.56864286837553,
            "PHP": 71.04104418933794,
            "Matlab": 0.9435445824815222
        }
    }
}
```

## Testing
To run the tests, execute the following command in the project root directory:

```bash
mvn test
```

This will run the unit tests for the GithubRepoController, GithubRepoService, and UrlBuilderService classes.

### Built With
Java
Spring Boot
Spring Web
JUnit

