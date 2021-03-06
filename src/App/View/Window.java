package App.View;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTipModel;

import App.Model.MyTip;

/**
 * This class defines the Window with everything the application contains.
 * @author Wojciech Niemiec
 * @version 1.0.1
 * Date: 2017.05.25
 *
 */
public class Window extends JFrame {
	/**
	 * A version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logs the informations
	 */
	static Logger log = Logger.getLogger(Window.class);
	
	private JMenu fileMenu;
	private JMenu optionsMenu;
	private JMenu helpMenu;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private JPanel statusBar;
	private JLabel statusLabel;
	private FrontPanel panelHandler;
	private JTipOfTheDay tip;
	
	private JTaskPane taskPane;
	
	/**
	 * Shows the JTipOfTheDay dialog.
	 */
	private void getTip() {
		DefaultTipModel model = new DefaultTipModel();
		model.add(new MyTip("Saving", "<html><body><h3>Progres bar</h3><p>Każda wykonana operacja zostawia ślad po sobie w dolnej części okienka aplikacji. Dzięki temu wiemy co ostatnio zostało wykonane.</p></body></html>"));
		model.add(new MyTip("Saving", "<html><body><h3>Zapisywanie</h3><p>Kliknięcie przycisku zapisu otwiera okienko w którym możemy sobie wybrać plik docelowy.</p></body></html>"));
		tip = new JTipOfTheDay(model);
		tip.setVisible(true);
		tip.showDialog(Window.this);
		
		log.info("constructing Tip");
	}
	
	/**
	 * Initializes the JTaskPane component.
	 */
	private void initTaskPane() {
		JButton button;
		JTaskPaneGroup group;
		taskPane = new JTaskPane();
		
		group = new JTaskPaneGroup();
		group.setTitle("Data setting");
		
		button = new JButton("Clear");
		button.addActionListener(e -> clearTable());
		group.add(button);
		
		button = new JButton("Random");
		button.addActionListener(e -> randomTable());
		group.add(button);
		
		taskPane.add(group);
		
		group = new JTaskPaneGroup();
		group.setTitle("Counting");
		
		button = new JButton("Amount");
		button.addActionListener(e -> findAmount());
		group.add(button);
		
		button = new JButton("Average");
		button.addActionListener(e -> findAverage());
		group.add(button);
		
		button = new JButton("Min Max");
		button.addActionListener(e -> findMinMax());
		group.add(button);
		
		taskPane.add(group);
		
		taskPane.setVisible(true);
		
		log.info("Task pane initialized");
	}
	
	/**
	 * Ininitializes the menu.
	 */
	private void initMenu() {
		menuBar = new JMenuBar();
		
		initFileMenu();
		initOptionsMenu();
		initHelpMenu();
		
		menuBar.add(fileMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
		log.info("constructing menu");
	}

	/**
	 * Defines the File button in a menu area with actions like
	 * creating new table, filling it with random numbers and
	 * saving the current table state to the file.
	 */
	private void initFileMenu() {
		JMenuItem item;
		fileMenu = new JMenu("File");
		
		item = new JMenuItem("New table");
		item.addActionListener(e->{
			clearTable();
		});
		fileMenu.add(item);
		item = new JMenuItem("Fill table with randoms");
		item.addActionListener(e->{
			randomTable();
		});
		fileMenu.add(item);
		item = new JMenuItem("Save table");
		item.addActionListener(e->{
			panelHandler.saveToFile();
		});
		fileMenu.add(item);
		
		log.info("initializing file menu");
	}

	/**
	 * Defines the Options button in a menu area with actions like
	 * finding minimal or maximal value in the table, counting
	 * total amount of values in the table and finding an average.
	 */
	private void initOptionsMenu() {
		JMenuItem item;
		optionsMenu = new JMenu("Options");
		
		item = new JMenuItem("Find min and max");
		item.addActionListener(e->{
			findMinMax();
		});
		optionsMenu.add(item);
		item = new JMenuItem("Count total amount");
		item.addActionListener(e->{
			findAmount();
		});
		optionsMenu.add(item);
		item = new JMenuItem("Count average");
		item.addActionListener(e->{
			findAverage();
		});
		optionsMenu.add(item);
		
		log.info("initializing options menu");
	}
	
	/**
	 * Defines the Help button in a menu area with actions like
	 * showing Help dialog or Information about author dialog
	 */
	private void initHelpMenu() {
		JMenuItem item;
		helpMenu = new JMenu("Help");
		
		item = new JMenuItem("Show help box");
		item.addActionListener(e->{
			showHelpDialog();
		});
		helpMenu.add(item);
		
		item = new JMenuItem("Show info about author");
		item.addActionListener(e->{
			showAuthorDialog();
		});
		helpMenu.add(item);
		
		log.info("initializing help menu");
	}
	
	/**
	 * Fills table with zeros and updates the result area
	 */
	public void clearTable() {
		panelHandler.getTableModel().reset();
		panelHandler.updateResultArea();
		statusLabel.setText("Table cleared");
		
		log.info("Table cleared");
	}

	/**
	 * Fills table with random values and updates the result area
	 */
	public void randomTable() {
		panelHandler.getTableModel().random();
		panelHandler.updateResultArea();
		statusLabel.setText("Table filled with random numbers");
		
		log.info("Table filled with randoms");
	}

	/**
	 * Finds average in the table and updates the result area
	 */
	public void findAverage() {
		panelHandler.getResultArea().setText("Average is: " + panelHandler.getTableModel().average());
		statusLabel.setText("Average found");
		
		log.info("Average found");
	}

	/**
	 * Finds amount in the table and updates the result area
	 */
	public void findAmount() {
		panelHandler.getResultArea().setText("Amount is: " + panelHandler.getTableModel().amount());
		statusLabel.setText("Total amount found");
		
		log.info("Amount found");
	}

	/**
	 * Finds minimal and maximal value in the table and updates the result area
	 */
	public void findMinMax() {
		Double min = panelHandler.getTableModel().min();
		Double max = panelHandler.getTableModel().max();
		panelHandler.getResultArea().setText("Min is: " + min + ", max is: " + max);
		statusLabel.setText("Min and max found");
		
		log.info("Min max found");
	}

	/**
	 * Shows Help dialog with information how to use this application.
	 */
	public void showHelpDialog() {
		new HelpWindow().setLocationRelativeTo(Window.this);
		statusLabel.setText("Help displayed");
		
		log.info("Dialog presented");
	}

	/**
	 * Shows the Autor dialog with information about developer witch made this application
	 */
	public void showAuthorDialog() {
		new InfoWindow().setLocationRelativeTo(Window.this);
		statusLabel.setText("Information about author displayed");
		
		log.info("Author presented");
	}
	
	/**
	 * Initializes the status bar on the bottom of the page witch presents the last
	 * action in the application
	 */
	private void initStatusBar() {
		statusLabel = new JLabel("Application start");
		
		statusBar = new JPanel();
		statusBar.add(statusLabel);
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		log.info("initializing status bar");
	}
	
	/**
	 * Initializes th toolbar with buttons to save table, fill it with randoms,
	 * clear table, find minimal and maximal value, find amount, average, present
	 * information about author and application help.
	 */
	private void initToolBar() {
		toolBar = new JToolBar();
		JButton button = null;
		
		button = new JButton(new ImageIcon("resources/save.png"));
		button.addActionListener(e -> {
			panelHandler.saveToFile();
		});
		toolBar.add(button);
		
		button = new JButton(new ImageIcon("resources/shuffle.png"));
		button.addActionListener(e->{
			randomTable();
		});
		toolBar.add(button);
		
		button = new JButton(new ImageIcon("resources/clear.png"));
		button.addActionListener(e->{
			panelHandler.getTableModel().reset();
			panelHandler.updateResultArea();
		});
		toolBar.add(button);
		
		button = new JButton(new ImageIcon("resources/minmax.png"));
		button.addActionListener(e -> {
			findMinMax();
		});
		toolBar.add(button);
		
		button = new JButton(new ImageIcon("resources/sum.png"));
		button.addActionListener(e -> {
			findAmount();
		});
		toolBar.add(button);
		
		button = new JButton(new ImageIcon("resources/average.png"));
		button.addActionListener(e -> {
			findAverage();
		});
		toolBar.add(button);
		
		button = new JButton(UIManager.getIcon("OptionPane.informationIcon"));
		button.addActionListener(e-> {
			showAuthorDialog();
		});
		toolBar.add(button);

		button = new JButton(UIManager.getIcon("OptionPane.questionIcon"));
		button.addActionListener(e->{
			showHelpDialog();
		});
		toolBar.add(button);
		
		log.info("Initializing toolbar");
	}
	
	/**
	 * Defines the close operation.
	 */
	private void setCustomCloseOperation() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Window.this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new ClosingWindow(Window.this);
			}
		});
		
		log.info("Custom close operation setted");
	}
	
	/**
	 * Creates new instance of Window and runs all init methods.
	 */
	public Window() {
		initMenu();
		initStatusBar();
		initToolBar();
		setCustomCloseOperation();
		initTaskPane();
		
		panelHandler = new FrontPanel();
		panelHandler.setStatusLabel(statusLabel);
		
		super.setLayout(new BorderLayout());
		super.setJMenuBar(menuBar);
		super.add(taskPane, BorderLayout.WEST);
		super.add(statusBar, BorderLayout.SOUTH);
		super.add(toolBar, BorderLayout.PAGE_START);
		super.getContentPane().add(panelHandler);
		super.setSize(panelHandler.getSize().width, panelHandler.getSize().height + 70);
		super.setLocationRelativeTo(null);
		super.setResizable(false);
		super.setVisible(true);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		getTip();
		
		log.info("Window constructed");
	}
}
