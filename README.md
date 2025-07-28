## Wymagania wstępne

- Java 21
- Maven
- Docker
- Docker Compose

## Uruchomienie aplikacji

### 1. Uruchomienie usług w Docker Compose

```shell script
docker-compose up -d
```

### 2. Uruchomienie aplikacji Spring Boot

#### Zbudowanie aplikacji

```shell script
mvn clean package
```

#### Uruchomienie aplikacji

```shell script
java -jar application/target/application.jar
```

## Przykładowe wywołania API

### Utworzenie nowego konta

```shell script
curl -X POST http://localhost:8080/api/v1/accounts \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: unique-key" \
  -d '{"firstName": "Hub", "lastName": "Mas","plnBalance": 1000}'
```

### Pobranie informacji o koncie

```shell script
curl -X GET http://localhost:8080/api/v1/accounts/{accountId}
```

### Wymiana waluty na koncie

```shell script
curl -X POST http://localhost:8080/api/v1/accounts/{accountId}/exchange \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: unique-key" \
  -d '{"fromCurrency": "PLN", "toCurrency": "USD", "amount": 100}'
```

## Komentarz do zadania

W zadaniu miałem wątpliwość czy operacja walutowa powinna być między dwoma kontami czy na jednym koncie. 
Intuicyjnie myślałem o dwóch, jedno PLN, drugie USD, czyli jak normalnie w banku, ale uznałem, że z opisu bardziej wynika, że konto ma wiele walut (zakładanie konta zawsze z inicjalną kwotą w PLN). 
To miałoby zasadniczy wpływ na operację wymiany, ponieważ taka operacja koniecznie musiałaby być transakcyjna i dodałbym serwis domenowy, który agreguje operacje obciążenia i uznania na dwóch różnych kontach (jako jedna transakcja).

## Rozwiązania użyte w zadaniu
- DDD
- Architektura hexagonalna (porty i adaptery)
- FeignClient do komunikacji API NBP
- Cache NBP clienta (symboliczny, raczej żeby pokazać potrzebę, w produkcyjnym kodzie bym uwzględnił niską częstotliwość aktualizowania kursów w NBP)
- Optimistic locking przy zapisywaniu aktualizacji balansu konta
- testy jednostkowe do wybranych klas (junit, mockito, mockmvc)
- w głównym katalogu załączona kolekcja żądań do Postmana

## Co można by poprawić
- testy integracyjne dla API z użyciem PostgreSQL w Testcontainers 
- testy integracyjne warstwy reposytoriów z @DataJpaTest

W razie jakichkolwiek pytań czy chęci dyskusji jestem otwarty na rozmowę, pozdrawiam!

