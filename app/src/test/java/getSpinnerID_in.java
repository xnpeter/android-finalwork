import java.io.Serializable;

public class getSpinnerID_in implements Serializable {
    private String type;

    public getSpinnerID_in(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
