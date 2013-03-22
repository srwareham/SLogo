package control;

import exceptions.IllegalInstructionException;
import instructions.BaseInstruction;
import instructions.CompoundInstruction;
import instructions.ConstantInstruction;
import instructions.Instruction;
import instructions.user_defined.VariableInstruction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InstructionMap implements Serializable{

    /**
     * Auto-generated ID for I/O
     */
    private static final long serialVersionUID = -5723192296795370586L;
    
    private Collection<Map<String, BaseInstruction>> myInstructionMaps;
    
    private Map<String, BaseInstruction> myDefaultInstructions;
    private Map<String, BaseInstruction> myUserDefinedVariables;
    private Map<String, BaseInstruction> myUserDefinedFunctions;
    
    private Map<String, BaseInstruction> myLocalVariables;
    
    public InstructionMap(ResourceBundle resource) {
        
        myInstructionMaps = new ArrayList<Map<String,BaseInstruction>>();
        
        
        myDefaultInstructions = new HashMap<String, BaseInstruction>();
        myInstructionMaps.add(myDefaultInstructions);
        myUserDefinedVariables = new HashMap<String, BaseInstruction>();
        myInstructionMaps.add(myUserDefinedVariables);
        myUserDefinedFunctions = new HashMap<String, BaseInstruction>();
        myInstructionMaps.add(myUserDefinedFunctions);
        myLocalVariables = new HashMap<String, BaseInstruction>();
        myInstructionMaps.add(myLocalVariables);
         
        initiateInstructionMap(resource);
    }

    /**
     * Populates myInstructionMap with relevant instructions
     * from the instruction_index.txt file and their keywords from a .properties
     * file
     */
    private void initiateInstructionMap (ResourceBundle resource) {

        InstructionMapFactory imf =
                new InstructionMapFactory(resource);
        myDefaultInstructions = imf.buildInstructionMap();

    }
    /**
     * Adds a new user defined instruction to the environment.
     * 
     * @param keyword associated with the instruction for future calls
     * @param userInstruction is the instruction to be added to the environment.
     */
    public void addInstruction (String keyword, BaseInstruction userInstruction) {
        
        
        
        myInstructionMap.put(keyword, userInstruction);
    }

    /**
     * TODO: comment
     * @param variables
     */
    public void addLocalVariables (CompoundInstruction variables, int[] variableValues) {
        for (int i = 0; i < variables.getSize(); i++) {
            VariableInstruction currentVariable = 
                    (VariableInstruction) variables.getInstruction(i);
            addVariable(currentVariable, variableValues[i]);
        }
    }
    
    /**
     * TODO: comment
     * @param variable
     * @param value
     */
    private void addLocalVariable(VariableInstruction variable, int value) {
        String variableName = variable.getName();
        BaseInstruction variableValue = new ConstantInstruction(value);
        addInstruction(variableName, variableValue);
    }

    /**
     * TODO: Comment
     */
    public void removeLocalVariables (CompoundInstruction variables) {
        for (int i = 0; i < variables.getSize(); i++) {
            VariableInstruction currentVariable = 
                    (VariableInstruction) variables.getInstruction(i);
            String variableName = currentVariable.getName();
            remove(variableName);
        }
    }
    
    public String variablesToString(){
        return null;
    }
    
    public String userDefinedInstructionstoString() {
        return null;
    }
    
    public boolean containsKey(String key) {
        return false;
        // TODO
    }
    
    public BaseInstruction get(String key) throws IllegalInstructionException {
        for(Map<String, BaseInstruction> map : myInstructionMaps){
            if(map.containsKey(key)){
                return map.get(key);
            }
        }
        throw new IllegalInstructionException(key);
    }
    
    
    public void put (String key, Instruction instruction) {
        
    }
    
    /**
     * Deletes an instruction from the environment.
     * Variable scope is implemented by removing the variableInstruction when
     * it is no longer visible.
     * 
     * @param key of the instruction to be deleted.
     */
    public void remove(String key) {
        for (Map<String, BaseInstruction> map : myInstructionMaps){
            if (map.containsKey(key)) {
                map.remove(key);
            }
        }
    }
}
