package sandbox;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Hapus extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9;
	private JTextField tf1, tf2, tf3, tf5;
	private   String tgl="01020304050607080910111213141516171819202122232425262728293031";
	private JComboBox<String> tg, bl, th, as, mn;
	private JButton hp;
	
	public Hapus(String r)
	{
		super("Hapus data jemaat");
		setLayout(null);
	      lb1 = new JLabel("Index");
	      lb1.setBounds(70, 130, 100, 25);
		  lb1.setForeground(Color.blue);
		  add(lb1);
	      lb2 = new JLabel("Nama");
	      lb2.setBounds(70, 160, 100, 25);
		  lb2.setForeground(Color.blue);
		  add(lb2);
	      lb3 = new JLabel("Alamat");
	      lb3.setBounds(70, 190, 100, 25);
		  lb3.setForeground(Color.blue);
		  add(lb3);
	      lb4 = new JLabel("Tanggal Lahir");
	      lb4.setBounds(70, 220, 100, 25);
		  lb4.setForeground(Color.blue);
		  add(lb4);
	      lb5 = new JLabel("Phone");
	      lb5.setBounds(70, 250, 100, 25);
		  lb5.setForeground(Color.blue);
		  add(lb5);
	      lb6 = new JLabel("Asal Gereja");
	      lb6.setBounds(70, 280, 100, 25);
		  lb6.setForeground(Color.blue);
		  add(lb6);
	      lb7 = new JLabel("Minat");
	      lb7.setBounds(70, 310, 100, 25);
		  lb7.setForeground(Color.blue);
		  add(lb7);
		  
	      tf1 = new JTextField();
	      tf1.setBounds(180, 130, 60, 28);
		  add(tf1);
	      tf2 = new JTextField();
	      tf2.setBounds(180, 160, 300, 28);
        add(tf2);
	      tf3 = new JTextField();
	      tf3.setBounds(180, 190, 400, 28);
        add(tf3);
	      tg = new JComboBox<String>();
		  for (int i=0; i < 62; i+=2)
		      {
		    	  tg.addItem(tgl.substring(i , i+2));
		      }
	      tg.setBounds(180, 220, 50, 28);
		  add(tg);
		  lb8 = new JLabel(" - ");
		  lb8.setBounds(230, 220, 15, 28);
		  add(lb8);
	      bl = new JComboBox<String>();
		  for (int i=0; i < 24; i+=2)
		     {
		    	  bl.addItem(tgl.substring(i, i+2));
		     }
	      bl.setBounds(245, 220, 50, 28);
		  add(bl);
		  lb9 = new JLabel(" - ");
		  lb9.setBounds(295, 220, 15, 28);
		  add(lb9);
	      th = new JComboBox<String>();
		  for (int i=1937; i < 2019; i++)
		     {
		    	  th.addItem(Integer.toString(i));
		     }
	      th.setBounds(310, 220, 80, 28);
		  add(th);
	      tf5 = new JTextField();
	      tf5.setBounds(180, 250, 160, 28);
		  add(tf5);
          as = new JComboBox<String>();
	      mn = new JComboBox<String>();
	      ResultSet rs = null;
		  try{
	           Connection con=DB.getConnection();
			   Statement st = con.createStatement();
			   rs = st.executeQuery("select * from gereja");
			   while(rs.next()){
				   as.addItem(rs.getString(2));
			   }
			   Statement st1 = con.createStatement();
			   rs = st1.executeQuery("select * from minat");
			   while(rs.next()){
				   mn.addItem(rs.getString(2));
			   }
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
		  as.setBounds(180, 280, 140, 28);
		  add(as);
		  mn.setBounds(180, 310, 140, 28);
		  add(mn);

          hp = new JButton("Hapus?");
          hp.addActionListener (new ActionListener () {
	    	    public void actionPerformed(ActionEvent e) {
	    	    	Tambah1(tf1.getText());
	    	    }
	    	});
	      hp.setBounds(180, 340, 140, 28);
		  add(hp);
		  PreparedStatement ps=null;
		  try{
				Connection con=DB.getConnection();
				ps = con.prepareStatement("select * from jemaat where j_id=?");
				ps.setString(1, r);
			    rs=ps.executeQuery();
			    rs.next();
				tf1.setText(rs.getString(1));
				tf2.setText(rs.getString(2));
				tf3.setText(rs.getString(3));
				tf5.setText(rs.getString(5));
				tg.setSelectedItem(rs.getString(4).substring(0, 2));
			    bl.setSelectedItem(rs.getString(4).substring(3, 5));
			    th.setSelectedItem(rs.getString(4).substring(6, 10));  			  
			    as.setSelectedIndex(Integer.parseInt(rs.getString(6)) - 1);
			    mn.setSelectedIndex(Integer.parseInt(rs.getString(7)) - 1);  
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
		  
	      setBounds(10, 10, 1300, 700);
	      addWindowListener(new WindowAdapter() {
	    	  public void windowClosing(WindowEvent e) {
	                Jemaat.main(new String[]{});
	                dispose();
	            }
	      });
	      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	      setVisible(true);
	      setExtendedState(JFrame.MAXIMIZED_BOTH);
	  }
	
  	public void Tambah1(String r)
  	{
  			int tanya = JOptionPane.showConfirmDialog (null, "Mau hapus data?",null, JOptionPane.YES_NO_OPTION);
  			if(tanya ==JOptionPane.YES_OPTION)
  			{
  				try{
  					Connection con=DB.getConnection();
  					PreparedStatement ps = con.prepareStatement("delete from jemaat where j_id=?");
  					ps.setString(1, r);
  				    ps.executeUpdate();
  				    con.close();
  				}catch(Exception e){System.out.println(e);}
  				JOptionPane.showMessageDialog(this, "Data sudah dihapus!!");
  			}
  		
  	}
}
