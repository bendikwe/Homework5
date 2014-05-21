/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Bendik Wendt Simonsen & Mads Riegel
 */
public class TextFields extends JPanel {
    
    //Defining two textareas for the program and the control feedback
    private final JTextArea programArea;
    private final JTextArea controlArea;
    
    //Defining scrollpanes for the textareas
    JScrollPane scrollProgram;
    JScrollPane scrollControl;
    
    //Defining jpanel to attach textareas
    private final JPanel secondcard;

    TextFields() {
        secondcard = new JPanel();
        //Randomely chosen dimensions of textareas
        programArea = new JTextArea(10,30);
        controlArea = new JTextArea(10,30);
        
        //attaching scrollpanes
        scrollProgram = new JScrollPane (programArea, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollControl = new JScrollPane (controlArea, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        
        //adding fields to panel
        secondcard.add(scrollProgram);
        secondcard.add(scrollControl);
        
        //adding panel to Textfield panel
        add(secondcard,BorderLayout.SOUTH);
    } 
    
    //program to add text to program textarea, used by gvm
    public void setProgramfile(String programline){
            programArea.append(programline + "\n");
    }
    //program to add text to control textarea, used by gvm
    public void setControlfile(String controlline){
            controlArea.append(controlline + "\n");
    }
}
