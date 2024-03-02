package GreenHub;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GreenHub {

	private static Scanner in;
	private static ArrayList<ChargingRate> chargingRateList = new ArrayList<ChargingRate>();
	private static ArrayList<EnergySupplier> energySupplierList = new ArrayList<EnergySupplier>();
	private static ArrayList<ChargingStation> chargingStationList = new ArrayList<ChargingStation>();
	private static ArrayList<Reward> rewardList = new ArrayList<Reward>();
	private static ArrayList<User> userList = new ArrayList<User>();
	private static ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private static ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

	public static void main(String[] args) throws IOException {

		System.out.println("-------------BENVENUTO IN GREENHUB-------------");
		System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
		System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo "
				+ "l'apposita opzione, altrimenti verranno perse.");

		readAll();

		in = new Scanner(System.in);
		boolean adminMode = false;
		int scelta, i;

		System.out.print("Se non sei registrato, inserisci 0 per registrarti, altrimenti inserisci il tuo nome utente: ");
		String currentUsername = in.next();

		if (currentUsername.equals("0")) {
			System.out.println("Benvenuto al processo di registrazione per GreenHub!");

			User newUser = new User();
			System.out.print("Inserisci il tuo nome: ");
			newUser.setName(in.next());
			System.out.print("Inserisci il tuo cognome: ");
			newUser.setSurname(in.next());

			boolean nameOk = false;
			String username;
			do {
			    System.out.print("Scegli ora il tuo username: ");
			    username = in.next();
			    nameOk = true; // Imposta il flag su true di default, se non viene trovato un username uguale

			    for (User u : userList) {
			        if (u.getUsername().equals(username)) {
			            System.out.println("L'username inserito non è disponibile!");
			            nameOk = false; // Se trova un username uguale, imposta il flag su false
			            break; // Esci dal ciclo perché hai già trovato un match
			        }
			    }
			} while (!nameOk);

			newUser.setUsername(username);
			System.out.println("Se hai un veicolo elettrico, inserisci 0");
			System.out.println("Se hai un veicolo a combustione interna, inserisci 1");
			System.out.println("Se non hai un veicolo e vuoi usufruire del car sharing, inserisci 2");
			System.out.print("Scelta: ");
			newUser.setType(in.nextInt());
			newUser.setGreenPointsBalance(0);
			userList.add(newUser);

			// Aggiungi il veicolo dell'utente solo se ha selezionato di averlo
			if (newUser.getType() != 2) {
				System.out.println("Utente registrato correttamente! Andiamo ora ad aggiungere il tuo veicolo.");

				Vehicle newVehicle = new Vehicle();
				System.out.print("Inserisci la marca: ");
				newVehicle.setMaker(in.next());

				System.out.print("Inserisci il modello: ");
				in.nextLine();
				newVehicle.setModel(in.nextLine());
				
				System.out.print("Capacità della batteria (in kWh): ");
				newVehicle.setCapacity(in.nextInt());

				System.out.print("Che tipologia di veicolo è? 0 Electric - 1 Hybrid - 2 ICE: ");
				newVehicle.setType(in.nextInt());

				if (newVehicle.getType() != 2) {
					System.out.println("Quale rate di ricarica supporta il tuo veicolo?");
					// Stampa di tutti i rate di ricarica
					ChargingRate.printAll(chargingRateList);
					System.out.print("Scelta: ");
					int cr = in.nextInt();
					newVehicle.setSupportedRate(chargingRateList.get(cr - 1));
				}

				newVehicle.setLocation(newUser.getLocation());
				newVehicle.setOwner(newUser);
				newUser.setPersonalVehicle(newVehicle);
				System.out.println("Veicolo aggiunto ed associato a te!");
				vehicleList.add(newVehicle);
				currentUsername = username;
			}
		} else if (currentUsername.equals("admin")) {
			adminMode = true;
		}

		if (adminMode) {
			do {
				System.out.println("-------------ADMIN MODE-------------");
				System.out.println("Effettuare una scelta:");
				System.out.println("1. Aggiunta di un charge rate");
				System.out.println("2. Rimozione di un charge rate");
				System.out.println("3. Modifica di un charge rate");
				System.out.println("4. Aggiunta di un fornitore di energia");
				System.out.println("5. Rimozione di un fornitore di energia");
				System.out.println("6. Modifica di un fornitore di energia");
				System.out.println("7. Aggiunta di una stazione di ricarica");
				System.out.println("8. Rimozione di una stazione di ricarica");
				System.out.println("9. Modifica di una stazione di ricarica");
				System.out.println("10. Aggiunta di una ricompensa");
				System.out.println("11. Rimozione di una ricompensa");
				System.out.println("12. Modifica di una ricompensa");
				System.out.println("13. Aggiunta di un utente");
				System.out.println("14. Rimozione di un utente");
				System.out.println("15. Modifica di un utente");
				System.out.println("16. Aggiunta di un veicolo");
				System.out.println("17. Rimozione di un veicolo");
				System.out.println("18. Modifica di un veicolo");
				System.out.println("99. Termine del programma");
				System.out.println("999. Modalità di debug");
				System.out.print("Scelta: ");
				scelta = in.nextInt();

				switch (scelta) {
				case 1: {
					System.out.println("---AGGIUNTA DI UN CHARGE RATE---");

					ChargingRate cr1 = new ChargingRate();

					int maxID = 0;
					for (ChargingRate cr : chargingRateList) {
						if (cr.getId() > maxID) {
							maxID = cr.getId();
						}
					}
					cr1.setId(maxID + 1);

					System.out.print("Potenza di ricarica del nuovo charge rate (in kW): ");
					cr1.setPower(in.nextInt());

					System.out.print("Prezzo di ricarica del nuovo charge rate (in €/kWh): ");
					cr1.setPrice(in.nextDouble());

					chargingRateList.add(cr1);
					System.out.println("Charge rate aggiunto!");

					// Stampa della lista aggiornata
					ChargingRate.printAll(chargingRateList);

					break;
				}

				case 2: {
					System.out.println("---RIMOZIONE DI UN CHARGE RATE---");

					// Stampa della lista iniziale
					ChargingRate.printAll(chargingRateList);

					System.out.print("Charge rate da rimuovere: ");
					chargingRateList.remove(in.nextInt() - 1);

					System.out.println("Charge rate rimosso!");

					// Stampa della lista aggiornata
					ChargingRate.printAll(chargingRateList);

					break;
				}

				case 3: {
					System.out.println("---MODIFICA DI UN CHARGE RATE---");

					// Stampa della lista iniziale
					ChargingRate.printAll(chargingRateList);

					System.out.print("Charge rate da modificare: ");
					int crNumber = in.nextInt();

					System.out.println("1. Modificare la potenza");
					System.out.println("2. Modificare il prezzo");
					System.out.print("Scelta: ");
					scelta = in.nextInt();

					switch (scelta) {
					case 1: {
						System.out.print("Nuova potenza (in kW): ");
						int newPower = in.nextInt();
						int oldPower = chargingRateList.get(crNumber - 1).getPower();

						chargingRateList.get(crNumber - 1).setPower(newPower);

						System.out.println("La potenza del charge rate scelto è passata da " + oldPower + " kW a "
								+ newPower + " kW.");
						break;
					}
					case 2: {
						System.out.print("Nuovo prezzo (in €/kW): ");
						double newPrice = in.nextDouble();
						double oldPrice = chargingRateList.get(crNumber - 1).getPrice();

						chargingRateList.get(crNumber - 1).setPrice(newPrice);

						System.out.println("Il prezzo del charge rate scelto è passato da " + oldPrice + " €/kW a "
								+ newPrice + " €/kW.");
						break;
					}
					}

					// Stampa della lista aggiornata
					ChargingRate.printAll(chargingRateList);

					break;
				}

				case 4: {
					System.out.println("---AGGIUNTA DI UN FORNITORE DI ENERGIA---");

					EnergySupplier es1 = new EnergySupplier();

					System.out.print("Nome del nuovo fornitore di energia: ");
					es1.setName(in.next());
					es1.setChargingStations(null);

					energySupplierList.add(es1);

					// Stampa della lista aggiornata
					EnergySupplier.printAll(energySupplierList);

					break;
				}

				case 5: {
					System.out.println("---RIMOZIONE DI UN FORNITORE DI ENERGIA---");

					// Stampa della lista iniziale
					EnergySupplier.printAll(energySupplierList);

					System.out.print("Fornitore di energia da rimuovere: ");
					energySupplierList.remove(in.nextInt() - 1);
					System.out.println("Fornitore di energia rimosso!");

					// Rimuovere anche tutte le colonnine in suo possesso

					// Stampa della lista aggiornata
					EnergySupplier.printAll(energySupplierList);

					break;
				}

				case 6: {
					System.out.println("---MODIFICA DI UN FORNITORE DI ENERGIA---");

					// Stampa della lista iniziale
					EnergySupplier.printAll(energySupplierList);

					System.out.print("Fornitore di energia da modificare: ");
					int esNumber = in.nextInt();

					System.out.println("1. Modificare il nome");
					// System.out.println("2. Altro?");

					System.out.print("Scelta: ");

					scelta = in.nextInt();

					switch (scelta) {
					case 1: {
						System.out.print("Nuovo nome: ");
						String newName = in.next();
						String oldName = energySupplierList.get(esNumber - 1).getName();

						energySupplierList.get(esNumber - 1).setName(newName);

						System.out.println(
								"Il nome del fornitore di energia passato da " + oldName + " a " + newName + ".");
						break;
					}
					}

					// Stampa della lista aggiornata
					EnergySupplier.printAll(energySupplierList);

					break;
				}

				case 7: {
					System.out.println("---AGGIUNTA DI UNA STAZIONE DI RICARICA---");

					ChargingStation csNew = new ChargingStation();

					int maxID = 0;
					for (ChargingStation cs : chargingStationList) {
						if (cs.getId() > maxID) {
							maxID = cs.getId();
						}
					}
					csNew.setId(maxID + 1);

					System.out.println("Quale fornitore di energia ha installato questa stazione?");

					// Stampa di tutti i fornitori di energia
					EnergySupplier.printAll(energySupplierList);

					System.out.print("Scelta: ");
					csNew.setOwner(energySupplierList.get(in.nextInt() - 1));

					System.out.println("Quali rate di ricarica supporta questa stazione?");

					// Stampa di tutti i rate di ricarica
					ChargingRate.printAll(chargingRateList);

					System.out.print("Scelta (separati da virgole ad es 1,3,5): ");

					// Divide la stringa in array di stringhe usando la virgola come delimitatore
					String[] chargingRateStringArray = in.next().split(",");

					// Crea un vettore di int per memorizzare le stazioni selezionate
					int[] chargingRateIntArray = new int[chargingRateStringArray.length];

					// Converte gli elementi dell'array di stringhe in int e li inserisce nel
					// vettore
					for (i = 0; i < chargingRateStringArray.length; i++) {
						chargingRateIntArray[i] = Integer.parseInt(chargingRateStringArray[i]);
					}

					ArrayList<ChargingRate> tempArray = new ArrayList<ChargingRate>();

					// Estrae gli standard di ricarica
					for (i = 0; i < chargingRateIntArray.length; i++) {
						tempArray.add(chargingRateList.get(chargingRateIntArray[i] - 1));
					}

					csNew.setAvailableRates(tempArray);

					Location l1 = new Location();
					System.out.print("Posizione X: ");
					l1.setLatitude(in.nextInt());
					System.out.print("Posizione Y: ");
					l1.setLongitude(in.nextInt());

					csNew.setLocation(l1);
					csNew.setMaintenance(false);
					csNew.setTimeTable(null);
					// update EnergySupplierStations?

					chargingStationList.add(csNew);

					// Stampa della lista aggiornata
					ChargingStation.printAll(chargingStationList);

					break;
				}

				case 8: {
					System.out.println("---RIMOZIONE DI UNA STAZIONE DI RICARICA---");

					// Stampa della lista iniziale
					ChargingStation.printAll(chargingStationList);

					System.out.print("Stazione di ricarica da rimuovere: ");
					chargingStationList.remove(in.nextInt() - 1);
					System.out.println("Stazione di ricarica rimossa!");

					// Stampa della lista aggiornata
					ChargingStation.printAll(chargingStationList);

					break;
				}

				case 9: {
					System.out.println("---MODIFICA DI UNA STAZIONE DI RICARICA---");

					// Stampa della lista iniziale
					ChargingStation.printAll(chargingStationList);

					System.out.print("Stazione di ricarica da modificare: ");
					int csNumber = in.nextInt();

					System.out.println("1. Modificare i charging rate disponibili");
					System.out.println("2. Modificare lo stato della manutenzione");

					System.out.print("Scelta: ");

					scelta = in.nextInt();

					switch (scelta) {
					case 1: {
						// Stampa di tutti i rate di ricarica
						ChargingRate.printAll(chargingRateList);
						System.out.print("Nuovi charging rate disponibili (separati da virgole ad es 1,3,5): ");

						// Divide la stringa in array di stringhe usando la virgola come delimitatore
						String[] chargingRateStringArray = in.next().split(",");

						// Crea un vettore di int per memorizzare le stazioni selezionate
						int[] chargingRateIntArray = new int[chargingRateStringArray.length];

						// Converte gli elementi dell'array di stringhe in int e li inserisce nel
						// vettore
						for (i = 0; i < chargingRateStringArray.length; i++) {
							chargingRateIntArray[i] = Integer.parseInt(chargingRateStringArray[i]);
						}

						ArrayList<ChargingRate> tempArray = new ArrayList<ChargingRate>();

						// Estrae gli standard di ricarica
						for (i = 0; i < chargingRateIntArray.length; i++) {
							tempArray.add(chargingRateList.get(chargingRateIntArray[i] - 1));
						}

						chargingStationList.get(csNumber - 1).setAvailableRates(tempArray);

						System.out.println("Nuovi charging rate impostati");
						break;
					}
					case 2: {
						if (chargingStationList.get(csNumber - 1).isMaintenance()) {
							System.out.print("La stazione " + chargingStationList.get(csNumber - 1).getId()
									+ " non è più in manutenzione");
							chargingStationList.get(csNumber - 1).setMaintenance(false);
						} else {
							System.out.print("La stazione " + chargingStationList.get(csNumber - 1).getId()
									+ " è da ora in manutenzione");
							chargingStationList.get(csNumber - 1).setMaintenance(true);
						}

						break;
					}
					}

					// Stampa della lista aggiornata
					ChargingStation.printAll(chargingStationList);

					break;
				}

				case 10: {
					System.out.println("---AGGIUNTA DI UNA RICOMPENSA---");

					Reward reward = new Reward();

					System.out.print("Nome: ");
					reward.setName(in.next());

					System.out.print("Descrizione: ");
					in.nextLine();
					reward.setDescription(in.nextLine());

					System.out.print("Costo in Green Points: ");
					reward.setGreenPointsCost(in.nextInt());

					System.out.print("Quantità rimanente: ");
					reward.setRemainingQuantity(in.nextInt());

					rewardList.add(reward);

					// Stampa della lista aggiornata
					Reward.printAll(rewardList);

					break;
				}

				case 11: {
					System.out.println("---RIMOZIONE DI UNA RICOMPENSA---");

					// Stampa della lista iniziale
					Reward.printAll(rewardList);

					System.out.print("Ricompensa da rimuovere: ");
					rewardList.remove(in.nextInt() - 1);
					System.out.println("Ricompensa rimossa!");

					// Stampa della lista aggiornata
					Reward.printAll(rewardList);

					break;
				}

				case 12: {
					System.out.println("---MODIFICA DI UNA RICOMPENSA---");

					Reward.printAll(rewardList);
					System.out.print("Ricompensa da modificare: ");
					int rNumber = in.nextInt();

					System.out.println("1. Modificare il nome");
					System.out.println("2. Modificare la descrizione");
					System.out.println("3. Modificare il costo in Green Points");
					System.out.println("4. Modificare la quantità rimanente");

					System.out.print("Scelta: ");

					scelta = in.nextInt();

					switch (scelta) {
					case 1: {
						System.out.print("Nuovo nome della ricompensa: ");

						String newName = in.next();
						String oldName = rewardList.get(rNumber - 1).getName();

						rewardList.get(rNumber - 1).setName(newName);

						System.out.println("Il nome della ricompensa è passato da " + oldName + " a " + newName + ".");

						break;
					}
					case 2: {
						System.out.print("Nuova descrizione della ricompensa: ");

						String newDescription = in.next();

						rewardList.get(rNumber - 1).setName(newDescription);

						System.out.println("Descrizione della ricompensa aggiornata.");

						break;
					}
					case 3: {
						System.out.print("Nuovo costo in Green Points della ricompensa: ");

						int newGPCost = in.nextInt();
						int oldGPCost = rewardList.get(rNumber - 1).getGreenPointsCost();

						rewardList.get(rNumber - 1).setGreenPointsCost(newGPCost);

						System.out
						.println("Il costo in Green Points ricompensa " + rewardList.get(rNumber - 1).getName()
								+ " è passato da " + oldGPCost + " a " + newGPCost);

						break;
					}
					case 4: {
						System.out.print("Nuova quantità rimanente della ricompensa: ");

						int newQuantity = in.nextInt();
						int oldQuantity = rewardList.get(rNumber - 1).getRemainingQuantity();

						rewardList.get(rNumber - 1).setRemainingQuantity(newQuantity);

						System.out.println(
								"La quantità rimanente della ricompensa " + rewardList.get(rNumber - 1).getName()
								+ " è passata da " + oldQuantity + " a " + newQuantity);

						break;
					}
					}
					// Stampa della lista aggiornata
					Reward.printAll(rewardList);

					break;
				}

				case 13: {
					System.out.println("---AGGIUNTA DI UN UTENTE---");

					User User1 = new User();

					System.out.print("Nome: ");
					User1.setName(in.next());

					System.out.print("Cognome: ");
					User1.setSurname(in.next());

					System.out.print("Username: ");
					User1.setUsername(in.next());

					System.out.println("Se hai un veicolo elettrico, inserisci 0");
					System.out.println("Se hai un veicolo a combustione interna, inserisci 1");
					System.out.println("Se non hai un veicolo e vuoi usufruire del car sharing, inserisci 2");
					System.out.print("Scelta: ");
					User1.setType(in.nextInt());

					User1.setGreenPointsBalance(0);

					Location l1 = new Location();
					System.out.print("Posizione X: ");
					l1.setLatitude(in.nextInt());
					System.out.print("Posizione Y: ");
					l1.setLongitude(in.nextInt());

					User1.setLocation(l1);

					userList.add(User1);

					// Stampa della lista aggiornata
					User.printAll(userList);

					break;
				}

				case 14: {
					System.out.println("---RIMOZIONE DI UN UTENTE---");

					// Stampa della lista iniziale
					User.printAll(userList);

					System.out.print("Utente da rimuovere: ");
					userList.remove(in.nextInt() - 1);
					System.out.println("Utente rimosso!");

					// Stampa della lista aggiornata
					User.printAll(userList);

					break;
				}

				case 15: {
					System.out.println("---MODIFICA DI UN UTENTE---");

					User.printAll(userList);
					System.out.print("Utente da modificare: ");
					int uNumber = in.nextInt();

					System.out.println("1. Modificare l'username");
					System.out.println("2. Modificare il bilancio dei Green Points");
					System.out.println("3. Modificare la tipologia di utente (EV-ICE-NoVehicle)");
					System.out.println("4. Modificare il nome");
					System.out.println("5. Modificare il cognome");

					System.out.print("Scelta: ");

					scelta = in.nextInt();

					switch (scelta) {
					case 1: {
						System.out.print("Nuovo username: ");

						String newUsername = in.next();
						String oldUsername = userList.get(uNumber - 1).getUsername();

						userList.get(uNumber - 1).setUsername(newUsername);

						System.out.println("L'username dell'utente è passato da " + oldUsername + " a " + newUsername);

						break;
					}
					case 2: {
						System.out.print("Nuovo bilancio dei Green Points: ");

						int newGPBalance = in.nextInt();
						int oldGPBalance = userList.get(uNumber - 1).getGreenPointsBalance();

						userList.get(uNumber - 1).setGreenPointsBalance(newGPBalance);

						System.out.println(
								"Il bilancio dei Green Points dell'utente " + userList.get(uNumber - 1).getUsername()
								+ " è passato da " + oldGPBalance + " a " + newGPBalance);
						break;
					}
					case 3: {
						System.out.print("Nuova tipologia (0 EV - 1 ICE - 2 NoVehicle): ");

						int newType = in.nextInt();
						int oldType = userList.get(uNumber - 1).getType();

						userList.get(uNumber - 1).setType(newType);

						System.out.println("La tipologia dell'utente " + userList.get(uNumber - 1).getUsername()
								+ " è passata da " + oldType + " a " + newType);

						break;
					}
					case 4: {
						System.out.print("Nuovo nome: ");

						String newName = in.next();
						String oldName = userList.get(uNumber - 1).getName();

						userList.get(uNumber - 1).setName(newName);

						System.out.println("Il nome dell'utente " + userList.get(uNumber - 1).getUsername()
								+ " è passato da " + oldName + " a " + newName);

						break;
					}
					case 5: {
						System.out.print("Nuovo cognome: ");

						String newSurname = in.next();
						String oldSurname = userList.get(uNumber - 1).getSurname();

						userList.get(uNumber - 1).setSurname(newSurname);

						System.out.println("Il cognome dell'utente " + userList.get(uNumber - 1).getUsername()
								+ " è passato da " + oldSurname + " a " + newSurname);

						break;
					}
					}

					// Stampa della lista aggiornata
					User.printAll(userList);

					break;
				}

				case 16: {
					System.out.println("---AGGIUNTA DI UN VEICOLO---");

					Vehicle v1 = new Vehicle();
					System.out.print("Marca: ");
					v1.setMaker(in.next());

					System.out.print("Modello: ");
					in.nextLine();
					v1.setModel(in.nextLine());

					System.out.print("Tipologia (0 Electric - 1 Hybrid - 2 ICE): ");
					v1.setType(in.nextInt());
					
					System.out.print("Capacità della batteria (in kWh): ");
					v1.setCapacity(in.nextInt());

					if (v1.getType() != 2) {
						System.out.println("Quale rate di ricarica supporta questo veicolo?");
						// Stampa di tutti i rate di ricarica
						ChargingRate.printAll(chargingRateList);
						System.out.print("Scelta: ");
						int cr = in.nextInt();
						v1.setSupportedRate(chargingRateList.get(cr - 1));
					}

					Location l1 = new Location();

					System.out.print("Posizione X: ");
					l1.setLatitude(in.nextInt());

					System.out.print("Posizione Y: ");
					l1.setLongitude(in.nextInt());

					v1.setLocation(l1);

					System.out.println("Veicolo aggiunto!");
					System.out.println(v1);

					vehicleList.add(v1);

					// Stampa della lista aggiornata
					Vehicle.printAll(vehicleList);

					break;
				}

				case 17: {
					System.out.println("---RIMOZIONE DI UN VEICOLO---");

					Vehicle.printAll(vehicleList);

					System.out.print("Veicolo da rimuovere: ");
					vehicleList.remove(in.nextInt() - 1);
					System.out.println("Veicolo rimosso!");

					// Stampa della lista aggiornata
					Vehicle.printAll(vehicleList);

					break;
				}

				case 18: {
					System.out.println("---MODIFICA DI UN VEICOLO---");

					Vehicle.printAll(vehicleList);

					System.out.print("Veicolo da modificare: ");
					int vNumber = in.nextInt();

					System.out.println("1. Modificare la marca");
					System.out.println("2. Modificare il modello");
					System.out.println("3. Modificare il proprietario");
					System.out.println("4. Modificare il rate di ricarica supportato");
					System.out.println("5. Modificare la tipologia");

					System.out.print("Scelta: ");

					scelta = in.nextInt();

					switch (scelta) {
					case 1: {
						System.out.print("Nuova marca: ");

						String newMaker = in.next();
						String oldMaker = vehicleList.get(vNumber - 1).getMaker();
						vehicleList.get(vNumber - 1).setMaker(newMaker);

						System.out.println("La marca del veicolo è passata da " + oldMaker + " a " + newMaker);

						break;
					}
					case 2: {
						System.out.print("Nuovo modello: ");

						String newModel = in.next();
						String oldModel = vehicleList.get(vNumber - 1).getModel();
						vehicleList.get(vNumber - 1).setModel(newModel);

						System.out.println("Il modello del veicolo è passato da " + oldModel + " a " + newModel);

						break;
					}
					case 3: {
						User.printAll(userList);
						System.out.print("Nuovo proprietario: ");

						User newOwner = userList.get(in.nextInt() - 1);
						vehicleList.get(vNumber).setOwner(newOwner);

						System.out.println("Il veicolo è ora di " + newOwner);

						break;
					}
					case 4: {
						ChargingRate.printAll(chargingRateList);
						System.out.print("Nuovo rate di ricarica supportato: ");

						ChargingRate newChargingRate = chargingRateList.get(in.nextInt() - 1);
						vehicleList.get(vNumber).setSupportedRate(newChargingRate);

						System.out.println("Il veicolo supporta ora il charging rate " + newChargingRate);

						break;
					}
					case 5: {
						System.out.print("Nuova tipologia: NON IMPLEMENTATO ANCORA");
						break;
					}
					}

					// Stampa della lista aggiornata
					Vehicle.printAll(vehicleList);

					break;
				}
				case 999: {
					do {
						System.out.println("---MODALITA' DI DEBUG---");
						System.out.println("1. Debug - Stampa di tutti i charge rate");
						System.out.println("2. Debug - Stampa di tutti i fornitori di energia");
						System.out.println("3. Debug - Stampa di tutte le stazioni di ricarica");
						System.out.println("4. Debug - Stampa di tutte le ricompense TODO");
						System.out.println("5. Debug - Stampa di tutti gli utenti TODO");
						System.out.println("6. Debug - Stampa di tutti i veicoli TODO");
						System.out.println("7. Debug - Stampa di tutte le ricariche effettuate TODO");
						System.out.println("99. Torna al menù precedente");
						System.out.print("Scelta: ");
						scelta = in.nextInt();

						switch (scelta) {
						case 1: {
							System.out.println("---Debug - Stampa di tutti i charge rate---");
							ChargingRate.printAll(chargingRateList);
							break;
						}
						case 2: {
							System.out.println("---Debug - Stampa di tutti i fornitori di energia---");
							EnergySupplier.printAll(energySupplierList);
							break;
						}
						case 3: {
							System.out.println("---Debug - Stampa di tutte le stazioni di ricarica---");
							ChargingStation.printAll(chargingStationList);
							break;
						}
						}
					} while (scelta != 99);
					scelta = 0;
				}
				}
			} while (scelta != 99);
		} else {
			User currentUser = User.getUserByUsername(userList, currentUsername);
			Vehicle currentVehicle = currentUser.getPersonalVehicle();
			ChargingStation currentCS = new ChargingStation();
			double pricePerkWh = 0;
			
			System.out.println(
					"Benvenuto " + currentUser.getName() + ". Saldo GP: " + currentUser.getGreenPointsBalance());
			System.out.println("1) Ricarica il tuo veicolo elettrico");
			System.out.println("2) Prenota una ricarica");
			System.out.println("3) Noleggia un veicolo");
			System.out.println("4) Riscatta una ricompensa");
			System.out.print("Cosa vuoi fare? ");
			int choice = in.nextInt();

			switch (choice) {
			case 1: {
				if (currentUser.getType() != 0) {
					System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico");
					break;
				}
				System.out.println("Dove ti trovi?");
				Location locCurrUser = new Location();
				System.out.print("X: ");
				locCurrUser.setLatitude(in.nextInt());
				System.out.print("Y: ");
				locCurrUser.setLongitude(in.nextInt());
				currentUser.setLocation(locCurrUser);

				System.out.println("Ecco la lista delle stazioni disponibili intorno a te:");
				for (ChargingStation cs : chargingStationList) {
					if (!cs.isMaintenance()) {
						System.out.println(cs);
					}
				}
				
				
				System.out.print("Inserisci l'ID della stazione dove vuoi effettuare la ricarica:");
				int csID = in.nextInt();
				while (true) {
					if(chargingStationList.get(csID).isCompatibleWithVehicle(currentVehicle, pricePerkWh)) {
						currentCS = chargingStationList.get(csID);
						break;
					} else {
						System.out.println("La stazione scelta non è compatibile con il tuo veicolo. Scegline un'altra:");
						csID = in.nextInt();
					}
				}
				
				double chargeAmount = currentVehicle.getCapacity()/pricePerkWh;

				//calcolare prezzo.... e registrare la ricarica
				System.out.println("Ricarica effettuata, totale: " + chargeAmount);
				
				Charge newCharge = new Charge();
				newCharge.setChargingStation(currentCS);
				newCharge.setVehicle(currentVehicle);
				newCharge.setChargingRate()
				// fare la transazione!
				break;
			}
			case 2: {
				System.out.println("Prenota una ricaricaaaaaaaaaaaaaaaaaaaa");
				break;
			}
			case 3: {
				System.out.println("Implementato nella seconda iterazione");
				break;
			}
			case 4: {
				System.out.println("Il tuo attuale saldo dei Green Points è: " + currentUser.getGreenPointsBalance());
				break;
			}
			}
		}

		in.close();

		saveAll();
	}

	public static void chargeNow() {
		System.out.println("chargeNow");

	}

	public static void reserveSpot() {
		System.out.println("reserveSpot");
	}

	@SuppressWarnings("unchecked")
	private static void readAll() throws FileNotFoundException, IOException {
		// Lettura chargingRateList
		FileInputStream FIS = new FileInputStream("ChargingRate.txt");
		ObjectInputStream OIS = new ObjectInputStream(FIS);
		try {
			chargingRateList = (ArrayList<ChargingRate>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura energySupplierList
		FIS = new FileInputStream("EnergySupplier.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			energySupplierList = (ArrayList<EnergySupplier>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura chargingStationList
		FIS = new FileInputStream("ChargingStation.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			chargingStationList = (ArrayList<ChargingStation>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura rewardList
		FIS = new FileInputStream("Reward.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			rewardList = (ArrayList<Reward>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura userList
		FIS = new FileInputStream("User.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			userList = (ArrayList<User>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura vehicleList
		FIS = new FileInputStream("Vehicle.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			vehicleList = (ArrayList<Vehicle>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		/*
		// Lettura transactionList
		FIS = new FileInputStream("Transaction.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			transactionList = (ArrayList<Transaction>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura reservationList
		FIS = new FileInputStream("Reservation.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			reservationList = (ArrayList<Reservation>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 */
	}

	public static void saveAll() throws IOException {
		// Salvataggio di chargingRateList
		try (FileOutputStream chargingRateFOS = new FileOutputStream(new File("ChargingRate.txt"));
				ObjectOutputStream chargingRateOOS = new ObjectOutputStream(chargingRateFOS)) {
			chargingRateOOS.writeObject(chargingRateList);
		}

		// Salvataggio di energySupplierList
		try (FileOutputStream energySupplierFOS = new FileOutputStream(new File("EnergySupplier.txt"));
				ObjectOutputStream energySupplierOOS = new ObjectOutputStream(energySupplierFOS)) {
			energySupplierOOS.writeObject(energySupplierList);
		}

		// Salvataggio di chargingStationList
		try (FileOutputStream chargingStationFOS = new FileOutputStream(new File("ChargingStation.txt"));
				ObjectOutputStream chargingStationOOS = new ObjectOutputStream(chargingStationFOS)) {
			chargingStationOOS.writeObject(chargingStationList);
		}

		// Salvataggio di rewardList
		try (FileOutputStream rewardFOS = new FileOutputStream(new File("Reward.txt"));
				ObjectOutputStream rewardOOS = new ObjectOutputStream(rewardFOS)) {
			rewardOOS.writeObject(rewardList);
		}

		// Salvataggio di userList
		try (FileOutputStream userFOS = new FileOutputStream(new File("User.txt"));
				ObjectOutputStream userOOS = new ObjectOutputStream(userFOS)) {
			userOOS.writeObject(userList);
		}

		// Salvataggio di vehicleList
		try (FileOutputStream vehicleFOS = new FileOutputStream(new File("Vehicle.txt"));
				ObjectOutputStream vehicleOOS = new ObjectOutputStream(vehicleFOS)) {
			vehicleOOS.writeObject(vehicleList);
		}

		// Salvataggio di transactionList
		try (FileOutputStream vehicleFOS = new FileOutputStream(new File("Transaction.txt"));
				ObjectOutputStream vehicleOOS = new ObjectOutputStream(vehicleFOS)) {
			vehicleOOS.writeObject(transactionList);
		}

		// Salvataggio di reservationList
		try (FileOutputStream vehicleFOS = new FileOutputStream(new File("Reservation.txt"));
				ObjectOutputStream vehicleOOS = new ObjectOutputStream(vehicleFOS)) {
			vehicleOOS.writeObject(reservationList);
		}
		System.out.println("\n\n----------------------------------------------------------------");
		System.out.println("Tutti i dati sono stati correttamente salvati su file.");
		System.out.println("----------------------------------------------------------------");
	}

}