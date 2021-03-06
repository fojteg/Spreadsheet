package App.View;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

/**
 * This class is responsible for presenting information about author.
 * @author Wojciech Niemiec
 * @version 1.0.0
 * Date: 2017.05.10
 *
 */
public class InfoWindow extends JDialog {
	/*
	 * A version from serializable.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logs the informations
	 */
	static Logger log = Logger.getLogger(InfoWindow.class);
	
	/**
	 * Constructor
	 */
	public InfoWindow() {
		log.info("Dialog presented");
		
		super.dialogInit();
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setSize(new Dimension(300, 250));

		super.setLayout(null);
		super.setTitle("Author");
		JLabel label = new JLabel("<html><body style=\"padding:20px;text-align: justify\">"
				+ "<h1>Wojciech Niemiec</h1>"
				+ "<p><center>&copy; 2017 github.com/Fojteg<center></p>"
				+ "</body></html>");
		label.setBounds(10, 10, 280, 150);
		super.add(label);
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoWindow.this.setVisible(false);
				InfoWindow.this.dispose();
			}
		});
		button.setSize(100, 30);
		button.setLocation(new Point(90, 160));
		super.add(button);
		super.setVisible(true);
		super.setResizable(false);
	}
};