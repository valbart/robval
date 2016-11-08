
package evenements;
import java.util.LinkedList;
import robot.*;
import terrain.*;
import java.util.*;

/**
 * Evenement affichant un messaage.
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
