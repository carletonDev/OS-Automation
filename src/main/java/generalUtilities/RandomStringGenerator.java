package generalUtilities;

import static restAssuredUtilities.Reporting.logStep;

import com.github.javafaker.Faker;

public class RandomStringGenerator {
	private static char[] characters = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static boolean allowCapitals = false;
	public static Faker faker = new Faker();

	/**
	 * Generates a random string within a range of minLength and maxLength
	 *
	 * @param minLength minimum length of string
	 * @param maxLength maximum lnegth of string
	 * @return random string with length between minLength and maxLength
	 */
	private static String randomString(int minLength, int maxLength) {
		logStep("Generating a random string within a range of "+ minLength+" and "+maxLength);
		int intIndex = 0;

		int intStringLength = RandomNumberGenerator.getRandomNumber(minLength, maxLength);
		char[] charArrStringToBe = new char[intStringLength];

		do {
			charArrStringToBe[intIndex] = characters[RandomNumberGenerator.getRandomNumber(characters.length - 1)];
			if (allowCapitals && RandomNumberGenerator.getRandomNumber(intStringLength) > (intStringLength / 2)) {
				charArrStringToBe[intIndex] = Character.toUpperCase(charArrStringToBe[intIndex]);
			}
			intIndex++;
		} while (intIndex < intStringLength);

		return new String(charArrStringToBe);
	}

	/**
	 * Generates a random string with a range of length between 2 and 10
	 *
	 * @return random string with length between 2 and 10
	 */
	public static String generateRandomString() {
		logStep("Generating a random string with a range of length between 2 and 10");
		return randomString(2, 10);
	}

	/**
	 * Generates a random string with a range of length between 2 and max
	 *
	 * @param max maximum length
	 * @return random string with length between 2 and max
	 */
	public static String generateRandomString(int max) {
		logStep("Generates a random string with a range of length between 2 and "+max);
		return randomString(2, max);
	}

	/**
	 * Generates a random string with a range of length between min and max
	 *
	 * @param min minimum length
	 * @param max maximum length
	 * @return random string with length between min and max
	 */
	public static String generateRandomString(int min, int max) {
		return randomString(min, max);
	}

	/**
	 * Generates a random string with specified word count
	 *
	 * @param wordCount number of word
	 * @return random sentence with specified word count
	 */
	public static String generateRandomSentence(int wordCount) {
		logStep("Generating random sentence");
		String strResponse = null;
		// strResponse = faker.harryPotter().quote();
		strResponse = faker.lorem().sentence(wordCount);
		return strResponse;
	}

	/**
	 * Generates a paragraph with specified sentence count
	 *
	 * @param sentenceCount number of sentence
	 * @return random paragraph with specified sentence count
	 */
	public static String generateRandomParagraph(int sentenceCount) {
		logStep("Generating random paragraph");
		String strResponse = null;
		strResponse = faker.lorem().paragraph(sentenceCount);
		// strResponse = faker.harryPotter().quote();
		return strResponse;
	}

}
