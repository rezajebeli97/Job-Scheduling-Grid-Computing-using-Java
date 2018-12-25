import java.util.ArrayList;

public class ArtificialBeeColony {
	public void algorithm(Problem problem , int numOfEmployedBees, int maxIteration) {
		double bestCost = -10000000;
		State bestState = null;
		
		ArrayList<State> employedBees = problem.initialStates(numOfEmployedBees);
		for (State state : employedBees) {
			double cost = problem.f(state);
			if (cost > bestCost) {
				bestCost = cost;
				bestState = state;
			}
		}
		
		
		for (int i = 0; i < maxIteration; i++) {
			int empBeeNumBefore = employedBees.size();
			employedBees = problem.nextStates(employedBees);
			int empBeeNumAfter = employedBees.size();
			int scoutBees = empBeeNumAfter - empBeeNumBefore;
			for (int j = 0; j < scoutBees; j++) {
				employedBees.add(problem.randomState());
			}
			for (State state : employedBees) {
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
