package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.grafikpembelianterbanyak;
import fungsi.grafikpembeliantersedikit;
import fungsi.grafikpenjualanterbanyak;
import fungsi.grafikpenjualantersedikit;
import fungsi.grafikpiutangterbanyak;
import fungsi.grafikpiutangtersedikit;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgBarang;
import keuangan.Jurnal;

public class DlgSirkulasiBarang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize(); 
    private double ttltotaljual=0,totaljual=0,jumlahjual=0,ttltotalbeli=0,totalbeli=0,jumlahbeli=0,
                   ttltotalpiutang=0,totalpiutang=0,jumlahpiutang=0,ttltotalretbeli=0,totalretbeli=0,jumlahretbeli=0,
                   ttltotalretjual=0,totalretjual=0,jumlahretjual=0,ttltotalretpiut=0,totalretpiut=0,jumlahretpiut=0,
                   jumlahpasin=0,totalpasien=0,ttltotalpasien=0;
    private DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,ps7,ps8;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6,rs7,rs8;

    /** 
     * @param parent
     * @param modal */
    public DlgSirkulasiBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Barang","Nama Barang","Satuan","Stok","Pembelian","Penjualan",
                      "Ke Pasien","Piutang Jual","Retur Beli","Retur Jual","Retur Piutang"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        barang.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdbar.setText(barang.getTextField().getText());
                Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbar.getText()+"'", nmbar);              
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
            ps=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng, "+
                        "kodesatuan.satuan,databarang.stok from databarang inner join kodesatuan   "+
                        "on databarang.kode_sat=kodesatuan.kode_sat "+
                        "where databarang.nama_brng like ? and databarang.kode_brng like ? or "+
                        "databarang.nama_brng like ? and databarang.nama_brng like ? or "+
                        "databarang.nama_brng like ? and kodesatuan.satuan like ? "+
                        " order by databarang.kode_brng");
            ps2=koneksi.prepareStatement("select sum(detailbeli.jumlah), sum(detailbeli.subtotal) "+
                        " from pembelian inner join detailbeli "+
                        " on pembelian.no_faktur=detailbeli.no_faktur "+
                        " where detailbeli.kode_brng=? and pembelian.tgl_beli "+
                        " between ? and ? ");
            ps3=koneksi.prepareStatement("select sum(detailjual.jumlah), sum(detailjual.total) "+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng=? and "+
                        " penjualan.tgl_jual  between ? and ? ");
            ps4=koneksi.prepareStatement("select sum(detailpiutang.jumlah), sum(detailpiutang.total) "+
                        " from piutang inner join detailpiutang "+
                        " on piutang.nota_piutang=detailpiutang.nota_piutang "+
                        " where detailpiutang.kode_brng=? and "+
                        " piutang.tgl_piutang between ? and ? ");
            ps5=koneksi.prepareStatement("select sum(detreturbeli.jml_retur), sum(detreturbeli.total) "+
                        " from returbeli inner join detreturbeli "+
                        " on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                        " where detreturbeli.kode_brng=? and "+
                        " returbeli.tgl_retur between ? and ? ");
            ps6=koneksi.prepareStatement("select sum(detreturjual.jml_retur), sum(detreturjual.subtotal) "+
                        " from returjual inner join detreturjual "+
                        " on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                        " where detreturjual.kode_brng=? and "+
                        " returjual.tgl_retur between ? and ? ");
            ps7=koneksi.prepareStatement("select sum(detreturpiutang.jml_retur), sum(detreturpiutang.subtotal) "+
                        " from returpiutang inner join detreturpiutang "+
                        " on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                        " where detreturpiutang.kode_brng=? and "+
                        " returpiutang.tgl_retur between ? and ?");
            ps8=koneksi.prepareStatement("select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.tambahan)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
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

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikJualBanyak = new javax.swing.JMenuItem();
        ppGrafikJualDikit = new javax.swing.JMenuItem();
        ppGrafikbeliBanyak = new javax.swing.JMenuItem();
        ppGrafikbelidikit = new javax.swing.JMenuItem();
        ppGrafikPiutangBanyak = new javax.swing.JMenuItem();
        ppGrafikPiutangDikit = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        BtnCari6 = new widget.Button();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikJualBanyak.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikJualBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikJualBanyak.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikJualBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikJualBanyak.setText("Grafik 10 Barang Penjualan Terbanyak");
        ppGrafikJualBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikJualBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikJualBanyak.setIconTextGap(8);
        ppGrafikJualBanyak.setName("ppGrafikJualBanyak"); // NOI18N
        ppGrafikJualBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikJualBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikJualBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikJualBanyak);

        ppGrafikJualDikit.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikJualDikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikJualDikit.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikJualDikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikJualDikit.setText("Grafik 10 Barang Penjualan Tersedikit");
        ppGrafikJualDikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikJualDikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikJualDikit.setIconTextGap(8);
        ppGrafikJualDikit.setName("ppGrafikJualDikit"); // NOI18N
        ppGrafikJualDikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikJualDikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikJualDikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikJualDikit);

        ppGrafikbeliBanyak.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikbeliBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbeliBanyak.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikbeliBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbeliBanyak.setText("Grafik 10 Barang Pembelian Terbanyak");
        ppGrafikbeliBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbeliBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbeliBanyak.setIconTextGap(8);
        ppGrafikbeliBanyak.setName("ppGrafikbeliBanyak"); // NOI18N
        ppGrafikbeliBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbeliBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbeliBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikbeliBanyak);

        ppGrafikbelidikit.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikbelidikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbelidikit.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikbelidikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbelidikit.setText("Grafik 10 Barang Pembelian Tersedikit");
        ppGrafikbelidikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbelidikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbelidikit.setIconTextGap(8);
        ppGrafikbelidikit.setName("ppGrafikbelidikit"); // NOI18N
        ppGrafikbelidikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbelidikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbelidikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikbelidikit);

        ppGrafikPiutangBanyak.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikPiutangBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiutangBanyak.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikPiutangBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiutangBanyak.setText("Grafik 10 Barang Piutang Terbanyak");
        ppGrafikPiutangBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiutangBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiutangBanyak.setIconTextGap(8);
        ppGrafikPiutangBanyak.setName("ppGrafikPiutangBanyak"); // NOI18N
        ppGrafikPiutangBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikPiutangBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiutangBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiutangBanyak);

        ppGrafikPiutangDikit.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikPiutangDikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiutangDikit.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikPiutangDikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiutangDikit.setText("Grafik 10 Barang Piutang Tersedikit");
        ppGrafikPiutangDikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiutangDikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiutangDikit.setIconTextGap(8);
        ppGrafikPiutangDikit.setName("ppGrafikPiutangDikit"); // NOI18N
        ppGrafikPiutangDikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikPiutangDikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiutangDikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiutangDikit);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Barang Keluar Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("yyyy-MM-dd");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("yyyy-MM-dd");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl2);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label17);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);

        BtnCari6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari6.setMnemonic('1');
        BtnCari6.setToolTipText("Alt+1");
        BtnCari6.setName("BtnCari6"); // NOI18N
        BtnCari6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari6ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnCari6);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(79, 30));
        panelisi1.add(label9);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

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
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,10).toString()+"','','','','','',''","Sirkulasi Barang Keluar Masuk"); 
            }
            Valid.MyReport("rptSirkulasi.jrxml","report","::[ Transaksi Pembelian Barang ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{Valid.pindah(evt, BtnKeluar, BtnCari);}
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbar.getText()+"'", nmbar);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbar.getText()+"'", nmbar);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbar.getText()+"'", nmbar);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void BtnCari6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari6ActionPerformed
        var.setStatus(true);
        barang.emptTeks();
        barang.tampil(" order by databarang.kode_brng");
        barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_BtnCari6ActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdbar.setText("");
        nmbar.setText("");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ppGrafikJualBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikJualBanyakActionPerformed
        grafikpenjualanterbanyak grafik=new grafikpenjualanterbanyak("Grafik 10 Barang Penjualan Terbanyak"," penjualan.tgl_jual between '"+Tgl1.getSelectedItem().toString()+"' "+
                       "and '"+Tgl2.getSelectedItem().toString()+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikJualBanyakActionPerformed

    private void ppGrafikJualDikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikJualDikitActionPerformed
        grafikpenjualantersedikit grafik=new grafikpenjualantersedikit("Grafik 10 Barang Penjualan Tersedikit"," penjualan.tgl_jual between '"+Tgl1.getSelectedItem().toString()+"' "+
                       "and '"+Tgl2.getSelectedItem().toString()+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikJualDikitActionPerformed

    private void ppGrafikbeliBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbeliBanyakActionPerformed
        grafikpembelianterbanyak grafik=new grafikpembelianterbanyak("Grafik 10 Barang Pembelian Terbanyak"," pembelian.tgl_beli between '"+Tgl1.getSelectedItem().toString()+"' "+
                       "and '"+Tgl2.getSelectedItem().toString()+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikbeliBanyakActionPerformed

    private void ppGrafikbelidikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbelidikitActionPerformed
        grafikpembeliantersedikit grafik=new grafikpembeliantersedikit("Grafik 10 Barang Pembelian Tersedikit"," pembelian.tgl_beli between '"+Tgl1.getSelectedItem().toString()+"' "+
                       "and '"+Tgl2.getSelectedItem().toString()+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikbelidikitActionPerformed

    private void ppGrafikPiutangBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiutangBanyakActionPerformed
        grafikpiutangterbanyak grafik=new grafikpiutangterbanyak("Grafik 10 Barang Piutang Terbanyak"," piutang.tgl_piutang between '"+Tgl1.getSelectedItem().toString()+"' "+
                       "and '"+Tgl2.getSelectedItem().toString()+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikPiutangBanyakActionPerformed

    private void ppGrafikPiutangDikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiutangDikitActionPerformed
        grafikpiutangtersedikit grafik=new grafikpiutangtersedikit("Grafik 10 Barang Piutang Tersedikit"," piutang.tgl_piutang between '"+Tgl1.getSelectedItem().toString()+"' "+
                       "and '"+Tgl2.getSelectedItem().toString()+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikPiutangDikitActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBarang dialog = new DlgSirkulasiBarang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari6;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppGrafikJualBanyak;
    private javax.swing.JMenuItem ppGrafikJualDikit;
    private javax.swing.JMenuItem ppGrafikPiutangBanyak;
    private javax.swing.JMenuItem ppGrafikPiutangDikit;
    private javax.swing.JMenuItem ppGrafikbeliBanyak;
    private javax.swing.JMenuItem ppGrafikbelidikit;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            ps.setString(1,"%"+nmbar.getText()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+nmbar.getText()+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+nmbar.getText()+"%");
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();            
            while(rs.next()){
                totaljual=0;jumlahjual=0;totalbeli=0;jumlahbeli=0;totalpiutang=0;jumlahpiutang=0;
                totalretbeli=0;jumlahretbeli=0;totalretjual=0;jumlahretjual=0;totalretpiut=0;jumlahretpiut=0;
                jumlahpasin=0;
                
                //pembelian                
                ps2.setString(1,rs.getString(1));
                ps2.setString(2,Tgl1.getSelectedItem().toString());
                ps2.setString(3,Tgl2.getSelectedItem().toString());
                rs2=ps2.executeQuery();
                if(rs2.next()){                    
                    jumlahbeli=rs2.getDouble(1);
                    totalbeli=rs2.getDouble(2);
                }
                
                //penjualan
                ps3.setString(1,rs.getString(1));
                ps3.setString(2,Tgl1.getSelectedItem().toString());
                ps3.setString(3,Tgl2.getSelectedItem().toString());
                rs3=ps3.executeQuery();
                if(rs3.next()){                    
                    jumlahjual=rs3.getDouble(1);
                    totaljual=rs3.getDouble(2);
                }
                
                //piutang  
                ps4.setString(1,rs.getString(1));
                ps4.setString(2,Tgl1.getSelectedItem().toString());
                ps4.setString(3,Tgl2.getSelectedItem().toString());
                rs4=ps4.executeQuery();
                if(rs4.next()){                    
                    jumlahpiutang=rs4.getDouble(1);
                    totalpiutang=rs4.getDouble(2);
                }
                
                //returbeli
                ps5.setString(1,rs.getString(1));
                ps5.setString(2,Tgl1.getSelectedItem().toString());
                ps5.setString(3,Tgl2.getSelectedItem().toString());
                rs5=ps5.executeQuery();
                if(rs5.next()){                    
                    jumlahretbeli=rs5.getDouble(1);
                    totalretbeli=rs5.getDouble(2);
                }
                
                //returjual
                ps6.setString(1,rs.getString(1));
                ps6.setString(2,Tgl1.getSelectedItem().toString());
                ps6.setString(3,Tgl2.getSelectedItem().toString());
                rs6=ps6.executeQuery();
                if(rs6.next()){                    
                    jumlahretjual=rs6.getDouble(1);
                    totalretjual=rs6.getDouble(2);
                }                
                
                ps7.setString(1,rs.getString(1));
                ps7.setString(2,Tgl1.getSelectedItem().toString());
                ps7.setString(3,Tgl2.getSelectedItem().toString());
                rs7=ps7.executeQuery();
                if(rs7.next()){                    
                    jumlahretpiut=rs7.getDouble(1);
                    totalretpiut=rs7.getDouble(2);
                }
                
                ps8.setString(1,rs.getString(1));
                ps8.setString(2,Tgl1.getSelectedItem().toString());
                ps8.setString(3,Tgl2.getSelectedItem().toString());
                rs8=ps8.executeQuery();
                if(rs8.next()){                    
                    jumlahpasin=rs8.getDouble(1);
                    totalpasien=rs8.getDouble(2);
                }
                
                tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),
                               rs.getString(3),rs.getString(4),
                               Valid.SetAngka(jumlahbeli),
                               Valid.SetAngka(jumlahjual),
                               Valid.SetAngka(jumlahpasin),
                               Valid.SetAngka(jumlahpiutang),
                               Valid.SetAngka(jumlahretbeli),
                               Valid.SetAngka(jumlahretjual),
                               Valid.SetAngka(jumlahretpiut)
                              });  
                
                tabMode.addRow(new Object[]{"","Nilai Barang :","","",
                               Valid.SetAngka(totalbeli),Valid.SetAngka(totaljual),Valid.SetAngka(totalpasien),
                               Valid.SetAngka(totalpiutang),Valid.SetAngka(totalretbeli),
                               Valid.SetAngka(totalretjual),Valid.SetAngka(totalretpiut)
                              }); 
                ttltotalbeli=ttltotalbeli+totalbeli;
                ttltotaljual=ttltotaljual+totaljual;
                ttltotalpasien=ttltotalpasien+totalpasien;
                ttltotalpiutang=ttltotalpiutang+totalpiutang;
                ttltotalretbeli=ttltotalretbeli+totalretbeli;
                ttltotalretjual=ttltotalretjual+totalretjual;
                ttltotalretpiut=ttltotalretpiut+totalretpiut;
            }   
            tabMode.addRow(new Object[]{"","","","","","","","","","",""}); 
            tabMode.addRow(new Object[]{"<>>","Total :","","",
                               Valid.SetAngka(ttltotalbeli),Valid.SetAngka(ttltotaljual),Valid.SetAngka(ttltotalpasien),
                               Valid.SetAngka(ttltotalpiutang),Valid.SetAngka(ttltotalretbeli),
                               Valid.SetAngka(ttltotalretjual),Valid.SetAngka(ttltotalretpiut)
                              }); 
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }
    
    public void isCek(){
         BtnPrint.setEnabled(var.getproyeksi());
    }
    
}
