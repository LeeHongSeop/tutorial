import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.io.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;

class First implements TreeWillExpandListener,TreeSelectionListener
{
	private JFrame frame = new JFrame("파일탐색기");
	private JPanel pRight=new JPanel(new BorderLayout());
 	private JPanel pNorth=new JPanel();
 	private JPanel pSouth = new JPanel(new BorderLayout());
	private Container con = null;
	private JSplitPane pMain=new JSplitPane();
	private JScrollPane pLeft=null;
	private JLabel subTitle = new JLabel("파일 탐색기");
	private JComboBox language = new JComboBox();
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("..");
	private JTree tree;
	private JTextField pathText=new JTextField();
    private Dimension dim,dim1;
	private int xp,yp;
	
	First(){
		init();
		start();
		frame.setSize(800,600);
		dim=Toolkit.getDefaultToolkit().getScreenSize();
		dim1=frame.getSize();
		xp=(int)(dim.getWidth()/2-dim1.getWidth()/2);
		yp=(int)(dim.getHeight()/2-dim1.getHeight()/2);
		frame.setLocation(xp,yp);
		frame.setVisible(true);
	}

	 void init(){
	  pMain.setResizeWeight(1);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  con = frame.getContentPane();
	  con.setLayout(new BorderLayout());
	  
	  pathText.setPreferredSize(new Dimension(600,20));
	  language.addItem("한국어");
	  language.addItem("ENGLISH");
	  language.addItemListener(new AddListener(subTitle));
	  pSouth.add(subTitle,BorderLayout.WEST);
	  pSouth.add(language,BorderLayout.EAST);
	  //northText.add(northLabel);
	  //northText.add(pathText);
	  //pNorth.add(northText);
	  con.add(pNorth,"North");
	  con.add(pSouth,BorderLayout.SOUTH);
	  
	  File file=new File("");
	  File list[]=file.listRoots();
	  DefaultMutableTreeNode temp;
	
	  for(int i=0;i<list.length;++i)
	  {
	   temp=new DefaultMutableTreeNode(list[i].getPath());
	   temp.add(new DefaultMutableTreeNode("없음"));
	   root.add(temp);
	  }
	  tree=new JTree(root);
	  pLeft=new JScrollPane(tree);
	  
	  pMain.setDividerLocation(150);
	  pMain.setLeftComponent(pLeft);
	  pMain.setRightComponent(pRight);
	  con.add(pMain);
	 }

	 void start()
	 {
	  tree.addTreeWillExpandListener(this);
	  tree.addTreeSelectionListener(this);
	 }
	
	 public static void main(String args[]){
	  JFrame.setDefaultLookAndFeelDecorated(true);
	  new First();
	 }
	
	 String getPath(TreeExpansionEvent e)
	 {
	  StringBuffer path=new StringBuffer();
	  TreePath temp=e.getPath(); 
	  Object list[]=temp.getPath();
	  for(int i=0;i<list.length;++i)
	  {
	   if(i>0)
	   {
	    path.append(((DefaultMutableTreeNode)list[i]).getUserObject()+"\\");
	   }
	  }
	  return path.toString();
	 }
	 String getPath(TreeSelectionEvent e)
	 {
	  StringBuffer path=new StringBuffer();
	  TreePath temp=e.getPath(); 
	  Object list[]=temp.getPath();
	  for(int i=0;i<list.length;++i)
	  {
	   if(i>0)
	   {
	    path.append(((DefaultMutableTreeNode)list[i]).getUserObject()+"\\");
	   }
	  }
	  return path.toString();
	 }
	 
	 public void treeWillCollapse(TreeExpansionEvent event){}
	 
	 public void treeWillExpand(TreeExpansionEvent e)
	 {
	  if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")){}
	  else
	  {
	   try{
	    DefaultMutableTreeNode parent=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
	    File tempFile=new File(getPath(e));
	    File list[]=tempFile.listFiles();
	    DefaultMutableTreeNode tempChild;
	    for(File temp:list)
	    {
	     if(temp.isDirectory() && !temp.isHidden())
	     {
	      tempChild=new DefaultMutableTreeNode(temp.getName());
	      if(true)
	      {
	       File inFile=new File(getPath(e)+temp.getName()+"\\");
	       File inFileList[]=inFile.listFiles();
	       for(File inTemp:inFileList)
	       {
	        if(inTemp.isDirectory() && !inTemp.isHidden())
	        {
	         tempChild.add(new DefaultMutableTreeNode("없음"));
	         break;
	        }
	       }
	      }
	      parent.add(tempChild);
	     }
	    }
	    parent.remove(0);
	   }
	   catch(Exception ex)
	   {
	    JOptionPane.showMessageDialog(frame, "디스크 혹은 파일을 못찾겠다.");
	   }
	  }
	 }
	 public void valueChanged(TreeSelectionEvent e)
	 {
	  if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")){
	   pathText.setText("내컴퓨터");
	  }
	  else
	  {
	   try
	   {
	    pathText.setText(getPath(e));
	    pRight=new FView(getPath(e)).getTablePanel();
	    pMain.setRightComponent(pRight);
	   }
	   catch(Exception ex)
	   {
	    JOptionPane.showMessageDialog(frame, "디스크 혹은 파일을 찾을수 없습니다.");
	   }
	  }
	 }
	}
	
	
	
	 
	
	class FView
	{ 
	 private LTable lt=new LTable();
	 private JTable jt=new JTable(lt);
	 
	 private JPanel pMain=new JPanel(new BorderLayout());
	 private JScrollPane pCenter=new JScrollPane(jt);
	
	 private File file;
	 private File list[];
	 private long size=0,time=0;
	
	 FView(String str){
	  init();
	  start(str);
	 }
	
	 void init()
	 { pMain.add(pCenter,"Center");}
	
	 void start(String strPath)
	 {
	  file=new File(strPath);
	  list=file.listFiles();
	  lt.setValueArr(list.length);
	  for(int i=0;i<list.length;++i)
	  {
	   size=list[i].length();
	   time=list[i].lastModified();
	   for(int j=0;j<4;++j)
	   {
	    switch(j)
	    {
	     case 0:
	      lt.setValueAt(list[i].getName(),i,j);
	      break;
	     case 1:
	      if(list[i].isFile())
	       lt.setValueAt(Long.toString((long)Math.round(size/1024.0))+"Kb",i,j);
	      break;
	     case 2:
	      if(list[i].isFile())
	      {
	       lt.setValueAt(getLastName(list[i].getName()),i,j);
	      }
	      else
	       lt.setValueAt("파일폴더",i,j);
	      break;
	     case 3:
	      lt.setValueAt(getFormatString(time),i,j);
	      break;
	    }
	   }
	  }
	  jt.repaint();
	  pCenter.setVisible(false);
	  pCenter.setVisible(true);
	 }
	
	 String getLastName(String name)
	 {
	  int pos=name.lastIndexOf(".");
	  String result=name.substring(pos+1,name.length());
	  return result;
	 }
	 String getFormatString(long time)
	 {
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	  Date d=new Date(time);
	  String temp = sdf.format(d);
	  return temp;
	 }
	 JPanel getTablePanel()
	 {
	  return pMain;
	 }
	}
	
	class LTable extends AbstractTableModel
	{
	 public static String title[]={"이름", "크기", "종류","수정한 날짜"};
	 public String val[][]=new String[1][4];
	 
	 public void setValueArr(int i)
	 { val=new String[i][4]; }
	 public int getRowCount()
	 { return val.length; }
	 public int getColumnCount()
	 { return val[0].length; }
	 public String getColumnName(int column )
	 { return title[column];}
	 public boolean isCellEditable(int rowIndex, int columnIndex)
	 {
	  if(columnIndex==0)
	   return true;
	  else
	   return false;
	 }
	 public Object getValueAt(int row, int column)
	 { return val[row][column]; }
	 public void setValueAt(String aValue, int rowIndex, int columnIndex ){
	  val[rowIndex][columnIndex] = aValue;
	 }
	}
	class AddListener implements ItemListener {
		JLabel subTitle;
		String title[];
		
		AddListener(JLabel subTitle)
		{ this.subTitle = subTitle; }
		AddListener(String title[]) 
		{ this.title = title; }
		
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			String lan = (String)arg0.getItem();
			if(lan.equals("ENGLISH")) {
				subTitle.setText("FILE READER");
				LTable.title[0] = "NAME";
				LTable.title[1] = "SIZE";
				LTable.title[2] = "KIND";
				LTable.title[3] = "MODIFY DATE";
			}
			else if(lan.equals("한국어")) {
				subTitle.setText("파일 탐색기");
				LTable.title[0] = "이름";
				LTable.title[1] = "크기";
				LTable.title[2] = "종류";
				LTable.title[3] = "수정한 날짜";	
			} 
		}
}
