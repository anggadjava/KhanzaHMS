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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgSuplier;
import keuangan.Jurnal;
import khanzahms.DlgCariBangsal;

public class DlgReturBeli extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgSuplier suplier=new DlgSuplier(null,false);
    private DlgCariReturBeli form=new DlgCariReturBeli(null,false);  
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double ttlretur=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgReturBeli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Faktur",
                    "Kode Barang",
                    "Nama Barang",
                    "Satuan",
                    "Hrg.Retur(Rp)",
                    "Jml.Retur",
                    "SubtotalRetur(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        

        NoRetur.setDocument(new batasInput((byte)8).getKata(NoRetur));
        NoFaktur.setDocument(new batasInput((byte)15).getKata(NoFaktur));
        Kdptg.setDocument(new batasInput((byte)25).getKata(Kdptg));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        Kdbar.setDocument(new batasInput((byte)15).getKata(Kdbar));
        //Jmlbeli.setDocument(new batasInput((byte)13).getOnlyAngka(Jmlbeli));
        Jmlretur.setDocument(new batasInput((byte)13).getKata(Jmlretur));
        Hargaretur.setDocument(new batasInput((byte)13).getOnlyAngka(Hargaretur));
        
        form.barang.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Kdbar.setText(form.barang.getTextField().getText());
                    ResultSet rs=koneksi.createStatement().executeQuery(
                                 "select nama_brng,kode_sat,h_distributor,stok from databarang where kode_brng='"+Kdbar.getText()+"'");
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        satuanretur.setText(rs.getString(2));
                        Hargaretur.setText(rs.getString(3));
                    }
                } catch (SQLException ex) {
                    System.out.println("error barang : "+ex);
                }
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
        
        form.satuan.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                satuanretur.setText(form.satuan.getTextField().getText());
                isHitung();
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
        
        form.petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                Kdptg.setText(form.petugas.getTextField().getText());
                Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'",Nmptg);
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
        
        suplier.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdsup.setText(suplier.getTextField().getText());
                Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier='"+kdsup.getText()+"'", nmsup);
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
        
        bangsal.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                    kdgudang.setText(bangsal.getTextField().getText());
                    Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal='"+kdgudang.getText()+"'",nmgudang);
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
        
        Jmlretur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        Hargaretur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_retur_beli,6),signed)),0) from returbeli ","RB",6,NoRetur); 

        
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
        Popup = new javax.swing.JPopupMenu();
        ppTambah = new javax.swing.JMenuItem();
        ppHapus = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label25 = new widget.Label();
        Subtotal = new widget.TextBox();
        label27 = new widget.Label();
        Hargaretur = new widget.TextBox();
        label17 = new widget.Label();
        Kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        BtnBrg = new widget.Button();
        BtnKnv = new widget.Button();
        satuanretur = new widget.TextBox();
        Jmlretur = new widget.TextBox();
        label26 = new widget.Label();
        Satuanbar = new widget.TextBox();
        label21 = new widget.Label();
        panelisiBeli = new widget.panelisi();
        label15 = new widget.Label();
        NoRetur = new widget.TextBox();
        label18 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label13 = new widget.Label();
        Kdptg = new widget.TextBox();
        Nmptg = new widget.TextBox();
        BtnPtg = new widget.Button();
        label11 = new widget.Label();
        TglRetur = new widget.Tanggal();
        label16 = new widget.Label();
        kdsup = new widget.TextBox();
        nmsup = new widget.TextBox();
        BtnSpl = new widget.Button();
        label32 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppTambah.setBackground(new java.awt.Color(242, 242, 242));
        ppTambah.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppTambah.setForeground(new java.awt.Color(102, 51, 0));
        ppTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        ppTambah.setText("Tambah");
        ppTambah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTambah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTambah.setIconTextGap(8);
        ppTambah.setName("ppTambah"); // NOI18N
        ppTambah.setPreferredSize(new java.awt.Dimension(150, 25));
        ppTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        Popup.add(ppTambah);

        ppHapus.setBackground(new java.awt.Color(242, 242, 242));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(102, 51, 0));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppHapus.setText("Hapus");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setIconTextGap(8);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        Popup.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Retur Ke Suplier ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setComponentPopupMenu(Popup);
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
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 130));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('T');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+T");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTambah);

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
        panelisi1.add(BtnHapus);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LTotal);

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
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Clear");
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
        panelisi1.add(BtnBatal);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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

        label25.setText("SubTotal :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label25);
        label25.setBounds(572, 40, 80, 23);

        Subtotal.setEditable(false);
        Subtotal.setName("Subtotal"); // NOI18N
        Subtotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Subtotal);
        Subtotal.setBounds(656, 40, 117, 23);

        label27.setText("Hrga.Retur :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label27);
        label27.setBounds(572, 10, 80, 23);

        Hargaretur.setName("Hargaretur"); // NOI18N
        Hargaretur.setPreferredSize(new java.awt.Dimension(80, 23));
        Hargaretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargareturKeyPressed(evt);
            }
        });
        panelisi4.add(Hargaretur);
        Hargaretur.setBounds(656, 10, 117, 23);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 63, 23);

        Kdbar.setName("Kdbar"); // NOI18N
        Kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdbarKeyPressed(evt);
            }
        });
        panelisi4.add(Kdbar);
        Kdbar.setBounds(67, 10, 120, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(189, 10, 325, 23);

        BtnBrg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBrg.setMnemonic('3');
        BtnBrg.setToolTipText("Alt+3");
        BtnBrg.setName("BtnBrg"); // NOI18N
        BtnBrg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBrgActionPerformed(evt);
            }
        });
        panelisi4.add(BtnBrg);
        BtnBrg.setBounds(516, 10, 28, 23);

        BtnKnv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKnv.setMnemonic('4');
        BtnKnv.setToolTipText("Alt+4");
        BtnKnv.setName("BtnKnv"); // NOI18N
        BtnKnv.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKnvActionPerformed(evt);
            }
        });
        panelisi4.add(BtnKnv);
        BtnKnv.setBounds(516, 40, 28, 23);

        satuanretur.setName("satuanretur"); // NOI18N
        satuanretur.setPreferredSize(new java.awt.Dimension(80, 23));
        satuanretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                satuanreturKeyPressed(evt);
            }
        });
        panelisi4.add(satuanretur);
        satuanretur.setBounds(449, 40, 65, 23);

        Jmlretur.setName("Jmlretur"); // NOI18N
        Jmlretur.setPreferredSize(new java.awt.Dimension(80, 23));
        Jmlretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlreturKeyPressed(evt);
            }
        });
        panelisi4.add(Jmlretur);
        Jmlretur.setBounds(394, 40, 53, 23);

        label26.setText("Jml.Retur :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label26);
        label26.setBounds(320, 40, 70, 23);

        Satuanbar.setEditable(false);
        Satuanbar.setName("Satuanbar"); // NOI18N
        Satuanbar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Satuanbar);
        Satuanbar.setBounds(67, 40, 80, 23);

        label21.setText("Satuan :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 40, 63, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisiBeli.setName("panelisiBeli"); // NOI18N
        panelisiBeli.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisiBeli.setLayout(null);

        label15.setText("No.Retur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoRetur.setName("NoRetur"); // NOI18N
        NoRetur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoReturKeyPressed(evt);
            }
        });
        panelisiBeli.add(NoRetur);
        NoRetur.setBounds(79, 10, 110, 23);

        label18.setText("No.Faktur :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label18);
        label18.setBounds(0, 40, 75, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisiBeli.add(NoFaktur);
        NoFaktur.setBounds(79, 40, 110, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label13);
        label13.setBounds(310, 40, 100, 23);

        Kdptg.setName("Kdptg"); // NOI18N
        Kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdptgKeyPressed(evt);
            }
        });
        panelisiBeli.add(Kdptg);
        Kdptg.setBounds(414, 40, 105, 23);

        Nmptg.setEditable(false);
        Nmptg.setName("Nmptg"); // NOI18N
        Nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(Nmptg);
        Nmptg.setBounds(521, 40, 220, 23);

        BtnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg.setMnemonic('2');
        BtnPtg.setToolTipText("Alt+2");
        BtnPtg.setName("BtnPtg"); // NOI18N
        BtnPtg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtgActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnPtg);
        BtnPtg.setBounds(744, 40, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label11);
        label11.setBounds(0, 70, 75, 23);

        TglRetur.setEditable(false);
        TglRetur.setDisplayFormat("yyyy-MM-dd");
        TglRetur.setName("TglRetur"); // NOI18N
        TglRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglReturKeyPressed(evt);
            }
        });
        panelisiBeli.add(TglRetur);
        TglRetur.setBounds(79, 70, 110, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label16);
        label16.setBounds(310, 10, 100, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisiBeli.add(kdsup);
        kdsup.setBounds(414, 10, 105, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(nmsup);
        nmsup.setBounds(521, 10, 220, 23);

        BtnSpl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSpl.setMnemonic('1');
        BtnSpl.setToolTipText("Alt+1");
        BtnSpl.setName("BtnSpl"); // NOI18N
        BtnSpl.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSplActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnSpl);
        BtnSpl.setBounds(744, 10, 28, 23);

        label32.setText("Lokasi :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label32);
        label32.setBounds(310, 70, 100, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisiBeli.add(kdgudang);
        kdgudang.setBounds(414, 70, 105, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(nmgudang);
        nmgudang.setBounds(521, 70, 220, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnGudang);
        BtnGudang.setBounds(744, 70, 28, 23);

        internalFrame1.add(panelisiBeli, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(nmbar.getText().trim().equals("")){
            Valid.textKosong(Kdbar,"barang");
        }else if(Jmlretur.getText().trim().equals("")){
            Valid.textKosong(Jmlretur,"jumlah retur");
        }else if(Jmlretur.getText().trim().equals("0")){
            Valid.textKosong(Jmlretur,"jumlah retur");
        }else if(Hargaretur.getText().trim().equals("")){
            Valid.textKosong(Hargaretur,"harga retur");
        }else{
            Sequel.menyimpan("tampreturbeli","'"+NoFaktur.getText()+"','"+Kdbar.getText()+"','"+nmbar.getText()+"','"+Satuanbar.getText()+
                             "','0','0','"+Hargaretur.getText()+"','"+Jmlretur.getText()+"','"+Subtotal.getText()+"'",
                             "nama_brng='"+nmbar.getText()+"',satuan='"+Satuanbar.getText()+"',h_beli='0',jml_beli='0',h_retur='"+
                              Hargaretur.getText()+"',jml_retur='"+Jmlretur.getText()+"',total='"+Subtotal.getText()+"'",
                             "kode_brng='"+Kdbar.getText()+"' and no_faktur='"+NoFaktur.getText()+"'");
            emptTeks();            
            tampil();
        }
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnTambahActionPerformed(null);
        }else{
            Valid.pindah(evt,Hargaretur, BtnHapus);
        }
}//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(nmbar.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,Kdbar," tampreturbeli","no_faktur='"+NoFaktur.getText()+"' and kode_brng");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnCari);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.tampil();
        form.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnBatal,Kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        var.setStatus(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.petugas.emptTeks();
        form.petugas.tampil();
        form.petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPtgActionPerformed

    private void TglReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglReturKeyPressed
        Valid.pindah(evt,NoRetur,Kdptg);
    }//GEN-LAST:event_TglReturKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRetur.getText().trim().equals("")){
            Valid.textKosong(NoRetur,"No.Retur");
        }else if(Nmptg.getText().trim().equals("")){
            Valid.textKosong(Kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Suplier");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            Kdbar.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    koneksi.createStatement().execute(
                        "insert into returbeli values('"+NoRetur.getText()+"','"+TglRetur.getSelectedItem()+"','"+Kdptg.getText()+"','"+kdsup.getText()+"','"+kdgudang.getText()+"')");
                    ResultSet rs=koneksi.createStatement().executeQuery(
                        "select no_faktur, kode_brng, satuan, h_beli, jml_beli, h_retur, jml_retur, total from tampreturbeli");
                    while(rs.next()){
                        koneksi.createStatement().execute(
                            "insert into detreturbeli values('"+NoRetur.getText()+"','"+rs.getString(1) +"','"+rs.getString(2) +"','"+rs.getString(3) +
                            "','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"','"+rs.getString(7)+"','"+rs.getString(8)+"')");
                        Sequel.menyimpan("gudangbarang","'"+rs.getString(2)+"','"+kdgudang.getText()+"','-"+rs.getString(7)+"'", 
                                        "stok=stok-'"+rs.getString(7)+"'","kode_brng='"+rs.getString(2)+"' and kd_bangsal='"+kdgudang.getText()+"'");
                    }
                    
                    ResultSet rs2=koneksi.createStatement().executeQuery("select * from rekening where kd_rek='54000'");
                    if(!rs2.next()){
                        koneksi.createStatement().execute("insert into rekening values('54000','RETUR PEMBELIAN','R','K')");
                    }
                    
                    ResultSet rs3=koneksi.createStatement().executeQuery("select * from rekening where kd_rek='11120'");
                    if(!rs3.next()){
                        koneksi.createStatement().execute("insert into rekening values('11120','KAS DI TANGAN','N','D')");
                    }
                    
                    ResultSet rs4=koneksi.createStatement().executeQuery("select * from rekeningtahun where thn='"+TglRetur.getSelectedItem().toString().substring(0,4) +"' and kd_rek='54000'");
                    if(!rs4.next()){
                        koneksi.createStatement().execute("insert into rekeningtahun values('"+TglRetur.getSelectedItem().toString().substring(0,4) +"','54000','0')");
                    }
                    
                    ResultSet rs5=koneksi.createStatement().executeQuery("select * from rekeningtahun where thn='"+TglRetur.getSelectedItem().toString().substring(0,4) +"' and kd_rek='11120'");
                    if(!rs5.next()){
                        koneksi.createStatement().execute("insert into rekeningtahun values('"+TglRetur.getSelectedItem().toString().substring(0,4) +"','11120','0')");
                    }
                    
                    Sequel.menyimpan("tampjurnal","'54000','RETUR PEMBELIAN','0','"+ttlretur+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','"+ttlretur+"','0'","Rekening"); 
                    jur.simpanJurnal(NoRetur.getText(),TglRetur.getSelectedItem().toString(),"U","RETUR PEMBELIAN DI "+nmgudang.getText().toUpperCase());
                    
                    BtnBatalActionPerformed(evt);                
                } catch (SQLException ex) {
                    System.out.println(ex);
                    int konfirm = JOptionPane.showConfirmDialog(rootPane,"No.Faktur sudah ada sebelumnya,\napa data mau ditambahkan ke returbeli di No.Faktur tersebut..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if (konfirm == JOptionPane.YES_OPTION) {
                        try {
                            ResultSet rs=koneksi.createStatement().executeQuery(
                                "select no_faktur, kode_brng, satuan, h_beli, jml_beli, h_retur, jml_retur, total from tampreturbeli");
                            while(rs.next()){
                                koneksi.createStatement().execute(
                                    "insert into detreturbeli values('"+NoRetur.getText()+"','"+rs.getString(1) +"','"+rs.getString(2) +"','"+rs.getString(3) +
                                    "','"+rs.getString(4)+"','"+rs.getString(5)+",'"+rs.getString(6)+",'"+rs.getString(7)+",'"+rs.getString(8)+"')");
                                Sequel.menyimpan("gudangbarang","'"+rs.getString(2)+"','"+kdgudang.getText()+"','-"+rs.getString(7)+"'", 
                                        "stok=stok-'"+rs.getString(7)+"'","kode_brng='"+rs.getString(2)+"' and kd_bangsal='"+kdgudang.getText()+"'");
                            }
                    
                            ResultSet rs2=koneksi.createStatement().executeQuery("select * from rekening where kd_rek='54000'");
                            if(!rs2.next()){
                                koneksi.createStatement().execute("insert into rekening values('54000','RETUR PEMBELIAN','R','K')");
                            }
                    
                            ResultSet rs3=koneksi.createStatement().executeQuery("select * from rekening where kd_rek='11120'");
                            if(!rs3.next()){
                                koneksi.createStatement().execute("insert into rekening values('11120','KAS DI TANGAN','N','D')");
                            }
                    
                            ResultSet rs4=koneksi.createStatement().executeQuery("select * from rekeningtahun where thn='"+TglRetur.getSelectedItem().toString().substring(0,4) +"' and kd_rek='54000'");
                            if(!rs4.next()){
                                koneksi.createStatement().execute("insert into rekeningtahun values('"+TglRetur.getSelectedItem().toString().substring(0,4) +"','54000','0')");
                            }
                    
                            ResultSet rs5=koneksi.createStatement().executeQuery("select * from rekeningtahun where thn='"+TglRetur.getSelectedItem().toString().substring(0,4) +"' and kd_rek='11120'");
                            if(!rs5.next()){
                                koneksi.createStatement().execute("insert into rekeningtahun values('"+TglRetur.getSelectedItem().toString().substring(0,4) +"','11120','0')");
                            }
                    
                            Sequel.menyimpan("tampjurnal","'54000','RETUR PEMBELIAN','0','"+ttlretur+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','"+ttlretur+"','0'","Rekening"); 
                            jur.simpanJurnal(NoRetur.getText(),TglRetur.getSelectedItem().toString(),"U","RETUR PEMBELIAN DI "+nmgudang.getText().toUpperCase());
                    
                            BtnBatalActionPerformed(evt); 
                        }catch (SQLException exc) {
                            System.out.println(exc);
                        }                        
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Kdbar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Sequel.queryu("delete from  tampreturbeli");
        tampil();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_retur_beli,6),signed)),0) from returbeli ","RB",6,NoRetur); 
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NoReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoReturKeyPressed
        Valid.pindah(evt, BtnTambah, TglRetur);
    }//GEN-LAST:event_NoReturKeyPressed

    private void KdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'", Nmptg);         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'", Nmptg);
            TglRetur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'", Nmptg);
            NoFaktur.requestFocus();   
        }
    }//GEN-LAST:event_KdptgKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, Kdbar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
            Valid.pindah(evt, Kdptg, Jmlretur);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void HargareturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargareturKeyPressed
        Valid.pindah(evt, Jmlretur, BtnTambah);
    }//GEN-LAST:event_HargareturKeyPressed

private void KdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            try {
                    ResultSet rs=koneksi.createStatement().executeQuery(
                                 "select nama_brng,kode_sat,h_distributor,stok from databarang where kode_brng='"+Kdbar.getText()+"'");
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        satuanretur.setText(rs.getString(2));
                        Hargaretur.setText(rs.getString(3));
                    }
            } catch (SQLException ex) {
                    System.out.println("error barang : "+ex);
            }           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            try {
                    ResultSet rs=koneksi.createStatement().executeQuery(
                                 "select nama_brng,kode_sat,h_distributor,stok from databarang where kode_brng='"+Kdbar.getText()+"'");
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        satuanretur.setText(rs.getString(2));
                        Hargaretur.setText(rs.getString(3));
                    }
            } catch (SQLException ex) {
                    System.out.println("error barang : "+ex);
            }
            Kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                    ResultSet rs=koneksi.createStatement().executeQuery(
                                 "select nama_brng,kode_sat,h_distributor,stok from databarang where kode_brng='"+Kdbar.getText()+"'");
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        satuanretur.setText(rs.getString(2));
                        Hargaretur.setText(rs.getString(3));
                    }
                    Jmlretur.requestFocus(); 
            } catch (SQLException ex) {
                    System.out.println("error barang : "+ex);
            }
        }
}//GEN-LAST:event_KdbarKeyPressed

private void BtnBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBrgActionPerformed
        var.setStatus(true);
        form.barang.emptTeks();
        form.barang.tampil(" order by databarang.nama_brng");
        form.barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.barang.setLocationRelativeTo(internalFrame1);
        form.barang.setAlwaysOnTop(false);
        form.barang.setVisible(true);
}//GEN-LAST:event_BtnBrgActionPerformed

private void BtnKnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKnvActionPerformed
        var.setStatus(true);
        form.satuan.emptTeks();
        form.satuan.tampil(" order by kode_sat");
        form.satuan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.satuan.setLocationRelativeTo(internalFrame1);
        form.satuan.setAlwaysOnTop(false);
        form.satuan.setVisible(true);
}//GEN-LAST:event_BtnKnvActionPerformed

private void satuanreturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_satuanreturKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isHitung();         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isHitung();
            Jmlretur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isHitung();
            BtnSimpan.requestFocus();   
        }
}//GEN-LAST:event_satuanreturKeyPressed

private void JmlreturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmlreturKeyPressed
        Valid.pindah(evt, NoFaktur,Hargaretur);
}//GEN-LAST:event_JmlreturKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier='"+kdsup.getText()+"'", nmsup);         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier='"+kdsup.getText()+"'", nmsup);
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier='"+kdsup.getText()+"'", nmsup);
            Kdptg.requestFocus();   
        }
}//GEN-LAST:event_kdsupKeyPressed

private void BtnSplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSplActionPerformed
        var.setStatus(true);
        suplier.emptTeks();
        suplier.tampil();
        suplier.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
}//GEN-LAST:event_BtnSplActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal='"+kdgudang.getText()+"'",nmgudang);
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal='"+kdgudang.getText()+"'",nmgudang);
        Kdptg.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal='"+kdgudang.getText()+"'",nmgudang);      
        BtnSimpan.requestFocus(); 
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    bangsal.tampil();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgReturBeli dialog = new DlgReturBeli(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnBrg;
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKnv;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnSpl;
    private widget.Button BtnTambah;
    private widget.TextBox Hargaretur;
    private widget.TextBox Jmlretur;
    private widget.TextBox Kd2;
    private widget.TextBox Kdbar;
    private widget.TextBox Kdptg;
    private widget.Label LTotal;
    private widget.TextBox Nmptg;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoRetur;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox Satuanbar;
    private widget.TextBox Subtotal;
    private widget.Tanggal TglRetur;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdsup;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label32;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgudang;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisiBeli;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppTambah;
    private widget.TextBox satuanretur;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String sql="select  no_faktur, kode_brng, nama_brng, satuan, h_beli, jml_beli, h_retur, jml_retur, total from  tampreturbeli ";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
       Valid.tabelKosong(tabMode);
        try{
            java.sql.Statement stat=koneksi.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            ttlretur=0;
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               df2.format(rs.getDouble(7)),
                               rs.getString(8),
                               df2.format(rs.getDouble(9))};
                ttlretur=ttlretur+rs.getDouble(9);
                tabMode.addRow(data);
            }                
            LTotal.setText(df2.format(ttlretur));
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }

    public void emptTeks() {
        Kdbar.setText("");
        nmbar.setText("");
        Satuanbar.setText("");
        satuanretur.setText("");
        Hargaretur.setText("0");
        Jmlretur.setText("0");
        Subtotal.setText("0");
        Kdbar.requestFocus();        
    }

    private void getData() {
       int row=tbDokter.getSelectedRow();
        if(row!= -1){
             NoFaktur.setText(tabMode.getValueAt(row,0).toString());
             Kdbar.setText(tabMode.getValueAt(row,1).toString());
             nmbar.setText(tabMode.getValueAt(row,2).toString());
             Satuanbar.setText(tabMode.getValueAt(row,3).toString());
             Hargaretur.setText(tabMode.getValueAt(row,4).toString());
             Jmlretur.setText(tabMode.getValueAt(row,5).toString());
             isHitung();
        }
    }
   
    
    private void isHitung(){
        if((!Jmlretur.getText().equals(""))&&(!Hargaretur.getText().equals(""))){
            if(satuanretur.getText().equals("")||satuanretur.getText().equals(Satuanbar.getText())){
                Subtotal.setText(Double.toString(Double.parseDouble(Jmlretur.getText())*Double.parseDouble(Hargaretur.getText())));
            }else if(!satuanretur.getText().equals(Satuanbar.getText())){
                try {
                    ResultSet rs=koneksi.createStatement().executeQuery(
                            "select nilai,nilai_konversi from konver_sat where kode_sat='"+Satuanbar.getText()+"' and sat_konversi='"+satuanretur.getText()+"'");
                    if(rs.next()){
                        Subtotal.setText(Double.toString(Double.parseDouble(Jmlretur.getText())*Double.parseDouble(Hargaretur.getText())*(rs.getDouble(1)/rs.getDouble(2))));
                    }else if(!rs.next()){
                        int reply = JOptionPane.showConfirmDialog(rootPane,"Maaf, konversi satuan tidak ditemukan. Apa anda ingin menambahkan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgKonversi formkonversi=new DlgKonversi(null,false);
                            formkonversi.emptTeks();
                            formkonversi.tampil(" order by konver_sat.kode_sat");
                            formkonversi.setSize(internalFrame1.getWidth()-40, internalFrame1.getHeight()-40);
                            formkonversi.setLocationRelativeTo(internalFrame1);
                            formkonversi.setAlwaysOnTop(false);
                            formkonversi.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }                            
                    }                    
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }            
        }else{
            Subtotal.setText("0");
        }          
    }
    
    public void isCek(){
        if(var.getjml2()>=1){
            Kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            BtnSimpan.setEnabled(var.getreturbeli());
            BtnTambah.setEnabled(var.getreturbeli());
            BtnHapus.setEnabled(var.getreturbeli());
            BtnBatal.setEnabled(var.getreturbeli());
            Kdptg.setText(var.getkode());
            Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'", Nmptg);
        }        
    }

 
}
