/**
 * Average filtering
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */

public class Filter {
	 
	 private double z_1;
	 private double beta;
	 
	 public Filter (int start, double b)
	 {
		 z_1 = (double)start;
		 beta = b;
	 }
	 
	 public int averageFilter(int sample)
	 {	  
		 // First order IIR filtering
		 z_1 = (beta * sample) + ((1 - beta) * z_1);
		 return (int)z_1;
	 }
	 
}
