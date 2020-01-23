package sandbox;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import resources.ReLo;
import javax.swing.*;

public class Tambah extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel  lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8;
	private JTextField  tf1, tf2, tf3;
	private   String tgl="01020304050607080910111213141516171819202122232425262728293031";
	private JComboBox<String> tg, bl, th, as, mn;
	private JButton sp, rst;
	private Image img;
	Minat m;
	Asal a;
	
	Tambah()
	{
	      super("Tambah Jemaat");
	      setLayout(null);
	      lb1 = new JLabel("Nama");
	      lb1.setBounds(70, 100, 100, 25);
		  lb1.setForeground(Color.blue);
		  add(lb1);
	      lb2 = new JLabel("Alamat");
	      lb2.setBounds(70, 130, 100, 25);
		  lb2.setForeground(Color.blue);
		  add(lb2);
	      lb3 = new JLabel("Tanggal Lahir");
	      lb3.setBounds(70, 160, 100, 25);
		  lb3.setForeground(Color.blue);
		  add(lb3);
	      lb4 = new JLabel("Phone");
	      lb4.setBounds(70, 190, 100, 25);
		  lb4.setForeground(Color.blue);
		  add(lb4);
	      lb5 = new JLabel("Asal Gereja");
	      lb5.setBounds(70, 220, 100, 25);
		  lb5.setForeground(Color.blue);
		  add(lb5);
	      lb6 = new JLabel("Minat");
	      lb6.setBounds(70, 250, 100, 25);
		  lb6.setForeground(Color.blue);
		  add(lb6);
		  
	      tf1 = new JTextField();
	      tf1.setBounds(180, 100, 350, 28);
		  add(tf1);
	      tf2 = new JTextField();
	      tf2.setBounds(180, 130, 400, 28);
          add(tf2);
	      tg = new JComboBox<String>();
		  for (int i=0; i < 62; i+=2)
		      {
		    	  tg.addItem(tgl.substring(i , i+2));
		      }
	      tg.setBounds(180, 160, 50, 28);
		  add(tg);
		  lb7 = new JLabel(" - ");
		  lb7.setBounds(230, 160, 15, 28);
		  add(lb7);
	      bl = new JComboBox<String>();
		  for (int i=0; i < 24; i+=2)
		     {
		    	  bl.addItem(tgl.substring(i, i+2));
		     }
	      bl.setBounds(245, 160, 50, 28);
		  add(bl);
		  lb8 = new JLabel(" - ");
		  lb8.setBounds(295, 160, 15, 28);
		  add(lb8);
	      th = new JComboBox<String>();
		  for (int i=1937; i < 2019; i++)
		     {
		    	  th.addItem(Integer.toString(i));
		     }
	      th.setBounds(310, 160, 80, 28);
		  add(th);
	      tf3 = new JTextField();
	      tf3.setBounds(180, 190, 200, 28);
          add(tf3);
	      as = new JComboBox<String>();
	      as.setPrototypeDisplayValue("Gereja");
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
		  as.setBounds(180, 220, 140, 28);
		  add(as);
		    img = ReLo.loadImage("tambah.png");
		    JButton t1 = new JButton(new ImageIcon(img));
		    t1.setBounds(320, 220, 28, 28);
	        t1.setToolTipText("Tambah gereja");
		    t1.addActionListener (new ActionListener () {
		    	    public void actionPerformed(ActionEvent e) {
		    	    	a= new Asal();
		    	    	a.setVisible(true);
		    	    	dispose();
		    	    }
		    });
		  add(t1);  
		  mn.setBounds(180, 250, 140, 28);
		  add(mn);
		    JButton t2 = new JButton(new ImageIcon(img));
		    t2.setBounds(320, 250, 28, 28);
	        t2.setToolTipText("Tambah minat");
		    t2.addActionListener (new ActionListener () {
		    	    public void actionPerformed(ActionEvent e) {
		    	    	m= new Minat();
		    	    	m.setVisible(true);
		    	    	dispose();
		    	    }
		    });
		   add(t2); 
          sp = new JButton("Simpan");
          sp.addActionListener(new ActionListener () {
	    	    public void actionPerformed(ActionEvent e) {
	    	    	Simpan1();
	    	    }
	    	});
	      sp.setBounds(180, 280, 140, 28);
		  add(sp);
          rst = new JButton("Bersihkan");
          rst.addActionListener(new ActionListener () {
	    	    public void actionPerformed(ActionEvent e) {
	    	    	Bersih();
	    	    }
	    	});
	      rst.setBounds(320, 280, 140, 28);
		  add(rst);
	      setBounds(2, 10, 1300,700);
	      addWindowListener(new WindowAdapter() {
	    	  public void windowClosing(WindowEvent e) {
	                Jemaat.main(new String[]{});
	                dispose();
	            }
	      });
	      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	      setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void Simpan1()
	{
		int dialogButton = JOptionPane.showConfirmDialog (null, "Sudah benar?",null,JOptionPane.YES_NO_OPTION);
		if (dialogButton == JOptionPane.YES_OPTION) {
			if(tf1.getText().equals("")||tf2.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Ada yang belum diisi!!");
			}else{
				Connection con = DB.getConnection();
				try{
				    PreparedStatement ps = con.prepareStatement("insert into jemaat(nama,alamat,lahir,phone,asal,minat)values(?,?,?,?,?,?)");
				    ps.setString(1, tf1.getText());
				    ps.setString(2, tf2.getText());
				    String tf4 = (String)tg.getSelectedItem() + "-" + (String)bl.getSelectedItem() + "-" + (String)th.getSelectedItem();
				    ps.setString(3, tf4);
				    ps.setString(4, tf3.getText());
				    ps.setString(5, Integer.toString(as.getSelectedIndex() + 1));
				    ps.setString(6, Integer.toString(mn.getSelectedIndex() + 1));
				    ps.executeUpdate();
				    con.close();
				}catch(Exception e){System.out.println(e);}
				JOptionPane.showMessageDialog(null, "Sudah disimpan!!");
			}
		}
	}
	
	public void Bersih()
	{
		tf1.setText("");
		tf2.setText("");
		tg.setSelectedIndex(0);
		bl.setSelectedIndex(0);
		th.setSelectedIndex(0);
		as.setSelectedIndex(0);
		mn.setSelectedIndex(0);
	}

}
