// Test file for StarNearEarth project.  See Read Me file for details
// CS 1C, Foothill College, Michael Loceff, creator

import cs_1c.*;
import java.util.*;
import java.text.*;

//------------------------------------------------------
public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      // how we read the data from files
      StarNearEarthReader  starInput 
         = new StarNearEarthReader("nearest_stars.txt");
      int arraySize;

      // how we test the success of the read:
      if (starInput.readError())
      {
         System.out.println("couldn't open " + starInput.getFileName()
            + " for input.");
         return;
      }

      System.out.println(starInput.getFileName());
      System.out.println(starInput.getNumStars());

      // create an array of objects for our own use:
      arraySize = starInput.getNumStars();
      StarNearEarth [] starArray  = new StarNearEarth[arraySize];
      for (int k = 0; k < arraySize; k++)
         starArray[k] = starInput.getStar(k);
      
      // show the array, unsorted
      for (int k = 0; k < arraySize; k++)
         System.out.println(starArray[k]);
      System.out.println();
   }
}

/* ---------------- Sample Run ----------------

nearest_stars.txt
100
   #1.  "Proxima Centauri"  ----------
    LHS Name: 49 CNS Name: GJ  551        
    Apparent Mag: 11.09
    Parallax Mean: 0.76887 variance: 2.9E-4
    Right Asc: 217.4292 Dec: -62.6794
    Mass: 0.11 Prop Mot Mag: 3.853

   #2.  "Barnard's Star"  ----------
    LHS Name: 57 CNS Name: GJ  699        
    Apparent Mag: 9.53
    Parallax Mean: 0.54698 variance: 0.0010
    Right Asc: 269.45 Dec: 4.6933
    Mass: 0.17 Prop Mot Mag: 10.358

   #3.  "Wolf 359"  ----------
    LHS Name: 36 CNS Name: GJ  406        
    Apparent Mag: 13.44
    Parallax Mean: 0.4191 variance: 0.0021
    Right Asc: 164.1208 Dec: 7.0147
    Mass: 0.09 Prop Mot Mag: 4.696

   #4.  "Lalande 21185"  ----------
    LHS Name: 37 CNS Name: GJ  411        
    Apparent Mag: 7.47
    Parallax Mean: 0.39342 variance: 7.0E-4
    Right Asc: 165.8333 Dec: 35.97
    Mass: 0.46 Prop Mot Mag: 4.802

   #5.  "Sirius"  ----------
    LHS Name: 219 CNS Name: GJ  244    A   
    Apparent Mag: -1.43
    Parallax Mean: 0.38002 variance: 0.00128
    Right Asc: 101.2833 Dec: -16.7161
    Mass: 1.99 Prop Mot Mag: 1.339

   #6.  "BL Ceti"  ----------
    LHS Name: 9 CNS Name: GJ   65    A   
    Apparent Mag: 12.54
    Parallax Mean: 0.3737 variance: 0.0027
    Right Asc: 24.7542 Dec: -17.9503
    Mass: 0.11 Prop Mot Mag: 3.368



   ... stars removed to save space ...




    Mass: 0.5 Prop Mot Mag: 2.377

   #95.  "Wolf 630 A"  ----------
    LHS Name: 428 CNS Name: GJ  644    A   
    Apparent Mag: 9.72
    Parallax Mean: 0.15497 variance: 5.6E-4
    Right Asc: 253.8667 Dec: -8.3364
    Mass: 0.43 Prop Mot Mag: 1.208

   #96.  "(no common name)"  ----------
    LHS Name: 71 CNS Name: GJ  892        
    Apparent Mag: 5.56
    Parallax Mean: 0.15341 variance: 5.4E-4
    Right Asc: 348.3208 Dec: 57.1683
    Mass: 0.81 Prop Mot Mag: 2.095

   #97.  "Jao et al. 2005"  ----------
    LHS Name: 271 CNS Name: GJ 1128        
    Apparent Mag: 12.74
    Parallax Mean: 0.15305 variance: 0.00241
    Right Asc: 145.6917 Dec: -68.885
    Mass: 0.15 Prop Mot Mag: 1.121

   #98.  "GL Virginis"  ----------
    LHS Name: 324 CNS Name: GJ 1156        
    Apparent Mag: 13.8
    Parallax Mean: 0.1529 variance: 0.0030
    Right Asc: 184.75 Dec: 11.1253
    Mass: 0.12 Prop Mot Mag: 1.301

   #99.  "(no common name)"  ----------
    LHS Name: 0 CNS Name: GJ  625        
    Apparent Mag: 10.1
    Parallax Mean: 0.15179 variance: 0.00101
    Right Asc: 246.35 Dec: 54.3042
    Mass: 0.37 Prop Mot Mag: 0.465

   #100.  "Ross 104"  ----------
    LHS Name: 0 CNS Name: GJ  408        
    Apparent Mag: 10.02
    Parallax Mean: 0.15016 variance: 0.00149
    Right Asc: 165.0167 Dec: 22.8331
    Mass: 0.39 Prop Mot Mag: 0.51


------------------------------------------- */


