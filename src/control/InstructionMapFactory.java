package control;

import instructions.Instruction;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Creates a map for all of the instructions in the user defined text file of
 * each instruction's
 * keyword to an instance of the instruction.
 * 
 * @author Scott Valentine
 * 
 */
public class InstructionMapFactory {

    /** location of all instruction classpath data */
    public static final String INSTRUCTION_INDEX_FILE =
            "/src/resources/instruction_index.txt";

    /** default location of the resouces package */
    // TODO: make one of these (currently one here and one in view)
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources.";

    /** Default Language */
    // TODO: make one of these (currently one here and one in view)
    public static final String ENGLISH = "English";

    /**
     * character that indicates a comment when places at beginning of line of
     * Instruction index file
     */
    private static final char COMMENT_CHARACTER = '#';

    /** string that splits elements on a line in instruction index file */
    private static final String SPLITTING_STRING = " ";

    /** resources for SLogo */
    // TODO: we currently have two of these (one for view and one here), want
    // only one
    private ResourceBundle myResources;

    /**
     * instantiates the factory based on the language to be used for the
     * commands
     * 
     * @param language - language of the commands (must be file in resource
     *        folder)
     * @throws FileNotFoundException - if the resource bundle cannot be found
     */
    public InstructionMapFactory(String language) {
        try {
            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE 
                                                  + language);                 
        } 
        catch (MissingResourceException e) {
            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE 
                                                  + ENGLISH);
        }
    }

    /**
     * 
     * builds an Instruction an instruction map from the file where the names
     * are stored
     * 
     * @param filename - name of file where instruction class names are stored
     * @return - a map of keywords to instructions
     * @throws FileNotFoundException - if the instruction_index file is not
     *         found
     */
    public Map<String, Instruction> buildInstructionMap(String filename) 
        throws FileNotFoundException {
                                                                        

        String currentDirectory = System.getProperty("user.dir");

        FileReader fileToBeRead = new FileReader(currentDirectory + filename);
        Scanner line = new Scanner(fileToBeRead);

        Map<String, Instruction> instructionMap =
                new HashMap<String, Instruction>();

        while (line.hasNextLine()) {
            String nextLine = line.nextLine();
            parseLine(instructionMap, nextLine);
        }
        return instructionMap;
    }

    /**
     * parses line of instruction and keyword and adds it to the instruction map
     * 
     * @param instructionMap - map of keywords to instructions
     * @param line - current line being read
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    private void parseLine(Map<String, Instruction> instructionMap, String line) {
        if (line.charAt(0) != COMMENT_CHARACTER && line.length() > 0) {
            String[] params = line.split(SPLITTING_STRING);
            Class<?> instruction;
            try {
                instruction = Class.forName(params[0]);
            } 
            catch (ClassNotFoundException e1) {
                return;
            }

            Instruction instruct;
            try {
                instruct = (Instruction) instruction.newInstance();
            } 
            catch (InstantiationException e) {
                return;
            } 
            catch (IllegalAccessException e) {
                return;
            }

            // gets parameters from line
            for (int i = 1; i < params.length; ++i) {
                instructionMap.put(myResources.getString(params[i]), instruct);

            }
        }
    }

}