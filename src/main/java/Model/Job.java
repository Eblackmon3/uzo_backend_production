package Model;

import org.joda.time.DateTime;

public class Job {
    private int job_id;
    private boolean completed;
    private DateTime date;
    private String rate;
    private String dress_code;
    private double precision;
    private boolean open;
    private DateTime clock_out;
    private DateTime clock_in;
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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
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

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public DateTime getClock_in() {
        return clock_in;
    }

    public void setClock_in(DateTime clock_in) {
        this.clock_in = clock_in;
    }

    public DateTime getClock_out() {
        return clock_out;
    }

    public void setClock_out(DateTime clock_out) {
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
