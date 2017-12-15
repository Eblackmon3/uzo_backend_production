package Model;

import java.time.LocalDateTime;


public class Job {
    private int job_id;
    private boolean completed;
    private LocalDateTime date;
    private String rate;
    private String dress_code;
    private double duration;
    private boolean open;
    private LocalDateTime clock_out;
    private LocalDateTime clock_in;
    private String job_title;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public LocalDateTime getClock_in() {
        return clock_in;
    }

    public void setClock_in(LocalDateTime clock_in) {
        this.clock_in = clock_in;
    }

    public LocalDateTime getClock_out() {
        return clock_out;
    }

    public void setClock_out(LocalDateTime clock_out) {
        this.clock_out = clock_out;
    }

    @Override
    public boolean equals(Object obj) {
        Job job2= (Job) obj;

        if(job2.getJob_id()==job_id){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Job Title:"+ job_title+ " Job ID: "+ job_id;
    }
}
