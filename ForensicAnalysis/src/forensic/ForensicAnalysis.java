package forensic;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRootNode;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRootNode = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {

        // WRITE YOUR CODE HERE
        int iteration = StdIn.readInt(); //for loop
        STR[] str = new STR[iteration];

        for(int i=0 ; i<iteration ; i++)
        {
            String strString = StdIn.readString();
            int occurrence = StdIn.readInt();
            STR strObject = new STR(strString , occurrence);
            str[i] = strObject;
        }
        Profile profile = new Profile(str);
        return profile; // update this line
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */

    public void insertPerson(String name, Profile newProfile) {
        
        // WRITE YOUR CODE HERE
        TreeNode newNode = new TreeNode(name , newProfile , null , null);
        if(treeRootNode == null)
        {
            treeRootNode = newNode;
        }
        insertNode(treeRootNode , newNode);
    }

    private void insertNode(TreeNode treeRoot , TreeNode newNode)
    {
        if(newNode.getName().compareTo(treeRoot.getName()) > 0)
        {
            if(treeRoot.getRight() == null)
            {
                treeRoot.setRight(newNode);
            }
            insertNode(treeRoot.getRight() , newNode);
        }
        else if(newNode.getName().compareTo(treeRoot.getName()) < 0)
        {
            if(treeRoot.getLeft() == null)
            {
                treeRoot.setLeft(newNode);
            }
            insertNode(treeRoot.getLeft() , newNode);
        }
    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {
        
        // WRITE YOUR CODE HERE
        return matchingProfileCount(treeRootNode , isOfInterest); // update this line
    }

    private int matchingProfileCount(TreeNode node , boolean isOfInterest)
    { 
        if(node == null)
        {
            return 0;
        }
        if(node.getProfile().getMarkedStatus() == isOfInterest)
        {
            return matchingProfileCount(node.getLeft(), isOfInterest) + matchingProfileCount(node.getRight(), isOfInterest) + 1;
        }
        else
        {
            return matchingProfileCount(node.getLeft(), isOfInterest) + matchingProfileCount(node.getRight(), isOfInterest);
        }
    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */
    public void flagProfilesOfInterest() {

        // WRITE YOUR CODE HERE
        traverseHelper(treeRootNode);

    }

    private void traverseHelper(TreeNode node)
    {
        if(node != null)
        {
            traverseHelper(node.getLeft());
            traverseHelper(node.getRight());
            
            Profile profile = node.getProfile();
            STR[] strArray = profile.getStrs();

            int matchCounter = 0;
            for(STR str : strArray)
            {
                int strOccurence = str.getOccurrences(); // number of occurrences from str object. i.e. 6 from (ACGT , 6)
                if(strOccurence == (numberOfOccurrences(firstUnknownSequence, str.getStrString()) + numberOfOccurrences(secondUnknownSequence, str.getStrString())))//str occurence in 1st 2nd sequence
                {
                    matchCounter++;
                }
            }

            if(matchCounter >= (strArray.length+1)/2)
            {
                node.getProfile().setInterestStatus(true);
            }
        }
        else{
            return;
        }
    }

    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {

        // WRITE YOUR CODE HERE
        String[] names = new String[getMatchingProfileCount(false)];
        Queue<TreeNode> queue = new Queue<TreeNode>();
        TreeNode currentNode = treeRootNode;
        if(currentNode != null)
        {
            queue.enqueue(currentNode);
        }
        
        int x = 0;
        while(!queue.isEmpty())
        {
            currentNode = queue.dequeue();

            if(currentNode.getLeft() != null)
            {
                queue.enqueue(currentNode.getLeft());
            }
            if(currentNode.getRight() != null)
            {
                queue.enqueue(currentNode.getRight());
            }

            if(currentNode.getProfile().getMarkedStatus() == false)
            {
                names[x] = currentNode.getName();
                x++;
            }
        }

        return names; // update this line
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {
        // WRITE YOUR CODE HERE
        treeRootNode = removeHelper(treeRootNode , fullName);
    }

    private TreeNode removeHelper(TreeNode node , String fullName){
        if(node == null) //tree is null
        {
            return null;
        }

        if(fullName.compareTo(node.getName()) == 0)//name found
        {
            if(node.getLeft() == null && node.getRight() == null)//no children
            {
                node = null;
            }
            else if(node.getLeft() == null && node.getRight() != null)//only right child
            {
                node = node.getRight();
            }
            else if(node.getLeft() != null && node.getRight() == null)//only left child
            {
                node = node.getLeft();
            }
            else//two children nodes
            {
                TreeNode successor = successorHelper(node.getRight());
                node.setName(successor.getName());
                node.setProfile(successor.getProfile());
                node.setRight(removeHelper(node.getRight(), successor.getName()));
            }
        }
        else if(fullName.compareTo(node.getName()) > 0)//traverse tree to right
        {
            node.setRight(removeHelper(node.getRight(), fullName));
        }
        else
        {
            node.setLeft(removeHelper(node.getLeft(), fullName));//to left
        }
        return node;
    }

    private TreeNode successorHelper(TreeNode node)
    {
        if(node.getLeft() == null)
        {
            return node;
        }
        else
        {
            return successorHelper(node.getLeft());
        }
    }

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE
        String[] names = getUnmarkedPeople();
        for(String x : names)
        {
            removePerson(x);
        }
    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRootNode() {
        return treeRootNode;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRootNode(TreeNode newRoot) {
        treeRootNode = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}
