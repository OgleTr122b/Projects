import java.util.Random;

public class Fighter {
	public Random rand = new Random();
	public int name;
	public int team = 0;
	public int maxHealth = 0;
	public int health = 0;
	public int attack = 0;
	public int block = 0;
	public int x = 0;
	public int y = 0;
	public float tx = 0;
	public float ty = 0;
	double xSpeed = 0;
	double ySpeed = 0;
	boolean closeEnough = false;
	boolean alive = true;
	boolean attacking = false;
	
	public Fighter(int name)
	{
		this.name = name;
	}
	
	public void initialize(int h, int a, int b, int x, int y, int tm)
	{
		health = h;
		attack = a;
		block = b;
		tx = x;
		ty = y;
		team = tm;
		
		/*
		 * If any of the values are 0, it randomizes them
		 * instead
		 */
		if(h == 0)
		{	
			health = 100 + rand.nextInt(200);	
			maxHealth = health;
		}
		if(a == 0)
		{
			attack = 10 + rand.nextInt(5);
		}
		if(b == 0)
		{
			block = 7 + rand.nextInt(5);
		}
		if(x == 0)
		{
			tx = rand.nextInt(400);
		}
		if(y == 0)
		{
			ty = rand.nextInt(400);	
		}
	}
	
	public int seek(int i)
	{
		//array to store the distances to each other fighter
		double[] arr = new double[JFramestudy.fNum];
		
		for(int j = 0; j < JFramestudy.fNum; j++)
		{
			Fighter f1 = JFramestudy.info(j);
			if(j != i)
			{
				float x2 = f1.tx;
				float y2 = f1.ty;
				
				
				/*
				finds the difference between the x and y locations from
				this and the other fighter
				*/
				float xd = x2 - x;
				float yd = y2 - y;
			
				//if the xd or yd value comes out negative it is made positive
				if(xd < 0)
				{
					xd = xd * -1;
				}
				if(yd < 0)
				{
					yd = yd * -1;
				}
			
				//double to be used in angle calculation
				float forAtan = yd/xd;
				
				//angle to other fighter
				double ang = Math.atan(forAtan);
			
				//doubles for calculating distance
				double a = (1 / Math.cos(ang)) * xd;
				double b = (1 / Math.sin(ang)) * yd;
				double dist = (a * a) + (b * b);
				dist = Math.sqrt(dist);
				arr[j] = dist;
				
				if(JFramestudy.deBug == true)
				{
					//for diagnostic use
					System.out.println("yd: " + yd);
					System.out.println("xd: " + xd);
					System.out.println("yd/xd = " + forAtan);
					System.out.println("a: " + a);
					System.out.println("b: " + b);
					System.out.println();
				}
			}
		}
		
		/*
		//for diagnostic use
		System.out.println(i + " " + ans3);
		*/
		//variable to help find the closes other fighter
		int closest = 0;
		
		/*
		 Sifts through all through array containing distances to
		 distances to all other fighters and chooses the closest
		 other fighter
		 */
		
		for(int j = 0; j < JFramestudy.fNum; j++)
		{	
			if(j != i)
			{
				double temp1 = arr[j];
				double temp2 = arr[closest];
				if(i == 0 && closest == 0)
				{
					temp2 = 999999999.0;
				}
				if(temp1 < temp2)
				{
					closest = j;
				}
			}
		}
		/*
		//diagnostic
		System.out.println(i + " " + closest);
		*/
		if(closest < 0 || closest > JFramestudy.fNum)
		{
			closest = i;
		}
		return(closest);
	}
	
	public void move(int t, int i)
	{
		Fighter f1 = JFramestudy.info(t);
		//gets x and y locations from targeted fighter
		float x2 = f1.tx;
		float y2 = f1.ty;
		
		/*
		finds the difference between the x and y locations from
		this and the other fighter
		*/
		float xd = x2 - tx;
		float yd = y2 - ty;
		
		//double to be used in angle calculation
		float forAtan = yd/xd;
		
		//angle to other fighter
		double ang = Math.abs(Math.atan(forAtan));
		
		//double for calculating direction and speed
		double xSM = 0;
		
		//if statements for correcting direction
		if(x2 > tx)
		{
			xSM = Math.cos(ang);
		} 
		else
		{
			xSM = Math.cos(ang) * -1;
		}
		
		
		//double for calculating direction and speed
		double ySM = 0;
		
		//if statements for correcting direction
		if(y2 > ty)
		{
			ySM = Math.sin(ang);
		}
		else
		{
			ySM = Math.sin(ang) * -1;
		}
		
		//decreasing raw direction and speed values to make movement slower
		xSpeed = xSM / 10;
		ySpeed = ySM / 10;
		
		//calculates distance for movement cancellation 
		double a = (1 / Math.cos(ang)) * xd;
		double b = (1 / Math.sin(ang)) * yd;
		double dist = (a * a) + (b * b);
		dist = Math.sqrt(dist);
		if(alive == true)
		{
			if(dist > 15)
			{
				tx += xSpeed;
				ty += ySpeed;
				closeEnough = false;
			}
			else
			{
				closeEnough = true;
			}
		}
		
		x = Math.round(tx);
		y = Math.round(ty);
		
		if(JFramestudy.deBug == true)
		{
			//for diagnostic use
			System.out.println("ySp: " + xSpeed);
			System.out.println("xSp: " + ySpeed);
			System.out.println("x speed mult: " + xSM);
			System.out.println("y speed mult: " + ySM);
			System.out.println(i + " targeting " + t);
			System.out.println();
		}
	}
	
	public void fight(int i, int t, int c)
	{
		if(t != i)
		{
			Fighter f1 = JFramestudy.info(t);
		
			float abr = attack / f1.block;
		
			if(abr > 1)
			{
				abr = 1;
			}
		
			if(closeEnough && c % 120 == 0)
			{
				f1.health -= 1 + attack * (abr);
				attacking = true;
			}
		}
	}
	
	public void alive()
	{
		if(health <= 0)
		{
			alive = false;
		}
		else
		{
			alive = true;
		}
	}
}
