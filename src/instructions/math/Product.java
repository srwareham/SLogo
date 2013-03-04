package instructions.math;

import exceptions.IllegalInstructionException;
import instructions.BaseInstruction;
import simulation.Model;

/**
 * Represents a multiplication math operation as an instruction.
 * <br><br>
 * <u> Examples:</u>
 * <br> product 10 10 ---> 100
 * <br> product -4 8 ---> -32
 * 
 * @author Scott Valentine
 * @author Ryan Fishel
 * @author Ellango Jothimurugesan
 *
 */
public class Product extends BaseInstruction {

    /**
     * Eclipse auto-generated ID to implement Serializable interface.
     */
    private static final long serialVersionUID = 5361665342647235501L;
    private static final int NUMBER_OF_ARGUMENTS = 2;

    @Override
    public int execute (Model model) throws IllegalInstructionException {
        return nextOperand().execute(model) * nextOperand().execute(model);
    }

    @Override
    public int getNumberOfArguments () {
        return NUMBER_OF_ARGUMENTS;
    }

}
