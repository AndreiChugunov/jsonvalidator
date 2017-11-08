/**
 * Project jsonvalidator Created by Andrei.
 */
public class JsonError {
    private int id;
    private int errorCode;
    private String errorMessage;
    private String errorPlace;

    public JsonError(int id, int errorCode, String errorMessage, String errorPlace) {
        this.id = id;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorPlace = errorPlace;
    }
}
