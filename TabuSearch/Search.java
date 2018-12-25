import java.util.ArrayList;

public class Search {

	public void tabu(Problem problem, int tabuTenure, int maxIteration , double k) {
		State p = problem.initialState();
		ArrayList<State> myPath = new ArrayList<State>();
		ArrayList<Action> myActions = new ArrayList<Action>(); // close list
		State best = p;
		double bestWorth = problem.f(p);
		int[] tabu = new int[p.getSolution().length];
		int[] frequency = new int[p.getSolution().length];
		myPath.add(p);
		int observedNodes = 1;
		int extendedNodes = 0;
		while (maxIteration > 0) {
			maxIteration--;
			ArrayList<Action> actions = problem.actions(p);
			State bestNeighbour = p;
			Action bestAction = null;
			double maxCostFrequency = 0;
			for (int i = 0; i < actions.size(); i++) { // find best neighbour which has minimum f value
				State neighbour = problem.result(p, actions.get(i));
				if (problem.contains(myPath, neighbour)) {
					continue; // TODO momkene bug bede tedade hamsaye ha 0 beshe. dar in soorat
								// bestNeighbour==p va kharej mishim.
				}
				double neighbourWorth = problem.f(neighbour);
				double neighbourWorthFrequency = neighbourWorth - k * frequency[actions.get(i).getJobIndex()];
				observedNodes++;
				if (tabu[actions.get(i).getJobIndex()] > 0 && neighbourWorth <= problem.f(best)) {
					continue;
				}
				if (neighbourWorthFrequency > maxCostFrequency) {
					maxCostFrequency = neighbourWorthFrequency;
					bestNeighbour = neighbour;
					bestAction = actions.get(i);
				}
			}
			if (bestNeighbour == p) {
				break;
			}
			p = bestNeighbour;
			tabu[bestAction.getJobIndex()] = tabuTenure;
			for (int i = 0; i < tabu.length; i++) {
				if (tabu[i] > 0)
					tabu[i]--;
			}
			frequency[bestAction.getJobIndex()]++;
			extendedNodes++;
			if ( problem.f(bestNeighbour) > bestWorth) {
				best = bestNeighbour;
				bestWorth = problem.f(bestNeighbour);
			}
			myPath.add(bestNeighbour);
			myActions.add(bestAction);
		}

		printDetailsHillClimbing(problem, observedNodes, extendedNodes, myPath, myActions , best);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void printDetailsHillClimbing(Problem problem, int observedNodes, int extendedNodes,
			ArrayList<State> myPath, ArrayList<Action> myActions, State best) {
		System.out.println("Number of observed nodes : " + observedNodes);
		System.out.println("Number of extended nodes : " + extendedNodes);
//		System.out.print("Path states : ");
//		for (State state : myPath) {
//			System.out.print(state.print() + " ");
//		}
//		System.out.println();
		double cost = 0;
//		System.out.print("Path actions : ");
		for (Action action : myActions) {
			cost += action.getCost();
//			System.out.print(action.print() + " ");
		}
//		System.out.println();
		System.out.println("Path cost : " + cost);
		System.out.println("Final state worth : " + problem.f(best));
		System.out.println("Final Answer : " + best.print());
	}

}
