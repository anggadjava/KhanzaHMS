/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package inventory;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgBarang;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class DlgCariObat extends javax.swing.JDialog {
    private final DefaultTableModel tabModeobat;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psobat2,psobatsimpan,psobatsimpan2,psobatsimpan3,psobatsimpan4,pshapusobat;
    private ResultSet rsobat;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"Jumlah",
                    "Kode Barang",
                    "Nama Barang",
                    "Satuan",
                    "Letak Barang",
                    "Harga(Rp)",
                    "Stok",
                    "Jenis Obat"};
        tabModeobat=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbObat.setModel(tabModeobat);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 8; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(100);
            }      
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                tampilobat();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tampilobat();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tampilobat();
            }

        });
        
        try{
            psobatsimpan= koneksi.prepareStatement("insert into detail_pemberian_obat values(?,?,?,?,?,?,?,?,?,?)");
            psobatsimpan3= koneksi.prepareStatement("update gudangbarang set stok=stok-? where kode_brng=? and kd_bangsal='"+bangsal+"'");
            psobatsimpan2= koneksi.prepareStatement("insert into gudangbarang values(?,?,?)");            
            psobatsimpan4= koneksi.prepareStatement("update gudangbarang set stok=stok+? where kode_brng=? and kd_bangsal='"+bangsal+"'");
            pshapusobat=koneksi.prepareStatement("delete from detail_pemberian_obat where no_rawat=? and kd_penyakit=? and kode_brng=? and tgl_perawatan=? and jam=?");
            
            psobat2=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat, detail_pemberian_obat.biaya_obat,"+
                " databarang.letak_barang,(select stok from gudangbarang where gudangbarang.kd_bangsal='"+bangsal+"' and gudangbarang.kode_brng=databarang.kode_brng) as stok,detail_pemberian_obat.jml "+
                " from databarang inner join kodesatuan inner join jenis inner join detail_pemberian_obat "+
                " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns and "+
                " detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.kode_brng order by databarang.kode_brng ");
            
            psobat=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.h_retail,databarang.h_distributor,databarang.h_grosir,"+
                    " databarang.letak_barang,(select stok from gudangbarang where gudangbarang.kd_bangsal='"+bangsal+"' and gudangbarang.kode_brng=databarang.kode_brng) as stok from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.kode_brng like ? or "+
                    " databarang.nama_brng like ? or "+
                    " jenis.nama like ? order by databarang.nama_brng");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }    
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double x=0,y=0,stokbarang=0;
    private int jml=0,index=0,i=0,z=0;
    private double[] jumlah,harga,stok;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis;
    private String bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
    public DlgBarang barang=new DlgBarang(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppHapusObat = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        TNoRw = new widget.TextBox();
        TKdPny = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        label12 = new widget.Label();
        Jenisjual = new widget.ComboBox();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppHapusObat.setBackground(new java.awt.Color(255, 255, 255));
        ppHapusObat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppHapusObat.setForeground(new java.awt.Color(102, 51, 0));
        ppHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppHapusObat.setText("Hapus Obat Terpilih");
        ppHapusObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusObat.setIconTextGap(8);
        ppHapusObat.setName("ppHapusObat"); // NOI18N
        ppHapusObat.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusObatActionPerformed(evt);
            }
        });
        Popup.add(ppHapusObat);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setSelectionColor(new java.awt.Color(255, 255, 255));
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.setSelectionColor(new java.awt.Color(255, 255, 255));

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setSelectionColor(new java.awt.Color(255, 255, 255));

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        Jam.setSelectionColor(new java.awt.Color(255, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat/Alkes/BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(376, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('2');
        BtnSeek5.setToolTipText("Alt+2");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label12);

        Jenisjual.setBackground(new java.awt.Color(245, 250, 240));
        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Ranap JKM", "Ranap Umum" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(100, 23));
        Jenisjual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisjualItemStateChanged(evt);
            }
        });
        Jenisjual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisjualKeyPressed(evt);
            }
        });
        panelisi3.add(Jenisjual);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }else{Valid.pindah(evt, BtnKeluar, BtnCari);}
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampilobat();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampilobat();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getDataobat();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    getDataobat();
                    i=tbObat.getSelectedColumn();
                    if(i==1){
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataobat();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbObat.getSelectedRow();
                if(i!= -1){
                    tbObat.setValueAt("",i,0);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }           
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        /*if(pilihtable.equals("rawat_jl_dr")){
                    DlgBilingRalan dlgbil=new DlgBilingRalan(null,false);
                    dlgbil.setNoRm(TNoRw.getText()); 
                    dlgbil.isRawat();
                    //dlgbil.setModal(true);
                    dlgbil.isCek();
                    dlgbil.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgbil.setLocationRelativeTo(internalFrame1);
                    dlgbil.setVisible(true);
        }*/
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        //barang.setModal(true);
        barang.emptTeks();
        barang.tampil(" order by databarang.kode_brng ");
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()+40,internalFrame1.getHeight()+40);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TKdPny.getText().trim().equals("")){
            Valid.textKosong(TCari,"Data");
        }else if(bangsal.equals("")){
            Valid.textKosong(TCari,"Lokasi");
        }else{
            try {  
                koneksi.setAutoCommit(false);                
                for(i=0;i<tbObat.getRowCount();i++){ 
                    if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){
                            psobatsimpan.setString(1,Tanggal.getText());
                            psobatsimpan.setString(2,Jam.getText());
                            psobatsimpan.setString(3,TNoRw.getText());
                            psobatsimpan.setString(4,TKdPny.getText());
                            psobatsimpan.setString(5,"-");
                            psobatsimpan.setString(6,tbObat.getValueAt(i,1).toString());
                            psobatsimpan.setString(7,tbObat.getValueAt(i,5).toString());
                            psobatsimpan.setDouble(8,Double.parseDouble(tbObat.getValueAt(i,0).toString()));
                            psobatsimpan.setString(9,"0");
                            psobatsimpan.setDouble(10, Double.parseDouble(tbObat.getValueAt(i,5).toString())*Double.parseDouble(tbObat.getValueAt(i,0).toString()));
                            psobatsimpan.executeUpdate();  
                            try{
                               psobatsimpan2.setString(1,tbObat.getValueAt(i,1).toString());
                               psobatsimpan2.setString(2,bangsal);
                               psobatsimpan2.setDouble(3,-Double.parseDouble(tbObat.getValueAt(i,0).toString()));
                               psobatsimpan2.executeUpdate();
                            }catch(Exception ex){
                                psobatsimpan3.setDouble(1,Double.parseDouble(tbObat.getValueAt(i,0).toString()));
                                psobatsimpan3.setString(2,tbObat.getValueAt(i,1).toString());
                                psobatsimpan3.executeUpdate();                                 
                            }
                    }
                    tbObat.setValueAt("",i,0);
                }  
                //psobat.close();
                //ps2.close();
                //ps3.close();
                koneksi.setAutoCommit(true);
                dispose();
            } catch (SQLException | NumberFormatException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setAlwaysOnTop(false);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbObat.getRowCount();i++){ 
                tbObat.setValueAt("",i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void ppHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusObatActionPerformed
           try{
                i=tbObat.getSelectedRow();
                if(bangsal.equals("")){
                    Valid.textKosong(TCari,"Lokasi");
                }else if(i!= -1){
                    if(!tbObat.getValueAt(i,0).toString().equals("")){                        
                        pshapusobat.setString(1,TNoRw.getText());
                        pshapusobat.setString(2,TKdPny.getText());
                        pshapusobat.setString(3,tbObat.getValueAt(i,1).toString());
                        /*java.sql.Date yoursqlDate= new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(Tanggal.getText()).getTime());
                        pshapusobat.setDate(4,yoursqlDate);
                        pshapusobat.setTime(5,new java.sql.Time(
                                Integer.parseInt(Jam.getText().substring(0,2)),
                                Integer.parseInt(Jam.getText().substring(3,5)),
                                Integer.parseInt(Jam.getText().substring(6,8))));*/
                        pshapusobat.setString(4,Tanggal.getText());
                        pshapusobat.setString(5,Jam.getText());
                        pshapusobat.executeUpdate();
                        try{
                           psobatsimpan2.setString(1,tbObat.getValueAt(i,1).toString());
                           psobatsimpan2.setString(2,bangsal);
                           psobatsimpan2.setDouble(3,Double.parseDouble(tbObat.getValueAt(i,0).toString()));
                           psobatsimpan2.executeUpdate();
                        }catch(Exception ex){
                           psobatsimpan4.setDouble(1,Double.parseDouble(tbObat.getValueAt(i,0).toString()));
                           psobatsimpan4.setString(2,tbObat.getValueAt(i,1).toString());
                           psobatsimpan4.executeUpdate();                                 
                        }                       
                        
                        tampilobat();    
                    }                    
                }                
            }catch(SQLException | NumberFormatException e){
                System.out.println("Pesan Error : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
}//GEN-LAST:event_ppHapusObatActionPerformed

private void JenisjualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisjualItemStateChanged
       tampilobat(); 
}//GEN-LAST:event_JenisjualItemStateChanged

private void JenisjualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisjualKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
}//GEN-LAST:event_JenisjualKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObat dialog = new DlgCariObat(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Jam;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Kd2;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRw;
    private widget.TextBox Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.Label label12;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppHapusObat;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampilobat() {        
        z=0;
        for(i=0;i<tbObat.getRowCount();i++){
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        
        jumlah=null;
        jumlah=new double[z];
        stok=null;
        stok=new double[z];
        harga=null;
        harga=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        namajenis=null;
        namajenis=new String[z];        
             
        
        z=0;        
        for(i=0;i<tbObat.getRowCount();i++){
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                jumlah[z]=Double.parseDouble(tbObat.getValueAt(i,0).toString());
                kodebarang[z]=tbObat.getValueAt(i,1).toString();
                namabarang[z]=tbObat.getValueAt(i,2).toString();
                kodesatuan[z]=tbObat.getValueAt(i,3).toString();
                letakbarang[z]=tbObat.getValueAt(i,4).toString();
                harga[z]=Double.parseDouble(tbObat.getValueAt(i,5).toString());
                stok[z]=Double.parseDouble(tbObat.getValueAt(i,6).toString());
                namajenis[z]=tbObat.getValueAt(i,7).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeobat);             
        
        for(i=0;i<z;i++){
            tabModeobat.addRow(new Object[] {jumlah[i],kodebarang[i],namabarang[i],
                           kodesatuan[i],letakbarang[i],harga[i],stok[i],namajenis[i]});
        }
        try{       
            psobat2.setString(1,TNoRw.getText());
            rsobat=psobat2.executeQuery();
            while(rsobat.next()){
                tabModeobat.addRow(new Object[] {"",
                               rsobat.getString("kode_brng"),
                               rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),
                               rsobat.getString("letak_barang"),
                               rsobat.getDouble("biaya_obat"),
                               rsobat.getDouble("stok"),
                               rsobat.getString("nama")});
            }    
        }catch(SQLException e){
            System.out.println("Error : "+e);
        } 
        
        try{           
            psobat.setString(1,"%"+TCari.getText().trim()+"%");
            psobat.setString(2,"%"+TCari.getText().trim()+"%");
            psobat.setString(3,"%"+TCari.getText().trim()+"%");
            rsobat=psobat.executeQuery();
            while(rsobat.next()){
                if(Jenisjual.getSelectedItem().equals("Ranap Umum")){
                    tabModeobat.addRow(new Object[] {"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),rsobat.getDouble("h_retail"),rsobat.getDouble("stok"),
                               rsobat.getString("nama")});
                }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                    tabModeobat.addRow(new Object[] {"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),rsobat.getDouble("h_distributor"),rsobat.getDouble("stok"),
                               rsobat.getString("nama")});
                }else if(Jenisjual.getSelectedItem().equals("Ranap JKM")){
                    tabModeobat.addRow(new Object[] {"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),rsobat.getDouble("h_grosir"),rsobat.getDouble("stok"),
                               rsobat.getString("nama")});
                }                
            }  
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }

    public void emptTeksobat() {
        Kd2.setText(""); 
        TCari.setText("");
        TCari.requestFocus();
    }

    private void getDataobat() {
        if(tbObat.getSelectedRow()!= -1){
            /*if(!tabModeobat.getValueAt(tbKamar.getSelectedRow(),0).toString().equals("")){
                if(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString())>Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString())){
                        JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                        TCari.requestFocus();
                        tbKamar.setValueAt("", tbKamar.getSelectedRow(),0);  
                }                
            }*/
            Kd2.setText("");
            Kd2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
        }
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbObat;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    
    public void isCek(){        
        BtnTambah.setEnabled(var.getobat());
        TCari.requestFocus();
    }
    
    public void setNoRm(String norwt,String penyakit, String tanggal, String jam) {        
        TKdPny.setText(penyakit);
        TNoRw.setText(norwt);
        Tanggal.setText(tanggal);
        Jam.setText(jam);  
        TCari.requestFocus();
    }
    
    
}
