import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input_day2.txt"));
		int valid = 0, valid2 = 0;
		
		for (String line : lines) {
			String[] instr = line.split(": ");
			char letter = instr[0].charAt(instr[0].length() - 1);
			int lower_b = Integer.parseInt((String) instr[0].subSequence(0, instr[0].indexOf('-')));
			int upper_b = Integer.parseInt(instr[0].substring(instr[0].indexOf('-') + 1, instr[0].length() - 1).strip());

			if (isValid(letter, lower_b, upper_b, instr[1])) ++valid;
			if ((instr[1].charAt(lower_b - 1) == letter) != (instr[1].charAt(upper_b - 1) == letter)) ++valid2;
		}
		System.out.println(valid);
		System.out.println(valid2);
	}

	private static boolean isValid(char letter, int lower_b, int upper_b, String string) {
		int counter = 0;
		for (char c : string.toCharArray()) if (c == letter) ++counter;
		return counter >= lower_b && counter <= upper_b;
	}
}
