public class State {
	
	private int[] solution;
	
	public State(int[] solution) {
		this.setSolution(solution);
	}
	
	public String print() {
		String s = "( ";
		for (int i : getSolution()) {
			s += i + "  ";
		}

		return s + ")";
	}

	public int[] getSolution() {
		return solution;
	}

	public void setSolution(int[] solution) {
		this.solution = solution;
	}

	public State movefireFly(State state , Problem problem) {
		int[] solution2 = state.getSolution();
		int[] childSolution = new int[solution.length];
		
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] == solution2[i]) {
				double rand = Math.random();
				if (rand < 0.9) {
					childSolution = problem.assign(childSolution , i , solution[i]);
				}
				else {
					childSolution = problem.assign(childSolution , i , (int) (Math.random() * (problem.clusters.length )));
				}
			}
			else {
				double rand = Math.random();
				if (rand < 0.3) {
					childSolution = problem.assign(childSolution , i , solution[i]);
				}
				else if(rand < 0.75) {
					childSolution = problem.assign(childSolution , i , solution2[i]);
				}
				else {
					childSolution = problem.assign(childSolution , i , (int) (Math.random() * (problem.clusters.length)));
				}
				
			}
		}
		
		return new State(childSolution);
	}
}