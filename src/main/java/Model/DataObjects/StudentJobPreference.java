package Model.DataObjects;

public class StudentJobPreference {

    private int student_id;
    private String uzo_reason;
    private boolean lift_ability;
    private String mobility;

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public boolean isLift_ability() {
        return lift_ability;
    }

    public String getMobility() {
        return mobility;
    }

    public String getUzo_reason() {
        return uzo_reason;
    }

    public void setLift_ability(boolean lift_ability) {
        this.lift_ability = lift_ability;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public void setUzo_reason(String uzo_reason) {
        this.uzo_reason = uzo_reason;
    }

    @Override
    public String toString() {
        return "Student id: " +student_id;
    }
}
