# Prime Number REST API implemented with AKKA-HTTP
This application exposes an API to retrieve a JSON-formatted list of prime numbers that exist before and inclusive of a given integer.
The prime numbers list can be retrieved through two different algorithms.

## To run
This application is currently configured to run on port 8002. To run this application you need SBT installed, and then when within the directory of the repository enter the following command:
```sbt run```

## To test
To test this application an installation of sbt is also required. From the shell you then will run the following command:
```sbt test```

## To access
This application's end-points can be accessed through the following address:
```https://whispering-wave-47474.herokuapp.com```

*(i.e ```https://whispering-wave-47474.herokuapp.com/prime/algoA/50```)*

## API

### GET /prime/:algorithm/:limit

Takes in two string parameters. The first indicates the algorithm to be used for the computation, the second the
limit at which primes should stop being computed.

Supported algorithm strings:

`algoA` - computers the primes by filtering a pre-computed (i.e non-lazily evaluted) list (of limit length) with a predicate that determines whether an element is a prime or not

`algoB` - takes from an infinite stream of prime numbers (i.e lazily evaluated) a finite number of primes by filtering with a predicate where the elements must be less than the limit supplied

Responds with a JSON document containing a list of the prime numbers computed.

Example response:
```json
    {"primes": ["2",
               "3",
               "5",
               "7",
               "13"]}
```
Error codes:
+ 400 BadRequest this can be triggered by either supplying an invalid algorithm string or supplying a very large or non-numerical limit.

### GET /prime/:limit

This end-point simply takes in a limit as the string parameter. It defaults the algorithm through which the primes will be retrieved to ```algoA```.

Example response:
```json
    {"primes": ["2",
               "3",
               "5",
               "7",
               "13"]}
```
Error codes:
+ 400 BadRequest this can be triggered by either supplying supplying a very large limit or a non-numerical limit.
