/*****************************************************************************
 * Name(s)      : Spencer Koo
 * NetID(s)     : skoo
 * Precept(s)   : P06
 * Program Name : Blob.java
 * Dependencies : StdOut.java
 * Description  : This program creates a Blob data type made up of a bunch of
 * pixels and centered at the average of all the pixels contained in the blob.
 *****************************************************************************/

public class Blob
{
    // instance variables
    private int points;    // number of points = number pixels
    private double cx;     // x-coord center of mass
    private double cy;     // y-coord center of mass
    
    // constructor
    public Blob()
    {
        points = 0;
        cx = 0.0;
        cy = 0.0;
    }
    
    // adding pixel (or point) (i, j) to the Blob
    public void add(int i, int j)
    {
        // updating both x and y coordinate center of masses
        cx = (cx * points + i) / (points + 1);
        cy = (cy * points + j) / (points + 1);
        
        // updating number pixels
        points++;
    }
    
    public int mass()
    {
        return points;
    }
    
    // Euclidean distance between this Blob and Blob b
    public double distanceTo(Blob b)
    {
        double dx = this.cx - b.cx;
        double dy = this.cy - b.cy;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // String representation of a Blob
    public String toString()
    {
        return String.format("%2d (%8.4f, %8.4f)", points, cx, cy);
    }
    
    // main testing method for Blob class data type
    public static void main(String[] args)
    {
        Blob blob = new Blob();
        blob.add(100, 200);
        StdOut.println(blob);
        Blob blob2 = new Blob();
        blob2.add(101, 201);
        StdOut.println(blob2);
        StdOut.println(blob2.distanceTo(blob));
    }
}