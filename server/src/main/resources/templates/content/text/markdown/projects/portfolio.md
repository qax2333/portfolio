---
title: Projekt - Portfolio
headline: Portfolio
description: Meine eigene kleine Spielwiese umgesetzt mit Spring Boot. Inklusive Kontaktformular und eigenem Captcha.
tags: 
  - project
project:
  languages:
    - Kotlin
    - HTML
    - Sass
    - TypeScript
---
Mit Portfolio will ich mir eine eigene kleine Spielwiese schaffen und Sachen ausprobieren, die ich schon länger ausprobieren wollte.

### Technologien, Frameworks und Libraries

Bei meiner Webseite kommen gleich einige Technologien zusammen:

* Kotlin
* Maven
* Spring Boot
* Thymeleaf
* Markdown
* Webpack
    * Sass
        * [Bootstrap 4](https://getbootstrap.com/)
        * [Fontawesome 5](https://fontawesome.com/)
        * [animate-scss](https://github.com/geoffgraham/animate.scss)
    * TypeScript
        * [jQuery](https://jquery.com/)
        * [parallax.js](https://github.com/wagerfield/parallax)

Da ich Java hauptsächlich und gerne für alles nutze, nutze ich Java natürlich auch hier.
Besser gesagt habe ich das Projekt später von Java in Kotlin übersetzt. Ich möchte mich mehr mit Kotlin
auseinandersetzen, daher hab ich auch noch keine Übung was Best-Practices in Kotlin angeht.
Außerdem wollte ich gerne Spring Boot verwenden.<br />
Das Spring Framework ist ein mächtiges Werkzeug und aus meiner Java-Welt nicht mehr wegzudenken.
Ich bin zwar immer noch dabei alles an Spring auszukundschaften und entdecke immer wieder Neues,
habe schon sehr viel Spaß mit dem was ich schon entdecken konnte.

Dies ist mein erstes Projekt mit Kotlin und ich hatte einige Anfangsschwierigkeiten und bin mir unsicher,
was den Code-Style angeht. Allerdings bin ich mir sicher, dass einiges viel eleganter lösbar wäre.
Daher brauche ich hier wohl noch einiges an Zeit und Arbeit.

Den kompletten Build hält Maven zusammen. Sowohl Backend als auch das Webdesign.<br />
Hier kommen natürlich auch noch einige Libraries zusammen, aber eigentlich nichts spezielles oder
besonderes. Für Kotlin musste ich nachträglich aber noch einige Libraries und Plugins hinzufügen.
Das [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin)
ist vielleicht noch erwähnenswert. Damit baue ich das Webdesign zusammen. Es kann eine bestimmte
Node und NPM Version runterladen und damit den Webpack Build ausführen.<br />
So ist das komplette Paket mit einem Build-Befehl gebaut.

Thymeleaf wollte ich ebenfalls gerne mal ausprobieren und bin bisher sehr begeistert.
Sonst habe ich nur JSP verwendet, aber mal nach links und rechts schauen kann nie schaden.
Thymeleaf hat nette Ansätze und mit dem verwendeten
[Layout Dialect](https://github.com/ultraq/thymeleaf-layout-dialect)
macht das Zusammenstecken der einzelnen Webseiten-Fragmente echt Spaß.

Alles folgende wird von Webpack zusammen gebaut, uglyfied und minified.<br />
Webpack nutze ich momentan am liebsten. Ich finde die Konfiguration gegenüber anderer Tools etwas
eingängiger, aber so komplex ist der Build auch nicht.

Mit Sass wurde ich relativ schnell warm und es macht das Styling der Webseite um einiges angenehmer.
Außerdem kommen hier noch einige Frameworks und Libraries zum Einsatz, die das Gestalten noch einfacher machen.<br/>

Mit Bootstrap hab ich schon einige Erfahrungen gemacht und ich nutze es immer wieder gern als Basis,
um daraus wieder eigene Webdesigns zu basteln, so wie hier auch.<br />

Wer Icons braucht, braucht Fontawesome. Manche Icons finde ich zwar etwas "overused", weil Fontawesome schon
lange kein Geheimtipp mehr ist, nichtsdestotrotz eine sehr geliebte Quelle wenn ich mal Icons brauche.<br/>

Für die kleinen, nicht ganz so subtilen Animationen hier, ist [animate-scss](https://github.com/geoffgraham/animate.scss) zuständig.
Beziehungsweise [animate.css](https://github.com/daneden/animate.css), von dort
kommen die Animationen eigentlich. Animate-SCSS ist nur eine Sammlung an Sass Mixins, um diese Animationen einfacher
in seine bestehenden Sass Styles einzubauen. Sehr praktisch.

Ich bin kein großer JavaScript Fan, auch wenn ich die Möglichkeiten und Ergebnisse sehr beeindruckend finde.<br />
Gerne selber JavaScript schreiben, tue ich allerdings nicht. <br />
Daher nutze ich gerne TypeScript, welches am Ende auch nur als JavaScript rauskommt. Es bietet einen Hauch
von Typen-Sicherheit und einen mehr oder weniger Objekt-orientierten Ansatz. Auf meiner Webseite kommt
TypeScript eigentlich nicht wirklich zum Einsatz. Den JavaScript Zauber der hier passiert, habe ich anderen
zu verdanken.<br />

Sagte ich anderen? Zum Beispiel jQuery. Mein "must-have". Auch wenn ich es hier nicht wirklich viel nutze
macht es doch alles angenehmer wenn es zB. um Events geht.<br />

Das wohl auffälligste auf meiner Seite ist der parallaxe Hintergrund. Den habe ich parallax.js zu verdanken.
Das wollte ich auch schon länger mal ausprobieren und ich bin zufrieden. Die Hintergrund Grafiken habe ich
in Adobe Illustrator und Photoshop zusammen gebaut. Inspiriert vom Flat-Design von
[Kurzgesagt](https://www.youtube.com/user/Kurzgesagt).

Letztlich gebaut wird das Projekt von meinem Jenkins-Server. Nach dem Bau wird das Projekt auch direkt
hier deployed. Ich muss quasi keinen Finger mehr rühren, was das Entwickeln nochmal angenehmer macht.

### GitHub
Natürlich ist das Projekt auf meinem GitHub Profil zu finden:
https://github.com/KennethWussmann/portfolio
