import java.util.*;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private Room currentRoom;
    private int carryingWeight;
    private HashMap<String, Item> items;
    private String pickupMessage;
    private String dropMessage;
    
    public Player()
    {
        carryingWeight = 1000;
        items = new HashMap<>();
    }

    public void setCarryingWeight(int weight)
    {
        carryingWeight=weight;
    }
    
    public void setCurrentRoom(Room room)
    {
        currentRoom=room;
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public int getCarryingWeight()
    {
        return carryingWeight;
    }
    
    public void pickupItem(String item)
    {
        HashMap<String, Item> roomItems = currentRoom.getItems();
        if(roomItems.isEmpty()){
            pickupMessage = "No item to pick up!";
        }
        else{
            if(roomItems.get(item)==null){
                pickupMessage = "This item isn't in the room!";
            }
            else{
                items.put(item, roomItems.get(item));
                currentRoom.removeItem(item);
                pickupMessage = "Picked up the " + item + "!";
            }
        }
    }
    
    public String getPickupMessage()
    {
        return pickupMessage;
    }
    
    public void dropItem(String item)
    {
        if(items.get(item)==null){
            dropMessage="You're not holding this item!";
        }
        else{
            currentRoom.dropItem(items.get(item));
            items.remove(items.get(item));
            dropMessage="Dropped the " + item + "!";
        }
    }

    public String getDropMessage()
    {
        return dropMessage;
    }
    
    public String getHeldItems()
    {
        String heldItems = "Items: ";
        for(String item:items.keySet()){
            heldItems = heldItems + items.get(item) + ", ";
        }
        return heldItems;
    }
}