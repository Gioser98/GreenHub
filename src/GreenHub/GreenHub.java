package GreenHub;

import java.io.*;
import java.time.LocalDateTime;
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
	
	// SISTEMARE L'ORARIO (GESTIRSELO DA SOLI CON CLASSE TIME?)
	
	/*
	private static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private static ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	*/
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args ) throws IOException {
		
		System.out.println("BENVENUTO IN GREENHUB!");
		System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
		System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo "
				+ "l'apposita opzione, altrimenti verranno perse.");
		
		
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
		} catch (InvalidClassException e) {
			System.out.println("Formato della classe cambiato!");
		}
		
		/*
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
		
		*/
		int scelta;		
		
		do {	
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
			System.out.println("100. chargeNow");
			System.out.println("999. Modalità di debug");

			System.out.print("Scelta: ");

			try {
				in = new Scanner(System.in);
				scelta = in.nextInt();
			} catch (Exception e) {
				scelta = -1;
			}
			
			switch (scelta) {
			case 1: {
				System.out.println("---AGGIUNTA DI UN CHARGE RATE---");
				
				ChargingRate cr1 = new ChargingRate();
				
				int maxID=0;
				for (ChargingRate cr : chargingRateList) {
					if (cr.getId()>maxID) {
						maxID = cr.getId();
					}
				}
				cr1.setId(maxID+1);
				
				System.out.print("Potenza di ricarica del nuovo charge rate (in kW): ");
				cr1.setPower(in.nextInt());
				
				System.out.print("Prezzo di ricarica del nuovo charge rate (in €/kW): ");
				cr1.setPrice(in.nextDouble());
				
				chargingRateList.add(cr1);
				System.out.println("Charge rate aggiunto!");
				
				// Stampa della lista aggiornata
				int i = 1;
				for (ChargingRate cr : chargingRateList) {
					System.out.println(i + ") " + cr);
					i++;
				}
				
				break;
			}
			
			case 2: {
				System.out.println("---RIMOZIONE DI UN CHARGE RATE---");
				
				// Stampa della lista iniziale
				int i = 1;
				for (ChargingRate cr : chargingRateList) {
					System.out.println(i + ") " + cr);
					i++;
				}
				
				System.out.print("Charge rate da rimuovere: ");
				chargingRateList.remove(in.nextInt() - 1);

				System.out.println("Charge rate rimosso!");
				
				// Stampa della lista aggiornata
				i = 1;
				for (ChargingRate cr : chargingRateList) {
					System.out.println(i + ") " + cr);
					i++;
				}
				
				break;
			}
			
			case 3: {
				System.out.println("---MODIFICA DI UN CHARGE RATE---");
				
				// Stampa della lista iniziale
				int i = 1;
				for (ChargingRate cr : chargingRateList) {
					System.out.println(i + ") " + cr);
					i++;
				}
				
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
					int oldPower = chargingRateList.get(crNumber-1).getPower();
					
					chargingRateList.get(crNumber-1).setPower(newPower);
					
					System.out.println("La potenza del charge rate scelto è passata da " + oldPower + " kW a " + newPower + " kW.");
					break;
				}
				case 2: {
					System.out.print("Nuovo prezzo (in €/kW): ");
					double newPrice = in.nextDouble();
					double oldPrice = chargingRateList.get(crNumber-1).getPrice();
					
					chargingRateList.get(crNumber-1).setPrice(newPrice);
					
					System.out.println("Il prezzo del charge rate scelto è passato da " + oldPrice + " €/kW a " + newPrice + " €/kW.");
					break;
				}
				}
				
				// Stampa della lista aggiornata
				i = 1;
				for (ChargingRate cr : chargingRateList) {
					System.out.println(i + ") " + cr);
					i++;
				}
				
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
				int i = 1;
				for (EnergySupplier es : energySupplierList) {
					System.out.println(i + ") " + es);
					i++;
				}
				
				break;
			}
			
			case 5: {
				System.out.println("---RIMOZIONE DI UN FORNITORE DI ENERGIA---");
				
				int i = 1;
				for (EnergySupplier es : energySupplierList) {
					System.out.println(i + ") " + es);
					i++;
				}
				System.out.print("Fornitore di energia da rimuovere: ");
				energySupplierList.remove(in.nextInt() - 1);

				System.out.println("Fornitore di energia rimosso!");
				
				//Rimuovere anche tutte le colonnine in suo possesso
				
				// Stampa della lista aggiornata
				i = 1;
				for (EnergySupplier es : energySupplierList) {
					System.out.println(i + ") " + es);
					i++;
				}
				
				break;
			}
			
			case 6: {
				System.out.println("---MODIFICA DI UN FORNITORE DI ENERGIA---");
				
				int i = 1;
				for (EnergySupplier es : energySupplierList) {
					System.out.println(i + ") " + es);
					i++;
				}
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
					String oldName = energySupplierList.get(esNumber-1).getName();
					
					energySupplierList.get(esNumber-1).setName(newName);
					
					System.out.println("Il nome del fornitore di energia passato da " + oldName + " a " + newName + ".");
					break;
				}
				}
				
				// Stampa della lista aggiornata
				i = 1;
				for (EnergySupplier es : energySupplierList) {
					System.out.println(i + ") " + es);
					i++;
				}
				
				break;
			}
			
			case 7: {
				System.out.println("---AGGIUNTA DI UNA STAZIONE DI RICARICA---");
				
				ChargingStation csNew = new ChargingStation();
				
				int maxID=0;
				for (ChargingStation cs : chargingStationList) {
					if (cs.getId()>maxID) {
						maxID = cs.getId();
					}
				}
				csNew.setId(maxID+1);
				
				System.out.println("Quale fornitore di energia ha installato questa stazione?");
				// Stampa di tutti i fornitori di energia
				int i = 1;
				for (EnergySupplier es : energySupplierList) {
					System.out.println(i + ") " + es);
					i++;
				}
				System.out.print("Scelta: ");
				csNew.setOwner(energySupplierList.get(in.nextInt()-1));
				
				System.out.println("Quali rate di ricarica supporta questa stazione?");
				// Stampa di tutti i rate di ricarica
				i = 1;
				for (ChargingRate cr : chargingRateList) {
					System.out.println(i + ") " + cr);
					i++;
				}
				System.out.print("Scelta (separati da virgole ad es 1,3,5): ");
				
		        // Divide la stringa in array di stringhe usando la virgola come delimitatore
				String[] chargingRateStringArray = in.next().split(",");
				
				// Crea un vettore di int per memorizzare le stazioni selezionate
		        int[] chargingRateIntArray = new int[chargingRateStringArray.length];
		        
		        // Converte gli elementi dell'array di stringhe in int e li inserisce nel vettore
		        for (i = 0; i < chargingRateStringArray.length; i++) {
		        	chargingRateIntArray[i] = Integer.parseInt(chargingRateStringArray[i]);
		        }
		        
		        ArrayList<ChargingRate> tempArray = new ArrayList<ChargingRate>();
		        
		        // Estrae gli standard di ricarica
		        for (i = 0; i < chargingRateIntArray.length; i++) {
		        	tempArray.add(chargingRateList.get(chargingRateIntArray[i]-1));		        	
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
				//update EnergySupplierStations?
				
				chargingStationList.add(csNew);
				
				// Stampa della lista aggiornata
				i = 1;
				for (ChargingStation cs : chargingStationList) {
					System.out.println(i + ") " + cs);
					i++;
				}
				
				break;
			}
			
			case 8: {
				System.out.println("---RIMOZIONE DI UNA STAZIONE DI RICARICA---");
				
				int i = 1;
				for (ChargingStation cs : chargingStationList) {
					System.out.println(i + ") " + cs);
					i++;
				}
				System.out.print("Stazione di ricarica da rimuovere: ");
				chargingStationList.remove(in.nextInt() - 1);

				System.out.println("Stazione di ricarica rimossa!");
				
				// Stampa della lista aggiornata
				i = 1;
				for (ChargingStation cs : chargingStationList) {
					System.out.println(i + ") " + cs);
					i++;
				}
				
				break;
			}
			
			case 9: {
				System.out.println("---MODIFICA DI UNA STAZIONE DI RICARICA---");
				
				/*
				int i = 1;
				for (ChargingStation cs : chargingStationList) {
					System.out.println(i + ") " + cs);
					i++;
				}
				
				System.out.print("Stazione di ricarica da modificare: ");
				int csNumber = in.nextInt();

				System.out.println("1. Modificare i charging rate disponibili");
				System.out.println("2. Modificare la disponibilità");
				
				System.out.print("Scelta: ");
				
				scelta = in.nextInt();
				
				switch (scelta) {
				case 1: {
					System.out.print("Nuova potenza (in kW): ");
					int newPower = in.nextInt();
					int oldPower = chargingRateList.get(crNumber-1).getPower();
					
					chargingRateList.get(crNumber-1).setPower(newPower);
					
					System.out.println("La potenza del charge rate scelto è passata da " + oldPower + " kW a " + newPower + " kW.");
					break;
				}
				case 2: {
					System.out.print("Nuovo prezzo (in €/kW): ");
					double newPrice = in.nextDouble();
					double oldPrice = chargingRateList.get(crNumber-1).getPrice();
					
					chargingRateList.get(crNumber-1).setPrice(newPrice);
					
					System.out.println("Il prezzo del charge rate scelto è passato da " + oldPrice + " €/kW a " + newPrice + " €/kW.");
					break;
				}
				}
				
				// Stampa della lista aggiornata
				i = 1;
				for (ChargingStation cs : chargingStationList) {
					System.out.println(i + ") " + cs);
					i++;
				}
				*/
				break;
			}
			
			case 10: {
				System.out.println("---AGGIUNTA DI UNA RICOMPENSA---");
				
				Reward reward = new Reward();
				
				System.out.print("Name: ");
				reward.setName(in.next());
				
				System.out.print("Description: ");
				in.nextLine();
				reward.setDescription(in.nextLine());
				
				System.out.print("GreenPointsCost: ");
				reward.setGreenPointsCost(in.nextInt());
				
				System.out.print("RemainingQuantity: ");
				reward.setRemainingQuantity(in.nextInt());
				
				rewardList.add(reward);
				
				
				// Stampa della lista aggiornata
				int i = 1;
				for (Reward r : rewardList) {
					System.out.println(i + ") " + r);
					i++;
				}
				
				break;
			}
			
			case 11: {
				System.out.println("---RIMOZIONE DI UNA RICOMPENSA---");
				
				int i = 1;
				for (Reward r : rewardList) {
					System.out.println(i + ") " + r);
					i++;
				}
				
				System.out.print("Ricompensa da rimuovere: ");
				rewardList.remove(in.nextInt() - 1);
				System.out.println("Ricompensa rimossa!");
				
				// Stampa della lista aggiornata
				i = 1;
				for (Reward r : rewardList) {
					System.out.println(i + ") " + r);
					i++;
				}
				
				break;
			}
			
			case 12: {
				System.out.println("---MODIFICA DI UNA RICOMPENSA---");
				int i = 1;
				for (Reward r : rewardList) {
					System.out.println(i + ") " + r);
					i++;
				}
				
				// Modifiche da fare alle ricompense....
				
				// Stampa della lista aggiornata
				i = 1;
				for (Reward r : rewardList) {
					System.out.println(i + ") " + r);
					i++;
				}
				
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
				int i = 1;
				for (User u : userList) {
					System.out.println(i + ") " + u);
					i++;
				}
				
				break;
			}
			
			case 14: {
				System.out.println("---RIMOZIONE DI UN UTENTE---");
				
				int i = 1;
				for (User u : userList) {
					System.out.println(i + ") " + u);
					i++;
				}
				
				System.out.print("Utente da rimuovere: ");
				userList.remove(in.nextInt() - 1);
				System.out.println("Utente rimosso!");
				
				// Stampa della lista aggiornata
				i = 1;
				for (User u : userList) {
					System.out.println(i + ") " + u);
					i++;
				}
				
				break;
			}
			
			case 15: {
				System.out.println("---MODIFICA DI UN UTENTE---");
				
				int i = 1;
				for (User u : userList) {
					System.out.println(i + ") " + u);
					i++;
				}
				
				// Modifiche all'utente....
				
				// Stampa della lista aggiornata
				i = 1;
				for (User u : userList) {
					System.out.println(i + ") " + u);
					i++;
				}
				
				break;
			}

			case 16: {
				System.out.println("---AGGIUNTA DI UN VEICOLO---");
				
				Vehicle v1 = new Vehicle();
				System.out.print("Marca: ");
				v1.setMaker(in.next());

				System.out.print("Modello: ");
				v1.setModel(in.next());
				
				System.out.print("Motorizzazione (0 Electric - 1 Hybrid - 2 ICE): ");
				v1.setEngineType(in.nextInt());

				System.out.print("Carburante (in litri o kWh): ");
				v1.setCapacity(in.nextInt());

				Location l1 = new Location();

				System.out.print("Posizione X: ");
				l1.setLatitude(in.nextInt());

				System.out.print("Posizione Y: ");
				l1.setLongitude(in.nextInt());

				v1.setLocation(l1);
				
				System.out.println("Auto aggiunta!");
				System.out.println(v1);

				vehicleList.add(v1);
				
				// Stampa della lista aggiornata
				int i = 1;
				for (Vehicle v : vehicleList) {
					System.out.println(i + ") " + v);
					i++;
				}
				
				break;
			}
			
			case 17: {
				System.out.println("---RIMOZIONE DI UN VEICOLO---");
				
				int i = 1;
				for (Vehicle v : vehicleList) {
					System.out.println(i + ") " + v);
					i++;
				}
				
				System.out.print("Veicolo da rimuovere: ");
				vehicleList.remove(in.nextInt() - 1);
				System.out.println("Veicolo rimosso!");
				
				// Stampa della lista aggiornata
				i = 1;
				for (Vehicle v : vehicleList) {
					System.out.println(i + ") " + v);
					i++;
				}
				
				break;
			}
			
			case 18: {
				System.out.println("---MODIFICA DI UN VEICOLO---");
				
				int i = 1;
				for (Vehicle v : vehicleList) {
					System.out.println(i + ") " + v);
					i++;
				}
				
				// Modifiche al veicolo....
				
				// Stampa della lista aggiornata
				i = 1;
				for (Vehicle v : vehicleList) {
					System.out.println(i + ") " + v);
					i++;
				}
				
				break;
			}
			
			case 100: {
				chargeNow();
				
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

					try {
						in = new Scanner(System.in);
						scelta = in.nextInt();
					} catch (Exception e) {
						scelta = -1;
					}
					
					switch (scelta) {
					case 1: {
						System.out.println("---Debug - Stampa di tutti i charge rate---");
						
						int i = 1;
						for (ChargingRate cr : chargingRateList) {
							System.out.println(i + ") " + cr);
							i++;
						}
						break;
					}
					case 2: {
						System.out.println("---Debug - Stampa di tutti i fornitori di energia---");
						
						int i = 1;
						for (EnergySupplier es : energySupplierList) {
							System.out.println(i + ") " + es);
							i++;
						}
						break;
					}
					case 3: {
						System.out.println("---Debug - Stampa di tutte le stazioni di ricarica---");
						
						int i = 1;
						for (ChargingStation cs : chargingStationList) {
							System.out.println(i + ") " + cs);
							i++;
						}
						break;
					}
					}
					
				} while (scelta != 99);
				
				scelta = 0;
			}
			}
			
		} while (scelta != 99);
		
		in.close();
		
		saveAll();		
	}

	public static void chargeNow() {
		System.out.println("chargeNow");
		
		
	}

	public static void reserveSpot() {
		System.out.println("reserveSpot");
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
		System.out.println("\n\n----------------------------------------------------------------");
		System.out.println("Tutti i dati sono stati correttamente salvati su file.");
		System.out.println("----------------------------------------------------------------");
	}

}