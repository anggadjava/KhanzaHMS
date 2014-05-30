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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class DlgTagihanOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,psobat;
    private ResultSet rs;
    private DlgCariPetugas petugas=new DlgCariPetugas( null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal*/
    public DlgTagihanOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Pilih",
                      "Kode Paket",
                      "Nama Operasi",
                      "Operator 1",
                      "Operator 2",
                      "Operator 3",
                      "Asisten Operator 1",
                      "Asisten Operator 2",
                      "Asisten Operator 3",
                      "Dokter Anak",
                      "Perawat Resusitas",
                      "Dokter Anestesi",
                      "Asisten Anestesi",
                      "Bidan",
                      "Perawat Luar",
                      "Sewa OK",
                      "Alat",
                      "Sewa VK",
                      "Bagian RS",
                      "Omloop",
                      "Total"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==20)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class   
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbtindakan.setModel(tabMode);

        tbtindakan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbtindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbtindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(150);
            }
        }
        tbtindakan.setDefaultRenderer(Object.class, new WarnaTable());
        
        //tagihan obat
        Object[] row2={"Jumlah",
        "Kode",
        "Nama",
        "Satuan",
        "Harga",
        "Total"};
        
        tabMode2=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObat.setModel(tabMode2);
        //tampil();

        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }
        }

        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TKdPny.setDocument(new batasInput((byte)10).getKata(TKdPny)); 
        jenis.setDocument(new batasInput((byte)8).getKata(jenis));
        kdoperator1.setDocument(new batasInput((byte)20).getKata(kdoperator1));
        kdoperator2.setDocument(new batasInput((byte)20).getKata(kdoperator2));
        kdoperator3.setDocument(new batasInput((byte)20).getKata(kdoperator3));
        kdasistoperator1.setDocument(new batasInput((byte)20).getKata(kdasistoperator1));
        kdasistoperator2.setDocument(new batasInput((byte)20).getKata(kdasistoperator2));
        kdasistoperator3.setDocument(new batasInput((byte)20).getKata(kdasistoperator3));
        kdanestesi.setDocument(new batasInput((byte)20).getKata(kdanestesi));
        kdasistanestesi.setDocument(new batasInput((byte)20).getKata(kdasistanestesi));
        kddranak.setDocument(new batasInput((byte)20).getKata(kddranak));
        kdprwresust.setDocument(new batasInput((byte)20).getKata(kdprwresust));
        kdbidan.setDocument(new batasInput((byte)20).getKata(kdbidan));
        kdprwluar.setDocument(new batasInput((byte)20).getKata(kdprwluar));
        
        TCariPaket.setDocument(new batasInput((byte)100).getKata(TCari)); 
        TCari.setDocument(new batasInput((byte)100).getKata(TCari)); 
        TCariPaket.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

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
        
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                tampil2();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tampil2();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tampil2();
            }

        });
        
        dlgpnykt.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPny.setText(dlgpnykt.getTextField().getText());     
                 Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
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
        
       
        dokter.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                if(pilihan==1){
                    kdoperator1.setText(dokter.getTextField().getText());
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator1,kdoperator1.getText());
                }else if(pilihan==2){
                    kdoperator2.setText(dokter.getTextField().getText());
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator2,kdoperator2.getText());
                }else if(pilihan==3){
                    kdoperator3.setText(dokter.getTextField().getText());
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator3,kdoperator3.getText());
                }else if(pilihan==4){
                    kdanestesi.setText(dokter.getTextField().getText());
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmanestesi,kdanestesi.getText());
                }else if(pilihan==5){
                    kddranak.setText(dokter.getTextField().getText());
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,kddranak.getText());
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
        
        petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                if(pilihan==1){
                    kdasistoperator1.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator1,kdasistoperator1.getText());
                }else if(pilihan==2){
                    kdasistoperator2.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator2,kdasistoperator2.getText());
                }else if(pilihan==3){
                    kdasistoperator3.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator3,kdasistoperator3.getText());
                }else if(pilihan==4){
                    kdasistanestesi.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmasistanestesi,kdasistanestesi.getText());
                }else if(pilihan==5){
                    kdprwresust.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmprwresust,kdprwresust.getText());
                }else if(pilihan==6){
                    kdprwluar.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmprwluar,kdprwluar.getText());
                }else if(pilihan==7){
                    kdbidan.setText(petugas.getTextField().getText());
                    Sequel.cariIsi("select nama from petugas where nip=?",nmbidan,kdbidan.getText());
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
        
        
        TCari.requestFocus();
        ChkInput.setSelected(false);
        isForm();
        try{
            pstindakan=koneksi.prepareStatement("select kode_paket, nm_perawatan, operator1, operator2, operator3, asisten_operator1, asisten_operator2,"+
                  "asisten_operator3, dokter_anak, perawaat_resusitas, dokter_anestesi, asisten_anestesi, bidan, "+
                  "perawat_luar, sewa_ok, alat,sewa_vk,bagian_rs,omloop,(operator1+operator2+operator3+asisten_operator1+asisten_operator2+"+
                  "asisten_operator3+dokter_anak+perawaat_resusitas+dokter_anestesi+asisten_anestesi+bidan+"+
                  "perawat_luar+sewa_ok+alat+sewa_vk+bagian_rs+omloop) as total "+
                   "from paket_operasi where kode_paket like ? or "+
                   "nm_perawatan like ? order by kode_paket ");
            psobat=koneksi.prepareStatement("select obatbhp_ok.kd_obat, obatbhp_ok.nm_obat, kodesatuan.satuan, "+
                   "obatbhp_ok.hargasatuan from obatbhp_ok inner join kodesatuan "+
                   "on obatbhp_ok.kode_sat=kodesatuan.kode_sat "+
                   "where obatbhp_ok.kd_obat like ? or "+
                   "obatbhp_ok.nm_obat like ? or "+
                   "kodesatuan.satuan like ? "+
                   "order by obatbhp_ok.kd_obat");
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    private DlgCariPenyakit dlgpnykt=new DlgCariPenyakit(null,false);
    private double y=0,biayatindakan=0,biayaobat=0;
    private int jml=0,pilihan=0,i=0,index=0;
    private boolean[] pilih; 
    private String[] kode_paket, nm_perawatan,kd_obat,nm_obat, satuan;
    private double[] operator1, operator2, operator3, asisten_operator1, asisten_operator2,
                  asisten_operator3, dokter_anak, perawaat_resusitas, dokter_anestesi, asisten_anestesi, bidan, 
                  perawat_luar, sewa_ok, alat,sewa_vk,bagian_rs,omloop,ttltindakan,jmlobat,hargasatuan,ttlobat;

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
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        TCariPaket = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        BtnTambah1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbtindakan = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        LTotal = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label14 = new widget.Label();
        kdoperator1 = new widget.TextBox();
        nmoperator1 = new widget.TextBox();
        BtnOperator1 = new widget.Button();
        label11 = new widget.Label();
        tgl = new widget.Tanggal();
        jLabel12 = new widget.Label();
        TKdPny = new widget.TextBox();
        TNmPny = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel4 = new widget.Label();
        jenis = new widget.TextBox();
        label17 = new widget.Label();
        kdasistoperator1 = new widget.TextBox();
        nmasistoperator1 = new widget.TextBox();
        BtnPtg3 = new widget.Button();
        label19 = new widget.Label();
        kdoperator2 = new widget.TextBox();
        nmoperator2 = new widget.TextBox();
        BtnOperator2 = new widget.Button();
        label20 = new widget.Label();
        kdoperator3 = new widget.TextBox();
        nmoperator3 = new widget.TextBox();
        BtnOperator3 = new widget.Button();
        label21 = new widget.Label();
        kdanestesi = new widget.TextBox();
        nmanestesi = new widget.TextBox();
        BtnOperator4 = new widget.Button();
        label22 = new widget.Label();
        kddranak = new widget.TextBox();
        nmdranak = new widget.TextBox();
        BtnOperator5 = new widget.Button();
        BtnPtg4 = new widget.Button();
        nmasistoperator2 = new widget.TextBox();
        kdasistoperator2 = new widget.TextBox();
        label18 = new widget.Label();
        BtnPtg5 = new widget.Button();
        nmasistoperator3 = new widget.TextBox();
        kdasistoperator3 = new widget.TextBox();
        label23 = new widget.Label();
        BtnPtg6 = new widget.Button();
        nmprwresust = new widget.TextBox();
        kdprwresust = new widget.TextBox();
        label24 = new widget.Label();
        label26 = new widget.Label();
        kdasistanestesi = new widget.TextBox();
        nmasistanestesi = new widget.TextBox();
        BtnPtg8 = new widget.Button();
        label27 = new widget.Label();
        kdbidan = new widget.TextBox();
        nmbidan = new widget.TextBox();
        BtnOperator6 = new widget.Button();
        label28 = new widget.Label();
        kdprwluar = new widget.TextBox();
        nmprwluar = new widget.TextBox();
        BtnPtg9 = new widget.Button();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tagihan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), ".: Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi5.add(label10);

        TCariPaket.setToolTipText("Alt+C");
        TCariPaket.setName("TCariPaket"); // NOI18N
        TCariPaket.setPreferredSize(new java.awt.Dimension(120, 23));
        TCariPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPaketKeyPressed(evt);
            }
        });
        panelisi5.add(TCariPaket);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('1');
        BtnCari2.setToolTipText("Alt+1");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelisi5.add(BtnCari2);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi5.add(BtnAll1);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        panelisi5.add(BtnTambah1);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbtindakan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbtindakan.setName("tbtindakan"); // NOI18N
        tbtindakan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtindakanMouseClicked(evt);
            }
        });
        tbtindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbtindakanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbtindakan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), ".: Obat & BHP ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 102));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setBorder(null);
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(120, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

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
        panelisi4.add(BtnCari1);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi4.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi4.add(BtnTambah);

        jPanel2.add(panelisi4, java.awt.BorderLayout.PAGE_END);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        jPanel2.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LTotal.setText("Total Biaya :");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(500, 30));
        panelisi1.add(LTotal);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 274));
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
        FormInput.setPreferredSize(new java.awt.Dimension(89, 254));
        FormInput.setLayout(null);

        label14.setText("Operator 1 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 82, 23);

        kdoperator1.setName("kdoperator1"); // NOI18N
        kdoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator1);
        kdoperator1.setBounds(84, 70, 100, 23);

        nmoperator1.setEditable(false);
        nmoperator1.setName("nmoperator1"); // NOI18N
        nmoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator1);
        nmoperator1.setBounds(185, 70, 190, 23);

        BtnOperator1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator1.setMnemonic('2');
        BtnOperator1.setToolTipText("Alt+2");
        BtnOperator1.setName("BtnOperator1"); // NOI18N
        BtnOperator1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator1);
        BtnOperator1.setBounds(376, 70, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(406, 10, 102, 23);

        tgl.setEditable(false);
        tgl.setDisplayFormat("yyyy-MM-dd");
        tgl.setName("tgl"); // NOI18N
        tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglKeyPressed(evt);
            }
        });
        FormInput.add(tgl);
        tgl.setBounds(510, 10, 100, 23);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 82, 23);

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);
        TKdPny.setBounds(84, 40, 100, 23);

        TNmPny.setEditable(false);
        TNmPny.setName("TNmPny"); // NOI18N
        TNmPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmPnyKeyPressed(evt);
            }
        });
        FormInput.add(TNmPny);
        TNmPny.setBounds(185, 40, 190, 23);

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
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(376, 40, 28, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 82, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 100, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(185, 10, 219, 23);

        jLabel4.setText("Jenis Anasthesi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(640, 10, 100, 23);

        jenis.setHighlighter(null);
        jenis.setName("jenis"); // NOI18N
        jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jenisKeyPressed(evt);
            }
        });
        FormInput.add(jenis);
        jenis.setBounds(742, 10, 86, 23);

        label17.setText("Ast. Operator 1 :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(406, 40, 102, 23);

        kdasistoperator1.setName("kdasistoperator1"); // NOI18N
        kdasistoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator1);
        kdasistoperator1.setBounds(510, 40, 100, 23);

        nmasistoperator1.setEditable(false);
        nmasistoperator1.setName("nmasistoperator1"); // NOI18N
        nmasistoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator1);
        nmasistoperator1.setBounds(611, 40, 190, 23);

        BtnPtg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg3.setMnemonic('2');
        BtnPtg3.setToolTipText("Alt+2");
        BtnPtg3.setName("BtnPtg3"); // NOI18N
        BtnPtg3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtg3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPtg3);
        BtnPtg3.setBounds(802, 40, 28, 23);

        label19.setText("Operator 2 :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(0, 100, 82, 23);

        kdoperator2.setName("kdoperator2"); // NOI18N
        kdoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator2);
        kdoperator2.setBounds(84, 100, 100, 23);

        nmoperator2.setEditable(false);
        nmoperator2.setName("nmoperator2"); // NOI18N
        nmoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator2);
        nmoperator2.setBounds(185, 100, 190, 23);

        BtnOperator2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator2.setMnemonic('2');
        BtnOperator2.setToolTipText("Alt+2");
        BtnOperator2.setName("BtnOperator2"); // NOI18N
        BtnOperator2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator2);
        BtnOperator2.setBounds(376, 100, 28, 23);

        label20.setText("Instrumen :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(0, 130, 82, 23);

        kdoperator3.setName("kdoperator3"); // NOI18N
        kdoperator3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator3KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator3);
        kdoperator3.setBounds(84, 130, 100, 23);

        nmoperator3.setEditable(false);
        nmoperator3.setName("nmoperator3"); // NOI18N
        nmoperator3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator3);
        nmoperator3.setBounds(185, 130, 190, 23);

        BtnOperator3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator3.setMnemonic('2');
        BtnOperator3.setToolTipText("Alt+2");
        BtnOperator3.setName("BtnOperator3"); // NOI18N
        BtnOperator3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator3);
        BtnOperator3.setBounds(376, 130, 28, 23);

        label21.setText("dr Anestesi :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(0, 160, 82, 23);

        kdanestesi.setName("kdanestesi"); // NOI18N
        kdanestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        kdanestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdanestesiKeyPressed(evt);
            }
        });
        FormInput.add(kdanestesi);
        kdanestesi.setBounds(84, 160, 100, 23);

        nmanestesi.setEditable(false);
        nmanestesi.setName("nmanestesi"); // NOI18N
        nmanestesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmanestesi);
        nmanestesi.setBounds(185, 160, 190, 23);

        BtnOperator4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator4.setMnemonic('2');
        BtnOperator4.setToolTipText("Alt+2");
        BtnOperator4.setName("BtnOperator4"); // NOI18N
        BtnOperator4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator4);
        BtnOperator4.setBounds(376, 160, 28, 23);

        label22.setText("dr Anak :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(0, 190, 82, 23);

        kddranak.setName("kddranak"); // NOI18N
        kddranak.setPreferredSize(new java.awt.Dimension(80, 23));
        kddranak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddranakKeyPressed(evt);
            }
        });
        FormInput.add(kddranak);
        kddranak.setBounds(84, 190, 100, 23);

        nmdranak.setEditable(false);
        nmdranak.setName("nmdranak"); // NOI18N
        nmdranak.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmdranak);
        nmdranak.setBounds(185, 190, 190, 23);

        BtnOperator5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator5.setMnemonic('2');
        BtnOperator5.setToolTipText("Alt+2");
        BtnOperator5.setName("BtnOperator5"); // NOI18N
        BtnOperator5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator5);
        BtnOperator5.setBounds(376, 190, 28, 23);

        BtnPtg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg4.setMnemonic('2');
        BtnPtg4.setToolTipText("Alt+2");
        BtnPtg4.setName("BtnPtg4"); // NOI18N
        BtnPtg4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtg4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPtg4);
        BtnPtg4.setBounds(802, 70, 28, 23);

        nmasistoperator2.setEditable(false);
        nmasistoperator2.setName("nmasistoperator2"); // NOI18N
        nmasistoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator2);
        nmasistoperator2.setBounds(611, 70, 190, 23);

        kdasistoperator2.setName("kdasistoperator2"); // NOI18N
        kdasistoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator2);
        kdasistoperator2.setBounds(510, 70, 100, 23);

        label18.setText("Ast. Operator 2 :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(406, 70, 102, 23);

        BtnPtg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg5.setMnemonic('2');
        BtnPtg5.setToolTipText("Alt+2");
        BtnPtg5.setName("BtnPtg5"); // NOI18N
        BtnPtg5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtg5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPtg5);
        BtnPtg5.setBounds(802, 100, 28, 23);

        nmasistoperator3.setEditable(false);
        nmasistoperator3.setName("nmasistoperator3"); // NOI18N
        nmasistoperator3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator3);
        nmasistoperator3.setBounds(611, 100, 190, 23);

        kdasistoperator3.setName("kdasistoperator3"); // NOI18N
        kdasistoperator3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator3KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator3);
        kdasistoperator3.setBounds(510, 100, 100, 23);

        label23.setText("Ast. Operator 3 :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(406, 100, 102, 23);

        BtnPtg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg6.setMnemonic('2');
        BtnPtg6.setToolTipText("Alt+2");
        BtnPtg6.setName("BtnPtg6"); // NOI18N
        BtnPtg6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtg6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPtg6);
        BtnPtg6.setBounds(802, 160, 28, 23);

        nmprwresust.setEditable(false);
        nmprwresust.setName("nmprwresust"); // NOI18N
        nmprwresust.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmprwresust);
        nmprwresust.setBounds(611, 160, 190, 23);

        kdprwresust.setName("kdprwresust"); // NOI18N
        kdprwresust.setPreferredSize(new java.awt.Dimension(80, 23));
        kdprwresust.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdprwresustKeyPressed(evt);
            }
        });
        FormInput.add(kdprwresust);
        kdprwresust.setBounds(510, 160, 100, 23);

        label24.setText("Prw.Resusitasi :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(406, 160, 102, 23);

        label26.setText("Ast. Anestesi :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(406, 130, 102, 23);

        kdasistanestesi.setName("kdasistanestesi"); // NOI18N
        kdasistanestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistanestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistanestesiKeyPressed(evt);
            }
        });
        FormInput.add(kdasistanestesi);
        kdasistanestesi.setBounds(510, 130, 100, 23);

        nmasistanestesi.setEditable(false);
        nmasistanestesi.setName("nmasistanestesi"); // NOI18N
        nmasistanestesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistanestesi);
        nmasistanestesi.setBounds(611, 130, 190, 23);

        BtnPtg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg8.setMnemonic('2');
        BtnPtg8.setToolTipText("Alt+2");
        BtnPtg8.setName("BtnPtg8"); // NOI18N
        BtnPtg8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtg8ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPtg8);
        BtnPtg8.setBounds(802, 130, 28, 23);

        label27.setText("Bidan :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(0, 220, 82, 23);

        kdbidan.setName("kdbidan"); // NOI18N
        kdbidan.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidanKeyPressed(evt);
            }
        });
        FormInput.add(kdbidan);
        kdbidan.setBounds(84, 220, 100, 23);

        nmbidan.setEditable(false);
        nmbidan.setName("nmbidan"); // NOI18N
        nmbidan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan);
        nmbidan.setBounds(185, 220, 190, 23);

        BtnOperator6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator6.setMnemonic('2');
        BtnOperator6.setToolTipText("Alt+2");
        BtnOperator6.setName("BtnOperator6"); // NOI18N
        BtnOperator6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator6);
        BtnOperator6.setBounds(376, 220, 28, 23);

        label28.setText("Perawat Luar :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(406, 190, 102, 23);

        kdprwluar.setName("kdprwluar"); // NOI18N
        kdprwluar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdprwluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdprwluarKeyPressed(evt);
            }
        });
        FormInput.add(kdprwluar);
        kdprwluar.setBounds(510, 190, 100, 23);

        nmprwluar.setEditable(false);
        nmprwluar.setName("nmprwluar"); // NOI18N
        nmprwluar.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmprwluar);
        nmprwluar.setBounds(611, 190, 190, 23);

        BtnPtg9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg9.setMnemonic('2');
        BtnPtg9.setToolTipText("Alt+2");
        BtnPtg9.setName("BtnPtg9"); // NOI18N
        BtnPtg9.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtg9ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPtg9);
        BtnPtg9.setBounds(802, 190, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariTagihanOperasi form=new DlgCariTagihanOperasi(null,false);
        //form.emptTeks();        
        form.tampil();
        form.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,TCariPaket,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"Penyakit");
        }else if(jenis.getText().trim().equals("")){
            Valid.textKosong(jenis,"Jenis");
        }else if(kdoperator1.getText().trim().equals("")||nmoperator1.getText().trim().equals("")){
            Valid.textKosong(kdoperator1,"Operator 1");
        }else if(kdoperator2.getText().trim().equals("")||nmoperator2.getText().trim().equals("")){
            Valid.textKosong(kdoperator2,"Operator 2");
        }else if(kdoperator3.getText().trim().equals("")||nmoperator3.getText().trim().equals("")){
            Valid.textKosong(kdoperator3,"Operator 3");
        }else if(kdanestesi.getText().trim().equals("")||nmanestesi.getText().trim().equals("")){
            Valid.textKosong(kdanestesi,"dr Anestesi");
        }else if(kddranak.getText().trim().equals("")||nmdranak.getText().trim().equals("")){
            Valid.textKosong(kddranak,"dr Anak");
        }else if(kdbidan.getText().trim().equals("")||nmbidan.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan");
        }else if(kdasistoperator1.getText().trim().equals("")||nmasistoperator1.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator1,"Asisten Operator 1");
        }else if(kdasistoperator2.getText().trim().equals("")||nmasistoperator2.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator2,"Asisten Operator 2");
        }else if(kdasistoperator3.getText().trim().equals("")||nmasistoperator3.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator3,"Asisten Operator 3");
        }else if(kdasistanestesi.getText().trim().equals("")||nmasistanestesi.getText().trim().equals("")){
            Valid.textKosong(kdasistanestesi,"Asisten Anastesi");
        }else if(kdprwresust.getText().trim().equals("")||nmprwresust.getText().trim().equals("")){
            Valid.textKosong(kdprwresust,"Perawat Resusitas");
        }else if(kdprwluar.getText().trim().equals("")||nmprwluar.getText().trim().equals("")){
            Valid.textKosong(kdprwluar,"Perawat Luar");
        }else if((tabMode.getRowCount()==0)||(tabMode2.getRowCount()==0)){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            for(i=0;i<tbtindakan.getRowCount();i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                      Sequel.menyimpan("operasi","'"+TNoRw.getText()+"','"+TKdPny.getText()+"','"+tgl.getSelectedItem()
                              +"','"+jenis.getText()+"','"+kdoperator1.getText()+"','"+kdoperator2.getText()+"','"+kdoperator3.getText()
                              +"','"+kdasistoperator1.getText()+"','"+kdasistoperator2.getText()+"','"+kdasistoperator3.getText()
                              +"','"+kddranak.getText()+"','"+kdprwresust.getText()+"','"+kdanestesi.getText()+"','"+kdasistanestesi.getText()
                              +"','"+kdbidan.getText()+"','"+kdprwluar.getText()
                              +"','"+tbtindakan.getValueAt(i,1).toString()
                              +"','"+tbtindakan.getValueAt(i,3).toString()
                              +"','"+tbtindakan.getValueAt(i,4).toString()
                              +"','"+tbtindakan.getValueAt(i,5).toString() 
                              +"','"+tbtindakan.getValueAt(i,6).toString()
                              +"','"+tbtindakan.getValueAt(i,7).toString()
                              +"','"+tbtindakan.getValueAt(i,8).toString() 
                              +"','"+tbtindakan.getValueAt(i,9).toString() 
                              +"','"+tbtindakan.getValueAt(i,10).toString() 
                              +"','"+tbtindakan.getValueAt(i,11).toString() 
                              +"','"+tbtindakan.getValueAt(i,12).toString()
                              +"','"+tbtindakan.getValueAt(i,13).toString() 
                              +"','"+tbtindakan.getValueAt(i,14).toString()
                              +"','"+tbtindakan.getValueAt(i,16).toString() 
                              +"','"+tbtindakan.getValueAt(i,15).toString()
                              +"','"+tbtindakan.getValueAt(i,17).toString()
                              +"','"+tbtindakan.getValueAt(i,18).toString()
                              +"','"+tbtindakan.getValueAt(i,19).toString()+"'","data");
                }      
            }
            for(int r=0;r<tbObat.getRowCount();r++){ 
                if(Valid.SetAngka(tbObat.getValueAt(r,0).toString())>0){
                      Sequel.menyimpan("beri_obat_operasi","'"+TNoRw.getText()+"','"+tgl.getSelectedItem()+
                                "','"+tbObat.getValueAt(r,1).toString()+"','"+tbObat.getValueAt(r,4).toString()+
                                "','"+tbObat.getValueAt(r,0).toString()+"'","data");                      
                }
            } 
            for(int r=0;r<tbtindakan.getRowCount();r++){ 
               tbtindakan.setValueAt(false,r,0);
            }
            tampil();
            for(int r=0;r<tbObat.getRowCount();r++){ 
               tbObat.setValueAt("",r,0);
            }
            tampil2();
            LTotal.setText("Total Biaya : 0");
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,kdprwluar,BtnKeluar);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void kdoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator1,kdoperator1.getText());
        }else{
            Valid.pindah(evt,TKdPny,kdoperator2);
        }
}//GEN-LAST:event_kdoperator1KeyPressed

private void BtnOperator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator1ActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.tampil("");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator1ActionPerformed

private void tglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglKeyPressed
        Valid.pindah(evt,kdasistoperator1,jenis);
}//GEN-LAST:event_tglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
        }else{            
            Valid.pindah(evt,TNoRw,kdoperator1);
        }
}//GEN-LAST:event_TKdPnyKeyPressed

private void TNmPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmPnyKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TNmPnyKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
       dlgpnykt.emptTeks();
        dlgpnykt.isCek();
        dlgpnykt.tampil();
        dlgpnykt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dlgpnykt.setLocationRelativeTo(internalFrame1);
        dlgpnykt.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        //Valid.pindah(evt,TKdPny,TDiagnosa);
}//GEN-LAST:event_BtnSeek1KeyPressed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,', ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                        " on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
        }else{            
            Valid.pindah(evt,TCari,TKdPny);
        }
}//GEN-LAST:event_TNoRwKeyPressed

private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TPasienKeyPressed

private void jenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jenisKeyPressed
 // Valid.pindah(evt,tgl,kdptgprwluar);
}//GEN-LAST:event_jenisKeyPressed

private void kdasistoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator1KeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator1,kdasistoperator1.getText());            
        }else{
            Valid.pindah(evt,kdbidan,kdoperator2);
        }
          
}//GEN-LAST:event_kdasistoperator1KeyPressed

private void BtnPtg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtg3ActionPerformed
   pilihan=1;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnPtg3ActionPerformed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil2();
}//GEN-LAST:event_BtnAllActionPerformed

private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatOperasi produsen=new DlgObatOperasi(null,false);
        produsen.emptTeks();   
        produsen.tampil();
        produsen.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        produsen.setLocationRelativeTo(internalFrame1);
        produsen.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());         
}//GEN-LAST:event_BtnTambahActionPerformed

private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    getData();
                    int row=tbObat.getSelectedColumn();
                    if(row==1){
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbObat.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                }
            }            
        }
}//GEN-LAST:event_tbObatKeyPressed

private void TCariPaketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPaketKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_TCariPaketKeyPressed

private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
   tampil();
}//GEN-LAST:event_BtnCari2ActionPerformed

private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnCari2KeyPressed

private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
  TCariPaket.setText("");
  tampil();
}//GEN-LAST:event_BtnAll1ActionPerformed

private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnAll1KeyPressed

private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPaketOperasi produsen=new DlgPaketOperasi(null,false);
        produsen.emptTeks();   
        produsen.tampil();
        produsen.isCek();
        produsen.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        produsen.setLocationRelativeTo(internalFrame1);
        produsen.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor()); 
}//GEN-LAST:event_BtnTambah1ActionPerformed

private void tbtindakanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtindakanMouseClicked
       if(tabMode2.getRowCount()!=0){
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbtindakanMouseClicked

private void tbtindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbtindakanKeyPressed
    if(tbtindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    int row=tbtindakan.getSelectedColumn();
                    if((row!=0)||(row!=20)){
                        if(tbtindakan.getSelectedRow()>-1){
                          tbtindakan.setValueAt(true,tbtindakan.getSelectedRow(),0);   
                        }                               
                        TCariPaket.setText("");
                        TCariPaket.requestFocus();
                    }                    
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbtindakanKeyPressed

private void kdoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator2KeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator2,kdoperator2.getText());
        }else{
            Valid.pindah(evt,kdoperator1,kdoperator3);
        }
}//GEN-LAST:event_kdoperator2KeyPressed

private void BtnOperator2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator2ActionPerformed
      pilihan=2;
        dokter.emptTeks();
        dokter.tampil("");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator2ActionPerformed

private void kdoperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator3KeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator3,kdoperator3.getText());
        }else{
            Valid.pindah(evt,kdoperator2,kdanestesi);
        }
}//GEN-LAST:event_kdoperator3KeyPressed

private void BtnOperator3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator3ActionPerformed
  pilihan=3;
        dokter.emptTeks();
        dokter.tampil("");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator3ActionPerformed

private void kdanestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdanestesiKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmanestesi,kdanestesi.getText());
        }else{
            Valid.pindah(evt,kdoperator3,kddranak);
        }
}//GEN-LAST:event_kdanestesiKeyPressed

private void BtnOperator4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator4ActionPerformed
        pilihan=4;
        dokter.emptTeks();
        dokter.tampil("");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator4ActionPerformed

private void kddranakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddranakKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,kddranak.getText());
        }else{
            Valid.pindah(evt,kdanestesi,kdbidan);
        }
}//GEN-LAST:event_kddranakKeyPressed

private void BtnOperator5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator5ActionPerformed
        pilihan=5;
        dokter.emptTeks();
        dokter.tampil("");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator5ActionPerformed

private void BtnPtg4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtg4ActionPerformed
   pilihan=2;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnPtg4ActionPerformed

private void kdasistoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator2KeyPressed
     if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator2,kdasistoperator2.getText());            
        }else{
            Valid.pindah(evt,kdoperator1,kdoperator3);
        }
}//GEN-LAST:event_kdasistoperator2KeyPressed

private void BtnPtg5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtg5ActionPerformed
  pilihan=3;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnPtg5ActionPerformed

private void kdasistoperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator3KeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip='"+kdasistoperator3.getText()+"'",nmasistoperator3);            
        }else{
            Valid.pindah(evt,kdasistoperator2,kdasistanestesi);
        }         
}//GEN-LAST:event_kdasistoperator3KeyPressed

private void BtnPtg6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtg6ActionPerformed
    pilihan=5;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnPtg6ActionPerformed

private void kdprwresustKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdprwresustKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmprwresust,kdprwresust.getText());            
        }else{
            Valid.pindah(evt,kdasistanestesi,kdprwluar);
        }
}//GEN-LAST:event_kdprwresustKeyPressed

private void kdasistanestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistanestesiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistanestesi,kdasistanestesi.getText());            
        }else{
            Valid.pindah(evt,kdoperator3,kdprwresust);
        }
}//GEN-LAST:event_kdasistanestesiKeyPressed

private void BtnPtg8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtg8ActionPerformed
   pilihan=4;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnPtg8ActionPerformed

private void kdbidanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidanKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan,kdbidan.getText());            
        }else{
            Valid.pindah(evt,kddranak,kdasistoperator1);
        }
}//GEN-LAST:event_kdbidanKeyPressed

private void BtnOperator6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator6ActionPerformed
    pilihan=7;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnOperator6ActionPerformed

private void kdprwluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdprwluarKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmprwluar,kdprwluar.getText());            
        }else{
            Valid.pindah(evt,kdprwresust,BtnSimpan);
        }
}//GEN-LAST:event_kdprwluarKeyPressed

private void BtnPtg9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtg9ActionPerformed
   pilihan=6;
        petugas.emptTeks();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnPtg9ActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
   isForm();  
}//GEN-LAST:event_ChkInputActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTagihanOperasi dialog = new DlgTagihanOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnKeluar;
    private widget.Button BtnOperator1;
    private widget.Button BtnOperator2;
    private widget.Button BtnOperator3;
    private widget.Button BtnOperator4;
    private widget.Button BtnOperator5;
    private widget.Button BtnOperator6;
    private widget.Button BtnPtg3;
    private widget.Button BtnPtg4;
    private widget.Button BtnPtg5;
    private widget.Button BtnPtg6;
    private widget.Button BtnPtg8;
    private widget.Button BtnPtg9;
    private widget.Button BtnSeek1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah1;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TCariPaket;
    private widget.TextBox TKdPny;
    private widget.TextBox TNmPny;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox jenis;
    private widget.TextBox kdanestesi;
    private widget.TextBox kdasistanestesi;
    private widget.TextBox kdasistoperator1;
    private widget.TextBox kdasistoperator2;
    private widget.TextBox kdasistoperator3;
    private widget.TextBox kdbidan;
    private widget.TextBox kddranak;
    private widget.TextBox kdoperator1;
    private widget.TextBox kdoperator2;
    private widget.TextBox kdoperator3;
    private widget.TextBox kdprwluar;
    private widget.TextBox kdprwresust;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label9;
    private widget.TextBox nmanestesi;
    private widget.TextBox nmasistanestesi;
    private widget.TextBox nmasistoperator1;
    private widget.TextBox nmasistoperator2;
    private widget.TextBox nmasistoperator3;
    private widget.TextBox nmbidan;
    private widget.TextBox nmdranak;
    private widget.TextBox nmoperator1;
    private widget.TextBox nmoperator2;
    private widget.TextBox nmoperator3;
    private widget.TextBox nmprwluar;
    private widget.TextBox nmprwresust;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.Table tbObat;
    private widget.Table tbtindakan;
    private widget.Tanggal tgl;
    // End of variables declaration//GEN-END:variables

    public void tampil() {  
        jml=0;
        for(i=0;i<tabMode.getRowCount();i++){
            if(tabMode.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        
        pilih=null;
        pilih=new boolean[jml]; 
        kode_paket=null;
        kode_paket=new String[jml];
        nm_perawatan=null;
        nm_perawatan=new String[jml];
        operator1=null;
        operator1=new double[jml];
        operator2=null;
        operator2=new double[jml];
        operator3=null;
        operator3=new double[jml];
        asisten_operator1=null;
        asisten_operator1=new double[jml];
        asisten_operator2=null;
        asisten_operator2=new double[jml];
        asisten_operator3=null;
        asisten_operator3=new double[jml];
        dokter_anak=null;
        dokter_anak=new double[jml];
        perawaat_resusitas=null;
        perawaat_resusitas=new double[jml];
        dokter_anestesi=null;
        dokter_anestesi=new double[jml];
        asisten_anestesi=null;
        asisten_anestesi=new double[jml];
        bidan=null;
        bidan=new double[jml];
        perawat_luar=null;
        perawat_luar=new double[jml];   
        sewa_ok=null;
        sewa_ok=new double[jml];
        sewa_vk=null;
        sewa_vk=new double[jml];
        bagian_rs=null;
        bagian_rs=new double[jml];
        omloop=null;
        omloop=new double[jml];
        alat=null;
        alat=new double[jml];   
        ttltindakan=null;
        ttltindakan=new double[jml];        
        index=0;        
        for(i=0;i<tabMode.getRowCount();i++){
            if(tabMode.getValueAt(i,0).toString().equals("true")){
                pilih[index]=true;
                kode_paket[index]=tabMode.getValueAt(i,1).toString();
                nm_perawatan[index]=tabMode.getValueAt(i,2).toString();
                operator1[index]=Double.parseDouble(tabMode.getValueAt(i,3).toString());
                operator2[index]=Double.parseDouble(tabMode.getValueAt(i,4).toString());
                operator3[index]=Double.parseDouble(tabMode.getValueAt(i,5).toString());
                asisten_operator1[index]=Double.parseDouble(tabMode.getValueAt(i,6).toString());
                asisten_operator2[index]=Double.parseDouble(tabMode.getValueAt(i,7).toString());
                asisten_operator3[index]=Double.parseDouble(tabMode.getValueAt(i,8).toString());
                dokter_anak[index]=Double.parseDouble(tabMode.getValueAt(i,9).toString());
                perawaat_resusitas[index]=Double.parseDouble(tabMode.getValueAt(i,10).toString());
                dokter_anestesi[index]=Double.parseDouble(tabMode.getValueAt(i,11).toString());
                asisten_anestesi[index]=Double.parseDouble(tabMode.getValueAt(i,12).toString());
                bidan[index]=Double.parseDouble(tabMode.getValueAt(i,13).toString());
                perawat_luar[index]=Double.parseDouble(tabMode.getValueAt(i,14).toString());
                sewa_ok[index]=Double.parseDouble(tabMode.getValueAt(i,15).toString());
                alat[index]=Double.parseDouble(tabMode.getValueAt(i,16).toString());   
                sewa_vk[index]=Double.parseDouble(tabMode.getValueAt(i,17).toString());  
                bagian_rs[index]=Double.parseDouble(tabMode.getValueAt(i,18).toString());  
                omloop[index]=Double.parseDouble(tabMode.getValueAt(i,19).toString());   
                ttltindakan[index]=Double.parseDouble(tabMode.getValueAt(i,20).toString());                
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            Object[] data={pilih[i],kode_paket[i],nm_perawatan[i],operator1[i],
                operator2[i],operator3[i],asisten_operator1[i],asisten_operator2[i],
                asisten_operator3[i],dokter_anak[i],perawaat_resusitas[i],dokter_anestesi[i],
                asisten_anestesi[i],bidan[i],perawat_luar[i],sewa_ok[i],alat[i],sewa_vk[i],bagian_rs[i],omloop[i],ttltindakan[i]};
            tabMode.addRow(data);
        }
        
        try{
            pstindakan.setString(1,"%"+TCariPaket.getText()+"%");
            pstindakan.setString(2,"%"+TCariPaket.getText()+"%");
            rs=pstindakan.executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{false,rs.getString(1),
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
                               rs.getString(18),
                               rs.getString(19),
                               rs.getString(20)});
            }                  
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }
    
    //obat
    
    public void tampil2() {
        jml=0;
        for(i=0;i<tbObat.getRowCount();i++){
            //System.out.println(tbObat.getValueAt(i,0).toString());
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        
        jmlobat=new double[jml];
        kd_obat=new String[jml];
        nm_obat=new String[jml];
        satuan=new String[jml];
        hargasatuan=new double[jml];
        ttlobat=new double[jml];
        
        index=0;        
        for(i=0;i<tabMode2.getRowCount();i++){
            if(!tabMode2.getValueAt(i,0).toString().equals("")){
                jmlobat[index]=Double.parseDouble(tabMode2.getValueAt(i,0).toString());
                kd_obat[index]=tabMode2.getValueAt(i,1).toString();
                nm_obat[index]=tabMode2.getValueAt(i,2).toString();
                satuan[index]=tabMode2.getValueAt(i,3).toString();
                hargasatuan[index]=Double.parseDouble(tabMode2.getValueAt(i,4).toString());
                ttlobat[index]=Double.parseDouble(tabMode2.getValueAt(i,5).toString());
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode2);
        
        for(i=0;i<jml;i++){
            Object[] data={jmlobat[i],kd_obat[i],nm_obat[i],satuan[i],hargasatuan[i],ttlobat[i]};
            tabMode2.addRow(data);
        }
        
        try{      
            psobat.setString(1,"%"+TCari.getText()+"%");
            psobat.setString(2,"%"+TCari.getText()+"%");
            psobat.setString(3,"%"+TCari.getText()+"%");
            rs=psobat.executeQuery();
            while(rs.next()){
                tabMode2.addRow(new Object[]{"",rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),0});
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }

    
       
    private void getData(){
       int row=tbObat.getSelectedRow();
        if(row!= -1){         
           int kolom=tbObat.getSelectedColumn();  
           if((kolom==0)||(kolom==1)){    
               if(!tbObat.getValueAt(row,0).toString().equals("")){
                   tbObat.setValueAt(Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(row,0).toString())*Double.parseDouble(tbObat.getValueAt(row,4).toString())), row,5);                    
                }                 
            }              
            
            biayaobat=0;
            y=0;
            int row2=tbObat.getRowCount();
            for(int r=0;r<row2;r++){ 
                if(!tbObat.getValueAt(r,5).toString().isEmpty()){
                    y=Double.parseDouble(tbObat.getValueAt(r,5).toString());                
                }else if(tbObat.getValueAt(r,5).toString().isEmpty()){
                    y=0;                
                }
                biayaobat=biayaobat+y;
            }
            LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
        }
    }
    
    private void getData2(){
       int row=tbtindakan.getSelectedRow();
        if(row!= -1){         
            if(tbtindakan.getValueAt(tbtindakan.getSelectedRow(),0).toString().equals("true")){
               tbtindakan.setValueAt(Valid.SetAngka2(Double.parseDouble(tbtindakan.getValueAt(row,3).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,4).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,5).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,6).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,7).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,8).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,9).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,10).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,11).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,12).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,13).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,14).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,15).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,16).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,17).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,18).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,19).toString())), row,20);
            }
           
            biayatindakan=0;
            y=0;
            int row2=tbtindakan.getRowCount();
            for(int r=0;r<row2;r++){ 
                switch (tbtindakan.getValueAt(r,0).toString()) {
                    case "true":
                        y=Double.parseDouble(tbtindakan.getValueAt(r,20).toString());
                        break;                
                    case "false":
                        y=0;
                        break;
                }
                biayatindakan=biayatindakan+y;
            }
            LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
        }
    }
    

    
    public void isCek(){
       // Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,6),signed)),0) from penjualan ","PJ",6,NoNota); 
        TCari.requestFocus();
        if(var.getjml2()>=1){
            kdoperator1.setEditable(false);
            BtnOperator1.setEnabled(false);
            BtnSimpan.setEnabled(var.getpenjualan());
            kdoperator1.setText(var.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmoperator1,kdoperator1.getText());
        }        
    }
    
    public void setNoRm(String norm,String nama){
        TNoRw.setText(norm);
        TPasien.setText(nama);
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,274));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
 
}
