
public class State {

	private int[] solution;

	public State(int[] solution) {
		this.setSolution(solution);
	}

	public String print() {
		String s = "( ";
		for (int i : solution) {
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

}
