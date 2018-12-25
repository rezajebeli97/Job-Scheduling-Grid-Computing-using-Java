
public class Action {
	private int jobIndex;
	private int clusterIndex;
	private int cost;

	public Action(int jobIndex, int clusterIndex, int cost) {
		this.setJobIndex(jobIndex);
		this.setClusterIndex(clusterIndex);
		this.setCost(cost);
	}

	public String print() {
		String s = "( job[" + jobIndex + "] -> cluster[" + clusterIndex + "] )";
		return s;
	}

	public int getJobIndex() {
		return jobIndex;
	}

	public void setJobIndex(int jobIndex) {
		this.jobIndex = jobIndex;
	}

	public int getClusterIndex() {
		return clusterIndex;
	}

	public void setClusterIndex(int clusterIndex) {
		this.clusterIndex = clusterIndex;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
