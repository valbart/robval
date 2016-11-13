package evenements;


/**
 * Evenement affichant un message.
 */
public class EvenementMessage extends Evenement {
	private String message;

	public EvenementMessage(long date, String message){
		super(date);
		this.message = message;
	}

	public void execute(){
		System.out.println(this.getDate() + this.message);
	}
}
