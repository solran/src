package overview;

public class SimplifiedTask {
	private String date;
	private int taskVersion;
	private float mean;
	
	
	public SimplifiedTask(String date, int taskVersion, float mean){
		this.date = date;
		this.taskVersion = taskVersion;
		this.mean = mean;
	}

	
	public String toString (){
		return date + " " + taskVersion + " " + mean;	
	}
	
	/* --- Getter & Setter --- */
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}


	public int getTaskVersion() {
		return taskVersion;
	}
	public void setTaskVersion(int taskVersion) {
		this.taskVersion = taskVersion;
	}


	public float getMean() {
		return mean;
	}
	public void setMean(float mean) {
		this.mean = mean;
	}	
}
