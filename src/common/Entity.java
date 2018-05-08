package common;

public abstract class Entity {

	protected int x,y;
	protected double velocityX,velocityY;
	protected byte identifier;
	public Entity(int x, int y, double velocityX, double velocityY, byte identifier) 
	{
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.identifier = identifier;
	}
	public void setLocation(int x1, int y1) 
	{
		x=x1;
		y=y1;
	}
	public void addToLocation(int x, int y) 
	{
		this.x +=x;
		this.y+=y;
	}
	
	public abstract void act(Map m);
	public String toString() 
	{
		return(x + " " + velocityX + " " + y + " " + velocityY + " " + identifier);
	}

}
