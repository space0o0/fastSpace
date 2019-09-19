package space.learning.baselibrary.photoChooser.bean;

public class PhotoError {

    private String message;

    public PhotoError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
