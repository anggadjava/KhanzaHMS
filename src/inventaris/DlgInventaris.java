/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package inventaris;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgInventaris extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();

    /** Creates new form DlgJnsPerawatan */
    public DlgInventaris(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Inventaris",
                      "Kode Barang",
                      "Nama Barang",
                      "Produsen",
                      "Merk",
                      "Thn.Produksi",
                      "Nomer ISBN",
                      "Kategori",
                      "Jenis",
                      "Asal Barang",
                      "Tgl.Pengadaan",
                      "Harga",
                      "Stts.Barang",
                      "Ruang",
                      "No.Rak",
                      "No.Box"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 16; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(60);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        no_inventaris.setDocument(new batasInput((byte)20).getKata(no_inventaris));
        kode_barang.setDocument(new batasInput((byte)10).getKata(kode_barang));
        harga.setDocument(new batasInput((byte)15).getOnlyAngka(harga));
        id_ruang.setDocument(new batasInput((byte)5).getKata(id_ruang));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.requestFocus();
        barang.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
               kode_barang.setText(barang.getTextField().getText());
               isBarang();               
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
              
        ruang.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                id_ruang.setText(ruang.getTextField().getText());
                Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang);
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
      
        ChkInput.setSelected(false);
        isForm(); 
    }
    private DlgBarangInventaris barang=new DlgBarangInventaris(null,false); 
    private DlgRuangInventaris ruang=new DlgRuangInventaris(null,false); 
    private double nilai_inven=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBarcode = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label1 = new widget.Label();
        no_inventaris = new widget.TextBox();
        nm_merk = new widget.TextBox();
        label8 = new widget.Label();
        label9 = new widget.Label();
        label19 = new widget.Label();
        kode_barang = new widget.TextBox();
        nama_barang = new widget.TextBox();
        BtnSatuan = new widget.Button();
        tgl_pengadaan = new widget.Tanggal();
        jLabel18 = new widget.Label();
        status_barang = new widget.ComboBox();
        label12 = new widget.Label();
        nm_kategori = new widget.TextBox();
        label13 = new widget.Label();
        harga = new widget.TextBox();
        label14 = new widget.Label();
        nm_jenis = new widget.TextBox();
        label15 = new widget.Label();
        nm_produsen = new widget.TextBox();
        jLabel19 = new widget.Label();
        asal_barang = new widget.ComboBox();
        jPanel1 = new javax.swing.JPanel();
        BtnSatuan1 = new widget.Button();
        nm_ruang = new widget.TextBox();
        id_ruang = new widget.TextBox();
        label20 = new widget.Label();
        no_box = new widget.ComboBox();
        no_rak = new widget.ComboBox();
        label21 = new widget.Label();
        label22 = new widget.Label();
        ChkInput = new widget.CekBox();

        Popup.setName("Popup"); // NOI18N

        ppBarcode.setBackground(new java.awt.Color(242, 242, 242));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Aset Inventaris ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setComponentPopupMenu(Popup);
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

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
        panelGlass8.add(BtnEdit);

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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        jLabel7.setText("Record | Nilai Aset :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(125, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 127));
        FormInput.setLayout(null);

        label1.setText("No.Inv :");
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(0, 10, 65, 23);

        no_inventaris.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        no_inventaris.setName("no_inventaris"); // NOI18N
        no_inventaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_inventarisKeyPressed(evt);
            }
        });
        FormInput.add(no_inventaris);
        no_inventaris.setBounds(68, 10, 140, 23);

        nm_merk.setEditable(false);
        nm_merk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nm_merk.setName("nm_merk"); // NOI18N
        FormInput.add(nm_merk);
        nm_merk.setBounds(68, 70, 140, 23);

        label8.setText("Tgl.Pengadaan :");
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(367, 10, 100, 23);

        label9.setText("Merk :");
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(0, 70, 65, 23);

        label19.setText("Barang :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(0, 40, 65, 23);

        kode_barang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        kode_barang.setName("kode_barang"); // NOI18N
        kode_barang.setPreferredSize(new java.awt.Dimension(207, 23));
        kode_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_barangKeyPressed(evt);
            }
        });
        FormInput.add(kode_barang);
        kode_barang.setBounds(68, 40, 121, 23);

        nama_barang.setEditable(false);
        nama_barang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nama_barang.setName("nama_barang"); // NOI18N
        nama_barang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nama_barang);
        nama_barang.setBounds(190, 40, 510, 23);

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
        BtnSatuan.setBounds(703, 40, 25, 23);

        tgl_pengadaan.setForeground(new java.awt.Color(50, 70, 50));
        tgl_pengadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-04-19" }));
        tgl_pengadaan.setDisplayFormat("yyyy-MM-dd");
        tgl_pengadaan.setName("tgl_pengadaan"); // NOI18N
        tgl_pengadaan.setOpaque(false);
        tgl_pengadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_pengadaanKeyPressed(evt);
            }
        });
        FormInput.add(tgl_pengadaan);
        tgl_pengadaan.setBounds(470, 10, 100, 23);

        jLabel18.setText("Status :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(540, 10, 90, 23);

        status_barang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Rusak", "Hilang", "Perbaikan", "Dipinjam", "-" }));
        status_barang.setLightWeightPopupEnabled(false);
        status_barang.setName("status_barang"); // NOI18N
        status_barang.setOpaque(false);
        status_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                status_barangKeyPressed(evt);
            }
        });
        FormInput.add(status_barang);
        status_barang.setBounds(633, 10, 95, 23);

        label12.setText("Kategori :");
        label12.setName("label12"); // NOI18N
        FormInput.add(label12);
        label12.setBounds(0, 100, 65, 23);

        nm_kategori.setEditable(false);
        nm_kategori.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nm_kategori.setName("nm_kategori"); // NOI18N
        FormInput.add(nm_kategori);
        nm_kategori.setBounds(68, 100, 140, 23);

        label13.setText("Harga : Rp.");
        label13.setName("label13"); // NOI18N
        FormInput.add(label13);
        label13.setBounds(0, 130, 85, 23);

        harga.setText("0");
        harga.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        harga.setName("harga"); // NOI18N
        harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hargaKeyPressed(evt);
            }
        });
        FormInput.add(harga);
        harga.setBounds(87, 130, 121, 23);

        label14.setText("Jenis :");
        label14.setName("label14"); // NOI18N
        FormInput.add(label14);
        label14.setBounds(190, 70, 82, 23);

        nm_jenis.setEditable(false);
        nm_jenis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nm_jenis.setName("nm_jenis"); // NOI18N
        FormInput.add(nm_jenis);
        nm_jenis.setBounds(275, 70, 140, 23);

        label15.setText("Produsen :");
        label15.setName("label15"); // NOI18N
        FormInput.add(label15);
        label15.setBounds(190, 100, 82, 23);

        nm_produsen.setEditable(false);
        nm_produsen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nm_produsen.setName("nm_produsen"); // NOI18N
        FormInput.add(nm_produsen);
        nm_produsen.setBounds(275, 100, 140, 23);

        jLabel19.setText("Asal :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(190, 130, 82, 23);

        asal_barang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Beli", "Bantuan", "Hibah", "-" }));
        asal_barang.setLightWeightPopupEnabled(false);
        asal_barang.setName("asal_barang"); // NOI18N
        asal_barang.setOpaque(false);
        asal_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                asal_barangKeyPressed(evt);
            }
        });
        FormInput.add(asal_barang);
        asal_barang.setBounds(275, 130, 140, 23);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "Posisi Inventaris Di :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        BtnSatuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSatuan1.setMnemonic('1');
        BtnSatuan1.setToolTipText("Alt+1");
        BtnSatuan1.setName("BtnSatuan1"); // NOI18N
        BtnSatuan1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSatuan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSatuan1ActionPerformed(evt);
            }
        });
        jPanel1.add(BtnSatuan1);
        BtnSatuan1.setBounds(273, 25, 25, 23);

        nm_ruang.setEditable(false);
        nm_ruang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nm_ruang.setName("nm_ruang"); // NOI18N
        nm_ruang.setPreferredSize(new java.awt.Dimension(207, 23));
        jPanel1.add(nm_ruang);
        nm_ruang.setBounds(121, 25, 150, 23);

        id_ruang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        id_ruang.setName("id_ruang"); // NOI18N
        id_ruang.setPreferredSize(new java.awt.Dimension(207, 23));
        id_ruang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_ruangKeyPressed(evt);
            }
        });
        jPanel1.add(id_ruang);
        id_ruang.setBounds(50, 25, 70, 23);

        label20.setText("Rak :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        jPanel1.add(label20);
        label20.setBounds(1, 55, 46, 23);

        no_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));
        no_box.setLightWeightPopupEnabled(false);
        no_box.setName("no_box"); // NOI18N
        no_box.setOpaque(false);
        no_box.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_boxKeyPressed(evt);
            }
        });
        jPanel1.add(no_box);
        no_box.setBounds(201, 55, 70, 23);

        no_rak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        no_rak.setLightWeightPopupEnabled(false);
        no_rak.setName("no_rak"); // NOI18N
        no_rak.setOpaque(false);
        no_rak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_rakKeyPressed(evt);
            }
        });
        jPanel1.add(no_rak);
        no_rak.setBounds(50, 55, 70, 23);

        label21.setText("Ruang :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        jPanel1.add(label21);
        label21.setBounds(1, 25, 46, 23);

        label22.setText("Box :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        jPanel1.add(label22);
        label22.setBounds(152, 55, 46, 23);

        FormInput.add(jPanel1);
        jPanel1.setBounds(420, 62, 310, 94);

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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(no_inventaris.getText().trim().equals("")){
            Valid.textKosong(no_inventaris,"No.Inventaris");
        }else if(nama_barang.getText().trim().equals("")){
            Valid.textKosong(kode_barang,"Nama Barang");
        }else if(harga.getText().trim().equals("")){
            Valid.textKosong(harga,"Harga Barang");
        }else if(id_ruang.getText().trim().equals("")||nm_ruang.getText().trim().equals("")){
            Valid.textKosong(id_ruang,"Ruangan");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.menyimpan("inventaris","'"+no_inventaris.getText()+"','"+kode_barang.getText()+"','"+asal_barang.getSelectedItem()+"','"+tgl_pengadaan.getSelectedItem()+
                        "','"+harga.getText()+"','"+status_barang.getSelectedItem()+"','"+id_ruang.getText()+"','"+no_rak.getSelectedItem()+"','"+no_box.getSelectedItem()+"'","No.Inventaris");
                //----------------------------------------------------------
                no_inventaris.requestFocus();
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,no_box,BtnBatal);
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
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,no_inventaris,"inventaris","no_inventaris");
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(no_inventaris.getText().trim().equals("")){
            Valid.textKosong(no_inventaris,"No.Inventaris");
        }else if(nama_barang.getText().trim().equals("")){
            Valid.textKosong(kode_barang,"Nama Barang");
        }else if(harga.getText().trim().equals("")){
            Valid.textKosong(harga,"Harga Barang");
        }else if(id_ruang.getText().trim().equals("")||nm_ruang.getText().trim().equals("")){
            Valid.textKosong(id_ruang,"Ruangan");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.mengedit("inventaris","no_inventaris='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0)+"'",
                        "no_inventaris='"+no_inventaris.getText()+"',kode_barang='"+kode_barang.getText()+"',asal_barang='"+asal_barang.getSelectedItem()+"',tgl_pengadaan='"+tgl_pengadaan.getSelectedItem()+
                        "',harga='"+harga.getText()+"',status_barang='"+status_barang.getSelectedItem()+"',id_ruang='"+id_ruang.getText()+"',no_rak='"+no_rak.getSelectedItem()+"',no_box='"+no_box.getSelectedItem()+"'");
                //----------------------------------------------------------
                no_inventaris.requestFocus();
            tampil();
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
                Valid.MyReport("rptInv.jrxml","report","::[ Data Inv Barang ]::","select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                   "inventaris_produsen.nama_produsen, inventaris_merk.nama_merk, inventaris_barang.thn_produksi, inventaris_barang.isbn,"+
                   "inventaris_kategori.nama_kategori, inventaris_jenis.nama_jenis,inventaris.asal_barang,inventaris.tgl_pengadaan,"+
                   "inventaris.harga,inventaris.status_barang,inventaris_ruang.nama_ruang,inventaris.no_rak,inventaris.no_box "+
                   "from inventaris inner join inventaris_barang inner join inventaris_produsen inner join inventaris_ruang "+
                   "inner join inventaris_jenis inner join inventaris_kategori inner join inventaris_merk "+
                   "on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                   "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori and inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                   "and inventaris_barang.kode_barang=inventaris.kode_barang and inventaris.id_ruang=inventaris_ruang.id_ruang "+
                   "where inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.no_inventaris like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.asal_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.tgl_pengadaan like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.status_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_ruang.nama_ruang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.jml_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.thn_produksi like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.isbn like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_jenis.nama_jenis like '%"+TCari.getText().trim()+"%' order by inventaris_barang.kode_barang,inventaris.no_inventaris");
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
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
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
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
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void no_inventarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_inventarisKeyPressed
        Valid.pindah(evt,BtnKeluar,nama_barang);
}//GEN-LAST:event_no_inventarisKeyPressed

private void kode_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_barangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        isBarang();
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        isBarang();
        kode_barang.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        isBarang();
        harga.requestFocus();
    }
}//GEN-LAST:event_kode_barangKeyPressed

private void BtnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanActionPerformed
    var.setStatus(true);
    barang.emptTeks();
    barang.tampil();
    barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    barang.setLocationRelativeTo(internalFrame1);
    barang.setAlwaysOnTop(false);
    barang.setVisible(true);
}//GEN-LAST:event_BtnSatuanActionPerformed

private void id_ruangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_ruangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang);        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang);
        asal_barang.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang);
        no_rak.requestFocus();
    }
}//GEN-LAST:event_id_ruangKeyPressed

private void BtnSatuan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuan1ActionPerformed
    var.setStatus(true);
    ruang.emptTeks();
    ruang.tampil();
    ruang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    ruang.setLocationRelativeTo(internalFrame1);
    ruang.setAlwaysOnTop(false);
    ruang.setVisible(true);
}//GEN-LAST:event_BtnSatuan1ActionPerformed

private void tgl_pengadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_pengadaanKeyPressed
    Valid.pindah(evt,asal_barang,status_barang);
}//GEN-LAST:event_tgl_pengadaanKeyPressed

private void status_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_status_barangKeyPressed
    Valid.pindah(evt,tgl_pengadaan,id_ruang);
}//GEN-LAST:event_status_barangKeyPressed

private void hargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaKeyPressed
    Valid.pindah(evt,kode_barang,asal_barang);
}//GEN-LAST:event_hargaKeyPressed

private void asal_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_asal_barangKeyPressed
    Valid.pindah(evt,harga,tgl_pengadaan);
}//GEN-LAST:event_asal_barangKeyPressed

private void no_boxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_boxKeyPressed
    // TODO add your handling code here:
}//GEN-LAST:event_no_boxKeyPressed

private void no_rakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_rakKeyPressed
    Valid.pindah(evt,id_ruang,BtnSimpan);
}//GEN-LAST:event_no_rakKeyPressed

private void ppBarcodeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBarcodeBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Valid.MyReport("rptBarcodeInventaris.jrxml","report","::[ Data Barang ]::","select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                   "inventaris_produsen.nama_produsen, inventaris_merk.nama_merk, inventaris_barang.thn_produksi, inventaris_barang.isbn,"+
                   "inventaris_kategori.nama_kategori, inventaris_jenis.nama_jenis,inventaris.asal_barang,inventaris.tgl_pengadaan,"+
                   "inventaris.harga,inventaris.status_barang,inventaris_ruang.nama_ruang,inventaris.no_rak,inventaris.no_box "+
                   "from inventaris inner join inventaris_barang inner join inventaris_produsen inner join inventaris_ruang "+
                   "inner join inventaris_jenis inner join inventaris_kategori inner join inventaris_merk "+
                   "on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                   "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori and inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                   "and inventaris_barang.kode_barang=inventaris.kode_barang and inventaris.id_ruang=inventaris_ruang.id_ruang "+
                   "where inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.no_inventaris like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.asal_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.tgl_pengadaan like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.status_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_ruang.nama_ruang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.jml_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.thn_produksi like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.isbn like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_jenis.nama_jenis like '%"+TCari.getText().trim()+"%' order by inventaris_barang.kode_barang,inventaris.no_inventaris");            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_ppBarcodeBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgInventaris dialog = new DlgInventaris(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSatuan;
    private widget.Button BtnSatuan1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox asal_barang;
    private widget.TextBox harga;
    private widget.TextBox id_ruang;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kode_barang;
    private widget.Label label1;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox nama_barang;
    private widget.TextBox nm_jenis;
    private widget.TextBox nm_kategori;
    private widget.TextBox nm_merk;
    private widget.TextBox nm_produsen;
    private widget.TextBox nm_ruang;
    private widget.ComboBox no_box;
    private widget.TextBox no_inventaris;
    private widget.ComboBox no_rak;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBarcode;
    private widget.ComboBox status_barang;
    private widget.Table tbJnsPerawatan;
    private widget.Tanggal tgl_pengadaan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String sql="select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                   "inventaris_produsen.nama_produsen, inventaris_merk.nama_merk, inventaris_barang.thn_produksi, inventaris_barang.isbn,"+
                   "inventaris_kategori.nama_kategori, inventaris_jenis.nama_jenis,inventaris.asal_barang,inventaris.tgl_pengadaan,"+
                   "inventaris.harga,inventaris.status_barang,inventaris_ruang.nama_ruang,inventaris.no_rak,inventaris.no_box "+
                   "from inventaris inner join inventaris_barang inner join inventaris_produsen inner join inventaris_ruang "+
                   "inner join inventaris_jenis inner join inventaris_kategori inner join inventaris_merk "+
                   "on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                   "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori and inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                   "and inventaris_barang.kode_barang=inventaris.kode_barang and inventaris.id_ruang=inventaris_ruang.id_ruang "+
                   "where inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.no_inventaris like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.asal_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.tgl_pengadaan like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris.status_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_ruang.nama_ruang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.jml_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.thn_produksi like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.isbn like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_jenis.nama_jenis like '%"+TCari.getText().trim()+"%' order by inventaris_barang.kode_barang,inventaris.no_inventaris";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{            
            ResultSet rs=koneksiDB.condb().prepareStatement(sql).executeQuery();
            nilai_inven=0;
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString("no_inventaris"),
                               rs.getString("kode_barang"),
                               rs.getString("nama_barang"),
                               rs.getString("nama_produsen"),
                               rs.getString("nama_merk"),
                               rs.getString("thn_produksi").substring(0,4),
                               rs.getString("isbn"),
                               rs.getString("nama_kategori"),
                               rs.getString("nama_jenis"),
                               rs.getString("asal_barang"),
                               rs.getString("tgl_pengadaan"),
                               Valid.SetAngka(rs.getDouble("harga")),
                               rs.getString("status_barang"),
                               rs.getString("nama_ruang"),
                               rs.getString("no_rak"),
                               rs.getString("no_box")});
                nilai_inven=nilai_inven+rs.getDouble("harga");
            }
            rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(tabMode.getRowCount()+" | "+Valid.SetAngka(nilai_inven));
    }

    public void emptTeks() {
        no_inventaris.setText("");
        kode_barang.setText("");
        nama_barang.setText("");
        nm_jenis.setText("");
        nm_kategori.setText("");
        nm_merk.setText("");
        nm_produsen.setText("");
        nm_ruang.setText("");
        harga.setText("0");
        asal_barang.setSelectedIndex(0);
        tgl_pengadaan.setDate(new Date());
        status_barang.setSelectedIndex(0);
        id_ruang.setText("");
        no_rak.setSelectedIndex(0);
        no_box.setSelectedIndex(0);
        TCari.setText("");
        no_inventaris.requestFocus();
        //Valid.autoNomer(" jns_perawatan ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_inventaris,5),signed)),0) from inventaris  ","I",9,no_inventaris);
        no_inventaris.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
                no_inventaris.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
                kode_barang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
                nama_barang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
                nm_produsen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
                nm_merk.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
                nm_kategori.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
                nm_jenis.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
                asal_barang.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString());
                Valid.SetTgl(tgl_pengadaan,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString());
                harga.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),11).toString());
                status_barang.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),12).toString());
                Sequel.cariIsi("select inventaris.id_ruang from inventaris where inventaris.no_inventaris='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()+"'",id_ruang);
                nm_ruang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),13).toString());
                no_rak.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),14).toString());
                no_box.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),15).toString());        
          }
    }

    
    public JTextField getTextField(){
        return no_inventaris;
    }

    public JTable getTable(){
        return tbJnsPerawatan;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,185));
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
        BtnSimpan.setEnabled(var.gettindakan());
        BtnHapus.setEnabled(var.gettindakan());
        BtnEdit.setEnabled(var.gettindakan());
        BtnPrint.setEnabled(var.gettindakan());
    }
    
    private void isBarang(){
              try {                    
                    PreparedStatement ps=koneksiDB.condb().prepareStatement("select inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                       "inventaris_produsen.nama_produsen, inventaris_merk.nama_merk,"+
                       "inventaris_kategori.nama_kategori, inventaris_jenis.nama_jenis from inventaris_barang inner join inventaris_produsen "+
                       "inner join inventaris_jenis inner join inventaris_kategori inner join inventaris_merk "+
                       "on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                       "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori and inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                       "where inventaris_barang.kode_barang=?");
                    ps.setString(1,kode_barang.getText());
                    ResultSet rs=ps.executeQuery();
                    while(rs.next()){
                        nama_barang.setText(rs.getString("nama_barang"));
                        nm_produsen.setText(rs.getString("nama_produsen"));
                        nm_merk.setText(rs.getString("nama_merk"));
                        nm_kategori.setText(rs.getString("nama_kategori"));
                        nm_jenis.setText(rs.getString("nama_jenis"));
                    }
                } catch (SQLException ex) {
                    System.out.println();
                }
    }   

    
}
