
public class Reservation implements Payment{
	
	private Client client;
	private boolean cashPayment; //--> corresponde a pagamentoAVista
	
	public Reservation(Client client, boolean cashPayment) {
		this.client = client;
		this.cashPayment = cashPayment;
	}
	
	public Client getClient() {
		return client;
	}
	
	public boolean getCashPayment() {
		return cashPayment;
	}
	
	public double calculatePayment() {
		double value = 3200;
		if(cashPayment) {
			value *= 0.9; //--> caso seja "À vista", o cliente receberá um desconto de 10%
		}
		
		return value;
	}
	
	public String toString() {
		String type_of_Client = ""; //--> corresponde ao tipo_de_Cliente
		
		if(client instanceof Physical_Person) {
			type_of_Client = "Pessoa Física";
		} else if(client instanceof Legal_Person) {
			type_of_Client = "Pessoa Jurídica";
		}
		
		String type_of_Payment; //--> corresponde ao tipo_de_Pagamento
		if(cashPayment) {
			type_of_Payment = "À vista";
		} else {
			type_of_Payment = "Parcelado";
		}
		
		String text = "Tipo de Cliente --> " + type_of_Client + " | Cliente --> " + client.getName() + " | Forma de Pagamento --> " + type_of_Payment;
		return text;
	}

}
