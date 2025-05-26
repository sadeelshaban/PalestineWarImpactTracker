package First;

/**
 * Represents the impact on healthcare infrastructure during the war.
 */
public class HealthCareImpact {
    private int hospitalsDestroyed;
    private int untreatedPatients;

    public HealthCareImpact(int hospitalsDestroyed, int untreatedPatients) {
        this.hospitalsDestroyed = hospitalsDestroyed;
        this.untreatedPatients = untreatedPatients;
    }

    // Getters and setters
    public int getHospitalsDestroyed() {
        return hospitalsDestroyed;
    }

    public void setHospitalsDestroyed(int hospitalsDestroyed) {
        this.hospitalsDestroyed = hospitalsDestroyed;
    }

    public int getUntreatedPatients() {
        return untreatedPatients;
    }

    public void setUntreatedPatients(int untreatedPatients) {
        this.untreatedPatients = untreatedPatients;
    }
}
