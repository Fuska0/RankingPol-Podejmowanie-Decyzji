# Aplikacja do Grupowego Podejmowania Decyzji (AHP)

Celem tego projektu jest stworzenie aplikacji umożliwiającej grupowe podejmowanie decyzji z wykorzystaniem metody AHP (Analytic Hierarchy Process) - metody porównywania parami.

## Funkcje Aplikacji
1. Wykorzystanie metody EVM (Eigenvalue Method) do tworzenia rankingów.
2. Implementacja odpowiednich metod AIR (Aggregated Individual Rankings) do grupowania osądów pochodzących od różnych ekspertów.
3. Możliwość korzystania z tzw. skali fundamentalnej oraz definiowania własnej skali porównań przez użytkownika, w tym bezpośrednio skali numerycznej.
4. Działanie w architekturze klient-serwer.

## Komunikacja Klient-Serwer
- Komunikacja pomiędzy częścią kliencką a serwerową odbywa się przy użyciu RESTful API.

## Technologie
- Implementacja części serwerowej opiera się na środowisku Java oraz wykorzystuje bazę danych MySQL.
- Część kliencka może wykorzystywać różne technologie, preferowanym wyborem jest:
  - WWW: React

## Przykładowy Use Case
Poniżej przedstawiono przykładowy scenariusz działania, który aplikacja powinna umożliwiać:

### Facylitator
1. Tworzy nowy ranking.
2. Definiuje nowe alternatywy.
3. Definiuje kryteria.
4. Określa parametry rankingu, takie jak:
   - Skalę pomiarową.
   - Kolejność zadawanych pytań (losowa / konkretna).

### Eksperci
1. Odpowiadają na pytania dotyczące porównania parami alternatyw (na jednym ekranie jedno pytanie porównania dwóch alternatyw).
2. Odpowiadają na pytania dotyczące porównania parami kryteriów (podobnie, na jednym ekranie jedno porównanie).
3. Naciskają guzik "submit", informujący system, że ocena z ich strony została zakończona.

### Facylitator
1. Sprawdza zebrane wyniki.
2. Nadzoruje wykonanie rankingu.
3. Rozsyła wyniki uczestnikom/ekspertom procesu oraz zainteresowanym decydentom zewnętrznym.

