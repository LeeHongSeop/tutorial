import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class IPTableExam extends JFrame implements ActionListener{
	JButton b_ip = new JButton("°„IP");
	JButton b_start = new JButton("¢∫START");
	JLabel readyLabel = new JLabel("Ready");
	public int flag = 0;
	JTable table = new JTable(new String[255][5], new String [] {"IP","PING","Hostname","TTL","Ports[4+]"});
	private IPTableExam() {
		super("IP Table Example");
		
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu Scan = new JMenu("Scan");
		JMenu Go_to = new JMenu("Go_to");
		JMenu Commands = new JMenu("Commands");
		JMenu Favorites = new JMenu("Favorite");
		JMenu Tools = new JMenu("Tools");
		JMenu Help = new JMenu("Help");
		
		menubar.add(Scan);
		menubar.add(Go_to);
		menubar.add(Commands);
		menubar.add(Favorites);
		menubar.add(Tools);
		menubar.add(Help);
		
		JMenuItem loadFromFileAction = new JMenuItem("Load from File");
		JMenuItem exportAllAction = new JMenuItem("Export All");
		JMenuItem exportSelctionAction = new JMenuItem("Export Selection");
		JMenuItem quitAction = new JMenuItem("Quit");
		

		
		Scan.add(loadFromFileAction);
		Scan.add(exportAllAction);
		Scan.add(exportSelctionAction);
		Scan.addSeparator();
		Scan.add(quitAction);

		
		JMenuItem nextAliveHostAction = new JMenuItem("Next alive host");
		JMenuItem nextOpenHostAction = new JMenuItem("Next open host");
		JMenuItem nextDeadHostAction = new JMenuItem("Next dead host");
		JMenuItem previousAliveHostAction = new JMenuItem("Previous alive host");
		JMenuItem previousOpenHostAction = new JMenuItem("Previous open host");
		JMenuItem previousDeadHostAction = new JMenuItem("Previous dead host");
		JMenuItem findAction = new JMenuItem("Find");

		
		Go_to.add(nextAliveHostAction);
		Go_to.add(nextOpenHostAction);
		Go_to.add(nextDeadHostAction);
		Go_to.addSeparator();
		Go_to.add(previousAliveHostAction);
		Go_to.add(previousOpenHostAction);
		Go_to.add(previousDeadHostAction);
		Go_to.addSeparator();
		Go_to.add(findAction);

		
		JMenuItem showDetailsAction = new JMenuItem("Show details");
		JMenuItem rescanIPction = new JMenuItem("Rescan IP");
		JMenuItem deleteIPction = new JMenuItem("Delete IP");
		JMenuItem copyIPction = new JMenuItem("Copy IP");
		JMenuItem copyDetailsIPction = new JMenuItem("Copy details");
		JMenuItem openAction = new JMenuItem("Open");

		
		Commands.add(showDetailsAction);
		Commands.addSeparator();
		Commands.add(rescanIPction);
		Commands.add(deleteIPction);
		Commands.addSeparator();
		Commands.add(copyIPction);
		Commands.add(copyDetailsIPction);
		Commands.addSeparator();
		Commands.add(openAction);

		
		JMenuItem addcurrentAction = new JMenuItem("Add current");
		JMenuItem manageFavoritesAction = new JMenuItem("Manage Favorites");

		
		Favorites.add(addcurrentAction);
		Favorites.add(manageFavoritesAction);

		
		JMenuItem preferencesAction = new JMenuItem("Preferences");
		JMenuItem fetchersAction = new JMenuItem("Fetchers");
		JMenuItem selectionAction = new JMenuItem("Selection");
		JMenuItem scanstatisticsAction = new JMenuItem("Scan statistics");

		
		Tools.add(preferencesAction);
		Tools.add(fetchersAction);
		Tools.addSeparator();
		Tools.add(selectionAction);
		Tools.add(scanstatisticsAction);

		
		JMenuItem gettingStartedAction = new JMenuItem("Getting Started");
		JMenuItem officialWebsiteAction = new JMenuItem("Official Website");
		JMenuItem faqAction = new JMenuItem("FAQ");
		JMenuItem reportAnIssueAction = new JMenuItem("Report an issue");
		JMenuItem pluginsAction = new JMenuItem("Plugins");
		JMenuItem commendLineUsageAction = new JMenuItem("Commend-line usage");
		JMenuItem checkfornewerversionAction = new JMenuItem("Check for newer version");
		JMenuItem aboutAction = new JMenuItem("About");

		
		Help.add(gettingStartedAction);
		Help.addSeparator();
		Help.add(officialWebsiteAction);
		Help.add(faqAction);
		Help.add(reportAnIssueAction);
		Help.add(pluginsAction);
		Help.addSeparator();
		Help.add(commendLineUsageAction);
		Help.add(checkfornewerversionAction);
		Help.addSeparator();
		Help.add(aboutAction);
		
		JToolBar toolbar1 = new JToolBar();
		toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolbar2 = new JToolBar();
		toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JPanel panel1 = new JPanel(new GridLayout(2,1));
		
		
		readyLabel.setPreferredSize(new Dimension(150,16));
		readyLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JLabel displayLabel = new JLabel("Dispay:All");
		displayLabel.setPreferredSize(new Dimension(150,16));
		displayLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JLabel threadLabel = new JLabel("Thread:0");
		threadLabel.setPreferredSize(new Dimension(150,16));
		threadLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JLabel IP_Range = new JLabel("IP Range : ");
		JLabel to = new JLabel("to : ");
		JLabel Hostname = new JLabel("Hostname : ");
		
		
		JComboBox select_cb = new JComboBox();
		JComboBox netmask_cb = new JComboBox();
		
		JTextField tf1 = new JTextField(10);
		JTextField tf2 = new JTextField(10);
		JTextField tf3 = new JTextField(10);
		
		JTextArea ta1 = new JTextArea(0, 10);
			
		b_start.addActionListener(this);
		
		select_cb.addItem("IP Range");
		select_cb.addItem("Random");
		select_cb.addItem("Text File");
		
		netmask_cb.addItem("/26");
		netmask_cb.addItem("/24");
		netmask_cb.addItem("/16");
		netmask_cb.addItem("255...192");
		netmask_cb.addItem("255...128");
		netmask_cb.addItem("255...0");
		netmask_cb.addItem("255..0.0");
		netmask_cb.addItem("255.0.0.0");
		
		
		toolbar1.add(IP_Range);
		toolbar1.add(tf1);
		toolbar1.add(to);
		toolbar1.add(tf2);
		toolbar1.add(select_cb);
		
		toolbar2.add(Hostname);
		toolbar2.add(tf3);
		toolbar2.add(b_ip);
		toolbar2.add(netmask_cb);
		toolbar2.add(b_start);
		
		
		String myIp = null;
		String myHostname = null;
		
		try {
			InetAddress ia = InetAddress.getLocalHost();
			
			myIp = ia.getHostAddress();
			myHostname = ia.getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fixedIp = myIp.substring(0, myIp.lastIndexOf(".") + 1);
		tf1.setText(fixedIp + "1");
		tf2.setText(fixedIp + "255");
		tf3.setText(myHostname);
		
		panel1.add(toolbar1);
		panel1.add(toolbar2);
		
		
		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);
		add(panel1,BorderLayout.NORTH);
		
		add(statusPanel, BorderLayout.SOUTH);
		add(new JScrollPane(table));
		
		
		setSize(1000,1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	public static void main(String[] args) {
		new IPTableExam();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == b_start) {
			b_start.setText("°·stop");
			flag++;

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
										table.setValueAt(matcher_hostname.group(3), I-1, 3);
								}
								if(line.indexOf("ms") >= 0) {
									Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=)(\\d+)");
									Matcher matcher = pattern.matcher(line);
									while(matcher.find()) {
										msg[2] = matcher.group(1);
										msg[3] = matcher.group(4);
										table.setValueAt(msg[2], I-1, 1);
										table.setValueAt(msg[3], I-1, 3);
									}
								break;
								}
							}
							InetAddress address = InetAddress.getByName(ip);
							boolean reachable = address.isReachable(200);
							if(reachable) {
									table.setValueAt("O "+msg[0], I-1, 0);
							}
							else {
								table.setValueAt("X "+msg[0], I-1, 0);
								table.setValueAt("[N/A]", I-1, 1);
								table.setValueAt("[N/S]", I-1, 2);
								table.setValueAt("[N/S]", I-1, 3);
							}
							table.setValueAt("[N/S]", I-1, 4);
						}catch(Exception e) {
							e.printStackTrace();
						}finally {
							try {
								if(br != null) br.close();
							}catch(Exception ex2) {}
							try {
								if(is != null) is.close();
							}catch (Exception ex2) {}
							table.repaint();
						}
					}
					
				};
				thread.start();
			}
			if(flag == 2) {
				b_start.setText("¢∫START");
				flag = 0;
			}
		
		}
	}
}
