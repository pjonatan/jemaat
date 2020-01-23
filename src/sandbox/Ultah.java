package sandbox;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.Calendar;
import java.awt.print.*;

public class Ultah extends JFrame implements Printable {

    
	private static final long serialVersionUID = 1L;
	private String data[][]=null;
	private String column[] = {"Nama","Alamat", "Tanggal Lahir","Phone"};	
	private int rows=0;
	private JTable jtb;
	private JScrollPane js;
	private String[] namaBulan = {"Januari", "Pebruari",
            "Maret", "April", "Mei", "Juni", "Juli",
            "Augustus", "September", "Ockober", "Nopember",
            "Desember"};
	private Calendar cal = Calendar.getInstance();
	private int bl = cal.get(Calendar.MONTH);
    private String bulan = namaBulan[bl];
	
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

    	if (page > 0) { /* We have only one page, and 'page' is zero-based */
    		return NO_SUCH_PAGE;
    	}else{

			Graphics2D g2d = (Graphics2D)g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());
			this.paint(g2d);
			return(PAGE_EXISTS);
    	}

    }
    
    Ultah()    
    {
      setTitle("Daftar jemaat yang berulang tahun di bulan: " + bulan);	
      setBounds(2, 10, 800, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      countRow();
      addUltah();
      setVisible(true);
	  print();
	  dispose();
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

   public void addUltah()
    {         
	 ResultSet rs =null;
	 
   	 try{
		Connection con=DB.getConnection();
		Statement st = con.createStatement();
	    rs=st.executeQuery("select * from jemaat");
    	data=new String[rows][4];
    	int count=0;
    	while(rs.next()){
    	   if(Integer.parseInt(rs.getString(4).substring(3, 5))==(bl + 1)){		
   		  			data[count][0]=rs.getString(2);
   		  		    data[count][1]=rs.getString(3);
   		  		    data[count][2]=rs.getString(4);
   		  		    data[count][3]=rs.getString(5);
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
   	jtb.setFont(new Font("SansSerif", Font.PLAIN, 9));
   	JTableHeader header = jtb.getTableHeader();
        header.setForeground(Color.blue);
        header.setFont(new Font("SansSerif", Font.BOLD, 10));
    	TableColumnModel col = jtb.getColumnModel();
    	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
    	col.getColumn(0).setMaxWidth(180);
    	col.getColumn(1).setMaxWidth(250);
    	col.getColumn(2).setMaxWidth(60);
    	col.getColumn(3).setMaxWidth(100);
    	js = new JScrollPane(jtb);
    	js.setBounds(2, 35, 700, 500);
    	add(js, BorderLayout.CENTER);
    }
   
   public void print() {
	   	PrinterJob printJob = PrinterJob.getPrinterJob();
	   	PageFormat pf = printJob.defaultPage();
	       Paper paper = new Paper();
	       double margin = 5; // half inch
	       paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight()
	           - margin * 2);
	       pf.setPaper(paper);
	       Book book = new Book();
	       book.append(this, pf);
	       printJob.setPageable(book);
	   	if (printJob.printDialog())
	   		try {
	   				printJob.print();
	   		} catch(PrinterException pe) {
	   			System.out.println("Error printing: " + pe);
	   		}
	}
}
