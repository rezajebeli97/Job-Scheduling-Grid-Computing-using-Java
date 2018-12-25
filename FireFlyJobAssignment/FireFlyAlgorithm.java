import java.util.ArrayList;

public class FireFlyAlgorithm {
	public void algorithm(Problem problem , int numOfFireFlies, int maxIteration) {
		double bestCost = -10000000;
		State bestState = null;
		
		ArrayList<State> fireFlies = problem.initialStates(numOfFireFlies);
		for (State state : fireFlies) {
			double cost = problem.f(state);
			if (cost > bestCost) {
				bestCost = cost;
				bestState = state;
			}
		}
		
		
		for (int k = 0; k < maxIteration; k++) {
			
			for (int i = 0; i < fireFlies.size(); i++) {
				for (int j = 0; j < i; j++) {
					if ( problem.f(fireFlies.get(j)) > problem.f(fireFlies.get(i)) ) {
						State s = fireFlies.get(i).movefireFly( fireFlies.get(j), problem );
						fireFlies.set(i, s);
					}
				}
			}
			
			for (State state : fireFlies) {
				double cost = problem.f(state);
				if (cost > bestCost) {
					bestCost = cost;
					bestState = state;
				}
			}
		}
		
		System.out.println(bestCost);
		System.out.println(bestState.print());
		
	}
}
