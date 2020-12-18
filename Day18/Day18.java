import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Day18 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input18.txt"));
		long sum1 = 0L, sum2 = 0L;
		for (String line : lines) {
			sum1 += evaluateExpression(line, false);
			sum2 += evaluateExpression(line, true);
		}
		System.out.println(sum1);
		System.out.println(sum2);
	}

	private static long evaluateExpression(String line, boolean isSecond) {
		Stack<String> stack = new Stack<>();
		for (char c : line.toCharArray()) {
			if (Character.isWhitespace(c)) continue;
			else if (c == ')') {
				Stack<String> parentheses = new Stack<>();
				while (!stack.peek().equals("(")) {
					parentheses.push(stack.pop());
				}
				stack.pop();
				String result = isSecond ? calculateParentheses2(parentheses) : calculateParentheses(parentheses);
				stack.push(result);

			} else {
				stack.push(String.valueOf(c));
			}
		}
		
		Stack<String> parentheses = new Stack<>();
		while (stack.size() > 0) {
			parentheses.push(stack.pop());
		}
		return isSecond ? Long.parseLong(calculateParentheses2(parentheses)) : Long.parseLong(calculateParentheses(parentheses));
	}

	private static String calculateParentheses(Stack<String> parentheses) {
		while (parentheses.size() > 1) {
			long a = Long.parseLong(parentheses.pop());
			String operator = parentheses.pop();
			long b = Long.parseLong(parentheses.pop());

			if (operator.equals("+")) {
				parentheses.push(String.valueOf(a + b));

			} else if (operator.equals("*")) {
				parentheses.push(String.valueOf(a * b));
			}
		}
		return parentheses.pop();
	}
	
	private static String calculateParentheses2(Stack<String> parentheses) {
		Queue<String> products = new ArrayBlockingQueue<String>(parentheses.size());
		while (parentheses.size() > 1) {
			long a = Long.parseLong(parentheses.pop());
			String operator = parentheses.pop();
			long b = Long.parseLong(parentheses.pop());

			if (operator.equals("+")) {
				parentheses.push(String.valueOf(a + b));

			} else if (operator.equals("*")) {
				parentheses.push(String.valueOf(b));
				products.add(String.valueOf(a));
			}
		}
		
		long product = 1L;
		while(!products.isEmpty()) product *= Long.parseLong(products.poll());
		return parentheses.isEmpty() ? String.valueOf(product) : String.valueOf(product * Long.parseLong(parentheses.pop()));
	}
}
