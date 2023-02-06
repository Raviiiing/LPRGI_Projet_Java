import java.io.Serializable;
import java.sql.ResultSet;

/**
 * <p>
 *     Cette classe permet au serveur de renvoyer une réponse à un client.
 *     Elle contient un message et un objet.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Serializable
 * @see ObjectRequest
 */
public class ObjectResponse implements Serializable {

    private String type;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    private Object result;

    public ObjectResponse(String type, String message, Object result) {
        this.type = type;
        this.message = message;
        this.result = result;
    }
}
