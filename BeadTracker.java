/*****************************************************************************
 * Name(s)      : Spencer Koo
 * NetID(s)     : skoo
 * Precept(s)   : P06
 * Program Name : BeadTracker.java
 * Dependencies : Blob.java, BlobFinder.java, StdOut.java, Stopwatch.java
 * Description  : This program is a client of BlobFinder.java, and it takes in
 * a bunch of Pictures, finds the beads on each Picture, and prints out the
 * radial displacement of each bead as it travels from one Picture to the next
 * one (but only if that distance is under delta). This program also calculates
 * the of frames and pixels processed, and it prints the amount of time that it
 * took to track the beads through all the frames.
 *****************************************************************************/

public class BeadTracker
{
    public static void main(String[] args)
    {
        // command-line arguments, non-Picture elements
        int P = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);
        
        // keeping track of the total number of frames read in
        int N = 0;
        
        // creating a Stopwatch to time running time
        Stopwatch timer = new Stopwatch();
        for (int i = 3; i < args.length - 1; i++)
        {
            // the two names of the picture files currrently comparing
            String pName1 = args[i];
            String pName2 = args[i + 1];
            
            // getting the two Pictures
            Picture pic1 = new Picture(pName1);
            Picture pic2 = new Picture(pName2);
            
            // creating new BlobFinders
            BlobFinder bf1 = new BlobFinder(pic1, tau);
            BlobFinder bf2 = new BlobFinder(pic2, tau);
            
            // getting arrays of the beads in each Picture
            Blob[] beads1 = bf1.getBeads(P);
            Blob[] beads2 = bf2.getBeads(P);
            
            // find the shortest distance from each bead in t+1 to t and write
            // it out if it's less than delta
            for (int j = 0; j < beads2.length; j++)
            {
                // variable to hold shortest length
                double shortest = Double.POSITIVE_INFINITY;
                
                for (int k = 0; k < beads1.length; k++)
                {
                    double currentD = beads2[j].distanceTo(beads1[k]);
                    
                    // seeing if that distance is shortest so far
                    if (currentD < shortest)
                        shortest = currentD;
                }
                
                // printing out shortest distance for the first bead from t+1
                // if it's less than delta
                if (shortest < delta)
                {
                    StdOut.printf("%6.4f", shortest);
                    StdOut.println();
                }
            }
            StdOut.println();
        }
        
        // the ellapsed time after running and total number of pixels processed
        double elapsed = timer.elapsedTime();
        
        // total number of frames processed in command-line
        // (multiply by 640*480 to get total pixels)
        int totalFramePixels = 640 * 480;
        int origArgs = 3;
        
        N = args.length - origArgs;
        int allPixels = N * totalFramePixels;
        StdOut.println("Frames processed: " + N + ", "
                     + " Pixels processed: " + allPixels + ", "
                     + " Running time: " + elapsed);
    }
}