import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

public abstract class Tailieu {
    protected String IDTaiLieu;
    protected String title;
    protected String NXB;
    protected int Soluong;
    protected int namPH;

    public Tailieu(String IDTaiLieu, String title, int Soluong, int namPH) {
        this.IDTaiLieu = IDTaiLieu;
        this.title = title;
        this.NXB = NXB;
        this.Soluong = Soluong;
        this.namPH = namPH;
    }
    //getter, setter
    public String getIDTaiLieu() {return IDTaiLieu;}
    public void setIDTaiLieu(String IDTaiLieu) {this.IDTaiLieu = IDTaiLieu;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getNXB() {return NXB;}
    public void setNXB(String NXB) {this.NXB = NXB;}
    public int getSoluong() {return Soluong;}
    public void setSoluong(int Soluong) {this.Soluong = Soluong;}
    public void accSoluong(int sl) {this.Soluong += sl;}
    public void desSoluong(int sl) {
        if(this.Soluong >= sl)
            this.Soluong -= sl;
    }
    public abstract String getLoai();
    public void nhap(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ID Tài liệu:");
        this.IDTaiLieu = sc.nextLine();
        System.out.print("Nhập tiêu đề:");
        this.title = sc.nextLine();
        System.out.print("Nhập NXB:");
        this.NXB = sc.nextLine();
        System.out.print("Nhập Số lượng:");
        this.Soluong = sc.nextInt();
        System.out.print("Nhập năm PH:");
        this.namPH = sc.nextInt();
    }
}

public class Tacgia{
    protected String ButDanh;

    public Tacgia(String Butdanh){
        this.ButDanh = Butdanh;
    }
    public String getButDanh(){return ButDanh;}
    public void setButDanh(String ButDanh) {this.ButDanh = ButDanh;}
}

public class Sach extends Tailieu {
    private List<Tacgia> dsTacgia = new ArrayList<>();
    private int soTrang;
    private String theloai;

    @Override
    public String getLoai() {return "Sách";}

    public List<Tacgia> getTacgia() {return dsTacgia;}
    public void setTacgia(List<Tacgia> dsTacgia) {}
    public int getSoTrang() {return soTrang;}
    public void setSoTrang(int SoTrang) {this.soTrang = SoTrang;}
    public String getTheloai() {return theloai;}
    public void setTheloai(String theloai) {this.theloai = theloai;}
}

public class TapChi extends Tailieu {
    private String SoPhatHanh;
    private int ThangPhatHanh;

    @Override
    public String getLoai() {return "Tạp chí";}

    public String getSoPhatHanh() {return SoPhatHanh;}
    public void setSoPhatHanh(String SoPhatHanh){this.SoPhatHanh = SoPhatHanh;}
    public int getThangPhatHanh() {return ThangPhatHanh;}
    public void setThangPhatHanh(int ThangPhatHanh){this.ThangPhatHanh = ThangPhatHanh;}
}

public class Bao extends Tailieu {
    private String NgayPH;

    @Override
    public String getLoai() {return "Báo";}

    public String getNgayPH() {return NgayPH;}
    public void setNgayPH(String NgayPH) {this.NgayPH = NgayPH;}
}

public class ThuVien {
    private String tenThuVien;
    private String diaChi;
    private List<Tailieu> dsTaiLieu;
    private List<DocGia> dsDocGia;
    private List<PhieuMuon> dsPhieuMuon;
    private List<PhieuTra> dsPhieuTra;

    public ThuVien() {
        this.dsTaiLieu = new ArrayList<>();
        this.dsDocGia = new ArrayList<>();
        this.dsPhieuMuon = new ArrayList<>();
        this.dsPhieuTra = new ArrayList<>();
    }

    public ThuVien(String tenThuVien, String diaChi) {
        this();
        this.tenThuVien = tenThuVien;
        this.diaChi = diaChi;
    }


    public String getTenThuVien() { return tenThuVien; }
    public void setTenThuVien(String tenThuVien) { this.tenThuVien = tenThuVien; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public List<Tailieu> getDsTaiLieu() { return dsTaiLieu; }
    public void setDsTaiLieu(List<Tailieu> dsTaiLieu) { this.dsTaiLieu = dsTaiLieu; }

    public List<DocGia> getDsDocGia() { return dsDocGia; }
    public void setDsDocGia(List<DocGia> dsDocGia) { this.dsDocGia = dsDocGia; }

    public List<PhieuMuon> getDsPhieuMuon() { return dsPhieuMuon; }
    public void setDsPhieuMuon(List<PhieuMuon> dsPhieuMuon) { this.dsPhieuMuon = dsPhieuMuon; }

    public List<PhieuTra> getDsPhieuTra() { return dsPhieuTra; }
    public void setDsPhieuTra(List<PhieuTra> dsPhieuTra) { this.dsPhieuTra = dsPhieuTra; }
}

public class QuanLyThuVien {
    private ThuVien thuVien;

    public QuanLyThuVien() {
        this.thuVien = new ThuVien();
    }

    public QuanLyThuVien(ThuVien thuVien) {
        this.thuVien = thuVien;
    }

    public ThuVien getThuVien() { return thuVien; }
    public void setThuVien(ThuVien tv) { this.thuVien = tv; }

    public void themTaiLieu(Tailieu tl) {
        this.thuVien.getDsTaiLieu().add(tl);
    }

    public void xoaTaiLieu(String ma) {
        this.thuVien.getDsTaiLieu().removeIf(tl -> tl.getIDTaiLieu().equalsIgnoreCase(ma));
    }

    public List<Tailieu> timSachTheoTen(String ten) {
        List<Tailieu> ketQua = new ArrayList<>();
        for (Tailieu tl : thuVien.getDsTaiLieu()) {
            if (tl instanceof Sach && tl.getTitle().toLowerCase().contains(ten.toLowerCase())) {
                ketQua.add(tl);
            }
        }
        return ketQua;
    }

    public void sapXepSachTheoTen() {
        this.thuVien.getDsTaiLieu().sort((tl1, tl2) -> tl1.getTitle().compareToIgnoreCase(tl2.getTitle()));
    }

    public void sapXepSachTheoSoLuong() {
        this.thuVien.getDsTaiLieu().sort(Comparator.comparingInt(Tailieu::getSoluong));
    }

    public void hienThiDanhSachTaiLieu() {
        System.out.println("DANH SÁCH TÀI LIỆU");
        for (Tailieu tl : thuVien.getDsTaiLieu()) {
            System.out.println("Mã: " + tl.getIDTaiLieu() + " | Tên: " + tl.getTitle() + " | Loại: " + tl.getLoai());
        }
    }

    public void themDocGia(DocGia dg) {
        this.thuVien.getDsDocGia().add(dg);
    }

    public void xoaDocGia(String ma) {
        this.thuVien.getDsDocGia().removeIf(dg -> dg.getIdDocGia().equalsIgnoreCase(ma));
    }
}