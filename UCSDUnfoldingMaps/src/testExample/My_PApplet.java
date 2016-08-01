package testExample;
import processing.core.*;
public class My_PApplet extends PApplet{
	
    private PImage backgroundImg;
	
	public void setup(){
		size(600,400);
		backgroundImg = loadImage("beach.jpg");
	}
	public void draw(){
		//resize the background image to adapt to the canvas size
		//if the parameter is 0, means do nothing with width/height
//		backgroundImg.resize(0,height);    
		image(backgroundImg,0,0);
		ellipse(width/6*5,height/4,width/6,width/6);
		int[] color = sunColor(second());
		fill(color[0],color[1],color[2]);
	}
	
	private int[] sunColor(float second){
		int[] rgb = new int[3];
		float difference = Math.abs(second-30)/30;
		rgb[0] = (int) (255*difference);
		rgb[1] = (int) (255*difference);
		rgb[2] = 0;
		return rgb;
	}

}
