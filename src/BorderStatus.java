package First;

/**
 * Represents the border status in a specific region and time.
 * This class is used as part of RegionData to describe border conditions.
 */
public class BorderStatus {
    private String status; 

    public BorderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
