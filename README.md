# 🏭 Progetto Mortadella: Simulazione Multi-thread

Questo progetto è un simulatore di una linea di produzione, ispezione e distribuzione alimentare sviluppato in **Java** con interfaccia grafica **JavaFX**. La simulazione affronta sfide avanzate di programmazione concorrente e gestione dei flussi di dati asincroni.

## 🚀 Caratteristiche Principali

- **Multi-threading**: Gestione parallela di Laboratori (Produttori), Ispettori e Distributori.
- **Configurazione Dinamica**: Caricamento delle impostazioni (numero laboratori, tempi, capacità) tramite file **JSON**.
- **UI Real-time**: Visualizzazione grafica sincronizzata dello stato dei magazzini e dei contatori globali.
- **Architettura Scalabile**: Sistema progettato per gestire carichi variabili senza colli di bottiglia.

## 🏗️ Architettura e Soluzioni Tecniche

Il progetto si distingue per alcune scelte architettoniche mirate alla stabilità e al disaccoppiamento:

### 1. Disaccoppiamento tramite UICallback
È stata definita un'interfaccia `UICallback` all'interno del Controller per separare nettamente la logica di business dalla grafica:
- I **Thread** invocano i metodi della callback (es. `onPut1()`, `onApprovata()`) per comunicare eventi.
- Il **Controller** implementa tali metodi gestendo l'aggiornamento grafico tramite `Platform.runLater()`, garantendo la thread-safety di JavaFX.

### 2. Gestione della Concorrenza
- **Buffer Limitati**: Utilizzo di `ArrayBlockingQueue` per implementare il pattern produttore-consumatore, gestendo automaticamente la contropressione.
- **Variabili Atomiche**: Uso di `AtomicInteger` per garantire incrementi sicuri dei contatori di produzione senza lock pesanti.

### 3. Graceful Shutdown (Chiusura a Cascata)
Per risolvere il problema dell'interruzione prematura dei processi, è stata implementata una **terminazione differenziata**:
- **Ispettori**: Terminano il ciclo solo quando la produzione è chiusa E il Magazzino 1 è completamente vuoto.
- **Distributori**: Terminano solo quando la produzione è chiusa E l'intera catena a monte (Magazzino 1 e 2) è vuota.
*Questo garantisce che l'ultimo prodotto inserito nel sistema venga processato e visualizzato prima della chiusura dei thread.*

## 🛠️ Tecnologie Utilizzate

- **Java 17+**
- **JavaFX**: Per la componente grafica.
- **Gson**: Per il parsing del file `config.json`.
- **Maven**: Per la gestione delle dipendenze.

## ⚠️ Sfide Tecniche Risolte

- **Reflection & Moduli**: Risolto il bug `InaccessibleObjectException` di Gson separando i modelli di dati (POJO) dalle classi che estendono `Thread`.
- **Sincronia Logica-UI**: Eliminazione dei "visual bug" durante lo shutdown attraverso il controllo combinato del flag `endOfProduction` e del metodo `isEmpty()` sui magazzini.

---

## 📦 Installazione (2 modi)

1. Clona il repository:
   ```bash
   git clone [https://github.com/tuo-username/progetto-mortadella.git](https://github.com/tuo-username/progetto-mortadella.git)
2. Scarica il file zippato, decomprimilo e aprilo tramite intellij o il tuo ambiente di sviluppo

## Importante: questo progetto usa maven quindi tutte le dependencies sono scritte dentro il file pom.xml insieme a tutto il resto come version control ecc... se ci sono problemi assicurarsi che non sia dannegiato e che sia corretto nel contenuto
