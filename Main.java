import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		Legal_Person p1 = new Legal_Person("Bello", "028");
		System.out.println(p1.toString());
		
		Physical_Person p2 = new Physical_Person("Hei Hei", "749");
		System.out.println(p2.toString());
		
		Reservation reservation1 = new Reservation(p1, true);
		Reservation reservation2 = new Reservation(p2, false);
		
		System.out.println(reservation1.toString());
		System.out.println(reservation1.calculatePayment());
		System.out.println(reservation2.toString());
		System.out.println(reservation2.calculatePayment());
		
		List<Reservation> reservations = new ArrayList<>();
		
		int option;
		
		do {
			try {
				option = parseInt(showInputDialog(optionsMenu()));
			} catch(NumberFormatException e) {
				showMessageDialog(null, "O valor que foi informado não é um número.");
				option = 0;
			}
			
			if(option < 1 || option > 6) {
				showMessageDialog(null, "Opção Inválida.");
			} else {
				switch(option) {
				case 1:
					bookATable(reservations);
					break;
				case 2:
					searchReservation(reservations);
					break;
				case 3:
					printReservations(reservations);
					break;
				case 4:
					printWaitingList(reservations);
					break;
				case 5:
					cancelReservation(reservations);
					break;
				}
			}
		} while (option !=6);
	}
	
	public static String optionsMenu() {
		String aux = "Restaurante SABOR SOFISTICADO\n";
		aux += "1. Reservar mesa\n";
		aux += "2. Pesquisar reserva\n";
		aux += "3. Imprimir reservas\n";
		aux += "4. Imprimir lista de espera\n";
		aux += "5. Cancelar reserva\n";
		aux += "6. Finalizar";
		return aux;
	}
	
	public static void bookATable(List<Reservation> reservations) {
		Reservation reservation;
		boolean inCash = true;
		
		String name = showInputDialog("Informe o nome do cliente --> ");
		if(name.equals("")) {
			showMessageDialog(null, "É obrigatório informar um nome!", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String cashPayment = showInputDialog("Se for pagamento à vista, digite 'YES', caso contrário digite 'NO'");
		if(!cashPayment.equalsIgnoreCase("YES") && !cashPayment.equalsIgnoreCase("NO")) {
			showMessageDialog(null, "Apenas digite 'YES' ou 'NO'", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
		if(cashPayment.equalsIgnoreCase("NO")) {
			inCash = false;
		}
		
		String option = showInputDialog("Se for pessoa física, digite 'PP', caso seja pessoa jurídica, digite 'LP'");
		if(!option.equals("PP") && !option.equals("LP")) {
			showMessageDialog(null, "Insira apenas 'PP' ou 'LP'", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(option.equals("PP")) {
			String cpf = showInputDialog("Informe o CPF --> ");
			if(cpf.equals("")) {
				showMessageDialog(null, "É obrigatório inserir um CPF!", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
			
			Physical_Person physical_person = new Physical_Person(name, cpf);
			reservation = new Reservation(physical_person, inCash);
			reservations.add(reservation);

		} else if(option.equals("LP")) {
			String cnpj = showInputDialog("Informe o CNPJ --> ");
			if(cnpj.equals("")) {
				showMessageDialog(null, "É obrigatório inserir um CNPJ!", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
			
			Legal_Person legal_person = new Legal_Person(name, cnpj);
			reservation = new Reservation(legal_person, inCash);
			reservations.add(reservation);
		}
		
		if(reservations.size() >= 7) {
			showMessageDialog(null, "Todas as mesas disponíveis encontram-se reservadas, portanto sua reserva foi para a lista de reserva.");
		}
		
	}
	
	public static void searchReservation(List<Reservation> reservation) {
		String identity = showInputDialog("Informe seu CPF ou seu CNPJ");
		boolean haveReservation = false;
		
		for(Reservation reservation : reservations) {
			if(reservation.getClient() instanceof Physical_Person) {
				Physical_Person physical_person = (Physical_Person) reservation.getClient();
				if(identity.equals(Physical_Person.getCpf())) {
					haveReservation = true;
					break;
				}
			} else if(reservation.getClient() instanceof Legal_Person) {
				Legal_Person legal_person = (Legal_Person) reservation.getClient();
				if(identity.equals(Legal_Person.getCnpj())) {
					haveReservation = true;
				}
			}
		}
		
		if(haveReservation) {
			showMessageDialog(null, "O cliente possui uma reserva.");
		} else {
			showMessageDialog(null, "O cliente não possui reserva.");
		}
	}
	
	public static void printReservations(List<Reservation> reservation) {
		String text = "Reservas Principais\n\n";
		String type_of_Payment = "";
		String type_of_Client = "";
		
		int size = 0;
		if(reservations.size() >= 6) {
			size = 6;
		} else {
			size = reservations.size();
		}
		
		for(int i = 0; i < size; i++) {
			type_of_Payment = "";
			type_of_Client = "";
			
			
			if(reservations.get(i).getClient() instanceof Physical_Person) {
				type_of_Client = "Pessoa Física";
			} else if(reservations.get(i).getClient() instanceof Legal_Person) {
				type_of_Client = "Pessoa Jurídica";
			}
			
			text += "Reserva Nº " + (i + 1) + " ";
			text += "Nome --> " + reservations.get(i).getClient().getName() + " | ";
			text += "Tipo de Cliente --> " + type_of_Client + " | ";
			
			if(reservations.get(i).getCashPayment()) {
				type_of_Payment += "À vista";
			} else {
				type_of_Payment += "Parcelado";
			}
			
			text += "Forma de Pagamento --> " + type_of_Payment + "\n";
		}
		
		showMessageDialog(null, text);
	}
	
	public static void printWaitingList(List<Reservation> reservations) {
		String text = "Lista de Espera\n\n";
		String type_of_Payment = "";
		String type_of_Client = "";
		
		for(int i =6; i < reservations.size(); i++) {
			type_of_Payment = "";
			type_of_Client = "";
			
			
			if(reservations.get(i).getClient() instanceof Physical_Person) {
				type_of_Client = "Pessoa Física";
			} else if(resrvations.get(i).getClient() instanceof Legal_Person) {
				type_of_Client = "Pessoa Jurídica";
			}
			
			text += "Reserva Nº " + (i + 1) + " ";
			text += "Nome --> " + reservations.get(i).getClient().getName() + " | ";
			text += "Tipo de Cliente --> " + type_of_Client + " | ";
			
			if(reservations.get(i).getCashPayment()) {
				type_of_Payment = "À vista";
			} else {
				type_of_Payment = "Parcelado";
			}
			
			text += "Forma de Pagamento --> " + type_of_Payment + "\n";
		}
		showMessageDialog(null, text);
	}
	
	public static void cancelReservation(List<Reservation> reservations) {
		String identity= showInputDialog("Informe seu CPF ou seu CNPJ");
		boolean haveReservation = false;
		int counter = -1;
		
		for(Reservation reservation : reservations) {
			counter++;
			if(reservation.getClient() instanceof Physical_Person) {
				Physical_Person physical_person = (Physical_Person) reservation.getClient();
				if(identity.equals(Physical_Person.getCpf())) {
					haveReservation = true;
					break;
				}
			} else if(reservation.getClient() instanceof Legal_Person) {
				Legal_Person legal_person = (Legal_Person) reservation.getClient();
				if(identity.equals(Legal_Person.getCnpj())) {
					haveReservation = true;
					break;
				}
			}
		}
		
		if(haveReservation) {
			reservations.remove(counter);
			showMessageDialog(null, "Reserva Cancelada.");
		} else {
			showMessageDialog(null, "O cliente informado não possui nenhuma reserva.");
		}
	}
}
