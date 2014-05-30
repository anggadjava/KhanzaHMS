package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import khanzahms.DlgBarang;
import khanzahms.DlgCariPetugas;
import khanzahms.DlgSuplier;

public class DlgCariPembelian extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    public  DlgSuplier suplier=new DlgSuplier(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgBarang barang=new DlgBarang(null,false);
    private DlgCariSatuan satuan=new DlgCariSatuan(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double tagihan=0;
    private Jurnal jur=new Jurnal();

    /** Creates new form DlgProgramStudi */
    public DlgCariPembelian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Beli",
                    "No.Faktur",
                    "Suplier",
                    "Petugas",
                    "Barang",
                    "Satuan",
                    "Jml.Beli",
                    "Harga Beli(Rp)",
                    "SubTotal(Rp)",
                    "Disk(%)",
                    "Bsr.Disk(Rp)",
                    "Total(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(190);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(120);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)15).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        kdsat.setDocument(new batasInput((byte)3).getKata(kdsat));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
       
        
        suplier.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdsup.setText(suplier.getTextField().getText());
                Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
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
        
        satuan.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdsat.setText(satuan.getTextField().getText());
                Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
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
        
        petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdptg.setText(petugas.getTextField().getText());
                Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
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
        
        barang.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdbar.setText(barang.getTextField().getText());
                Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());              
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
        
        BtnPrint.setEnabled(var.getpembelian());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        BtnCari6 = new widget.Button();
        label24 = new widget.Label();
        kdsat = new widget.TextBox();
        BtnCari7 = new widget.Button();
        nmsat = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnCari4 = new widget.Button();
        BtnCari5 = new widget.Button();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(242, 242, 242));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(102, 51, 0));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppHapus.setText("Hapus Pembelian");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setIconTextGap(8);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Pembelian Barang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

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
        panelisi1.add(BtnCari);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 30));
        panelisi1.add(LTotal);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
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
        panelisi1.add(BtnAll);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(295, 10, 90, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(389, 10, 110, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(501, 10, 230, 23);

        BtnCari6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari6.setMnemonic('4');
        BtnCari6.setToolTipText("Alt+4");
        BtnCari6.setName("BtnCari6"); // NOI18N
        BtnCari6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari6ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnCari6);
        BtnCari6.setBounds(734, 10, 28, 23);

        label24.setText("Satuan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 75, 23);

        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsatKeyPressed(evt);
            }
        });
        panelisi4.add(kdsat);
        kdsat.setBounds(80, 10, 53, 23);

        BtnCari7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari7.setMnemonic('3');
        BtnCari7.setToolTipText("Alt+3");
        BtnCari7.setName("BtnCari7"); // NOI18N
        BtnCari7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari7ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnCari7);
        BtnCari7.setBounds(253, 10, 28, 23);

        nmsat.setName("nmsat"); // NOI18N
        nmsat.setPreferredSize(new java.awt.Dimension(80, 23));
        nmsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmsatKeyPressed(evt);
            }
        });
        panelisi4.add(nmsat);
        nmsat.setBounds(135, 10, 115, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(84, 10, 219, 23);

        label11.setText("Tgl.Beli :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 80, 23);

        TglBeli1.setEditable(false);
        TglBeli1.setDisplayFormat("yyyy-MM-dd");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(84, 40, 95, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(305, 10, 80, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 40, 80, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(389, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(389, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(471, 10, 260, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(471, 40, 260, 23);

        BtnCari4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari4.setMnemonic('1');
        BtnCari4.setToolTipText("Alt+1");
        BtnCari4.setName("BtnCari4"); // NOI18N
        BtnCari4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari4ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCari4);
        BtnCari4.setBounds(734, 10, 28, 23);

        BtnCari5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari5.setMnemonic('2');
        BtnCari5.setToolTipText("Alt+2");
        BtnCari5.setName("BtnCari5"); // NOI18N
        BtnCari5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari5ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCari5);
        BtnCari5.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(179, 40, 27, 23);

        TglBeli2.setEditable(false);
        TglBeli2.setDisplayFormat("yyyy-MM-dd");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(208, 40, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnCari4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari4ActionPerformed
        var.setStatus(true);
        suplier.emptTeks();
        suplier.tampil();
        suplier.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_BtnCari4ActionPerformed

    private void BtnCari5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari5ActionPerformed
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnCari5ActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void BtnCari6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari6ActionPerformed
        var.setStatus(true);
        barang.emptTeks();
        barang.tampil(" order by databarang.nama_brng");
        barang.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_BtnCari6ActionPerformed

    private void BtnCari7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari7ActionPerformed
        satuan.emptTeks();
        satuan.tampil(" order by kode_sat");
        satuan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        satuan.setLocationRelativeTo(internalFrame1);
        satuan.setAlwaysOnTop(false);
        satuan.setVisible(true);
    }//GEN-LAST:event_BtnCari7ActionPerformed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus();
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnKeluar, kdsup);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdbar.requestFocus();       
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            kdsat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void kdsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
            kdbar.requestFocus();   
        }
    }//GEN-LAST:event_kdsatKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBeli2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NoFaktur.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        nmsat.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','','','','','','','',''","Transaksi Pembelian"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            Sequel.menyimpan("temporary","'0','Jml.Total :','','','','','','',' ','"+LTotal.getText()+"','','','','','','','',''","Transaksi Pembelian"); 
            
            Valid.MyReport("rptPembelian.jrxml","report","::[ Transaksi Pembelian Barang ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void nmsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmsatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmsatKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
  if(Kd2.getText().trim().equals("")){
      Valid.textKosong(TCari,"No.Faktur");
  }else{
     try {
         PreparedStatement pscaribeli=koneksi.prepareStatement(
                 "select no_faktur, tagihan, kd_bangsal,tgl_beli from pembelian where no_faktur='"+Kd2.getText()+"'");
         ResultSet rscaribeli=pscaribeli.executeQuery();
         while(rscaribeli.next()){
             PreparedStatement psdetailbeli=koneksi.prepareStatement(
                  "select kode_brng,jumlah from detailbeli where no_faktur='"+rscaribeli.getString(1) +"' ");
             ResultSet rsdetailbeli=psdetailbeli.executeQuery();
             while(rsdetailbeli.next()){
                 Sequel.menyimpan("gudangbarang","'"+rsdetailbeli.getString("kode_brng") +"','"+rscaribeli.getString("kd_bangsal") +"','-"+rsdetailbeli.getString("jumlah") +"'", 
                                        "stok=stok-'"+rsdetailbeli.getString("jumlah") +"'","kode_brng='"+rsdetailbeli.getString("kode_brng")+"' and kd_bangsal='"+rscaribeli.getString("kd_bangsal") +"'");
             }
             Sequel.queryu("delete from tampjurnal");
             Sequel.menyimpan("tampjurnal","'51000','PEMBELIAN','0','"+rscaribeli.getString("tagihan")+"'","Rekening");    
             Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','"+rscaribeli.getString("tagihan") +"','0'","Rekening"); 
             jur.simpanJurnal(rscaribeli.getString("no_faktur"),Sequel.cariIsi("select current_date()"),"U","BATAL PEMBELIAN DI "+Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+rscaribeli.getString("kd_bangsal")+"'").toUpperCase());
         }          
         Sequel.queryu("delete from pembelian where no_faktur='"+Kd2.getText()+"'");
         tampil();
     } catch (SQLException ex) {
         System.out.println(ex);
     }
      
  }       
    
}//GEN-LAST:event_ppHapusActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPembelian dialog = new DlgCariPembelian(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari4;
    private widget.Button BtnCari5;
    private widget.Button BtnCari6;
    private widget.Button BtnCari7;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private widget.TextBox NoFaktur;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdptg;
    private widget.TextBox kdsat;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmptg;
    private widget.TextBox nmsat;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String tanggal="  pembelian.tgl_beli between '"+TglBeli1.getSelectedItem()+"' and '"+TglBeli2.getSelectedItem()+"' ";
        String nofak="",supl="",ptg="",sat="",bar="";
        if(!NoFaktur.getText().equals("")){
            nofak=" and pembelian.no_faktur='"+NoFaktur.getText()+"' ";
        }        
        if(!nmsup.getText().equals("")){
            supl=" and datasuplier.nama_suplier='"+nmsup.getText()+"' ";
        }
        if(!nmptg.getText().equals("")){
            ptg=" and petugas.nama='"+nmptg.getText()+"' ";
        }
        if(!nmsat.getText().equals("")){
            sat=" and kodesatuan.satuan='"+nmsat.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
        }
        String sql="select pembelian.tgl_beli,pembelian.no_faktur, "+
                    "pembelian.kode_suplier,datasuplier.nama_suplier, "+
                    "pembelian.nip,petugas.nama,bangsal.nm_bangsal "+
                    " from pembelian inner join datasuplier inner join petugas inner join bangsal  "+
                    " inner join detailbeli inner join databarang inner join kodesatuan "+
                    " on detailbeli.kode_brng=databarang.kode_brng "+
                    " and detailbeli.kode_sat=kodesatuan.kode_sat "+
                    " and pembelian.kd_bangsal=bangsal.kd_bangsal "+
                    " and pembelian.kode_suplier=datasuplier.kode_suplier "+
                    " and pembelian.nip=petugas.nip"+
                    " where "+tanggal+nofak+supl+ptg+sat+bar+" and pembelian.no_faktur like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and pembelian.kode_suplier like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and datasuplier.nama_suplier like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and pembelian.nip like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and petugas.nama like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and bangsal.nm_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and detailbeli.kode_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and detailbeli.kode_sat like '%"+TCari.getText()+"%' or "+
                    tanggal+nofak+supl+ptg+sat+bar+" and kodesatuan.satuan like '%"+TCari.getText()+"%' "+
                    " group by pembelian.no_faktur order by pembelian.tgl_beli,pembelian.no_faktur ";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
       Valid.tabelKosong(tabMode);
        try{
            java.sql.Statement stat=koneksi.createStatement();
            java.sql.Statement stat2=koneksi.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            tagihan=0;
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3)+", "+rs.getString(4),
                               rs.getString(5)+", "+rs.getString(6),
                               "Pembelian di "+rs.getString(7) +" :","","","","","","",""};
                tabMode.addRow(data);
                String sat="",bar="";
                if(!nmsat.getText().equals("")){
                    sat=" and kodesatuan.satuan='"+nmsat.getText()+"' ";
                }
                if(!nmbar.getText().equals("")){
                    bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
                }
                ResultSet rs2=stat2.executeQuery("select detailbeli.kode_brng,databarang.nama_brng, "+
                        "detailbeli.kode_sat,kodesatuan.satuan,detailbeli.jumlah,detailbeli.h_beli, "+
                        "detailbeli.subtotal,detailbeli.dis,detailbeli.besardis,detailbeli.total "+
                        "from detailbeli inner join databarang inner join kodesatuan "+
                        " on detailbeli.kode_brng=databarang.kode_brng "+
                        " and detailbeli.kode_sat=kodesatuan.kode_sat where "+
                        " detailbeli.no_faktur='"+rs.getString(2)+"' "+sat+bar+" and detailbeli.kode_brng like '%"+TCari.getText()+"%' or "+
                        " detailbeli.no_faktur='"+rs.getString(2)+"' "+sat+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                        " detailbeli.no_faktur='"+rs.getString(2)+"' "+sat+bar+" and detailbeli.kode_sat like '%"+TCari.getText()+"%' or "+
                        " detailbeli.no_faktur='"+rs.getString(2)+"' "+sat+bar+" and kodesatuan.satuan like '%"+TCari.getText()+"%' order by detailbeli.kode_brng  ");
                int no=1;
                while(rs2.next()){
                    String[] data2={"","","","",no+". "+rs2.getString(1)+", "+rs2.getString(2),
                                    rs2.getString(3)+", "+rs2.getString(4),
                                    rs2.getString(5),df2.format(rs2.getDouble(6)),df2.format(rs2.getDouble(7)),
                                    df2.format(rs2.getDouble(8)),df2.format(rs2.getDouble(9)),df2.format(rs2.getDouble(10))};
                    tabMode.addRow(data2);
                    no++;
                }
                String[] data3={"","","","","","","","","","Total",":",df2.format(Sequel.cariIsiAngka("select total2 from pembelian where no_faktur='"+rs.getString(2)+"'"))};
                String[] data4={"","","","","","","","","","PPN",":",df2.format(Sequel.cariIsiAngka("select ppn from pembelian where no_faktur='"+rs.getString(2)+"'"))};
                String[] data5={"","","","","","","","","","Tagihan",":",df2.format(Sequel.cariIsiAngka("select tagihan from pembelian where no_faktur='"+rs.getString(2)+"'"))};
                tabMode.addRow(data3);
                tabMode.addRow(data4);
                tabMode.addRow(data5);
                tagihan=tagihan+Sequel.cariIsiAngka("select tagihan from pembelian where no_faktur='"+rs.getString(2)+"'");
            }                
            LTotal.setText(df2.format(tagihan));
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        kdbar.requestFocus();        
    }
    
    private void getData() {
        int row=tbDokter.getSelectedRow();
        if(row!= -1){
            Kd2.setText(tbDokter.getValueAt(row,1).toString());
        }
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
 
}
