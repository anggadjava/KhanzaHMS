/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget;

import java.awt.Color;
import javax.swing.JComboBox;

/**
 *
 * @author dosen3
 */
public final class ComboBox extends JComboBox {
    private static final long serialVersionUID = 1L;

    public ComboBox(){
        //super();
        setFont(new java.awt.Font("Tahoma", 0, 11));
        //setBackground(new Color(245,180,245));
        //setForeground(new Color(90,90,90));
        setBackground(new Color(245,250,240));
        setForeground(new Color(50,70,40));
        setSize(WIDTH,23);
    } 
}
