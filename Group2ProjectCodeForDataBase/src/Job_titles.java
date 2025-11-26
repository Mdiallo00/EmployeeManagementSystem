public class Job_titles {

    public int job_title_id;
    private String job_title;

    
    public int getJob_title_id() {
        return job_title_id;
    }


    public void setJob_title_id(int job_title_id) {
        this.job_title_id = job_title_id;
    }


    public String getJob_title() {
        return job_title;
    }


    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }


    public Job_titles(int job_title_id, String job_title) {
        this.job_title_id = job_title_id;
        this.job_title = job_title;
    }


    
}
