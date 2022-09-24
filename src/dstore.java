import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class dstore implements Serializable {
	dstore save;
	Timestamp time;
    String text;
    public dstore(String input) {
        text = input;
    }
    
    public dstore(dstore input) {
        time = new Timestamp((new Date()).getTime());
        save = input;
    }
}
