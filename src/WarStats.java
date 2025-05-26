package First;

/**
 * Holds war-related statistics: martyrs, wounded, and prisoners.
 */
public class WarStats {
    private int martyrs;
    private int wounded;
    private int prisoners;

    public WarStats(int martyrs, int wounded, int prisoners) {
        this.martyrs = martyrs;
        this.wounded = wounded;
        this.prisoners = prisoners;
    }

    // Getters and setters
    public int getMartyrs() {
        return martyrs;
    }

    public void setMartyrs(int martyrs) {
        this.martyrs = martyrs;
    }

    public int getWounded() {
        return wounded;
    }

    public void setWounded(int wounded) {
        this.wounded = wounded;
    }

    public int getPrisoners() {
        return prisoners;
    }

    public void setPrisoners(int prisoners) {
        this.prisoners = prisoners;
    }
}
