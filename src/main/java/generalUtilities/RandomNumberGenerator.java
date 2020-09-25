package generalUtilities;

import static restAssuredUtilities.Reporting.logStep;

import java.util.Random;

public class RandomNumberGenerator {
	/**
	 * Generates a random integer
	 *
	 * @return random integer
	 */
	public static int getRandomNumber() {
		Random random = new Random();
		logStep("Generating a random integer");
		return random.nextInt();
	}

	/**
	 * Generates a random integer with max limit
	 * @param max maximum number limit
	 * @return random number below max
	 */
	public static int getRandomNumber(int max)
	{
		Random random = new Random();
		logStep("Generating Random Number");
		return random.nextInt(max);
	}

	/**
	 * Generates a random integer within a range of min and max
	 * @param min minimum number
	 * @param max maximum number
	 * @return random number between min and max
	 */
	public static int getRandomNumber(int min, int max) {
		logStep("Generating a random integer within a range of "+min+" and "+max);
		int intKeep=-1;
		Random random = new Random();
		do {
			int number = random.nextInt(max);
			if(number> min)
			{
				intKeep = number;
			}
		}while(intKeep<0);
		return intKeep;
	}

}
