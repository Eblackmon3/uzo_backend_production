package Model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;


public class Job {
    private int job_id;
    private boolean completed;
    private Date date;
    private String rate;
    private String dress_code;
    private double duration;
    private boolean open;
    private Time clock_out;
    private Time clock_in;
    private String job_title;
    private int company_id;
    private int time;
    private int captain_id;
    private int coCaptain_id;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Time getClock_in() {
        return clock_in;
    }

    public void setClock_in(Time clock_in) {
        this.clock_in = clock_in;
    }

    public Time getClock_out() {
        return clock_out;
    }

    public void setClock_out(Time clock_out) {
        this.clock_out = clock_out;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCaptain_id() {
        return captain_id;
    }

    public void setCaptain_id(int captain_id) {
        this.captain_id = captain_id;
    }

    public int getCoCaptain_id() {
        return coCaptain_id;
    }

    public void setCoCaptain_id(int coCaptain_id) {
        this.coCaptain_id = coCaptain_id;
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
