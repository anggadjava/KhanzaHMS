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
package khanzahms;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.grafikjkel;
import fungsi.grafikpasienperagama;
import fungsi.grafikpasienperpekerjaaan;
import fungsi.grafiksql;
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
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author igos
 */
public class DlgPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    public  DlgKabupaten kab=new DlgKabupaten(null,false);
    public  DlgKecamatan kec=new DlgKecamatan(null,false);
    public  DlgKelurahan kel=new DlgKelurahan(null,false);
    private int pilih=0,z=0,j=0,mulai=0;
    private double jumlah=0,x=0,i=0;
    private int[] hlm;
    private String klg="SAUDARA",say="",awal="0";
    private PreparedStatement ps,pshal,pscariwilayah;
    private ResultSet rs;

    /** Creates new form DlgPas
     * @param parent
     * @param modal */
    public DlgPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"No.Rekam Medis",
                      "Nama Pasien",
                      "No.SIM/KTP",
                      "J.K",
                      "Tmp.Lahir",
                      "Tgl.Lahir",
                      "Alamat",
                      "G.D.",
                      "Pekerjaan",
                      "Stts.Nikah",
                      "Agama",
                      "Tgl.Daftar",
                      "No.Telp/HP",
                      "Umur",
                      "Pendidikan",
                      "Keluarga",
                      "Nama Keluarga",
                      "Png.Jawab/Asuransi/Askes",
                      "Daftar"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPasien.setModel(tabMode);

        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbPasien.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (z = 0; z < 19; z++) {
            TableColumn column = tbPasien.getColumnModel().getColumn(z);
            if(z==0){
                column.setPreferredWidth(110);
            }else if(z==1){
                column.setPreferredWidth(200);
            }else if(z==2){
                column.setPreferredWidth(100);
            }else if(z==3){
                column.setPreferredWidth(40);
            }else if(z==4){
                column.setPreferredWidth(150);
            }else if(z==5){
                column.setPreferredWidth(140);
            }else if(z==6){
                column.setPreferredWidth(200);
            }else if(z==7){
                column.setPreferredWidth(40);
            }else if(z==8){
                column.setPreferredWidth(100);
            }else if(z==9){
                column.setPreferredWidth(80);
            }else if(z==10){
                column.setPreferredWidth(80);
            }else if(z==11){
                column.setPreferredWidth(100);
            }else if(z==12){
                column.setPreferredWidth(80);
            }else if(z==13){
                column.setPreferredWidth(50);
            }else if(z==14){
                column.setPreferredWidth(80);
            }else if(z==15){
                column.setPreferredWidth(80);
            }else if(z==16){
                column.setPreferredWidth(150);
            }else if(z==17){
                column.setPreferredWidth(150);
            }else if(z==18){
                column.setPreferredWidth(60);
            }
        }
        tbPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TNo.setDocument(new batasInput((byte)10).getKata(TNo));
        TNm.setDocument(new batasInput((byte)40).getKata(TNm));
        TKtp.setDocument(new batasInput((byte)20).getKata(TKtp));
        Kdpnj.setDocument(new batasInput((byte)3).getKata(Kdpnj));
        TTlp.setDocument(new batasInput((byte)13).getOnlyAngka(TTlp));
        TTmp.setDocument(new batasInput((byte)15).getKata(TTmp));
        TAlmt.setDocument(new batasInput((int)200).getFilter(TAlmt));
        TPkj.setDocument(new batasInput((byte)15).getKata(TPkj));
        TUmur.setDocument(new batasInput((byte)20).getKata(TUmur));
        TSaudara.setDocument(new batasInput((byte)50).getKata(TSaudara));
        Kabupaten.setDocument(new batasInput((byte)60).getFilter(Kabupaten));
        Kecamatan.setDocument(new batasInput((byte)60).getFilter(Kecamatan));
        Kelurahan.setDocument(new batasInput((byte)60).getFilter(Kelurahan));
        Kabupaten2.setDocument(new batasInput((byte)60).getFilter(Kabupaten2));
        Kecamatan2.setDocument(new batasInput((byte)60).getFilter(Kecamatan2));
        Kelurahan2.setDocument(new batasInput((byte)60).getFilter(Kelurahan2));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        ChkInput.setSelected(false);
        
        penjab.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                Kdpnj.setText(penjab.getTextField().getText());
                Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
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
        
        kab.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                if(pilih==1){                    
                    Kabupaten.setText(kab.getTextField().getText());
                }else if(pilih==2){                    
                    Kabupaten2.setText(kab.getTextField().getText());
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
        
        kec.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                if(pilih==1){  
                    Kecamatan.setText(kec.getTextField().getText());                    
                }else if(pilih==2){  
                    Kecamatan2.setText(kec.getTextField().getText());  
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
        
        kel.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                if(pilih==1){ 
                    Kelurahan.setText(kel.getTextField().getText());
                }else if(pilih==2){                     
                    Kelurahan2.setText(kel.getTextField().getText());
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
        
        try{
            ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ? and pasien.no_rkm_medis like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_pasien like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_ktp like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.jk like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tmp_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and penjab.png_jawab like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.alamat like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.gol_darah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.pekerjaan like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.stts_nikah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.agama like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_daftar like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_tlp like ?  order by pasien.no_rkm_medis desc LIMIT ? ,500");
            pshal=koneksi.prepareStatement("select count(pasien.no_rkm_medis) as jumlah from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ? and pasien.no_rkm_medis like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_pasien like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_ktp like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.jk like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tmp_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and penjab.png_jawab like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.alamat like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.gol_darah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.pekerjaan like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.stts_nikah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.agama like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_daftar like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_tlp like ?  order by pasien.no_rkm_medis desc");  
            pscariwilayah=koneksi.prepareStatement(
                        "select pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?");
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnKartu = new javax.swing.JMenuItem();
        MnKartu1 = new javax.swing.JMenuItem();
        MnKartu2 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnKartuStatus = new javax.swing.JMenuItem();
        MnResumePasien = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        MnLaporanRM = new javax.swing.JMenuItem();
        MnLaporanIGD = new javax.swing.JMenuItem();
        MnLembarKeluarMasuk = new javax.swing.JMenuItem();
        MnLembarAnamNesa = new javax.swing.JMenuItem();
        MnLembarGrafik = new javax.swing.JMenuItem();
        MnLembarCatatanPerkembangan = new javax.swing.JMenuItem();
        MnLembarCatatanKeperawatan = new javax.swing.JMenuItem();
        MnLaporanAnestesia = new javax.swing.JMenuItem();
        ppKelahiranBayi = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ppGrafikPerAgama = new javax.swing.JMenuItem();
        ppGrafikPerPekerjaan = new javax.swing.JMenuItem();
        ppGrafikjkbayi = new javax.swing.JMenuItem();
        ppGrafikDemografi = new javax.swing.JMenuItem();
        ppRegistrasi = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        Kd2 = new widget.TextBox();
        DlgDemografi = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        BtnPrint2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        Kelurahan2 = new widget.TextBox();
        BtnSeek8 = new widget.Button();
        Kecamatan2 = new widget.TextBox();
        BtnSeek9 = new widget.Button();
        Kabupaten2 = new widget.TextBox();
        BtnSeek10 = new widget.Button();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        BtnPrint3 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPasien = new widget.Table();
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
        panelGlass9 = new widget.panelisi();
        jLabel11 = new widget.Label();
        Carialamat = new widget.TextBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel6 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TTmp = new widget.TextBox();
        CmbJk = new widget.ComboBox();
        TNm = new widget.TextBox();
        jLabel8 = new widget.Label();
        CMbGd = new widget.ComboBox();
        jLabel9 = new widget.Label();
        jLabel13 = new widget.Label();
        DTPLahir = new widget.Tanggal();
        jLabel18 = new widget.Label();
        cmbAgama = new widget.ComboBox();
        jLabel19 = new widget.Label();
        CmbStts = new widget.ComboBox();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        jLabel12 = new widget.Label();
        TPkj = new widget.TextBox();
        TAlmt = new widget.TextBox();
        TTlp = new widget.TextBox();
        TNo = new widget.TextBox();
        jLabel15 = new widget.Label();
        TKtp = new widget.TextBox();
        DTPDaftar = new widget.Tanggal();
        jLabel22 = new widget.Label();
        jLabel17 = new widget.Label();
        TUmur = new widget.TextBox();
        jLabel23 = new widget.Label();
        CMbPnd = new widget.ComboBox();
        TSaudara = new widget.TextBox();
        R5 = new widget.RadioButton();
        R4 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R1 = new widget.RadioButton();
        jLabel24 = new widget.Label();
        Kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        Kelurahan = new widget.TextBox();
        Kecamatan = new widget.TextBox();
        Kabupaten = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnSeek3 = new widget.Button();
        BtnSeek4 = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKartu.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu.setForeground(java.awt.Color.darkGray);
        MnKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu.setText("Kartu Berobat Depan");
        MnKartu.setName("MnKartu"); // NOI18N
        MnKartu.setPreferredSize(new java.awt.Dimension(250, 25));
        MnKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartuActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKartu);

        MnKartu1.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu1.setForeground(java.awt.Color.darkGray);
        MnKartu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu1.setText("Kartu Berobat Belakang");
        MnKartu1.setName("MnKartu1"); // NOI18N
        MnKartu1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnKartu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKartu1);

        MnKartu2.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu2.setForeground(java.awt.Color.darkGray);
        MnKartu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu2.setText("Kartu Berobat Tanpa Backgroud");
        MnKartu2.setName("MnKartu2"); // NOI18N
        MnKartu2.setPreferredSize(new java.awt.Dimension(250, 25));
        MnKartu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKartu2);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(java.awt.Color.darkGray);
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Kartu Indeks Keseluruhan");
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(250, 25));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBarcode);

        MnKartuStatus.setBackground(new java.awt.Color(255, 255, 255));
        MnKartuStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartuStatus.setForeground(java.awt.Color.darkGray);
        MnKartuStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartuStatus.setText("Kartu Indeks Pasien Yang Dipilih");
        MnKartuStatus.setName("MnKartuStatus"); // NOI18N
        MnKartuStatus.setPreferredSize(new java.awt.Dimension(250, 25));
        MnKartuStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartuStatusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKartuStatus);

        MnResumePasien.setBackground(new java.awt.Color(255, 255, 255));
        MnResumePasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResumePasien.setForeground(java.awt.Color.darkGray);
        MnResumePasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResumePasien.setText("Resume Perawatan Pasien");
        MnResumePasien.setName("MnResumePasien"); // NOI18N
        MnResumePasien.setPreferredSize(new java.awt.Dimension(250, 28));
        MnResumePasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResumePasienActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnResumePasien);

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setForeground(java.awt.Color.darkGray);
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu1.setText("Laporan");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(180, 30));

        MnLaporanRM.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRM.setForeground(java.awt.Color.darkGray);
        MnLaporanRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRM.setText("Laporan Rekam Medik");
        MnLaporanRM.setName("MnLaporanRM"); // NOI18N
        MnLaporanRM.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLaporanRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRMActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRM);

        MnLaporanIGD.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanIGD.setForeground(java.awt.Color.darkGray);
        MnLaporanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanIGD.setText("Laporan IGD");
        MnLaporanIGD.setName("MnLaporanIGD"); // NOI18N
        MnLaporanIGD.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLaporanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanIGDActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanIGD);

        MnLembarKeluarMasuk.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarKeluarMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarKeluarMasuk.setForeground(java.awt.Color.darkGray);
        MnLembarKeluarMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarKeluarMasuk.setText("Lembar Masuk Keluar");
        MnLembarKeluarMasuk.setName("MnLembarKeluarMasuk"); // NOI18N
        MnLembarKeluarMasuk.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLembarKeluarMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarKeluarMasukActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarKeluarMasuk);

        MnLembarAnamNesa.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarAnamNesa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarAnamNesa.setForeground(java.awt.Color.darkGray);
        MnLembarAnamNesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarAnamNesa.setText("Lembar Anamnesa");
        MnLembarAnamNesa.setName("MnLembarAnamNesa"); // NOI18N
        MnLembarAnamNesa.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLembarAnamNesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarAnamNesaActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarAnamNesa);

        MnLembarGrafik.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarGrafik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarGrafik.setForeground(java.awt.Color.darkGray);
        MnLembarGrafik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarGrafik.setText("Lembar Grafik");
        MnLembarGrafik.setName("MnLembarGrafik"); // NOI18N
        MnLembarGrafik.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLembarGrafik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarGrafikActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarGrafik);

        MnLembarCatatanPerkembangan.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarCatatanPerkembangan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCatatanPerkembangan.setForeground(java.awt.Color.darkGray);
        MnLembarCatatanPerkembangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCatatanPerkembangan.setText("Lembar Catatan Perkembangan");
        MnLembarCatatanPerkembangan.setName("MnLembarCatatanPerkembangan"); // NOI18N
        MnLembarCatatanPerkembangan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLembarCatatanPerkembangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCatatanPerkembanganActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarCatatanPerkembangan);

        MnLembarCatatanKeperawatan.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCatatanKeperawatan.setForeground(java.awt.Color.darkGray);
        MnLembarCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCatatanKeperawatan.setText("Lembar Catatan Keperawatan");
        MnLembarCatatanKeperawatan.setName("MnLembarCatatanKeperawatan"); // NOI18N
        MnLembarCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLembarCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCatatanKeperawatanActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarCatatanKeperawatan);

        MnLaporanAnestesia.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanAnestesia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanAnestesia.setForeground(java.awt.Color.darkGray);
        MnLaporanAnestesia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanAnestesia.setText("Lembar Laporan Anastesia");
        MnLaporanAnestesia.setName("MnLaporanAnestesia"); // NOI18N
        MnLaporanAnestesia.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLaporanAnestesia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanAnestesiaActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanAnestesia);

        jPopupMenu1.add(jMenu1);

        ppKelahiranBayi.setBackground(new java.awt.Color(255, 255, 255));
        ppKelahiranBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppKelahiranBayi.setForeground(java.awt.Color.darkGray);
        ppKelahiranBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppKelahiranBayi.setText("Data Kelahiran Bayi");
        ppKelahiranBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppKelahiranBayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppKelahiranBayi.setIconTextGap(5);
        ppKelahiranBayi.setName("ppKelahiranBayi"); // NOI18N
        ppKelahiranBayi.setPreferredSize(new java.awt.Dimension(180, 30));
        ppKelahiranBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppKelahiranBayiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppKelahiranBayi);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setForeground(java.awt.Color.darkGray);
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu2.setText("Grafik Analisa");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu2.setIconTextGap(5);
        jMenu2.setName("jMenu2"); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(180, 30));

        ppGrafikPerAgama.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikPerAgama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerAgama.setForeground(java.awt.Color.darkGray);
        ppGrafikPerAgama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerAgama.setText("Grafik Pasien Per Agama");
        ppGrafikPerAgama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerAgama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerAgama.setIconTextGap(5);
        ppGrafikPerAgama.setName("ppGrafikPerAgama"); // NOI18N
        ppGrafikPerAgama.setPreferredSize(new java.awt.Dimension(250, 28));
        ppGrafikPerAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerAgamaActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerAgama);

        ppGrafikPerPekerjaan.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikPerPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerPekerjaan.setForeground(java.awt.Color.darkGray);
        ppGrafikPerPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerPekerjaan.setText("Grafik Pasien Per Pekerjaan");
        ppGrafikPerPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerPekerjaan.setIconTextGap(5);
        ppGrafikPerPekerjaan.setName("ppGrafikPerPekerjaan"); // NOI18N
        ppGrafikPerPekerjaan.setPreferredSize(new java.awt.Dimension(250, 28));
        ppGrafikPerPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerPekerjaanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerPekerjaan);

        ppGrafikjkbayi.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikjkbayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikjkbayi.setForeground(java.awt.Color.darkGray);
        ppGrafikjkbayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikjkbayi.setText("Grafik Jenis Kelamin Pasien");
        ppGrafikjkbayi.setActionCommand("Grafik Pasien Per Jenis Kelamin");
        ppGrafikjkbayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikjkbayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikjkbayi.setIconTextGap(5);
        ppGrafikjkbayi.setName("ppGrafikjkbayi"); // NOI18N
        ppGrafikjkbayi.setPreferredSize(new java.awt.Dimension(180, 30));
        ppGrafikjkbayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikjkbayiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikjkbayi);

        ppGrafikDemografi.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikDemografi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikDemografi.setForeground(java.awt.Color.darkGray);
        ppGrafikDemografi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikDemografi.setText("Demografi Pasien");
        ppGrafikDemografi.setActionCommand("Grafik Demografi Pasien");
        ppGrafikDemografi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikDemografi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikDemografi.setIconTextGap(5);
        ppGrafikDemografi.setName("ppGrafikDemografi"); // NOI18N
        ppGrafikDemografi.setPreferredSize(new java.awt.Dimension(250, 28));
        ppGrafikDemografi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikDemografiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikDemografi);

        jPopupMenu1.add(jMenu2);

        ppRegistrasi.setBackground(new java.awt.Color(255, 255, 255));
        ppRegistrasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRegistrasi.setForeground(java.awt.Color.darkGray);
        ppRegistrasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRegistrasi.setText("Tampilkan Banyak Daftar");
        ppRegistrasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRegistrasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRegistrasi.setIconTextGap(5);
        ppRegistrasi.setName("ppRegistrasi"); // NOI18N
        ppRegistrasi.setPreferredSize(new java.awt.Dimension(150, 25));
        ppRegistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRegistrasiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRegistrasi);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        DlgDemografi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgDemografi.setName("DlgDemografi"); // NOI18N
        DlgDemografi.setUndecorated(true);
        DlgDemografi.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Demografi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        BtnPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint2.setMnemonic('T');
        BtnPrint2.setText("Grafik");
        BtnPrint2.setToolTipText("Alt+T");
        BtnPrint2.setName("BtnPrint2"); // NOI18N
        BtnPrint2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint2);
        BtnPrint2.setBounds(110, 110, 100, 30);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluar2);
        BtnKeluar2.setBounds(430, 110, 100, 30);

        Kelurahan2.setHighlighter(null);
        Kelurahan2.setName("Kelurahan2"); // NOI18N
        Kelurahan2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(Kelurahan2);
        Kelurahan2.setBounds(105, 70, 350, 23);

        BtnSeek8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek8.setMnemonic('1');
        BtnSeek8.setToolTipText("ALt+1");
        BtnSeek8.setName("BtnSeek8"); // NOI18N
        BtnSeek8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek8ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSeek8);
        BtnSeek8.setBounds(460, 70, 28, 23);

        Kecamatan2.setHighlighter(null);
        Kecamatan2.setName("Kecamatan2"); // NOI18N
        Kecamatan2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(Kecamatan2);
        Kecamatan2.setBounds(105, 40, 350, 23);

        BtnSeek9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek9.setMnemonic('1');
        BtnSeek9.setToolTipText("ALt+1");
        BtnSeek9.setName("BtnSeek9"); // NOI18N
        BtnSeek9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek9ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSeek9);
        BtnSeek9.setBounds(460, 40, 28, 23);

        Kabupaten2.setHighlighter(null);
        Kabupaten2.setName("Kabupaten2"); // NOI18N
        Kabupaten2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(Kabupaten2);
        Kabupaten2.setBounds(105, 10, 350, 23);

        BtnSeek10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek10.setMnemonic('1');
        BtnSeek10.setToolTipText("ALt+1");
        BtnSeek10.setName("BtnSeek10"); // NOI18N
        BtnSeek10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek10ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSeek10);
        BtnSeek10.setBounds(460, 10, 28, 23);

        jLabel33.setText("Kelurahan :");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa2.add(jLabel33);
        jLabel33.setBounds(0, 70, 100, 23);

        jLabel34.setText("Kabupaten :");
        jLabel34.setName("jLabel34"); // NOI18N
        panelBiasa2.add(jLabel34);
        jLabel34.setBounds(0, 10, 100, 23);

        jLabel35.setText("Kecamatan :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelBiasa2.add(jLabel35);
        jLabel35.setBounds(0, 40, 100, 23);

        BtnPrint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint3.setMnemonic('T');
        BtnPrint3.setText("Cetak");
        BtnPrint3.setToolTipText("Alt+T");
        BtnPrint3.setName("BtnPrint3"); // NOI18N
        BtnPrint3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint3ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint3);
        BtnPrint3.setBounds(10, 110, 100, 30);

        internalFrame3.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgDemografi.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPasien.setAutoCreateRowSorter(true);
        tbPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPasien.setComponentPopupMenu(jPopupMenu1);
        tbPasien.setName("tbPasien"); // NOI18N
        tbPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPasienMouseClicked(evt);
            }
        });
        tbPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPasienKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPasien);

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
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

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(58, 23));
        panelGlass9.add(jLabel11);

        Carialamat.setName("Carialamat"); // NOI18N
        Carialamat.setPreferredSize(new java.awt.Dimension(340, 23));
        Carialamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CarialamatKeyPressed(evt);
            }
        });
        panelGlass9.add(Carialamat);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel7.setText("Key Word :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel7);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel6.setText("Halaman :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setOpaque(false);
        cmbHlm.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(cmbHlm);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 219));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(560, 168));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rekam Medik :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(4, 12, 110, 23);

        jLabel4.setText("Nama Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(4, 42, 110, 23);

        TTmp.setName("TTmp"); // NOI18N
        TTmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTmpKeyPressed(evt);
            }
        });
        FormInput.add(TTmp);
        TTmp.setBounds(118, 102, 140, 23);

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.setOpaque(false);
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });
        FormInput.add(CmbJk);
        CmbJk.setBounds(118, 72, 110, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(118, 42, 250, 23);

        jLabel8.setText("J.K. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(4, 72, 110, 23);

        CMbGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "AB", "O" }));
        CMbGd.setName("CMbGd"); // NOI18N
        CMbGd.setOpaque(false);
        CMbGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbGdKeyPressed(evt);
            }
        });
        FormInput.add(CMbGd);
        CMbGd.setBounds(307, 72, 60, 23);

        jLabel9.setText("Gol. Darah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(231, 72, 72, 23);

        jLabel13.setText("Tmp/Tgl. Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(4, 102, 110, 23);

        DTPLahir.setEditable(false);
        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-30" }));
        DTPLahir.setDisplayFormat("yyyy-MM-dd");
        DTPLahir.setName("DTPLahir"); // NOI18N
        DTPLahir.setOpaque(false);
        DTPLahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPLahirItemStateChanged(evt);
            }
        });
        DTPLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahirKeyPressed(evt);
            }
        });
        FormInput.add(DTPLahir);
        DTPLahir.setBounds(263, 102, 104, 23);

        jLabel18.setText("Agama :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(402, 12, 90, 23);

        cmbAgama.setBackground(new java.awt.Color(245, 253, 240));
        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "KATOLIK", "HINDU", "BUDHA", "KONG HU CHU", "-" }));
        cmbAgama.setLightWeightPopupEnabled(false);
        cmbAgama.setName("cmbAgama"); // NOI18N
        cmbAgama.setOpaque(false);
        cmbAgama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAgamaKeyPressed(evt);
            }
        });
        FormInput.add(cmbAgama);
        cmbAgama.setBounds(496, 12, 130, 23);

        jLabel19.setText("Stts. Nikah :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(629, 12, 120, 23);

        CmbStts.setBackground(new java.awt.Color(245, 253, 240));
        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENIKAH", "SINGLE", "JANDA", "DUDHA" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.setOpaque(false);
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        FormInput.add(CmbStts);
        CmbStts.setBounds(753, 12, 120, 23);

        jLabel20.setText("Alamat :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(4, 162, 110, 23);

        jLabel21.setText("No.Telp :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(402, 72, 90, 23);

        jLabel12.setText("Pekerjaan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(402, 102, 90, 23);

        TPkj.setName("TPkj"); // NOI18N
        TPkj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPkjKeyPressed(evt);
            }
        });
        FormInput.add(TPkj);
        TPkj.setBounds(496, 102, 150, 23);

        TAlmt.setText("ALAMAT");
        TAlmt.setHighlighter(null);
        TAlmt.setName("TAlmt"); // NOI18N
        TAlmt.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TAlmtMouseMoved(evt);
            }
        });
        TAlmt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TAlmtMouseExited(evt);
            }
        });
        TAlmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlmtKeyPressed(evt);
            }
        });
        FormInput.add(TAlmt);
        TAlmt.setBounds(118, 162, 249, 23);

        TTlp.setHighlighter(null);
        TTlp.setName("TTlp"); // NOI18N
        TTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTlpKeyPressed(evt);
            }
        });
        FormInput.add(TTlp);
        TTlp.setBounds(496, 72, 150, 23);

        TNo.setHighlighter(null);
        TNo.setName("TNo"); // NOI18N
        TNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoKeyPressed(evt);
            }
        });
        FormInput.add(TNo);
        TNo.setBounds(118, 12, 140, 23);

        jLabel15.setText("No.KTP/SIM :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(649, 102, 100, 23);

        TKtp.setName("TKtp"); // NOI18N
        TKtp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtpKeyPressed(evt);
            }
        });
        FormInput.add(TKtp);
        TKtp.setBounds(753, 102, 120, 23);

        DTPDaftar.setEditable(false);
        DTPDaftar.setForeground(new java.awt.Color(50, 70, 50));
        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-30" }));
        DTPDaftar.setDisplayFormat("yyyy-MM-dd");
        DTPDaftar.setName("DTPDaftar"); // NOI18N
        DTPDaftar.setOpaque(false);
        DTPDaftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPDaftarKeyPressed(evt);
            }
        });
        FormInput.add(DTPDaftar);
        DTPDaftar.setBounds(753, 72, 120, 23);

        jLabel22.setText("Pertama Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(649, 72, 100, 23);

        jLabel17.setText("Umur :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(4, 132, 110, 23);

        TUmur.setName("TUmur"); // NOI18N
        TUmur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurKeyPressed(evt);
            }
        });
        FormInput.add(TUmur);
        TUmur.setBounds(118, 132, 110, 23);

        jLabel23.setText("Pendidikan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(231, 132, 72, 23);

        CMbPnd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        CMbPnd.setName("CMbPnd"); // NOI18N
        CMbPnd.setOpaque(false);
        CMbPnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbPndKeyPressed(evt);
            }
        });
        FormInput.add(CMbPnd);
        CMbPnd.setBounds(307, 132, 60, 23);

        TSaudara.setName("TSaudara"); // NOI18N
        TSaudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSaudaraKeyPressed(evt);
            }
        });
        FormInput.add(TSaudara);
        TSaudara.setBounds(652, 132, 221, 23);

        R5.setBorder(null);
        buttonGroup1.add(R5);
        R5.setSelected(true);
        R5.setText("Saudara :");
        R5.setIconTextGap(0);
        R5.setName("R5"); // NOI18N
        FormInput.add(R5);
        R5.setBounds(580, 132, 70, 23);

        R4.setBorder(null);
        buttonGroup1.add(R4);
        R4.setText("Suami/");
        R4.setIconTextGap(0);
        R4.setName("R4"); // NOI18N
        FormInput.add(R4);
        R4.setBounds(528, 132, 70, 23);

        R3.setBorder(null);
        buttonGroup1.add(R3);
        R3.setText("Istri/");
        R3.setIconTextGap(0);
        R3.setName("R3"); // NOI18N
        FormInput.add(R3);
        R3.setBounds(487, 132, 70, 23);

        R2.setBorder(null);
        buttonGroup1.add(R2);
        R2.setText("Ibu/");
        R2.setIconTextGap(0);
        R2.setName("R2"); // NOI18N
        FormInput.add(R2);
        R2.setBounds(447, 132, 70, 23);

        R1.setBorder(null);
        buttonGroup1.add(R1);
        R1.setText("Ayah/");
        R1.setIconTextGap(0);
        R1.setName("R1"); // NOI18N
        FormInput.add(R1);
        R1.setBounds(400, 132, 70, 23);

        jLabel24.setText("Askes/Asuransi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(372, 42, 120, 23);

        Kdpnj.setText("-");
        Kdpnj.setHighlighter(null);
        Kdpnj.setName("Kdpnj"); // NOI18N
        Kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdpnjKeyPressed(evt);
            }
        });
        FormInput.add(Kdpnj);
        Kdpnj.setBounds(496, 42, 80, 23);

        nmpnj.setEditable(false);
        nmpnj.setText("-");
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(578, 42, 265, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('1');
        BtnSeek1.setToolTipText("ALt+1");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(845, 42, 28, 23);

        Kelurahan.setText("KELURAHAN");
        Kelurahan.setHighlighter(null);
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanMouseMoved(evt);
            }
        });
        Kelurahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanMouseExited(evt);
            }
        });
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        FormInput.add(Kelurahan);
        Kelurahan.setBounds(369, 162, 136, 23);

        Kecamatan.setText("KECAMATAN");
        Kecamatan.setHighlighter(null);
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanMouseMoved(evt);
            }
        });
        Kecamatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanMouseExited(evt);
            }
        });
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        FormInput.add(Kecamatan);
        Kecamatan.setBounds(537, 162, 136, 23);

        Kabupaten.setText("KABUPATEN");
        Kabupaten.setHighlighter(null);
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenMouseMoved(evt);
            }
        });
        Kabupaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenMouseExited(evt);
            }
        });
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        FormInput.add(Kabupaten);
        Kabupaten.setBounds(707, 162, 136, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('1');
        BtnSeek2.setToolTipText("ALt+1");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(506, 162, 28, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('1');
        BtnSeek3.setToolTipText("ALt+1");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(675, 162, 28, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('1');
        BtnSeek4.setToolTipText("ALt+1");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(845, 162, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void tbPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPasienMouseClicked
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
}//GEN-LAST:event_tbPasienMouseClicked

private void tbPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPasienKeyPressed
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
}//GEN-LAST:event_tbPasienKeyPressed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
   if(TNo.getText().trim().equals("")){
            Valid.textKosong(TNo,"No.Rekam Medis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama pasien");
        }else if(nmpnj.getText().trim().equals("")||Kdpnj.getText().trim().equals("")){
            Valid.textKosong(Kdpnj,"Asuransi/Askes/Png.Jawab");
        }else if(TAlmt.getText().trim().equals("")){
            Valid.textKosong(TAlmt,"Alamat");
        }else if(Kelurahan.getText().trim().equals("")){
            Valid.textKosong(Kelurahan,"Kelurahan");
        }else if(Kecamatan.getText().trim().equals("")){
            Valid.textKosong(Kecamatan,"Kecamatan");
        }else if(Kabupaten.getText().trim().equals("")){
            Valid.textKosong(Kabupaten,"Kabupaten");
        }else{
            if(R1.isSelected()==true){
                klg="AYAH";
            }else if(R2.isSelected()==true){
                klg="IBU";
            }else if(R3.isSelected()==true){
                klg="ISTRI";
            }else if(R4.isSelected()==true){
                klg="SUAMI";
            }else if(R5.isSelected()==true){
                klg="SAUDARA";
            }  
            Sequel.queryu2("insert into kelurahan values('0','"+Kelurahan.getText().replaceAll("KELURAHAN","") +"')");
            Sequel.queryu2("insert into kecamatan values('0','"+Kecamatan.getText().replaceAll("KECAMATAN","")+"')");
            Sequel.queryu2("insert into kabupaten values('0','"+Kabupaten.getText().replaceAll("KABUPATEN","")+"')");
            Sequel.menyimpan("pasien","'"+TNo.getText()+"','"+
                    TNm.getText()+"','"+
                    TKtp.getText()+"','"+
                    CmbJk.getSelectedItem().toString().substring(0,1)+"','"+
                    TTmp.getText()+"','"+
                    DTPLahir.getSelectedItem()+"','"+
                    TAlmt.getText().replaceAll("ALAMAT","")+"','"+
                    CMbGd.getSelectedItem()+"','"+
                    TPkj.getText()+"','"+
                    CmbStts.getSelectedItem()+"','"+
                    cmbAgama.getSelectedItem()+"','"+
                    DTPDaftar.getSelectedItem()+"','"+
                    TTlp.getText()+"','"+
                    TUmur.getText()+"','"+
                    CMbPnd.getSelectedItem()+"','"+
                    klg+"','"+
                    TSaudara.getText()+"','"+
                    Kdpnj.getText()+"','"+
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","")) +"','"+
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","")) +"','"+
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","")) +"'","No.Rekam Medis Pasien");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TSaudara,BtnBatal);
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
   Valid.hapusTable(tabMode,TNo,"pasien","no_rkm_medis");
   tampil();
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
        if(TNo.getText().trim().equals("")){
            Valid.textKosong(TNo,"No.Rekam Medis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama pasien");
        }else if(nmpnj.getText().trim().equals("")||Kdpnj.getText().trim().equals("")){
            Valid.textKosong(Kdpnj,"Asuransi/Askes/Png.Jawab");
        }else if(TAlmt.getText().trim().equals("")){
            Valid.textKosong(TAlmt,"Alamat");
        }else if(Kelurahan.getText().trim().equals("")){
            Valid.textKosong(Kelurahan,"Kelurahan");
        }else if(Kecamatan.getText().trim().equals("")){
            Valid.textKosong(Kecamatan,"Kecamatan");
        }else if(Kabupaten.getText().trim().equals("")){
            Valid.textKosong(Kabupaten,"Kabupaten");
        }else{
            if(R1.isSelected()==true){
                klg="AYAH";
            }else if(R2.isSelected()==true){
                klg="IBU";
            }else if(R3.isSelected()==true){
                klg="ISTRI";
            }else if(R4.isSelected()==true){
                klg="SUAMI";
            }else if(R5.isSelected()==true){
                klg="SAUDARA";
            }
            Sequel.queryu2("insert into kelurahan values('0','"+Kelurahan.getText()+"')");
            Sequel.queryu2("insert into kecamatan values('0','"+Kecamatan.getText()+"')");
            Sequel.queryu2("insert into kabupaten values('0','"+Kabupaten.getText()+"')");
            Valid.editTable(tabMode,"pasien","no_rkm_medis",Kd2,"no_rkm_medis='"+TNo.getText()+"',nm_pasien='"+TNm.getText()+
                    "',no_ktp='"+TKtp.getText()+
                    "',jk='"+CmbJk.getSelectedItem().toString().substring(0,1)+
                    "',tmp_lahir='"+TTmp.getText()+
                    "',tgl_lahir='"+DTPLahir.getSelectedItem()+
                    "',alamat='"+TAlmt.getText()+
                    "',gol_darah='"+CMbGd.getSelectedItem()+
                    "',pekerjaan='"+TPkj.getText()+
                    "',stts_nikah='"+CmbStts.getSelectedItem()+
                    "',agama='"+cmbAgama.getSelectedItem()+
                    "',tgl_daftar='"+DTPDaftar.getSelectedItem()+
                    "',no_tlp='"+TTlp.getText()+"',umur='"+TUmur.getText()+
                    "',pnd='"+CMbPnd.getSelectedItem()+
                    "',keluarga='"+klg+"',namakeluarga='"+TSaudara.getText()+
                    "',kd_pj='"+Kdpnj.getText()+"',kd_kel='"+
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText()) +"',kd_kec='"+
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText()) +"',kd_kab='"+
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText()) +"'");
            if(tabMode.getRowCount()!=0){tampil();}
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Valid.MyReport("rptPasien.jrxml","report","::[ Data Pasien Umum ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_ktp like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.jk like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tmp_lahir like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.gol_darah like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.pekerjaan like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.agama like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_daftar like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_tlp like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc"+" LIMIT "+awal+",500");

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

private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        Carialamat.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
    DlgDemografi.dispose();
    dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

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

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
   isForm();  
}//GEN-LAST:event_ChkInputActionPerformed

private void TTmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTmpKeyPressed
   Valid.pindah(evt,CMbGd,DTPLahir);
}//GEN-LAST:event_TTmpKeyPressed

private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
   Valid.pindah(evt,TNm,CMbGd);
}//GEN-LAST:event_CmbJkKeyPressed

private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCari.setText(TNm.getText());
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            CmbJk.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            TNo.requestFocus();  
        }
}//GEN-LAST:event_TNmKeyPressed

private void CMbGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbGdKeyPressed
   Valid.pindah(evt,CmbJk,TTmp);
}//GEN-LAST:event_CMbGdKeyPressed

private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed
   Valid.pindah(evt,TTmp,TUmur);
}//GEN-LAST:event_DTPLahirKeyPressed

private void cmbAgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAgamaKeyPressed
   Valid.pindah(evt,Kabupaten,CmbStts);
}//GEN-LAST:event_cmbAgamaKeyPressed

private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbSttsKeyPressed
   Valid.pindah(evt,cmbAgama,Kdpnj);
}//GEN-LAST:event_CmbSttsKeyPressed

private void TPkjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPkjKeyPressed
   Valid.pindah(evt,TTlp,TKtp);
}//GEN-LAST:event_TPkjKeyPressed

private void TAlmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlmtKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Kelurahan.getText().equals("KELURAHAN")){
           Kelurahan.setText("");
       }
       Kelurahan.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CMbPnd.requestFocus();
   }
}//GEN-LAST:event_TAlmtKeyPressed

private void TTlpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTlpKeyPressed
   Valid.pindah(evt,Kdpnj,TPkj);
}//GEN-LAST:event_TTlpKeyPressed

private void TNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCari.setText(TNo.getText());
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TNm.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){   
            TCari.requestFocus();  
        }
}//GEN-LAST:event_TNoKeyPressed

private void TKtpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtpKeyPressed
   Valid.pindah(evt,TPkj,TSaudara);
}//GEN-LAST:event_TKtpKeyPressed

private void DTPDaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPDaftarKeyPressed
   Valid.pindah(evt,TPkj,BtnSimpan);
}//GEN-LAST:event_DTPDaftarKeyPressed

private void TUmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurKeyPressed
   Valid.pindah(evt,DTPLahir,CMbPnd);
}//GEN-LAST:event_TUmurKeyPressed

private void CMbPndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbPndKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(TAlmt.getText().equals("ALAMAT")){
           TAlmt.setText("");
       }
       TAlmt.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TUmur.requestFocus();
   }
}//GEN-LAST:event_CMbPndKeyPressed

private void TSaudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSaudaraKeyPressed
   Valid.pindah(evt,TKtp,BtnSimpan);
}//GEN-LAST:event_TSaudaraKeyPressed

private void MnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Valid.MyReport("rptKartuPasien.jrxml","report","::[ Kartu Periksa Pasien(Umum) ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab  from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_ktp like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.jk like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tmp_lahir like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.gol_darah like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.pekerjaan like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.agama like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_daftar like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_tlp like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc");            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnBarcodeActionPerformed

private void MnKartuStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartuStatusActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuPasien.jrxml","report","::[ Kartu Periksa Pasien(Umum) ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnKartuStatusActionPerformed

private void DTPLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPLahirItemStateChanged
   Sequel.cariIsi("select DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT('"+DTPLahir.getSelectedItem()+"', '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT('"+DTPLahir.getSelectedItem()+"', '00-%m-%d')) AS age", TUmur);
}//GEN-LAST:event_DTPLahirItemStateChanged

private void KdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdpnjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
            CmbStts.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
            TTlp.requestFocus();            
        }
}//GEN-LAST:event_KdpnjKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        var.setStatus(true);
        penjab.tampil("");
        penjab.isCek();
        penjab.emptTeks();
        penjab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void MnResumePasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResumePasienActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            DlgResumePerawatan resume=new DlgResumePerawatan(null,false);
            resume.setNoRm(TNo.getText()); 
            resume.isPasien();
            resume.tampil();
            resume.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
        }
}//GEN-LAST:event_MnResumePasienActionPerformed

private void MnKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartuActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuBerobat.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnKartuActionPerformed

private void ppGrafikjkbayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikjkbayiActionPerformed
       grafikjkel kas=new grafikjkel("Grafik Jenis Kelamin Pasien "," ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikjkbayiActionPerformed

private void MnLaporanRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRMActionPerformed
      if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuRM.jrxml","report","::[ Laporan Rekam Medik ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLaporanRMActionPerformed

private void MnLaporanIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanIGDActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuIgd.jrxml","report","::[ Laporan Rekam Medik ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLaporanIGDActionPerformed

private void ppKelahiranBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppKelahiranBayiActionPerformed
    if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            DlgIKBBayi resume=new DlgIKBBayi(null,false);
            resume.setNoRM(TNo.getText(),TNm.getText(),TSaudara.getText(),TAlmt.getText(),CmbJk.getSelectedItem().toString(),
                    TUmur.getText(),DTPLahir.getDate(),DTPDaftar.getDate());
            resume.tampil();
            resume.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
        }
}//GEN-LAST:event_ppKelahiranBayiActionPerformed

private void MnLembarKeluarMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarKeluarMasukActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarKeluarMasuk.jrxml","report","::[ Lembar Masuk Keluar ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarKeluarMasukActionPerformed

private void MnLembarAnamNesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarAnamNesaActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarAnamnesi.jrxml","report","::[ Lembar Anamnesa ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarAnamNesaActionPerformed

private void MnLembarGrafikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarGrafikActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarGrafik.jrxml","report","::[ Lembar Grafik ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarGrafikActionPerformed

private void MnLembarCatatanPerkembanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCatatanPerkembanganActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarPerkembangan.jrxml","report","::[ Lembar Catatan Perkembangan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarCatatanPerkembanganActionPerformed

private void MnLembarCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCatatanKeperawatanActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarPerkembangan.jrxml","report","::[ Lembar Catatan Keperawatan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarCatatanKeperawatanActionPerformed

private void MnLaporanAnestesiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanAnestesiaActionPerformed
     if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLaporanAnestesia.jrxml","report","::[ Lembar Catatan Keperawatan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLaporanAnestesiaActionPerformed

private void CarialamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CarialamatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            BtnCariActionPerformed(null);
            BtnCari.requestFocus();
        }
}//GEN-LAST:event_CarialamatKeyPressed

private void MnKartu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuBerobat2.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnKartu1ActionPerformed

private void MnKartu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuBerobat3.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnKartu2ActionPerformed

private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Kecamatan.getText().equals("KECAMATAN")){
           Kecamatan.setText("");
       }
       Kecamatan.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(TAlmt.getText().equals("ALAMAT")){
          TAlmt.setText("");
       }     
       TAlmt.requestFocus();
   }
}//GEN-LAST:event_KelurahanKeyPressed

private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Kabupaten.getText().equals("KABUPATEN")){
           Kabupaten.setText("");
       }
       Kabupaten.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(Kelurahan.getText().equals("KELURAHAN")){
          Kelurahan.setText("");
       }     
       Kelurahan.requestFocus();
   }
}//GEN-LAST:event_KecamatanKeyPressed

private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       cmbAgama.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(Kecamatan.getText().equals("KECAMATAN")){
          Kecamatan.setText("");
       }     
       Kecamatan.requestFocus();
   }
}//GEN-LAST:event_KabupatenKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
       pilih=1;
       kel.tampil();
        kel.isCek();
        kel.emptTeks();
        kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        pilih=1;
        kec.tampil();
        kec.isCek();
        kec.emptTeks();
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        pilih=1;
        kab.tampil();
        kab.isCek();
        kab.emptTeks();
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void ppGrafikDemografiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikDemografiActionPerformed
        DlgDemografi.setSize(550,180);
        DlgDemografi.setLocationRelativeTo(internalFrame1);
        DlgDemografi.setVisible(true);
}//GEN-LAST:event_ppGrafikDemografiActionPerformed

private void ppGrafikPerAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerAgamaActionPerformed
       grafikpasienperagama kas=new grafikpasienperagama("Grafik Pasien Per Agama "," ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerAgamaActionPerformed

private void ppGrafikPerPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerPekerjaanActionPerformed
       grafikpasienperpekerjaaan kas=new grafikpasienperpekerjaaan("Grafik Pasien Per Pekerjaan "," ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerPekerjaanActionPerformed

private void BtnPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint2ActionPerformed
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                        " pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        " where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and kelurahan.nm_kel='"+Kelurahan2.getText()+"'", 
                        "pasien.alamat","Area");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                        " pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                         "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"'", 
                         "kelurahan.nm_kel","Kelurahan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                         " pasien inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"'", 
                         "kecamatan.nm_kec","Kecamatan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kabupaten ]::",
                         " pasien inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab", 
                          "kabupaten.nm_kab","Kabupaten");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            } 
}//GEN-LAST:event_BtnPrint2ActionPerformed

private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
   DlgDemografi.dispose();
}//GEN-LAST:event_BtnKeluar2ActionPerformed

private void BtnSeek8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek8ActionPerformed
   if(Kecamatan2.getText().equals("")){
       Valid.textKosong(Kecamatan2,"Kecamatan");
   }else{
       pilih=2;
       kel.tampil();
        kel.isCek();
        kel.emptTeks();
        kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
   }       
}//GEN-LAST:event_BtnSeek8ActionPerformed

private void BtnSeek9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek9ActionPerformed
   if(Kabupaten2.getText().equals("")){
       Valid.textKosong(Kabupaten2,"Kabupaten");
   }else{
       pilih=2;
       kec.tampil();
        kec.isCek();
        kec.emptTeks();
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
   }       
}//GEN-LAST:event_BtnSeek9ActionPerformed

private void BtnSeek10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek10ActionPerformed
        pilih=2;
       kab.tampil();
        kab.isCek();
        kab.emptTeks();
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);   
}//GEN-LAST:event_BtnSeek10ActionPerformed

private void BtnPrint3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> param = new HashMap<>();
                param.put("judul","Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                param.put("area","Area");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select  pasien.alamat as area,count(pasien.alamat) as jumlah from pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' "+
                   "and kelurahan.nm_kel='"+Kelurahan2.getText()+"' group by pasien.alamat order by pasien.alamat",param);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> param = new HashMap<>();
                param.put("judul","Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                param.put("area","Kelurahan");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kelurahan.nm_kel as area,count(kelurahan.nm_kel) as jumlah from pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' group by kelurahan.nm_kel order by kelurahan.nm_kel",param);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> param = new HashMap<>();
                param.put("judul","Data Demografi Per Kecamatan Kabupaten "+Kabupaten2.getText());
                param.put("area","Kecamatan");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kecamatan.nm_kec as area,count(kecamatan.nm_kec) as jumlah from pasien "+
                   "inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"' group by kecamatan.nm_kec order by kecamatan.nm_kec",param);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> param = new HashMap<>();
                param.put("judul","Data Demografi Per Kabupaten");
                param.put("area","Kabupaten");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kabupaten ]::","select kabupaten.nm_kab as area,count(kabupaten.nm_kab) as jumlah from pasien "+
                   "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab group by kabupaten.nm_kab order by kabupaten.nm_kab",param);
            }                        
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrint3ActionPerformed

private void ppRegistrasiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRegistrasiBtnPrintActionPerformed
     prosesCari2();
}//GEN-LAST:event_ppRegistrasiBtnPrintActionPerformed

private void TAlmtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TAlmtMouseExited
        if(TAlmt.getText().equals("")){
            TAlmt.setText("ALAMAT");
        }
}//GEN-LAST:event_TAlmtMouseExited

private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if(Kelurahan.getText().equals("")){
            Kelurahan.setText("KELURAHAN");
        }
}//GEN-LAST:event_KelurahanMouseExited

private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if(Kecamatan.getText().equals("")){
            Kecamatan.setText("KECAMATAN");
        }
}//GEN-LAST:event_KecamatanMouseExited

private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
       if(Kabupaten.getText().equals("")){
            Kabupaten.setText("KABUPATEN");
        }
}//GEN-LAST:event_KabupatenMouseExited

private void TAlmtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TAlmtMouseMoved
        if(TAlmt.getText().equals("ALAMAT")){
            TAlmt.setText("");
        }
}//GEN-LAST:event_TAlmtMouseMoved

private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if(Kelurahan.getText().equals("KELURAHAN")){
            Kelurahan.setText("");
        }
}//GEN-LAST:event_KelurahanMouseMoved

private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if(Kecamatan.getText().equals("KECAMATAN")){
            Kecamatan.setText("");
        }
}//GEN-LAST:event_KecamatanMouseMoved

private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if(Kabupaten.getText().equals("KABUPATEN")){
            Kabupaten.setText("");
        }
}//GEN-LAST:event_KabupatenMouseMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            DlgPasien dialog = new DlgPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar2;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint2;
    private widget.Button BtnPrint3;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek10;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek8;
    private widget.Button BtnSeek9;
    private widget.Button BtnSimpan;
    private widget.ComboBox CMbGd;
    private widget.ComboBox CMbPnd;
    private widget.TextBox Carialamat;
    private widget.CekBox ChkInput;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPDaftar;
    private widget.Tanggal DTPLahir;
    private javax.swing.JDialog DlgDemografi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kabupaten;
    private widget.TextBox Kabupaten2;
    private widget.TextBox Kd2;
    private widget.TextBox Kdpnj;
    private widget.TextBox Kecamatan;
    private widget.TextBox Kecamatan2;
    private widget.TextBox Kelurahan;
    private widget.TextBox Kelurahan2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnKartu;
    private javax.swing.JMenuItem MnKartu1;
    private javax.swing.JMenuItem MnKartu2;
    private javax.swing.JMenuItem MnKartuStatus;
    private javax.swing.JMenuItem MnLaporanAnestesia;
    private javax.swing.JMenuItem MnLaporanIGD;
    private javax.swing.JMenuItem MnLaporanRM;
    private javax.swing.JMenuItem MnLembarAnamNesa;
    private javax.swing.JMenuItem MnLembarCatatanKeperawatan;
    private javax.swing.JMenuItem MnLembarCatatanPerkembangan;
    private javax.swing.JMenuItem MnLembarGrafik;
    private javax.swing.JMenuItem MnLembarKeluarMasuk;
    private javax.swing.JMenuItem MnResumePasien;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton R4;
    private widget.RadioButton R5;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlmt;
    private widget.TextBox TCari;
    private widget.TextBox TKtp;
    private widget.TextBox TNm;
    private widget.TextBox TNo;
    private widget.TextBox TPkj;
    private widget.TextBox TSaudara;
    private widget.TextBox TTlp;
    private widget.TextBox TTmp;
    private widget.TextBox TUmur;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbAgama;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel3;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator5;
    private widget.TextBox nmpnj;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppGrafikDemografi;
    private javax.swing.JMenuItem ppGrafikPerAgama;
    private javax.swing.JMenuItem ppGrafikPerPekerjaan;
    private javax.swing.JMenuItem ppGrafikjkbayi;
    private javax.swing.JMenuItem ppKelahiranBayi;
    private javax.swing.JMenuItem ppRegistrasi;
    private widget.Table tbPasien;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{
            awal="0";
            if(cmbHlm.getItemCount()>0){
                awal=Integer.toString(hlm[Integer.parseInt(cmbHlm.getSelectedItem().toString())]);
            }
            
            ps.setString(1,"%"+Carialamat.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+Carialamat.getText().trim()+"%");
            ps.setString(4, "%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+Carialamat.getText().trim()+"%");
            ps.setString(6, "%"+TCari.getText().trim()+"%");
            ps.setString(7,"%"+Carialamat.getText().trim()+"%");
            ps.setString(8, "%"+TCari.getText().trim()+"%");
            ps.setString(9,"%"+Carialamat.getText().trim()+"%");
            ps.setString(10, "%"+TCari.getText().trim()+"%");
            ps.setString(11,"%"+Carialamat.getText().trim()+"%");
            ps.setString(12, "%"+TCari.getText().trim()+"%");
            ps.setString(13,"%"+Carialamat.getText().trim()+"%");
            ps.setString(14, "%"+TCari.getText().trim()+"%");
            ps.setString(15,"%"+Carialamat.getText().trim()+"%");
            ps.setString(16, "%"+TCari.getText().trim()+"%");
            ps.setString(17,"%"+Carialamat.getText().trim()+"%");
            ps.setString(18, "%"+TCari.getText().trim()+"%");
            ps.setString(19,"%"+Carialamat.getText().trim()+"%");
            ps.setString(20, "%"+TCari.getText().trim()+"%");
            ps.setString(21,"%"+Carialamat.getText().trim()+"%");
            ps.setString(22, "%"+TCari.getText().trim()+"%");
            ps.setString(23,"%"+Carialamat.getText().trim()+"%");
            ps.setString(24, "%"+TCari.getText().trim()+"%");
            ps.setString(25,"%"+Carialamat.getText().trim()+"%");
            ps.setString(26, "%"+TCari.getText().trim()+"%");
            ps.setString(27,"%"+Carialamat.getText().trim()+"%");
            ps.setString(28, "%"+TCari.getText().trim()+"%");
            ps.setInt(29,Integer.parseInt(awal));
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new String[] {rs.getString(1),
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
                               rs.getString(14),
                               rs.getString(15),
                               rs.getString(16),
                               rs.getString(17),
                               rs.getString(18),"Klik Kanan, Tampilkan Banyak Daftar"});
            }          

            cmbHlm.removeAllItems();            
            pshal.setString(1,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(2,"%"+TCari.getText().trim()+"%");
            pshal.setString(3,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(4, "%"+TCari.getText().trim()+"%");
            pshal.setString(5,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(6, "%"+TCari.getText().trim()+"%");
            pshal.setString(7,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(8, "%"+TCari.getText().trim()+"%");
            pshal.setString(9,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(10, "%"+TCari.getText().trim()+"%");
            pshal.setString(11,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(12, "%"+TCari.getText().trim()+"%");
            pshal.setString(13,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(14, "%"+TCari.getText().trim()+"%");
            pshal.setString(15,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(16, "%"+TCari.getText().trim()+"%");
            pshal.setString(17,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(18, "%"+TCari.getText().trim()+"%");
            pshal.setString(19,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(20, "%"+TCari.getText().trim()+"%");
            pshal.setString(21,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(22, "%"+TCari.getText().trim()+"%");
            pshal.setString(23,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(24, "%"+TCari.getText().trim()+"%");
            pshal.setString(25,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(26, "%"+TCari.getText().trim()+"%");
            pshal.setString(27,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(28, "%"+TCari.getText().trim()+"%");
            rs=pshal.executeQuery();
            jumlah=0;
            if(rs.next()){
                jumlah=rs.getDouble("jumlah");
            }            
            x=jumlah/499;
            i=Math.ceil(x);
            z=(int) i;
            
            hlm=null;            
            hlm=new int[z+1];
            for(j=1;j<=i;j++){
                 mulai=((j-1)*499+j)-1;
                 hlm[j]=mulai;
                 cmbHlm.addItem(j);
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TNo.setText("");
        Kd2.setText("");
        TNm.setText("");
        CmbJk.setSelectedIndex(0);
        CMbGd.setSelectedIndex(0);
        TTmp.setText("");
        cmbAgama.setSelectedIndex(0);
        CmbStts.setSelectedIndex(0);
        TAlmt.setText("ALAMAT");
        TKtp.setText("");
        TPkj.setText("");
        TTlp.setText("");
        TUmur.setText("");
        TSaudara.setText("");        
        Kdpnj.setText("-");         
        Kelurahan.setText("KELURAHAN");      
        Kecamatan.setText("KECAMATAN");      
        Kabupaten.setText("KABUPATEN"); 
        nmpnj.setText("-");
        R5.setSelected(true);
        DTPLahir.setDate(new Date());
        DTPDaftar.setDate(new Date());
        //Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rkm_medis,6),signed)),0) from pasien","",6,TNo);   
        TNo.requestFocus();
    }

    private void getData() {
        if(tbPasien.getSelectedRow()!= -1){
            TNo.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),0).toString());
            Kd2.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),0).toString());
            TNm.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),1).toString());
            TKtp.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),2).toString());

            switch (tbPasien.getValueAt(tbPasien.getSelectedRow(),3).toString()) {
                case "L":
                    CmbJk.setSelectedItem("LAKI-LAKI");
                    break;
                case "P":
                    CmbJk.setSelectedItem("PEREMPUAN");
                    break;
            }

            TTmp.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),4).toString());
            try {                
                pscariwilayah.setString(1,tbPasien.getValueAt(tbPasien.getSelectedRow(),0).toString());
                rs=pscariwilayah.executeQuery();
                if(rs.next()){
                    TAlmt.setText(rs.getString("alamat"));
                    Kabupaten.setText(rs.getString("nm_kab"));
                    Kecamatan.setText(rs.getString("nm_kec"));
                    Kelurahan.setText(rs.getString("nm_kel"));
                }                
            } catch (SQLException ex) {
               System.out.println(ex);
            }            
            
            
            CMbGd.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),7).toString());
            TPkj.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),8).toString());
            CmbStts.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),9).toString());
            cmbAgama.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),10).toString());
            TTlp.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),12).toString());
            CMbPnd.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),14).toString());
            switch (tbPasien.getValueAt(tbPasien.getSelectedRow(),15).toString()) {
                case "AYAH":
                    R1.setSelected(true);
                    break;
                case "IBU":
                    R2.setSelected(true);
                    break;
                case "ISTRI":
                    R3.setSelected(true);
                    break;
                case "SUAMI":  
                    R4.setSelected(true);
                    break;
                case "SAUDARA":
                    R5.setSelected(true);
                    break;
            }
            TSaudara.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),16).toString());
            Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis='"+TNo.getText()+"'",Kdpnj);
            nmpnj.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),17).toString());
            
            Valid.SetTgl(DTPLahir,tbPasien.getValueAt(tbPasien.getSelectedRow(),5).toString());
            Valid.SetTgl(DTPDaftar,tbPasien.getValueAt(tbPasien.getSelectedRow(),11).toString());            
            TUmur.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),13).toString());
        }
    }
    
    public JTextField getTextField(){
        return TNo;
    }
    
    public JTable getTable(){
        return tbPasien;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,219));
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
        BtnSimpan.setEnabled(var.getpasien());
        BtnHapus.setEnabled(var.getpasien());
        BtnEdit.setEnabled(var.getpasien());
        BtnPrint.setEnabled(var.getpasien());
    }

    private void prosesCari2() {
        Valid.tabelKosong(tabMode);
        try{
            awal="0";
            if(cmbHlm.getItemCount()>0){
                awal=Integer.toString(hlm[Integer.parseInt(cmbHlm.getSelectedItem().toString())]);
            }
            ps.setString(1,"%"+Carialamat.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+Carialamat.getText().trim()+"%");
            ps.setString(4, "%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+Carialamat.getText().trim()+"%");
            ps.setString(6, "%"+TCari.getText().trim()+"%");
            ps.setString(7,"%"+Carialamat.getText().trim()+"%");
            ps.setString(8, "%"+TCari.getText().trim()+"%");
            ps.setString(9,"%"+Carialamat.getText().trim()+"%");
            ps.setString(10, "%"+TCari.getText().trim()+"%");
            ps.setString(11,"%"+Carialamat.getText().trim()+"%");
            ps.setString(12, "%"+TCari.getText().trim()+"%");
            ps.setString(13,"%"+Carialamat.getText().trim()+"%");
            ps.setString(14, "%"+TCari.getText().trim()+"%");
            ps.setString(15,"%"+Carialamat.getText().trim()+"%");
            ps.setString(16, "%"+TCari.getText().trim()+"%");
            ps.setString(17,"%"+Carialamat.getText().trim()+"%");
            ps.setString(18, "%"+TCari.getText().trim()+"%");
            ps.setString(19,"%"+Carialamat.getText().trim()+"%");
            ps.setString(20, "%"+TCari.getText().trim()+"%");
            ps.setString(21,"%"+Carialamat.getText().trim()+"%");
            ps.setString(22, "%"+TCari.getText().trim()+"%");
            ps.setString(23,"%"+Carialamat.getText().trim()+"%");
            ps.setString(24, "%"+TCari.getText().trim()+"%");
            ps.setString(25,"%"+Carialamat.getText().trim()+"%");
            ps.setString(26, "%"+TCari.getText().trim()+"%");
            ps.setString(27,"%"+Carialamat.getText().trim()+"%");
            ps.setString(28, "%"+TCari.getText().trim()+"%");
            ps.setInt(29,Integer.parseInt(awal));
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new String[] {rs.getString(1),
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
                               rs.getString(14),
                               rs.getString(15),
                               rs.getString(16),
                               rs.getString(17),
                               rs.getString(18),Sequel.cariIsi("select count(reg_periksa.no_rkm_medis) from reg_periksa where reg_periksa.no_rkm_medis=?",rs.getString(1))+" X"});
            }           
            
            cmbHlm.removeAllItems();
            pshal.setString(1,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(2,"%"+TCari.getText().trim()+"%");
            pshal.setString(3,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(4, "%"+TCari.getText().trim()+"%");
            pshal.setString(5,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(6, "%"+TCari.getText().trim()+"%");
            pshal.setString(7,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(8, "%"+TCari.getText().trim()+"%");
            pshal.setString(9,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(10, "%"+TCari.getText().trim()+"%");
            pshal.setString(11,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(12, "%"+TCari.getText().trim()+"%");
            pshal.setString(13,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(14, "%"+TCari.getText().trim()+"%");
            pshal.setString(15,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(16, "%"+TCari.getText().trim()+"%");
            pshal.setString(17,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(18, "%"+TCari.getText().trim()+"%");
            pshal.setString(19,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(20, "%"+TCari.getText().trim()+"%");
            pshal.setString(21,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(22, "%"+TCari.getText().trim()+"%");
            pshal.setString(23,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(24, "%"+TCari.getText().trim()+"%");
            pshal.setString(25,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(26, "%"+TCari.getText().trim()+"%");
            pshal.setString(27,"%"+Carialamat.getText().trim()+"%");
            pshal.setString(28, "%"+TCari.getText().trim()+"%");
            rs=pshal.executeQuery();
            jumlah=0;
            if(rs.next()){
                jumlah=rs.getDouble("jumlah");
            }       
            x=jumlah/499;
            i=Math.ceil(x);
            z=(int) i;
            
            hlm=null;            
            hlm=new int[z+1];
            for(j=1;j<=i;j++){
                 mulai=((j-1)*499+j)-1;
                 hlm[j]=mulai;
                 cmbHlm.addItem(j);
            }

        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
}
