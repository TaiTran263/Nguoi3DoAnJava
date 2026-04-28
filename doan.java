import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
class PhieuMuon extends Document {
    protected  String MaPhieuMuon;
    protected  LocalDate NgayMuon;
    protected  docgia DocGia;
    protected thuthu ThuThu;
    protected ArrayList<Document> dsTaiLieu = new ArrayList<>();
    protected ArrayList<Integer> SoLuongMuon = new ArrayList<>();
    protected ArrayList<LocalDate> dsNgayMuon = new ArrayList<>();
    protected ArrayList<LocalDate> dsHanTra = new ArrayList<>();
    public PhieuMuon(){}
    public PhieuMuon(String MaPhieuMuon, LocalDate NgayMuon, docgia DocGia, thuthu ThuThu, ArrayList<Document> dsTaiLieu, ArrayList<Integer> SoLuongMuon, ArrayList<LocalDate> dsNgayMuon, ArrayList<LocalDate> dsHanTra) {
        this.MaPhieuMuon = MaPhieuMuon;
        this.NgayMuon = NgayMuon;
        this.DocGia = DocGia;
        this.ThuThu = ThuThu;
        this.dsTaiLieu = dsTaiLieu;
        this.SoLuongMuon = SoLuongMuon;
        this.dsNgayMuon = dsNgayMuon;
        this.dsHanTra = dsHanTra;
    }
    public String getMaPhieuMuon() {
        return MaPhieuMuon;
    }
    public void setMaPhieuMuon(String MaPhieuMuon) {
        this.MaPhieuMuon = MaPhieuMuon;
    }
    public LocalDate getNgayMuon() {
        return NgayMuon;
    }
    public void setNgayMuon(LocalDate NgayMuon) {
        this.NgayMuon = NgayMuon;
    }
    public docgia getDocGia() {
        return DocGia;
    }
    public void setDocGia(docgia DocGia) {
        this.DocGia = DocGia;
    }
    public thuthu getThuThu() {
        return ThuThu;
    }
    public void setThuThu(thuthu ThuThu) {
        this.ThuThu = ThuThu;
    }
    public ArrayList<Document> getDsTaiLieu() {
        return dsTaiLieu;
    }
    public void setDsTaiLieu(ArrayList<Document> dsTaiLieu) {
        this.dsTaiLieu = dsTaiLieu;
    }
    public ArrayList<Integer> getSoLuongMuon() {
        return SoLuongMuon;
    }
    public void setSoLuongMuon(ArrayList<Integer> SoLuongMuon) {
        this.SoLuongMuon = SoLuongMuon;
    }
    public ArrayList<LocalDate> getDsNgayMuon() {
        return dsNgayMuon;
    }
    public void setDsNgayMuon(ArrayList<LocalDate> dsNgayMuon) {
        this.dsNgayMuon = dsNgayMuon;
    }
    public ArrayList<LocalDate> getDsHanTra() {
        return dsHanTra;
    }
    public void setDsHanTra(ArrayList<LocalDate> dsHanTra) {
        this.dsHanTra = dsHanTra;
    }
    public void ThemTaiLieu (Document TaiLieu, Integer SoLuong, LocalDate NgayMuon, LocalDate HanTra)
    {
        dsTaiLieu.add(TaiLieu);
        SoLuongMuon.add(SoLuong);
        dsNgayMuon.add(NgayMuon);
        dsHanTra.add(HanTra);
    }
    public void xoaTaiLieu (String MaTaiLieu)
    {
        for(int i=0;i<dsTaiLieu.size();i++)
        {
            if(dsTaiLieu.get(i).getMaTaiLieu().equals(MaTaiLieu))
            {
                dsTaiLieu.remove(i);
                SoLuongMuon.remove(i);
                dsNgayMuon.remove(i);
                dsHanTra.remove(i);
                break;
            }
        }
    }
    public int TongSoLuongMuon()
    {
        int tong=0;
        for(int i=0;i<SoLuongMuon.size();i++)
        {
            tong+=SoLuongMuon.get(i);
        }
        return tong;
    }
    public void Nhap()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Nhap ma phieu muon: ");
        MaPhieuMuon=sc.nextLine();
        System.out.println("Nhap ngay muon (yyyy-mm-dd): ");
        NgayMuon=LocalDate.parse(sc.nextLine());
        DocGia=new docgia();
        DocGia.Nhap();
        ThuThu=new thuthu();
        ThuThu.Nhap();
    }
    public void HienThiThongTin()
    {
        System.out.println("Ma phieu muon: "+MaPhieuMuon);
        System.out.println("Ngay muon: "+NgayMuon);
        if (DocGia!=null)
            System.out.println("Ten doc gia: "+DocGia.getTenDocGia());
        if (ThuThu!=null)
            System.out.println("Ten thu thu: "+ThuThu.getTenThuThu());
        System.out.println("Danh sach tai lieu muon:");
        for(int i=0;i<dsTaiLieu.size();i++)
        {
            if(dsTaiLieu.get(i)!=null)
            {
                System.out.println("- "+dsTaiLieu.get(i).getTenTaiLieu()+" (So luong: "+SoLuongMuon.get(i)+", Ngay muon: "+dsNgayMuon.get(i)+", Han tra: "+dsHanTra.get(i)+")");
            }
        }
    }
}