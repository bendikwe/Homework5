/**
 *
 * @author Bendik Wendt Simonsen & Mads Riegel
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel 
{
    //defining classes to be used
    private final View view;
    public final GVM gvm;
 
    //Defining Buttons
    private final JButton runButton;
    private final JButton stepButton;
    private final JButton loadButton;

    //defining panel to add buttons into
    private final JPanel firstcard;


   
    ControlPanel( View view, GVM gvm ) throws IOException 
    {
        //assigning the classes
        this.view = view;
        this.gvm = gvm;

        //assigning the panel
        firstcard = new JPanel();

        //naming the buttons
        runButton  = new JButton( "Run" );
        stepButton = new JButton( "Step" );
        loadButton = new JButton( "Load" );

        //add buttons to the panel
        firstcard.add( runButton);
        firstcard.add( stepButton);
        firstcard.add( loadButton);

        //add panel to the Controlpanel panel
        add(firstcard,BorderLayout.CENTER);

        //add actionlisteners to buttons
        initialize();
        //assigning the image
        view.setImage( gvm.getImage() );
    }

    //adding actionlisteners
    private void initialize() 
    {
        runButton.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) 
            {
                runButtonActionPerformed( actionEvent );
            }
        });
               
        stepButton.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) 
            {
                stepButtonActionPerformed( actionEvent );
            }
        });
        
        loadButton.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) 
            {
                try {
                    loadButtonActionPerformed( actionEvent );
                } catch (IOException ex) {
                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    //"listen" after actions
    private void runButtonActionPerformed( ActionEvent actionEvent ) 
    {
        gvm.run();
        view.repaint();
    }

    private void stepButtonActionPerformed( ActionEvent actionEvent ) 
    { 
        gvm.step();
        view.repaint();
    }

    private void loadButtonActionPerformed( ActionEvent actionEvent ) throws IOException 
    { 
        gvm.load();
        view.repaint();
    }
}