
package khanzahms;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgRawatInap extends javax.swing.JDialog {
    private final DefaultTableModel tabModeDr;
    private final DefaultTableModel tabModePr;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgCariPerawatanRanap perawatan=new DlgCariPerawatanRanap(null,false);
    public  DlgCariPerawatanRanap2 perawatan2=new DlgCariPerawatanRanap2(null,false);
    private DlgPasien pasien=new DlgPasien(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs;

    /** Creates new form DlgRawatInap
     * @param parent
     * @param modal */
    public DlgRawatInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"No.Rawat","No.Rekam Medis","Nama Pasien","ICD/Penyakit","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani","Suhu Tubuh",
                "Tensi","Hasil Pemeriksaan","Perkembangan","Tgl.Rawat","Jam Rawat","Biaya"};
        tabModeDr=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRawatDr.setModel(tabModeDr);
        //tampilDr();

        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(110);
            }else if(i==12){
                column.setPreferredWidth(110);
            }else if(i==13){
                column.setPreferredWidth(100);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row2={"No.Rawat",
                    "No.Rekam Medis",
                    "Nama Pasien",
                    "ICD/Penyakit",
                    "Perawatan/Tindakan",
                    "NIP",
                    "Petugast Yg Menangani",
                    "Suhu Tubuh",
                    "Tensi",
                    "Hasil Pemeriksaan",
                    "Perkembangan",
                    "Tgl.Rawat",
                    "Jam Rawat",
                    "Biaya"};
        tabModePr=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRawatPr.setModel(tabModePr);
        //tampilPr();

        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(110);
            }else if(i==12){
                column.setPreferredWidth(110);
            }else if(i==13){
                column.setPreferredWidth(100);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TKdPrw.setDocument(new batasInput((byte)8).getKata(TKdPrw));
        TKdPny.setDocument(new batasInput((byte)10).getKata(TKdPny));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)7).getKata(TTensi));
        TCariPasien.setDocument(new batasInput((byte)20).getKata(TCariPasien));
        THasil.setDocument(new batasInput((byte)100).getKata(THasil));
        TPrkmbngn.setDocument(new batasInput((byte)100).getKata(TPrkmbngn));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));

        
        
        pasien.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TCariPasien.setText(pasien.getTextField().getText());                
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
                       
        perawatan.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPrw.setText(perawatan.getTextField().getText());   
                isJns();
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
        
        perawatan2.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPrw.setText(perawatan2.getTextField().getText());   
                isJns();
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
        
        perawatan.dokter.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KdDok.setText(perawatan.dokter.getTextField().getText());   
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,perawatan.dokter.getTextField().getText());
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
        
        perawatan.petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdptg.setText(perawatan.petugas.getTextField().getText());   
                Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,perawatan.petugas.getTextField().getText());
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
        
        perawatan.dlgpnykt.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPny.setText(perawatan.dlgpnykt.getTextField().getText());     
                isPny();
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
       // isForm(); 
        jam();
        try {            
            ps=koneksi.prepareStatement("select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penyakit.nm_penyakit,"+
                   "concat(rawat_inap_dr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.suhu_tubuh,"+
                   "rawat_inap_dr.tensi,rawat_inap_dr.hasil,rawat_inap_dr.perkembangan,"+
                   "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat " +
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_dr inner join penyakit "+
                   "on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and penyakit.kd_penyakit=rawat_inap_dr.kd_penyakit "+
                   "and rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_dr.no_rawat like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and penyakit.nm_penyakit like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_dr.kd_dokter like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and dokter.nm_dokter like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and suhu_tubuh like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and tensi like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and hasil like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and tgl_perawatan like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and perkembangan like ? "+
                   " order by rawat_inap_dr.no_rawat desc");
            
            ps2=koneksi.prepareStatement("select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penyakit.nm_penyakit,"+
                   "concat(rawat_inap_pr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.suhu_tubuh,"+
                   "rawat_inap_pr.tensi,rawat_inap_pr.hasil,rawat_inap_pr.perkembangan,"+
                   "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,rawat_inap_pr.biaya_rawat " +
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "petugas inner join rawat_inap_pr inner join penyakit "+
                   "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and penyakit.kd_penyakit=rawat_inap_pr.kd_penyakit "+
                   "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_pr.nip=petugas.nip where  "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.no_rawat like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and penyakit.nm_penyakit like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.nip like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and petugas.nama like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.suhu_tubuh like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.tensi like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.hasil like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.tgl_perawatan like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.perkembangan like ? "+
                   "order by rawat_inap_pr.no_rawat desc");  
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

        TBiayaPr = new javax.swing.JTextField();
        TBiayaDr = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel20 = new widget.Label();
        TCariPasien = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        panelGlass7 = new widget.panelisi();
        TDokter = new widget.TextBox();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        panelGlass9 = new widget.panelisi();
        TPerawat = new widget.TextBox();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TSuhu = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        TTensi = new widget.TextBox();
        jLabel8 = new widget.Label();
        THasil = new widget.TextBox();
        TPrkmbngn = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel11 = new widget.Label();
        TKdPrw = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        TNmPrw = new widget.TextBox();
        jLabel18 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel12 = new widget.Label();
        TKdPny = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        TNmPny = new widget.TextBox();
        ChkJln = new widget.CekBox();
        BtnSeek3 = new widget.Button();
        ChkInput = new widget.CekBox();

        TBiayaPr.setEditable(false);
        TBiayaPr.setText("0");
        TBiayaPr.setName("TBiayaPr"); // NOI18N

        TBiayaDr.setEditable(false);
        TBiayaDr.setText("0");
        TBiayaDr.setName("TBiayaDr"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perawatan/Tindakan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setBackground(new java.awt.Color(215, 225, 215));
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
        BtnSimpan.setIconTextGap(3);
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
        BtnBatal.setIconTextGap(3);
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
        BtnHapus.setIconTextGap(3);
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
        BtnEdit.setIconTextGap(3);
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
        BtnPrint.setIconTextGap(3);
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
        BtnAll.setIconTextGap(3);
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
        jLabel10.setPreferredSize(new java.awt.Dimension(75, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(85, 30));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setIconTextGap(3);
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

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari2);

        jLabel20.setText("No.RM :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel20);

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass10.add(TCariPasien);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('6');
        BtnSeek4.setToolTipText("Alt+6");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        panelGlass10.add(BtnSeek4);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass10.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(50, 70, 40));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRawatDr.setAutoCreateRowSorter(true);
        tbRawatDr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDr.setName("tbRawatDr"); // NOI18N
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbRawatDr);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 641, 23);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(74, 10, 130, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(849, 10, 28, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Dokter :.", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRawatPr.setAutoCreateRowSorter(true);
        tbRawatPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatPr.setName("tbRawatPr"); // NOI18N
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRawatPr);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(null);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); // NOI18N
        panelGlass9.add(TPerawat);
        TPerawat.setBounds(206, 10, 641, 23);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass9.add(jLabel13);
        jLabel13.setBounds(0, 10, 70, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass9.add(kdptg);
        kdptg.setBounds(74, 10, 130, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(849, 10, 28, 23);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Petugas :.", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(710, 137));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(null);
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 95, 23);

        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(779, 42, 50, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(98, 12, 130, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
        });
        FormInput.add(TSuhu);
        TSuhu.setBounds(720, 42, 56, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(230, 12, 130, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(362, 12, 526, 23);

        jLabel7.setText("Suhu Badan :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(637, 42, 80, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        FormInput.add(TTensi);
        TTensi.setBounds(832, 42, 56, 23);

        jLabel8.setText("Hasil Periksa :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(null);
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 102, 95, 23);

        THasil.setHighlighter(null);
        THasil.setName("THasil"); // NOI18N
        THasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THasilKeyPressed(evt);
            }
        });
        FormInput.add(THasil);
        THasil.setBounds(98, 102, 320, 23);

        TPrkmbngn.setHighlighter(null);
        TPrkmbngn.setName("TPrkmbngn"); // NOI18N
        TPrkmbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPrkmbngnKeyPressed(evt);
            }
        });
        FormInput.add(TPrkmbngn);
        TPrkmbngn.setBounds(562, 102, 326, 23);

        jLabel9.setText("Perkembangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(449, 102, 110, 23);

        jLabel11.setText("Tndkn/Tghan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 72, 95, 23);

        TKdPrw.setName("TKdPrw"); // NOI18N
        TKdPrw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrwKeyPressed(evt);
            }
        });
        FormInput.add(TKdPrw);
        TKdPrw.setBounds(98, 72, 80, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('3');
        BtnSeek1.setToolTipText("Alt+3");
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
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(468, 72, 28, 23);

        TNmPrw.setEditable(false);
        TNmPrw.setName("TNmPrw"); // NOI18N
        FormInput.add(TNmPrw);
        TNmPrw.setBounds(180, 72, 285, 23);

        jLabel18.setText("Tanggal :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(532, 72, 71, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPTgl.setDisplayFormat("yyyy-MM-dd");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(606, 72, 100, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setOpaque(false);
        cmbJam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(709, 72, 50, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setOpaque(false);
        cmbMnt.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(761, 72, 50, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setOpaque(false);
        cmbDtk.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(813, 72, 50, 23);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 42, 95, 23);

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);
        TKdPny.setBounds(98, 42, 80, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('2');
        BtnSeek2.setToolTipText("Alt+2");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
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
        BtnSeek2.setBounds(592, 42, 28, 23);

        TNmPny.setEditable(false);
        TNmPny.setName("TNmPny"); // NOI18N
        FormInput.add(TNmPny);
        TNmPny.setBounds(180, 42, 410, 23);

        ChkJln.setBackground(new java.awt.Color(235, 255, 235));
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setForeground(new java.awt.Color(153, 0, 51));
        ChkJln.setSelected(true);
        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(865, 72, 23, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
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
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(498, 72, 28, 23);

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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,TKdPrw);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,THasil,TTensi);
}//GEN-LAST:event_TSuhuKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,DTPTgl);
}//GEN-LAST:event_TTensiKeyPressed

    private void THasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THasilKeyPressed
        Valid.pindah(evt,TKdPrw,TSuhu);
}//GEN-LAST:event_THasilKeyPressed

    private void TPrkmbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPrkmbngnKeyPressed
        if(TabRawat.getSelectedIndex()==0){
            Valid.pindah(evt,cmbDtk,KdDok);
        }else if(TabRawat.getSelectedIndex()==1){
            Valid.pindah(evt,cmbDtk,kdptg);
        }
}//GEN-LAST:event_TPrkmbngnKeyPressed

    private void TKdPrwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else{            
            Valid.pindah(evt,TNoRw,THasil);
        }
}//GEN-LAST:event_TKdPrwKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(TDokter,"Dokter");
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(TPerawat,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false); 
                perawatan.setPetugas(KdDok.getText(),TDokter.getText(),TSuhu.getText(),TTensi.getText(),THasil.getText(),TPrkmbngn.getText());
            }else if(TabRawat.getSelectedIndex()==1){
                perawatan.setNoRm(TNoRw.getText(),"rawat_inap_pr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false);  
                perawatan.setPetugas(kdptg.getText(),TPerawat.getText(),TSuhu.getText(),TTensi.getText(),THasil.getText(),TPrkmbngn.getText());
            }
            perawatan.emptTeks();
            perawatan.isCek();
            perawatan.tampil();
            perawatan.setSize(this.getWidth()-40,this.getHeight()-40);
            perawatan.setLocationRelativeTo(internalFrame1);
            perawatan.setVisible(true);
        }    
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,TKdPrw,THasil);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
            Valid.textKosong(TKdPrw,"perawatan");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(KdDok,"Dokter");
        }else if(TBiayaDr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan dokter..!!!");
            KdDok.requestFocus();
        }else if(TBiayaPr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan perawat/paramedis..!!!");
            kdptg.requestFocus();
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(kdptg,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Sequel.menyimpan("rawat_inap_dr","'"+TNoRw.getText()+"','"+
                        TKdPny.getText()+"','"+
                        TKdPrw.getText()+"','"+
                        KdDok.getText()+"','"+
                        TSuhu.getText()+"','"+
                        TTensi.getText()+"','"+
                        THasil.getText()+"','"+
                        TPrkmbngn.getText()+"','"+
                        DTPTgl.getSelectedItem()+"','"+
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                        TBiayaDr.getText()+"'","Data");
                tampilDr();
            }else if(TabRawat.getSelectedIndex()==1){
                Sequel.menyimpan("rawat_inap_pr","'"+TNoRw.getText()+"','"+
                        TKdPny.getText()+"','"+
                        TKdPrw.getText()+"','"+
                        kdptg.getText()+"','"+
                        TSuhu.getText()+"','"+
                        TTensi.getText()+"','"+
                        THasil.getText()+"','"+
                        TPrkmbngn.getText()+"','"+
                        DTPTgl.getSelectedItem()+"','"+
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                        TBiayaPr.getText()+"'","Data");
                tampilPr();
            }
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,KdDok,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,kdptg,BtnBatal);
            }
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
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeDr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                try{
                    Sequel.queryu("delete from rawat_inap_dr where no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+TKdPrw.getText()+"' and kd_dokter='"+KdDok.getText()+"' and tgl_perawatan='"+DTPTgl.getSelectedItem()+"' and jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and kd_penyakit='"+TKdPny.getText()+"' ");
                    tampilDr();
                }catch(Exception e){
                    System.out.println("Pesan Error : "+e);
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                }
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModePr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                try{
                    Sequel.queryu("delete from rawat_inap_pr where no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+TKdPrw.getText()+"' and nip='"+kdptg.getText()+"' and tgl_perawatan='"+DTPTgl.getSelectedItem()+"' and jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and kd_penyakit='"+TKdPny.getText()+"' ");
                    tampilPr();
                }catch(Exception e){
                    System.out.println("Pesan Error : "+e);
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                }
            }
        }

        emptTeks();
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
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeDr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModeDr.getRowCount()!=0){
                String pas="";
                if(!TCariPasien.getText().equals("")){
                   pas=" and reg_periksa.no_rkm_medis='"+TCariPasien.getText()+"' "; 
                }
                String tgl=" rawat_inap_dr.tgl_perawatan between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' "+pas;
                Valid.MyReport("rptInapDr.jrxml","report","::[ Data Rawat Inap Yang Ditangani Dokter ]::",
                   "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penyakit.nm_penyakit,"+
                   "jns_perawatan_inap.nm_perawatan,rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.suhu_tubuh,"+
                   "rawat_inap_dr.tensi,rawat_inap_dr.hasil,rawat_inap_dr.perkembangan,"+
                   "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat " +
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_dr inner join penyakit "+
                   "on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and penyakit.kd_penyakit=rawat_inap_dr.kd_penyakit "+
                   "and rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where "+tgl+" and rawat_inap_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and suhu_tubuh like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and tensi like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and hasil like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and perkembangan like '%"+TCari.getText().trim()+"%' "+
                   " order by rawat_inap_dr.no_rawat desc");
                    
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModePr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModePr.getRowCount()!=0){
                String pas="";
                if(!TCariPasien.getText().equals("")){
                   pas=" and reg_periksa.no_rkm_medis='"+TCariPasien.getText()+"' "; 
                }
                String tgl=" rawat_inap_pr.tgl_perawatan between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' "+pas;
                Valid.MyReport("rptInapPr.jrxml","report","::[ Data Rawat Inap Yang Ditangani Perawat ]::","select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penyakit.nm_penyakit,"+
                   "jns_perawatan_inap.nm_perawatan,rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.suhu_tubuh,"+
                   "rawat_inap_pr.tensi,rawat_inap_pr.hasil,rawat_inap_pr.perkembangan,"+
                   "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,rawat_inap_pr.biaya_rawat " +
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "petugas inner join rawat_inap_pr inner join penyakit "+
                   "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and penyakit.kd_penyakit=rawat_inap_pr.kd_penyakit "+
                   "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_pr.nip=petugas.nip where  "+
                    tgl+"and rawat_inap_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_pr.suhu_tubuh like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_pr.tensi like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_pr.hasil like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_pr.tgl_perawatan like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and rawat_inap_pr.perkembangan like '%"+TCari.getText().trim()+"%' "+
                   "order by rawat_inap_pr.no_rawat desc");
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
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCariPasien.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }
        TCari.setText("");
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
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }
}//GEN-LAST:event_TabRawatMouseClicked

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TTensi,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,TPrkmbngn);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        var.setStatus(true);
        pasien.tampil();
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(rootPaneCheckingEnabled);
}//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        Valid.pindah(evt,TCariPasien,DTPCari1);
}//GEN-LAST:event_BtnSeek4KeyPressed

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrMouseClicked
        if(tabModeDr.getRowCount()!=0){
            try {
                getDataDr();
            } catch (java.lang.NullPointerException e) {
            }           
            
        }
}//GEN-LAST:event_tbRawatDrMouseClicked

    private void tbRawatDrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrKeyPressed
        if(tabModeDr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDr();
                } catch (java.lang.NullPointerException e) {
                }
            }
                       
        }
}//GEN-LAST:event_tbRawatDrKeyPressed

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrMouseClicked
        if(tabModePr.getRowCount()!=0){
            try {
                getDataPr();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbRawatPrMouseClicked

    private void tbRawatPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrKeyPressed
        if(tabModePr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
        }
}//GEN-LAST:event_tbRawatPrKeyPressed

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
        }else{            
            Valid.pindah(evt,TPrkmbngn,BtnSimpan);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        perawatan.dokter.emptTeks();
        perawatan.dokter.isCek();
        perawatan.dokter.tampil(" order by kd_dokter");
        perawatan.dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        perawatan.dokter.setLocationRelativeTo(internalFrame1);
        perawatan.dokter.setVisible(true);
}//GEN-LAST:event_BtnSeekDokterActionPerformed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,kdptg.getText());
        }else{
            Valid.pindah(evt,TPrkmbngn,BtnSimpan);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        perawatan.petugas.emptTeks();
        perawatan.petugas.isCek();
        perawatan.petugas.tampil();
        perawatan.petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        perawatan.petugas.setLocationRelativeTo(internalFrame1);
        perawatan.petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugasActionPerformed

private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPny();
        }else{            
            Valid.pindah(evt,TNoRw,TKdPrw);
        }
}//GEN-LAST:event_TKdPnyKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        perawatan.dlgpnykt.emptTeks();
        perawatan.dlgpnykt.isCek();
        perawatan.dlgpnykt.tampil();
        perawatan.dlgpnykt.setSize(this.getWidth()-40,this.getHeight()-40);
        perawatan.dlgpnykt.setLocationRelativeTo(internalFrame1);
        perawatan.dlgpnykt.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        Valid.pindah(evt,TKdPny,TKdPrw);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
            Valid.textKosong(TKdPrw,"perawatan");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(KdDok,"Dokter");
        }else if(TBiayaDr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan dokter..!!!");
            KdDok.requestFocus();
        }else if(TBiayaPr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan perawat/paramedis..!!!");
            kdptg.requestFocus();
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(kdptg,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Sequel.mengedit("rawat_inap_dr","no_rawat='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),0)+
                        "' and kd_penyakit='"+Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString()+"' ")+
                        "' and kd_jenis_prw='"+Sequel.cariIsi("select kd_jenis_prw from jns_perawatan_inap where concat(kd_jenis_prw,' ',nm_perawatan)='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString()+"' ")+
                        "' and kd_dokter='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5)+
                        "' and tgl_perawatan='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11)+
                        "' and jam_rawat='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12)+"'",
                        "no_rawat='"+TNoRw.getText()+"',kd_penyakit='"+TKdPny.getText()+"',kd_jenis_prw='"+TKdPrw.getText()+
                        "',kd_dokter='"+KdDok.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+
                        "',hasil='"+THasil.getText()+"',perkembangan='"+TPrkmbngn.getText()+"',biaya_rawat='"+TBiayaDr.getText()+
                        "',tgl_perawatan='"+DTPTgl.getSelectedItem()+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'");
                tampilDr();
            }else if(TabRawat.getSelectedIndex()==1){
                Sequel.mengedit("rawat_inap_pr","no_rawat='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),0)+
                        "' and kd_penyakit='"+Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString()+"' ")+
                        "' and kd_jenis_prw='"+Sequel.cariIsi("select kd_jenis_prw from jns_perawatan_inap where concat(kd_jenis_prw,' ',nm_perawatan)='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString()+"' ")+
                        "' and nip='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5)+
                        "' and tgl_perawatan='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11)+
                        "' and jam_rawat='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12)+"'",
                        "no_rawat='"+TNoRw.getText()+"',kd_penyakit='"+TKdPny.getText()+"',kd_jenis_prw='"+TKdPrw.getText()+
                        "',nip='"+kdptg.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+
                        "',hasil='"+THasil.getText()+"',perkembangan='"+TPrkmbngn.getText()+"',biaya_rawat='"+TBiayaPr.getText()+
                        "',tgl_perawatan='"+DTPTgl.getSelectedItem()+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'");
                tampilPr();
            }
            
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(TDokter,"Dokter");
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(TPerawat,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                perawatan2.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false); 
                perawatan2.setPetugas(KdDok.getText(),TDokter.getText(),TSuhu.getText(),TTensi.getText(),THasil.getText(),TPrkmbngn.getText());
            }else if(TabRawat.getSelectedIndex()==1){
                perawatan2.setNoRm(TNoRw.getText(),"rawat_inap_pr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false);  
                perawatan2.setPetugas(kdptg.getText(),TPerawat.getText(),TSuhu.getText(),TTensi.getText(),THasil.getText(),TPrkmbngn.getText());
            }
            perawatan2.emptTeks();
            perawatan2.isCek();
            perawatan2.tampil();
            perawatan2.setSize(this.getWidth()-40,this.getHeight()-40);
            perawatan2.setLocationRelativeTo(internalFrame1);
            perawatan2.setVisible(true);
        }    
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatInap dialog = new DlgRawatInap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDok;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private javax.swing.JTextField TBiayaDr;
    private javax.swing.JTextField TBiayaPr;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TDokter;
    private widget.TextBox THasil;
    private widget.TextBox TKdPny;
    private widget.TextBox TKdPrw;
    private widget.TextBox TNmPny;
    private widget.TextBox TNmPrw;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPerawat;
    private widget.TextBox TPrkmbngn;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.TextBox kdptg;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatPr;
    // End of variables declaration//GEN-END:variables

    public void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            ps.setString(1,DTPCari1.getSelectedItem().toString());
            ps.setString(2,DTPCari2.getSelectedItem().toString());
            ps.setString(3,"%"+TCariPasien.getText()+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            ps.setString(5,DTPCari1.getSelectedItem().toString());
            ps.setString(6,DTPCari2.getSelectedItem().toString());
            ps.setString(7,"%"+TCariPasien.getText()+"%");
            ps.setString(8,"%"+TCari.getText().trim()+"%");
            ps.setString(9,DTPCari1.getSelectedItem().toString());
            ps.setString(10,DTPCari2.getSelectedItem().toString());
            ps.setString(11,"%"+TCariPasien.getText()+"%");
            ps.setString(12,"%"+TCari.getText().trim()+"%");
            ps.setString(13,DTPCari1.getSelectedItem().toString());
            ps.setString(14,DTPCari2.getSelectedItem().toString());
            ps.setString(15,"%"+TCariPasien.getText()+"%");
            ps.setString(16,"%"+TCari.getText().trim()+"%");
            ps.setString(17,DTPCari1.getSelectedItem().toString());
            ps.setString(18,DTPCari2.getSelectedItem().toString());
            ps.setString(19,"%"+TCariPasien.getText()+"%");
            ps.setString(20,"%"+TCari.getText().trim()+"%");
            ps.setString(21,DTPCari1.getSelectedItem().toString());
            ps.setString(22,DTPCari2.getSelectedItem().toString());
            ps.setString(23,"%"+TCariPasien.getText()+"%");
            ps.setString(24,"%"+TCari.getText().trim()+"%");
            ps.setString(25,DTPCari1.getSelectedItem().toString());
            ps.setString(26,DTPCari2.getSelectedItem().toString());
            ps.setString(27,"%"+TCariPasien.getText()+"%");
            ps.setString(28,"%"+TCari.getText().trim()+"%");
            ps.setString(29,DTPCari1.getSelectedItem().toString());
            ps.setString(30,DTPCari2.getSelectedItem().toString());
            ps.setString(31,"%"+TCariPasien.getText()+"%");
            ps.setString(32,"%"+TCari.getText().trim()+"%");
            ps.setString(33,DTPCari1.getSelectedItem().toString());
            ps.setString(34,DTPCari2.getSelectedItem().toString());
            ps.setString(35,"%"+TCariPasien.getText()+"%");
            ps.setString(36,"%"+TCari.getText().trim()+"%");
            ps.setString(37,DTPCari1.getSelectedItem().toString());
            ps.setString(38,DTPCari2.getSelectedItem().toString());
            ps.setString(39,"%"+TCariPasien.getText()+"%");
            ps.setString(40,"%"+TCari.getText().trim()+"%");
            ps.setString(41,DTPCari1.getSelectedItem().toString());
            ps.setString(42,DTPCari2.getSelectedItem().toString());
            ps.setString(43,"%"+TCariPasien.getText()+"%");
            ps.setString(44,"%"+TCari.getText().trim()+"%");
            ps.setString(45,DTPCari1.getSelectedItem().toString());
            ps.setString(46,DTPCari2.getSelectedItem().toString());
            ps.setString(47,"%"+TCariPasien.getText()+"%");
            ps.setString(48,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
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
                               rs.getString(10),
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString(13),
                               Valid.SetAngka(rs.getDouble(14))};
                tabModeDr.addRow(data);
            }           
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabModeDr.getRowCount());
    }

    public void load(String param) {
        if(! param.equals("")){
            KdDok.setText(param);   
            KdDok.setEditable(false);
            BtnSeekDokter.setEnabled(false);
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,param);
            kdptg.setText(param);
            kdptg.setEditable(false);
            BtnSeekPetugas.setEnabled(false);
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,param);
        }else if(param.equals("")){
            KdDok.setText("");   
            KdDok.setEditable(true);
            BtnSeekDokter.setEnabled(true);
            kdptg.setText("");
            kdptg.setEditable(true);
            BtnSeekPetugas.setEnabled(true);
        }

    }

    public void emptTeks() {
        //TNoRw.setText("");
        TSuhu.setText("");
        TKdPrw.setText("");
        TNmPrw.setText("");
        TTensi.setText("");
        THasil.setText("");
        TPrkmbngn.setText("");
        TBiayaDr.setText("0");
        TBiayaPr.setText("0");
        DTPTgl.setDate(new Date());
        TNoRw.requestFocus();        
    }

    private void isRawat(){
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    private void isJns(){
        Sequel.cariIsi("select nm_perawatan from jns_perawatan_inap where kd_jenis_prw=? ",TNmPrw,TKdPrw.getText());
        Sequel.cariIsi("select total_byrdr from jns_perawatan_inap where kd_jenis_prw=? ",TBiayaDr,TKdPrw.getText());
        Sequel.cariIsi("select total_byrpr from jns_perawatan_inap where kd_jenis_prw=? ",TBiayaPr,TKdPrw.getText());
    }

    private void getDataDr() {
        if(tbRawatDr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),0).toString());
            TNoRM.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString());
            TPasien.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),2).toString());
            TNmPny.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString()); 
            KdDok.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5).toString());
            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            TSuhu.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7).toString());
            TTensi.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString());
            THasil.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString());
            TPrkmbngn.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),10).toString());            
            cmbJam.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString());
            Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit=? ",TKdPny,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString());            
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan_inap where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());    
            Sequel.cariIsi("select nm_perawatan from jns_perawatan_inap where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TNmPrw,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());             
        }
    }

    private void tampilPr() {
        Valid.tabelKosong(tabModePr);
        try{                  
            ps2.setString(1,DTPCari1.getSelectedItem().toString());
            ps2.setString(2,DTPCari2.getSelectedItem().toString());
            ps2.setString(3,"%"+TCariPasien.getText()+"%");
            ps2.setString(4,"%"+TCari.getText().trim()+"%");
            ps2.setString(5,DTPCari1.getSelectedItem().toString());
            ps2.setString(6,DTPCari2.getSelectedItem().toString());
            ps2.setString(7,"%"+TCariPasien.getText()+"%");
            ps2.setString(8,"%"+TCari.getText().trim()+"%");
            ps2.setString(9,DTPCari1.getSelectedItem().toString());
            ps2.setString(10,DTPCari2.getSelectedItem().toString());
            ps2.setString(11,"%"+TCariPasien.getText()+"%");
            ps2.setString(12,"%"+TCari.getText().trim()+"%");
            ps2.setString(13,DTPCari1.getSelectedItem().toString());
            ps2.setString(14,DTPCari2.getSelectedItem().toString());
            ps2.setString(15,"%"+TCariPasien.getText()+"%");
            ps2.setString(16,"%"+TCari.getText().trim()+"%");
            ps2.setString(17,DTPCari1.getSelectedItem().toString());
            ps2.setString(18,DTPCari2.getSelectedItem().toString());
            ps2.setString(19,"%"+TCariPasien.getText()+"%");
            ps2.setString(20,"%"+TCari.getText().trim()+"%");
            ps2.setString(21,DTPCari1.getSelectedItem().toString());
            ps2.setString(22,DTPCari2.getSelectedItem().toString());
            ps2.setString(23,"%"+TCariPasien.getText()+"%");
            ps2.setString(24,"%"+TCari.getText().trim()+"%");
            ps2.setString(25,DTPCari1.getSelectedItem().toString());
            ps2.setString(26,DTPCari2.getSelectedItem().toString());
            ps2.setString(27,"%"+TCariPasien.getText()+"%");
            ps2.setString(28,"%"+TCari.getText().trim()+"%");
            ps2.setString(29,DTPCari1.getSelectedItem().toString());
            ps2.setString(30,DTPCari2.getSelectedItem().toString());
            ps2.setString(31,"%"+TCariPasien.getText()+"%");
            ps2.setString(32,"%"+TCari.getText().trim()+"%");
            ps2.setString(33,DTPCari1.getSelectedItem().toString());
            ps2.setString(34,DTPCari2.getSelectedItem().toString());
            ps2.setString(35,"%"+TCariPasien.getText()+"%");
            ps2.setString(36,"%"+TCari.getText().trim()+"%");
            ps2.setString(37,DTPCari1.getSelectedItem().toString());
            ps2.setString(38,DTPCari2.getSelectedItem().toString());
            ps2.setString(39,"%"+TCariPasien.getText()+"%");
            ps2.setString(40,"%"+TCari.getText().trim()+"%");
            ps2.setString(41,DTPCari1.getSelectedItem().toString());
            ps2.setString(42,DTPCari2.getSelectedItem().toString());
            ps2.setString(43,"%"+TCariPasien.getText()+"%");
            ps2.setString(44,"%"+TCari.getText().trim()+"%");
            ps2.setString(45,DTPCari1.getSelectedItem().toString());
            ps2.setString(46,DTPCari2.getSelectedItem().toString());
            ps2.setString(47,"%"+TCariPasien.getText()+"%");
            ps2.setString(48,"%"+TCari.getText().trim()+"%");
            rs=ps2.executeQuery();
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
                               rs.getString(10),
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString(13),
                               Valid.SetAngka(rs.getDouble(14))};
                tabModePr.addRow(data);
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabModePr.getRowCount());
    }

    private void getDataPr() {
        if(tbRawatPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),0).toString());
            TNoRM.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString());
            TPasien.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),2).toString());        
            TNmPny.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString());         
            TNmPrw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());
            kdptg.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5).toString());
            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),6).toString());
            TSuhu.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7).toString());
            TTensi.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString());
            THasil.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString());
            TPrkmbngn.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),10).toString());
            cmbJam.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString());
            Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit=? ",TKdPny,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString());            
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan_inap where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());    
            Sequel.cariIsi("select nm_perawatan from jns_perawatan_inap where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TNmPrw,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());             
        }
    }
    
    public void setNoRm(String norwt,Date awal,Date akhir) {
        TNoRw.setText(norwt);
        KdDok.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt));
        isRawat();
        isPsien();      
        TKdPny.setText("-");
        TNmPny.setText("-");
        DTPCari1.setDate(awal);
        DTPCari2.setDate(akhir);
        TCari.setText(norwt);
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
    }

    private void isPny() {
        Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,158));
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
        BtnSimpan.setEnabled(var.getranap());
        BtnHapus.setEnabled(var.getranap());
        BtnEdit.setEnabled(var.getranap());
        BtnPrint.setEnabled(var.getranap());
        
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
}
