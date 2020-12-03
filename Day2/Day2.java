import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input_day2.txt"));
		int valid = 0;
		int valid2 = 0;
		for(String line : lines) {
			String[] instr = line.split(": ");
			char letter = instr[0].charAt(instr[0].length()-1);
			int lower_b = Integer.parseInt((String) instr[0].subSequence(0, instr[0].indexOf('-')));
			int upper_b = Integer.parseInt(instr[0].substring(instr[0].indexOf('-')+1, instr[0].length()-1).strip());
			
			if(isValid(letter, lower_b-1, upper_b-1, instr[1])) ++valid;
			if(isValid2(letter, lower_b-1, upper_b-1, instr[1])) ++valid2;
		}
		System.out.println(valid);
		System.out.println(valid2);
	}

	private static boolean isValid2(char letter, int lower_b, int upper_b, String string) {
		if(string.charAt(lower_b) == letter && string.charAt(upper_b) != letter) return true;
		if(string.charAt(lower_b) != letter && string.charAt(upper_b) == letter) return true;
		return false;
	}

	private static boolean isValid(char letter, int lower_b, int upper_b, String string) {
		int counter = 0;
		for(char c : string.toCharArray()) if (c == letter) ++counter;
		return counter >= lower_b && counter <=upper_b;
	}
}
