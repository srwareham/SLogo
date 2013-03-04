package instructions.math;

import exceptions.IllegalInstructionException;
import simulation.Model;
import instructions.BaseInstruction;


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
