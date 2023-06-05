# GitHubGetRepositoriesTask

Simple application that allow user to get list of not forked GitHub 
user repositories, branches and last commits using user login. 

## Usage
Application have single endpoint (controller address was left empty):
```
"/get_user_github_repositories"
```
that requires param "login" with value being login of account that we want to check.

## Some explanation about the decision-making

Here I will shortly explain some decisions that I made while writing this app.
First, I decided to use construction based on ResponseEntityExceptionHandler, as it allowed me to 
change structure of response entities, based on what I needed. The only thing
I added that wasn't the part of initial task, was one additional exceptions' handler:

````java
  @ExceptionHandler(InternalApplicationException.class)
    public ResponseEntity<ExceptionResponseDto> internalApplicationExceptionHandler() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new ExceptionResponseDto(500, ErrorMessagesEnum.INTERNAL_SERVER_ERROR.value),headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

````
Its added as universal exception to close some program parts, for instance,
when media type is neither xml, nor json. Also, the part of code:

````java
   HttpHeaders headers = new HttpHeaders();
   headers.setContentType(MediaType.APPLICATION_JSON);
````
secures application ability to use ExceptionHandler in situations mentioned above. I know that app still have
holes in therms of possible errors but ive tired to not make the project too bloated.

I couldn't figure out how to filter out forked repositories using api parameters
(ive seen on stack that it can be done, but I probably was doing something wrong), 
so I opted for just filtering it using stream:
````java
 repositoryDtoList = repositoryDtoList.stream().filter(e -> !e.getFork()).collect(Collectors.toList());
````
As I never needed to figure out headers (The only time I was
 doing something with them was to add/read JWT), I asked AI on how to approach this problem.
The solution of just looping through headers worked well, and it was also most recommended on stackoverflow
(AI learned from the best).

There is an additional mapper present in this project that makes output data more readable, because for me
,after reducing data to one defined in the instruction, it was really messy and hard to read (mostly because of 
how different parts of api present the data).

# Tests

Most of the tests were made in postman, but there is json file copied in the test resources,
because of low amount of requests that non-authorized users can send to the api (in early development,
I simply made a mock of the data using this json file), There is also simple controller test that I made to verify visually
if output of mappers is proper (using sout and counting objects).

