/**
 *
 * @author Bendik Wendt Simonsen & Mads Riegel
 */
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
 
public class View extends JPanel
{
    final static int IMAGE_SIZE = 800;
    private Image image;
     
    @Override
    public void paintComponent( Graphics graphics )
    {
        super.paintComponent( graphics );
        graphics.drawImage( image, 0, 0, IMAGE_SIZE, IMAGE_SIZE, this );
    }
    // Declare a setter method for the Image attribute, to be invoked by the Controller.

    void setImage(Image image) {
        this.image = image;
    }
}