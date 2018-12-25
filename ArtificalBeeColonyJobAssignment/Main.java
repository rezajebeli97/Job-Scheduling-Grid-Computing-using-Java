//فرض شده استیت دارای اف بزرگتر بهتر است
public class Main {
	public static void main(String[] args) {
		int timeToLive = 100;
		 int numOfEmployedBees = 50;
		 int maxIteration = 1000;
		
		 System.out.println("Bee Colony!");
		 Problem problem = new Problem(timeToLive);
		 ArtificialBeeColony abc = new ArtificialBeeColony();
		 abc.algorithm(problem, numOfEmployedBees, maxIteration);

	}
}
