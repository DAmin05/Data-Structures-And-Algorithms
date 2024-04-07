package restaurant;
/**
 * Use this class to test your Menu method. 
 * This class takes in two arguments:
 * - args[0] is the menu input file
 * - args[1] is the output file
 * 
 * This class:
 * - Reads the input and output file names from args
 * - Instantiates a new RUHungry object
 * - Calls the menu() method 
 * - Sets standard output to the output and prints the restaurant
 *   to that file
 * 
 * To run: java -cp bin restaurant.Menu menu.in menu.out
 * 
 */
public class Menu {
    public static void main(String[] args) {
	
        // 2. Instantiate an RUHungry object
        RUHungry rh = new RUHungry();

	// 3. Call the menu() method to read the menu
        rh.menu("menu.in");

        rh.createStockHashTable("stock.in");

        rh.updatePriceAndProfit();

        /*StdIn.setFile("order1.in");
        int loopCount = StdIn.readInt(); //getting rid of the input number by reading it.
        while(StdIn.hasNextLine())
        {
                int quantity = StdIn.readInt();
                StdIn.readChar();
                String dishname = StdIn.readLine();
                rh.order(dishname, quantity);
        }*/

        /*StdIn.setFile("donate1.in");
        loopCount = StdIn.readInt();
        while(StdIn.hasNextLine())
        {
                int quantity = StdIn.readInt();
                StdIn.readChar();
                String ingredientName = StdIn.readLine();
                rh.donation(ingredientName, quantity);
        }*/

        /*StdIn.setFile("restock1.in");
        loopCount = StdIn.readInt();
        while(StdIn.hasNextLine())
        {
                int quantity = StdIn.readInt();
                StdIn.readChar();
                String ingredientName = StdIn.readLine();
                rh.restock(ingredientName, quantity);
        }*/

        StdIn.setFile("transaction1.in");
        int loopCounter = StdIn.readInt();
        loopCounter = 0;
        while(StdIn.hasNextLine())
        {
                String type = StdIn.readString();
                StdIn.readChar();
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();

                if(type.equals("order"))
                {
                        rh.order(item, amount);
                }
                else if(type.equals("donation"))
                {
                        rh.donation(item, amount);
                }
                else
                {
                        rh.restock(item, amount);
                }
        }

	// 5. Print restaurant
        rh.printRestaurant();
    }
}
