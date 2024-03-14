# Aplikacja do Grupowego Podejmowania Decyzji (AHP)

Celem tego projektu jest stworzenie aplikacji umożliwiającej grupowe podejmowanie decyzji z wykorzystaniem metody AHP (Analytic Hierarchy Process) - metody porównywania parami.

## Opis
Aplikacja ma służyć do wspólnego podejmowania decyzji, gdzie eksperci mogą porównywać parami alternatywy oraz kryteria, a następnie wygenerować ranking oparty na metodyce AHP. Do tego celu wykorzystywana jest metoda EVM do tworzenia rankingów oraz metody AIR do grupowania osądów pochodzących od różnych ekspertów.

## Funkcje
- Użytkownik może korzystać z tzw. skali fundamentalnej lub definiować własne skale porównań.
- Aplikacja działa w architekturze klient-serwer, gdzie komunikacja odbywa się poprzez RESTful API z wykorzystaniem Spring Boot.
- Facylitator może:
  - Tworzyć nowe rankingi.
  - Definiować nowe alternatywy i kryteria.
  - Określać parametry rankingu, takie jak skala pomiarowa i kolejność pytań.
- Eksperci mogą:
  - Odpowiadać na pytania o porównanie parami alternatyw.
  - Odpowiadać na pytania o porównanie parami kryteriów.
  - Zatwierdzać swoje odpowiedzi.

## Technologie
- Część serwerowa oparta jest o:
  - Spring Boot (Java)
  - MySQL
- Część kliencka:
  - React

## Przykładowy Scenariusz Użycia
### Facylitator
1. Tworzy nowy ranking.
2. Definiuje nowe alternatywy.
3. Definiuje kryteria.
4. Określa parametry rankingu, takie jak:
   - Skala pomiarowa.
   - Kolejność zadawanych pytań.

### Eksperci
1. Odpowiadają na pytania o porównanie parami alternatyw.
2. Odpowiadają na pytania o porównanie parami kryteriów.
3. Naciskają guzik "submit", aby zakończyć ocenę.

### Facylitator
1. Sprawdza zebrane wyniki.
2. Nadzoruje wykonanie rankingu.
3. Rozsyła wyniki uczestnikom procesu oraz zainteresowanym decydentom zewnętrznym.

