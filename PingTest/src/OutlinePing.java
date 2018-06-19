import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class OutlinePing extends JFrame {
	JComboBox com1 = new JComboBox();
	
	public OutlinePing() {
		super("IP Scanner");
		//menu begin
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu scanMenu = new JMenu("Scan");
		JMenu gotoMenu = new JMenu("Go to");
		JMenu commandMenu = new JMenu("Command");
		JMenu favoritesMenu = new JMenu("Favorites");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu helpMenu = new JMenu("Help");
		
		menubar.add(scanMenu);
		menubar.add(gotoMenu);
		menubar.add(commandMenu);
		menubar.add(favoritesMenu);
		menubar.add(toolsMenu);
		menubar.add(helpMenu);
		
		JMenuItem loadFromFilesAction = new JMenuItem("Load from file...");
		JMenuItem exportallAction = new JMenuItem("Exprot All");
		JMenuItem exportselectionAction = new JMenuItem("Export selection");
		JMenuItem quitAction = new JMenuItem("Quit");
		
		scanMenu.add(loadFromFilesAction);
		scanMenu.add(exportallAction);
		scanMenu.add(exportselectionAction);
		scanMenu.addSeparator();
		scanMenu.add(quitAction);
		
		JMenuItem nextalivehostAction = new JMenuItem("Next alive host");
		JMenuItem nextopenportAction = new JMenuItem("Next open port");
		JMenuItem nextdeadhostAction = new JMenuItem("Next dead host");
		JMenuItem previousalivehostAction = new JMenuItem("Previous alive host");
		JMenuItem previousopenportAction = new JMenuItem("Previous open port");
		JMenuItem previousdeadhostAction = new JMenuItem("Previous dead host");
		JMenuItem findAction = new JMenuItem("Find...");
		
		gotoMenu.add(nextalivehostAction);
		gotoMenu.add(nextopenportAction);
		gotoMenu.add(nextdeadhostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(previousalivehostAction);
		gotoMenu.add(previousopenportAction);
		gotoMenu.add(previousdeadhostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(findAction);
		
		JMenuItem showdetailsAction = new JMenuItem("Show details");
		JMenuItem rescanIpsAction = new JMenuItem("Rescan IP(s)");
		JMenuItem deleteIPsAction = new JMenuItem("Delete IP(s)");
		JMenuItem copyipAction = new JMenuItem("Copy IP");
		JMenuItem copydetailsAction = new JMenuItem("Copy details");
		JMenuItem openAction = new JMenuItem("Open");
		
		commandMenu.add(showdetailsAction);
		commandMenu.addSeparator();
		commandMenu.add(rescanIpsAction);
		commandMenu.add(deleteIPsAction);
		commandMenu.addSeparator();
		commandMenu.add(copyipAction);
		commandMenu.add(copydetailsAction);
		commandMenu.addSeparator();
		commandMenu.add(openAction);
		
		JMenuItem addcurrentAction = new JMenuItem("Add current...");
		JMenuItem managefavoritesAction = new JMenuItem("Manage favorites...");
		
		favoritesMenu.add(addcurrentAction);
		favoritesMenu.add(managefavoritesAction);
		
		JMenuItem preferencesAction = new JMenuItem("Preferences...");
		JMenuItem fetchersAction = new JMenuItem("Fetchers...");
		JMenuItem selectionAction = new JMenuItem("Selection");
		JMenuItem scanstatisticsAction = new JMenuItem("Scan statistics");
		
		toolsMenu.add(preferencesAction);
		toolsMenu.add(fetchersAction);
		toolsMenu.addSeparator();
		toolsMenu.add(selectionAction);
		toolsMenu.add(scanstatisticsAction);
		
		JMenuItem gettingstartedAction = new JMenuItem("Getting Started");
		JMenuItem offcialwebsiteAction = new JMenuItem("Offical Website");
		JMenuItem faqAction = new JMenuItem("FAQ");
		JMenuItem reportanissueAction = new JMenuItem("Report an issue");
		JMenuItem pluginsAction = new JMenuItem("Plugins");
		JMenuItem commandlineussageAction = new JMenuItem("Command-line ussage");
		JMenuItem checkfornewerversionAction = new JMenuItem("Check for newer version...");
		JMenuItem aboutAction = new JMenuItem("About");
		
		helpMenu.add(gettingstartedAction);
		helpMenu.addSeparator();
		helpMenu.add(offcialwebsiteAction);
		helpMenu.add(faqAction);
		helpMenu.add(reportanissueAction);
		helpMenu.add(pluginsAction);
		helpMenu.addSeparator();
		helpMenu.add(commandlineussageAction);
		helpMenu.addSeparator();
		helpMenu.add(checkfornewerversionAction);
		helpMenu.addSeparator();
		helpMenu.add(aboutAction);
		
		quitAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		//menu end
		
		//status bar begin
		
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JLabel readyLabel = new JLabel("Ready");
		readyLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		readyLabel.setPreferredSize(new Dimension(100,15));
		JLabel displayLabel = new JLabel("Disply:All");
		displayLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		displayLabel.setPreferredSize(new Dimension(100,15));
		JLabel threadLabel = new JLabel("Threads:0");
		threadLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		threadLabel.setPreferredSize(new Dimension(100,15));
		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);
		//status bar end
		
		//table begin
		
		String[] titles = new String[] {
				"IP","Ping", "TTL", "Host name", "Ports[0+]"
		};
		Object[][] stats = initTable();
		
		JTable jTable = new JTable(stats,titles);
		
		JScrollPane sp = new JScrollPane(jTable);
		add(sp, BorderLayout.CENTER);
		//table end
		
		//toolbar begin
		
		Font myFont = new Font("Serof", Font.BOLD, 16);
		JToolBar toolbar1 = new JToolBar();
		toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolbar2 = new JToolBar();
		toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel rangeStartLabel = new JLabel("IP Range: ");
		JTextField rangeStartTextField = new JTextField(10);
		
		JLabel rangeEndLabel = new JLabel("to");
		JTextField rangeEndTextField = new JTextField(10);
		JComboBox com1 = new JComboBox();
		com1.addItem("IP Range");
		com1.addItem("Random");
		com1.addItem("Text File");
		
		rangeStartLabel.setFont(myFont);
		rangeStartLabel.setPreferredSize(new Dimension(90,30));
		rangeEndLabel.setFont(myFont);
		rangeEndLabel.setPreferredSize(new Dimension(20,30));
		
		toolbar1.add(rangeStartLabel);
		toolbar1.add(rangeStartTextField);
		toolbar1.add(rangeEndLabel);
		toolbar1.add(rangeEndTextField);
		toolbar1.add(com1);
		
		JLabel hostNameLabel = new JLabel("Hostname: ");
		JTextField hostnameTextFiled = new JTextField(10);
		String myHostName;
		try {
			myHostName = InetAddress.getLocalHost().getHostName();
			hostnameTextFiled.setText(myHostName);
		} catch (UnknownHostException e1) {
			hostnameTextFiled.setText("");
		}

		JButton upButton = new JButton("IP¡è");
		JComboBox optionComboBox = new JComboBox();
		optionComboBox.addItem("/24");
		optionComboBox.addItem("/26");
		JButton startButton = new JButton("¢ºStart");
		
		hostNameLabel.setFont(myFont);
		hostNameLabel.setPreferredSize(new Dimension(90,30));
		upButton.setPreferredSize(new Dimension(50,30));
		optionComboBox.setPreferredSize(new Dimension(90,30));
		startButton.setPreferredSize(new Dimension(90,30));
		
		toolbar2.add(hostNameLabel);
		toolbar2.add(hostnameTextFiled);
		toolbar2.add(upButton);
		toolbar2.add(optionComboBox);
		toolbar2.add(startButton);
		
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(toolbar1, BorderLayout.NORTH);
		pane.add(toolbar2, BorderLayout.SOUTH);
		
		add(pane, BorderLayout.NORTH);
		//toolbar end
		String myIP = null;
		try {
			InetAddress ia = InetAddress.getLocalHost();
			
			myIP = ia.getHostAddress();
		} catch (Exception e) {
			
		}
		String fixedIP = myIP.substring(0, myIP.lastIndexOf(".") + 1);
		rangeStartTextField.setText(fixedIP + "1");
		rangeEndTextField.setText(fixedIP + "254");
		
		setSize(700,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == startButton) {
					startButton.setText("¡ástop");
					int bt = 0;
					bt++;

					for(int i = 1; i <= 255; i++) {
						final int I = i;
						
						String ip = "192.168.1." + I;
						readyLabel.setText(ip);
						String msg[] = {null, null, null, null, null};
						msg[0] = ip;
						Thread thread = new Thread() {
							@Override
							public void run() {
								InputStream is = null;
								BufferedReader br = null; 
								try {
									Runtime run = Runtime.getRuntime();
									Process p = run.exec("ping -a "+ip);

									is = p.getInputStream();
									br = new BufferedReader(new InputStreamReader(is));
									String line = null;
									while((line = br.readLine()) != null) {
										if(line.indexOf("[") >= 0) {
											Pattern pattern_hostname = Pattern.compile("(Ping)(\\s+)(.+)(\\s+)(\\[)");
											Matcher matcher_hostname = pattern_hostname.matcher(line);
											while(matcher_hostname.find())
												jTable.setValueAt(matcher_hostname.group(3), I-1, 3);
										}
										if(line.indexOf("ms") >= 0) {
											Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=)(\\d+)");
											Matcher matcher = pattern.matcher(line);
											while(matcher.find()) {
												msg[2] = matcher.group(1);
												msg[3] = matcher.group(4);
												jTable.setValueAt(msg[2], I-1, 1);
												jTable.setValueAt(msg[3], I-1, 2);
											}
										break;
										}
									}
									InetAddress address = InetAddress.getByName(ip);
									boolean reachable = address.isReachable(200);
									if(reachable) {
											jTable.setValueAt(msg[0], I-1, 0);
									}
									else {
										jTable.setValueAt(msg[0], I-1, 0);
										jTable.setValueAt("[n/a]", I-1, 1);
										jTable.setValueAt("[n/s]", I-1, 2);
										jTable.setValueAt("[n/s]", I-1, 3);
									}
									    jTable.setValueAt("[n/s]", I-1, 4);
								}catch(Exception e) {
									e.printStackTrace();
								}finally {
									try {
										if(br != null) br.close();
									}catch(Exception ex2) {}
									try {
										if(is != null) is.close();
									}catch (Exception ex2) {}
									jTable.repaint();
								}
							}
							
						};
						thread.start();
					}
					if(bt == 2) {
						startButton.setText("¢ºSTART");
						bt = 0;
					}
				
				}
			 }
			});
		
	}
	
	public Object[][] initTable() {
		// TODO Auto-generated method stub
		Object[][] result = new Object[255][5];
		return result;
	}	
	
	public static void main(String[] args) {
		OutlinePing op = new OutlinePing();	
	}
} 		