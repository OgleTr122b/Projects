import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JFramestudy extends JFrame 
{
	public static int fNum = 10;
	public static ArrayList<Fighter> fighters = new ArrayList<Fighter> ();
	public MyCanvas frame = new MyCanvas();
	public static boolean deBug = false;
	public static int count = 0;
	
	private JFramestudy()
	{
		setLayout(new BorderLayout());
		setSize(400, 420);
		setTitle("Bout.java");
		add("Center", frame);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class MyCanvas extends Canvas
	{
		public void paint(Graphics g)
		{
			g.setColor(Color.black);
			
			for(int i = 0; i < fNum * 2; i++)
			{
				//grabs fighter information
				if(i < fNum)
				{
					Fighter f1 = info(i);
					float hdr = 20 * ((float)f1.health / (float)f1.maxHealth);
					int nhdr = (int) Math.round(hdr);
					g.fillRect(f1.x - 6, f1.y + 12,  22, 5);
					g.setColor(Color.green);
					g.fillRect(f1.x - 5, f1.y + 13, nhdr, 3);
					g.setColor(Color.black);
					
					if(deBug == true)
					{
						String temp = Integer.toString(f1.name);
						g.drawString(temp, f1.x + 10, f1.y + 20);
						temp = Boolean.toString(f1.alive);
						g.drawString(temp, f1.x + 10, f1.y + 30);
						temp = Integer.toString(f1.health);
					}
				}
				else
				{
					Fighter f1 = info(i - fNum);
					g.drawOval(f1.x, f1.y, 10, 10);
					if(f1.attacking)
					{
						attAni(g, f1.x, f1.y);
					}
				}
			}
			repaint();
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		for(int i = 0; i < fNum; i++)
		{
			fighters.add( new Fighter(i));
			Fighter f2 = info(i);
			f2.initialize(0, 0, 0, 0, 0, 0);
		}
		
		
		
		JFramestudy please = new JFramestudy();
		
		while(true)
		{
			count++;
			updateGame();
			if(count > 120)
			{
				count = 0;
			}
		}
	}
	
	public static void updateGame() throws InterruptedException
	{
		for(int i = 0; i < fNum; i++)
		{
			Fighter f1 = info(i);
			if(f1.alive == true)
			{
				int target;
				target = f1.seek(i);
				f1.move(target, i);
				f1.fight(i, target, count);
			}
			f1.alive();
			if(f1.alive == false)
			{
				fighters.remove(i);
				fNum = fNum - 1;
			}
		}
		
		TimeUnit.MILLISECONDS.sleep(4);
	}
	
	public static Fighter info(int i)
	{
		return fighters.get(i);
	}
	
	private static void attAni(Graphics g, int x, int y)
	{
		
	}
}