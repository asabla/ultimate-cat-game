package katt;
import java.io.Serializable;

public class Score implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long points;
	private String name = "";
	
	//Constructor
	public Score(Long points){
		this.points = points;
	}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public Long getPoints(){
		return points;
	}

}
