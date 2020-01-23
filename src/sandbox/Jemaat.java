package sandbox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.EventQueue;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import resources.ReLo;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JFrame;

public class Jemaat extends JFrame
{
    private static final long serialVersionUID = 1L;
    private String r="",s="";
    private String[][] data=null;
    private String[] column = {"Index","Nama","Alamat", "Tanggal Lahir","Phone","Asal Gereja","Minat"};
    private int rows=0;
    private JTable jtb;
    private JPopupMenu pM;
    private JMenuItem ubah;
    private JMenuItem hapus;
    private JTextField jtf;
    private JScrollPane js;
    private JToolBar tb;
    Ubah u;
    Hapus h;
    Tambah t;
    Lansia d;
    Image img1=null, img2=null, img3=null;
    
    Jemaat() {
        setTitle("Daftar Jemaat");
        setBounds(2, 10, 1300, 740);
        setDefaultCloseOperation(3);
        setLayout(null);
        pM = new JPopupMenu();
        hapus = new JMenuItem("Hapus data jemaat");
        ubah = new JMenuItem("Ubah data jemaat");
        pM.add(this.hapus);
        pM.add(this.ubah);
        addWidgets();
        countRow();
        String comm = "select * from jemaat";
        addTable(comm);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public void addWidgets() {
        img1 = ReLo.loadImage("tambah.png");
        JButton tm = new JButton(new ImageIcon(img1));
        tm.setBounds(40, 10, 30, 30);
        tm.setToolTipText("Tambah data jemaat");
        tm.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	t= new Tambah();
    	    	t.setVisible(true);
    	    	dispose();
    	    }
        });
        (jtf = new JTextField()).setBounds(70, 10, 150, 30);
        jtf.setToolTipText("Tik nama disini!");
        img2 = ReLo.loadImage("cari.png");
        JButton Cr = new JButton(new ImageIcon(img2));
        Cr.setBounds(320, 10, 30, 30);
        Cr.setToolTipText("Cari dengan nama");
	    Cr.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	s = jtf.getText();
    	    	cari(s);
    	    }
        });
        img3 = ReLo.loadImage("lansia.png");
        JButton Df = new JButton(new ImageIcon(img3));
        Df.setBounds(350, 10, 30, 30);
        Df.setToolTipText("Daftar Lansia");
	    Df.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	d = new Lansia();
    	    	dispose();
    	    }
        });
        (tb = new JToolBar()).add(tm);
        tb.add(this.jtf);
        tb.add(Cr);
        tb.add(Df);
        tb.setBounds(5, 0, 500, 35);
        add(this.tb);
    }
    
    public void cari(String s)
    {
      String comm=null;
      int l=s.length();
      if(l<3)
      {
         comm = "select * from jemaat where nama like '" + s + "%'";
      }else{
    	  comm = "select * from jemaat where nama like '%" + s + "%'"; 
      }
      addTable(comm);
    }
    
    public class PTL extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
        	int row = jtb.rowAtPoint(e.getPoint());
        	r = jtb.getValueAt(row, 0).toString();
        	if (e.getButton()==MouseEvent.BUTTON3) {
                pM.show(jtb, e.getX(), e.getY());
        	ubah.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	if(u==null){
                    		u = new Ubah(r);
                            dispose();
                    	}
                  }
                });  
    	        hapus.addActionListener(new ActionListener() {
    	             public void actionPerformed(ActionEvent e) {
    	            	if(h==null){
    	            	 	 h = new Hapus(r);
    	            		 dispose();
    	            	}
    	           }
    	        });  	
        	}
        }
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

   public void addTable(String command)
    {         
	 ResultSet rs =null;   
   	 try{
		Connection con=DB.getConnection();
		Statement st = con.createStatement();
	    rs=st.executeQuery(command);
    	data=new String[rows][7];
    	int count=0;
    		while(rs.next()){
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
   	 
   	jtb = new JTable(data,column){

			private static final long serialVersionUID = 1L;

			public boolean editCellAt(int row, int column, java.util.EventObject e) {
    	            return false;
    		 }    
    	};   
    	jtb.addMouseListener(new PTL());
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
   
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				try {
					  new Jemaat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
}
