package sandbox;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import resources.ReLo;

public class Lansia extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private String data[][]=null;
	private String column[] = {"Index","Nama","Alamat", "Tgl Lahir","Phone","Asal Gereja","Minat"};	
	private int rows=0;
	private  Date date = new Date();
	private  SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy");
	int tahun = Integer.parseInt(ft1.format(date).toString());
	private JComboBox<String> gr, mn;
	private JTable jtb;
	private JScrollPane js;
	private JToolBar tb;
	DMinat dm;
	AGereja a;
	Ultah u;
	PLansia p;
	
    Lansia()    
    {
      setTitle("Daftar Lansia");	
      setBounds(2, 10, 1300, 740);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      addWidgets();
      countRow();
      String comm = "select * from jemaat";
      addLansia(comm);
      setVisible(true);
      setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public void addWidgets()
    {
    	Image img = ReLo.loadImage("brtd.png");
    	JButton Br = new JButton(new ImageIcon(img));
    	Br.setBounds(70, 10, 30, 30);
        Br.setToolTipText("Daftar yang Ulang Tahun");
	    Br.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	u= new Ultah();
    	    	u.setVisible(true);
    	    	dispose();
    	    }
	    });
        gr = new JComboBox<String>();
	    try{
	    	Connection con=DB.getConnection();
	    	Statement st = con.createStatement();
	    	ResultSet rs = st.executeQuery("select * from gereja");
	    	while(rs.next()){
	    		gr.addItem(rs.getString(2));
	    	}
	    	con.close();
	    } catch(Exception e){
            e.printStackTrace();
        }
	    gr.setBounds(100, 10, 100, 30);
	    gr.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	gereja();
    	    }
	    });
	    mn = new JComboBox<String>();
	    try{
	    	Connection con=DB.getConnection();
	    	Statement st = con.createStatement();
	    	ResultSet rs = st.executeQuery("select * from minat");
	    	while(rs.next()){
	    		mn.addItem(rs.getString(2));
	    	}
	    	con.close();
	    } catch(Exception e){
            e.printStackTrace();
        }
	    mn.setBounds(200, 10, 100, 30);
	    mn.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	minat();
    	    }
	    });
	    img = ReLo.loadImage("printer.png");
    	JButton Pr = new JButton(new ImageIcon(img));
    	Pr.setBounds(300, 10, 30, 30);
        Pr.setToolTipText("Print daftar Lansia");
	    Pr.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	p = new PLansia();
    	    	p.setVisible(true);
    	    	dispose();
    	    }
	    });
	    tb = new JToolBar();
	    tb.add(Br);
	    tb.add(gr);
	    tb.add(mn);
	    tb.add(Pr);
	    tb.setBounds(5, 0, 500, 35);
	    add(tb);
    }
    
    public void countRow()
    {
    	Statement st =null;
    	ResultSet res=null;
    	try{
    		Connection con=DB.getConnection();
    		st = con.createStatement();
	  		res = st.executeQuery("SELECT COUNT(*) FROM jemaat");
            while (res.next()){
                rows = res.getInt(1);
            }
            con.close();
    	}catch(Exception e){System.out.println(e);}finally{
			  if(res != null){
		             try{
		                  res.close();
		             } catch(Exception e){
		                 e.printStackTrace();
		             }
		     }  		
    	}
    } 	

   public void addLansia(String command)
    {         
	 ResultSet rs =null;
	 
   	 try{
		Connection con=DB.getConnection();
		Statement st = con.createStatement();
	    rs=st.executeQuery(command);
    	data=new String[rows][7];
    	int count=0;
    		while(rs.next()){
    			int ls = Integer.parseInt(rs.getString(4).substring(6, 10));
    			if((tahun - ls) >55 || (tahun - ls)==55){
    		  		for(int i=0;i<=4;i++){
    		  			data[count][i]=rs.getString(i + 1);
    		  		}
    		  		String gid = rs.getString(6);
   					PreparedStatement ps = con.prepareStatement("select * from gereja where g_id=" + gid);
   					ResultSet rsg = ps.executeQuery();
   					data[count][5]=rsg.getString(2);
   					String mid = rs.getString(7);
   					PreparedStatement ps1 = con.prepareStatement("select * from minat where m_id=" + mid);
   					ResultSet rsm = ps1.executeQuery();
    		  		data[count][6]=rsm.getString(2);
    				count++;
    			 }	
    		}
		con.close();
    	}catch(Exception e){System.out.println(e);}finally{
			  if(rs != null){
		             try{
		                  rs.close();
		             } catch(Exception e){
		                 e.printStackTrace();
		             }
		     }  		
    	}
   	 
   	jtb = new JTable(data,column);
   	JTableHeader header = jtb.getTableHeader();
        header.setForeground(Color.blue);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
    	TableColumnModel col = jtb.getColumnModel();
    	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
    	col.getColumn(0).setMaxWidth(50);
    	col.getColumn(0).setCellRenderer(rightRenderer);
    	col.getColumn(1).setMaxWidth(300);
    	col.getColumn(2).setMaxWidth(350);
    	col.getColumn(3).setMaxWidth(120);
    	col.getColumn(3).setCellRenderer(centerRenderer);
    	col.getColumn(4).setMaxWidth(120);
    	col.getColumn(4).setCellRenderer(centerRenderer);
    	col.getColumn(5).setMaxWidth(150);
      	col.getColumn(5).setCellRenderer(centerRenderer);
    	col.getColumn(6).setMaxWidth(200);
      	col.getColumn(6).setCellRenderer(centerRenderer);
    	js = new JScrollPane(jtb);
    	js.setBounds(2, 35, 1300, 600);
    	add(js, BorderLayout.CENTER);
    }
   
   public void gereja()
   {
	   int gereja = gr.getSelectedIndex() + 1;
	   String gere = Integer.toString(gereja);
	    a= new AGereja(gere);   
   }
   
   public void minat()
   {
	   int minat = mn.getSelectedIndex() + 1;
	   String mnt = Integer.toString(minat);
	   dm = new DMinat(mnt);
   }
}
