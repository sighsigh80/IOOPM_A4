package org.ioopm.calculator.ast;
import java.util.HashMap;

/** 
 * Top-class of the AST that all other node-types inherites from.
 */
public abstract class SymbolicExpression {

    /** 
     *  Used to check whether a SymbolicExpression is a variable, namedconstant, or other.
     * @return 0 if other, 1 if variable, or 2 if namedconstant.
     */
    public int isConstant() {
	return 0;
    }

    /** 
     * gets the name (or symbol) of a perticular SymbolicExpression.
     * @return the name(or symbol) of the SymbolicExpression as a string. Commands will throw exception.
     * @throws RuntimeException.
     */
    public String getName() {
	throw new RuntimeException("getName() called on expression with no operator");
    }
    
    /** 
     * Returns the priority of a preticular SymbolicExpression. Used for correct parenthasis placement.
     * @return int representing SymbolicExpression priority.
     */
    public int getPriority() {
	return 0;
    }

    /** 
     * Fetches the numeric value of a SymbolicExpression if able.
     * @return double with SymbolicExpression value if able.
     * @throws RuntimeException.
     */
    public double getValue() {
	throw new RuntimeException("getValue() called on non-constant");
    }

    /** 
     * Evaluate a SymbolicExpression tree.
     * @param Enviorment with hashmap of user assigned Veriables.
     * @return Evaluated SymbolicExpression (if possible).
     */
    public abstract SymbolicExpression eval(Environment vars);

    public boolean isCommand() {
	return false;
    }
}