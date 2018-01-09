/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

public class Hw8 {

	static String test_values[] = {"London","Paris","Berlin","New York","Tokyo","Hong Kong"};
	
	public static void main(String[] args) {
		IR.displayProgramInformation("Hw8", "10-16-17", "Homework 8", "Generic Class Types",
				"Demonstrates use of a modified class of a generic type.",
				"Replaced ArrayList with a regular array.");
		runTests();
		IR.displayEndOfProgram();		
	}

	private static void runTests() {
		GenericStack<String> stack = new GenericStack<>();
		
		System.out.println("---------- < Pree Assignment Tests > ----------");
		tests(stack);
		
		System.out.println("---------- < Post Assignment Tests > ----------");
		for (String value : test_values) stack.push(value);
		tests(stack);
		
		System.out.println("---------- < Pop Clear Test > ----------");
		while (stack.getSize() > 0) System.out.println(stack.pop());
		System.out.println("\n< Clear >\n");
		tests(stack);
	}

	private static void tests(GenericStack<String> stack) {
		System.out.println("Size: " + stack.getSize());
		System.out.println("Peek: " + stack.peek());
		System.out.println("isEmpty: " + true_false(stack.isEmpty()));
		stack.push("success");
		System.out.println("Push / Pop: " + stack.pop());
		System.out.print("toString:\n\n" + stack.toString());
		System.out.println();
	}
	
	private static String true_false(boolean test) {
		if (test) return "true";
		return "false";
	}

}