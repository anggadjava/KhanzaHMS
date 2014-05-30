package khanzahms;
import keuangan.*;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DlgFrekuensiPenyakitRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgFrekuensiPenyakitRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Penyakit","Nama Penyakit","Ciri-ciri","Lk2(Hidup)","Pr(Hidup)","Lk2(Mati)","Pr(Mati)","Jumlah"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Integer.class, 
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int m = 0; m < 8; m++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(m);
            if(m==0){
                column.setPreferredWidth(100);
            }else if(m==1){
                column.setPreferredWidth(300);
            }else if(m==2){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdpenyakit.setDocument(new batasInput((byte)20).getKata(kdpenyakit));
                
        penyakit.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdpenyakit.setText(penyakit.getTextField().getText());
                Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit='"+kdpenyakit.getText()+"' ",nmpenyakit);  
                prosesCari();
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
            ps=koneksi.prepareStatement("select penyakit.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as penyakit,SUBSTRING(penyakit.ciri_ciri,1,80) ciri from penyakit inner join rawat_jl_dr inner join reg_periksa "+
                    "on penyakit.kd_penyakit=rawat_jl_dr.kd_penyakit and reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                    "where penyakit.kd_penyakit like ? and reg_periksa.tgl_registrasi between ? and ? group by penyakit.kd_penyakit order by penyakit.kd_penyakit");
            ps2=koneksi.prepareStatement("select rawat_jl_dr.no_rawat as jumlah from rawat_jl_dr inner join reg_periksa on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                       "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_penyakit=? group by rawat_jl_dr.no_rawat");    
            ps3=koneksi.prepareStatement("select rawat_jl_dr.no_rawat as jumlah from rawat_jl_dr inner join reg_periksa inner join pasien_mati "+
                       "inner join pasien on reg_periksa.no_rawat=rawat_jl_dr.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "and pasien_mati.no_rkm_medis=pasien.no_rkm_medis where pasien.jk='L' and reg_periksa.tgl_registrasi between ? and ? "+
                       "and rawat_jl_dr.kd_penyakit=? group by rawat_jl_dr.no_rawat");  
            ps4=koneksi.prepareStatement("select rawat_jl_dr.no_rawat as jumlah from rawat_jl_dr inner join reg_periksa inner join pasien_mati "+
                       "inner join pasien on reg_periksa.no_rawat=rawat_jl_dr.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "and pasien_mati.no_rkm_medis=pasien.no_rkm_medis where pasien.jk='P' and reg_periksa.tgl_registrasi between ? and ? "+
                       "and rawat_jl_dr.kd_penyakit=? group by rawat_jl_dr.no_rawat"); 
            ps5=koneksi.prepareStatement("select rawat_jl_dr.no_rawat as jumlah from rawat_jl_dr inner join reg_periksa "+
                       "inner join pasien on reg_periksa.no_rawat=rawat_jl_dr.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "where pasien.jk='L' and reg_periksa.tgl_registrasi between ? and ? "+
                       "and rawat_jl_dr.kd_penyakit=? group by rawat_jl_dr.no_rawat");
            ps6=koneksi.prepareStatement("select rawat_jl_dr.no_rawat as jumlah from rawat_jl_dr inner join reg_periksa "+
                       "inner join pasien on reg_periksa.no_rawat=rawat_jl_dr.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "where pasien.jk='P' and reg_periksa.tgl_registrasi between ? and ? "+
                       "and rawat_jl_dr.kd_penyakit=? group by rawat_jl_dr.no_rawat");
        }catch (SQLException e) {
            System.out.println(e);
        }
     
    }
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    private int i=0,a=0,b=0,c=0,d=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikTerbanyakBatang = new javax.swing.JMenuItem();
        ppGrafikTerbanyakPie = new javax.swing.JMenuItem();
        ppGrafikTerkecilBatang = new javax.swing.JMenuItem();
        ppGrafikTerkecilPie = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdpenyakit = new widget.TextBox();
        nmpenyakit = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikTerbanyakBatang.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerbanyakBatang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerbanyakBatang.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerbanyakBatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerbanyakBatang.setText("Grafik Batang 10 Penyakit Terbanyak");
        ppGrafikTerbanyakBatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerbanyakBatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerbanyakBatang.setIconTextGap(8);
        ppGrafikTerbanyakBatang.setName("ppGrafikTerbanyakBatang"); // NOI18N
        ppGrafikTerbanyakBatang.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerbanyakBatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerbanyakBatangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerbanyakBatang);

        ppGrafikTerbanyakPie.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerbanyakPie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerbanyakPie.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerbanyakPie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerbanyakPie.setText("Grafik Pie 10 Penyakit Terbanyak");
        ppGrafikTerbanyakPie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerbanyakPie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerbanyakPie.setIconTextGap(8);
        ppGrafikTerbanyakPie.setName("ppGrafikTerbanyakPie"); // NOI18N
        ppGrafikTerbanyakPie.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerbanyakPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerbanyakPieActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerbanyakPie);

        ppGrafikTerkecilBatang.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerkecilBatang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerkecilBatang.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerkecilBatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerkecilBatang.setText("Grafik Batang 10 Penyakit Tersedikit");
        ppGrafikTerkecilBatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerkecilBatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerkecilBatang.setIconTextGap(8);
        ppGrafikTerkecilBatang.setName("ppGrafikTerkecilBatang"); // NOI18N
        ppGrafikTerkecilBatang.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerkecilBatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerkecilBatangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerkecilBatang);

        ppGrafikTerkecilPie.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerkecilPie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerkecilPie.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerkecilPie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerkecilPie.setText("Grafik Pie 10 Penyakit Tersedikit");
        ppGrafikTerkecilPie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerkecilPie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerkecilPie.setIconTextGap(8);
        ppGrafikTerkecilPie.setName("ppGrafikTerkecilPie"); // NOI18N
        ppGrafikTerkecilPie.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerkecilPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerkecilPieActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerkecilPie);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Frekuensi Penyakit Di Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Perawatan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("yyyy-MM-dd");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("yyyy-MM-dd");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl2);

        label17.setText("Penyakit :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);

        kdpenyakit.setName("kdpenyakit"); // NOI18N
        kdpenyakit.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenyakitKeyPressed(evt);
            }
        });
        panelisi4.add(kdpenyakit);

        nmpenyakit.setEditable(false);
        nmpenyakit.setName("nmpenyakit"); // NOI18N
        nmpenyakit.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmpenyakit);

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
        panelisi4.add(BtnSeek2);

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
        panelisi4.add(BtnCari);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(450, 30));
        panelisi1.add(label9);

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
        if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tbDokter.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tbDokter.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tbDokter.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                tbDokter.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                tbDokter.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tbDokter.getValueAt(r,3).toString()+"','"+
                                tbDokter.getValueAt(r,4).toString()+"','"+
                                tbDokter.getValueAt(r,5).toString()+"','"+
                                tbDokter.getValueAt(r,6).toString()+"','"+
                                tbDokter.getValueAt(r,7).toString()+"','','','','','','','','',''","Rekap Frekuensi Penyakit"); 
            }
            Valid.panggilUrl("billing/LaporanPenyakitRalan.php?tanggal1="+Tgl1.getSelectedItem()+"&tanggal2="+Tgl2.getSelectedItem());                       
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdpenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit='"+kdpenyakit.getText()+"' ",nmpenyakit);  
           // TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit='"+kdpenyakit.getText()+"' ",nmpenyakit);  
            //TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit='"+kdpenyakit.getText()+"' ",nmpenyakit);  
        }
    }//GEN-LAST:event_kdpenyakitKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdpenyakit.setText("");
        nmpenyakit.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ppGrafikTerbanyakBatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerbanyakBatangActionPerformed
        if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())>=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(9,3).toString()),tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());


            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);           
            
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
           
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
             
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                  
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }        
        
    }//GEN-LAST:event_ppGrafikTerbanyakBatangActionPerformed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        var.setStatus(true);
        penyakit.tampil();
        penyakit.emptTeks();        
        penyakit.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setAlwaysOnTop(false);
        penyakit.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            //Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void ppGrafikTerbanyakPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerbanyakPieActionPerformed
     if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())>=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
            dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Integer.parseInt(tbDokter.getValueAt(9,3).toString()));

            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
            
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
               
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);         
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);        
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }    
}//GEN-LAST:event_ppGrafikTerbanyakPieActionPerformed

private void ppGrafikTerkecilBatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerkecilBatangActionPerformed
    if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())<=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(9,3).toString()),tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());


            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
           
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
             
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                 
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                  
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }     
}//GEN-LAST:event_ppGrafikTerkecilBatangActionPerformed

private void ppGrafikTerkecilPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerkecilPieActionPerformed
   if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())<=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
            dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Integer.parseInt(tbDokter.getValueAt(9,3).toString()));

            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
            
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);       
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
               
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);          
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem(),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);          
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }    
}//GEN-LAST:event_ppGrafikTerkecilPieActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgFrekuensiPenyakitRalan dialog = new DlgFrekuensiPenyakitRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.TextBox Kd2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenyakit;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmpenyakit;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppGrafikTerbanyakBatang;
    private javax.swing.JMenuItem ppGrafikTerbanyakPie;
    private javax.swing.JMenuItem ppGrafikTerkecilBatang;
    private javax.swing.JMenuItem ppGrafikTerkecilPie;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            ps.setString(1,"%"+kdpenyakit.getText()+"%");
            ps.setString(2,Tgl1.getSelectedItem().toString());
            ps.setString(3,Tgl2.getSelectedItem().toString());
            rs=ps.executeQuery();
            while(rs.next()){
               i=0;
               ps2.setString(1,Tgl1.getSelectedItem().toString());
               ps2.setString(2,Tgl2.getSelectedItem().toString());
               ps2.setString(3,rs.getString("kd_penyakit"));
               rs2=ps2.executeQuery();
               rs2.last();
               if(rs2.getRow()>0) i=rs2.getRow();
               
               a=0;
               ps3.setString(1,Tgl1.getSelectedItem().toString());
               ps3.setString(2,Tgl2.getSelectedItem().toString());
               ps3.setString(3,rs.getString("kd_penyakit"));
               rs3=ps3.executeQuery();
               rs3.last();
               if(rs3.getRow()>0) a=rs3.getRow();

               b=0;
               ps4.setString(1,Tgl1.getSelectedItem().toString());
               ps4.setString(2,Tgl2.getSelectedItem().toString());
               ps4.setString(3,rs.getString("kd_penyakit"));
               rs4=ps4.executeQuery();
               rs4.last();       
               if(rs4.getRow()>0) b=rs4.getRow();
               
               c=0;
               ps5.setString(1,Tgl1.getSelectedItem().toString());
               ps5.setString(2,Tgl2.getSelectedItem().toString());
               ps5.setString(3,rs.getString("kd_penyakit"));
               rs5=ps5.executeQuery();
               rs5.last();
               if(rs5.getRow()>0)  c=rs5.getRow()-a;
                          
               d=0;
               ps6.setString(1,Tgl1.getSelectedItem().toString());
               ps6.setString(2,Tgl2.getSelectedItem().toString());
               ps6.setString(3,rs.getString("kd_penyakit"));
               rs6=ps6.executeQuery();
               rs6.last();
               if(rs6.getRow()>0) d=rs6.getRow()-b;
                 
               tabMode.addRow(new Object[]{rs.getString("kd_penyakit"),rs.getString("penyakit"),rs.getString("ciri"),c,d,a,b,i});                  
            } 
            //rs.close();
            label9.setText("      Record : "+tabMode.getRowCount());                        
        }catch(SQLException e){
            System.out.println("Error  "+e);
        }
        
    }
    
    public void isCek(){
        // BtnPrint.setEnabled(var.getproyeksi());
    }
    
}
