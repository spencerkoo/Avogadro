/*****************************************************************************
 * Name(s)      : Spencer Koo
 * NetID(s)     : skoo
 * Precept(s)   : P06
 * Program Name : BlobFinder.java
 * Dependencies : Picture.java, Luminance.java, StdOut.java, Blob.java,
 *              : Node.java
 * Description  : This program finds all the Blobs in the Picture, and then it
 * finds all the beads, Blobs that have greater than or equal to P pixels.
 * Before it does any of this though, the program firstly cleans up all the
 * noise in the Picture; all the pixels with a luminance value under a user
 * inputed Tau value are not considered as within the range. Finally, it prints
 * out the total number of Blobs and beads and then prints out the toString
 * value of corresponding to each one.
 *****************************************************************************/

public class BlobFinder
{
    // instance variables
    private Picture pic;
    private double tau;
    private Node first, current;
    private boolean[][] visited;
    
    // nested class for using a linked list to keep track of the Blobs
    private class Node
    {
        private Blob blob;
        private Node next;
    }
    
    // constructor that finds all the blobs in pic using the luminance
    // threshold tau
    public BlobFinder(Picture picture, double tau)
    {
        first = new Node();
        current = first;
        first.blob = null;
        first.next = null;
        visited = new boolean[picture.width()][picture.height()];
        pic = picture;
        this.tau = tau;
        
        // thresholding image
        for (int i = 0; i < pic.width(); i++)
        {
            for (int j = 0; j < pic.height(); j++)
            {
                if (Luminance.lum(pic.get(i, j)) < tau)
                    visited[i][j] = true;
            }
        }
        
        // finding the blobs
        findBlobs();
    }
    
    // return all Blobs with >= P pixels
    public Blob[] getBeads(int P)
    {
        // keeping track of the total number of Blobs over P size
        int totalBeads = 0;
        // for going through array
        int i = 0;
        
        // finding total number of beads
        for (Node x = first; x != null; x = x.next)
        {
            if (x.blob.mass() >= P)
                totalBeads++;
        }
        
        // creating array of beads
        Blob[] beads = new Blob[totalBeads];
        
        // implementing array of Blobs considered to be beads
        for (Node x = first; x != null; x = x.next)
        {
            if (x.blob.mass() >= P)
            {
                beads[i] = x.blob;
                i++;
            }
        }
        
        return beads;
    }
    
    // retun number of Blobs with >= P pixels
    public int countBeads(int P)
    {
        // keeping track of the total number of Blobs over P size
        int totalBeads = 0;
        
        // traversing through linked list to find beads
        for (Node x = first; x != null; x = x.next)
        {
            if (x.blob.mass() >= P)
                totalBeads++;
        }
        
        return totalBeads;
    }
    
    // recursive function call to find blobs
    private void dfs(int i, int j)
    {
        // base cases
        // pixel out of bounds
        if (i < 0 || i >= pic.width())
            return;
        
        if (j < 0 || j >= pic.height())
            return;
        
        // already visited
        if (visited[i][j])
            return;
        
        // adding pixel to blob
        current.blob.add(i, j);
        
        // set pixel as visited
        visited[i][j] = true;
        
        // making recursive calls
        dfs(i - 1, j);
        dfs(i + 1, j);
        dfs(i, j - 1);
        dfs(i, j + 1);
    }
    
    // go through pic to find all Blobs and make a linked list of them
    private void findBlobs()
    {
        for (int i = 0; i < pic.width(); i++)
        {
            for (int j = 0; j < pic.height(); j++)
            {
                // testing to see if it's a light pixel
                if (!visited[i][j])
                {
                    // creating a new Blob
                    Blob b = new Blob();
                    
                    // if first in linked list
                    if (first.blob == null && first.next == null)
                        first.blob = b;
                    
                    // if second and up in linked list
                    else
                    {
                        Node toAdd = new Node();
                        current.next = toAdd;
                        current = current.next;
                        current.blob = b;
                        current.next = null;
                    }
                    
                    dfs(i, j);
                }
            }
        }
    }
    
    // total number of Blobs of any size
    private int totalBlobs()
    {
        int totalBlobs = 1;
        Node x = first;
        do {
            totalBlobs++;
            x = x.next;
        } while (x.next != null);
        
        return totalBlobs;
    }
    
    // putting all Blobs from linked list into array
    private Blob[] allBlobs()
    {
        Blob[] allB = new Blob[totalBlobs()];
        int i = 0;
        
        for (Node x = first; x != null; x = x.next)
        {
            allB[i] = x.blob;
            i++;
        }
        
        return allB;
    }
    
    // testing main method
    public static void main(String[] args)
    {
        // command-line arguments
        int minBead = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        String pictureFile = args[2];
        
        Picture pict = new Picture(pictureFile);
        
        // creating a new BlobFinder
        BlobFinder bf = new BlobFinder(pict, tau);
        
        // printing out all the Blobs
        Blob[] allB = bf.allBlobs();
        StdOut.println(bf.totalBlobs() + " Blobs:");
        for (int j = 0; j < bf.totalBlobs(); j++)
            StdOut.println(allB[j]);
        
        // how many beads of all the Blobs
        int beadTotal = bf.countBeads(minBead);
        
        // getting array of all Beads
        Blob[] allBeads = bf.getBeads(minBead);
        
        // printing out all the beads
        StdOut.println(beadTotal + " Beads:");
        for (int i = 0; i < beadTotal; i++)
            StdOut.println(allBeads[i]);
    }
}