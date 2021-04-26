
public class Legal_Person extends Client{
	private String cnpj;
	
	//--> Legal_Person corresponde a Pessoa_Juridica
	public Legal_Person(String name, String cnpj) {
		super(name);
		this.cnpj = cnpj;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public String toString() {
		String text = "Nome --> " + this.getName() + " | CNPJ --> " + cnpj;
		return text;
	}
	
}
