//

Il diagramma e i test possono essere visualizzati in /diagram

Sulla base della consegna sono state create tre entità principali: Utente, Evento e Prenotazione.

La tabella utenti contiene le informazioni essenziali per l'autenticazione: email, password cifrata e ruolo. L'email è
stata resa univoca e viene utilizzata anche come username in fase di login. La password non viene mai salvata in
chiaro, ma viene memorizzato solo l'hash generato tramite BCrypt.

La tabella eventi contiene i dati principali dell'evento. Ogni evento ha anche un riferimento all'organizzatore tramite
la colonna organizzatore_id. Un organizzatore può modificare o cancellare solo gli eventi creati da lui.

La tabella prenotazioni collega utenti ed eventi. È stata modellata come tabella separata perché un utente può prenotare
più eventi e un evento può avere più utenti prenotati. Inoltre è stato aggiunto un vincolo di unicità sulla coppia
utente_id ed evento_id, in modo da impedire allo stesso utente di prenotare due volte lo stesso evento.

Gli eventi prenotabili (eventi/prenotabili) vengono filtrati considerando due condizioni: devono essere eventi futuri e
devono avere ancora posti disponibili. I posti disponibili non vengono salvati direttamente nel database, ma vengono
calcolati sottraendo al numero totale di posti il numero di prenotazioni già effettuate. Questa scelta evita di
duplicare informazioni e riduce il rischio di avere dati incoerenti. Gli eventi prenotabili vengono inoltre restituiti
ordinati per data crescente, quindi dal più vicino al più lontano. Questo rende la risposta più utile per l'utente
finale. Inoltre se un organizzatore prova a ridurre i posti di un evento sotto il numero delle prenotazioni effettive
viene lanciato un errore. La cancellazione dell'evento da parte dell'organizzatore è invece a cascata.

È stata implementata anche la funzionalità extra richiesta: l'utente può visualizzare le proprie prenotazioni (
/prenotazioni/me ) e può annullarle. L'annullamento è permesso solo al proprietario della prenotazione e solo per
prenotazioni che non sono già passate.

//