import javaobj

# Specifica il percorso del file serializzato in Java
file_path = 'file.txt'

# Apri il file in modalità binaria
with open('RedeemedRewardsMap.txt', 'rb') as file:
    # Decodifica il contenuto usando javaobj
    dati = javaobj.load(file)

# Controlla se 'dati' ha un metodo o proprietà 'items' per iterare su chiavi e valori
if hasattr(dati, 'items'):
    # Itera sugli elementi della mappa e stampa chiavi e valori
    print("Contenuto della HashMap deserializzata:")
    for chiave, valore in dati.items():
        print(f"Chiave: {chiave}, Valore: {valore}")
else:
    # Se non ha 'items', esplora l'oggetto per capire la struttura
    print("L'oggetto deserializzato non sembra avere un metodo 'items'.")
    print("Struttura dell'oggetto:", dir(dati))
    print("Oggetto:", dati)

    # In alcuni casi, i dati possono essere accessibili attraverso altri attributi
    # Prova ad esplorare i campi e gli attributi con ulteriori print, se necessario.
