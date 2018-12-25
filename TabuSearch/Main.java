public class Main {
	public static void main(String[] args) {
		System.out.println("Tabu Search!");
		 Problem problem = new Problem();
		 Search search = new Search();
		 search.tabu(problem, 5 , 1000 , 0.01);
	}
}