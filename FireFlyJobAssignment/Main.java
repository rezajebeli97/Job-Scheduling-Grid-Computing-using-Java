//فرض شده استیت دارای اف بزرگتر بهتر است
public class Main {
	public static void main(String[] args) {
		int numOfFireFlies = 50;
		int maxIteration = 1000;
		
		System.out.println("FireFlies!");
		Problem problem = new Problem();
		FireFlyAlgorithm fireFly = new FireFlyAlgorithm();
		fireFly.algorithm(problem, numOfFireFlies, maxIteration);
	}
}
