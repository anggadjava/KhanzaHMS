/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgObatPenyakit.java
 *
 * Created on May 23, 2010, 12:40:35 AM
 */

package khanzahms;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgObatPenyakit extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();

    /** Creates new form DlgObatPenyakit
     * @param parent
     * @param modal */
    public DlgObatPenyakit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"Kode Penyakit",
                "Nama Penyakit",
                "Ciri-ciri Penyakit",
                "Keterangan",
                "Kategori Penyakit",
                "Ciri-ciri Umum",
                "Kode Obat",
                "Nama Obat",
                "Jenis Obat",
                "Harga Obat",
                "Referensi"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObatPenyakit.setModel(tabMode);

        //tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObatPenyakit.getBackground()));
        tbObatPenyakit.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbObatPenyakit.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         for (int i = 0; i < 11; i++) {
            TableColumn column = tbObatPenyakit.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(120);
            }else if(i==10){
                column.setPreferredWidth(200);
            }
        }
        tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable());

        kdobat.setDocument(new batasInput((byte)15).getKata(kdobat));
        kdpenyakit.setDocument(new batasInput((byte)10).getKata(kdpenyakit));
        TRef.setDocument(new batasInput((byte)60).getKata(TRef));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));

        penyakit.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdpenyakit.setText(penyakit.getTextField().getText());    
                Sequel.cariIsi("select nm_penyakit from penyakit where  kd_penyakit=?", TPenyakit,penyakit.getTextField().getText());
                Sequel.cariIsi("select nm_penyakit from penyakit where  kd_penyakit=?",PenyakitCari,penyakit.getTextField().getText());
                isKtg();
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
        
        databarang.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 kdobat.setText(databarang.getTextField().getText());      
                Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", TObat,databarang.getTextField().getText());
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
        
        ktg.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sequel.cariIsi("select nm_kategori from kategori_penyakit where kd_ktg=?", KtgCari,ktg.getTextField().getText());
                isKtg();
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
    }
    
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    private DlgKtgPenyakit ktg=new DlgKtgPenyakit(null,false);
    private DlgBarang databarang=new DlgBarang(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObatPenyakit = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel7 = new widget.Label();
        KtgCari = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        jLabel11 = new widget.Label();
        PenyakitCari = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass2 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TPenyakit = new widget.TextBox();
        TObat = new widget.TextBox();
        jLabel12 = new widget.Label();
        TRef = new widget.TextBox();
        BtnSeek = new widget.Button();
        BtnSeek1 = new widget.Button();
        kdpenyakit = new widget.TextBox();
        kdobat = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat Penyakit/Alkes Dibutuhkan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObatPenyakit.setAutoCreateRowSorter(true);
        tbObatPenyakit.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatPenyakit.setName("tbObatPenyakit"); // NOI18N
        tbObatPenyakit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatPenyakitMouseClicked(evt);
            }
        });
        tbObatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatPenyakitKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObatPenyakit);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(108, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 30));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel7.setText("Ktg.Penyakit :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel7);

        KtgCari.setName("KtgCari"); // NOI18N
        KtgCari.setPreferredSize(new java.awt.Dimension(150, 23));
        KtgCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KtgCariKeyPressed(evt);
            }
        });
        panelGlass9.add(KtgCari);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
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
        panelGlass9.add(BtnSeek2);

        jLabel11.setText("Penyakit :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel11);

        PenyakitCari.setName("PenyakitCari"); // NOI18N
        PenyakitCari.setPreferredSize(new java.awt.Dimension(150, 23));
        PenyakitCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitCariKeyPressed(evt);
            }
        });
        panelGlass9.add(PenyakitCari);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('4');
        BtnSeek3.setToolTipText("Alt+4");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnSeek3);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TCariKeyTyped(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelGlass2.setName("panelGlass2"); // NOI18N
        panelGlass2.setPreferredSize(new java.awt.Dimension(711, 77));
        panelGlass2.setLayout(null);

        jLabel3.setText("Penyakit Yang Diderita :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass2.add(jLabel3);
        jLabel3.setBounds(-8, 12, 160, 23);

        jLabel4.setText("Obat/Alkes Dibutuhkan :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass2.add(jLabel4);
        jLabel4.setBounds(-8, 42, 160, 23);

        TPenyakit.setEditable(false);
        TPenyakit.setHighlighter(null);
        TPenyakit.setName("TPenyakit"); // NOI18N
        TPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenyakitKeyPressed(evt);
            }
        });
        panelGlass2.add(TPenyakit);
        TPenyakit.setBounds(257, 12, 250, 23);

        TObat.setEditable(false);
        TObat.setName("TObat"); // NOI18N
        TObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TObatKeyPressed(evt);
            }
        });
        panelGlass2.add(TObat);
        TObat.setBounds(257, 42, 250, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Referensi/Sumber Informasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass2.add(jLabel12);
        jLabel12.setBounds(569, 12, 180, 23);

        TRef.setHighlighter(null);
        TRef.setName("TRef"); // NOI18N
        TRef.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRefKeyPressed(evt);
            }
        });
        panelGlass2.add(TRef);
        TRef.setBounds(569, 42, 258, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelGlass2.add(BtnSeek);
        BtnSeek.setBounds(510, 12, 28, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('2');
        BtnSeek1.setToolTipText("Alt+2");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        panelGlass2.add(BtnSeek1);
        BtnSeek1.setBounds(510, 42, 28, 23);

        kdpenyakit.setHighlighter(null);
        kdpenyakit.setName("kdpenyakit"); // NOI18N
        kdpenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenyakitKeyPressed(evt);
            }
        });
        panelGlass2.add(kdpenyakit);
        kdpenyakit.setBounds(155, 12, 100, 23);

        kdobat.setHighlighter(null);
        kdobat.setName("kdobat"); // NOI18N
        kdobat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdobatKeyPressed(evt);
            }
        });
        panelGlass2.add(kdobat);
        kdobat.setBounds(155, 42, 100, 23);

        internalFrame1.add(panelGlass2, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenyakitKeyPressed
        //Valid.pindah(evt,TCari,cmbBangsal);
}//GEN-LAST:event_TPenyakitKeyPressed

    private void TObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TObatKeyPressed
        //Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_TObatKeyPressed

    private void TRefKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRefKeyPressed
        Valid.pindah(evt,kdobat,BtnSimpan);
}//GEN-LAST:event_TRefKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TPenyakit.getText().trim().equals("")){
            Valid.textKosong(TPenyakit,"Penyakit Yang Diderita");
        }else if(TObat.getText().trim().equals("")){
            Valid.textKosong(TObat,"Obat/Penawar Penyakit");
        }else if(TRef.getText().trim().equals("")){
            Valid.textKosong(TRef,"Referensi/Sumber Informasi");
        }else{
            try{
                java.sql.Statement stat=koneksi.createStatement();
                ResultSet rs=stat.executeQuery("select * from obat_penyakit where kd_penyakit='"+kdpenyakit.getText()+"' and kode_brng='"+kdobat.getText()+"'");
                rs.last();
                if(rs.getRow()>=1){
                    JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan. Ada data yang sama disimpan sebelumnya...!");
                }else if(rs.getRow()==0){
                    //menyimpan-------------------------------------------------
                    Sequel.menyimpan("obat_penyakit","'"+kdpenyakit.getText()+"','"+kdobat.getText()+"','"+TRef.getText()+"'","Data Sama");
                    //----------------------------------------------------------
                    tampil("");
                    emptTeks();
                }
                kdpenyakit.requestFocus();
            }catch(SQLException e){
                System.out.println("Error : "+e);
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TRef,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            kdpenyakit.requestFocus();
        }else if(TObat.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TObat.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from obat_penyakit where kd_penyakit='"+kdpenyakit.getText()+"' and kode_brng='"+kdobat.getText()+"'");
                tampil("");
                emptTeks();
            }catch(Exception e){
                System.out.println("Pesan Error : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if((! TCari.getText().trim().equals(""))&&(KtgCari.getText().equals(""))&&(PenyakitCari.getText().equals(""))){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if((TCari.getText().trim().equals(""))&&(KtgCari.getText().equals(""))&&(PenyakitCari.getText().equals(""))){
                Valid.MyReport("rptObtPenyakit.jrxml","report","::[ Data Obat Penyakit ]::","select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                               "nm_kategori,ciri_umum, obat_penyakit.kode_brng,nama_brng,jenis.nama,h_retail,referensi "+
                               "from obat_penyakit,penyakit,kategori_penyakit,databarang,jenis "+
                               "where penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                               "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                               "databarang.kdjns=jenis.kdjns and "+
                               "obat_penyakit.kode_brng=databarang.kode_brng "+
                               " order by obat_penyakit.kd_penyakit");
            }else if((! TCari.getText().trim().equals(""))&&(KtgCari.getText().equals(""))&&(PenyakitCari.getText().equals(""))){
                String sql=" penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                "databarang.kdjns=jenis.kdjns and "+
                "obat_penyakit.kode_brng=databarang.kode_brng ";
                
                Valid.MyReport("rptObtPenyakit.jrxml","report","::[ Data Obat Penyakit ]::","select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                                "nm_kategori,ciri_umum,obat_penyakit.kode_brng,nama_brng,jenis.nama,h_retail,referensi "+
                                "from obat_penyakit,penyakit,kategori_penyakit,databarang,jenis "+
                                "where "+
                                sql+"and obat_penyakit.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and ciri_ciri like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and penyakit.keterangan like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and nm_kategori like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and ciri_umum like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and obat_penyakit.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and nama_brng like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and jenis.nama like '%"+TCari.getText().trim()+"%' or "+
                                sql+"and referensi like '%"+TCari.getText().trim()+"%' "+
                                "order by obat_penyakit.kd_penyakit ");
            }else if(TCari.getText().trim().equals("")&&(! KtgCari.getText().equals("") || ! PenyakitCari.getText().equals(""))){                
                    String sql="";
                    if(KtgCari.getText().equals("") && PenyakitCari.getText().equals("")){
                        sql="";
                    }else if((! KtgCari.getText().equals(""))&& PenyakitCari.getText().equals("")){
                        sql="and nm_kategori='"+KtgCari.getText()+"' ";
                    }else if((! KtgCari.getText().equals("")) &&(! PenyakitCari.getText().equals(""))){
                        sql="and nm_kategori='"+KtgCari.getText()+"' "+
                            "and nm_penyakit='"+PenyakitCari.getText()+"' ";
                    }else if((KtgCari.getText().equals(""))&&(! PenyakitCari.getText().equals(""))){
                        sql="and nm_penyakit='"+PenyakitCari.getText()+"' ";
                    }
                    Valid.MyReport("rptObtPenyakit.jrxml","report","::[ Data Obat Penyakit ]::","select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                               "nm_kategori,ciri_umum,obat_penyakit.kode_brng,nama_brng,jenis.nama,h_retail,referensi "+
                               "from obat_penyakit,penyakit,kategori_penyakit,databarang,jenis "+
                               "where penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                               "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                               "databarang.kdjns=jenis.kdjns and "+
                               "obat_penyakit.kode_brng=databarang.kode_brng "+sql+
                               " order by obat_penyakit.kd_penyakit");

                }
            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,KtgCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        KtgCari.setText("");
        PenyakitCari.setText("");
        tampil("");
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        String sql=" penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                               "databarang.kdjns=jenis.kdjns and "+
                "obat_penyakit.kode_brng=databarang.kode_brng ";

        prosesCari("select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                "nm_kategori,ciri_umum,obat_penyakit.kode_brng,nama_brng,jenis.nama,h_retail,referensi "+
                "from obat_penyakit,penyakit,kategori_penyakit,databarang,jenis "+
                "where "+
                sql+"and obat_penyakit.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                sql+"and nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                sql+"and ciri_ciri like '%"+TCari.getText().trim()+"%' or "+
                sql+"and penyakit.keterangan like '%"+TCari.getText().trim()+"%' or "+
                sql+"and nm_kategori like '%"+TCari.getText().trim()+"%' or "+
                sql+"and ciri_umum like '%"+TCari.getText().trim()+"%' or "+
                sql+"and obat_penyakit.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                sql+"and nama_brng like '%"+TCari.getText().trim()+"%' or "+
                sql+"and jenis.nama like '%"+TCari.getText().trim()+"%' or "+
                sql+"and referensi like '%"+TCari.getText().trim()+"%' "+
                "order by obat_penyakit.kd_penyakit ");
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyTyped
        if((! KtgCari.getText().equals("")) ||(! PenyakitCari.getText().equals(""))){
            KtgCari.setText("");
            PenyakitCari.setText("");
        }
    }//GEN-LAST:event_TCariKeyTyped

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        penyakit.tampil();
        penyakit.isCek();
        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,kdpenyakit,kdobat);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        var.setStatus(true);
        databarang.tampil(" order by databarang.kode_brng ");
        databarang.isCek();
        databarang.emptTeks();
        databarang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        databarang.setLocationRelativeTo(internalFrame1);
        databarang.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,kdobat,BtnSimpan);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void tbObatPenyakitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatPenyakitMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatPenyakitMouseClicked

    private void tbObatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatPenyakitKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatPenyakitKeyPressed

private void kdpenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where  kd_penyakit=?", TPenyakit,kdpenyakit.getText());
        }else{
             Valid.pindah(evt,TCari,kdobat);
        }
}//GEN-LAST:event_kdpenyakitKeyPressed

private void kdobatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdobatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", TObat,kdobat.getText());
        }else{
             Valid.pindah(evt,kdpenyakit,TRef);
        }
}//GEN-LAST:event_kdobatKeyPressed

private void KtgCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KtgCariKeyPressed
   isKtg();
}//GEN-LAST:event_KtgCariKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        var.setStatus(true);
        ktg.tampil();
        ktg.emptTeks();        
        ktg.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        ktg.setLocationRelativeTo(internalFrame1);
        ktg.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   Valid.pindah(evt,BtnSeek3,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void PenyakitCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitCariKeyPressed
   isKtg();
}//GEN-LAST:event_PenyakitCariKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penyakit.tampil();
        penyakit.emptTeks();
        penyakit.isCek();
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgObatPenyakit dialog = new DlgObatPenyakit(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSimpan;
    private widget.TextBox KtgCari;
    private widget.Label LCount;
    private widget.TextBox PenyakitCari;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TObat;
    private widget.TextBox TPenyakit;
    private widget.TextBox TRef;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.TextBox kdobat;
    private widget.TextBox kdpenyakit;
    private widget.panelisi panelGlass2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObatPenyakit;
    // End of variables declaration//GEN-END:variables


    public void tampil(String kay) {
        String sql="select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                   "nm_kategori,ciri_umum,obat_penyakit.kode_brng,nama_brng,jenis.nama,h_retail,referensi "+
                   "from obat_penyakit,penyakit,kategori_penyakit,databarang,jenis "+
                   "where penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                   "databarang.kdjns=jenis.kdjns and "+
                   "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                   "obat_penyakit.kode_brng=databarang.kode_brng "+kay+
                   " order by obat_penyakit.kd_penyakit";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            java.sql.Statement stat=koneksi.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9),
                               Valid.SetAngka(rs.getDouble(10)),
                               rs.getString(11)};
                tabMode.addRow(data);
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        TRef.setText("");
        kdpenyakit.setText("");
        TPenyakit.setText("");
        kdobat.setText("");
        TObat.setText("");
        TPenyakit.setText("");
        kdpenyakit.requestFocus();
    }

    private void getData() {
        int row=tbObatPenyakit.getSelectedRow();
        if(row!= -1){
            kdpenyakit.setText(tbObatPenyakit.getValueAt(row,0).toString());
            TPenyakit.setText(tbObatPenyakit.getValueAt(row,1).toString());
            kdobat.setText(tbObatPenyakit.getValueAt(row,6).toString());
            TObat.setText(tbObatPenyakit.getValueAt(row,7).toString());
            TRef.setText(tbObatPenyakit.getValueAt(row,10).toString());
        }
    }
    
    private void isKtg(){
        if(! TCari.getText().equals("")){
            TCari.setText("");
        }
   
        if(KtgCari.getText().equals("") && PenyakitCari.getText().equals("")){
            tampil("");
        }else if((! KtgCari.getText().equals(""))&&PenyakitCari.getText().equals("")){
            tampil("and nm_kategori='"+KtgCari.getText()+"' ");
        }else if((! KtgCari.getText().equals(""))&&(! PenyakitCari.getText().equals(""))){
            tampil("and nm_kategori='"+KtgCari.getText()+"' "+
                   "and nm_penyakit='"+PenyakitCari.getText()+"' ");
        }else if((KtgCari.getText().equals(""))&&(! PenyakitCari.getText().equals(""))){
            tampil("and nm_penyakit='"+PenyakitCari.getText()+"' ");
        }        
    }  
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getobat_penyakit());
        BtnHapus.setEnabled(var.getobat_penyakit());
        BtnPrint.setEnabled(var.getobat_penyakit());
    }
}
