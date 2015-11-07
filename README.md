# clj-suomi [![Circle CI](https://circleci.com/gh/metosin/clj-suomi.svg?style=shield)](https://circleci.com/gh/metosin/clj-suomi) [![Dependencies Status](http://jarkeeper.com/metosin/clj-suomi/status.svg)](http://jarkeeper.com/metosin/clj-suomi)

[![Clojars Project](http://clojars.org/metosin/clj-suomi/latest-version.svg)](http://clojars.org/metosin/clj-suomi)

A Clojure library designed to access Finnish code sets.

[**API Docs**](http://metosin.github.io/clj-suomi/)

## Features

- Requires: Java 1.8
    - Uses `java.time.LocalDate`

### Posti - Finnish postal codes code set

Downloads postal codes from Posti FTP server and parses the fixed length
text fields to Clojure maps.

- [Posti postal code data](http://www.posti.fi/yritysasiakkaat/laheta/postinumeropalvelut/postinumerotiedostot.html)
- [Service description and terms](http://www.posti.fi/liitteet-yrityksille/ehdot/postinumeropalvelut-palvelukuvaus-ja-kayttoehdot.pdf)

## TODO

- [ ] Municipality codes
- [ ] Business ID (y-tunnus), Personal Identification Number (hetu) validators
    - Possibly as separate package and possibly including [Schema](https://github.com/Prismatic/schema)
    predicates

## License

Copyright © 2015 [Metosin Oy](http://metosin.fi)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
