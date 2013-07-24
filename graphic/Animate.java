package graphic;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.JComponent;
import javax.swing.JPanel;

import graphic.ImageBox;

// --- Ajouter une fonction "Wait" ---

public class Animate {

	private int bufferX, bufferY;
	
	
	private final static int HUE = 0;
	private final static int SATURATION = 1;
	private final static int BRIGHTNESS = 2;
	
	
	

	private JComponent myComponent;
	private int duration, easing;
	private Callable<Integer> myFunc;
	private String[] properties = new String[2];
	
	
	private String text = null;
	private ImageBox myImage;
	private int x, y, width, height;
	private JPanel panel;
	
	public static ArrayList<Animate> AnimationStack2 = new ArrayList<Animate>();
	public static ArrayList<Animate> AnimationStack = new ArrayList<Animate>();
	
// --- JComponent ---	size	fontSize	background	foreground	???visibilit�???
	
	// Color  Blue		[][]
	public Animate(String[] properties, int duration, JComponent myComponent){
		this.properties = properties;
		this.myComponent = myComponent;
		this.duration = duration;
		this.easing = 100;
		this.myFunc = null;
		this.myImage = null;
		
		AnimationStack2.add(this);
	}

	public Animate(String[] properties, int duration, int easing, JComponent myComponent){
		this.properties = properties;
		this.myComponent = myComponent;
		this.duration = duration;
		this.easing = easing;
		this.myFunc = null;
		this.myImage = null;
		
		AnimationStack2.add(this);
	}

	public Animate(String[] properties, int duration, int easing, Callable<Integer> myFunc, JComponent myComponent){
		this.properties = properties;
		this.myComponent = myComponent;
		this.duration = duration;
		this.easing = easing;
		this.myFunc = myFunc;
		this.myImage = null;
		
		AnimationStack2.add(this);
	}
	
// --- Buffered Image ---	size	rotate	fade	translation fadein
	
	public Animate(String[] properties, int duration, ImageBox srcBuffer, JPanel panel){
		this.properties = properties;
		this.myComponent = null;
		this.duration = duration;
		this.easing = 100;		//test
		this.myFunc = null;
		this.myImage = srcBuffer;
		
		this.x = srcBuffer.getX();
		this.y = srcBuffer.getY();
		this.width = srcBuffer.getWidth();
		this.height = srcBuffer.getHeight();
		this.panel = panel;
		
		AnimationStack2.add(this);
	}
	
	public Animate(String[] properties, int duration, int x, int y, ImageBox srcBuffer, JPanel panel){
		this.properties = properties;
		this.myComponent = null;
		this.duration = duration;
		this.easing = 100;		//test
		this.myFunc = null;
		this.myImage = srcBuffer;
		
		this.x = x;
		this.y = y;
		this.width = srcBuffer.getWidth();
		this.height = srcBuffer.getHeight();
		this.panel = panel;
		
		AnimationStack2.add(this);
	}
	
	public Animate(String[] properties, int duration, int easing, ImageBox srcBuffer, JPanel panel, Callable<Integer> myFunc){
		this.properties = properties;
		this.myComponent = null;
		this.duration = duration;
		this.easing = easing;
		this.myFunc = myFunc;
		this.myImage = srcBuffer;
		
		this.x = srcBuffer.getX();
		this.y = srcBuffer.getY();
		this.width = srcBuffer.getWidth();
		this.height = srcBuffer.getHeight();
		this.panel = panel;
		
		AnimationStack2.add(this);
	}
	
	
	//String
	public Animate(String[] properties, int duration, int easing, int x, int y, int width, int height, String text){
		this.properties = properties;
		this.myComponent = null;
		this.duration = duration;
		this.easing = easing;
		this.myFunc = null;
		this.myImage = null;
		this.text = text;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		AnimationStack2.add(this);
	}
	
	
	public Animate(String[] properties, int duration, int easing, int x, int y, int width, int height, String text, Callable<Integer> myFunc){
		this.properties = properties;
		this.myComponent = null;
		this.duration = duration;
		this.easing = easing;
		this.myFunc = myFunc;
		this.myImage = null;
		this.text = text;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		AnimationStack2.add(this);
	}
	

	public static void refreshArrayList(){
		AnimationStack = new ArrayList<Animate>(AnimationStack2);
		
	}
	
	/*public static List<Animate> cloneList(List<Animate> list) {
	    List<Animate> clone = new ArrayList<Animate>(list.size());
	    for(Animate item: list) 
	    	clone.add(item.clone());
	    return clone;
	}*/
	
	
	/*
	public Animate(String[] properties, int duration, int easing, Callable<Integer> myFunc, BufferedImage myImage){
		this.properties = properties;
		this.myComponent = null;
		this.duration = duration;
		this.easing = easing;
		this.myFunc = myFunc;
		this.myImage = myImage;
		
		myTimer = new Timer();
		myTimer.schedule(new Animation(), 0, this.easing);
	}
	//*/


		private int count = 0;
		
		private float step;
		
		
		private Color lastColor;
		private Color newColor;
		private float alpha, red, green, blue; 

		private int lastWidth, lastHeight;
		private int newWidth, newHeight;
		private float stepWidth, stepHeight;
		
		private int lastX, lastY;
		private int newX, newY;
		private float stepX, stepY;
		
		
		private int lastFontSize;
		private int newFontSize;
		private float stepFontSize;
		
		private float stepOpacity;
		private float opacity;
		
		
		private float[] hsbStep = new float[3];
		private float[] hsbNewColor = new float[3];
		private float[][][] hsbLastColor;
		private float[] hsbNowColor = new float[3];
		
		public boolean isActive() {
			return isActive;
		}


		public boolean isActive = true;
		
		public void stop(){
			isActive = false;
			
			AnimationStack2.remove(AnimationStack2.indexOf(this));
			
			if(myFunc != null){
				try {
					myFunc.call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		

		public void refresh(Graphics2D dstBuffer){
			if(myComponent != null){	//Component
				if(count * easing > duration && isActive == true){		//end
					stop();
				}else{
					if(count == 0){
						step = (float)duration/(float)easing;
						
						switch(properties[0]){
							case "background": 
							case "foreground":
								
								newColor = new Color(Integer.parseInt(properties[1].split("\\s*,\\s*")[0]),Integer.parseInt(properties[1].split("\\s*,\\s*")[1]),Integer.parseInt(properties[1].split("\\s*,\\s*")[2]),Integer.parseInt(properties[1].split("\\s*,\\s*")[3]));
								
								if(properties[0].equalsIgnoreCase("background"))
									lastColor = myComponent.getBackground();
								else if(properties[0].equalsIgnoreCase("foreground"))
									lastColor = myComponent.getForeground();
	
								alpha = (newColor.getAlpha() - lastColor.getAlpha())/step ;
								red = (newColor.getRed() - lastColor.getRed())/step ;
								green = (newColor.getGreen() - lastColor.getGreen())/step ;
								blue = (newColor.getBlue() - lastColor.getBlue())/step ;
	
							break;
							case "size":
								lastWidth = (int) myComponent.getSize().getWidth();
								lastHeight = (int) myComponent.getSize().getHeight();
								
								newWidth = Integer.parseInt(properties[1].split("\\s*,\\s*")[0]);
								newHeight = Integer.parseInt(properties[1].split("\\s*,\\s*")[1]);
								
								stepWidth = (newWidth - lastWidth)/step;
								stepHeight = (newHeight - lastHeight)/step;
							break;
							case "fontSize":
								lastFontSize = myComponent.getFont().getSize();
								newFontSize = Integer.parseInt(properties[1].split("\\s*,\\s*")[0]);
								stepFontSize = (newFontSize - lastFontSize )/step;
							break;
						}
					}else{
						
						switch(properties[0]){
							case "background": 
								newColor = new Color(lastColor.getRed() + (int)(red * count), lastColor.getGreen() + (int)(green * count),lastColor.getBlue() + (int)(blue * count), lastColor.getAlpha() + (int)(alpha * count));
								myComponent.setBackground(newColor);
							break;
							case "foreground":
								newColor = new Color(lastColor.getRed() + (int)(red * count), lastColor.getGreen() + (int)(green * count),lastColor.getBlue() + (int)(blue * count), lastColor.getAlpha() + (int)(alpha * count));
								myComponent.setForeground(newColor);
							break;
							case "size":
								newWidth = lastWidth + (int)(stepWidth * count);
								newHeight = lastHeight + (int)(stepHeight * count);
								myComponent.setSize(new Dimension(newWidth, newHeight));   //Preferred
							break;
							case "fontSize":
								newFontSize = lastFontSize + (int)(stepFontSize * count);
								myComponent.setFont(new Font(myComponent.getFont().getFontName(), myComponent.getFont().getStyle(), newFontSize));   //Preferred
							break;
						}
					}
				}
			}else if(myImage != null){	//BufferedImage
				if(count * easing > duration && isActive == true){		//end
					stop();
				}else{
					if(count == 0){
						step = (float)duration/(float)easing;
						
						switch(properties[0]){
							case "size":
								lastWidth = width;
								lastHeight = height;
								
								newWidth = Integer.parseInt(properties[1].split("\\s*,\\s*")[0]);
								newHeight = Integer.parseInt(properties[1].split("\\s*,\\s*")[1]);
								
								stepWidth = (newWidth - lastWidth)/step;
								stepHeight = (newHeight - lastHeight)/step;
								
							break;
							
							case "fade":
								
								stepOpacity = 1.0f/step;
								opacity = 1.f;
							break;
							
							case "fadein":
								
								stepOpacity = 1.0f/step;
								opacity = 0.f;
							break;
							
							case "color":

								//hsb
								Color.RGBtoHSB(Integer.parseInt(properties[1].split("\\s*,\\s*")[0]),Integer.parseInt(properties[1].split("\\s*,\\s*")[1]),Integer.parseInt(properties[1].split("\\s*,\\s*")[2]), hsbNewColor);
																
								hsbNowColor[HUE] = hsbNewColor[HUE]; 

								
								//System.out.println("SATURATION: " + hsbNowColor[SATURATION]);

								
								hsbLastColor = new float[width][height][3];
								
								//System.out.println("W: " + width + " H: " + height);
								
								
								for(int i = 0; i < width; i++){
									for(int j = 0; j < height; j++){
										lastColor = new Color(((BufferedImage) myImage.getMyImage()).getRGB(i, j), true);
										Color.RGBtoHSB(lastColor.getRed(), lastColor.getGreen(), lastColor.getBlue(), hsbLastColor[i][j]);
									}
								}
								
							break;
							
							case "translation":
								lastX = x;
								lastY = y;
								
								newX = Integer.parseInt(properties[1].split("\\s*,\\s*")[0]);
								newY = Integer.parseInt(properties[1].split("\\s*,\\s*")[1]);
								
								stepX = (newX - lastX)/step;
								stepY = (newY - lastY)/step;
							break;
							
							case "visible":
								System.out.println("visible count 0");
								dstBuffer.drawImage(myImage.getMyImage(), x, y, null);
							break;
						}
					}else{
						
						
						
						switch(properties[0]){
							case "size":
								/*
								newWidth = lastWidth + (int)(stepWidth * count);
								newHeight = lastHeight + (int)(stepHeight * count);
								*/
								
								x += 5;
								y += 5;
								
								//dstBuffer.drawImage(myImage, x, y, null);
								
								dstBuffer.setColor(Color.BLACK);
								ImageBox.clear(dstBuffer, panel, 1000, 1000);
								dstBuffer.setColor(Color.RED);
								dstBuffer.fillRect(x, y, 10, 10);
								panel.repaint();
								

							break;
							
							case "visible":
								
								dstBuffer.drawImage(myImage.getMyImage(), x, y, null);
							break;
							
							
							case "fade":
								
								if(opacity - stepOpacity > 0.f)
									opacity -= stepOpacity;
								else
									opacity = 0.f;
								

								int type = AlphaComposite.SRC_OVER; 
								AlphaComposite composite = AlphaComposite.getInstance(type, opacity);
								
								dstBuffer.setComposite(composite);
								dstBuffer.drawImage(myImage.getMyImage(), x, y, null);
								
								
							break;
							
							case "fadein":
								
								if(opacity + stepOpacity < 1.f)
									opacity += stepOpacity;
								else
								{
									opacity = 1.f;
									if(isActive == true){
										myImage.setVisible(true);
										myImage.setX(this.x);
										myImage.setY(this.y);
									}
								}
								

								int type2 = AlphaComposite.SRC_OVER; 
								AlphaComposite composite2 = AlphaComposite.getInstance(type2, opacity);
								
								dstBuffer.setComposite(composite2);
								dstBuffer.drawImage(myImage.getMyImage(), x, y, null);
								
								
							break;
							
							case "color":

								for(int i = 0; i < width; i++){
									for(int j = 0; j < height; j++){
										lastColor = new Color(((BufferedImage)myImage.getMyImage()).getRGB(i, j), true);

										//if pixel is not transparent, change it
										if(lastColor.getAlpha() != 0){
											
											hsbNowColor[SATURATION] = hsbLastColor[i][j][SATURATION] - (hsbLastColor[i][j][SATURATION] - hsbNewColor[SATURATION])/step * count ;
											hsbNowColor[BRIGHTNESS] = hsbLastColor[i][j][BRIGHTNESS] - (hsbLastColor[i][j][BRIGHTNESS] - hsbNewColor[BRIGHTNESS])/step * count ;
											
											
											Color midNewColor = Color.getHSBColor(hsbNowColor[HUE], hsbNowColor[SATURATION], hsbNowColor[BRIGHTNESS]);  
	
											((BufferedImage) myImage.getMyImage()).setRGB(i, j, ((lastColor.getAlpha() & 0xFF) << 24) | (midNewColor.getRGB() & 0xFFFFFF));
										}
									}
								}
							break;
							
							case "translation":

								newX = lastX + (int)(stepX * count);
								newY = lastY + (int)(stepY * count);

								//dstBuffer.drawImage(this.myImage, newX, newY, null);
								myImage.setX(newX);
								myImage.setY(newY);
							break;
						}
					}
				}
			}else if(text != null){	//Text
				if(count * easing > duration && isActive == true){		//end
					stop();
				}else{
					if(count == 0){
						step = (float)duration/(float)easing;
						
						switch(properties[0]){

							case "fadein":

								stepOpacity = 1.0f/step;
								opacity = 1.f;
							break;
							
							
						}
					}else{
						
						switch(properties[0]){

							
							case "fadein":
								
								if(opacity - stepOpacity > 0.f)
									opacity -= stepOpacity;
								else
									opacity = 0.f;

								
								int type = AlphaComposite.SRC_OVER; 
								AlphaComposite composite = AlphaComposite.getInstance(type, opacity);
								
								dstBuffer.setColor(Color.WHITE);
								dstBuffer.setFont(new Font("Verdana", Font.BOLD, 92)); //� modifier,   rendre configurable
								dstBuffer.setComposite(composite);
								
								dstBuffer.drawString(text, x, y);
							break;
							
							
						}
					}
				}
			}
			count++;
		}
		
		
		public int getBufferX() {
			return bufferX;
		}
		public void setBufferX(int bufferX) {
			this.bufferX = bufferX;
		}
		
		public int getBufferY() {
			return bufferY;
		}
		public void setBufferY(int bufferY) {
			this.bufferY = bufferY;
		}
		
		
	}


//}
