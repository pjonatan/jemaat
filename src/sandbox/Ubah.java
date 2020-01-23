package sandbox;

import javax.swing.*;

import resources.ReLo;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Ubah extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb8, lb9;
	private JTextField tf1, tf2, tf3;
	private JButton ub;
	private   String tgl="01020304050607080910111213141516171819202122232425262728293031";
	private JComboBox<String> tg, bl, th, as, mn;
    private String pid=null;
	private Image img;
	Minat m;
	Asal a;
	
	Ubah(String r)
	{
		super("Ubah data jemaat");
		setLayout(null);
		pid=r;
	      lb1 = new JLabel("Nama");
	      lb1.setBounds(70, 130, 120, 25);
		  lb1.setForeground(Color.blue);
		  add(lb1);
	      lb2 = new JLabel("Alamat");
	      lb2.setBounds(70, 160, 120, 25);
		  lb2.setForeground(Color.blue);
		  add(lb2);
	      lb3 = new JLabel("Tanggal Lahir");
	      lb3.setBounds(70, 190, 120, 25);
		  lb3.setForeground(Color.blue);
		  add(lb3);
	      lb4 = new JLabel("Phone");
	      lb4.setBounds(70, 220, 120, 25);
		  lb4.setForeground(Color.blue);
		  add(lb4);
	      lb5 = new JLabel("Asal Gereja");
	      lb5.setBounds(70, 250, 120, 25);
		  lb5.setForeground(Color.blue);
		  add(lb5);
	      lb6 = new JLabel("Minat");
	      lb6.setBounds(70, 280, 120, 25);
		  lb6.setForeground(Color.blue);
		  add(lb6);

		  tf1 = new JTextField();
	      tf1.setBounds(200, 130, 300, 28);
		  add(tf1);
	      tf2 = new JTextField();
	      tf2.setBounds(200, 160, 400, 28);
          add(tf2);
	      tg = new JComboBox<String>();
		  for (int i=0; i < 62; i+=2)
		      {
		    	  tg.addItem(tgl.substring(i , i+2));
		      }
	      tg.setBounds(200, 190, 50, 28);
		  add(tg);
		  lb8 = new JLabel(" - ");
		  lb8.setBounds(250, 190, 15, 28);
		  add(lb8);
	      bl = new JComboBox<String>();
		  for (int i=0; i < 24; i+=2)
		     {
		    	  bl.addItem(tgl.substring(i, i+2));
		     }
	      bl.setBounds(265, 190, 50, 28);
		  add(bl);
		  lb9 = new JLabel(" - ");
		  lb9.setBounds(315, 190, 15, 28);
		  add(lb9);
	      th = new JComboBox<String>();
		  for (int i=1937; i < 2019; i++)
		     {
		    	  th.addItem(Integer.toString(i));
		     }
	      th.setBounds(330, 190, 80, 28);
		  add(th);
	      tf3 = new JTextField();
	      tf3.setBounds(200, 220, 160, 28);
          add(tf3);
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
		  as.setBounds(200, 250, 200, 28);
		  add(as);
		  img = ReLo.loadImage("tambah.png");
		  JButton t1 = new JButton(new ImageIcon(img));
		  t1.setBounds(400, 250, 28, 28);
	      t1.setToolTipText("Tambah gereja");
		  t1.addActionListener (new ActionListener () {
		    	    public void actionPerformed(ActionEvent e) {
		    	    	a= new Asal();
		    	    	a.setVisible(true);
		    	    	dispose();
		    	    }
		  });
		  add(t1);  
		  mn.setBounds(200, 280, 200, 28);
		  add(mn);
		  JButton t2 = new JButton(new ImageIcon(img));
		  t2.setBounds(400, 280, 28, 28);
	      t2.setToolTipText("Tambah minat");
		  t2.addActionListener (new ActionListener () {
		    	    public void actionPerformed(ActionEvent e) {
		    	    	m= new Minat();
		    	    	m.setVisible(true);
		    	    	dispose();
		    	    }
		   });
		   add(t2); 
          ub = new JButton("Ubah?");
          ub.addActionListener (new ActionListener () {
	    	    public void actionPerformed(ActionEvent e) {
	    	    	Ubah_jemaat();
	    	    }
	    	});
	      ub.setBounds(200, 310, 80, 28);
		  add(ub);
		  PreparedStatement ps  = null;
		  try{
				Connection con=DB.getConnection();
				ps = con.prepareStatement("select * from jemaat where j_id=?");
				ps.setString(1, pid);
			    rs=ps.executeQuery();
			    rs.next();
				tf1.setText(rs.getString(2));
				tf2.setText(rs.getString(3));
				tf3.setText(rs.getString(5));
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
	
  	public void Ubah_jemaat()
  	{
  			int tanya = JOptionPane.showConfirmDialog (null, "Mau ubah data?",null, JOptionPane.YES_NO_OPTION);
  			if(tanya ==JOptionPane.YES_OPTION)
  			{
  				PreparedStatement ps =null;
  				try{
  					Connection con=DB.getConnection();
  					ps = con.prepareStatement("update jemaat set nama=?,alamat=?,lahir=?,phone=?,asal=?,minat=? where j_id=" + pid);
  					ps.setString(1, tf1.getText());
  					ps.setString(2, tf2.getText());
  					String tf4 = (String)tg.getSelectedItem() + "-" + (String)bl.getSelectedItem() + "-" + (String)th.getSelectedItem();
  					ps.setString(3, tf4);
  					ps.setString(4, tf3.getText());
  					ps.setString(5, Integer.toString(as.getSelectedIndex() + 1));
  					ps.setString(6, Integer.toString(mn.getSelectedIndex() + 1));
  				    ps.executeUpdate();
  				    con.close();
  				}catch(Exception e){System.out.println("Error: " + e.toString());}
 		  	   					
  				JOptionPane.showMessageDialog(this, "Data sudah diubah!!");
  			}
  	}
}
