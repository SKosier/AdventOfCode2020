import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input5.txt"));
		List<Integer> seats = new ArrayList<>();
		int max = -1;
	
		for(String instr : lines) {
			int seatID = calculateSeatID(instr);
			if(seatID >= max) max = seatID;
			seats.add(seatID);
		}
		System.out.println(max);
		
		seats = seats.stream().sorted().collect(Collectors.toList());
		for(int i = 0; i < seats.size(); i++) {
			if(seats.get(i+1) - seats.get(i) > 1) {
				System.out.println(seats.get(i) + 1);
				break;
			}
		}
	}

	private static int calculateSeatID(String instr) {
		int lower = 0, upper = 127, row, seat;
		for(int i = 0; i < 6; i++) {
			if(instr.charAt(i) == 'F') {
				upper = lower + ((upper - lower + 1) / 2) - 1;
			
			} else if(instr.charAt(i) == 'B') {
				lower = lower + ((upper - lower + 1) / 2);
			}
		}
		if(instr.charAt(6) == 'F') row = lower;
		else row = upper;
		
		lower = 0;
		upper = 7;
		for(int i = 0; i < 2; i++) {
			if(instr.charAt(i+7) == 'L') {
				upper = lower + ((upper - lower + 1) / 2) - 1;
			
			} else if(instr.charAt(i+7) == 'R') {
				lower = lower + ((upper - lower + 1) / 2);
			}
		}
		if(instr.charAt(9) == 'L') seat = lower;
		else seat = upper;
		
		return row * 8 + seat;
	}
}
