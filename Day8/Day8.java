import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input8.txt"));
		Set<Integer> linesExecuted = new HashSet<>();
		int accumulator = 0, i = 0;

		while(true) {
			if(linesExecuted.contains(i)) {
				System.out.println(accumulator);
				break;
			}
			
			linesExecuted.add(i);
			String instr = lines.get(i).split(" ")[0];
			int value = Integer.parseInt(lines.get(i).split(" ")[1]);
			
			switch (instr) {
				case "nop":
					++i;
					break;
				case "jmp":
					i = i + value;
					break;
				case "acc":
					accumulator += value;
					++i;
					break;
				default:
					break;
			}
		}
		System.out.println(findInstruction(lines));
	}
	
	public static int findInstruction(List<String> lines) {
		Set<Integer> linesExecuted = new HashSet<>();
		Set<Integer> linesChanged = new HashSet<>();
		int accumulator = 0, i = 0;
		boolean changedInInteration = false;
	
		while(true) {
			if(linesExecuted.contains(i)) {
				changedInInteration = false;
				linesExecuted.clear();
				accumulator = 0;
				i = 0;
				continue;
			}
			
			linesExecuted.add(i);
			String instr = lines.get(i).split(" ")[0];
			int value = Integer.parseInt(lines.get(i).split(" ")[1]);
			
			if(instr.equals("jmp") && changedInInteration == false && !linesChanged.contains(i)) {
				changedInInteration = true;
				linesChanged.add(i);
				instr = "nop";
				
			} else if(instr.equals("nop") && changedInInteration == false && !linesChanged.contains(i)) {
				changedInInteration = true;
				linesChanged.add(i);
				instr = "jmp";
			}
			
			switch (instr) {
				case "nop":
					++i;
					break;
				case "jmp":
					i = i + value;
					break;
				case "acc":
					accumulator += value;
					++i;
					break;
				default:
					break;
			}
			
			if(i >= lines.size()) break;
		}
		return accumulator;
	}
}
