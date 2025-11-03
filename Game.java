import java.util.*;
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
    private Stack<Room> previousRooms; 
    private Player player;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        createRooms();
        parser = new Parser();
        previousRooms = new Stack();
        play();
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
        dave1 = new Room("in a large room with a strange man. He introduces himself as Dave.");
        secretTunnel = new Room("in a narrow tunnel. It seems to go on forever.");
        treasureS = new Room("in a small and barren room. An equally small chest is in the centre of the room.");
        puzzle = new Room("in a the centre of a perplexing room. A series of levers lay before you, they seem to be connecvted to a numberr of lamps on the wall.");
        spikePit = new Room("in a treacherous room. It seems like falling here would certainly spell your doom.");
        puzzleD = new Room("in a the centre of a perplexing room. Strange carvings line the walls, and a series of levers lay before you.");
        treasureL = new Room("in a large room, bedazzled with gems. A large chest stands proudly in the centre.");
        treasureM = new Room("in a large room. A large chest sits the centre, elevated on a pedestal");
        dave2 = new Room("in a small, cozy room. The same strange man from before is laying on a chair to the side.");
        megaDave = new Room("in a giant room, the walls covered in spikes. Dave stands in the middle, panting. His eyes burn with anger.");
        
        
        // initialise room exits
        entrance.setExit("north", dave1); entrance.setExit("south",cliff);entrance.addItem("Sword", "A small sword. Might be useful if you encounter any dangers.", 1000);
        dave1.setExit("north",puzzle);dave1.setExit("east",secretTunnel);dave1.setExit("south",entrance);dave1.setExit("west",spikePit);
        secretTunnel.setExit("north",treasureM);secretTunnel.setExit("west",dave1);
        treasureS.setExit("north",dave2);
        puzzle.setExit("north",treasureS);puzzle.setExit("south",dave1);
        treasureS.setExit("north",dave2);treasureS.addChest();
        spikePit.setExit("north",puzzleD);spikePit.setExit("east",dave1);
        puzzleD.setExit("north",treasureL);puzzleD.setExit("west",spikePit);
        treasureL.setExit("north",dave2);treasureL.addChest();
        treasureM.setExit("north",dave2);treasureM.addChest();
        dave2.setExit("north",megaDave);
        megaDave.setExit("south",dave2);
        
        // start game outside
        player.setCurrentRoom(entrance);
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

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch(commandWord){
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                wantToQuit = quit(command);
                break;
            case "look":
                printLocationInfo();
                break;
            case "openChest":
                if(player.getCurrentRoom().containsChest()){
                    System.out.println("You open the chest and get a treasure");
                }
                else{
                    System.out.println("This room does not contain a chest...");
                }
                break;
            case "detailedHelp":
                System.out.println(parser.detailedHelp());
                break;
            case "back":
                goBack();
                break;
            case "take":
                pickupItem(command);
                break;
            case "drop":
                dropItem(command);
                break;
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
        Room nextRoom = player.getCurrentRoom().getExits(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRooms.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
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
    
    private void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println(player.getCurrentRoom().getItemInfo());
    }
    
    private void goBack()
    {
        if(previousRooms.empty()){
            System.out.println("Nowhere to go back to!");
        }
        else{
            player.setCurrentRoom(previousRooms.pop());
            printLocationInfo();
        }
    }
    
    public void pickupItem(Command command)
    {
        player.pickupItem(command.getSecondWord());
        System.out.println(player.getPickupMessage());
    }
    
    public void dropItem(Command command)
    {
        player.dropItem(command.getSecondWord());
        System.out.println(player.getDropMessage());
    }
}
