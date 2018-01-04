package Model;

public class Student {
    private int student_id;
    private String email;
    private String password;
    private String first_name;
    private String resume_location;
    private String last_name;
    private String university;
    private int total_rating;
    private int times_rated;
    private String phone_number;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getResume_location() {
        return resume_location;
    }

    public void setResume_location(String resume_location) {
        this.resume_location = resume_location;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getTimes_rated() {
        return times_rated;
    }

    public void setTimes_rated(int times_rated) {
        this.times_rated = times_rated;
    }

    public int getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Student Email: "+ email+ " Student Name:"+ first_name+
                " "+ last_name+ "Student id "+ student_id;
    }

    @Override
    public boolean equals(Object obj) {
        Student compare= (Student) obj;
        if(this.student_id==((Student) obj).student_id){
            return true;
        }
        return false;
    }
}
