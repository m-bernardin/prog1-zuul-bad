
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    String description;
    int weight;
    String name;
    
    public Item(String description, int weight, String name)
    {
        this.description = description;       
        this.weight = weight;
        this.name = name;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getItemInfo()
    {
        return name + ": " + description + ", " + weight + "g";
    }
}