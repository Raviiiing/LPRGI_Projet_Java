import java.io.Serializable;

/**
 * <p>
 *     Cette classe permet de prendre en charge un objet pour le préparer à une requête au serveur.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Serializable
 * @see Livre
 * @see Lecteur
 */
public class ObjectRequest implements Serializable {

    private String type;
    private Object object;

    public ObjectRequest(String type, Object object) {
        this.type = type;
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
