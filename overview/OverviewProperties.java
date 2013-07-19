package overview;
import java.awt.Color;
import java.util.ArrayList;


public class OverviewProperties {
	
	// Color
	private static final Color BLUE = new Color(50, 120, 240), YELLOW = new Color(255, 255, 110);
	
	private static final Color PURPLE = new Color(196, 145, 217, 255),
							   INDIGO = new Color(145, 158, 217, 255),
							   DARK_GREEN = new Color(145, 217, 195, 255),
							   BROWN = new Color(213, 217, 147, 255),
							   LAST = new Color(196, 145, 217, 255);
	
	
	private static final Color TITLE_COLOR = new Color(230, 230, 230);	
	private static final Color GRID_COLOR = new Color(200, 200, 200);
	private static final Color BACKGROUND_COLOR = new Color(50, 50, 50);
	private static final Color[] TASK_COLOR = {PURPLE, INDIGO, DARK_GREEN, BROWN, LAST};
	private static final String[][] IMG_PATH = {{"images/1/A/1a.png", "images/1/B/1a.png"}, {"images/2/A/1a.png", "images/2/B/1a.png"},{"images/3/A/1a.png", "images/3/B/1a.png"},{"images/4/A/1a.png", "images/4/B/1a.png"}}; //ajouter tâche 5
	
	
	private static final Color[] CHECKBOX_COLOR = {PURPLE, INDIGO, DARK_GREEN, BROWN, LAST};
	private static final String[] CHECKBOX_NAME = {"Dual", "Task", "DM", "SM", "SP"};
	
	
	
	
	private ArrayList<ArrayList<ArrayList<String>>> msg = new ArrayList<ArrayList<ArrayList<String>>>();
	private ArrayList<ArrayList<String>> tempMsg2 = new ArrayList<ArrayList<String>>();
	private ArrayList<String> tempMsg = new ArrayList<String>();
	
	
	private ArrayList<ArrayList<SimplifiedTask>> line;
	private int[] tasks;
		
	private Color[] lineColor = {BLUE, YELLOW};
	private String[] legend = {"Main Gauche", "Main Droite"};	
	
	private int width, height, innerWidth, innerHeight ;	
	private int x, y, nbXDivision, nbYDivision;
	private int max, min;
	
	
	private boolean checkbox = false;
	private boolean inversed = false;

	public OverviewProperties(ArrayList<ArrayList<SimplifiedTask>> line, int width, int height, String legend) {
		this.line = line;
		max = findMax(this.line);
		min = (findMin(this.line));		
		
		this.x = 50;
		this.y = 100;
		this.nbXDivision = this.line.get(0).size();
		this.nbYDivision = 8;
		
		this.width = width;
		this.height = height;
		this.innerWidth = this.width - 200;
		this.innerHeight = this.height - 200;	
		

		tasks = new int[this.line.get(0).size()];
		
		// Set the msg
		for(int ii = 0; ii < this.line.size(); ii++){
			for(int i = 0; i < this.line.get(0).size(); i++){
				tasks[i] = this.line.get(ii).get(i).getTaskVersion();
				
				tempMsg.add(this.line.get(ii).get(i).getDate());
				
				//Philippe - 1
				if(legend.equalsIgnoreCase("speed"))
					tempMsg.add(Math.abs((int)this.line.get(ii).get(i).getMean()) + " sec");
				if(legend.equalsIgnoreCase("acc"))
					tempMsg.add(Math.abs((int)this.line.get(ii).get(i).getMean()) + " %");
				//Philippe
				
				tempMsg2.add(tempMsg);
				tempMsg = new ArrayList<String>();
			}
			msg.add(tempMsg2);
			tempMsg2 = new ArrayList<ArrayList<String>>();
		}
	}

	
	private int findMax(ArrayList<ArrayList<SimplifiedTask>> myArray){
		//find the max of a 2D-Array (for the highest dot)
		int max = 0;
		
		for(int j = 0; j < myArray.size(); j++){
			for(int i = 0; i < myArray.get(j).size(); i++){
				if(myArray.get(j).get(i).getMean() > max)
					max = (int) myArray.get(j).get(i).getMean();
			}
		}
		return max;	
	}
	
	private int findMin(ArrayList<ArrayList<SimplifiedTask>> myArray){
		//find the min of a 2D-Array (for the lowest dot)
		int min = 10000;
		
		for(int j = 0; j < myArray.size(); j++){
			for(int i = 0; i < myArray.get(j).size(); i++){
				if(myArray.get(j).get(i).getMean() < min)
					min = (int) myArray.get(j).get(i).getMean();
			}
		}
		return min;	
	}
	
	
	/* --- Getter & Setter ---*/
	
	public ArrayList<ArrayList<ArrayList<String>>> getMsg() {
		return msg;
	}
	public void setMsg(ArrayList<ArrayList<ArrayList<String>>> msg) {
		this.msg = msg;
	}

	public int[] getTasks() {
		return tasks;
	}
	public void setTasks(int[] tasks) {
		this.tasks = tasks;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}

	public Color[] getLineColor() {
		return lineColor;
	}
	public void setLineColor(Color[] lineColor) {
		this.lineColor = lineColor;
	}

	public ArrayList<ArrayList<SimplifiedTask>> getLine() {
		return line;
	}
	public void setLine(ArrayList<ArrayList<SimplifiedTask>> line) {
		this.line = line;
	}

	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int getInnerWidth() {
		return innerWidth;
	}
	public void setInnerWidth(int innerWidth) {
		this.innerWidth = innerWidth;
	}

	public int getInnerHeight() {
		return innerHeight;
	}
	public void setInnerHeight(int innerHeight) {
		this.innerHeight = innerHeight;
	}

	public int getNbXDivision() {
		return nbXDivision;
	}
	public void setNbXDivision(int nbXDivision) {
		this.nbXDivision = nbXDivision;
	}

	public int getNbYDivision() {
		return nbYDivision;
	}
	public void setNbYDivision(int nbYDivision) {
		this.nbYDivision = nbYDivision;
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}


	public boolean isCheckbox() {
		return checkbox;
	}


	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}


	public boolean isInversed() {
		return inversed;
	}


	public void setInversed(boolean inversed) {
		this.inversed = inversed;
	}


	public static Color getTitleColor() {
		return TITLE_COLOR;
	}
	public static Color getGridColor() {
		return GRID_COLOR;
	}
	public static Color getBackgroundColor() {
		return BACKGROUND_COLOR;
	}
	public static Color[] getTaskColor() {
		return TASK_COLOR;
	}
	public static String[][] getImgPath() {
		return IMG_PATH;
	}


	public static Color[] getCheckboxColor() {
		return CHECKBOX_COLOR;
	}


	public static String[] getCheckboxName() {
		return CHECKBOX_NAME;
	}
}
