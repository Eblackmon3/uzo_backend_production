package Model;

public class StudentJob {
    private int student_id;
    private int company_id;
    private int job_id;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    @Override
    public boolean equals(Object obj) {
      StudentJob studentJob= (StudentJob) obj;
      if(student_id==studentJob.getStudent_id()&& company_id==studentJob.company_id){
          return true;
      }

      return false;
    }

    @Override
    public String toString() {
        return "Company ID:"+company_id+ " Student ID:"+student_id+" for Job ID:"+ job_id;
    }
}
