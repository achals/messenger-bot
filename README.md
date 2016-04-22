# messenger-bot
A simple (dumb?) messenger-bot for the [messenger platform](https://messengerplatform.fb.com/) in Java.

Uses [spring-boot](http://projects.spring.io/spring-boot/) and [OkHttp](http://square.github.io/okhttp/), and absolutely no XML!

## Running locally
Getting the server running locally is as simple as:
    mvn spring-boot:run

You can verify the service is up by navigating to [localhost:8080/bot/v1/health](localhost:8080/bot/v1/health).

## Actually working with the messenger platform
To get the service actually working with the messenger platform, you'll need a verification token and a page access key. More details [here](https://developers.facebook.com/docs/messenger-platform/quickstart).
The values are read from the System env, and should be used with the Keys `VALIDATION_TOKEN` and `ACCESS_TOKEN` respectively.

You can also specify `PORT` and the server will try to bind to that port.