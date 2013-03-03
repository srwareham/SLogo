package control;

import instructions.BaseInstruction;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;
import exceptions.FileSavingException;
import exceptions.IllegalInstructionException;
import exceptions.IncorrectFileFormatException;


/**
 * Represents the current available commands and variables in the workspace
 * 
 * @author Scott Valentine
 * 
 */
public class Environment {

    /** mapping of Instruction keywords to Instruction */
    private Map<String, BaseInstruction> myInstructionMap;

    /**
     * default constructor initiates the instructionMap
     */
    public Environment () {
        initiateInstructionMap();
    }

    /**
     * populates myInstructionMap with relevant instructions
     */
    private void initiateInstructionMap () {

        // TODO: would much rather have the constructor take a ResourceBundle instead of a
        // string that indicates where to find the ResourceBundle
        InstructionMapFactory imf =
                new InstructionMapFactory(InstructionMapFactory.ENGLISH_LANGUAGE);
        myInstructionMap = imf.buildInstructionMap();

    }

    /**
     * adds a new user defined instruction to the environment
     * 
     * @param keyword associated with the instruction for future calls
     * @param userInstruction - instruction to be added to the environment
     */
    public void addUserDefinedFunction (String keyword,
                                        BaseInstruction userInstruction) {
        myInstructionMap.put(keyword, userInstruction);
    }

    /**
     * gives the Instruction associated with the passed keyword
     * 
     * if the keyword is not associated with an Instruction, this
     * will throw an IllegalInstructionExcpection
     * 
     * @param commandName - the keyword for the instruction
     * @return - the Instruction associated with the keyword
     * @throws IllegalInstructionException
     */
    public BaseInstruction systemInstructionSkeleton (String commandName)
                                                                     throws IllegalInstructionException {

        if (!myInstructionMap.containsKey(commandName))
            throw new IllegalInstructionException(commandName);

        return myInstructionMap.get(commandName).copy();

    }

    /**
     * Loads in instructions and variables for the Environment from an
     * InputStream. The source must be something saved by the save() method.
     * 
     * @param is the source to read in from
     * @throws IncorrectFileFormatException if not readable.
     * 
     */
    @SuppressWarnings("unchecked") // will only load from files saved by save()
    public void load (InputStream is) throws IncorrectFileFormatException {
        ObjectInput in;
        try {
            in = new ObjectInputStream(is);
            myInstructionMap = (Map<String, BaseInstruction>) in.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            throw new IncorrectFileFormatException();
        }

    }

    /**
     * Saves instructions and variables to an OutputStream. Used only for
     * reading in at a later point by the load() method.
     * 
     * @param os to write to
     * @throws FileSavingException is an exception thrown if the OutputStream
     *         provided cannot be written to successfully.
     */
    public void save (OutputStream os) throws FileSavingException {
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(os);
            out.writeObject(myInstructionMap);
        }
        catch (IOException e) {
            throw new FileSavingException();
        }

    }

}
