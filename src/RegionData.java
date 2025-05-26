package First;

import java.util.ArrayList;

/**
 * Represents all war impact data for a specific region and time period.
 */
public class RegionData {
    private String region;
    private int year;
    private String month;
    private ArrayList<WarVictim> victims = new ArrayList<>();

    private WarStats warStats;
    private HealthCareImpact healthImpact;
    private EducationImpact eduImpact;
    private BorderStatus borderStatus;

    public RegionData(String region, int year, String month, WarStats warStats,
                      HealthCareImpact healthImpact, EducationImpact eduImpact,
                      BorderStatus borderStatus) {
        this.region = region;
        this.year = year;
        this.month = month;
        this.warStats = warStats;
        this.healthImpact = healthImpact;
        this.eduImpact = eduImpact;
        this.borderStatus = borderStatus;
    }

    // Getters
    public String getRegion() {
        return region;
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public WarStats getWarStats() {
        return warStats;
    }

    public HealthCareImpact getHealthImpact() {
        return healthImpact;
    }

    public EducationImpact getEduImpact() {
        return eduImpact;
    }

    public BorderStatus getBorderStatus() {
        return borderStatus;
    }

    public ArrayList<WarVictim> getVictims() {
        return victims;
    }

    // Setters
    public void setWarStats(WarStats warStats) {
        this.warStats = warStats;
    }

    public void setHealthImpact(HealthCareImpact healthImpact) {
        this.healthImpact = healthImpact;
    }

    public void setEduImpact(EducationImpact eduImpact) {
        this.eduImpact = eduImpact;
    }

    public void setBorderStatus(BorderStatus borderStatus) {
        this.borderStatus = borderStatus;
    }

    public void addVictim(WarVictim victim) {
        victims.add(victim);
    }

    // Optional update method
    public void updateStats(int martyrs, int wounded, int prisoners) {
        this.warStats.setMartyrs(martyrs);
        this.warStats.setWounded(wounded);
        this.warStats.setPrisoners(prisoners);
    }
}
