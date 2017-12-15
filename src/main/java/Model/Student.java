package Model;

public class Student {
    private int student_id;
    private String email;
    private String password;
    private boolean on_call;
    private String first_name;
    private String last_name;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOn_call() {
        return on_call;
    }

    public void setOn_call(boolean on_call) {
        this.on_call = on_call;
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

    @Override
    public String toString() {
        return "Student Email: "+ email+ " Student Name:"+ first_name+
                " "+ last_name+ "Student id "+ student_id+" On Call?: "+ on_call;
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
