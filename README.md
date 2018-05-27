# Portfolio

> :de: Du willst mehr über meine Webseite und ihre Technologien wissen? [Schau hier](https://ketrwu.de/page/project-portfolio)

[My personal website](https://ketrwu.de) powered by
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
> Do this before starting with something else.

* In the root of this project:
    * `mvn clean install`

### Start the webdesign watcher
* In `webdesign`:
    * `npm run dev`

It'll build the `bundle.css` & `bundle.js` when you change something. 
These files getting transferred to the `server` static resources path.

### Intellij Idea Settings
This is a helpful thing to develop even faster: https://stackoverflow.com/a/35895848

It will automatically build changes in your Java code.

