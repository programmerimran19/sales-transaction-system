/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.transaction.management;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Programmer Imran
 */
public class SalesTransactionSystem extends javax.swing.JFrame 
{

    SalesTransactionSystem() throws SQLException {
        initComponents();
        Connect();
        txtbillno.setText(String.valueOf(getBillNumber()));
        //SimpleDateFormat format = SimpleDateFormat("DD-MM-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        Date day = new Date(); 
        txtdate.setText(format.format(day));
        txtcustomername.requestFocus();
        
    }
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    
    
    
    public void Connect() throws SQLException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/billing system","root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesTransactionSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int getBillNumber()
    {
        int billno = 1;
        try{
         
        Statement smt = con.createStatement();
            try (ResultSet rs = smt.executeQuery("select max(bill_no) from sales")) {
                rs.next();
                billno = rs.getInt(1) + 1;
            }
        
    }catch(SQLException ex){
        Logger.getLogger(SalesTransactionSystem.class.getName()).log(Level.SEVERE,null, ex);
    }
        return billno;
}
    
    
    public int getStock(String qty)
    {
        int stock_level = 0;
        try {
            Statement smt1 = con.createStatement();
            try(ResultSet rs = smt1.executeQuery("select stock from products where id = '"+ qty + "'")){ 
                rs.next();
                stock_level = rs.getInt(1);
                //stock_level = rs.getInt(1) + 1;
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(SalesTransactionSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stock_level;
    }
    
    
    
    public void Print()
    {
        
        txtprint.setText(txtprint.getText() + "\n");
        txtprint.setText(txtprint.getText() + "\n");
        
        String billno = txtbillno.getText();
        String billdate = txtdate.getText();
        String customer = txtcustomername.getText();
        String qty = txtquantity.getText();
        String price = txtprice.getText();
        String totalprice = txttotalamount.getText();
        String pcode = txtitemcode.getText();
        
        txtprint.setText(txtprint.getText()+ "Bill No : - "  + billno + "\n");
        txtprint.setText(txtprint.getText()+ "Bill Date : - "  + billdate + "\n");
        txtprint.setText(txtprint.getText()+ "Customer Name : - "  + customer + "\n");
        txtprint.setText(txtprint.getText()+ "Product Name : - "  + pcode + "\n");
        txtprint.setText(txtprint.getText()+ "Pruduct Price : - "  + price + "\n");
        txtprint.setText(txtprint.getText()+ "Quantity : - "  + qty + "\n");
        txtprint.setText(txtprint.getText()+ "Total Amount : - "  + totalprice + "\n");
        txtprint.setText(txtprint.getText()+ "\n");
        txtprint.setText(txtprint.getText() + "******************************\n");
        txtprint.setText(txtprint.getText() + "Thank you Come Again \n");
        
    }
    
    
    
    
    public void Save()
    {
        
        try {
            con.setAutoCommit(false);
            String billno = txtbillno.getText();
            String billdate = txtdate.getText();
            String customer = txtitemcode.getText();
            String pcode = txtcustomername.getText();
            String price = txtprice.getText();
            String totalprice = txttotalamount.getText();
            String qty = txtquantity.getText();
            
            
            
            int i = 0;
            pst = con.prepareStatement("insert into sales(bill_no,sales_date,customer_name,product_name,product_price,quantity,total_price)values(?,?,?,?,?,?,?)");
            pst.setString(1, billno);
            pst.setString(2, billdate);
            pst.setString(3, pcode);
            pst.setString(4, customer);
            pst.setString(5, price);
            pst.setString(6, qty);
            pst.setString(7, totalprice);
            
            
            i = pst.executeUpdate();
            /*i = pst.executeUpdate(billno);
            i = pst.executeUpdate(billdate);
            i = pst.executeUpdate(pcode);
            i = pst.executeUpdate(qty);
            i = pst.executeUpdate(customer);*/
            
        
        
        pst1 = con.prepareStatement("update products set stock=? where id =?");
        int curstock = getStock(qty);
        
        curstock = curstock - Integer.parseInt(txtquantity.getText());
        pst1.setInt(1, curstock);
        pst1.setString(2, qty);
        i = pst1.executeUpdate();
        curstock = getStock(qty);
            /*i = pst.executeUpdate(billno);
            i = pst.executeUpdate(billdate);
            i = pst.executeUpdate(pcode);
            i = pst.executeUpdate(qty);
            i = pst.executeUpdate(customer);*/
        
        
        
        if(curstock < 0)
        {
            con.rollback();
            JOptionPane.showMessageDialog(this, "Sorry Transaction Failed");
            //JOptionPane.showMessageDialog(this, "Sorry Transaction Failed");
        }
        else
        {
            con.commit();
            JOptionPane.showMessageDialog(this, "Transaction Successfull!!!!!!");
        }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SalesTransactionSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtbillno = new javax.swing.JLabel();
        txtquantity = new javax.swing.JTextField();
        txtitemcode = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnprint = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        txtcustomername = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtprint = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        txtdate = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnclear = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtprice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txttotalamount = new javax.swing.JTextField();
        btncalculate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 204));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel4.setText("Product Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 16)); // NOI18N
        jLabel2.setText("Billing Number");

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel5.setText("Quantity");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel6.setText("Customer Name");

        txtbillno.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N

        btnsave.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnsave.setForeground(new java.awt.Color(0, 102, 102));
        btnsave.setText("SAVE");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnprint.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnprint.setForeground(new java.awt.Color(0, 102, 102));
        btnprint.setText("PRINT");
        btnprint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnprintMouseClicked(evt);
            }
        });
        btnprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintActionPerformed(evt);
            }
        });

        btnexit.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnexit.setForeground(new java.awt.Color(0, 102, 102));
        btnexit.setText("EXIT");
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(txtprint);

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 16)); // NOI18N
        jLabel3.setText("Date");

        txtdate.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 204));
        jLabel1.setText("Sales Transaction System");

        btnclear.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnclear.setForeground(new java.awt.Color(0, 102, 102));
        btnclear.setText("CLEAR");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel7.setText("Product Price");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel8.setText("Total Amount");

        btncalculate.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btncalculate.setForeground(new java.awt.Color(0, 102, 102));
        btncalculate.setText("CALCULATE");
        btncalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btncalculate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnprint)
                                .addGap(10, 10, 10)
                                .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 86, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtbillno, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(44, 44, 44)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtcustomername, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                            .addComponent(txtquantity, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                            .addComponent(txtitemcode, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                            .addComponent(txtprice)
                                            .addComponent(txttotalamount))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtbillno)
                    .addComponent(jLabel3)
                    .addComponent(txtdate))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtcustomername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtitemcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtquantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttotalamount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprint, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        
        Save();
           
        
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        
        Print();
        /*txtprint.setText(txtprint.getText() + "\n");
        txtprint.setText(txtprint.getText() + "\n");
        
        String billno = txtbillno.getText();
        String billdate = txtdate.getText();
        String pcode = txtitemcode.getText();
        String qty = txtquantity.getText();
        String customer = txtcustomarname.getText();
        
        txtprint.setText(txtprint.getText()+ "Bill No : - "  + billno + "\n");
        txtprint.setText(txtprint.getText()+ "Bill Date : - "  + billdate + "\n");
        txtprint.setText(txtprint.getText()+ "Item Code : - "  + pcode + "\n");
        txtprint.setText(txtprint.getText()+ "Quantity : - "  + qty + "\n");
        txtprint.setText(txtprint.getText()+ "Customer Name : - "  + customer + "\n");
        txtprint.setText(txtprint.getText()+ "\n");
        txtprint.setText(txtprint.getText() + "******************************\n");
        txtprint.setText(txtprint.getText() + "Thank you Come Again \n");*/
        
    }//GEN-LAST:event_btnprintActionPerformed

    private void btnprintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnprintMouseClicked
        
        PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print Data");
            
            job.setPrintable(new Printable(){
            @Override
            public int print(Graphics pg,PageFormat pf, int pageNum){
                    pf.setOrientation(PageFormat.LANDSCAPE);
                 if(pageNum > 0){
                    return Printable.NO_SUCH_PAGE;
                }
                
                Graphics2D g2 = (Graphics2D)pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.47,0.47);
                
                txtprint.print(g2);
         
               
                return Printable.PAGE_EXISTS;
                         
                
            }
    });

        boolean ok = job.printDialog();
        if(ok){
            try{
            
                job.print();
            }catch (PrinterException ex){
                ex.printStackTrace();
            }
        }
                
    }//GEN-LAST:event_btnprintMouseClicked

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnexitActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        
        //getBillNumber();
            /*int billno = 1;
            try{
                Statement smt = con.createStatement();
                try (ResultSet rs = smt.executeQuery("select max(bill_no) from sales")) {
                    rs.next();
                    billno = rs.getInt(1) + 1;
                }
            }catch(SQLException ex){
                Logger.getLogger(SalesTransactionSystem.class.getName()).log(Level.SEVERE,null, ex);
            }*/
        
        
        txtbillno.setText(" ");
        //txtdate.setText(" ");
        txtcustomername.setText(" ");
        txtprice.setText(" ");
        txttotalamount.setText(" ");
        txtquantity.setText(" ");
        txtitemcode.setText(" ");
        txtprint.setText(" ");
        
        
    }//GEN-LAST:event_btnclearActionPerformed

    private void btncalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalculateActionPerformed

        int i = Integer.parseInt(txtprice.getText());
        int j = Integer.parseInt(txtquantity.getText());
        String total = String.valueOf(i*j);
        txttotalamount.setText(total);
                
    }//GEN-LAST:event_btncalculateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {       
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesTransactionSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new SalesTransactionSystem().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(SalesTransactionSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncalculate;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnprint;
    private javax.swing.JButton btnsave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txtbillno;
    private javax.swing.JTextField txtcustomername;
    private javax.swing.JLabel txtdate;
    private javax.swing.JTextField txtitemcode;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextPane txtprint;
    private javax.swing.JTextField txtquantity;
    private javax.swing.JTextField txttotalamount;
    // End of variables declaration//GEN-END:variables




}
