package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstStateNode;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstStateNode = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstStateNode;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine(); // Skips header
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] line_array = inputLine.split(",");
        String state = line_array[2];

        StateNode currentNode = firstStateNode;
        while(currentNode != null)
        {
            if(currentNode.getName().equals(state))
            {
                return; //skips if the state already exists
            }
            currentNode = currentNode.getNext();
        }

        StateNode newStateNode = new StateNode();
        newStateNode.setName(state);
        
        if(firstStateNode == null) //if list is empty, set firstStateNode to newStateNode
        {
            firstStateNode = newStateNode;
        }
        else //find the last state and add new state after
        {
            currentNode = firstStateNode; //set currentNode to firstStateNode for else statement
            while(currentNode.getNext()!=null)
            {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(newStateNode);
        }
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] line_array = inputLine.split(",");
        String state = line_array[2];
        String county = line_array[1];

        StateNode currentStateNode = firstStateNode;
        while(!currentStateNode.getName().equals(state))
        {
            currentStateNode = currentStateNode.getNext(); //sets currentStateNode to where state is
        }

        CountyNode firstCountyNode = currentStateNode.getDown();
        CountyNode currentCountyNode = firstCountyNode;
        
        while(currentCountyNode != null)
        {
            if(currentCountyNode.getName().equals(county)) //gets the county node from state and checks it
            {
                return; //skips if county already exist
            }
            currentCountyNode = currentCountyNode.getNext();
        }

        CountyNode newCountyNode = new CountyNode();
        newCountyNode.setName(county);

        if(firstCountyNode == null)
        {
            firstCountyNode = newCountyNode;
            currentStateNode.setDown(firstCountyNode); //setting firstCountyNode to the state
        }
        else
        {
            currentCountyNode = firstCountyNode;
            while(currentCountyNode.getNext() != null)
            {
                currentCountyNode = currentCountyNode.getNext();
            }
            currentCountyNode.setNext(newCountyNode);
        }
    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] line_array = inputLine.split(",");
        String state = line_array[2];
        String county = line_array[1];
        String community = line_array[0];

        double prcntAfricanAmerican = Double.parseDouble(line_array[3]); 
        double prcntNative = Double.parseDouble(line_array[4]);
        double prcntAsian = Double.parseDouble(line_array[5]);
        double prcntWhite = Double.parseDouble(line_array[8]); 
        double prcntHispanic = Double.parseDouble(line_array[9]); 
        String disadvantaged = line_array[19];
        double PMlevel = Double.parseDouble(line_array[49]);
        double chanceOfFlood = Double.parseDouble(line_array[37]); 
        double povertyLine = Double.parseDouble(line_array[121]);

        Data data = new Data(prcntAfricanAmerican, prcntNative, prcntAsian, prcntWhite, prcntHispanic, disadvantaged, PMlevel, chanceOfFlood, povertyLine);

        StateNode currentStateNode = firstStateNode;
        while(!currentStateNode.getName().equals(state))
        {
            currentStateNode = currentStateNode.getNext(); //sets currentStateNode to where state is
        }

        CountyNode currentCountyNode = currentStateNode.getDown();
        while(!currentCountyNode.getName().equals(county))
        {
            currentCountyNode = currentCountyNode.getNext(); //sets currentCountyNode to where county is
        }

        CommunityNode firstCommunityNode = currentCountyNode.getDown();
        CommunityNode currentCommunityNode = firstCommunityNode;

        while(currentCommunityNode != null)
        {
            if(currentCommunityNode.getName().equals(community))
            {
                return; //skips if community already exists
            }
            currentCommunityNode = currentCommunityNode.getNext();
        }

        CommunityNode newCommunityNode = new CommunityNode();
        newCommunityNode.setName(community);
        newCommunityNode.setInfo(data);

        if(firstCommunityNode == null)
        {
            firstCommunityNode = newCommunityNode;
            currentCountyNode.setDown(firstCommunityNode);
        }
        else
        {
            currentCommunityNode = firstCommunityNode;
            while(currentCommunityNode.getNext() != null)
            {
                currentCommunityNode = currentCommunityNode.getNext();
            }
            currentCommunityNode.setNext(newCommunityNode);       
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        // WRITE YOUR CODE HERE
        userPrcntage = userPrcntage/100;
        int counter = 0;
        for(StateNode a = firstStateNode ; a!=null ; a = a.getNext())
        {
            for(CountyNode b = a.getDown() ; b!=null ; b = b.getNext())
            {
                for(CommunityNode c = b.getDown() ; c!=null ; c = c.getNext())
                {
                    if(race.equals("African American") && (c.getInfo().getPrcntAfricanAmerican() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("True")))
                    {
                        counter++;
                    }
                    else if(race.equals("Native American") && (c.getInfo().getPrcntNative() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("True")))
                    {
                        counter++;
                    }
                    else if(race.equals("Asian American") && (c.getInfo().getPrcntAsian() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("True")))
                    {
                        counter++;
                    }
                    else if(race.equals("White American") && (c.getInfo().getPrcntWhite() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("True")))
                    {
                        counter++;
                    }
                    else if(race.equals("Hispanic American") && (c.getInfo().getPrcntHispanic() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("True")))
                    {
                        counter++;
                    }
                }
            }
        }
        return counter;// update this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        //WRITE YOUR CODE HERE
        userPrcntage = userPrcntage/100;
        int counter = 0;
        for(StateNode a = firstStateNode ; a!=null ; a = a.getNext())
        {
            for(CountyNode b = a.getDown() ; b!=null ; b = b.getNext())
            {
                for(CommunityNode c = b.getDown() ; c!=null ; c = c.getNext())
                {
                    if(race.equals("African American") && (c.getInfo().getPrcntAfricanAmerican() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("False")))
                    {
                        counter++;
                    }
                    else if(race.equals("Native American") && (c.getInfo().getPrcntNative() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("False")))
                    {
                        counter++;
                    }
                    else if(race.equals("Asian American") && (c.getInfo().getPrcntAsian() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("False")))
                    {
                        counter++;
                    }
                    else if(race.equals("White American") && (c.getInfo().getPrcntWhite() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("False")))
                    {
                        counter++;
                    }
                    else if(race.equals("Hispanic American") && (c.getInfo().getPrcntHispanic() >= userPrcntage) && (c.getInfo().getAdvantageStatus().equals("False")))
                    {
                        counter++;
                    }
                }
            }
        }
        return counter;// update this line
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        // WRITE YOUR METHOD HERE
        ArrayList<StateNode> states_PMLevels = new ArrayList<StateNode>();

        for(StateNode a = firstStateNode ; a!=null ; a = a.getNext())
        {
            for(CountyNode b = a.getDown() ; b!=null ; b = b.getNext())
            {
                for(CommunityNode c = b.getDown() ; c!=null ; c = c.getNext())
                {
                    
                    if(c.getInfo().getPMlevel() >= PMlevel) // checking pm levels
                    {
                        if(states_PMLevels.size() != 0) 
                        {
                            if(!states_PMLevels.contains(a)) // checks if the state already exists
                            {
                                states_PMLevels.add(a);
                            }
                        }
                        else
                        {
                            states_PMLevels.add(a);
                        }
                    }
                }
            }
        }
	
        return states_PMLevels; // update this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

	    // WRITE YOUR METHOD HERE
        int numCommunities = 0;

        for(StateNode a = firstStateNode ; a!=null ; a = a.getNext())
        {
            for(CountyNode b = a.getDown() ; b!=null ; b = b.getNext())
            {
                for(CommunityNode c = b.getDown() ; c!=null ; c = c.getNext())
                {
                    if(c.getInfo().getChanceOfFlood() >= userPercntage)
                    {
                        numCommunities++;
                    }
                }
            }
        }
        
        return numCommunities; // update this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

	//WRITE YOUR METHOD HERE
        ArrayList<CommunityNode> lowestIncome_arraylist = new ArrayList<CommunityNode>();

        StateNode currentStateNode = firstStateNode;
        while(!currentStateNode.getName().equals(stateName) && currentStateNode != null)
        {
            currentStateNode = currentStateNode.getNext(); //sets currentStateNode to where state is
        }
        
        for(CountyNode a = currentStateNode.getDown() ; a!=null ; a = a.getNext())
        {
            for(CommunityNode b = a.getDown() ; b!=null ; b=b.getNext())
            {
                if(lowestIncome_arraylist.size() < 10)
                {
                    lowestIncome_arraylist.add(b);
                }
                else
                {
                    //work on this
                    double minimum = lowestIncome_arraylist.get(0).getInfo().getPercentPovertyLine();
                    int min_index = 0;

                    //finding lowest in the arraylist
                    for(int i=0 ; i<lowestIncome_arraylist.size() ; i++)
                    {
                        if(lowestIncome_arraylist.get(i).getInfo().getPercentPovertyLine() < minimum)
                        {
                            minimum = lowestIncome_arraylist.get(i).getInfo().getPercentPovertyLine();
                            min_index = i;
                        }
                    }
                    //check if the current community is greater than lowest in the arraylist
                    if(b.getInfo().getPercentPovertyLine() > minimum)
                    {
                        lowestIncome_arraylist.set(min_index, b); //update the arraylist
                    }
                }
            }
        }

        return lowestIncome_arraylist; // update this line
    }
}