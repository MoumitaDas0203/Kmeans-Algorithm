//package kmeans;

public class coordinates {

	
	int id;
	double x,y;
	
	coordinates(int a, double b, double c){
		
		this.id = a;
		this.x= b;
		this.y = c;
		
		
	}
	public String toString(){
		//return x + "," + y;
		return Integer.toString(id);
	}
}
