package khanzahms;
import inventory.DlgCariJenis;
import inventory.DlgCariSatuan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgPindahGudang;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgBarang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private String sql="";
    public DlgCariJenis jenis=new DlgCariJenis(null,false);
    public DlgCariSatuan satuan=new DlgCariSatuan(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double x=0,y=0,z=0,totalstok,stokgudang;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Barang",
                    "Nama Barang",
                    "Kode Satuan",
                    "Nama Satuan",
                    "Letak Barang",
                    "Hrg.Beli(Rp)",
                    "Hrg.Rawat Jalan(Rp)",
                    "Hrg.Ranap JKM(Rp)",
                    "Hrg.Ranap Umum(Rp)",
                    "Stok",
                    "Kode Jenis",
                    "Nama Jenis",
                    "Kapasitas"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(75);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        Kd.setDocument(new batasInput((byte)15).getKata(Kd));
        Nm.setDocument(new batasInput((byte)50).getKata(Nm));        
        kdsat.setDocument(new batasInput((byte)3).getKata(kdsat)); 
        Letak.setDocument(new batasInput((byte)50).getKata(Letak));  
        hbeli.setDocument(new batasInput((byte)15).getOnlyAngka(hbeli)); 
        hdistributor.setDocument(new batasInput((byte)15).getOnlyAngka(hdistributor));    
        hgrosir.setDocument(new batasInput((byte)15).getOnlyAngka(hgrosir)); 
        hretail.setDocument(new batasInput((byte)15).getOnlyAngka(hretail)); 
        Kapasitas.setDocument(new batasInput((byte)15).getOnlyAngka(hretail)); 
        stok.setDocument(new batasInput((byte)15).getKata(stok)); 
        kdjns.setDocument(new batasInput((byte)5).getKata(kdjns));        

        jenis.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdjns.setText(jenis.getTextField().getText());
                Sequel.cariIsi("select nama from jenis where kdjns='"+kdjns.getText()+"'", nmjns);
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
                Sequel.cariIsi("select satuan from kodesatuan where kode_sat='"+kdsat.getText()+"'", nmsat);
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
        
        hbeli.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        
        ChkInput.setSelected(false);
        isForm(); 
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
        ppGanti = new javax.swing.JMenuItem();
        ppHapus = new javax.swing.JMenuItem();
        ppCetak = new javax.swing.JMenuItem();
        ppBarcode = new javax.swing.JMenuItem();
        ppBarcodeItem = new javax.swing.JMenuItem();
        ppOrder = new javax.swing.JMenu();
        ppOrderKode = new javax.swing.JMenuItem();
        ppOrderNama = new javax.swing.JMenuItem();
        ppOrderSatuan = new javax.swing.JMenuItem();
        ppOrderLetak = new javax.swing.JMenuItem();
        ppOrderHBeli = new javax.swing.JMenuItem();
        ppOrderStok = new javax.swing.JMenuItem();
        ppOrderJenis = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        ppMutasi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        Nm = new widget.TextBox();
        label18 = new widget.Label();
        label26 = new widget.Label();
        hgrosir = new widget.TextBox();
        label28 = new widget.Label();
        hretail = new widget.TextBox();
        label31 = new widget.Label();
        Letak = new widget.TextBox();
        label27 = new widget.Label();
        hdistributor = new widget.TextBox();
        nmsat = new widget.TextBox();
        label19 = new widget.Label();
        kdsat = new widget.TextBox();
        BtnSatuan = new widget.Button();
        label14 = new widget.Label();
        stok = new widget.TextBox();
        label20 = new widget.Label();
        kdjns = new widget.TextBox();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label29 = new widget.Label();
        hbeli = new widget.TextBox();
        label32 = new widget.Label();
        Kapasitas = new widget.TextBox();
        ChkInput = new widget.CekBox();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppGanti.setBackground(new java.awt.Color(255, 255, 255));
        ppGanti.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGanti.setForeground(new java.awt.Color(102, 51, 0));
        ppGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppGanti.setText("Ganti");
        ppGanti.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGanti.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGanti.setIconTextGap(8);
        ppGanti.setName("ppGanti"); // NOI18N
        ppGanti.setPreferredSize(new java.awt.Dimension(150, 25));
        ppGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        Popup.add(ppGanti);

        ppHapus.setBackground(new java.awt.Color(255, 255, 255));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
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

        ppCetak.setBackground(new java.awt.Color(255, 255, 255));
        ppCetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCetak.setForeground(new java.awt.Color(102, 51, 0));
        ppCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppCetak.setText("Cetak");
        ppCetak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCetak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCetak.setIconTextGap(8);
        ppCetak.setName("ppCetak"); // NOI18N
        ppCetak.setPreferredSize(new java.awt.Dimension(150, 25));
        ppCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppCetak);

        ppBarcode.setBackground(new java.awt.Color(255, 255, 255));
        ppBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBarcode.setForeground(new java.awt.Color(102, 51, 0));
        ppBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppBarcode.setText("Barcode");
        ppBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBarcode.setIconTextGap(8);
        ppBarcode.setName("ppBarcode"); // NOI18N
        ppBarcode.setPreferredSize(new java.awt.Dimension(150, 25));
        ppBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBarcodeBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppBarcode);

        ppBarcodeItem.setBackground(new java.awt.Color(255, 255, 255));
        ppBarcodeItem.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBarcodeItem.setForeground(new java.awt.Color(102, 51, 0));
        ppBarcodeItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppBarcodeItem.setText("Barcode Perbarang");
        ppBarcodeItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBarcodeItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBarcodeItem.setIconTextGap(8);
        ppBarcodeItem.setName("ppBarcodeItem"); // NOI18N
        ppBarcodeItem.setPreferredSize(new java.awt.Dimension(150, 25));
        ppBarcodeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBarcodeItemBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppBarcodeItem);

        ppOrder.setBackground(new java.awt.Color(255, 255, 255));
        ppOrder.setForeground(new java.awt.Color(102, 51, 0));
        ppOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrder.setText("Urutkan Berdasar");
        ppOrder.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrder.setIconTextGap(8);
        ppOrder.setName("ppOrder"); // NOI18N

        ppOrderKode.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderKode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderKode.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderKode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderKode.setText("Kode Barang");
        ppOrderKode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderKode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderKode.setIconTextGap(8);
        ppOrderKode.setName("ppOrderKode"); // NOI18N
        ppOrderKode.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderKodeActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderKode);

        ppOrderNama.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderNama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderNama.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderNama.setText("Nama Barang");
        ppOrderNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderNama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderNama.setIconTextGap(8);
        ppOrderNama.setName("ppOrderNama"); // NOI18N
        ppOrderNama.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderNamaActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderNama);

        ppOrderSatuan.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderSatuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderSatuan.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderSatuan.setText("Satuan");
        ppOrderSatuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderSatuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderSatuan.setIconTextGap(8);
        ppOrderSatuan.setName("ppOrderSatuan"); // NOI18N
        ppOrderSatuan.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderSatuanActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderSatuan);

        ppOrderLetak.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderLetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderLetak.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderLetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderLetak.setText("Letak");
        ppOrderLetak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderLetak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderLetak.setIconTextGap(8);
        ppOrderLetak.setName("ppOrderLetak"); // NOI18N
        ppOrderLetak.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderLetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderLetakActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderLetak);

        ppOrderHBeli.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderHBeli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderHBeli.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderHBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderHBeli.setText("Harga Beli");
        ppOrderHBeli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderHBeli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderHBeli.setIconTextGap(8);
        ppOrderHBeli.setName("ppOrderHBeli"); // NOI18N
        ppOrderHBeli.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderHBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderHBeliActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderHBeli);

        ppOrderStok.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderStok.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderStok.setText("Stok");
        ppOrderStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderStok.setIconTextGap(8);
        ppOrderStok.setName("ppOrderStok"); // NOI18N
        ppOrderStok.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderStokActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderStok);

        ppOrderJenis.setBackground(new java.awt.Color(242, 242, 242));
        ppOrderJenis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderJenis.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderJenis.setText("Jenis");
        ppOrderJenis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderJenis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderJenis.setIconTextGap(8);
        ppOrderJenis.setName("ppOrderJenis"); // NOI18N
        ppOrderJenis.setPreferredSize(new java.awt.Dimension(150, 25));
        ppOrderJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderJenisActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderJenis);

        Popup.add(ppOrder);

        ppStok.setBackground(new java.awt.Color(255, 255, 255));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(102, 51, 0));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppStok.setText("Tampilkan Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setIconTextGap(8);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(150, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        ppMutasi.setBackground(new java.awt.Color(255, 255, 255));
        ppMutasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppMutasi.setForeground(new java.awt.Color(102, 51, 0));
        ppMutasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppMutasi.setText("Mutasi Antar Gudang");
        ppMutasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppMutasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppMutasi.setIconTextGap(8);
        ppMutasi.setName("ppMutasi"); // NOI18N
        ppMutasi.setPreferredSize(new java.awt.Dimension(150, 25));
        ppMutasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppMutasiBtnEditActionPerformed(evt);
            }
        });
        Popup.add(ppMutasi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat/Alkes/BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(400, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelisi2.add(BtnCari);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(LCount);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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
        panelisi1.add(BtnSimpan);

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
        panelisi1.add(BtnBatal);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelisi1.add(BtnEdit);

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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnAll);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 167));
        FormInput.setLayout(null);

        label12.setText("Kode Barang :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label12);
        label12.setBounds(8, 12, 88, 23);

        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(100, 12, 100, 23);

        Nm.setName("Nm"); // NOI18N
        Nm.setPreferredSize(new java.awt.Dimension(207, 23));
        Nm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKeyPressed(evt);
            }
        });
        FormInput.add(Nm);
        Nm.setBounds(100, 42, 380, 23);

        label18.setText("Nama Barang :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label18);
        label18.setBounds(8, 42, 88, 23);

        label26.setText("Ranap JKM : Rp.");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label26);
        label26.setBounds(497, 72, 116, 23);

        hgrosir.setName("hgrosir"); // NOI18N
        hgrosir.setPreferredSize(new java.awt.Dimension(207, 23));
        hgrosir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hgrosirMouseExited(evt);
            }
        });
        hgrosir.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                hgrosirMouseMoved(evt);
            }
        });
        hgrosir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hgrosirKeyPressed(evt);
            }
        });
        FormInput.add(hgrosir);
        hgrosir.setBounds(617, 72, 110, 23);

        label28.setText("Ranap Umum : Rp.");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label28);
        label28.setBounds(497, 102, 116, 23);

        hretail.setName("hretail"); // NOI18N
        hretail.setPreferredSize(new java.awt.Dimension(207, 23));
        hretail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hretailMouseExited(evt);
            }
        });
        hretail.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                hretailMouseMoved(evt);
            }
        });
        hretail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hretailKeyPressed(evt);
            }
        });
        FormInput.add(hretail);
        hretail.setBounds(617, 102, 110, 23);

        label31.setText("Letak Barang :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label31);
        label31.setBounds(8, 72, 88, 23);

        Letak.setName("Letak"); // NOI18N
        Letak.setPreferredSize(new java.awt.Dimension(207, 23));
        Letak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakKeyPressed(evt);
            }
        });
        FormInput.add(Letak);
        Letak.setBounds(100, 72, 220, 23);

        label27.setText("Rawat Jalan : Rp.");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label27);
        label27.setBounds(497, 42, 116, 23);

        hdistributor.setName("hdistributor"); // NOI18N
        hdistributor.setPreferredSize(new java.awt.Dimension(207, 23));
        hdistributor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hdistributorMouseExited(evt);
            }
        });
        hdistributor.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                hdistributorMouseMoved(evt);
            }
        });
        hdistributor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hdistributorKeyPressed(evt);
            }
        });
        FormInput.add(hdistributor);
        hdistributor.setBounds(617, 42, 110, 23);

        nmsat.setEditable(false);
        nmsat.setName("nmsat"); // NOI18N
        nmsat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmsat);
        nmsat.setBounds(192, 102, 260, 23);

        label19.setText("Satuan :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(8, 102, 88, 23);

        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(207, 23));
        kdsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsatKeyPressed(evt);
            }
        });
        FormInput.add(kdsat);
        kdsat.setBounds(100, 102, 90, 23);

        BtnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSatuan.setMnemonic('1');
        BtnSatuan.setToolTipText("Alt+1");
        BtnSatuan.setName("BtnSatuan"); // NOI18N
        BtnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSatuanActionPerformed(evt);
            }
        });
        FormInput.add(BtnSatuan);
        BtnSatuan.setBounds(455, 102, 25, 23);

        label14.setText("Stok Barang :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label14);
        label14.setBounds(497, 132, 116, 23);

        stok.setEditable(false);
        stok.setHighlighter(null);
        stok.setName("stok"); // NOI18N
        stok.setSelectionColor(new java.awt.Color(255, 255, 255));
        stok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stokMouseExited(evt);
            }
        });
        stok.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                stokMouseMoved(evt);
            }
        });
        stok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stokKeyPressed(evt);
            }
        });
        FormInput.add(stok);
        stok.setBounds(617, 132, 90, 23);

        label20.setText("Jenis Barang :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(8, 132, 88, 23);

        kdjns.setName("kdjns"); // NOI18N
        kdjns.setPreferredSize(new java.awt.Dimension(207, 23));
        kdjns.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjnsKeyPressed(evt);
            }
        });
        FormInput.add(kdjns);
        kdjns.setBounds(100, 132, 90, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmjns);
        nmjns.setBounds(192, 132, 260, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        FormInput.add(BtnJenis);
        BtnJenis.setBounds(455, 132, 25, 23);

        label29.setText("Harga Beli : Rp.");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label29);
        label29.setBounds(497, 12, 116, 23);

        hbeli.setName("hbeli"); // NOI18N
        hbeli.setPreferredSize(new java.awt.Dimension(207, 23));
        hbeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hbeliMouseExited(evt);
            }
        });
        hbeli.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                hbeliMouseMoved(evt);
            }
        });
        hbeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hbeliKeyPressed(evt);
            }
        });
        FormInput.add(hbeli);
        hbeli.setBounds(617, 12, 110, 23);

        label32.setText("Kapasitas :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label32);
        label32.setBounds(328, 72, 70, 23);

        Kapasitas.setName("Kapasitas"); // NOI18N
        Kapasitas.setPreferredSize(new java.awt.Dimension(207, 23));
        Kapasitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KapasitasKeyPressed(evt);
            }
        });
        FormInput.add(Kapasitas);
        Kapasitas.setBounds(400, 72, 80, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        tampil(" order by databarang.kode_brng ");
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if((var.getStatus()==true)&&(evt.getClickCount()==2)){
                var.setStatus(false);
                dispose();
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
            
            if((var.getStatus()==true)&&(evt.getKeyCode()==KeyEvent.VK_SPACE)){
                var.setStatus(false);
                dispose();
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void NmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKeyPressed
       Valid.pindah(evt,Kd,Letak);
}//GEN-LAST:event_NmKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(Nm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,Kd,"databarang","kode_brng");
            tampil("");
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"Kode Barang");
        }else if(Nm.getText().trim().equals("")){
            Valid.textKosong(Nm,"Nama Barang");
        }else if(Letak.getText().trim().equals("")){
            Valid.textKosong(Letak,"Letak Barang");
        }else if(hgrosir.getText().trim().equals("")){
            Valid.textKosong(hgrosir,"Harga Ranap JKMir");
        }else if(hbeli.getText().trim().equals("")){
            Valid.textKosong(hbeli,"Harga Beli");
        }else if(hretail.getText().trim().equals("")){
            Valid.textKosong(hretail,"Harga Ranap Umum");
        }else if(hdistributor.getText().trim().equals("")){
            Valid.textKosong(hdistributor,"Harga Rawat Jalan");
        }else if(kdsat.getText().trim().equals("")||nmsat.getText().trim().equals("")){
            Valid.textKosong(kdsat,"Satuan");
        }else if(kdjns.getText().trim().equals("")||nmjns.getText().trim().equals("")){
            Valid.textKosong(kdjns,"Jenis");
        }else if(stok.getText().trim().equals("")){
            Valid.textKosong(stok,"Stok");
        }else{
            Valid.editTable(tabMode,"databarang","kode_brng",Kd2,"nama_brng='"+
                    Nm.getText()+"',kode_brng='"+Kd.getText()+"',kapasitas='"+Kapasitas.getText()+"',kode_sat='"+
                    kdsat.getText()+"',letak_barang='"+
                    Letak.getText()+"',h_beli='"+
                    hbeli.getText()+"',h_distributor='"+
                    hdistributor.getText().replaceAll(",","")+"',h_grosir='"+
                    hgrosir.getText().replaceAll(",","")+"',h_retail='"+
                    hretail.getText().replaceAll(",","")+"',stok='"+
                    stok.getText()+"',kdjns='"+
                    kdjns.getText()+"'");
                    if(tabMode.getRowCount()!=0){tampil(" order by databarang.kode_brng ");}
                    emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
             if(TCari.getText().equals("")){
                sql="select databarang.kode_brng, databarang.nama_brng, "+
                    " databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang, databarang.h_beli,"+
                    " databarang.h_distributor, databarang.h_grosir, databarang.h_retail, databarang.stok, databarang.kdjns,"+
                    " jenis.nama,(databarang.stok*databarang.h_beli) as nomibeli,"+
                    " (databarang.stok*databarang.h_distributor) as nomijual,(databarang.stok*databarang.h_grosir) as nomiresep,"+
                    " (databarang.stok*databarang.h_retail) as nomiretail "+
                    " from databarang inner join kodesatuan inner join jenis "+
                    " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns order by databarang.kode_brng";
            }else if(!TCari.getText().equals("")){
                sql="select databarang.kode_brng, databarang.nama_brng, "+
                    " databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang, databarang.h_beli,"+
                    " databarang.h_distributor, databarang.h_grosir, databarang.h_retail, databarang.stok, databarang.kdjns,"+
                    " jenis.nama,(databarang.stok*databarang.h_beli) as nomibeli,"+
                    " (databarang.stok*databarang.h_distributor) as nomijual,(databarang.stok*databarang.h_grosir) as nomiresep,"+
                    " (databarang.stok*databarang.h_retail) as nomiretail "+
                    " from databarang inner join kodesatuan inner join jenis "+
                    " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "+
                    " where databarang.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.kode_sat like '%"+TCari.getText().trim()+"%' or "+
                    " kodesatuan.satuan like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.letak_barang like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.stok like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.kdjns like '%"+TCari.getText().trim()+"%' or "+
                    " jenis.nama like '%"+TCari.getText().trim()+"%'  order by databarang.kode_brng";
            }
            Valid.MyReport("rptBarang.jrxml","report","::[ Data Barang ]::",sql);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil(" order by databarang.kode_brng");
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"Kode Barang");
        }else if(Nm.getText().trim().equals("")){
            Valid.textKosong(Nm,"Nama Barang");
        }else if(Letak.getText().trim().equals("")){
            Valid.textKosong(Letak,"Letak Barang");
        }else if(hgrosir.getText().trim().equals("")){
            Valid.textKosong(hgrosir,"Harga Ranap JKMir");
        }else if(hbeli.getText().trim().equals("")){
            Valid.textKosong(hbeli,"Harga Beli");
        }else if(hretail.getText().trim().equals("")){
            Valid.textKosong(hretail,"Harga Ranap Umum");
        }else if(hdistributor.getText().trim().equals("")){
            Valid.textKosong(hdistributor,"Harga Rawat Jalan");
        }else if(kdsat.getText().trim().equals("")||nmsat.getText().trim().equals("")){
            Valid.textKosong(kdsat,"Satuan");
        }else if(kdjns.getText().trim().equals("")||nmjns.getText().trim().equals("")){
            Valid.textKosong(kdjns,"Jenis");
        }else if(stok.getText().trim().equals("")){
            Valid.textKosong(stok,"Stok");
        }else if(Kapasitas.getText().trim().equals("")){
            Valid.textKosong(Kapasitas,"Kapasitas");
        }else{
           Sequel.menyimpan("databarang","'"+Kd.getText()+"','"+
                    Nm.getText()+"','"+
                    kdsat.getText()+"','"+
                    Letak.getText()+"','"+
                    hbeli.getText()+"','"+
                    hdistributor.getText().replaceAll(",","") +"','"+
                    hgrosir.getText().replaceAll(",","")+"','"+
                    hretail.getText().replaceAll(",","")+"','"+
                    stok.getText()+"','"+
                    kdjns.getText()+"','"+
                    Kapasitas.getText()+"'","Kode Barang");
            tampil(" order by databarang.kode_brng ");
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,stok,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
            ChkInput.setSelected(true);
            isForm(); 
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void hgrosirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hgrosirKeyPressed
         Valid.pindah(evt,hdistributor,hretail);
    }//GEN-LAST:event_hgrosirKeyPressed

    private void hretailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hretailKeyPressed
         Valid.pindah(evt,hgrosir,stok);
    }//GEN-LAST:event_hretailKeyPressed

private void LetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakKeyPressed
        Valid.pindah(evt,Nm,Kapasitas);
}//GEN-LAST:event_LetakKeyPressed

private void hdistributorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hdistributorKeyPressed
    Valid.pindah(evt,hbeli,hgrosir);
}//GEN-LAST:event_hdistributorKeyPressed

private void kdsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsatKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat='"+kdsat.getText()+"'", nmsat);           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat='"+kdsat.getText()+"'", nmsat);
            Kapasitas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat='"+kdsat.getText()+"'", nmsat);
            kdjns.requestFocus(); 
        }
}//GEN-LAST:event_kdsatKeyPressed

private void BtnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanActionPerformed
        satuan.emptTeks();
        satuan.tampil(" order by kode_sat");
        satuan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        satuan.setLocationRelativeTo(internalFrame1);
        satuan.setAlwaysOnTop(false);
        satuan.setVisible(true);
}//GEN-LAST:event_BtnSatuanActionPerformed

private void stokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stokKeyPressed
        Valid.pindah(evt,hretail,BtnSimpan);
}//GEN-LAST:event_stokKeyPressed

    private void kdjnsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjnsKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from jenis where kdjns='"+kdjns.getText()+"'", nmjns);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from jenis where kdjns='"+kdjns.getText()+"'", nmjns);
            kdsat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from jenis where kdjns='"+kdjns.getText()+"'", nmjns);
            hbeli.requestFocus();
        }
    }//GEN-LAST:event_kdjnsKeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.emptTeks();
        jenis.tampil(" order by kdjns");
        jenis.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        Valid.pindah(evt,TCari,Nm);
    }//GEN-LAST:event_KdKeyPressed

    private void stokMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stokMouseExited
        if(stok.getText().equals("")){
            stok.setText("0");
        }
    }//GEN-LAST:event_stokMouseExited

    private void hdistributorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hdistributorMouseExited
        if(hdistributor.getText().equals("")){
            hdistributor.setText("0");
        }
    }//GEN-LAST:event_hdistributorMouseExited

    private void hgrosirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hgrosirMouseExited
        if(hgrosir.getText().equals("")){
            hgrosir.setText("0");
        }
    }//GEN-LAST:event_hgrosirMouseExited

    private void hretailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hretailMouseExited
        if(hretail.getText().equals("")){
            hretail.setText("0");
        }
    }//GEN-LAST:event_hretailMouseExited

    private void stokMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stokMouseMoved
        if(stok.getText().equals("0")){
            stok.setText("");
        }
    }//GEN-LAST:event_stokMouseMoved

    private void hdistributorMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hdistributorMouseMoved
        if(hdistributor.getText().equals("0")||hdistributor.getText().equals("0.0")){
            hdistributor.setText("");
        }
    }//GEN-LAST:event_hdistributorMouseMoved

    private void hgrosirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hgrosirMouseMoved
        if(hgrosir.getText().equals("0")||hgrosir.getText().equals("0.0")){
            hgrosir.setText("");
        }
    }//GEN-LAST:event_hgrosirMouseMoved

    private void hretailMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hretailMouseMoved
        if(hretail.getText().equals("0")||hretail.getText().equals("0.0")){
            hretail.setText("");
        }
    }//GEN-LAST:event_hretailMouseMoved

    private void hbeliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hbeliMouseExited
        if(hbeli.getText().equals("")){
            hbeli.setText("0");
        }
    }//GEN-LAST:event_hbeliMouseExited

    private void hbeliMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hbeliMouseMoved
        if(hbeli.getText().equals("0")){
            hbeli.setText("");
        }
    }//GEN-LAST:event_hbeliMouseMoved

    private void hbeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hbeliKeyPressed
        Valid.pindah(evt,kdjns,hdistributor);
    }//GEN-LAST:event_hbeliKeyPressed

    private void ppOrderKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderKodeActionPerformed
        tampil(" order by databarang.kode_brng");
    }//GEN-LAST:event_ppOrderKodeActionPerformed

    private void ppOrderNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderNamaActionPerformed
        tampil(" order by databarang.nama_brng");
    }//GEN-LAST:event_ppOrderNamaActionPerformed

    private void ppOrderSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderSatuanActionPerformed
        tampil(" order by databarang.kode_sat");
    }//GEN-LAST:event_ppOrderSatuanActionPerformed

    private void ppOrderLetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderLetakActionPerformed
        tampil(" order by databarang.letak_barang");
    }//GEN-LAST:event_ppOrderLetakActionPerformed

    private void ppOrderHBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderHBeliActionPerformed
        tampil(" order by databarang.h_beli");
    }//GEN-LAST:event_ppOrderHBeliActionPerformed

    private void ppOrderStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderStokActionPerformed
        tampil(" order by databarang.stok");
    }//GEN-LAST:event_ppOrderStokActionPerformed

    private void ppOrderJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderJenisActionPerformed
        tampil(" order by databarang.kdjns");
    }//GEN-LAST:event_ppOrderJenisActionPerformed

    private void ppBarcodeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBarcodeBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if(TCari.getText().equals("")){
                sql="select databarang.kode_brng "+
                    " from databarang inner join kodesatuan inner join jenis "+
                    " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns order by databarang.kode_brng";
            }else if(!TCari.getText().equals("")){
                sql="select databarang.kode_brng  "+
                    " from databarang inner join kodesatuan inner join jenis "+
                    " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "+
                    " where databarang.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.kode_sat like '%"+TCari.getText().trim()+"%' or "+
                    " kodesatuan.satuan like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.letak_barang like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.stok like '%"+TCari.getText().trim()+"%' or "+
                    " databarang.kdjns like '%"+TCari.getText().trim()+"%' or "+
                    " jenis.nama like '%"+TCari.getText().trim()+"%'  order by databarang.kode_brng";
            }
            Valid.MyReport("rptBarcode.jrxml","report","::[ Data Barang ]::",sql);            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBarcodeBtnPrintActionPerformed

    private void ppBarcodeItemBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBarcodeItemBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(Nm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            int jml;
            try{
               jml=Integer.parseInt(JOptionPane.showInputDialog("Masukkan jumlah barcode yang mau dicetak !!!")); 
            }catch(HeadlessException | NumberFormatException e){
                jml=0;
            }
            
            if(jml>0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu("delete from temporary");
                jml=Math.round(jml/3);
                for(int i=0;i<jml;i++){  
                    Sequel.menyimpan("temporary","'0','"+Kd.getText()+"','"+Kd.getText()+"','"+Kd.getText()+"','','','','','','','','','','','','','',''","Barcode"); 
                }
                Valid.MyReport("rptBarcodeItem.jrxml","report","::[ Barcode Perbarang ]::",
                           "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc");                           
                this.setCursor(Cursor.getDefaultCursor());
            }   
        }
        
    }//GEN-LAST:event_ppBarcodeItemBtnPrintActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void ppStokBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokBtnPrintActionPerformed
            sql="select databarang.kode_brng, databarang.nama_brng, "+
                " databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang, databarang.h_beli,"+
                " databarang.h_distributor, databarang.h_grosir, databarang.h_retail, databarang.stok, databarang.kdjns,"+
                " jenis.nama,(databarang.stok*databarang.h_beli) as nomibeli,"+
                " (databarang.stok*databarang.h_distributor) as nomijual,(databarang.stok*databarang.h_grosir) as nomiresep,"+
                " (databarang.stok*databarang.h_retail) as nomiretail,kapasitas "+
                " from databarang inner join kodesatuan inner join jenis "+
                " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "+
                " where databarang.kode_brng like ? or "+
                " databarang.nama_brng like ? or "+
                " databarang.kode_sat like ? or "+
                " kodesatuan.satuan like ? or "+
                " databarang.letak_barang like ? or "+
                " databarang.kdjns like ? or "+
                " jenis.nama like ? order by databarang.nama_brng";
        prosesCari2(sql);
}//GEN-LAST:event_ppStokBtnPrintActionPerformed

private void ppMutasiBtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppMutasiBtnEditActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda mutasi..!!!!");
            Kd.requestFocus();
        }else if(Nm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda mutasikan dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else if(! Nm.getText().trim().equals("")){
            DlgPindahGudang pindah=new DlgPindahGudang(null,false);
            pindah.tampil(" order by mutasibarang.tanggal");
            pindah.setBarang(Kd.getText(),Nm.getText());
            pindah.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            pindah.setLocationRelativeTo(internalFrame1);
            pindah.setAlwaysOnTop(false);
            pindah.setVisible(true);
        }        
}//GEN-LAST:event_ppMutasiBtnEditActionPerformed

private void KapasitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KapasitasKeyPressed
  Valid.pindah(evt,Letak,kdsat);
}//GEN-LAST:event_KapasitasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBarang dialog = new DlgBarang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnJenis;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSatuan;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kapasitas;
    private widget.TextBox Kd;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.TextBox Letak;
    private widget.TextBox Nm;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox hbeli;
    private widget.TextBox hdistributor;
    private widget.TextBox hgrosir;
    private widget.TextBox hretail;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox kdjns;
    private widget.TextBox kdsat;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label9;
    private widget.TextBox nmjns;
    private widget.TextBox nmsat;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppBarcode;
    private javax.swing.JMenuItem ppBarcodeItem;
    private javax.swing.JMenuItem ppCetak;
    private javax.swing.JMenuItem ppGanti;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppMutasi;
    private javax.swing.JMenu ppOrder;
    private javax.swing.JMenuItem ppOrderHBeli;
    private javax.swing.JMenuItem ppOrderJenis;
    private javax.swing.JMenuItem ppOrderKode;
    private javax.swing.JMenuItem ppOrderLetak;
    private javax.swing.JMenuItem ppOrderNama;
    private javax.swing.JMenuItem ppOrderSatuan;
    private javax.swing.JMenuItem ppOrderStok;
    private javax.swing.JMenuItem ppStok;
    private widget.ScrollPane scrollPane1;
    private widget.TextBox stok;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil(String order) {
        sql="select databarang.kode_brng, databarang.nama_brng, "+
                " databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang, databarang.h_beli,"+
                " databarang.h_distributor, databarang.h_grosir, databarang.h_retail, databarang.stok, databarang.kdjns,"+
                " jenis.nama,(databarang.stok*databarang.h_beli) as nomibeli,"+
                " (databarang.stok*databarang.h_distributor) as nomijual,(databarang.stok*databarang.h_grosir) as nomiresep,"+
                " (databarang.stok*databarang.h_retail) as nomiretail,kapasitas "+
                " from databarang inner join kodesatuan inner join jenis "+
                " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "+
                " where databarang.kode_brng like ? or "+
                " databarang.nama_brng like ? or "+
                " databarang.kode_sat like ? or "+
                " kodesatuan.satuan like ? or "+
                " databarang.letak_barang like ? or "+
                " databarang.kdjns like ? or "+
                " jenis.nama like ? "+order; 
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(sql);
            ps.setString(1,"%"+TCari.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+TCari.getText().trim()+"%");
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            ps.setString(7,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               df2.format(rs.getDouble(6)),
                               df2.format(rs.getDouble(7)),
                               df2.format(rs.getDouble(8)),
                               df2.format(rs.getDouble(9)),
                               "Klik Kanan, Lihat Stok",
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString("kapasitas")});
            }
            LCount.setText(""+tabMode.getRowCount());    
            //rs.close();
            //stat.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }
    
    private void prosesCari2(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(sql);
            ps.setString(1,"%"+TCari.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+TCari.getText().trim()+"%");
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            ps.setString(7,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            ps2=koneksi.prepareStatement("select kd_bangsal,nm_bangsal from bangsal");
            ps3=koneksi.prepareStatement("select stok from gudangbarang where kode_brng=? and kd_bangsal=?");
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               df2.format(rs.getDouble(6)),
                               df2.format(rs.getDouble(7)),
                               df2.format(rs.getDouble(8)),
                               df2.format(rs.getDouble(9)),
                               "Lokasi Barang :",
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString("kapasitas")});
                rs2=ps2.executeQuery();
                totalstok=0;
                while(rs2.next()){           
                    stokgudang=0;
                    ps3.setString(1,rs.getString(1));
                    ps3.setString(2,rs2.getString(1));                    
                    rs3=ps3.executeQuery();
                    if(rs3.next()){
                        stokgudang=rs3.getDouble(1);
                        totalstok=totalstok+rs3.getDouble(1);
                    }
                    tabMode.addRow(new Object[]{"","","","","","","","","",
                               rs2.getString(2),": "+df2.format(stokgudang),""});
                }
                tabMode.addRow(new Object[]{"","","","","","","","","","Total Stok",": "+df2.format(totalstok),""});
            }
            rs.last();
            LCount.setText(""+rs.getRow());    
           // rs.close();
           // stat.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }

    public void emptTeks() {
        Kd.setText("");
        Kd2.setText("");
        Nm.setText("");
        kdsat.setText("");
        nmsat.setText("");
        Letak.setText("");
        hbeli.setText("0");
        hretail.setText("0");
        hgrosir.setText("0");
        hdistributor.setText("0");
        Kapasitas.setText("0");
        stok.setText("0");
        kdjns.setText("");
        nmjns.setText("");
        
        Kd.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_brng,4),signed)),0)  from databarang ","B",9,Kd);
        //Valid.autoNomer("databarang","B",9,Kd);
    }

    private void getData() {
        int row=tbDokter.getSelectedRow();
        if(row!= -1){
            Kd.setText(tbDokter.getValueAt(row,0).toString());
            Kd2.setText(tbDokter.getValueAt(row,0).toString());
            Nm.setText(tbDokter.getValueAt(row,1).toString());
            kdsat.setText(tbDokter.getValueAt(row,2).toString());
            nmsat.setText(tbDokter.getValueAt(row,3).toString());
            Letak.setText(tbDokter.getValueAt(row,4).toString());
            hbeli.setText(tbDokter.getValueAt(row,5).toString());
            hdistributor.setText(tbDokter.getValueAt(row,6).toString());
            hgrosir.setText(tbDokter.getValueAt(row,7).toString());
            hretail.setText(tbDokter.getValueAt(row,8).toString());
            kdjns.setText(tbDokter.getValueAt(row,10).toString());
            nmjns.setText(tbDokter.getValueAt(row,11).toString());
            Kapasitas.setText(tbDokter.getValueAt(row,12).toString());
            stok.setText(Double.toString(Sequel.cariIsiAngka("select sum(stok) from gudangbarang where kode_brng=?",tbDokter.getValueAt(row,0).toString())));
        }
    }


    public JTextField getTextField(){
        return Kd;
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    
    private void isHitung(){
        try{
            rs=koneksi.createStatement().executeQuery("select * from setpenjualan");
            while(rs.next()){
                if(!hbeli.getText().equals("")){
                    x=Double.parseDouble(hbeli.getText())+(Double.parseDouble(hbeli.getText())*(rs.getDouble(1)/100));
                    hdistributor.setText(Valid.SetAngka2(x));
                    y=Double.parseDouble(hbeli.getText())+(Double.parseDouble(hbeli.getText())*(rs.getDouble(2)/100));
                    hgrosir.setText(Valid.SetAngka2(y));
                    z=Double.parseDouble(hbeli.getText())+(Double.parseDouble(hbeli.getText())*(rs.getDouble(3)/100));
                    hretail.setText(Valid.SetAngka2(z));
                }
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }          
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,188));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getobat());
        BtnHapus.setEnabled(var.getobat());
        BtnEdit.setEnabled(var.getobat());
        BtnPrint.setEnabled(var.getobat());
        
        ppGanti.setEnabled(var.getobat());        
        ppHapus.setEnabled(var.getobat());
        ppCetak.setEnabled(var.getobat());
    }
    

 
}
