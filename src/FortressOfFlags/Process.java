package FortressOfFlags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

public class Process extends Application{
	
	private static boolean gameOn = true;
	private static final String ARMY = "Army";
	private static final String X = "X";
	private static final String Y = "Y";
	private static final String TERRAIN = "Terrain";
	private static final String FLAGID = "FlagID";
	private static final String RANK = "Rank";
	private static final String REVEALED = "Revealed";
	private static final String NONE = "None";
	private static final String GRASS = "Grass";
	
	private static ArrayList<RedPiece> redArmy;
	private static ArrayList<RedPiece> blueArmy;
	private static ArrayList<Environment> envList;

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		
		launch(args);
	
	}
	
	@Override 
	public void start(Stage stage) throws IOException, SAXException, ParserConfigurationException, TransformerException {
				
        stage.setTitle("Scatter Chart");
        final NumberAxis xAxis = new NumberAxis(0, 100, 2);
        final NumberAxis yAxis = new NumberAxis(0, 100, 2);        
        final ScatterChart<Number,Number> sc = new
            ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("X");                
        yAxis.setLabel("Y");
        sc.setTitle("Army Positions");
       
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Red Army");
        series1.getData().add(new XYChart.Data(4.2, 193.2));
        series1.getData().add(new XYChart.Data(2.8, 33.6));
        series1.getData().add(new XYChart.Data(6.2, 24.8));
        series1.getData().add(new XYChart.Data(1, 14));
        series1.getData().add(new XYChart.Data(1.2, 26.4));
        series1.getData().add(new XYChart.Data(4.4, 114.4));
        series1.getData().add(new XYChart.Data(8.5, 323));
        series1.getData().add(new XYChart.Data(6.9, 289.8));
        series1.getData().add(new XYChart.Data(9.9, 287.1));
        series1.getData().add(new XYChart.Data(0.9, -9));
        series1.getData().add(new XYChart.Data(3.2, 150.8));
        series1.getData().add(new XYChart.Data(4.8, 20.8));
        series1.getData().add(new XYChart.Data(7.3, -42.3));
        series1.getData().add(new XYChart.Data(1.8, 81.4));
        series1.getData().add(new XYChart.Data(7.3, 110.3));
        series1.getData().add(new XYChart.Data(2.7, 41.2));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Blue Army");
        series2.getData().add(new XYChart.Data(5.2, 229.2));
        series2.getData().add(new XYChart.Data(2.4, 37.6));
        series2.getData().add(new XYChart.Data(3.2, 49.8));
        series2.getData().add(new XYChart.Data(1.8, 134));
        series2.getData().add(new XYChart.Data(3.2, 236.2));
        series2.getData().add(new XYChart.Data(7.4, 114.1));
        series2.getData().add(new XYChart.Data(3.5, 323));
        series2.getData().add(new XYChart.Data(9.3, 29.9));
        series2.getData().add(new XYChart.Data(8.1, 287.4));
 
        sc.getData().addAll(series1, series2);
        
        
        Task <Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException, IOException, SAXException, ParserConfigurationException, TransformerException {
              // "message2" time consuming method (this message will be seen).
            	PrintWriter writer = new PrintWriter("control.ini", "UTF-8");
        		writer.println("Narrow Pass.map");
        		writer.println("None");
        		writer.println("0");
        		writer.close();
        		java.lang.Process process = Runtime.getRuntime ().exec ("C:\\Users\\kfraser\\workspaceMars\\FoF\\FoFAIS.exe");

        		boolean fileExists = false;
        		while(gameOn){
        			try {
    				    Thread.sleep(2000);                 //1000 milliseconds is one second.
    				} catch(InterruptedException ex) {
    				    Thread.currentThread().interrupt();
    				}
        			File input = new File("AIDataInput.xml");
        			fileExists = input.exists();
        			if(input.exists()){ // File exists twice?
        				
        				getInputData(input); // gets input and loads Redarmy
        				Platform.runLater(() -> {
    						sc.getData().get(0).getData().removeAll(series1.getData());
    						sc.getData().get(1).getData().removeAll(series2.getData());
        					for(RedPiece flag : redArmy){
        						sc.getData().get(0).getData().add(new XYChart.Data(flag.getX(), flag.getY()));
        					}
        					for(RedPiece flag : blueArmy){
        						sc.getData().get(1).getData().add(new XYChart.Data(flag.getX(), flag.getY()));
        					}
        					//sc.getData().addAll(series1, series2);
                        });
        				try {
        				    Thread.sleep(2000);                 //1000 milliseconds is one second.
        				} catch(InterruptedException ex) {
        				    Thread.currentThread().interrupt();
        				}
        				searchForPiece();
        				fileExists = false;	
        				
        			}
        			
        		}

              return null;
            }
          };

          Scene scene  = new Scene(sc, 500, 400);
          Thread thread = new Thread(task);
          thread.setDaemon(true);
          thread.start();
        
        // Create the control.ini file
		// Hardcoded for now
          
          stage.setScene(scene);
          stage.show();
		
		
		/**/
    }
	
	public void updateChart(ScatterChart<Number,Number> sc, Series series1, Series series2){

		
	}
	
	/**
	 * Get the input data from FoFAIS.exe and once harvested delete the input file
	 * @param input
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static void getInputData(File input) throws SAXException, IOException, ParserConfigurationException{
	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();
		input.delete();
		loadArmy(doc);
	}
	
	private static void createOutputData(RedPiece flag, String direction) throws ParserConfigurationException, TransformerException, IOException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("root");
		doc.appendChild(rootElement);
		
		Element command = doc.createElement("Command");
		rootElement.appendChild(command);
		
		command.setAttribute("FlagID", String.valueOf(flag.getFlagID()));
		command.setAttribute("Direction", direction);
		command.setAttribute("SerpentJump", "01");
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		DOMSource source = new DOMSource(doc);
		//StreamResult result = new StreamResult(new File("C:\\Users\\kfraser\\workspaceMars\\FoF\\AICommand.xml"));
		StreamResult result2 = new StreamResult(new File("C:\\Users\\kfraser\\workspaceMars\\FoF\\bin\\AICommand.xml"));

		// Output to console for testing
		StreamResult result = new StreamResult(System.out);
		
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		String c[] = output.split(" ");
		String commandString = c[0]+" "+c[2]+" "+c[1]+" "+c[3];
		System.out.println("String: "+output);
		System.out.println(commandString);
		
		java.io.FileWriter fw = new java.io.FileWriter("AICommand.xml");
		fw.write(commandString);
		fw.close();

		//transformer.transform(source, result);
		//transformer.transform(source, result2);

		System.out.println("File saved!");
	}
	
	public static void loadArmy(Document doc){

		redArmy = new ArrayList<RedPiece>();
		blueArmy = new ArrayList<RedPiece>();
		envList = new ArrayList<Environment>();
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Map");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				Environment env = new Environment();
				env.setX(UtilityProcess.stringToInt(eElement.getAttribute(X)));
				env.setY(UtilityProcess.stringToInt(eElement.getAttribute(Y)));
				env.setTerrain(eElement.getAttribute(TERRAIN));
				if(eElement.getAttribute(ARMY).equals(NONE)){
					env.setTaken(false);
				}
				else{
					env.setTaken(true);
				}
				envList.add(env);

				if(eElement.getAttribute(ARMY).equals(RedPiece.RED)){
					RedPiece flag = new RedPiece();
					flag.setFlagID(UtilityProcess.stringToInt(eElement.getAttribute(FLAGID)));
					flag.setPlayer(RedPiece.RED);
					flag.setRank(eElement.getAttribute(RANK));
					flag.setRevealed(Boolean.valueOf(eElement.getAttribute(REVEALED)));
					flag.setTerrain(eElement.getAttribute(TERRAIN));
					flag.setX(UtilityProcess.stringToInt(eElement.getAttribute(X)));
					flag.setY(UtilityProcess.stringToInt(eElement.getAttribute(Y)));
					redArmy.add(flag);
				}
				if(eElement.getAttribute(ARMY).equals(RedPiece.BLUE)){
					RedPiece flag = new RedPiece();
					flag.setFlagID(UtilityProcess.stringToInt(eElement.getAttribute(FLAGID)));
					flag.setPlayer(RedPiece.BLUE);
					flag.setRank(eElement.getAttribute(RANK));
					flag.setRevealed(Boolean.valueOf(eElement.getAttribute(REVEALED)));
					flag.setTerrain(eElement.getAttribute(TERRAIN));
					flag.setX(UtilityProcess.stringToInt(eElement.getAttribute(X)));
					flag.setY(UtilityProcess.stringToInt(eElement.getAttribute(Y)));
					blueArmy.add(flag);
				}
			}
		}
		System.out.println(redArmy.size());
	}
	
	private static void searchForPiece() throws ParserConfigurationException, TransformerException, IOException{
		RedPiece flag = null;
		String direction = null;
		boolean found = false;
		for(RedPiece piece : redArmy){
			// check that not rank of piece that can't move
			if(piece.getRank() != "King" && piece.getRank() != "Death Trap" && !found ){
				flag = piece;
				switch(clearTerrain(piece)){
				case "North":
					direction = "N";
					createOutputData(flag, direction);
					found = true;
					break;
				case "South":
					direction = "S";
					createOutputData(flag, direction);
					found = true;
					break;
				case "East":
					direction = "E";
					createOutputData(flag, direction);
					found = true;
					break;
				case "West":
					direction = "W";
					createOutputData(flag, direction);
					found = true;
					break;
				case "No move":
					searchForPiece();
					break;
				}
			}
		}
	}
	
	private static String clearTerrain(RedPiece flag){
		//north
		if(getTerrainForCoord(flag.getX(), flag.getY()+1).equals(GRASS)){
			return "North";
		}
		//south
		if(getTerrainForCoord(flag.getX(), flag.getY()-1).equals(GRASS)){
			return "South";
		}
		//east
		if(getTerrainForCoord(flag.getX()+1, flag.getY()).equals(GRASS)){
			return "East";
		}
		//west
		if(getTerrainForCoord(flag.getX()-1, flag.getY()).equals(GRASS)){
			return "West";
		}
		return "No move";
	}
	
	
	private static String getTerrainForCoord(int x, int y){
		for(Environment env : envList){
			if(env.getX() == x && env.getY() == y && !env.isTaken()){
				return env.getTerrain();
			}
		}
		return "No terrain";
	}
}
