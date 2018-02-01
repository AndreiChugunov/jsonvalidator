# Json Formatter Server

This is server for validation and formatting json files

## Get starting

For building and running image type script

### based maven

```
docker build -t validator github.com/AndreiChugunov/jsonvalidator
docker run -t --rm -p 8080:8080 validation-service
```

### based gradle

```
./gradlew clean
./gradlew buildImage
docker run -t --rm -p 8080:8080 validation-service
```

## Usage

Create json file with name `filename.json`

```
{"a":[1,2,3],"b":null}
```

Send this file to server
```
curl -s --data-binary @filename.json http://localhost:8080
```

Server return formatted file
```
{
    "a": [1, 2, 3],
    "b": null
}
```

## Errors

Error format have structure like
```
{
    "errorCode"  : 12345,
    "errorMessage" : ["verbose, plain language description of the problem with hints about how to fix it]",
    "errorPlace" : ["highlight the point where error has occurred"],
    "resource"   : ["filename"],
    "request-id" : ["the request id generated by the API for easier tracking of errors"],
}
```