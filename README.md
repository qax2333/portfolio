# Portfolio

> :de: Du willst mehr über meine Webseite und ihre Technologien wissen? [Schau hier](https://kenneth.wussmann.net/page/project/portfolio)

[My personal website](https://kenneth.wussmann.net) powered by

* Kotlin
* Spring Boot & Thymeleaf
* SCSS & TypeScript

Basically, it's just a webserver serving HTML. But it also has a contact form which sends e-mails and a crappy CAPTCHA, because I didn't want to use reCAPTCHA.

## Modules
It's splitted in two modules

* `server` = Contains all the backend stuff (It also serves the pages)
* `webdesign` = Contains the frontend design stuff

## Development
Spring-devtool will do a warm restart if you change something. It's like JRebel but not that powerful.
Thymeleaf (HTML) changes will also be reloaded on the server in no time.
Sadly, webdesign changes only take effect after Intellij Idea: `Build` -> `Rebuild project`. Maybe I'll find a better solution for that.

### Build

* In the root of this project:
  * `mvn clean install`
* To also clean webdesign dependencies:
  * `mvn clean install -Pfrontend-clean`

### Start the webdesign proxy
During development you'll need a proxy that serves the written SCSS/JS on the fly:

* In `webdesign`:
  * `npm run serve`

It'll start a webserver on `http://localhost:4000`:
* `/**` = Will proxy to port `8080`. Start the `server` on this port
* `/static/bundle.*` = Will serve the latest built SCSS/JS

Changes made to SCSS/JS will automatically build and update in your browser.
