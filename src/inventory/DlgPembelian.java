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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import khanzahms.DlgCariBangsal;

public class DlgPembelian extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgCariPembelian form=new DlgCariPembelian(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private DecimalFormat df3 = new DecimalFormat("###");
    private double saldoawal=0,mutasi=0,kas=0,ttl=0,y=0,w=0,ttldisk=0,sbttl=0,ppn=0,tagihan=0;
    private int jml=0;
    private String[] kodebarang,namabarang,satuan,harga,jumlah,subtotal,diskon,besardiskon,jmltotal;

    /** Creates new form DlgProgramStudi */
    public DlgPembelian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Jumlah",
                    "Kode Barang",
                    "Nama Barang",
                    "Satuan",
                    "Hrg.Beli(Rp)",
                    "Subtotal Beli(Rp)",
                    "Diskon(%)",
                    "Diskon(Rp)",
                    "Ttl.Beli"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)||(colIndex==6)) {
                    a=true;
                }
                return a;
             }
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
                column.setPreferredWidth(350);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)15).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
       
        
        form.suplier.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {         
                kdsup.setText(form.suplier.getTextField().getText());
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
        form.petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdptg.setText(form.petugas.getTextField().getText());
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
        
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                tampil();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tampil();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tampil();
            }

        });
        
        bangsal.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                    kdgudang.setText(bangsal.getTextField().getText());
                    Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
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
        try{            
            ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_sat, databarang.h_beli "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " where databarang.kode_brng like ? or "+
                " databarang.nama_brng like ? or "+
                " jenis.nama like ? order by databarang.nama_brng");
        }catch(SQLException e){
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

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        label9 = new widget.Label();
        LSubtotal = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        label12 = new widget.Label();
        LPotongan = new widget.Label();
        LTotal2 = new widget.Label();
        label14 = new widget.Label();
        label17 = new widget.Label();
        LPpn = new widget.Label();
        label19 = new widget.Label();
        LTagiha = new widget.Label();
        tppn = new widget.TextBox();
        label21 = new widget.Label();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli = new widget.Tanggal();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnSpl = new widget.Button();
        BtnPtg = new widget.Button();
        label18 = new widget.Label();
        SaldoKas = new widget.TextBox();
        label20 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Pembelian/Pengadaan Barang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 112));
        panelisi1.setLayout(null);

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
        BtnSimpan.setBounds(10, 62, 97, 30);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);
        label10.setBounds(110, 66, 75, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);
        TCari.setBounds(190, 66, 300, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);
        BtnCari1.setBounds(492, 66, 28, 23);

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setText("Potongan :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label9);
        label9.setBounds(130, 0, 90, 30);

        LSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LSubtotal.setText("0");
        LSubtotal.setName("LSubtotal"); // NOI18N
        LSubtotal.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LSubtotal);
        LSubtotal.setBounds(10, 20, 120, 30);

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
        BtnCari.setBounds(560, 62, 100, 30);

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
        BtnKeluar.setBounds(670, 62, 100, 30);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Total 1 :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label12);
        label12.setBounds(10, 0, 60, 30);

        LPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPotongan.setText("0");
        LPotongan.setName("LPotongan"); // NOI18N
        LPotongan.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LPotongan);
        LPotongan.setBounds(130, 20, 120, 30);

        LTotal2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal2.setText("0");
        LTotal2.setName("LTotal2"); // NOI18N
        LTotal2.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTotal2);
        LTotal2.setBounds(250, 20, 120, 30);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Total 2 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label14);
        label14.setBounds(250, 0, 90, 30);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("PPN :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label17);
        label17.setBounds(370, 0, 90, 30);

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LPpn);
        LPpn.setBounds(470, 20, 120, 30);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("Jumlah Tagihan :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label19);
        label19.setBounds(590, 0, 130, 30);

        LTagiha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTagiha.setText("0");
        LTagiha.setName("LTagiha"); // NOI18N
        LTagiha.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTagiha);
        LTagiha.setBounds(590, 20, 120, 30);

        tppn.setName("tppn"); // NOI18N
        tppn.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppnKeyPressed(evt);
            }
        });
        panelisi1.add(tppn);
        tppn.setBounds(370, 26, 60, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("%");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label21);
        label21.setBounds(433, 26, 40, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(79, 10, 110, 23);

        label11.setText("Tgl.Beli :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 70, 75, 23);

        TglBeli.setEditable(false);
        TglBeli.setDisplayFormat("yyyy-MM-dd");
        TglBeli.setName("TglBeli"); // NOI18N
        TglBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeliKeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli);
        TglBeli.setBounds(79, 70, 110, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 40, 100, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(409, 10, 80, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(305, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(409, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(491, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(491, 40, 240, 23);

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
        panelisi3.add(BtnSpl);
        BtnSpl.setBounds(734, 10, 28, 23);

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
        panelisi3.add(BtnPtg);
        BtnPtg.setBounds(734, 40, 28, 23);

        label18.setText("Saldo Kas :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 75, 23);

        SaldoKas.setEditable(false);
        SaldoKas.setName("SaldoKas"); // NOI18N
        SaldoKas.setPreferredSize(new java.awt.Dimension(207, 23));
        SaldoKas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaldoKasKeyPressed(evt);
            }
        });
        panelisi3.add(SaldoKas);
        SaldoKas.setBounds(79, 40, 110, 23);

        label20.setText("Lokasi :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label20);
        label20.setBounds(305, 70, 100, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(409, 70, 80, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(491, 70, 240, 23);

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
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(734, 70, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.emptTeks();        
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
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoFaktur.getText().trim().equals("")){
            Valid.textKosong(NoFaktur,"No.Faktur");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan pembelian...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    koneksiDB.condb().createStatement().execute(
                        "insert into pembelian values('"+NoFaktur.getText()+"','"+kdsup.getText()+"','"+kdptg.getText()+"','"+TglBeli.getSelectedItem()+
                            "','"+sbttl+"','"+ttldisk+"','"+ttl+"','"+ppn+"','"+(ttl+ppn)+"','"+kdgudang.getText()+"')");
                    int row=tabMode.getRowCount();
                    for(int i=0;i<row;i++){  
                         if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                                Sequel.menyimpan("detailbeli","'"+NoFaktur.getText()+"','"+
                                        tabMode.getValueAt(i,1).toString()+"','"+
                                        tabMode.getValueAt(i,3).toString()+"','"+
                                        tabMode.getValueAt(i,0).toString()+"','"+
                                        tabMode.getValueAt(i,4).toString()+"','"+
                                        tabMode.getValueAt(i,5).toString()+"','"+
                                        tabMode.getValueAt(i,6).toString()+"','"+
                                        tabMode.getValueAt(i,7).toString()+"','"+
                                        tabMode.getValueAt(i,8).toString()+"'","Transaksi Pembelian"); 
                                Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','"+tabMode.getValueAt(i,0).toString()+"'", 
                                        "stok=stok+'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");
                         }                
                    } 
                    ResultSet rs2=koneksiDB.condb().createStatement().executeQuery("select * from rekening where kd_rek='51000'");
                    if(!rs2.next()){
                        koneksiDB.condb().createStatement().execute("insert into rekening values('51000','PEMBELIAN','R','D')");
                    }
                    
                    ResultSet rs3=koneksiDB.condb().createStatement().executeQuery("select * from rekening where kd_rek='11120'");
                    if(!rs3.next()){
                        koneksiDB.condb().createStatement().execute("insert into rekening values('11120','KAS DI TANGAN','N','D')");
                    }
                    
                    ResultSet rs4=koneksiDB.condb().createStatement().executeQuery("select * from rekeningtahun where thn='"+TglBeli.getSelectedItem().toString().substring(0,4) +"' and kd_rek='51000'");
                    if(!rs4.next()){
                        koneksiDB.condb().createStatement().execute("insert into rekeningtahun values('"+TglBeli.getSelectedItem().toString().substring(0,4) +"','51000','0')");
                    }
                    
                    ResultSet rs5=koneksiDB.condb().createStatement().executeQuery("select * from rekeningtahun where thn='"+TglBeli.getSelectedItem().toString().substring(0,4) +"' and kd_rek='11120'");
                    if(!rs5.next()){
                        koneksiDB.condb().createStatement().execute("insert into rekeningtahun values('"+TglBeli.getSelectedItem().toString().substring(0,4) +"','11120','0')");
                    }
                    Sequel.queryu("delete from tampjurnal");
                    
                    Sequel.menyimpan("tampjurnal","'51000','PEMBELIAN','"+(ttl+ppn)+"','0'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','0','"+(ttl+ppn)+"'","Rekening"); 
                    jur.simpanJurnal(NoFaktur.getText(),TglBeli.getSelectedItem().toString(),"U","PEMBELIAN DI "+nmgudang.getText().toUpperCase());
                    
                } catch (SQLException ex) {
                    System.out.println(ex);
                    int konfirm = JOptionPane.showConfirmDialog(rootPane,"No.Nota sudah ada sebelumnya,\napa data mau ditambahkan ke pembelian di No.Nota tersebut..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if (konfirm == JOptionPane.YES_OPTION) {
                        try {
                            int row=tabMode.getRowCount();
                            for(int i=0;i<row;i++){  
                                 if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                                        Sequel.menyimpan("detailbeli","'"+NoFaktur.getText()+"','"+
                                                tabMode.getValueAt(i,1).toString()+"','"+
                                                tabMode.getValueAt(i,3).toString()+"','"+
                                                tabMode.getValueAt(i,0).toString()+"','"+
                                                tabMode.getValueAt(i,4).toString()+"','"+
                                                tabMode.getValueAt(i,5).toString()+"','"+
                                                tabMode.getValueAt(i,6).toString()+"','"+
                                                tabMode.getValueAt(i,7).toString()+"','"+
                                                tabMode.getValueAt(i,8).toString()+"'","Transaksi Pembelian"); 
                                        Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','"+tabMode.getValueAt(i,0).toString()+"'", 
                                                "stok=stok+'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");
                                 }                
                            } 
                            ResultSet rs2=koneksiDB.condb().createStatement().executeQuery("select * from rekening where kd_rek='51000'");
                            if(!rs2.next()){
                                koneksiDB.condb().createStatement().execute("insert into rekening values('51000','PEMBELIAN','R','D')");
                            }

                            ResultSet rs3=koneksiDB.condb().createStatement().executeQuery("select * from rekening where kd_rek='11120'");
                            if(!rs3.next()){
                                koneksiDB.condb().createStatement().execute("insert into rekening values('11120','KAS DI TANGAN','N','D')");
                            }

                            ResultSet rs4=koneksiDB.condb().createStatement().executeQuery("select * from rekeningtahun where thn='"+TglBeli.getSelectedItem().toString().substring(0,4) +"' and kd_rek='51000'");
                            if(!rs4.next()){
                                koneksiDB.condb().createStatement().execute("insert into rekeningtahun values('"+TglBeli.getSelectedItem().toString().substring(0,4) +"','51000','0')");
                            }

                            ResultSet rs5=koneksiDB.condb().createStatement().executeQuery("select * from rekeningtahun where thn='"+TglBeli.getSelectedItem().toString().substring(0,4) +"' and kd_rek='11120'");
                            if(!rs5.next()){
                                koneksiDB.condb().createStatement().execute("insert into rekeningtahun values('"+TglBeli.getSelectedItem().toString().substring(0,4) +"','11120','0')");
                            }
                            
                            Sequel.queryu("delete from tampjurnal");
                            Sequel.menyimpan("tampjurnal","'51000','PEMBELIAN','"+(ttl+ppn)+"','0'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','0','"+(ttl+ppn)+"'","Rekening"); 
                            jur.simpanJurnal(NoFaktur.getText(),TglBeli.getSelectedItem().toString(),"U","PEMBELIAN DI "+nmgudang.getText().toUpperCase());
                        } catch (SQLException ex1) {
                            System.out.println(ex1);
                        }
                    }
                }                
                int row2=tabMode.getRowCount();
                for(int r=0;r<row2;r++){ 
                    tabMode.setValueAt("",r,0);
                    tabMode.setValueAt("0",r,5);
                    tabMode.setValueAt("0",r,6);
                    tabMode.setValueAt("0",r,7);
                    tabMode.setValueAt("0",r,8);
                }
                getData();
        
                Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_faktur,6),signed)),0) from pembelian ","PB",6,NoFaktur); 
            }
        }        
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                int row=tbDokter.getSelectedColumn();
                if((row==1)||(row==4)||(row==6)){                        
                    getData();
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {                  
                   int row=tbDokter.getSelectedColumn();
                   if((row==1)||(row==4)||(row==6)){                       
                        getData();  
                        TCari.setText("");
                        TCari.requestFocus();
                   }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbDokter.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoFakturKeyPressed

private void TglBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeliKeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
}//GEN-LAST:event_TglBeliKeyPressed

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

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_krywn from datakaryawan where kode_krywn=?", nmptg,kdptg.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_krywn from datakaryawan where kode_krywn=?", nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_krywn from datakaryawan where kode_krywn=?", nmptg,kdptg.getText());
            kdgudang.requestFocus();  
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnSplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSplActionPerformed
        var.setStatus(true);
        form.suplier.emptTeks();
        form.suplier.tampil();
        form.suplier.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.suplier.setLocationRelativeTo(internalFrame1);
        form.suplier.setAlwaysOnTop(false);
        form.suplier.setVisible(true);
}//GEN-LAST:event_BtnSplActionPerformed

private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        form.petugas.emptTeks();
        form.petugas.tampil();
        form.petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
}//GEN-LAST:event_BtnPtgActionPerformed

private void SaldoKasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaldoKasKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_SaldoKasKeyPressed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());        
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            BtnSimpan.requestFocus();    
        }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        var.setStatus(true);
        bangsal.tampil();
        bangsal.emptTeks();        
        bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

private void tppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppnKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       getData();
   }
}//GEN-LAST:event_tppnKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPembelian dialog = new DlgPembelian(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnGudang;
    private widget.Button BtnKeluar;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnSpl;
    private widget.TextBox Kd2;
    private widget.Label LPotongan;
    private widget.Label LPpn;
    private widget.Label LSubtotal;
    private widget.Label LTagiha;
    private widget.Label LTotal2;
    private widget.TextBox NoFaktur;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox SaldoKas;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.TextBox tppn;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
      int row=tabMode.getRowCount();
        jml=0;
        for(int i=0;i<row;i++){
            if(!tabMode.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        harga=new String[jml];
        jumlah=new String[jml];
        subtotal=new String[jml];
        diskon=new String[jml];
        besardiskon=new String[jml];
        jmltotal=new String[jml];
        int index=0;        
        for(int i=0;i<row;i++){
            if(!tabMode.getValueAt(i,0).toString().equals("")){
                jumlah[index]=tabMode.getValueAt(i,0).toString();
                kodebarang[index]=tabMode.getValueAt(i,1).toString();
                namabarang[index]=tabMode.getValueAt(i,2).toString();
                satuan[index]=tabMode.getValueAt(i,3).toString();
                harga[index]=tabMode.getValueAt(i,4).toString();
                subtotal[index]=tabMode.getValueAt(i,5).toString();
                diskon[index]=tabMode.getValueAt(i,6).toString();
                besardiskon[index]=tabMode.getValueAt(i,7).toString();
                jmltotal[index]=tabMode.getValueAt(i,8).toString();
                index++;
            }
        }
        Valid.tabelKosong(tabMode);
        for(int i=0;i<jml;i++){
            String[] data={jumlah[i],kodebarang[i],namabarang[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i]};
            tabMode.addRow(data);
        }
        try{
            ps.setString(1,"%"+TCari.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{"",rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),"0","0","0","0"});
            }                 
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }

    private void getData(){
        int row=tbDokter.getSelectedRow();
        if(row!= -1){            
            if(!tabMode.getValueAt(row,0).toString().equals("")){
                double jmlbeli=Double.parseDouble(tabMode.getValueAt(row,0).toString());
                double hargabeli=Double.parseDouble(tabMode.getValueAt(row,4).toString());
                double subttl=jmlbeli*hargabeli;
                tabMode.setValueAt(Valid.SetAngka2(subttl), row,5);
                double dis=Double.parseDouble(tabMode.getValueAt(row,6).toString());
                double besardis=subttl*(dis/100);
                tabMode.setValueAt(Valid.SetAngka2(besardis), row,7);
                double ttlbeli=subttl-besardis;
               tabMode.setValueAt(Valid.SetAngka2(ttlbeli), row,8);
             }
            
            ttl=0;sbttl=0;ppn=0;ttldisk=0;
            y=0;w=0;
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){                 
                if(!tabMode.getValueAt(r,5).toString().isEmpty()){
                    w=Double.parseDouble(tabMode.getValueAt(r,5).toString());                
                }else if(tabMode.getValueAt(r,5).toString().isEmpty()){
                    w=0;                
                }
                sbttl=sbttl+w;                
                if(!tabMode.getValueAt(r,7).toString().isEmpty()){
                    y=Double.parseDouble(tabMode.getValueAt(r,7).toString());                
                }else if(tabMode.getValueAt(r,7).toString().isEmpty()){
                    y=0;                
                }
                ttldisk=ttldisk+y;
            }
            LSubtotal.setText(Valid.SetAngka(sbttl));
            LPotongan.setText(Valid.SetAngka(ttldisk));
            ttl=sbttl-ttldisk;
            LTotal2.setText(Valid.SetAngka(ttl));
            ppn=0;
            if(!tppn.getText().equals("")){
                ppn=(Double.parseDouble(tppn.getText())/100) *(ttl);
                LPpn.setText(Valid.SetAngka(ppn));
                LTagiha.setText(Valid.SetAngka(ttl+ppn));
            }
            
        }
    }
    
        
    private void isSaldo(){
        /*Sequel.cariIsi("select (rekeningtahun.saldo_awal+(sum(detailjurnal.debet)-sum(detailjurnal.kredit))) "+
                       "from jurnal inner join detailjurnal inner join rekeningtahun "+
                       " on detailjurnal.no_jurnal=jurnal.no_jurnal "+
                       " and detailjurnal.kd_rek=rekeningtahun.kd_rek where rekeningtahun.thn='"+TglBeli.getSelectedItem().toString().substring(0,4) +"' "+
                       " and detailjurnal.kd_rek='11120' and jurnal.tgl_jurnal like '%"+TglBeli.getSelectedItem().toString().substring(0,4) +"%' ",SaldoKas); */
        saldoawal=Sequel.cariIsiAngka("select rekeningtahun.saldo_awal "+
                       " from  rekeningtahun where rekeningtahun.thn='"+TglBeli.getSelectedItem().toString().substring(0,4) +"' "+
                       " and rekeningtahun.kd_rek='11120' ");
        mutasi=Sequel.cariIsiAngka("select (sum(detailjurnal.debet)-sum(detailjurnal.kredit)) "+
                       "from jurnal inner join detailjurnal "+
                       " on detailjurnal.no_jurnal=jurnal.no_jurnal "+
                       " where detailjurnal.kd_rek='11120' and jurnal.tgl_jurnal like '%"+TglBeli.getSelectedItem().toString().substring(0,4) +"%' ");
        kas=saldoawal+mutasi;
        SaldoKas.setText(df3.format(kas));
                
    }
    
    
    public void isCek(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_faktur,6),signed)),0) from pembelian ","PB",6,NoFaktur); 
        TCari.requestFocus();
        tppn.setText("10");
        isSaldo();
        if(var.getjml2()>=1){
            kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            kdptg.setText(var.getkode());
            BtnSimpan.setEnabled(var.getpembelian());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }        
    }

 
}
