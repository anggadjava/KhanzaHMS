/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package inventory;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgCariPenyakit;

/**
 *
 * @author dosen
 */
public final class DlgCariObat3 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private int i=0,jml=0;
    private ResultSet rstampilbarang,rsstokmasuk,rspemberian,rskeluar,rsretur,rscariharga,rspasien;
    private PreparedStatement pstampilbarang,psstokmasuk,pspemberian,pskeluar,psretur,psimpanretur,pscariharga,
                              pshapusobat,pshapusretur,psobatsimpan,psobatsimpan3,psobatsimpan4,pspasien;
    private double stokmasuk=0,pagi=0,siang=0,sore=0,malam=0,keluar=0,retur=0,harga=0,kapasitas=0;
    private DlgCariPenyakit dlgpnykt=new DlgCariPenyakit(null,false);
    private String bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariObat3(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(856,350);
        Object[] row={"K","Kode Barang","Nama Barang","Stok.Msk","Pagi","Siang","Sore","Malam","Ttl.Msk","Ttl.Klr","Retur","Rtr.Sh","Ttl.Hlg"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==3)||(colIndex==8)||(colIndex==9)||(colIndex==11)||(colIndex==12)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class 
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else{
                column.setPreferredWidth(60);
            }      
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());            
        
        dlgpnykt.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPny.setText(dlgpnykt.getTextField().getText());     
                Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        try {
            pstampilbarang=koneksi.prepareStatement(
                    "select stok_obat_pasien.kode_brng,databarang.nama_brng,sum(stok_obat_pasien.jumlah) "+
                    "from stok_obat_pasien inner join databarang on databarang.kode_brng=stok_obat_pasien.kode_brng "+
                    "where stok_obat_pasien.no_rawat=? group by stok_obat_pasien.kode_brng order by databarang.nama_brng");
            psstokmasuk=koneksi.prepareStatement(
                    "select sum(stok_obat_pasien.jumlah) as jumlah from stok_obat_pasien where "+
                    "stok_obat_pasien.no_rawat=? and stok_obat_pasien.tanggal=? and stok_obat_pasien.kode_brng=?");
            pspemberian=koneksi.prepareStatement(
                    "select sum(detail_pemberian_obat.jml) as jml from detail_pemberian_obat where "+
                    "detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.tgl_perawatan=? and "+
                    "detail_pemberian_obat.kode_brng=? and jam between ? and ?");
            pskeluar=koneksi.prepareStatement(
                    "select sum(detail_pemberian_obat.jml) as jml from detail_pemberian_obat where "+
                    "detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.kode_brng=?");
            psretur=koneksi.prepareStatement(
                    "select sum(returpasien.jml) as jml from returpasien where "+
                    "returpasien.no_rawat=? and returpasien.kode_brng=?");
            pshapusobat=koneksi.prepareStatement(
                    "delete from detail_pemberian_obat where detail_pemberian_obat.no_rawat=? and "+
                    "detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.kode_brng=? ");
            pshapusretur=koneksi.prepareStatement(
                    "delete from returpasien where returpasien.no_rawat=? and returpasien.kode_brng=? ");
            psobatsimpan= koneksi.prepareStatement("insert into detail_pemberian_obat values(?,?,?,?,?,?,?,?,?,?)");
            psobatsimpan3= koneksi.prepareStatement("update gudangbarang set stok=stok-? where kode_brng=? and kd_bangsal=?");           
            psobatsimpan4= koneksi.prepareStatement("update gudangbarang set stok=stok+? where kode_brng=? and kd_bangsal=?");
            psimpanretur= koneksi.prepareStatement("insert into returpasien values(?,?,?,?)");
            pscariharga=koneksi.prepareStatement("select databarang.h_retail,databarang.h_distributor,databarang.h_grosir,"+
                    "IFNULL(kapasitas,0) as kapasitas from databarang "+
                    "where databarang.kode_brng=?");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TNoRw = new widget.TextBox();
        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelisi3 = new widget.panelisi();
        label12 = new widget.Label();
        Jenisjual = new widget.ComboBox();
        label13 = new widget.Label();
        BtnSimpan = new widget.Button();
        btnCetak = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        Tanggal = new widget.Tanggal();
        BtnCari = new widget.Button();
        jLabel12 = new widget.Label();
        TKdPny = new widget.TextBox();
        TNmPny = new widget.TextBox();
        BtnSeek2 = new widget.Button();

        TNoRw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setSelectionColor(new java.awt.Color(255, 255, 255));
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat/Alkes/BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
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
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label12);

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ranap Umum", "Rawat Jalan", "Ranap BPJS" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi3.add(Jenisjual);

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label13);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        btnCetak.setMnemonic('T');
        btnCetak.setText("Cetak");
        btnCetak.setToolTipText("Alt+T");
        btnCetak.setName("btnCetak"); // NOI18N
        btnCetak.setPreferredSize(new java.awt.Dimension(92, 36));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        panelisi3.add(btnCetak);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(92, 36));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 43));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel5.setText("Tanggal :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(jLabel5);

        Tanggal.setEditable(false);
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-06-08" }));
        Tanggal.setDisplayFormat("yyyy-MM-dd");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(100, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        FormInput.add(BtnCari);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(jLabel12);

        TKdPny.setText("-");
        TKdPny.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.setPreferredSize(new java.awt.Dimension(100, 23));
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);

        TNmPny.setEditable(false);
        TNmPny.setText("-");
        TNmPny.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNmPny.setName("TNmPny"); // NOI18N
        TNmPny.setPreferredSize(new java.awt.Dimension(246, 23));
        FormInput.add(TNmPny);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('2');
        BtnSeek2.setToolTipText("Alt+2");
        BtnSeek2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek2);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                if(tbObat.getSelectedRow()!= -1){
                    if((tbObat.getSelectedColumn()==4)||(tbObat.getSelectedColumn()==5)||(tbObat.getSelectedColumn()==6)||(tbObat.getSelectedColumn()==7)||(tbObat.getSelectedColumn()==10)){
                        try {            
                            tbObat.setValueAt("",tbObat.getSelectedRow(),tbObat.getSelectedColumn());        
                        } catch (Exception e) {
                        }
                    }                    
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            } 
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
       dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TKdPny.getText().trim().equals("")){
            Valid.textKosong(Jenisjual,"Data");
        }else if(bangsal.equals("")){
            Valid.textKosong(Jenisjual,"Lokasi");
        }else{
            try {  
                koneksi.setAutoCommit(false); 
                jml=tbObat.getRowCount();
                for(i=0;i<jml;i++){   
                    harga=0;
                    kapasitas=1;
                    pscariharga.setString(1,tbObat.getValueAt(i,1).toString());
                    rscariharga=pscariharga.executeQuery();
                    while(rscariharga.next()){
                        if(Jenisjual.getSelectedItem().equals("Ranap Umum")){
                            harga=rscariharga.getDouble("h_retail");
                        }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                            harga=rscariharga.getDouble("h_distributor");
                        }else if(Jenisjual.getSelectedItem().equals("Ranap BPJS")){
                            harga=rscariharga.getDouble("h_grosir");
                        }  
                        if(rscariharga.getDouble("kapasitas")>0){
                           kapasitas=rscariharga.getDouble("kapasitas");                            
                        }
                    }                    
                         
                    pagi=0;
                    try {
                        pagi=Double.parseDouble(tbObat.getValueAt(i,4).toString()); 
                    } catch (Exception e) {
                        pagi=0;
                    }
                    siang=0;
                    try {
                        siang=Double.parseDouble(tbObat.getValueAt(i,5).toString()); 
                    } catch (Exception e) {
                        siang=0;
                    }
                    sore=0;
                    try {
                        sore=Double.parseDouble(tbObat.getValueAt(i,6).toString()); 
                    } catch (Exception e) {
                        sore=0;
                    }
                    malam=0;
                    try {
                        malam=Double.parseDouble(tbObat.getValueAt(i,7).toString()); 
                    } catch (Exception e) {
                        malam=0;
                    }
                    retur=0;
                    try {
                        retur=Double.parseDouble(tbObat.getValueAt(i,10).toString()); 
                    } catch (Exception e) {
                        retur=0;
                    } 
                    
                    pshapusobat.setString(1,TNoRw.getText());
                    pshapusobat.setString(2,Tanggal.getSelectedItem().toString());
                    pshapusobat.setString(3,tbObat.getValueAt(i,1).toString());
                    pshapusobat.executeUpdate();
                    
                    if(retur>0){
                        pshapusretur.setString(1,TNoRw.getText());
                        pshapusretur.setString(2,tbObat.getValueAt(i,1).toString());
                        pshapusretur.executeUpdate();                        

                        psretur.setString(1,TNoRw.getText());
                        psretur.setString(2,tbObat.getValueAt(i,1).toString());
                        rsretur=psretur.executeQuery();
                        if(rsretur.next()){
                            psobatsimpan3.setDouble(1,rsretur.getDouble("jml"));
                            psobatsimpan3.setString(2,tbObat.getValueAt(i,1).toString());
                            psobatsimpan3.setString(3,bangsal);
                            psobatsimpan3.executeUpdate(); 
                        }                      

                        psimpanretur.setString(1,Tanggal.getSelectedItem().toString());
                        psimpanretur.setString(2,TNoRw.getText());
                        psimpanretur.setString(3,tbObat.getValueAt(i,1).toString());
                        psimpanretur.setDouble(4,retur);
                        psimpanretur.executeUpdate();
                        
                        psobatsimpan4.setDouble(1,retur);
                        psobatsimpan4.setString(2,tbObat.getValueAt(i,1).toString());
                        psobatsimpan4.setString(3,bangsal);
                        psobatsimpan4.executeUpdate();
                    }                  
                                       
                    if(pagi>0){
                        psobatsimpan.setString(1,Tanggal.getSelectedItem().toString());
                        psobatsimpan.setString(2,"07:00:00");
                        psobatsimpan.setString(3,TNoRw.getText());
                        psobatsimpan.setString(4,TKdPny.getText());
                        psobatsimpan.setString(5,"-");
                        psobatsimpan.setString(6,tbObat.getValueAt(i,1).toString());
                        psobatsimpan.setDouble(7,harga);
                        if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                            psobatsimpan.setDouble(8,(pagi/kapasitas));
                            psobatsimpan.setDouble(10,(harga*(pagi/kapasitas)));  
                        }else{                                
                            psobatsimpan.setDouble(8,pagi);
                            psobatsimpan.setDouble(10, harga*pagi);
                        }
                        psobatsimpan.setString(9,"0");
                        psobatsimpan.executeUpdate();  
                    }
                    
                    if(siang>0){
                        psobatsimpan.setString(1,Tanggal.getSelectedItem().toString());
                        psobatsimpan.setString(2,"12:00:00");
                        psobatsimpan.setString(3,TNoRw.getText());
                        psobatsimpan.setString(4,TKdPny.getText());
                        psobatsimpan.setString(5,"-");
                        psobatsimpan.setString(6,tbObat.getValueAt(i,1).toString());
                        psobatsimpan.setDouble(7,harga);
                        if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                            psobatsimpan.setDouble(8,(siang/kapasitas));
                            psobatsimpan.setDouble(10,(harga*(siang/kapasitas)));    
                        }else{                                
                            psobatsimpan.setDouble(8,siang);
                            psobatsimpan.setDouble(10, harga*siang);
                        }
                        psobatsimpan.setString(9,"0");
                        psobatsimpan.executeUpdate();  
                    }
                    
                    if(sore>0){
                        psobatsimpan.setString(1,Tanggal.getSelectedItem().toString());
                        psobatsimpan.setString(2,"16:00:00");
                        psobatsimpan.setString(3,TNoRw.getText());
                        psobatsimpan.setString(4,TKdPny.getText());
                        psobatsimpan.setString(5,"-");
                        psobatsimpan.setString(6,tbObat.getValueAt(i,1).toString());
                        psobatsimpan.setDouble(7,harga);
                        if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                            psobatsimpan.setDouble(8,(sore/kapasitas));
                            psobatsimpan.setDouble(10,(harga*(sore/kapasitas)));    
                        }else{                                
                            psobatsimpan.setDouble(8,sore);
                            psobatsimpan.setDouble(10, harga*sore);
                        }
                        psobatsimpan.setString(9,"0");
                        psobatsimpan.executeUpdate();  
                    }
                    
                    if(malam>0){
                        psobatsimpan.setString(1,Tanggal.getSelectedItem().toString());
                        psobatsimpan.setString(2,"20:00:00");
                        psobatsimpan.setString(3,TNoRw.getText());
                        psobatsimpan.setString(4,TKdPny.getText());
                        psobatsimpan.setString(5,"-");
                        psobatsimpan.setString(6,tbObat.getValueAt(i,1).toString());
                        psobatsimpan.setDouble(7,harga);
                        if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                            psobatsimpan.setDouble(8,(malam/kapasitas));
                            psobatsimpan.setDouble(10,(harga*(malam/kapasitas)));  
                        }else{                                
                            psobatsimpan.setDouble(8,malam);
                            psobatsimpan.setDouble(10, harga*malam);
                        }
                        psobatsimpan.setString(9,"0");
                        psobatsimpan.executeUpdate();  
                    }                  
                }    
                koneksi.setAutoCommit(true);
                tampil(); 
            }catch(Exception e){
                System.out.println(e);
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    Valid.pindah(evt,BtnKeluar,TKdPny);
}//GEN-LAST:event_TanggalKeyPressed

private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
        }else{            
            Valid.pindah(evt,TNoRw,BtnSimpan);
        }        
}//GEN-LAST:event_TKdPnyKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dlgpnykt.emptTeks();
        dlgpnykt.isCek();
        dlgpnykt.tampil();
        dlgpnykt.setSize(this.getWidth()-40,this.getHeight()-40);
        dlgpnykt.setLocationRelativeTo(internalFrame1);
        dlgpnykt.setAlwaysOnTop(false);
        dlgpnykt.setVisible(true);        
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
    Valid.pindah(evt,TKdPny,BtnSimpan);
}//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TNoRw.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(tbObat.getRowCount()==0){
            Valid.textKosong(Tanggal,"Data Obat");
        }else{
            try {
                pspasien=koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.no_rawat=?");
                pspasien.setString(1,TNoRw.getText());
                rspasien=pspasien.executeQuery();
                while(rspasien.next()){
                    Sequel.queryu("delete from temporary");
                    for(i=0;i<tbObat.getRowCount();i++){
                        Sequel.menyimpan("temporary","'0','"+
                                tbObat.getValueAt(i,2).toString()+"','"+
                                tbObat.getValueAt(i,3).toString()+"','"+
                                tbObat.getValueAt(i,4).toString()+"','"+
                                tbObat.getValueAt(i,5).toString()+"','"+
                                tbObat.getValueAt(i,6).toString()+"','"+
                                tbObat.getValueAt(i,7).toString()+"','"+
                                tbObat.getValueAt(i,8).toString()+"','"+
                                tbObat.getValueAt(i,9).toString()+"','"+
                                tbObat.getValueAt(i,10).toString()+"','"+
                                tbObat.getValueAt(i,11).toString()+"','"+
                                tbObat.getValueAt(i,12).toString()+"','','','','','',''","Data User");
                    }
                    Map<String, Object> param = new HashMap<>();
                    param.put("norm",rspasien.getString("no_rkm_medis"));
                    param.put("namapasien",rspasien.getString("nm_pasien"));
                    param.put("jkel",rspasien.getString("jk"));
                    param.put("umur",rspasien.getString("umur"));
                    param.put("tanggal",Tanggal.getSelectedItem().toString());
                    Valid.MyReport("rptObatPasien.jrxml","report","::[ Obat Keluar Masuk ]::",
                            "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);
                }                
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCetakActionPerformed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
            getData();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                dispose();
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObat3 dialog = new DlgCariObat3(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Kd2;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKdPny;
    private widget.TextBox TNmPny;
    private widget.TextBox TNoRw;
    private widget.Tanggal Tanggal;
    private widget.Button btnCetak;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel5;
    private widget.Label label12;
    private widget.Label label13;
    private widget.panelisi panelisi3;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    
    public void tampil() {         
        try {             
            Valid.tabelKosong(tabMode);
            pstampilbarang.setString(1,TNoRw.getText());
            rstampilbarang=pstampilbarang.executeQuery();
            while(rstampilbarang.next()){
                stokmasuk=0;
                psstokmasuk.setString(1,TNoRw.getText());
                psstokmasuk.setString(2,Tanggal.getSelectedItem().toString());
                psstokmasuk.setString(3,rstampilbarang.getString("kode_brng"));
                rsstokmasuk=psstokmasuk.executeQuery();
                if(rsstokmasuk.next()){
                    stokmasuk=rsstokmasuk.getDouble("jumlah");
                }
                
                pagi=0;
                pspemberian.setString(1,TNoRw.getText());
                pspemberian.setString(2,Tanggal.getSelectedItem().toString());
                pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                pspemberian.setString(4,"00:00:01");
                pspemberian.setString(5,"10:00:00");
                rspemberian=pspemberian.executeQuery();
                if(rspemberian.next()){
                    pagi=rspemberian.getDouble("jml");
                }
                
                siang=0;
                pspemberian.setString(1,TNoRw.getText());
                pspemberian.setString(2,Tanggal.getSelectedItem().toString());
                pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                pspemberian.setString(4,"10:00:01");
                pspemberian.setString(5,"15:00:00");
                rspemberian=pspemberian.executeQuery();
                if(rspemberian.next()){
                    siang=rspemberian.getDouble("jml");
                }
                
                sore=0;
                pspemberian.setString(1,TNoRw.getText());
                pspemberian.setString(2,Tanggal.getSelectedItem().toString());
                pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                pspemberian.setString(4,"15:00:01");
                pspemberian.setString(5,"19:00:00");
                rspemberian=pspemberian.executeQuery();
                if(rspemberian.next()){
                    sore=rspemberian.getDouble("jml");
                }
                
                malam=0;
                pspemberian.setString(1,TNoRw.getText());
                pspemberian.setString(2,Tanggal.getSelectedItem().toString());
                pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                pspemberian.setString(4,"19:00:01");
                pspemberian.setString(5,"23:59:59");
                rspemberian=pspemberian.executeQuery();
                if(rspemberian.next()){
                    malam=rspemberian.getDouble("jml");
                }
                
                keluar=0;
                pskeluar.setString(1,TNoRw.getText());
                pskeluar.setString(2,rstampilbarang.getString("kode_brng"));
                rskeluar=pskeluar.executeQuery();
                if(rskeluar.next()){
                    keluar=rskeluar.getDouble("jml");
                }
                
                retur=0;
                psretur.setString(1,TNoRw.getText());
                psretur.setString(2,rstampilbarang.getString("kode_brng"));
                rsretur=psretur.executeQuery();
                if(rsretur.next()){
                    retur=rsretur.getDouble("jml");
                }
                
                tabMode.addRow(new Object[]{false,rstampilbarang.getString("kode_brng"),rstampilbarang.getString("nama_brng"),stokmasuk,
                           pagi,siang,sore,malam,rstampilbarang.getDouble(3),keluar,retur,(rstampilbarang.getDouble(3)-keluar),((rstampilbarang.getDouble(3)-keluar)-retur)});
            }
        } catch (SQLException e) {
            System.out.println(e);
        }       
    }
    
    public void setNoRm(String norwt,String penyakit,Date tanggal) {        
        TKdPny.setText(penyakit);
        TNoRw.setText(norwt);

        Tanggal.setDate(tanggal);
        tbObat.requestFocus();
    }  
    
    private void getData(){
        int row=tbObat.getSelectedRow();
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No. Rawat");
        }else if(row!= -1){ 
            Kd2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            int kolom=tbObat.getSelectedColumn();  
            if((kolom==4)||(kolom==5)||(kolom==6)||(kolom==7)){
                pagi=0;
                try {
                    pagi=Double.parseDouble(tbObat.getValueAt(row,4).toString()); 
                } catch (Exception e) {
                    pagi=0;
                }
                siang=0;
                try {
                    siang=Double.parseDouble(tbObat.getValueAt(row,5).toString()); 
                } catch (Exception e) {
                    siang=0;
                }
                sore=0;
                try {
                    sore=Double.parseDouble(tbObat.getValueAt(row,6).toString()); 
                } catch (Exception e) {
                    sore=0;
                }
                malam=0;
                try {
                    malam=Double.parseDouble(tbObat.getValueAt(row,7).toString()); 
                } catch (Exception e) {
                    malam=0;
                }
                stokmasuk=0;
                try {
                    stokmasuk=Double.parseDouble(tbObat.getValueAt(row,8).toString());
                } catch (Exception e) {
                    stokmasuk=0;
                }
                if(tbObat.getValueAt(row,0).toString().equals("false")){
                    if((stokmasuk-(pagi+siang+sore+malam))<0){
                        JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                        tbObat.requestFocus();
                        tbObat.setValueAt("0", row,kolom);  
                    }else{
                        tbObat.setValueAt(Double.toString(pagi+siang+sore+malam), row,9);  
                    }  
                }else{
                    try {
                        kapasitas=1;
                        pscariharga.setString(1,tbObat.getValueAt(row,1).toString());
                        rscariharga=pscariharga.executeQuery();
                        while(rscariharga.next()){
                            if(rscariharga.getDouble("kapasitas")>0){
                                kapasitas=rscariharga.getDouble("kapasitas");                                
                            }
                        }
                        if((stokmasuk-((pagi/kapasitas)+(siang/kapasitas)+(sore/kapasitas)+(malam/kapasitas)))<0){
                            JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                            tbObat.requestFocus();
                            tbObat.setValueAt("0", row,kolom);  
                        }else{
                            tbObat.setValueAt(Double.toString((pagi/kapasitas)+(siang/kapasitas)+(sore/kapasitas)+(malam/kapasitas)), row,9);  
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
                                               
            }else if(kolom==10){
                retur=0;
                try {
                    retur=Double.parseDouble(tbObat.getValueAt(row,10).toString()); 
                } catch (Exception e) {
                    retur=0;
                }
                stokmasuk=0;
                try {
                    stokmasuk=Double.parseDouble(tbObat.getValueAt(row,11).toString());
                } catch (Exception e) {
                    stokmasuk=0;
                }
                if((stokmasuk-retur)<0){
                    JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                    tbObat.requestFocus();
                    tbObat.setValueAt("0", row,kolom);  
                }else{
                    tbObat.setValueAt(Double.toString(stokmasuk-retur), row,12);   
                }   
            }
        }
    }
    
    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbObat;
    }
}
