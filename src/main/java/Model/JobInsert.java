package Model;



import java.time.LocalDateTime;

public class JobInsert {
    private boolean completed;
    private String date;
    private String rate;
    private String dress_code;
    private double duration;
    private boolean open;
    private String job_title;
    private String company_id;
    private int time;


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDress_code() {
        return dress_code;
    }

    public void setDress_code(String dress_code) {
        this.dress_code = dress_code;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        JobInsert job=(JobInsert)obj;
        if(job.getJob_title().equals(job_title)&&job.getCompany_id()==company_id){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Job Title:"+ job_title;
    }
}
