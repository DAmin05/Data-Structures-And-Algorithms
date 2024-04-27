package spiderman;

public class Person {

    public String name;
    public int currentDimension;
    public int homeDimension;
    public boolean isAnomaly;
    
    public Person(int currentDimension , String name , int homeDimension , boolean isAnomaly){
        this.currentDimension = currentDimension;
        this.name = name;
        this.homeDimension = homeDimension;
        this.isAnomaly = isAnomaly;
    }

    public int currentDimension()
    {
        return currentDimension;
    }

    public int homeDimension()
    {
        return homeDimension;
    }

    public boolean isAnomaly()
    {
        return isAnomaly;
    }
}
