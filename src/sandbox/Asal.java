package sandbox;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;

public class Asal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel  lb1, lb2, lb11, lb12, lb13;
	private JTextField tf2, tf11, tf12;
	private JButton sp, ub, hp;
	private JScrollPane js;
	private  String s="";
	private int rows=0, count=0;
	Image img1=null, img2=null;
	private String data[][]=null;
	private String column[] = {"Index","Gereja Asal"};	
	
	Asal()
	{
      setTitle("Daftar gereja");
      setLayout(null);
      addWidgets();
      addGereja();
      addTable();
      setVisible(true);
	  setBounds(10, 10, 1300,730);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
			
	public void Simpan1()
	{
		int dialogButton = JOptionPane.showConfirmDialog (null, "Sudah benar?",null,JOptionPane.YES_NO_OPTION);
		if (dialogButton == JOptionPane.YES_OPTION) {
			PreparedStatement ps=null;
			try{
				Connection con = DB.getConnection();
			    ps = con.prepareStatement("insert into gereja(gereja)values(?)");
   			    ps.setString(1, tf2.getText());
		        ps.executeUpdate();
			    con.close();
			}catch(Exception e){System.out.println(e);}
			JOptionPane.showMessageDialog(null, "Sudah disimpan!!");
  			}
		}

		public void addWidgets()
		{
		   lb1 = new JLabel("Tambah gereja", SwingConstants.CENTER);
		   lb1.setBounds(120, 20, 200, 25);
		   lb1.setForeground(Color.blue);
		   lb1.setFont(new Font("Monospaced", Font.BOLD, 16));
		   lb1.setBackground(Color. blue);
		   lb1.setForeground(Color. white);
		   lb1.setOpaque(true);
		   lb1.setAlignmentX(CENTER_ALIGNMENT);
		   add(lb1);	
		   lb2 = new JLabel("Nama Gereja");
		   lb2.setBounds(40, 60, 100, 25);
		   lb2.setForeground(Color.blue);
		   add(lb2);
		  
	       tf2 = new JTextField();
	       tf2.setBounds(140, 60, 300, 28);
           add(tf2);
           sp = new JButton("Simpan");
           sp.addActionListener(new ActionListener () {
   	       public void actionPerformed(ActionEvent e) {
			    	Simpan1();
		   	    }
		    });
		   sp.setBounds(140, 90, 140, 28);
		   add(sp);

		   addWindowListener(new WindowAdapter() {
		     public void windowClosing(WindowEvent e) {
		       Jemaat.main(new String[]{});
			   dispose();
		     }
		   });
		}
		
		public void addGereja()
		{
		   lb11 = new JLabel("Edit gereja", SwingConstants.CENTER);
		   lb11.setBounds(500, 70, 200, 25);
		   lb11.setForeground(Color.blue);
		   lb11.setFont(new Font("Monospaced", Font.BOLD, 16));
		   lb11.setBackground(Color. blue);
		   lb11.setForeground(Color. white);
		   lb11.setOpaque(true);
		   lb11.setAlignmentX(CENTER_ALIGNMENT);
		   add(lb11);
	       lb12 = new JLabel("Index");
	       lb12.setBounds(450, 110, 100, 25);
		   lb12.setForeground(Color.blue);
		   add(lb12);
	       lb13 = new JLabel("Nama Gereja");
	       lb13.setBounds(450, 140, 100, 25);
		   lb13.setForeground(Color.blue);
		   add(lb13);
		   
	       tf11 = new JTextField();
	       tf11.setBounds(550, 110, 100, 28);
	       tf11.setEnabled(false);
           add(tf11);		  
	       tf12 = new JTextField();
	       tf12.setBounds(550, 140, 300, 28);
           add(tf12);
           ub = new JButton("Ubah");
           ub.addActionListener(new ActionListener () {
   	       public void actionPerformed(ActionEvent e) {
			    	Ubah1();
		   	    }
		    });
		   ub.setBounds(550, 170, 140, 28);
		   add(ub);
	       hp = new JButton("Hapus");
	       hp.addActionListener(new ActionListener () {
	   	       public void actionPerformed(ActionEvent e) {
				    	Hapus1();
			   	    }
			    });
		   hp.setBounds(700, 170, 140, 28);
		   add(hp);
		}	
		
	public void addTable()
	{
	  ResultSet rs=null;
      try{
		Connection con=DB.getConnection();
		Statement st = con.createStatement();
		rs= st.executeQuery("SELECT COUNT(*) FROM gereja");
        while (rs.next()){
            rows = rs.getInt(1);
        }
		rs = st.executeQuery("select * from gereja");
		data=new String[rows][2];
		while(rs.next()){
			data[count][0]=rs.getString(1);
			data[count][1]=rs.getString(2);
		  	count++;
		} 

		JTable jtb = new JTable(data,column){
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
	   	col.getColumn(0).setMaxWidth(100);
		    	col.getColumn(0).setCellRenderer(rightRenderer);
		    	col.getColumn(1).setMaxWidth(200);
		    	js = new JScrollPane(jtb);
		    	js.setBounds(300, 335, 300, 300);
		    	add(js, BorderLayout.CENTER);
	  	
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

	}		
	
    public class PTL extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
       		JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            s = target.getValueAt(row, 0).toString();	 
        	if(e.getClickCount()==2){
        			Ubah_Hapus(s);
        			
    		}
         }
    }

    public void Ubah_Hapus(String s)
    {
		  ResultSet rs=null;	
		  PreparedStatement ps=null;
		  try{
				Connection con=DB.getConnection();
				ps = con.prepareStatement("select * from gereja where g_id=?");
				ps.setString(1, s);
				rs=ps.executeQuery();
			    rs.next();
				tf11.setText(rs.getString(1));
				tf12.setText(rs.getString(2));
				con.close();
				  }catch (Exception e) {e.printStackTrace();}finally{
					  if(rs != null){
				             try{
				                  rs.close();
				             } catch(Exception e){
				                 e.printStackTrace();
				             }
				     }				  				  
				  }    	
    }
    
    public void Ubah1()
    {
			int tanya = JOptionPane.showConfirmDialog (null, "Mau ubah gereja?",null, JOptionPane.YES_NO_OPTION);
  			if(tanya ==JOptionPane.YES_OPTION)
  			{
  				PreparedStatement ps =null;
  				try{
  					String gid = tf11.getText();
  					Connection con=DB.getConnection();
  					ps = con.prepareStatement("update gereja set gereja=? where g_id=" + gid);
  					ps.setString(1, tf12.getText());  					
  				    ps.executeUpdate();
  				    con.close();
  				}catch(Exception e){System.out.println(e);} 					
  				JOptionPane.showMessageDialog(this, "Asal sudah diubah!!");
  			}    	
    }
    
    public void Hapus1()
    {
		int tanya = JOptionPane.showConfirmDialog (null, "Mau hapus Gereja?",null, JOptionPane.YES_NO_OPTION);
			if(tanya ==JOptionPane.YES_OPTION)
			{
				PreparedStatement ps =null;
				try{
					Connection con=DB.getConnection();
					ps = con.prepareStatement("delete from gereja  where g_id=?");
					ps.setString(1, tf11.getText());
				    ps.executeUpdate();
				    con.close();
				}catch(Exception e){System.out.println(e);} 					
				JOptionPane.showMessageDialog(this, "Gereja sudah dihapus!!");
			}    	    	
    }
}


	 