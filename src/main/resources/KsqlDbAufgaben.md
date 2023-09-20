## KSQLDB 1

### 1. Stream erstellen
Erstellen Sie einen Stream `bestellungen_stream` basierend auf einem Kafka Topic `bestellungen` im AVRO-Format mit folgenden Feldern:
- `bestell_id` vom Typ INT
- `produkt_name` vom Typ VARCHAR
- `preis` vom Typ DOUBLE

### 2. Daten einfügen
Fügen Sie die folgenden Testdaten in `bestellungen_stream` ein:
- Bestell-ID: 1, Produktname: 'Laptop', Preis: 899.99
- Bestell-ID: 2, Produktname: 'Handy', Preis: 499.99

### 3. Select-Abfragen
a. Führen Sie eine Pull Query aus, um Daten für `bestell_id=1` aus `bestellungen_stream` abzurufen.
b. Führen Sie eine Push Query aus, um kontinuierlich Daten aus `bestellungen_stream` zu streamen.

### 4. Einen neuen Stream ableiten
Leiten Sie einen neuen Stream `teure_bestellungen` aus `bestellungen_stream` ab, der nur Bestellungen mit einem Preis über 500€ enthält.

## KSQLDB 2
### 1. Aggregation von Bestellungen
Verwenden Sie den `bestellungen_stream` aus der vorherigen Aufgabe und aggregieren Sie die Anzahl der Bestellungen pro Produkt. Sie sollten dazu die Aggregationsfunktion `COUNT` verwenden. Speichern Sie das Ergebnis in `gesamtverkaeufe_tabelle`.

### 2. Abfrage der aggregierten Daten
a. Führen Sie eine Pull Query aus, um die gesamte verkaufte Menge für das Produkt 'Laptop' aus `gesamtverkaeufe_tabelle` abzurufen.
b. Führen Sie eine Pull Query aus, um die Produkte mit mehr als 10 Verkäufen aus `gesamtverkaeufe_tabelle` abzurufen.

