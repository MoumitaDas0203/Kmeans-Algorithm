//package kmeans;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.io.FileReader;
import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class kmeans {
	
	 public static coordinates arr;
	 public static coordinates arr1;
	 public static ArrayList<coordinates> Clist = new ArrayList<coordinates>();
	 
	 
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ArrayList<coordinates> centroid = new ArrayList<>();
		ArrayList<coordinates> newCentroids = new ArrayList<>();
		HashMap<coordinates,ArrayList<coordinates>> map1 = new HashMap<>();
		HashMap<coordinates,ArrayList<coordinates>> map2 = new HashMap<>();

			
		String input = args[0];
		int k = Integer.parseInt(input);
		 
		String inputFile = args[1];
		String outputFile = args[2];
		// int k=2;
		PrintStream out = new PrintStream(new FileOutputStream(outputFile));
		System.setOut(out);
				
		System.out.println("Parameters : " + inputFile + " " +  outputFile +" " +k);
		kmeans obj =  new kmeans();
		obj.fileRead(inputFile);
		//int k = 5;
		//k.print();
		System.out.println();
		centroid = obj.generateCentroid(k);
		map1 = obj.centroidDist(centroid);
		//printMap(map1);
		newCentroids= getMeanCentroid(map1);
		obj.iterate(newCentroids, 25);
	}
	
	
	public void iterate(ArrayList<coordinates> centroid, int count){
		HashMap<coordinates, ArrayList<coordinates>> cluster = new HashMap<>();
		HashMap<coordinates, ArrayList<coordinates>> cluster1 = new HashMap<>();
		for(int i=1; i < count+1; i++){
			System.out.println("ITERATION " + i);
			cluster = (HashMap<coordinates, ArrayList<coordinates>>) cluster1.clone();
			cluster1 = centroidDist(centroid);
			centroid = getMeanCentroid(cluster1);
			showOutput(cluster1);
			System.out.println();
			if(checkSame(cluster1, cluster))
				break;
			
		}
		System.out.println("SSE: " + sse(cluster));
	}
	
	public void showOutput(HashMap<coordinates, ArrayList<coordinates>> cluster){
		int i = 1;
		Iterator it = cluster.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(i + " - " + pair.getValue());
	        i++;
	    }
	}
	
	public double sse(HashMap<coordinates, ArrayList<coordinates>> cluster){
		double sse = 0;
		Iterator it = cluster.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        coordinates clusterPoint = (coordinates) pair.getKey();
	        ArrayList<coordinates> points = (ArrayList<coordinates>) pair.getValue();
	        for(coordinates c : points){
	        	double d = dist(c.x, c.y, clusterPoint.x, clusterPoint.y);
	        	sse += d*d;
	        }
	    }
		return sse;
	}
	
	public static boolean checkSame(HashMap<coordinates, ArrayList<coordinates>> map1, HashMap<coordinates, ArrayList<coordinates>> map2){
		boolean res = true;
		Iterator it = map1.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if(!map2.containsKey(pair.getKey()))
	        	res = false;
	    }
		return res;
	}
	
	public void fileRead(String inputFile) throws IOException{
		
		//String path="test_data.txt";
		String split="/t";
		int lines = 0;
		int index =0;
		
		int count;
		BufferedReader b  = new BufferedReader(new FileReader(inputFile)); 
		for(count = 0;b.readLine() != null;count++);
		count--;
		b.close();
		BufferedReader br  = new BufferedReader(new FileReader(inputFile));
		br.readLine();			
		for(int i=0;i<count;i++){
		String textLine = br.readLine();
		String[] words = textLine.split("\t");
		int id = Integer.parseInt(words[0]);
		double x = Double.parseDouble(words[1]);
		double y = Double.parseDouble(words[2]);
		//arr[i]= new coordinates(id,x,y);
		coordinates c = new coordinates(id,x,y);
		Clist.add(c);
		}
		br.close();
		
	}
	
	public void print(){
		for(int i = 0;i<100;i++){
			
			System.out.println(Clist.get(i).id + " " + Clist.get(i).x + " " + Clist.get(i).y );
			
		}
		}
	
	
	public static ArrayList<coordinates> generateCentroid(int k){
				 
		 //Double[] arr1 = new Double[100];
		 //Double[] minArr = new Double[25];
		 int randomInt;
		 ArrayList<coordinates> centroid = new ArrayList<>();
		 HashMap<Integer,Integer> centroid1 = new HashMap<Integer,Integer>();
		 Random randomGenerator = new Random();
		for (int idx = 0; centroid1.size()<k; idx++){
		      randomInt = randomGenerator.nextInt(100);
		  //    System.out.println(" Centr25oid for cluster id  "+ idx + " : "+  randomInt);
		      if(centroid1.containsKey(randomInt)){
		    	idx--;  
		      }
		      else{
		      centroid1.put(randomInt, 0);
		      centroid.add(Clist.get(randomInt));
		      //System.out.println(centroid[idx]);
		      }
		    
		      
		}
		
		
		
		return centroid;
	}      
		
	public static HashMap centroidDist(ArrayList<coordinates> centroid){
		

		 double x;
		 double y;
		 double x1 ;
		 double y1 ;
		 double root = 0.0;
		 double min = 100.0;
		 coordinates minCluster;
		 int j=0;
		 HashMap<coordinates,ArrayList<coordinates>> h = new HashMap<>();
		 ArrayList<coordinates> temp;
		 for(int i = 0;i<100;i++){
		 arr = Clist.get(i);
		 x = arr.x;
		 y = arr.y;
		 
		 min = 100.0;
		 minCluster= null;
		 
		 	for( j=0;j<centroid.size();j++)
		 	{
		 	
		 		arr1 = centroid.get(j);
		 		x1 = arr1.x;
		 		y1 = arr1.y;
		 		//System.out.println(x1 + " " + y1);
		 		//if( x!=x1 && y!=y1){
		 		root = dist(x,y,x1,y1);
		 		//}
		 		//System.out.println(root);
		 		//System.out.println(min);
		 		if(root< min){
		    		 
		    		 min = root;
		    		 minCluster = centroid.get(j);		    		
		    		
		    	 }
		 			
		 	}
		 	//System.out.println("x is"+ x);
		 	//System.out.println("y is"+ y);
		 	//System.out.println("x1 is"+ x1);
		 	//System.out.println("y1 is"+ y1);
		 	//System.out.println("Min distance is:" +min);
		 	//System.out.println(minCluster);
		 	//cen.add(arr);
		 	if(h.get(minCluster) == null)
		 	{
		 		temp = new ArrayList<coordinates>();
		 		temp.add(arr);
		 	}
		 	else
		 	{
		 		temp = (ArrayList<coordinates>) h.get(minCluster);
		 		temp.add(arr);
		 	}
		 	
		 	h.put(minCluster, temp);
		 }
		 return h;
		 
	}
	
	public static void printMap(Map h) {
		
		ArrayList<coordinates> n = new ArrayList<coordinates>();
	    Iterator it = h.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	       // System.out.println("key is  "+pair.getKey());
	        System.out.print(pair.getKey() + " - ");
	        n = (ArrayList<coordinates>) pair.getValue();
	        
	        for(int a=0;a<n.size();a++){
	        	
	        	int id = n.get(a).id;
	        	double x= n.get(a).x;
	        	double y = n.get(a).y;
	        	System.out.print(id +" ");
	        }
	        System.out.println();
	         // it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    
	}
	
		
	public static ArrayList<coordinates> getMeanCentroid(Map h){
		
		double sumX=0.0;
		double sumY=0.0;
		ArrayList<coordinates> newCentroids = new ArrayList<>();
		double avX=0.0;
		double avY=0.0;
		double count=0.0;
		int a=0;
		
		ArrayList<coordinates> points = new ArrayList<coordinates>(); 
		Iterator it = h.entrySet().iterator();
		int num = h.size();
		int b=0;
	    while (it.hasNext()) {
	    	sumX = 0.0;
	    	sumY = 0.0;
	    	count =0;
	        Map.Entry pair = (Map.Entry)it.next();
		    //System.out.print(pair.getKey() + " - ");  
	        points = (ArrayList<coordinates>) pair.getValue();
		        
	        for(a=0;a<points.size();a++){
	        	
	        	int id = points.get(a).id;
	        	double x= points.get(a).x;
	        	double y = points.get(a).y;
	        	sumX = sumX + x;
	        	sumY = sumY + y;
	        	count++;
	        	
	        }
		       
	        avX = sumX/count;
	        avY = sumY/count;
	        //System.out.println(avX + " , " + avY+ " ");
	        coordinates c = new coordinates(200, avX, avY);
	        newCentroids.add(c);
	        b++;
	        
		}
	    
	    for(int j=0;j<25;j++){
	    	
	    	//System.out.println(newCentroids[j][0]+ " " + newCentroids[j][1]);
	    }
	
	    return newCentroids;
	}
	
	public static double dist(Double x, Double y, Double x1, Double y1){
		
		Double xSquare = 0.0;
		Double ySquare = 0.0;
		Double sum = 0.0;
		Double root = 0.0;
		
		
		xSquare = (x-x1)*(x-x1);
		ySquare = (y-y1)*(y-y1);
		
		sum = xSquare + ySquare;
		
		root = Math.sqrt(sum);
		
		
		return root;
	}
	
	
	}
		

	



