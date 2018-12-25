import java.util.ArrayList;

public class Problem {
	Cluster[] clusters;
	Job[] jobs;
	
	public Problem() {
		
		clusters = new Cluster[5];
		jobs = new Job[30];

		Job job1 = new Job(0, 120, 1);
		Job job2 = new Job(1, 120, 1);
		Job job3 = new Job(2, 120, 1);
		Job job4 = new Job(3, 120, 1);
		Job job5 = new Job(4, 120, 1);
		Job job6 = new Job(5, 120, 1);
		Job job7 = new Job(6, 120, 1);
		Job job8 = new Job(7, 120, 1);
		Job job9 = new Job(8, 120, 1);
		Job job10 = new Job(9, 120, 1);

		Job job11 = new Job(10, 150, 1);
		Job job12 = new Job(11, 150, 1);
		Job job13 = new Job(12, 150, 1);
		Job job14 = new Job(13, 150, 1);
		Job job15 = new Job(14, 150, 1);
		Job job16 = new Job(15, 150, 1);
		Job job17 = new Job(16, 150, 1);
		Job job18 = new Job(17, 150, 1);
		Job job19 = new Job(18, 150, 1);
		Job job20 = new Job(19, 150, 1);

		Job job21 = new Job(20, 170, 1);
		Job job22 = new Job(21, 170, 1);
		Job job23 = new Job(22, 170, 1);
		Job job24 = new Job(23, 170, 1);
		Job job25 = new Job(24, 170, 1);
		Job job26 = new Job(25, 170, 1);
		Job job27 = new Job(26, 170, 1);
		Job job28 = new Job(27, 170, 1);
		Job job29 = new Job(28, 170, 1);
		Job job30 = new Job(29, 170, 1);

		jobs[0] = job1;
		jobs[1] = job2;
		jobs[2] = job3;
		jobs[3] = job4;
		jobs[4] = job5;
		jobs[5] = job6;
		jobs[6] = job7;
		jobs[7] = job8;
		jobs[8] = job9;
		jobs[9] = job10;

		jobs[10] = job11;
		jobs[11] = job12;
		jobs[12] = job13;
		jobs[13] = job14;
		jobs[14] = job15;
		jobs[15] = job16;
		jobs[16] = job17;
		jobs[17] = job18;
		jobs[18] = job19;
		jobs[19] = job20;

		jobs[20] = job21;
		jobs[21] = job22;
		jobs[22] = job23;
		jobs[23] = job24;
		jobs[24] = job25;
		jobs[25] = job26;
		jobs[26] = job27;
		jobs[27] = job28;
		jobs[28] = job29;
		jobs[29] = job30;

		Cluster cluster1 = new Cluster(0, 1000, 64);
		Cluster cluster2 = new Cluster(1, 1000, 64);
		Cluster cluster3 = new Cluster(2, 1000, 64);
		Cluster cluster4 = new Cluster(3, 1000, 64);
		Cluster cluster5 = new Cluster(4, 1000, 64);
		clusters[0] = cluster1;
		clusters[1] = cluster2;
		clusters[2] = cluster3;
		clusters[3] = cluster4;
		clusters[4] = cluster5;
	}
	
	public double f(State state) {

		int[] solution = state.getSolution();

		double[] clustersUtil_core = new double[clusters.length];
		double[] clustersUtil_ram = new double[clusters.length];

		for (int i = 0; i < solution.length; i++) {
			int clusterIndex = solution[i];
			if (clusterIndex == clusters.length)
				continue;
			clustersUtil_core[clusterIndex] += jobs[i].core;
			clustersUtil_ram[clusterIndex] += jobs[i].ram;
		}

		for (int i = 0; i < clusters.length; i++) {
			clustersUtil_core[i] /= clusters[i].core;
			clustersUtil_ram[i] /= clusters[i].ram;
		}

		double cost_core = 0;
		for (int i = 0; i < clusters.length; i++) {
			cost_core += Math.pow(clustersUtil_core[i], 2);
		}

		double cost_ram = 0;
		for (int i = 0; i < clusters.length; i++) {
			cost_ram += Math.pow(clustersUtil_ram[i], 2);
		}

		return cost_core;
	}
	
	public boolean equal(State s1, State s2) {

		int[] solution1 = s1.getSolution();
		int[] solution2 = s2.getSolution();

		for (int i = 0; i < solution1.length; i++)
			if (solution1[i] != solution2[i])
				return false;

		return true;
	}
	
	public ArrayList<State> initialStates(int n) {

		ArrayList<State> initialStates = new ArrayList<State>();

		int numberOfInitialStates = n;

		for (int k = 0; k < numberOfInitialStates; k++) {

			int[] solution = new int[jobs.length];
			for (int i = 0; i < solution.length; i++) {
				int clusterIndex = (int) (Math.random() * clusters.length);
				// check that jobs[i] can place in clusters[clusterIndex]
				int remainedCore = clusters[clusterIndex].core;
				int remainedRam = clusters[clusterIndex].ram;
				for (int j = 0; j < i; j++) {
					if (solution[j] == clusterIndex) {
						remainedCore -= jobs[j].core;
						remainedRam -= jobs[j].ram;
					}
				}
				if (remainedCore >= jobs[i].core && remainedRam >= jobs[i].ram)
					solution[i] = clusterIndex;
				else
					i--;
			}
			initialStates.add(new State(solution));
		}
		return initialStates;
	}

	public int[] assign(int[] childSolution, int i, int clusterIndex) {
		if (clusterIndex == clusters.length) {
			childSolution[i] = clusterIndex;
			return childSolution;
		}
		int remainedCore = clusters[clusterIndex].core;
		int remainedRam = clusters[clusterIndex].ram;
		for (int j = 0; j < i; j++) {
			if (childSolution[j] == clusterIndex) {
				remainedCore -= jobs[j].core;
				remainedRam -= jobs[j].ram;
			}
		}
		if (remainedCore >= jobs[i].core && remainedRam >= jobs[i].ram) {
			childSolution[i] = clusterIndex;
			return childSolution;
		}
		else {
			for (clusterIndex = 0; clusterIndex < clusters.length; clusterIndex++) {
				remainedCore = clusters[clusterIndex].core;
				remainedRam = clusters[clusterIndex].ram;
				for (int j = 0; j < i; j++) {
					if (childSolution[j] == clusterIndex) {
						remainedCore -= jobs[j].core;
						remainedRam -= jobs[j].ram;
					}
				}
				if (remainedCore >= jobs[i].core && remainedRam >= jobs[i].ram){
					childSolution[i] = clusterIndex;
					return childSolution;
				}
			}
			childSolution[i] = clusters.length;
			return childSolution;
		}
	}
	
	
}

class Cluster {
	public int id;
	public int ram;
	public int core;

	public Cluster(int id, int core, int ram) {
		this.id = id;
		this.core = core;
		this.ram = ram;
	}
}

class Job {
	public int id;
	public int ram;
	public int core;

	public Job(int id, int core, int ram) {
		this.id = id;
		this.core = core;
		this.ram = ram;
	}
}