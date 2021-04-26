
public class Physical_Person extends Client{
	private String cpf;
	
	//--> Physical_Person corresponde a Pessoa_Fisica
	public Physical_Person(String name, String cpf) {
		super(name);
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String toString() {
		String text = "Nome --> " + this.getName() + " | CPF --> " + cpf;
		return text;
	}
	
}
