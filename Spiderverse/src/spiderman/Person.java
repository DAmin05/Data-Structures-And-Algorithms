package spiderman;

public class Person {

    public String name;
    public int currentDimension;
    public int homeDimension;
    
    public Person(int currentDimension , String name , int homeDimension){
        this.currentDimension = currentDimension;
        this.name = name;
        this.homeDimension = homeDimension;
    }

    public int getcurrentDimension()
    {
        return currentDimension;
    }

    public int gethomeDimension()
    {
        return homeDimension;
    }

}
