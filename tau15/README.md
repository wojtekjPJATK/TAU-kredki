# Przykład Karma+Jasmine


## Materiały

 * Karma - [karma-runner](https://karma-runner.github.io/2.0/index.html)
 * Jasmine - [jasmine](https://jasmine.github.io/)
  * [Firs Jasmine Suite](https://jasmine.github.io/tutorials/your_first_suite)


## Inicjalizacja i początek

```bash
npm init
npm install -s karma-browserify
npm install --save-dev watchify
npm install --save-dev jasmine
npm install --save-dev karma
npm install --save-dev karma-jasmine karma-<browser>-launcher
# Internet Explorer # npm install --save-dev karma-jasmine karma-ie-launcher
# Firefox # npm install --save-dev karma-jasmine karma-firefox-launcher
# Chrome # npm install --save-dev karma-jasmine karma-chrome-launcher
```

Dodajmy jQuery:

```bash
npm install --save jquery
```

Logi:

```bash
npm install --save-dev karma-verbose-reporter
```

Inicjalizacja karmy:

```bash
karma init
```

Uwaga: nie potrzebujemy Require.js

### Uruchamianie programów z paczek npm

Uwaga - powyższe nie zadziała tak od razu. Należy albo zainstalować w systemie pakiet karma (nie możemy tego zrobić na laboratorium), albo uruchamiamy z pakietów zainstalowanych w projekcie. Oto jak to zrobić:

```bash
function npm-do { (PATH=$(npm bin):$PATH; eval $@;) } # funkcja powłoki
# npm-do karma ...
```

## jQuery

Chcielibyśmy zrobić małą wtyczkę do jQuery. Będziemy potrzebowali pakietu, który nam pozwoli w łatwy sposób używać tej biblioteki w środowisku testowym.

```bash
npm install --save-dev jasmine-jquery
```

## Ustawienia w karma.conf.js

Należy ustawić dołączone pliki js. W szczególności chcielibyśmy dołączyć bibliotekę jQuery. Poszukaj w node_modules pliku z biblioteką jQuery. Dodaj to do ścieżek w karma.conf.js. Pamiętaj że kolejność ładowania ma znaczenie.