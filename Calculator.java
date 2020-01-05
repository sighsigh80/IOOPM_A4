package org.ioopm.calculator;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import java.util.HashMap;
import java.io.IOException;
import java.util.Scanner;
import java.io.StringReader;
import java.io.*;
import java.util.*;

/**
 * Class that initializes the necessary data and runs the calculator. 
 */
class Calculator {
    private static int expressionsEntered = 0;
    private static int successEvaluated = 0;
    private static int fullyEvaluated = 0;
    
    	/**
	 * Main function for the calculator.
	 */
    public static void main(String[] args) {
        Environment vars = new Environment();
	boolean event = true;
	int mode = 0;
	System.out.println("Welcome to the parser!");
       	System.out.print("Please enter an expression: ");
	Scanner sc = new Scanner(System.in);
	String expression = sc.nextLine();
	CalculatorParser p = new CalculatorParser(expression);
        final EvaluationVisitor eval = new EvaluationVisitor();
	final FullEvaluationVisitor fullEval = new FullEvaluationVisitor();
	if(args.length > 0){
	    if(args[0].equals("--not-symbolic")) {
		mode++;
	    }
	}
	while(event) {
	    try {
		SymbolicExpression result = p.parse();

		if (result.isCommand()) {
		    if(result == Quit.instance()) {
			event = false;
		    }
		    else if (result == Vars.instance()) {
			System.out.println(vars);
		    }
		    else{
		        vars.clear();
			System.out.println("Variables cleared.");
		    }
		}
		else {
		    
		    SymbolicExpression evaluation;
		    if(mode == 0) {
			evaluation = eval.evaluate(result, vars);
		    }
		    else {
			evaluation = fullEval.evaluate(result, vars);
		    }
		    successEvaluated++;
		    if (evaluation.isConstant() > 0){
		    	fullyEvaluated++;
		    }
		    System.out.println(evaluation);
		}
	    }catch(UndefinedVariableException e) {
	        System.out.print("Error:");
	        System.out.println(e.getMessage());
	    }catch(IllegalExpressionException e) {
		System.out.print("Assignment Error: ");
	        System.out.println(e.getMessage());
	    }catch(SyntaxErrorException e) {
		System.out.print("Syntax Error: ");
		System.out.println(e.getMessage());
	    }catch(IOException e) {
		System.err.println("IO Exception!");
	    }
	    if(event){
	        System.out.println("Please enter an expression: ");
		String str = sc.nextLine();
		expressionsEntered++;
		p.updateString(str);
	    }
	}
	System.out.println("\nprogram terminated.\n"
			   +"\n------Statistics------\n"
			   +"Expressions entered: " + expressionsEntered
			   +"\nSuccessfully evaluated: " + successEvaluated
			   +"\nFully evaluated: " + fullyEvaluated);
    }
}
