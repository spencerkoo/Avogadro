/*****************************************************************************
 * Name(s)      : Spencer Koo
 * NetID(s)     : skoo
 * Precept(s)   : P06
 * Program Name : Avogadro.java
 * Dependencies : Blob.java, BlobFinder.java, BeadTracker.java, StdOut.java,
 *              : StdIn.java
 * Description  : This program reads in a sequence of real numbers from
 * standard input and prints out an estimate of Avogadro's number.
 *****************************************************************************/

public class Avogadro
{ 
    // estimating the self-diffusion constant
    private static double variance(double totalDis, int numDis)
    {
        // hard-wired 2 is because the formula calls for
        // 2*total number of displacements
        return (totalDis / (2 * numDis));
    }
    
    // estimating the Boltzmann constant
    private static double boltzmann(double diffusion)
    {
        int T = 297;                  // room temp in Kelvin
        double eta = 9.135e-4;        // viscosity of water at room temp
        double rho = 0.5e-6;          // radius of bead
        
        return ((diffusion * 6 * Math.PI * eta * rho) / T);
    }
    
    // estimating Avogadro's number
    private static double avogadro(double k)
    {
        // universal gas constant R
        double uniR = 8.31457;
        
        return (uniR / k);
    }
    
    // calculating the Boltzmann constant and Avogadro's number
    public static void main(String[] args)
    {
        // total displacement and number of displacements
        double displacements = 0.0;
        int n = 0;
        // for converting pixels to meters
        double convert = 0.175e-6;
        
        // calculating total displacement
        while (!StdIn.isEmpty())
        {
            double current = (StdIn.readDouble() * convert);
            displacements += (current * current);
            n++;
        }
        
        // calculating self-diffusion constant D
        double sdcD = variance(displacements, n);
        
        // calculating Boltzmann constant
        double boltzmann = boltzmann(sdcD);
        String boltz = String.format("%5.4e", boltzmann);
        
        // printing out Boltzmann constant
        StdOut.println("Boltzmann = " + boltz);
        
        // calculating Avogadro's number
        double avogadrosNum = avogadro(boltzmann);
        String avo = String.format("%5.4e", avogadrosNum);
        
        // printing out Avogadro's number
        StdOut.println("Avogadro  = " + avo);
    }
}