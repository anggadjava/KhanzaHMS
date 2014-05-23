package khanzahms;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgInputStokPasien;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingRanap2;

/**
 *
 * @author perpustakaan
 */
public class DlgKamarInap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();

    public  DlgKamar kamar=new DlgKamar(null,false);
    public  DlgReg reg=new DlgReg(null,false);
    public  DlgBilingRanap2 billing=new DlgBilingRanap2( null,false);
    public  DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
    
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String now=dateFormat.format(date),kmr="",key="",tglmasuk,jammasuk;
    private PreparedStatement ps;
    private ResultSet rs;
    private int i;
    private double lama=Sequel.cariIsiAngka("select lamajam from set_jam_minimal");

    /** Creates new form DlgKamarInap
     * @param parent
       @param modal */
    public DlgKamarInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Rawat","Nomer RM","Nama Pasien","Alamat Pasien","Cara Bayar","Kamar","Tarif Kamar",
                    "Diagnosa Awal","Diagnosa Akhir","Tgl.Masuk","Jam Masuk","Tgl.Keluar","Jam Keluar",
                    "Ttl.Biaya Kamar","Stts.Pulang","Lama Rawat","Dokter P.J."};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(240);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(140);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(200);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        norawat.setDocument(new batasInput((byte)17).getKata(norawat));
        kdkamar.setDocument(new batasInput((byte)8).getKata(kdkamar));
        ttlbiaya.setDocument(new batasInput((byte)25).getKata(ttlbiaya));
        diagnosaawal.setDocument(new batasInput((byte)100).getKata(diagnosaawal));
        diagnosaakhir.setDocument(new batasInput((byte)25).getKata(diagnosaakhir));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));


        TJmlHari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        TTarif.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        Valid.LoadTahun(CmbTahun);   
        Valid.LoadTahun(CmbTahunpindah);      
        
        WindowInput.setSize(675,275);
        WindowInput.setLocationRelativeTo(null);
        WindowInput2.setSize(675,285);
        WindowInput2.setLocationRelativeTo(null);
        
        kamar.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdkamar.setText(kamar.getTextField().getText());  
                kdkamarpindah.setText(kamar.getTextField().getText()); 
                isKmr();
                if((WindowInput.isVisible()==true)&&(!TBangsal.getText().equals(""))&&(!norawat.getText().equals(""))){
                     if(TIn.getText().equals("")){                 
                        tglmasuk=CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem();
                        jammasuk=cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem();
                     }else{
                         tglmasuk=TIn.getText();
                         jammasuk=JamMasuk.getText();
                     }
                     Sequel.cariIsi("select if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+" "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')) as lama",TJmlHari);             
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
        
        kamar.bangsal.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal='"+kamar.bangsal.getTextField().getText()+"'",BangsalCari);  
                tampil();
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
        
        reg.pasien.penjab.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdpenjab.setText(reg.pasien.penjab.getTextField().getText());
                Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpenjab,kdpenjab.getText());
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
        
        reg.getButton().addActionListener((ActionEvent e) -> {
            norawat.setText(reg.getTextField().getText());
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,norawat.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        });
        
        //kamar.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        //kamar.setModalityType(ModalityType.APPLICATION_MODAL);
        //bangsal.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        //bangsal.setModalityType(ModalityType.APPLICATION_MODAL);
        //reg.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        //reg.setModalityType(ModalityType.APPLICATION_MODAL);
        
        CmbTahun.setSelectedItem(now.substring(0,4));
        CmbBln.setSelectedItem(now.substring(5,7));
        CmbTgl.setSelectedItem(now.substring(8,10));
        cmbJam.setSelectedItem(now.substring(11,13));
        cmbMnt.setSelectedItem(now.substring(14,16));
        cmbDtk.setSelectedItem(now.substring(17,19));      
        try {
            ps=koneksi.prepareStatement("select no_rawat, kd_kamar, diagnosa_awal, diagnosa_akhir, tgl_masuk, jam_masuk, tgl_keluar, jam_keluar, ttl_biaya "+
                    "from kamar_inap where no_rawat=? order by tgl_masuk,jam_masuk desc limit 1 ");
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

        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        norawat = new widget.TextBox();
        TPasien = new widget.TextBox();
        kdkamar = new widget.TextBox();
        jLabel10 = new widget.Label();
        ttlbiaya = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel12 = new widget.Label();
        BtnSeek = new widget.Button();
        TNoRM = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        TKdBngsal = new widget.TextBox();
        jLabel13 = new widget.Label();
        cmbDtk = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbJam = new widget.ComboBox();
        TBangsal = new widget.TextBox();
        jLabel11 = new widget.Label();
        TJmlHari = new widget.TextBox();
        jLabel15 = new widget.Label();
        TSttsKamar = new widget.TextBox();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        jLabel14 = new widget.Label();
        jLabel16 = new widget.Label();
        TTarif = new widget.TextBox();
        LblStts = new widget.Label();
        jLabel18 = new widget.Label();
        diagnosaawal = new widget.TextBox();
        diagnosaakhir = new widget.TextBox();
        jLabel23 = new widget.Label();
        CmbTahun = new widget.ComboBox();
        CmbBln = new widget.ComboBox();
        CmbTgl = new widget.ComboBox();
        jLabel24 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel26 = new widget.Label();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRawanInap = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnDiet = new javax.swing.JMenuItem();
        MnResepObat = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        MnRujuk = new javax.swing.JMenuItem();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MnStokObatPasien = new javax.swing.JMenuItem();
        MnHapusDataSalah = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        JamMasuk = new widget.TextBox();
        WindowInput2 = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        norawatpindah = new widget.TextBox();
        TPasienpindah = new widget.TextBox();
        kdkamarpindah = new widget.TextBox();
        ttlbiayapindah = new widget.TextBox();
        jLabel4 = new widget.Label();
        jLabel20 = new widget.Label();
        TNoRMpindah = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        TKdBngsalpindah = new widget.TextBox();
        jLabel27 = new widget.Label();
        cmbDtkpindah = new widget.ComboBox();
        cmbMntpindah = new widget.ComboBox();
        cmbJampindah = new widget.ComboBox();
        TBangsalpindah = new widget.TextBox();
        jLabel28 = new widget.Label();
        TJmlHaripindah = new widget.TextBox();
        jLabel29 = new widget.Label();
        TSttsKamarpindah = new widget.TextBox();
        BtnCloseInpindah = new widget.Button();
        jLabel30 = new widget.Label();
        BtnSimpanpindah = new widget.Button();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        TTarifpindah = new widget.TextBox();
        CmbTahunpindah = new widget.ComboBox();
        CmbBlnpindah = new widget.ComboBox();
        CmbTglpindah = new widget.ComboBox();
        jLabel35 = new widget.Label();
        Rganti3 = new widget.RadioButton();
        jLabel33 = new widget.Label();
        Rganti2 = new widget.RadioButton();
        Rganti1 = new widget.RadioButton();
        Rganti4 = new widget.RadioButton();
        buttonGroup2 = new javax.swing.ButtonGroup();
        WindowInput4 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        BtnIn = new widget.Button();
        BtnOut = new widget.Button();
        btnPindah = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        jLabel21 = new widget.Label();
        BangsalCari = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        R3 = new widget.RadioButton();
        DTPCari3 = new widget.Tanggal();
        cmbJam1 = new widget.ComboBox();
        cmbMnt1 = new widget.ComboBox();
        cmbDtk1 = new widget.ComboBox();
        jLabel25 = new widget.Label();
        cmbJam2 = new widget.ComboBox();
        cmbMnt2 = new widget.ComboBox();
        cmbDtk2 = new widget.ComboBox();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Input Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame2.setLayout(null);

        norawat.setHighlighter(null);
        norawat.setName("norawat"); // NOI18N
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        internalFrame2.add(norawat);
        norawat.setBounds(75, 25, 150, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        internalFrame2.add(TPasien);
        TPasien.setBounds(359, 25, 269, 23);

        kdkamar.setHighlighter(null);
        kdkamar.setName("kdkamar"); // NOI18N
        kdkamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarKeyPressed(evt);
            }
        });
        internalFrame2.add(kdkamar);
        kdkamar.setBounds(75, 55, 95, 23);

        jLabel10.setText("Proses :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame2.add(jLabel10);
        jLabel10.setBounds(0, 175, 72, 23);

        ttlbiaya.setEditable(false);
        ttlbiaya.setText("0");
        ttlbiaya.setHighlighter(null);
        ttlbiaya.setName("ttlbiaya"); // NOI18N
        ttlbiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ttlbiayaKeyPressed(evt);
            }
        });
        internalFrame2.add(ttlbiaya);
        ttlbiaya.setBounds(368, 145, 290, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(0, 25, 72, 23);

        jLabel12.setText("Kamar :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame2.add(jLabel12);
        jLabel12.setBounds(0, 55, 72, 23);

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
        internalFrame2.add(BtnSeek);
        BtnSeek.setBounds(630, 25, 28, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        internalFrame2.add(TNoRM);
        TNoRM.setBounds(227, 25, 130, 23);

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
        internalFrame2.add(BtnSeek1);
        BtnSeek1.setBounds(428, 55, 28, 23);

        TKdBngsal.setEditable(false);
        TKdBngsal.setName("TKdBngsal"); // NOI18N
        internalFrame2.add(TKdBngsal);
        TKdBngsal.setBounds(172, 55, 82, 23);

        jLabel13.setText("Tanggal :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame2.add(jLabel13);
        jLabel13.setBounds(0, 85, 72, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbDtk);
        cmbDtk.setBounds(177, 115, 48, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbMnt);
        cmbMnt.setBounds(126, 115, 48, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbJam);
        cmbJam.setBounds(75, 115, 48, 23);

        TBangsal.setEditable(false);
        TBangsal.setHighlighter(null);
        TBangsal.setName("TBangsal"); // NOI18N
        TBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalKeyPressed(evt);
            }
        });
        internalFrame2.add(TBangsal);
        TBangsal.setBounds(256, 55, 170, 23);

        jLabel11.setForeground(new java.awt.Color(153, 0, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("X");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame2.add(jLabel11);
        jLabel11.setBounds(173, 145, 15, 23);

        TJmlHari.setText("0");
        TJmlHari.setHighlighter(null);
        TJmlHari.setName("TJmlHari"); // NOI18N
        internalFrame2.add(TJmlHari);
        TJmlHari.setBounds(75, 145, 96, 23);

        jLabel15.setForeground(new java.awt.Color(153, 0, 51));
        jLabel15.setText("=");
        jLabel15.setName("jLabel15"); // NOI18N
        internalFrame2.add(jLabel15);
        jLabel15.setBounds(342, 145, 20, 23);

        TSttsKamar.setEditable(false);
        TSttsKamar.setName("TSttsKamar"); // NOI18N
        internalFrame2.add(TSttsKamar);
        TSttsKamar.setBounds(548, 55, 110, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('C');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+C");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(560, 225, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 200, 850, 14);

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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(14, 225, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(117, 225, 100, 30);

        jLabel14.setText("Stts.Kamar :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame2.add(jLabel14);
        jLabel14.setBounds(444, 55, 100, 23);

        jLabel16.setText("Biaya :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame2.add(jLabel16);
        jLabel16.setBounds(0, 145, 72, 23);

        TTarif.setText("0");
        TTarif.setHighlighter(null);
        TTarif.setName("TTarif"); // NOI18N
        internalFrame2.add(TTarif);
        TTarif.setBounds(188, 145, 160, 23);

        LblStts.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblStts.setText("Check In");
        LblStts.setName("LblStts"); // NOI18N
        internalFrame2.add(LblStts);
        LblStts.setBounds(75, 175, 180, 23);

        jLabel18.setText("Diagnosa Awal Masuk :");
        jLabel18.setName("jLabel18"); // NOI18N
        internalFrame2.add(jLabel18);
        jLabel18.setBounds(275, 85, 140, 23);

        diagnosaawal.setHighlighter(null);
        diagnosaawal.setName("diagnosaawal"); // NOI18N
        diagnosaawal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaawalKeyPressed(evt);
            }
        });
        internalFrame2.add(diagnosaawal);
        diagnosaawal.setBounds(418, 85, 240, 23);

        diagnosaakhir.setHighlighter(null);
        diagnosaakhir.setName("diagnosaakhir"); // NOI18N
        diagnosaakhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaakhirKeyPressed(evt);
            }
        });
        internalFrame2.add(diagnosaakhir);
        diagnosaakhir.setBounds(418, 115, 240, 23);

        jLabel23.setText("Diagnosa Akhir Keluar :");
        jLabel23.setName("jLabel23"); // NOI18N
        internalFrame2.add(jLabel23);
        jLabel23.setBounds(275, 115, 140, 23);

        CmbTahun.setName("CmbTahun"); // NOI18N
        CmbTahun.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTahunKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbTahun);
        CmbTahun.setBounds(75, 85, 70, 23);

        CmbBln.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        CmbBln.setName("CmbBln"); // NOI18N
        CmbBln.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbBln.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbBlnKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbBln);
        CmbBln.setBounds(148, 85, 48, 23);

        CmbTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        CmbTgl.setName("CmbTgl"); // NOI18N
        CmbTgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTglKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbTgl);
        CmbTgl.setBounds(199, 85, 48, 23);

        jLabel24.setText("Jam :");
        jLabel24.setName("jLabel24"); // NOI18N
        internalFrame2.add(jLabel24);
        jLabel24.setBounds(0, 115, 72, 23);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Rujuk", "APS", "+", "Meninggal", "Sembuh", "Membaik", "Pulang Paksa", "-" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbStatusKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbStatus);
        cmbStatus.setBounds(418, 175, 240, 23);

        jLabel26.setText("Status Pulang/Keluar :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame2.add(jLabel26);
        jLabel26.setBounds(275, 175, 140, 23);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        TOut.setEditable(false);
        TOut.setForeground(new java.awt.Color(255, 255, 255));
        TOut.setHighlighter(null);
        TOut.setName("TOut"); // NOI18N
        TOut.setSelectionColor(new java.awt.Color(255, 255, 255));
        TOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOutKeyPressed(evt);
            }
        });

        TIn.setEditable(false);
        TIn.setForeground(new java.awt.Color(255, 255, 255));
        TIn.setHighlighter(null);
        TIn.setName("TIn"); // NOI18N
        TIn.setSelectionColor(new java.awt.Color(255, 255, 255));
        TIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInKeyPressed(evt);
            }
        });

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRawanInap.setBackground(new java.awt.Color(255, 255, 255));
        MnRawanInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawanInap.setForeground(java.awt.Color.darkGray);
        MnRawanInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawanInap.setText("Tagihan/Tindakan Rawat Inap");
        MnRawanInap.setName("MnRawanInap"); // NOI18N
        MnRawanInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawanInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawanInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawanInap);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(java.awt.Color.darkGray);
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat/BHP");
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        MnDiet.setBackground(new java.awt.Color(255, 255, 255));
        MnDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiet.setForeground(java.awt.Color.darkGray);
        MnDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiet.setText("Diet Pasien");
        MnDiet.setName("MnDiet"); // NOI18N
        MnDiet.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDietActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDiet);

        MnResepObat.setBackground(new java.awt.Color(255, 255, 255));
        MnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepObat.setForeground(java.awt.Color.darkGray);
        MnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepObat.setText("Resep Pulang");
        MnResepObat.setName("MnResepObat"); // NOI18N
        MnResepObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnResepObat);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(java.awt.Color.darkGray);
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaLab);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(java.awt.Color.darkGray);
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi");
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnOperasi);

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(java.awt.Color.darkGray);
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Hapus Tagihan Operasi");
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(java.awt.Color.darkGray);
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Hapus Obat Operasi");
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusObatOperasi);

        MnBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(java.awt.Color.darkGray);
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        MnRujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(java.awt.Color.darkGray);
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujuk Keluar");
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRujuk);

        MnRujukMasuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(java.awt.Color.darkGray);
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukMasuk.setText("Rujukan Masuk");
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRujukMasuk);

        MnStokObatPasien.setBackground(new java.awt.Color(255, 255, 255));
        MnStokObatPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStokObatPasien.setForeground(java.awt.Color.darkGray);
        MnStokObatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStokObatPasien.setText("Stok Obat Pasien Ranap");
        MnStokObatPasien.setName("MnStokObatPasien"); // NOI18N
        MnStokObatPasien.setPreferredSize(new java.awt.Dimension(250, 28));
        MnStokObatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStokObatPasienActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnStokObatPasien);

        MnHapusDataSalah.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDataSalah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDataSalah.setForeground(java.awt.Color.darkGray);
        MnHapusDataSalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDataSalah.setText("Hapus Data Salah");
        MnHapusDataSalah.setName("MnHapusDataSalah"); // NOI18N
        MnHapusDataSalah.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusDataSalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDataSalahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusDataSalah);

        MnPenjab.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(java.awt.Color.darkGray);
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Ganti Cara Bayar");
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjab);

        JamMasuk.setEditable(false);
        JamMasuk.setForeground(new java.awt.Color(255, 255, 255));
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        JamMasuk.setSelectionColor(new java.awt.Color(255, 255, 255));
        JamMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMasukKeyPressed(evt);
            }
        });

        WindowInput2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput2.setName("WindowInput2"); // NOI18N
        WindowInput2.setUndecorated(true);
        WindowInput2.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Pindah Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame3.setLayout(null);

        norawatpindah.setEditable(false);
        norawatpindah.setHighlighter(null);
        norawatpindah.setName("norawatpindah"); // NOI18N
        internalFrame3.add(norawatpindah);
        norawatpindah.setBounds(75, 25, 150, 23);

        TPasienpindah.setEditable(false);
        TPasienpindah.setHighlighter(null);
        TPasienpindah.setName("TPasienpindah"); // NOI18N
        internalFrame3.add(TPasienpindah);
        TPasienpindah.setBounds(359, 25, 299, 23);

        kdkamarpindah.setHighlighter(null);
        kdkamarpindah.setName("kdkamarpindah"); // NOI18N
        kdkamarpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(kdkamarpindah);
        kdkamarpindah.setBounds(75, 55, 95, 23);

        ttlbiayapindah.setEditable(false);
        ttlbiayapindah.setText("0");
        ttlbiayapindah.setHighlighter(null);
        ttlbiayapindah.setName("ttlbiayapindah"); // NOI18N
        internalFrame3.add(ttlbiayapindah);
        ttlbiayapindah.setBounds(368, 115, 290, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame3.add(jLabel4);
        jLabel4.setBounds(0, 25, 72, 23);

        jLabel20.setText("Kamar :");
        jLabel20.setName("jLabel20"); // NOI18N
        internalFrame3.add(jLabel20);
        jLabel20.setBounds(0, 55, 72, 23);

        TNoRMpindah.setEditable(false);
        TNoRMpindah.setHighlighter(null);
        TNoRMpindah.setName("TNoRMpindah"); // NOI18N
        internalFrame3.add(TNoRMpindah);
        TNoRMpindah.setBounds(227, 25, 130, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('2');
        BtnSeek4.setToolTipText("Alt+2");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        internalFrame3.add(BtnSeek4);
        BtnSeek4.setBounds(428, 55, 28, 23);

        TKdBngsalpindah.setEditable(false);
        TKdBngsalpindah.setName("TKdBngsalpindah"); // NOI18N
        internalFrame3.add(TKdBngsalpindah);
        TKdBngsalpindah.setBounds(172, 55, 82, 23);

        jLabel27.setText("Tanggal :");
        jLabel27.setName("jLabel27"); // NOI18N
        internalFrame3.add(jLabel27);
        jLabel27.setBounds(0, 85, 72, 23);

        cmbDtkpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtkpindah.setName("cmbDtkpindah"); // NOI18N
        internalFrame3.add(cmbDtkpindah);
        cmbDtkpindah.setBounds(466, 85, 55, 23);

        cmbMntpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMntpindah.setName("cmbMntpindah"); // NOI18N
        internalFrame3.add(cmbMntpindah);
        cmbMntpindah.setBounds(408, 85, 55, 23);

        cmbJampindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJampindah.setName("cmbJampindah"); // NOI18N
        internalFrame3.add(cmbJampindah);
        cmbJampindah.setBounds(350, 85, 55, 23);

        TBangsalpindah.setEditable(false);
        TBangsalpindah.setHighlighter(null);
        TBangsalpindah.setName("TBangsalpindah"); // NOI18N
        TBangsalpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(TBangsalpindah);
        TBangsalpindah.setBounds(256, 55, 170, 23);

        jLabel28.setForeground(new java.awt.Color(153, 0, 51));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("X");
        jLabel28.setName("jLabel28"); // NOI18N
        internalFrame3.add(jLabel28);
        jLabel28.setBounds(173, 115, 15, 23);

        TJmlHaripindah.setText("0");
        TJmlHaripindah.setHighlighter(null);
        TJmlHaripindah.setName("TJmlHaripindah"); // NOI18N
        internalFrame3.add(TJmlHaripindah);
        TJmlHaripindah.setBounds(75, 115, 96, 23);

        jLabel29.setForeground(new java.awt.Color(153, 0, 51));
        jLabel29.setText("=");
        jLabel29.setName("jLabel29"); // NOI18N
        internalFrame3.add(jLabel29);
        jLabel29.setBounds(342, 115, 20, 23);

        TSttsKamarpindah.setEditable(false);
        TSttsKamarpindah.setName("TSttsKamarpindah"); // NOI18N
        internalFrame3.add(TSttsKamarpindah);
        TSttsKamarpindah.setBounds(548, 55, 110, 23);

        BtnCloseInpindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseInpindah.setMnemonic('C');
        BtnCloseInpindah.setText("Tutup");
        BtnCloseInpindah.setToolTipText("Alt+C");
        BtnCloseInpindah.setName("BtnCloseInpindah"); // NOI18N
        BtnCloseInpindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInpindahActionPerformed(evt);
            }
        });
        BtnCloseInpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(BtnCloseInpindah);
        BtnCloseInpindah.setBounds(560, 235, 100, 30);

        jLabel30.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame3.add(jLabel30);
        jLabel30.setBounds(-10, 215, 850, 14);

        BtnSimpanpindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanpindah.setMnemonic('S');
        BtnSimpanpindah.setText("Simpan");
        BtnSimpanpindah.setToolTipText("Alt+S");
        BtnSimpanpindah.setName("BtnSimpanpindah"); // NOI18N
        BtnSimpanpindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanpindahActionPerformed(evt);
            }
        });
        BtnSimpanpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpanpindah);
        BtnSimpanpindah.setBounds(14, 235, 100, 30);

        jLabel31.setText("Stts.Kamar :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame3.add(jLabel31);
        jLabel31.setBounds(444, 55, 100, 23);

        jLabel32.setText("Pilihan :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame3.add(jLabel32);
        jLabel32.setBounds(0, 145, 72, 23);

        TTarifpindah.setEditable(false);
        TTarifpindah.setText("0");
        TTarifpindah.setHighlighter(null);
        TTarifpindah.setName("TTarifpindah"); // NOI18N
        internalFrame3.add(TTarifpindah);
        TTarifpindah.setBounds(188, 115, 160, 23);

        CmbTahunpindah.setName("CmbTahunpindah"); // NOI18N
        internalFrame3.add(CmbTahunpindah);
        CmbTahunpindah.setBounds(75, 85, 80, 23);

        CmbBlnpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        CmbBlnpindah.setName("CmbBlnpindah"); // NOI18N
        internalFrame3.add(CmbBlnpindah);
        CmbBlnpindah.setBounds(158, 85, 55, 23);

        CmbTglpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        CmbTglpindah.setName("CmbTglpindah"); // NOI18N
        internalFrame3.add(CmbTglpindah);
        CmbTglpindah.setBounds(216, 85, 55, 23);

        jLabel35.setText("Jam :");
        jLabel35.setName("jLabel35"); // NOI18N
        internalFrame3.add(jLabel35);
        jLabel35.setBounds(280, 85, 67, 23);

        buttonGroup2.add(Rganti3);
        Rganti3.setText("3. Kamar Inap sebelumnya distatuskan pindah, lama inap dihitung dan pasien dimasukkan Ke Kamar inap yang baru");
        Rganti3.setName("Rganti3"); // NOI18N
        internalFrame3.add(Rganti3);
        Rganti3.setBounds(75, 176, 620, 20);

        jLabel33.setText("Biaya :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame3.add(jLabel33);
        jLabel33.setBounds(0, 115, 72, 23);

        buttonGroup2.add(Rganti2);
        Rganti2.setText("2. Kamar Inap sebelumnya diganti kamarnya dengan Kamar Inap terbaru dan harga kamar menyesuaikan harga baru");
        Rganti2.setName("Rganti2"); // NOI18N
        internalFrame3.add(Rganti2);
        Rganti2.setBounds(75, 159, 620, 20);

        buttonGroup2.add(Rganti1);
        Rganti1.setText("1. Kamar Inap sebelumnya dihapus dan pasien dihitung menginap mulai saat ini (Kamar Inap lama dihapus dari billing)");
        Rganti1.setName("Rganti1"); // NOI18N
        internalFrame3.add(Rganti1);
        Rganti1.setBounds(75, 142, 620, 20);

        buttonGroup2.add(Rganti4);
        Rganti4.setSelected(true);
        Rganti4.setText("4. Seperti nomer 3, Kamar Inap sebelumnya mengikuti harga tertinggi");
        Rganti4.setName("Rganti4"); // NOI18N
        internalFrame3.add(Rganti4);
        Rganti4.setBounds(75, 193, 620, 20);

        WindowInput2.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        WindowInput4.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput4.setName("WindowInput4"); // NOI18N
        WindowInput4.setUndecorated(true);
        WindowInput4.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Cara Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('P');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+P");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel17.setText("Cara Bayar :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame5.add(jLabel17);
        jLabel17.setBounds(0, 32, 77, 23);

        kdpenjab.setHighlighter(null);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setSelectionColor(new java.awt.Color(255, 255, 255));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        internalFrame5.add(kdpenjab);
        kdpenjab.setBounds(81, 32, 100, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame5.add(nmpenjab);
        nmpenjab.setBounds(183, 32, 181, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('7');
        BtnSeek5.setToolTipText("ALt+7");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSeek5);
        BtnSeek5.setBounds(366, 32, 28, 23);

        WindowInput4.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnIn.setMnemonic('M');
        BtnIn.setText("Masuk");
        BtnIn.setToolTipText("Alt+M");
        BtnIn.setName("BtnIn"); // NOI18N
        BtnIn.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInActionPerformed(evt);
            }
        });
        BtnIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnInKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnIn);

        BtnOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnOut.setMnemonic('U');
        BtnOut.setText("Pulang");
        BtnOut.setToolTipText("Alt+U");
        BtnOut.setName("BtnOut"); // NOI18N
        BtnOut.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOutActionPerformed(evt);
            }
        });
        BtnOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOutKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnOut);

        btnPindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        btnPindah.setMnemonic('P');
        btnPindah.setText("Pindah");
        btnPindah.setToolTipText("Alt+P");
        btnPindah.setName("btnPindah"); // NOI18N
        btnPindah.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPindahActionPerformed(evt);
            }
        });
        btnPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPindahKeyPressed(evt);
            }
        });
        panelGlass10.add(btnPindah);

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
        panelGlass10.add(BtnHapus);

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
        panelGlass10.add(BtnPrint);

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
        panelGlass10.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass10.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(75, 30));
        panelGlass10.add(LCount);

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
        panelGlass10.add(BtnKeluar);

        PanelCariUtama.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel21.setText("Kamar :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass11.add(jLabel21);

        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(280, 23));
        BangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangsalCariKeyPressed(evt);
            }
        });
        panelGlass11.add(BangsalCari);

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
        panelGlass11.add(BtnSeek2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(97, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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
        panelGlass11.add(BtnCari);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        R1.setBackground(new java.awt.Color(240, 250, 230));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setForeground(new java.awt.Color(50, 70, 50));
        R1.setSelected(true);
        R1.setText("Belum Pulang");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(95, 23));
        R1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                R1ItemStateChanged(evt);
            }
        });
        panelCari.add(R1);

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setForeground(new java.awt.Color(50, 70, 50));
        R2.setText("Tgl.Masuk :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(90, 23));
        R2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                R2ItemStateChanged(evt);
            }
        });
        panelCari.add(R2);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-16" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel22);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-16" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setForeground(new java.awt.Color(50, 70, 50));
        R3.setText("Pulang :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(70, 23));
        R3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                R3ItemStateChanged(evt);
            }
        });
        panelCari.add(R3);

        DTPCari3.setEditable(false);
        DTPCari3.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-16" }));
        DTPCari3.setDisplayFormat("yyyy-MM-dd");
        DTPCari3.setName("DTPCari3"); // NOI18N
        DTPCari3.setOpaque(false);
        DTPCari3.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari3ItemStateChanged(evt);
            }
        });
        DTPCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari3KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari3);

        cmbJam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam1.setName("cmbJam1"); // NOI18N
        cmbJam1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJam1ItemStateChanged(evt);
            }
        });
        cmbJam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJam1KeyPressed(evt);
            }
        });
        panelCari.add(cmbJam1);

        cmbMnt1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt1.setName("cmbMnt1"); // NOI18N
        cmbMnt1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMnt1ItemStateChanged(evt);
            }
        });
        cmbMnt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMnt1KeyPressed(evt);
            }
        });
        panelCari.add(cmbMnt1);

        cmbDtk1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk1.setName("cmbDtk1"); // NOI18N
        cmbDtk1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDtk1ItemStateChanged(evt);
            }
        });
        cmbDtk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtk1KeyPressed(evt);
            }
        });
        panelCari.add(cmbDtk1);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel25);

        cmbJam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam2.setName("cmbJam2"); // NOI18N
        cmbJam2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJam2ItemStateChanged(evt);
            }
        });
        cmbJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJam2KeyPressed(evt);
            }
        });
        panelCari.add(cmbJam2);

        cmbMnt2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt2.setName("cmbMnt2"); // NOI18N
        cmbMnt2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMnt2ItemStateChanged(evt);
            }
        });
        cmbMnt2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMnt2KeyPressed(evt);
            }
        });
        panelCari.add(cmbMnt2);

        cmbDtk2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk2.setName("cmbDtk2"); // NOI18N
        cmbDtk2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDtk2ItemStateChanged(evt);
            }
        });
        cmbDtk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtk2KeyPressed(evt);
            }
        });
        panelCari.add(cmbDtk2);

        PanelCariUtama.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setAutoCreateRowSorter(true);
        tbKamIn.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamIn.setComponentPopupMenu(jPopupMenu1);
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void norawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,norawat.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        }else{
            Valid.pindah(evt,BtnCloseIn,kdkamar);
        }
}//GEN-LAST:event_norawatKeyPressed

    private void kdkamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkamarKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            i=1;
            isKmr();
        }else{
            Valid.pindah(evt,norawat,CmbTahun);
        }
}//GEN-LAST:event_kdkamarKeyPressed

    private void ttlbiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttlbiayaKeyPressed
       // Valid.pindah(evt,TKdOb,BtnSimpan);
}//GEN-LAST:event_ttlbiayaKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        reg.tampil();
        reg.emptTeks();
        reg.isCek();
        reg.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        reg.setLocationRelativeTo(internalFrame1);
        reg.setAlwaysOnTop(false);
        reg.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,norawat,kdkamar);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        var.setStatus(true);
        i=1;
        kamar.tampil();
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setAlwaysOnTop(false);
        kamar.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,kdkamar,CmbTahun);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInActionPerformed
        norawat.setEditable(true);
        kdkamar.setEditable(true);
        diagnosaawal.setEditable(true);
        diagnosaakhir.setVisible(false);
        cmbStatus.setVisible(false);
        jLabel26.setVisible(false);
        jLabel23.setVisible(false);
        LblStts.setText("Masuk/Check In");
        BtnSeek.setEnabled(true);
        BtnSeek1.setEnabled(true);
        emptTeks();
        lama=Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
        WindowInput.setVisible(true);
}//GEN-LAST:event_BtnInActionPerformed

    private void BtnInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnInActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnOut);
        }
}//GEN-LAST:event_BtnInKeyPressed

    private void BtnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOutActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data inap pasien yang mau pulang dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        }if(TOut.getText().trim().length()>0){
                 JOptionPane.showMessageDialog(null,"Maaf, pasien ini sudah pulang pada tanggal "+TOut.getText()+" ...!!!");
                 emptTeks();
                 tbKamIn.requestFocus();
        }else if((TOut.getText().length()==0)&&(TPasien.getText().length()>0)){
                norawat.setEditable(false);
                kdkamar.setEditable(false);
                i=1;
                isKmr();
                diagnosaawal.setEditable(false);
                
                diagnosaakhir.setVisible(true);
                jLabel23.setVisible(true);
                diagnosaakhir.setText("");
                cmbStatus.setVisible(true);
                jLabel26.setVisible(true);
                lama=Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
                LblStts.setText("Pulang/Check Out");

                BtnSeek.setEnabled(false);
                BtnSeek1.setEnabled(false);

                CmbTahun.setSelectedItem(now.substring(0,4));
                CmbBln.setSelectedItem(now.substring(5,7));
                CmbTgl.setSelectedItem(now.substring(8,10));
                cmbJam.setSelectedItem(now.substring(11,13));
                cmbMnt.setSelectedItem(now.substring(14,16));
                cmbDtk.setSelectedItem(now.substring(17,19));  
                tglmasuk=TIn.getText();
                jammasuk=JamMasuk.getText();
                Sequel.cariIsi("select if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+" "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')) as lama",TJmlHari);             

                norawat.requestFocus();   
                isjml();    
                WindowInput.setVisible(true);
        }
}//GEN-LAST:event_BtnOutActionPerformed

    private void BtnOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOutKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnOutActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnIn,btnPindah);
        }
}//GEN-LAST:event_BtnOutKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            norawat.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Data pasien dengan No.Rawat "+norawat.getText()+" belum pulang/check out. Data belum bisa anda hapus...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from kamar_inap where no_rawat='"+norawat.getText()+"' and kd_kamar='"+kdkamar.getText()+"' and tgl_masuk='"+TIn.getText()+"' and jam_masuk='"+JamMasuk.getText()+"'");
                tampil();
            }catch(Exception e){
                System.out.println("Pesan Error : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }

        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, btnPindah,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            WindowInput.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
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
                if(R1.isSelected()==true){
                    kmr=" tgl_keluar='0000:00:00' ";
                    if(!BangsalCari.getText().equals("")){
                        kmr=" tgl_keluar='0000:00:00' and nm_bangsal='"+BangsalCari.getText()+"' ";
                    }
                }else if(R2.isSelected()==true){
                    kmr=" tgl_masuk between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ";
                    if(!BangsalCari.getText().equals("")){
                        kmr=" tgl_masuk between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and nm_bangsal='"+BangsalCari.getText()+"' ";
                    }
                }else if(R3.isSelected()==true){
                    kmr=" kamar_inap.tgl_keluar='"+DTPCari3.getSelectedItem()+"' and kamar_inap.jam_keluar between '"+
                          cmbJam1.getSelectedItem()+":"+cmbMnt1.getSelectedItem()+":"+cmbDtk1.getSelectedItem()+"' and '"+
                          cmbJam2.getSelectedItem()+":"+cmbMnt2.getSelectedItem()+":"+cmbDtk2.getSelectedItem()+"' ";
                    if(!BangsalCari.getText().equals("")){
                        kmr=" kamar_inap.tgl_keluar='"+DTPCari3.getSelectedItem()+"' and kamar_inap.jam_keluar between '"+
                          cmbJam1.getSelectedItem()+":"+cmbMnt1.getSelectedItem()+":"+cmbDtk1.getSelectedItem()+"' and '"+
                          cmbJam2.getSelectedItem()+":"+cmbMnt2.getSelectedItem()+":"+cmbDtk2.getSelectedItem()+"' and bangsal.nm_bangsal='"+BangsalCari.getText()+"' ";
                    }
                }
                key=kmr+" ";
                if(!TCari.getText().equals("")){
                      key= kmr+"and kamar_inap.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and kamar_inap.kd_kamar like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and nm_bangsal like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and kamar_inap.diagnosa_awal like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and kamar_inap.diagnosa_akhir like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and trf_kamar like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and tgl_masuk like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and kamar_inap.stts_pulang like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and tgl_keluar like '%"+TCari.getText().trim()+"%' or "+
                           kmr+"and ttl_biaya like '%"+TCari.getText().trim()+"%' ";
                }

                String sql="select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir," +
                           "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,kamar_inap.tgl_keluar,kamar_inap.jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang  " +
                           "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal  inner join dokter " +
                           "on kamar_inap.no_rawat=reg_periksa.no_rawat " +
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                           "and kamar_inap.kd_kamar=kamar.kd_kamar " +
                           "and kamar.kd_bangsal=bangsal.kd_bangsal " +
                           "and reg_periksa.kd_dokter=dokter.kd_dokter " +
                           "where  "+key+" order by kamar_inap.no_rawat";
                Valid.MyReport("rptKamarInap.jrxml","report","::[ Data Kamar Inap Pasien (Umum) ]::",sql);

        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{Valid.pindah(evt, BtnKeluar, BtnCari);}
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        BangsalCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnIn);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
      Valid.pindah(evt,kdkamar,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,diagnosaawal);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,CmbTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void TBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBangsalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBangsalKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowInput.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, norawat);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TPasien.getText().trim().equals("")){
            Valid.textKosong(norawat,"pasien");
        }else if(TKdBngsal.getText().trim().equals("")){
            Valid.textKosong(kdkamar,"kamar");
        }else{
            if(norawat.isEditable()==true){
                switch (TSttsKamar.getText().trim()) {
                    case "ISI":
                        JOptionPane.showMessageDialog(null,"Maaf, Status kamar sudah terisi silahkan pilih kamar kosong..!!!!");
                        kdkamar.requestFocus();
                        break;
                    case "KOSONG":
                        Sequel.menyimpan("kamar_inap","'"+norawat.getText()+"','"+
                                kdkamar.getText()+"','"+
                                diagnosaawal.getText()+"','"+
                                diagnosaakhir.getText()+"','"+
                                CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"','"+
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','0000-00-00','00:00:00','"+
                                ttlbiaya.getText()+"','-'","No.Rawat");                        
                        Sequel.mengedit("reg_periksa","no_rawat='"+norawat.getText()+"'","status_lanjut='Ranap'");
                        Sequel.mengedit("kamar","kd_kamar='"+kdkamar.getText()+"'","status='ISI'");                
                        emptTeks();
                        break;
                }
                norawat.requestFocus();
            }else if(norawat.isEditable()==false){
                Sequel.mengedit("kamar_inap","no_rawat='"+norawat.getText()+"' and kd_kamar='"+kdkamar.getText()+"' and tgl_masuk='"+TIn.getText()+"' and jam_masuk='"+JamMasuk.getText()+"'","tgl_keluar='"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+
                        "',jam_keluar='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+
                        "',ttl_biaya='"+ttlbiaya.getText()+"',stts_pulang='"+cmbStatus.getSelectedItem()+"'");                
                if(cmbStatus.getSelectedItem().equals("Meninggal")){
                    Sequel.menyimpan("pasien_mati","'"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"','"+
                                   cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+diagnosaakhir.getText()+"'","pasien");
                }else if(cmbStatus.getSelectedItem().equals("Rujuk")){
                    DlgRujuk dlgrjk=new DlgRujuk(null,false);
                    dlgrjk.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.emptTeks();
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(norawat.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
                    dlgrjk.tampil();
                    dlgrjk.setAlwaysOnTop(false);
                    dlgrjk.setVisible(true);
                }
                Sequel.mengedit("kamar","kd_kamar='"+kdkamar.getText()+"'","status='KOSONG'");
                WindowInput.dispose();
                emptTeks();
            }
            tampil();            
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,cmbDtk,BtnBatal);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(norawat.isEditable()==true){
            emptTeks();
        }else if(norawat.isEditable()==false){
            emptTeks();
            WindowInput.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TInKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged
        
    }//GEN-LAST:event_DTPTglItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
         R2.setSelected(true);
         tampil();          
       
}//GEN-LAST:event_DTPCari1ItemStateChanged

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        Valid.pindah(evt,BangsalCari,DTPCari2);
}//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        Valid.pindah(evt,DTPCari1,BangsalCari);
}//GEN-LAST:event_DTPCari2KeyPressed

    private void diagnosaawalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagnosaawalKeyPressed
        Valid.pindah(evt, cmbDtk, diagnosaakhir);
    }//GEN-LAST:event_diagnosaawalKeyPressed

    private void diagnosaakhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagnosaakhirKeyPressed
        Valid.pindah(evt, diagnosaawal, BtnSimpan);
    }//GEN-LAST:event_diagnosaakhirKeyPressed

    private void CmbTahunItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbTahunItemStateChanged
         if((WindowInput.isVisible()==true)&&(!TBangsal.getText().equals(""))&&(!norawat.getText().equals(""))){
             if(TIn.getText().equals("")){                 
                tglmasuk=CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem();
                jammasuk=cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem();
             }else{
                 tglmasuk=TIn.getText();
                 jammasuk=JamMasuk.getText();
             }
             Sequel.cariIsi("select if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+" "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')) as lama",TJmlHari);             
         }
    }//GEN-LAST:event_CmbTahunItemStateChanged

    private void CmbTahunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbTahunKeyPressed
        Valid.pindah(evt,kdkamar,CmbBln);
    }//GEN-LAST:event_CmbTahunKeyPressed

    private void CmbBlnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbBlnKeyPressed
        Valid.pindah(evt,CmbTahun,CmbTgl);
    }//GEN-LAST:event_CmbBlnKeyPressed

    private void CmbTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbTglKeyPressed
        Valid.pindah(evt,CmbBln,cmbJam);
    }//GEN-LAST:event_CmbTglKeyPressed

    private void TOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOutKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TOutKeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                i=tbKamIn.getSelectedColumn();
                if(i==0){
                    MnRawanInapActionPerformed(null);
                }else if(i==1){
                    MnPemberianObatActionPerformed(null);
                }else if(i==2){
                    MnBillingActionPerformed(null);
                }else if(i==3){
                    MnDietActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbKamIn.getSelectedColumn();
                if(i==0){
                    MnRawanInapActionPerformed(null);
                }else if(i==1){
                    MnPemberianObatActionPerformed(null);
                }else if(i==2){
                    MnBillingActionPerformed(null);
                }else if(i==3){
                    MnDietActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        var.setStatus(true);
        kamar.bangsal.tampil();
        kamar.bangsal.emptTeks();        
        kamar.bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kamar.bangsal.setLocationRelativeTo(internalFrame1);
        kamar.bangsal.setAlwaysOnTop(false);
        kamar.bangsal.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BangsalCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else{Valid.pindah(evt, DTPCari2, TCari);}
}//GEN-LAST:event_BangsalCariKeyPressed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
   if((!(TOut.getText().trim().length()>0))&&(var.getStatus()==true)){
       WindowInput.setVisible(true);
   }
}//GEN-LAST:event_formWindowOpened

private void MnRawanInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawanInapActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{
                billing.rawatinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                billing.rawatinap.setLocationRelativeTo(internalFrame1);
                billing.rawatinap.emptTeks();    
                billing.rawatinap.isCek();
                billing.rawatinap.setNoRm(norawat.getText(),DTPCari1.getDate(),DTPCari2.getDate());   
                billing.rawatinap.tampilDr();
                billing.rawatinap.setAlwaysOnTop(false);
                billing.rawatinap.setVisible(true);
                //this.dispose();
      }
           
        
}//GEN-LAST:event_MnRawanInapActionPerformed

private void MnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepObatActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{
            billing.reseppulang.emptTeks();
            billing.reseppulang.isCek();
            billing.reseppulang.setNoRm(norawat.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norawat.getText()+"'"),
                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+norawat.getText()+"'"));
            billing.reseppulang.tampil(" order by databarang.nama_brng");
            billing.reseppulang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            billing.reseppulang.setLocationRelativeTo(internalFrame1);
            billing.reseppulang.setAlwaysOnTop(false);
            billing.reseppulang.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnResepObatActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{
                DlgRujuk dlgrjk=new DlgRujuk(null,false);
                dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.emptTeks();
                dlgrjk.isCek();
                dlgrjk.setNoRm(norawat.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
                dlgrjk.tampil();
                dlgrjk.setAlwaysOnTop(false);
                dlgrjk.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{    
                var.setBangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar='"+kdkamar.getText()+"'"));
                billing.beriobat.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                billing.beriobat.setLocationRelativeTo(internalFrame1);
                billing.beriobat.emptTeks(); 
                billing.beriobat.isCek();
                billing.beriobat.setNoRm(norawat.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                billing.beriobat.tampilPO();
                billing.beriobat.setAlwaysOnTop(false);
                billing.beriobat.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{               
                var.setBangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",kdkamar.getText()));
                billing.TNoRw.setText(norawat.getText());  
                billing.TBayar.setText("0");
                billing.TKembali.setText("0");    
                billing.isRawat(); 
                billing.isKembali();
                billing.isCek();
                billing.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                billing.setLocationRelativeTo(internalFrame1);
                billing.setAlwaysOnTop(false);
                billing.setVisible(true);
               // this.dispose();
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void MnDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDietActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{
                DlgPemberianDiet rawatinap=new DlgPemberianDiet(null,false);
                rawatinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                rawatinap.setLocationRelativeTo(internalFrame1);
                rawatinap.emptTeks(); 
                rawatinap.isCek();
                rawatinap.setNoRm(norawat.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                rawatinap.tampil();
                rawatinap.setAlwaysOnTop(false);
                rawatinap.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnDietActionPerformed

private void DTPCari3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari3ItemStateChanged
   R3.setSelected(true);
   tampil(); 
}//GEN-LAST:event_DTPCari3ItemStateChanged

private void DTPCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_DTPCari3KeyPressed

private void cmbJam1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJam1ItemStateChanged
   R3.setSelected(true);
   tampil();
}//GEN-LAST:event_cmbJam1ItemStateChanged

private void cmbJam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJam1KeyPressed
  
}//GEN-LAST:event_cmbJam1KeyPressed

private void cmbMnt1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMnt1KeyPressed
 
}//GEN-LAST:event_cmbMnt1KeyPressed

private void cmbDtk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtk1KeyPressed
  
}//GEN-LAST:event_cmbDtk1KeyPressed

private void cmbDtk2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtk2KeyPressed
   
}//GEN-LAST:event_cmbDtk2KeyPressed

private void cmbMnt2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMnt2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_cmbMnt2KeyPressed

private void cmbJam2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJam2ItemStateChanged
   R3.setSelected(true);
   tampil();
}//GEN-LAST:event_cmbJam2ItemStateChanged

private void cmbJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJam2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_cmbJam2KeyPressed

private void cmbMnt1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMnt1ItemStateChanged
   R3.setSelected(true);
   tampil();
}//GEN-LAST:event_cmbMnt1ItemStateChanged

private void cmbDtk1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDtk1ItemStateChanged
   R3.setSelected(true);
   tampil();
}//GEN-LAST:event_cmbDtk1ItemStateChanged

private void cmbMnt2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMnt2ItemStateChanged
   R3.setSelected(true);
   tampil();
}//GEN-LAST:event_cmbMnt2ItemStateChanged

private void cmbDtk2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDtk2ItemStateChanged
   R3.setSelected(true);
   tampil();
}//GEN-LAST:event_cmbDtk2ItemStateChanged

private void R1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_R1ItemStateChanged
   tampil();
}//GEN-LAST:event_R1ItemStateChanged

private void R2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_R2ItemStateChanged
   tampil();
}//GEN-LAST:event_R2ItemStateChanged

private void R3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_R3ItemStateChanged
   tampil();
}//GEN-LAST:event_R3ItemStateChanged

private void cmbStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbStatusKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_cmbStatusKeyPressed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        }else{
            billing.periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            billing.periksalab.setLocationRelativeTo(internalFrame1);
            billing.periksalab.emptTeks();
            billing.periksalab.setNoRm(norawat.getText());  
            billing.periksalab.tampiltarif();
            billing.periksalab.tampil();
            billing.periksalab.setAlwaysOnTop(false);
            billing.periksalab.setVisible(true);
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void JamMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMasukKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_JamMasukKeyPressed

private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
   if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        }else{
            DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(norawat.getText(),TNoRM.getText()+", "+TPasien.getText());  
            dlgro.tampil();
            dlgro.tampil2();
            dlgro.setAlwaysOnTop(false);
            dlgro.setVisible(true);
        }
}//GEN-LAST:event_MnOperasiActionPerformed

private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
   Sequel.queryu("delete from operasi where no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0) +"'");
}//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
  Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0) +"'");
}//GEN-LAST:event_MnHapusObatOperasiActionPerformed

private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukMasukActionPerformed
   if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
      }else{
                rujukmasuk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                rujukmasuk.setLocationRelativeTo(internalFrame1);
                rujukmasuk.emptTeks();
                rujukmasuk.isCek();
                rujukmasuk.setNoRm(norawat.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
                rujukmasuk.tampil();
                rujukmasuk.setAlwaysOnTop(false);
                rujukmasuk.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnRujukMasukActionPerformed

    private void btnPindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPindahActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data inap pasien yang mau pindah dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        }if(TOut.getText().trim().length()>0){
                 JOptionPane.showMessageDialog(null,"Maaf, pasien ini sudah pulang pada tanggal "+TOut.getText()+" ...!!!");
                 emptTeks();
                 tbKamIn.requestFocus();
        }else if((TOut.getText().length()==0)&&(TPasien.getText().length()>0)){
            kdkamarpindah.setText("");
            TKdBngsalpindah.setText("");
            TBangsalpindah.setText("");
            TJmlHaripindah.setText("0");
            TTarifpindah.setText("0");
            ttlbiayapindah.setText("0");
                CmbTahunpindah.setSelectedItem(now.substring(0,4));
                CmbBlnpindah.setSelectedItem(now.substring(5,7));
                CmbTglpindah.setSelectedItem(now.substring(8,10));
                cmbJampindah.setSelectedItem(now.substring(11,13));
                cmbMntpindah.setSelectedItem(now.substring(14,16));
                cmbDtkpindah.setSelectedItem(now.substring(17,19));  
                norawat.requestFocus();      
                WindowInput2.setVisible(true);
                lama=Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
                i=2;
                isKmr();
                isjml();
        }
    }//GEN-LAST:event_btnPindahActionPerformed

    private void btnPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPindahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnPindahActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnOut,BtnHapus);
        }
    }//GEN-LAST:event_btnPindahKeyPressed

    private void kdkamarpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkamarpindahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            i=2;
            isKmr();
        }else{
            Valid.pindah(evt,BtnSimpanpindah,CmbTahun);
        }
    }//GEN-LAST:event_kdkamarpindahKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        var.setStatus(true);
        i=2;
        kamar.tampil();
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setAlwaysOnTop(false);
        kamar.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void TBangsalpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBangsalpindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBangsalpindahKeyPressed

    private void BtnCloseInpindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInpindahActionPerformed
        WindowInput2.dispose();
    }//GEN-LAST:event_BtnCloseInpindahActionPerformed

    private void BtnCloseInpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInpindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCloseInpindahKeyPressed

    private void BtnSimpanpindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanpindahActionPerformed
        if(TPasienpindah.getText().trim().equals("")){
            Valid.textKosong(norawatpindah,"pasien");
        }else if(TKdBngsalpindah.getText().trim().equals("")){
            Valid.textKosong(kdkamarpindah,"kamar");
        }else{
            switch (TSttsKamarpindah.getText().trim()) {
                case "ISI":
                    JOptionPane.showMessageDialog(null,"Maaf, Status kamar sudah terisi silahkan pilih kamar kosong..!!!!");
                    kdkamar.requestFocus();
                    break;
                case "KOSONG":
                    if(Rganti1.isSelected()==true){
                        Sequel.menyimpan("kamar_inap","'"+norawatpindah.getText()+"','"+
                                kdkamarpindah.getText()+"','"+
                                diagnosaawal.getText()+"','"+
                                diagnosaakhir.getText()+"','"+
                                CmbTahunpindah.getSelectedItem()+"-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+"','"+
                                cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+"','0000-00-00','00:00:00','"+
                                ttlbiayapindah.getText()+"','-'","No.Rawat");
                        Sequel.mengedit("kamar","kd_kamar='"+kdkamarpindah.getText()+"'","status='ISI'");  
                        Sequel.queryu("delete from kamar_inap where no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString()+
                                "' and kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+
                                "' and tgl_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                "' and jam_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"'");
                        Sequel.mengedit("kamar","kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+"'","status='KOSONG'");  
                    }else if(Rganti2.isSelected()==true){
                        Sequel.queryu("update kamar_inap set kd_kamar='"+kdkamarpindah.getText()+"', ttl_biaya='"+ttlbiayapindah.getText()+
                                "' where no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString()+
                                "' and kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+
                                "' and tgl_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                "' and jam_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"'");                        
                        Sequel.mengedit("kamar","kd_kamar='"+kdkamarpindah.getText()+"'","status='ISI'"); 
                        Sequel.mengedit("kamar","kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+"'","status='KOSONG'");  
                    }else if(Rganti3.isSelected()==true){
                        i=1;
                        kdkamar.setText(Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString()));
                        isKmr();
                        Sequel.cariIsi("select if(to_days('"+CmbTahunpindah.getSelectedItem()+
                                "-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                " "+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+
                                ":"+cmbDtkpindah.getSelectedItem()+"')-to_days('"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                " "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+
                                "')=0,if(time_to_sec('"+CmbTahunpindah.getSelectedItem()+
                                "-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                " "+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+
                                ":"+cmbDtkpindah.getSelectedItem()+"')-time_to_sec('"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                " "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"')>(3600*"+
                                lama+"),1,0),to_days('"+CmbTahunpindah.getSelectedItem()+
                                "-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                " "+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+
                                "')-to_days('"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                " "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"')) as lama",TJmlHari);             
                        isjml();
                        Sequel.mengedit("kamar_inap","no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString()+
                                "' and kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+
                                "' and tgl_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                "' and jam_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"'",
                                "tgl_keluar='"+CmbTahunpindah.getSelectedItem()+"-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                "',jam_keluar='"+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+
                                "',ttl_biaya='"+ttlbiaya.getText()+"',stts_pulang='Pindah Kamar'");
                        System.out.println("kamar_inap | no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString()+
                                "' and kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+
                                "' and tgl_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                "' and jam_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"' | tgl_keluar='"+CmbTahunpindah.getSelectedItem()+"-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                "',jam_keluar='"+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+
                                "',ttl_biaya='"+ttlbiaya.getText()+"',stts_pulang='Pindah Kamar'");
                        Sequel.mengedit("kamar","kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+"'","status='KOSONG'");
                        Sequel.menyimpan("kamar_inap","'"+norawatpindah.getText()+"','"+
                                kdkamarpindah.getText()+"','"+
                                diagnosaawal.getText()+"','"+
                                diagnosaakhir.getText()+"','"+
                                CmbTahunpindah.getSelectedItem()+"-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+"','"+
                                cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+"','0000-00-00','00:00:00','"+
                                ttlbiayapindah.getText()+"','-'","No.Rawat");
                        Sequel.mengedit("kamar","kd_kamar='"+kdkamarpindah.getText()+"'","status='ISI'");                         
                    }else if(Rganti4.isSelected()==true){
                        i=1;
                        kdkamar.setText(Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString()));
                        isKmr();
                        Sequel.cariIsi("select if(to_days('"+CmbTahunpindah.getSelectedItem()+
                                "-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                " "+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+
                                ":"+cmbDtkpindah.getSelectedItem()+"')-to_days('"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                " "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+
                                "')=0,if(time_to_sec('"+CmbTahunpindah.getSelectedItem()+
                                "-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                " "+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+
                                ":"+cmbDtkpindah.getSelectedItem()+"')-time_to_sec('"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                " "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"')>(3600*"+
                                lama+"),1,0),to_days('"+CmbTahunpindah.getSelectedItem()+
                                "-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                " "+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+
                                "')-to_days('"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                " "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"')) as lama",TJmlHari);             
                        DecimalFormat df2 = new DecimalFormat("####");
                        if((! TJmlHari.getText().equals(""))&&(! TTarif.getText().equals(""))){                    
                            double x=Double.parseDouble(TJmlHari.getText().trim());
                            double y=0;
                            if(Double.parseDouble(TTarif.getText().trim())>Double.parseDouble(TTarifpindah.getText().trim())){
                                y=Double.parseDouble(TTarif.getText().trim());
                            }else if(Double.parseDouble(TTarif.getText().trim())<Double.parseDouble(TTarifpindah.getText().trim())){
                                y=Double.parseDouble(TTarifpindah.getText().trim());
                            }
                            ttlbiaya.setText(df2.format(x*y));
                        }
                        Sequel.mengedit("kamar_inap","no_rawat='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString()+
                                "' and kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+
                                "' and tgl_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString()+
                                "' and jam_masuk='"+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString()+"'",
                                "tgl_keluar='"+CmbTahunpindah.getSelectedItem()+"-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+
                                "',jam_keluar='"+cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+
                                "',ttl_biaya='"+ttlbiaya.getText()+"',stts_pulang='Pindah Kamar'");
                        Sequel.mengedit("kamar","kd_kamar='"+Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString())+"'","status='KOSONG'");
                        Sequel.menyimpan("kamar_inap","'"+norawatpindah.getText()+"','"+
                                kdkamarpindah.getText()+"','"+
                                diagnosaawal.getText()+"','"+
                                diagnosaakhir.getText()+"','"+
                                CmbTahunpindah.getSelectedItem()+"-"+CmbBlnpindah.getSelectedItem()+"-"+CmbTglpindah.getSelectedItem()+"','"+
                                cmbJampindah.getSelectedItem()+":"+cmbMntpindah.getSelectedItem()+":"+cmbDtkpindah.getSelectedItem()+"','0000-00-00','00:00:00','"+
                                ttlbiayapindah.getText()+"','-'","No.Rawat");
                        Sequel.mengedit("kamar","kd_kamar='"+kdkamarpindah.getText()+"'","status='ISI'");                         
                    }   
                    tampil();
                    WindowInput2.dispose();
                    break;
            }            
        }
    }//GEN-LAST:event_BtnSimpanpindahActionPerformed

    private void BtnSimpanpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanpindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpanpindahKeyPressed

    private void MnHapusDataSalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDataSalahActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            norawat.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            i = JOptionPane.showConfirmDialog(rootPane,"Yakin data mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Sequel.queryu("delete from kamar_inap where no_rawat='"+norawat.getText()+"' and kd_kamar='"+kdkamar.getText()+"' and tgl_masuk='"+TIn.getText()+"' and jam_masuk='"+JamMasuk.getText()+"'");
                tampil();
            }
        }
        emptTeks();
    }//GEN-LAST:event_MnHapusDataSalahActionPerformed

    private void MnStokObatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStokObatPasienActionPerformed
        if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKamIn.requestFocus();
        }else{
                DlgInputStokPasien dlgrjk=new DlgInputStokPasien(null,false);
                dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.isCek();
                dlgrjk.setNoRm(norawat.getText(),TNoRM.getText()+" "+TPasien.getText()); 
                dlgrjk.tampil();
                dlgrjk.setAlwaysOnTop(false);
                dlgrjk.setVisible(true);
                //this.dispose();
        }
    }//GEN-LAST:event_MnStokObatPasienActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowInput4.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(norawat.getText().trim().equals("")){
            Valid.textKosong(norawat,"No.Rawat");
        }if(kdpenjab.getText().trim().equals("")||nmpenjab.getText().trim().equals("")){
            Valid.textKosong(kdpenjab,"Cara Bayar");
        }else{
            Sequel.mengedit("reg_periksa","no_rawat='"+norawat.getText()+"'"," kd_pj='"+kdpenjab.getText()+"'");
            tampil();
            WindowInput4.dispose();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab,kdpenjab.getText());
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        var.setStatus(true);
        reg.pasien.penjab.emptTeks();
        reg.pasien.penjab.isCek();
        reg.pasien.penjab.tampil("");
        reg.pasien.penjab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        reg.pasien.penjab.setLocationRelativeTo(internalFrame1);
        reg.pasien.penjab.setAlwaysOnTop(false);
        reg.pasien.penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjabActionPerformed
        WindowInput4.setSize(630,80);
        WindowInput4.setLocationRelativeTo(internalFrame1);
        WindowInput4.setAlwaysOnTop(false);
        WindowInput4.setVisible(true);
    }//GEN-LAST:event_MnPenjabActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKamarInap dialog = new DlgKamarInap(new javax.swing.JFrame(), true);
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
    private widget.TextBox BangsalCari;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseInpindah;
    private widget.Button BtnHapus;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnOut;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpanpindah;
    private widget.ComboBox CmbBln;
    private widget.ComboBox CmbBlnpindah;
    private widget.ComboBox CmbTahun;
    private widget.ComboBox CmbTahunpindah;
    private widget.ComboBox CmbTgl;
    private widget.ComboBox CmbTglpindah;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.TextBox JamMasuk;
    private widget.Label LCount;
    private widget.Label LblStts;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnDiet;
    private javax.swing.JMenuItem MnHapusDataSalah;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnRawanInap;
    private javax.swing.JMenuItem MnResepObat;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JMenuItem MnStokObatPasien;
    private javax.swing.JPanel PanelCariUtama;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton Rganti1;
    private widget.RadioButton Rganti2;
    private widget.RadioButton Rganti3;
    private widget.RadioButton Rganti4;
    private widget.ScrollPane Scroll;
    private widget.TextBox TBangsal;
    private widget.TextBox TBangsalpindah;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TJmlHari;
    private widget.TextBox TJmlHaripindah;
    private widget.TextBox TKdBngsal;
    private widget.TextBox TKdBngsalpindah;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRMpindah;
    private widget.TextBox TOut;
    private widget.TextBox TPasien;
    private widget.TextBox TPasienpindah;
    private widget.TextBox TSttsKamar;
    private widget.TextBox TSttsKamarpindah;
    private widget.TextBox TTarif;
    private widget.TextBox TTarifpindah;
    private javax.swing.JDialog WindowInput;
    private javax.swing.JDialog WindowInput2;
    private javax.swing.JDialog WindowInput4;
    private widget.Button btnPindah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbDtk1;
    private widget.ComboBox cmbDtk2;
    private widget.ComboBox cmbDtkpindah;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJam1;
    private widget.ComboBox cmbJam2;
    private widget.ComboBox cmbJampindah;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMnt1;
    private widget.ComboBox cmbMnt2;
    private widget.ComboBox cmbMntpindah;
    private widget.ComboBox cmbStatus;
    private widget.TextBox diagnosaakhir;
    private widget.TextBox diagnosaawal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel35;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdkamar;
    private widget.TextBox kdkamarpindah;
    private widget.TextBox kdpenjab;
    private widget.TextBox nmpenjab;
    private widget.TextBox norawat;
    private widget.TextBox norawatpindah;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.Table tbKamIn;
    private widget.TextBox ttlbiaya;
    private widget.TextBox ttlbiayapindah;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        if(R1.isSelected()==true){
            kmr=" kamar_inap.tgl_keluar='0000:00:00' ";
            if(!BangsalCari.getText().equals("")){
                kmr=" kamar_inap.tgl_keluar='0000:00:00' and bangsal.nm_bangsal='"+BangsalCari.getText()+"' ";
            }
        }else if(R2.isSelected()==true){
            kmr=" kamar_inap.tgl_masuk between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ";
            if(!BangsalCari.getText().equals("")){
                kmr=" kamar_inap.tgl_masuk between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and bangsal.nm_bangsal='"+BangsalCari.getText()+"' ";
            }
        }else if(R3.isSelected()==true){
            kmr=" kamar_inap.tgl_keluar='"+DTPCari3.getSelectedItem()+"' and kamar_inap.jam_keluar between '"+
                  cmbJam1.getSelectedItem()+":"+cmbMnt1.getSelectedItem()+":"+cmbDtk1.getSelectedItem()+"' and '"+
                  cmbJam2.getSelectedItem()+":"+cmbMnt2.getSelectedItem()+":"+cmbDtk2.getSelectedItem()+"' ";
            if(!BangsalCari.getText().equals("")){
                kmr=" kamar_inap.tgl_keluar='"+DTPCari3.getSelectedItem()+"' and kamar_inap.jam_keluar between '"+
                  cmbJam1.getSelectedItem()+":"+cmbMnt1.getSelectedItem()+":"+cmbDtk1.getSelectedItem()+"' and '"+
                  cmbJam2.getSelectedItem()+":"+cmbMnt2.getSelectedItem()+":"+cmbDtk2.getSelectedItem()+"' and bangsal.nm_bangsal='"+BangsalCari.getText()+"' ";
            }
        }
        
        key=kmr+" ";
        if(!TCari.getText().equals("")){
            key= kmr+"and kamar_inap.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.kd_kamar like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.diagnosa_awal like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.diagnosa_akhir like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar.trf_kamar like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.tgl_masuk like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.stts_pulang like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.tgl_keluar like '%"+TCari.getText().trim()+"%' or "+
                   kmr+"and kamar_inap.ttl_biaya like '%"+TCari.getText().trim()+"%' ";
        }
        
        Valid.tabelKosong(tabMode);
        try{
            rs=koneksi.prepareStatement(
                   "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab),"+
                   "penjab.png_jawab,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal),kamar.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir," +
                   "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,kamar_inap.tgl_keluar,kamar_inap.jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, " +
                   "(IFNULL(to_days(concat(tgl_keluar,' ',jam_keluar))-to_days(concat(tgl_masuk,' ',jam_masuk)),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))) as lama,dokter.nm_dokter "+
                   "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join kelurahan inner join kecamatan inner join kabupaten inner join dokter inner join penjab " +
                   "on kamar_inap.no_rawat=reg_periksa.no_rawat " +
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                   "and reg_periksa.kd_dokter=dokter.kd_dokter " +
                   "and reg_periksa.kd_pj=penjab.kd_pj " +
                   "and kamar_inap.kd_kamar=kamar.kd_kamar " +
                   "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab " +
                   "where  "+key+" order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk").executeQuery();
            while(rs.next()){
                tabMode.addRow(new String[]{rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               Valid.SetAngka(rs.getDouble(7)),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10),
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString(13),
                               Valid.SetAngka(rs.getDouble(14)),
                               rs.getString(15),
                               rs.getString(16),
                               rs.getString(17)});
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {       
        norawat.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        kdkamar.setText("");
        TKdBngsal.setText("");
        TBangsal.setText("");
        diagnosaawal.setText("");
        diagnosaakhir.setText("");

        CmbTahun.setSelectedItem(now.substring(0,4));
        CmbBln.setSelectedItem(now.substring(5,7));
        CmbTgl.setSelectedItem(now.substring(8,10));        
        cmbJam.setSelectedItem(now.substring(11,13));
        cmbMnt.setSelectedItem(now.substring(14,16));
        cmbDtk.setSelectedItem(now.substring(17,19));  

        TOut.setText("");
        TIn.setText("");
        JamMasuk.setText("");
        TTarif.setText("0");
        TJmlHari.setText("0");
        ttlbiaya.setText("0");
        norawat.requestFocus();
    }

    private void getData() {
        TOut.setText("");
        TIn.setText("");
        JamMasuk.setText("");
        if(tbKamIn.getSelectedRow()!= -1){
            norawat.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,norawat.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
            norawatpindah.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());
            TNoRMpindah.setText(TNoRM.getText());
            TPasienpindah.setText(TPasien.getText());            
            Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",kdkamar,tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString());
            diagnosaawal.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),7).toString());
            diagnosaakhir.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),8).toString());
            TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString());            
            JamMasuk.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString());
            TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),11).toString());
            ttlbiaya.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),13).toString());
            cmbStatus.setSelectedItem(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),14).toString());
        }
    }

    
    private void isKmr() {  
        if(i==1){
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ",TKdBngsal,kdkamar.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ",TBangsal,TKdBngsal.getText());
            Sequel.cariIsi("select status from kamar where kd_kamar=? ",TSttsKamar,kdkamar.getText());
            Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ",TTarif,kdkamar.getText());  
        }else{
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ",TKdBngsalpindah,kdkamarpindah.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ",TBangsalpindah,TKdBngsalpindah.getText());
            Sequel.cariIsi("select status from kamar where kd_kamar=? ",TSttsKamarpindah,kdkamarpindah.getText());
            Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ",TTarifpindah,kdkamarpindah.getText());  
        }      
        isjml();
    }

    private void isjml() {       

        DecimalFormat df2 = new DecimalFormat("####");

        if((! TJmlHari.getText().equals(""))&&(! TTarif.getText().equals(""))){           
            
            double x=Double.parseDouble(TJmlHari.getText().trim());
            double y=Double.parseDouble(TTarif.getText().trim());
            ttlbiaya.setText(df2.format(x*y));
        }
        
        if((! TJmlHaripindah.getText().equals(""))&&(! TTarifpindah.getText().equals(""))){           
            
            double x=Double.parseDouble(TJmlHaripindah.getText().trim());
            double y=Double.parseDouble(TTarifpindah.getText().trim());
            ttlbiayapindah.setText(df2.format(x*y));
        }
    }

    public void setNoRm(String norwt) {
        norawat.setText(norwt);
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,norawat.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        R1.setSelected(true);        
        TCari.setText(norwt);
        try {            
            ps.setString(1,norawat.getText());
            rs=ps.executeQuery();
            if(rs.next()){
                norawat.setEditable(false);
                norawat.setText(rs.getString(1));
                kdkamar.setText(rs.getString(2));
                isKmr();
                diagnosaawal.setText(rs.getString(3));
                diagnosaakhir.setText(rs.getString(4));
                TIn.setText(rs.getString(5));
                JamMasuk.setText(rs.getString(6));
                TOut.setText(rs.getString(7));
                ttlbiaya.setText(rs.getString(8));
                
                kdkamar.setEditable(false);
                diagnosaawal.setEditable(false);                
                diagnosaakhir.setVisible(true);
                jLabel23.setVisible(true);
                cmbStatus.setVisible(true);
                jLabel26.setVisible(true);
                diagnosaakhir.setText("");
                LblStts.setText("Pulang/Check Out");
                isjml();
                BtnSeek.setEnabled(false);
                BtnSeek1.setEnabled(false);
                CmbTahun.setSelectedItem(now.substring(0,4));
                CmbBln.setSelectedItem(now.substring(5,7));
                CmbTgl.setSelectedItem(now.substring(8,10));
            }else{
                norawat.setEditable(true);
                kdkamar.setEditable(true);
                diagnosaawal.setEditable(true);
                diagnosaakhir.setVisible(false);
                TIn.setText("");
                JamMasuk.setText("");
                TOut.setText("");
                ttlbiaya.setText("0");
                jLabel23.setVisible(false);                
                cmbStatus.setVisible(false);
                jLabel26.setVisible(false);
                diagnosaakhir.setText("-");
                LblStts.setText("Masuk/Check In");
                BtnSeek.setEnabled(true);
                BtnSeek1.setEnabled(true);   
                CmbTahun.setSelectedItem(now.substring(0,4));
                CmbBln.setSelectedItem(now.substring(5,7));
                CmbTgl.setSelectedItem(now.substring(8,10));          
            }
            CmbTahunItemStateChanged(null);   
        } catch (SQLException e) {
            System.out.println(e);
        }       
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getkamin());
        BtnHapus.setEnabled(var.getkamin());
        BtnPrint.setEnabled(var.getkamin());
        
        MnBilling.setEnabled(var.getbilling_ranap());
        MnPemberianObat.setEnabled(var.getberiobat());
        MnRawanInap.setEnabled(var.getranap());
        MnResepObat.setEnabled(var.getresep());
        MnDiet.setEnabled(var.getdiet_pasien());
        MnPeriksaLab.setEnabled(var.getperiksa_lab());
        MnRujuk.setEnabled(var.getrujuk());
        MnRujukMasuk.setEnabled(var.getrujuk());
        MnStokObatPasien.setEnabled(var.getstockopname());
    }
    
    

}
