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
        entrance = new Room("at the entrance to a great dungeon.");
        cliff = new Room("at a steep cliff. You fall off and die.");
        dave1 = new Room("in a large room with a strange man. he introduces himself as Dave.");
        secretTunnel = new Room("in a narrow tunnel. It seems to go on forever.");
        treasureS = new Room("in a small and barren room. An equally small chest is in the centre of the room.");
        puzzle = new Room("in a the centre of a perplexing room. A series of levers lay before you, they seem to be connecvted to a numberr of lamps on the wall.");
        spikePit = new Room("in a treacherous room. It seems like falling here would certainly spell your doom.");
        puzzleD = new Room("in a the centre of a perplexing room. Strange carvings line the walls, and a series of levers lay before you.");
        treasureL = new Room("in a large room, bedazzled with gems. A large chest stands proudly in the centre.");
        treasureM = new Room("in a large room. A large chest sits the centre, eolevated on a pedestal");
        dave2 = new Room(" in a small, cozy room. The same strange man from before is laying on a chair to the side.");
        megaDave = new Room(" in a giant room, the walls covered in spikes. Dave stands in the middle, panting. His eyes burn with anger.");
        
        
        // initialise room exits
        entrance.setExit("north", dave1); entrance.setExit("south",cliff);entrance.addItem("Sword", "A small sword. Might be useful if you encounter any dangers.", 1000);
        dave1.setExit("north",puzzle);dave1.setExit("east",secretTunnel);dave1.setExit("south",entrance);dave1.setExit("west",spikePit);
        secretTunnel.setExit("north",treasureM);secretTunnel.setExit("west",dave1);
        treasureS.setExit("north",dave2);
        puzzle.setExit("north",treasureS);puzzle.setExit("south",dave1);
        treasureS.setExit("north",dave2);
        spikePit.setExit("north",puzzleD);spikePit.setExit("east",dave1);
        puzzleD.setExit("north",treasureL);puzzleD.setExit("west",spikePit);
        treasureL.setExit("north",dave2);
        treasureM.setExit("north",dave2);
        dave2.setExit("north",megaDave);
        megaDave.setExit("south",dave2);
        
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
        else if (commandWord.equals("look")) {
            printLocationInfo();
        }
        else if (commandWord.equals("openChest")) {
            if(currentRoom.containsChest()){
                System.out.println("You open the chest and get a treasure");
            }
            else{
                System.out.println("This room does not contain a chest...");
            }
        }
        else if (commandWord.equals("detailedHelp")) {
            System.out.println(parser.detailedHelp());
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
        System.out.println("You wander a great dungeon.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getAllCommands());
        System.out.println();
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
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.getItemInfo());
    }
}
