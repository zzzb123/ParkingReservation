
import java.text.SimpleDateFormat;
import java.util.Locale;


public class OrderForm extends javax.swing.JFrame {

    public OrderForm() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jDateChooser1);
        jDateChooser1.setBounds(330, 40, 120, 70);

        jButton1.setText("Select Slot");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(320, 540, 130, 90);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7.00AM", "7.30AM", "8.00AM", "8.30AM", "9.00AM", "9.30AM", "10.00AM", "10.30AM", "11.00AM", "11.30AM", "12.00PM", "12.30PM", "1.00PM", "1.30PM", "2.00PM", "2.30PM", "3.00PM", "3.30PM", "4.00PM", "4.30PM", "5.00PM", "5.30PM", "6.00PM", " " }));
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(150, 180, 90, 50);

        jTextField1.setFont(new java.awt.Font("Garamond", 0, 14)); // NOI18N
        jTextField1.setText("Start Time");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(80, 180, 70, 50);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30 minutes", "45 minutes", "1 hour", "1 hour 30 minutes", "1 hour 45 minutes", "2 hours" }));
        getContentPane().add(jComboBox2);
        jComboBox2.setBounds(590, 180, 140, 50);

        jTextField2.setText("Select Hours");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2);
        jTextField2.setBounds(510, 180, 80, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Pictures\\pp.jpg")); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 820, 777);

        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 41, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy",Locale.getDefault());
        String d = sdf.format(jDateChooser1.getDate());
        Confirmation c = new Confirmation();
        c.setVisible(true);
        c.pack();
        c.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
