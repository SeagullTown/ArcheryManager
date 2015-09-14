package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.Medlem;
import controller.Controller;

/*
 01111001 01101111 01110101 00100000 01101010 
 01110101 01110011 01110100 00100000 01101000 
 01100001 01100100 00100000 01110100 01101111 
 00100000 01100011 01101000 01100101 01100011 
 01101011 00101100 00100000 01100100 01101001 
 01100100 01101110 00100111 01110100 00100000 
 01111001 01101111 01110101 00111111 
 */

public class Hoved extends JFrame {

	private ToolBar toolbar;
	private Reg reg;
	private TablePanel tablepanel;
	private EditMember editPanel;
	private Statistics demography;

	private JFileChooser fileChooser;
	private Preferences prefs;
	
	//keeps track of the visibility of the panels.
	private boolean regVisible = false;
	private boolean tableVisible = false;
	private boolean editMemberVisible = false;
	private boolean demographyVisible = false;

	private Controller controller;

	/*
	 * Constructor
	 */
	Hoved() {
		super("ClubManager (Version 1.0)");
		setLayout(new BorderLayout());

		/*
		 * setting the look of the application to that of the current system.
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * initializing components
		 */
		toolbar = new ToolBar();
		reg = new Reg();
		tablepanel = new TablePanel();

		setJMenuBar(createMenuBar());
		prefs = Preferences.userRoot().node("click");
		controller = new Controller();

		/*
		 * filling the members table.
		 */
		tablepanel.setData(controller.getMedlemmer());
		demography = new Statistics(controller.getMedlemTall(), controller.getMedlemStotte(), controller.getMedlemAntall(),
				controller.getSrKvinner(), controller.getSrMenn(), controller.getJrKvinner(), controller.getJrMenn());
		/*
		 * initializing utilities.
		 */
		fileChooser = new JFileChooser();

		/*
		 * Toolbar listener Takes Strings from the buttons on the toolbar and
		 * performs actions depending on the string sent, crude but it works.
		 * 
		 * TODO: fix this shit
		 */
		toolbar.setWindowChange(new WindowChange() {
			public void setWindow(String text) {
				
				
				 // switch case to handle panel display
				switch(text) {
					
				case "menu": 
					removePanels();
					break;
				case "reg":
					showReg();
					break;
				case "table":
					showTable();
					break;
				case "statistics":
					showDemography();
					break;
				}
				

			}
		});

		/*
		 * Listener on the table
		 */
		tablepanel.setMedlemTableListener(new MedlemTableListener() {
			public void rowDeleted(String firstName, String lastName) {
				controller.removeRecord(firstName, lastName);
			}

			public void rowEdit(int row) {
				showEdit(row);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see gui.MedlemTableListener#copyRow(int) method adds a row to
			 * the windows clipboard
			 */
			@Override
			public void copyRow(int row) {

				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				String text = tablepanel.getText(row);
				StringSelection selection = new StringSelection(text);
				clipboard.setContents(selection, selection);

			}

			@Override
			public void copySquare(String square) {

				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(square);
				clipboard.setContents(selection, selection);
				System.out.println(square);

			}
		});

		/*
		 * RegistrationListener for when registering a new member.
		 */
		reg.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent e) {

				try {
					controller.addMember(e);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tablepanel.refresh();
				try {
					controller.load();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				demography.refreshData();
			}
		});

		demography.setGraphRefresh(new GraphRefresh() {
			public void refresh() {
				demography.setDataMedlemmer(controller.getMedlemTall());
				demography.setDataStotte(controller.getMedlemStotte());
				demography.setSrKvinner(controller.getSrKvinner());
				demography.setSrMenn(controller.getSrMenn());
				demography.setJrKvinner(controller.getJrKvinner());
				demography.setJrMenn(controller.getJrMenn());
				demography.setMedlemTotal(controller.getMedlemAntall());
			}
		});
		

		/*
		 * initial startup panels
		 * 
		 * as the program will have more uses. reg should not be show on
		 * startup. table only should suffice and looks cleaner.
		 */
		setToolbar();
		showTable();
		tableVisible = true;
		/*
		 * handles the closing of the application by disposing and running the
		 * garbagecollector.
		 * 
		 * also saves the position of the window to the preferences.
		 */
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					controller.save();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// controller.disconnect();
				prefs.put("x", Integer.toString(getX()));
				prefs.put("y", Integer.toString(getY()));
				dispose();
				System.gc();

			}

		});

		/*
		 * retrieves window coordinates from the prefs stored from the last
		 * closing of the application.
		 */
		int posX = prefs.getInt("x", 0);
		int posY = prefs.getInt("y", 0);

		setMinimumSize(new Dimension(600, 400));
		setSize(900, 600);

		// using a listener to close the program so it can store and do a few
		// last tasks before closing.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setLocation(posX, posY);
		setVisible(true);
		/*
		 * methods that have to be initiated last
		 */
		tablepanel.setRowSort();
		demography.refreshData();

	}

	private void connect() {

		try {

			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Hoved.this, "Cannot connect to database.", "Database Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
		// loading.setVisible(false);
	}

	public void setToolbar() {
		add(toolbar, BorderLayout.NORTH);
	}

	/*
	 * showReg will not remove table panel as they should both be visible at the
	 * same time.
	 */
	public void showReg() {
		if (demographyVisible == true) {
			remove(demography);
			demographyVisible = false;
		}
		if (regVisible == true) {
			remove(reg);
			regVisible = false;
		} else {
			if (editMemberVisible == true) {
				remove(editPanel);
			}

			add(reg, BorderLayout.WEST);
			reg.setVisible(true);
			regVisible = true;
		}

		revalidate();
		repaint();
	}

	/*
	 * ShowTable will not remove registration panel as they should both be
	 * visible at the same time.
	 */
	public void showTable() {
		if (demographyVisible == true) {
			remove(demography);
			demographyVisible = false;
		}
		if (tableVisible == true) {
			remove(tablepanel);
			tableVisible = false;
		} else {
			add(tablepanel, BorderLayout.CENTER);
			tablepanel.setVisible(true);
			tableVisible = true;
		}

		revalidate();
		repaint();
	}

	/*
	 * showEdit don't have hide/show function as its creation and info filling
	 * is handled after editmember is clicked in the program.
	 */
	public void showEdit(int row) {
		Medlem member;
		if (demographyVisible == true) {
			remove(demography);
			demographyVisible = false;
		}

		if (regVisible == true) {
			remove(reg);
			regVisible = false;
		}

		member = controller.getMedlemmer().get(row);

		editPanel = new EditMember(member);
		add(editPanel, BorderLayout.WEST);
		editPanel.setVisible(true);
		editMemberVisible = true;
		editPanel.setEditMedlemListener(new EditMedlemListener() {

			public void editEventOccured(UpdateFormEvent e) {
				controller.updateMedlem(e);
				tablepanel.refresh();
			}
		});
		revalidate();
		repaint();
	}

	/*
	 * showDemography will first remove all other panels as it requires the
	 * entire panel for now, demography is dynamic and even if another panel
	 * were to be shown west or east. north or south would warp the content too
	 * much.
	 */
	public void showDemography() {
		if (regVisible == true) {
			remove(reg);
			regVisible = false;
		}
		if (editMemberVisible == true) {
			remove(editPanel);
			editMemberVisible = false;
		}
		if (tableVisible == true) {
			remove(tablepanel);
			tableVisible = false;
		}
		if (demographyVisible == true) {
			remove(demography);
			demographyVisible = false;
		} else {
			add(demography, BorderLayout.CENTER);
			demographyVisible = true;
		}

		revalidate();
		repaint();
	}

	/*
	 * this method removes all the panels in this panel.
	 */
	public void removePanels() {
		if (demographyVisible == true) {
			remove(demography);
			demographyVisible = false;
		}
		if (tableVisible == true) {
			remove(tablepanel);
			tableVisible = false;
		}
		if (regVisible == true) {
			remove(reg);
			regVisible = false;
		}
		if (editMemberVisible == true) {
			remove(editPanel);
			editMemberVisible = false;
		}
		repaint();
		revalidate();
	}
	
	public void hidePanel(JPanel panel) {
		remove(panel);
		panel.setVisible(false);
		
		
	}

	/*
	 * Creates the menubar at the top of the display
	 * 
	 * 
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem connectDatabase = new JMenuItem("Connect to Database");
		JMenuItem refreshTable = new JMenuItem("Refresh table");
		JMenuItem exportDataItem = new JMenuItem("Export Data");
		JMenuItem importDataItem = new JMenuItem("Import Data");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(connectDatabase);
		fileMenu.add(refreshTable);
		fileMenu.addSeparator();
		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem showFormItem = new JMenuItem("Member Form");
		showMenu.add(showFormItem);
		windowMenu.add(showMenu);

		menubar.add(fileMenu);
		menubar.add(windowMenu);

		/*
		 * ActionListener for importing datafile
		 */
		importDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(Hoved.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablepanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Hoved.this, "Could not load data from file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		/*
		 * ActionListener for exporting datafile
		 */
		exportDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(Hoved.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Hoved.this, "Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		/*
		 * ActionListener for exiting program with confirmation popup.
		 */
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(Hoved.this, "Do you really want to exit the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();

					for (WindowListener listener : listeners) {
						listener.windowClosing(new WindowEvent(Hoved.this, 0));
					}
				}
			}
		});

		connectDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					connect();
					controller.load();
					tablepanel.refresh();
					demography.refreshData();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		refreshTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.load();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				tablepanel.refresh();

			}

		});

		/*
		 * sets mnemonics for the menubar.
		 */
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		return menubar;
	}

}
