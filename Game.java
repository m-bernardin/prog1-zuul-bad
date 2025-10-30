/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 7.0
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrance, cliff, dave1, secretTunnel, treasureS, puzzle, spikePit, puzzleD, treasureL, treasureM, dave2, megaDave;
      
        // create the rooms
        entrance = new Room("You stand at the entrance to a great dungeon.");
        cliff = new Room("A steep cliff. You fall off and die.");
        dave1 = new Room("");
        secretTunnel = new Room("");
        treasureS = new Room("");
        puzzle = new Room("");
        spikePit = new Room("");
        puzzleD = new Room("");
        treasureL = new Room("");
        treasureM = new Room("");
        dave2 = new Room("");
        megaDave = new Room("");
        
        
        // initialise room exits
        entrance.setExits(dave1, null, cliff, null);
        cliff.setExits(null, null, null, null);
        dave1.setExits(puzzle, secretTunnel, entrance, spikePit);
        secretTunnel.setExits(treasureM, null, null, dave1);
        treasureS.setExits(dave2, null, null, null);
        puzzle.setExits(treasureS, null, dave1, null);
        treasureS.setExits(dave2, null, null, null);
        spikePit.setExits(puzzleD, dave1, null, null);
        puzzleD.setExits(treasureL, null, null, spikePit);
        treasureL.setExits(dave2, null, null, null);
        treasureM.setExits(dave2, null, null, null);
        dave2.setExits(megaDave, null, null, null);
        megaDave.setExits(null, null, dave2, null);
        
        // start game outside
        currentRoom = entrance;  
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExits("north");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExits("east");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExits("south");
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.getExits("west");
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            // signal that we want to quit
            return true;  
        }
    }
    
    public void printLocationInfo()
    {
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.getExits("north") != null) {
            System.out.print("north ");
        }
        if(currentRoom.getExits("east") != null) {
            System.out.print("east ");
        }
        if(currentRoom.getExits("south") != null) {
            System.out.print("south ");
        }
        if(currentRoom.getExits("west") != null) {
            System.out.print("west ");
        }
        System.out.println();
    }
}
