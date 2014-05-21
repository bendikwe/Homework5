/**
 * Graphics Virtual Machine
 *
 * @author Bendik Wendt Simonsen & Mads Riegel
 */
import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;


public class GVM 
{
    //Assigning constants(changing from int to string)
    public final static int IMAGE_SIZE = 800;
    private final String STOP = "stop";
    private final String SET = "set";
    private final String LOAD = "load";
    private final String STORE = "store";
    private final String ADD = "add";
    private final String ZERO = "zero";
    private final String GOTO = "goto";
    private final String SETCOLOR = "setcolor";
    private final String DRAWLINE = "drawline";
    private final String DRAWRECT = "drawrect";
    private final String FILLRECT = "fillrect";
    private final String DRAWOVAL = "drawoval";
    private final String FILLOVAL = "filloval";
    
    //Assigning datamemory addresses
    private final int ACC = 0;
    private final int X = 1;
    private final int Y = 2;
    private final int WIDTH = 3;
    private final int HEIGHT = 4;
    private final int RED = 5;
    private final int GREEN = 6;
    private final int BLUE = 7;
    
    //Declaring attributes
    private final int[] dataMemory;
    private final ArrayList<String> programMemory;
    String[] parts;
    private int index;
    private int counter;
    private int errorcounter;
    private int linecounter;
    private Color color;
    private final Image image;
    private final Graphics graphics;
    private final Map<String, Integer> definitions;
    TextFields textfields;
    long startTime;
    long endTime;
    long duration;
    double seconds;
    
    GVM(TextFields textfields) 
    {   
        //Assigning values to attributes
        startTime = 0;
        endTime = 0;
        duration = 0;
        seconds = 0;
        this.textfields = textfields;
        errorcounter = 0;
        linecounter = 1;
        counter = 0;
        index = 0;
        programMemory = new ArrayList<>();
        dataMemory = new int[100];
        
        //starting to build the hashmap
        definitions = new HashMap<>();
        definitions.put("ACC",0);
        definitions.put("X",1);
        definitions.put("Y",2);
        definitions.put("WIDTH",3);
        definitions.put("HEIGHT",4);
        definitions.put("RED",5);
        definitions.put("GREEN",6);
        definitions.put("BLUE",7);
              
        //painting Image white
        image = new BufferedImage( IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB );
        graphics = image.getGraphics();
        graphics.setColor(WHITE);
        graphics.fillRect(0,0,800,800);
        graphics.setColor(BLACK);
        
    }
    
    //getImage function

    /**
     *
     * @return
     */
    public Image getImage() { return this.image; }
    
        
    
    //Replacing all constants in programmemory with the value assigned to it
    private void replaceConstants(Map<String, Integer> hash, ArrayList<String> programMemory){
        for (int i = 0; i < programMemory.size(); i++) {
            if (hash.get(programMemory.get(i)) != null){
                programMemory.set(i,Integer.toString(hash.get(programMemory.get(i))));
            }
        }
    }
    
    //reading and assigning elements to either definitions
    //or programmemory and writing to textareas
    private void readFile(BufferedReader reader) throws IOException{
        startTime = System.nanoTime(); 
        textfields.setControlfile("Starting assembly ...");
        for ( String line; ( line = reader.readLine() ) != null; ) 
                {
                    textfields.setProgramfile(linecounter + " " + line);
                    linecounter++;
                    line = line.trim();
                    if (line.startsWith("define")){
                        line = line.replaceFirst("define", "");
                        line = line.trim();
                        if(line.startsWith("#")){
                            errorcounter++;
                            textfields.setControlfile((linecounter-1) + ": The number of operands for this opcode is incorrect.");
                            continue;
                        }
                        parts = line.split("\\s+");
                        try {
                            definitions.put(parts[0],Integer.parseInt(parts[1]));
                        }
                        catch(NumberFormatException e) {
                        errorcounter++;
                        textfields.setControlfile((linecounter-1) + ": The number of operands for this opcode is incorrect.");
                        }
                    }
                    else if(line.startsWith("#")){
                    }
                    else {
                        parts = line.split("\\s+");
                        if(parts.length==3){
                             definitions.put(parts[0],counter);   
                        }
                        for (String part : parts) {
                            if (part.startsWith("#")) {
                                break;
                            }
                            programMemory.add(part);
                            counter++;
                            //programMemory.remove("");
                        }
                        }
                }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        seconds = (double)duration / 1000000000.0;
        if (errorcounter == 0){
            textfields.setControlfile("BUILD SUCCESSFUL. Assembly time: " + seconds + " sec.");
        }
        else{
           textfields.setControlfile("BUILD FAILED. Assembly time: " + seconds + " sec. Number of invalid statements: " + errorcounter); 
        }
    }
    
    //Creating choose file option for user, and loading content

    /**
     *
     * @throws IOException
     */
        public void load() throws IOException
    {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog( null );
        if ( returnValue == JFileChooser.APPROVE_OPTION )
        {
            File readFile = fileChooser.getSelectedFile();
            try ( BufferedReader reader = new BufferedReader( new FileReader( readFile.getCanonicalPath() ) ) )
            {
                readFile(reader);
            }
            replaceConstants(definitions,programMemory);
        }
    }
        
    //performing one step of the assembly code

    /**
     *
     */
        public void step()
    { 
        if (!programMemory.get(index).equals(STOP)){
            executeInstruction(programMemory.get(index));
        }
    }
    
    //running the entire assembly code

    /**
     *
     */
        public void run()
    {
        while (!programMemory.get(index).equals(STOP)){
             executeInstruction(programMemory.get(index));
        }
    }
    
    //Switch cases for the assemblycode
    private void executeInstruction( String instruction )
    {
        switch (instruction){
            case STOP:
                break;
            case SET:
                index++;
                dataMemory[ACC] = Integer.parseInt(programMemory.get(index));
                index++;
                break;
            case LOAD:
                index++;
                dataMemory[ACC] = dataMemory[Integer.parseInt(programMemory.get(index))];
                index++;
                break;
            case STORE:
                index++;
                dataMemory[Integer.parseInt(programMemory.get(index))] = dataMemory[ACC];
                index++;
                break;
            case ADD:
                index++;
                dataMemory[ACC] += dataMemory[Integer.parseInt(programMemory.get(index))];
                index++;
                break;
            case ZERO:
                index++;
                if (dataMemory[ACC] != 0){
                    try {
                    index = Integer.parseInt(programMemory.get(index));
                }
                catch(NumberFormatException e) {
                    index = programMemory.lastIndexOf(programMemory.get(index));
                    break;
                }
                }
                else{
                    index++;
                }
                break;
            case GOTO: 
                index++;
                try {
                    index = Integer.parseInt(programMemory.get(index));
                }
                catch(NumberFormatException e) {
                    index = programMemory.lastIndexOf(programMemory.get(index));
                    break;
                }
                break;
            case SETCOLOR:
                color = new Color (dataMemory[RED],dataMemory[GREEN], dataMemory[BLUE]);
                graphics.setColor(color);
                index++;
                break;
            case DRAWLINE:
                graphics.setColor(color);
                graphics.drawLine(dataMemory[X],dataMemory[Y],
                        dataMemory[X]+dataMemory[WIDTH],dataMemory[Y]+dataMemory[HEIGHT]);
                index++;
                break;
            case DRAWRECT:
                graphics.setColor(color);
                graphics.drawRect(dataMemory[X],dataMemory[Y],
                        dataMemory[WIDTH],dataMemory[HEIGHT]);
                index++;
                break;
            case FILLRECT:
                graphics.setColor(color);
                graphics.fillRect(dataMemory[X],dataMemory[Y],
                        dataMemory[WIDTH],dataMemory[HEIGHT]);
                index++;
                break;
            case DRAWOVAL:
                graphics.setColor(color);
                graphics.drawOval(dataMemory[X],dataMemory[Y],
                        dataMemory[WIDTH],dataMemory[HEIGHT]);
                index++;
                break;
            case FILLOVAL:
                graphics.setColor(color);
                graphics.fillOval(dataMemory[X],dataMemory[Y],
                        dataMemory[WIDTH],dataMemory[HEIGHT]);
                index++;
                break;
            //if unknow label, continue reading to next index
            default:
                index++;
                
        }
    }
}