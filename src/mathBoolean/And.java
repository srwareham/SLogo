package mathBoolean;

import instructions.Instruction;
import simulation.Model;


/**
 * represents the logical and operation on two values.
 * Example:
 * and 1 0 --> 0
 * and 1 1 --> 1
 * 
 * @author Scott Valentine
 * 
 */
public class And extends DoubleValueMathInstruction {

    public final static String KEYWORD = "AND";

    @Override
    public int execute (Model model) {
        Instruction[] myInstructions = getTwoValues();
        if (myInstructions[0].execute(model) == 1 && myInstructions[1].execute(model) == 1) { return 1; }
        return 0;
    }

}
