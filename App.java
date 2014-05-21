/**
 *
 * @author Bendik Wendt Simonsen & Mads Riegel
 */
 import java.awt.BorderLayout;
 import java.awt.Dimension;
 import java.io.IOException;
 import javax.swing.JFrame;
 
    public class App extends JFrame
{
    //Declaring classes
    private final View view = new View();;
    private final ControlPanel controlPanel;
    private final GVM gvm;
    private final TextFields textfields;
                
    App() throws IOException 
    {
        //assigning values to classes
        textfields = new TextFields();
        gvm = new GVM(textfields);
        controlPanel = new ControlPanel( view, gvm );

        setTitle( "Graphics Virtual Machine" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        //add different panels to frame
        add( view, BorderLayout.CENTER );
        add( controlPanel, BorderLayout.NORTH );
        add( textfields, BorderLayout.SOUTH);

        //set size of frame
        Dimension dimension = new Dimension( GVM.IMAGE_SIZE, GVM.IMAGE_SIZE + controlPanel.getHeight() );
        setSize( dimension  );
        setPreferredSize( dimension );
        setVisible( true );
    }
    
    /**
    * Run the Graphics Virtual Machine application.
    * @param args unused 
        * @throws java.io.IOException 
    */
    public static void main( String[] args ) throws IOException { App app = new App(); }
 }
