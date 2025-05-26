package First;

/**
 * Represents the impact on the education sector during the war.
 */
public class EducationImpact {
    private int schoolsDestroyed;
    private int displacedStudents;

    public EducationImpact(int schoolsDestroyed, int displacedStudents) {
        this.schoolsDestroyed = schoolsDestroyed;
        this.displacedStudents = displacedStudents;
    }

    // Getters and setters
    public int getSchoolsDestroyed() {
        return schoolsDestroyed;
    }

    public void setSchoolsDestroyed(int schoolsDestroyed) {
        this.schoolsDestroyed = schoolsDestroyed;
    }

    public int getDisplacedStudents() {
        return displacedStudents;
    }

    public void setDisplacedStudents(int displacedStudents) {
        this.displacedStudents = displacedStudents;
    }
}
