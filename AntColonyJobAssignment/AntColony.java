public class AntColony {

	private float pheremoneInitialVal = 2;
	private int maxIterations = 100; 
	
	private int currentJob = 0;
	private int numOfAnts = 4;
	Ant[] ants;
	private int n;
	private int m;

	private int[] bestSolution;
	private double bestTourCost = -100000;

	private Cluster[] clusters;
	private Job[] jobs;

	private double[][] heuristic;
	private double[][] pheromone;

	public void initial() {
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
		
		n = jobs.length;
		
		bestSolution = new int[n];
		
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
		m = clusters.length;
		
		

		heuristic = new double[n][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				heuristic[i][j] = 2;
			}
		}
		for (int i = 0; i < n; i++) {
				heuristic[i][m] = 0.1;
		}

		pheromone = new double[n][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m+1; j++) {
				pheromone[i][j] = pheremoneInitialVal;
			}
		}

		ants = new Ant[numOfAnts];
		for (int i = 0; i < numOfAnts; i++)
			ants[i] = new Ant(jobs.length , clusters.length);
	}

	public void solve() {
		// clear pheromones
		for (int i = 0; i < n ; i++)
			for (int j = 0; j < m; j++)
				pheromone[i][j] = pheremoneInitialVal;
		
		int iteration = 0;
		
		while (iteration < maxIterations) {
			setupAnts();
			moveAnts();
			updateTrails();
			iteration++;
		}
		
		System.out.println("Best Solution Cost: " + bestTourCost );
		System.out.println("Best Solution: " + tourToString(bestSolution) + "\n");
	}

	private void setupAnts() {	//ants job0 initializes
		currentJob = -1;
		
		for (int i = 0; i < numOfAnts; i++) {
			int index = (int) (Math.random() * (clusters.length + 1));		//clusters.length shows that job doesn't assign to any cluster
			while (index < clusters.length && !ants[i].canAssign( 0 , jobs[0] , clusters[index] , jobs )) {
				index++;
				if (index >= clusters.length) {
					index -= clusters.length;
					index--;
				}
			}
			ants[i].solution[0] = index;
		}
		currentJob ++;
	}
	
	// Choose the next town for all ants
	private void moveAnts() {
		currentJob++;
		for (; currentJob < jobs.length ; currentJob++) {
			for (Ant a : ants) {
				int clusterIndex = assignJob(a);		//check kon bbin mishe hamishe assign kard in job ro ya momkene gheyre ghabele ghabool ham bede va inke momkene -1 ham khorooji bede?
				a.solution[currentJob] = clusterIndex;
			}
		}
	}

	public int assignJob(Ant ant) {
		double pr = 0.15;
        // sometimes just randomly select
        if (Math.random() < pr) {
        	int clusterIndex = (int) (Math.random() * (clusters.length + 1));		//clusters.length shows that job doesn't assign to any cluster
			while ( clusterIndex < clusters.length && !ant.canAssign( currentJob , jobs[currentJob] , clusters[clusterIndex] , jobs)) {
				clusterIndex++;
			}
			return clusterIndex;
        }
        
        double[] probs = probTo(ant);
        // randomly select according to probs
        double r = Math.random();
        double tot = 0;
        for (int i = 0; i < clusters.length + 1; i++) {
            tot += probs[i];
            if (tot >= r)
                return i;
        }

        throw new RuntimeException("Not supposed to get here.");
	}
	
	private double[] probTo(Ant ant) {
		double[] probs = new double[clusters.length + 1];
		
		int alpha = 3;
		int beta = 1;
		
        double denom = 0.0;
        for (int i = 0; i < clusters.length + 1; i++)
            if (  i == clusters.length || ant.canAssign( currentJob , jobs[currentJob], clusters[i] , jobs ))
                denom += Math.pow(pheromone[currentJob][i], alpha)
                        * Math.pow(heuristic[currentJob][i], beta);


        for (int i = 0; i < clusters.length + 1; i++) {
            if ( i < clusters.length && !ant.canAssign( currentJob , jobs[currentJob], clusters[i] , jobs )) {
                probs[i] = 0.0;
            } else {
                double numerator = Math.pow( pheromone[currentJob][i], alpha )
                        		 * Math.pow( heuristic[currentJob][i], beta );
                probs[i] = numerator / denom;			//probs[j] means propability of assign jobs[currentJob] to cluster j
            }
        }
        
        return probs;
    }

	private void updateTrails() {
		// divided by 2 each path
		
		for (int i = 0; i < pheromone.length; i++) {
			for (int j = 0; j < pheromone[0].length; j++) {
				pheromone[i][j] /= 2;
			}
		}
		
		
		for (Ant ant : ants) {
			double cost = cost(ant);
			for (int jobIndex = 0; jobIndex < ant.solution.length; jobIndex++) {
				int clusterIndex = ant.solution[jobIndex];
				pheromone[jobIndex][clusterIndex] += cost; 
			}
			if (cost > bestTourCost) {
				bestTourCost = cost;
				for (int i = 0; i < ant.solution.length; i++) {
					bestSolution[i] = ant.solution[i];
				}
			}
		}
	}




//	private double cost(Ant ant) {				//should add power utilization
//		int[] solution = ant.solution;
//		int cost = 0;
//		for (int i : solution) {
//			if (i == clusters.length) {
//				cost++;
//			}
//		}
//		return Math.pow( 10/(cost+1) , 5);
//	}
	
	private double cost(Ant ant) {
		int[] solution = ant.solution;
		double[] clustersUtil_core = new double[clusters.length];
		double[] clustersUtil_ram = new double[clusters.length];
//		int cost = 0;
//		for (int i = 0; i < clusters.length; i++) {
//			boolean empty = true;
//			for (int j = 0; j < solution.length; j++) {
//				if (solution[j] == i) {
//					empty = false;
//				}
//			}
//			if (empty) {
//				cost++;
//			}
//		}
//		
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
			cost_core += Math.pow( clustersUtil_core[i] , 2 );
		}
		
		double cost_ram = 0;
		for (int i = 0; i < clusters.length; i++) {
			cost_ram += Math.pow( clustersUtil_ram[i] , 2 );
		}
		
		
//		for (int i : solution) {
//			if (i == clusters.length) {
//				return 0;
//			}
//		}
			
		return cost_core;
	}
	
	private String tourToString(int[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s += "job[" + i + "] -> " + array[i] + "\t";
		}
		return s;
	}


	public static void main(String[] args) {
		AntColony anttsp = new AntColony();
//		anttsp.readFromDB();
		anttsp.initial();
		int maxRepeat = 10;
		int repeat = 0;
		while (repeat < maxRepeat) {
			anttsp.solve();
			repeat++;
		}
	}
}