import java.util.*;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 7.0
 */

public class CommandWords
{
    // A constant array that holds all valid command words.
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "openChest", "detailedHelp", "back", "take", "drop", "items", "drink"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString)) {
                return true;
            }
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    public String getAllCommands()
    {
        String exits = "Exits:";
        Arrays.sort(validCommands);
        exits = Arrays.toString(validCommands);
        return exits;
    }
    
    public HashMap buildCommandDescriptions()
    {
        HashMap<String, String> descriptions = new HashMap<>();
        descriptions.put("go", "Takes a direction as an input and goes to the next room in that direction");
        descriptions.put("quit", "Exits then game.");
        descriptions.put("help", "Prints a list of available commands");
        descriptions.put("look", "Look around the current room");
        descriptions.put("openChest", "Opens the chest in the current room, if there is one.");
        //descriptions.put("",""); (format for adding new descriptions)
        descriptions.put("detailedHelp","Provides a list of commands with a description of each one.");
        descriptions.put("back","Sends you back to the previous room.");
        descriptions.put("take","Picks up a designated item in the current room.");
        descriptions.put("drop","Drops an item currently being held.");
        descriptions.put("items","Prints all currently held items and their total weight.");
        descriptions.put("drink","Drink the specified potion in the current room.");
        for(int i = 0; i<validCommands.length; ++i){
            descriptions.putIfAbsent(validCommands[i], "no description");
        }
        return descriptions;
    }
    
    public String detailedHelp()
    {
        HashMap<String, String> commands = buildCommandDescriptions();
        String detailedHelpString = "Available commands: \n";
        for(String command:commands.keySet()){
            detailedHelpString = detailedHelpString + command + ": " + commands.get(command) + "\n";
        }
        return detailedHelpString;
    }
}