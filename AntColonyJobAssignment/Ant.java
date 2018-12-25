public class Ant {
	
	public int[] solution;
	
	public Ant(int numOfJobs , int numOfClusters) {
		solution = new int[numOfJobs];
		for (int i = 0; i < solution.length; i++) {
			solution[i] = numOfClusters;
		}
	}

	public boolean canAssign(int jobIndex , Job job, Cluster cluster , Job[] jobs) {
		int freeCore = cluster.core;
		for (int i = 0; i < jobIndex ; i++) {
			if (solution[i] == cluster.id) {
				freeCore -= jobs[i].core;
			}
		}
		int freeRam = cluster.ram;
		for (int i = 0; i < jobIndex ; i++) {
			if (solution[i] == cluster.id) {
				freeRam -= jobs[i].ram;
			}
		}
		
		if (freeCore >= job.core && freeRam >= job.ram )
			return true;
		return false;
	}

}
