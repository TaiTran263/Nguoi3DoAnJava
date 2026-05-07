import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
interface IQuanly
{
    void docFile();
    void luuFile();
    void menu(Scanner sc);
}

abstract class Tailieu {
    protected String IDTaiLieu;
    protected String title;
    protected String NXB;
    protected int Soluong;
    protected int namPH;

    public Tailieu(String IDTaiLieu, String title, String NXB, int Soluong, int namPH) {
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
    public void nhap(Scanner sc){
        System.out.print("Nhap ID Tai lieu:");
        this.IDTaiLieu = sc.nextLine();
        System.out.print("Nhap tieu de:");
        this.title = sc.nextLine();
        System.out.print("Nhap NXB:");
        this.NXB = sc.nextLine();
        System.out.print("Nhap So luong:");
        this.Soluong = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap nam PH:");
        this.namPH = Integer.parseInt(sc.nextLine());
    }
}

class Tacgia{
    protected String ButDanh;

    public Tacgia(String Butdanh){
        this.ButDanh = Butdanh;
    }
    public String getButDanh(){return ButDanh;}
    public void setButDanh(String ButDanh) {this.ButDanh = ButDanh;}
}

class Sach extends Tailieu {
    private List<Tacgia> dsTacgia = new ArrayList<>();
    private int soTrang;
    private String theloai;
    public Sach (String IDTaiLieu, String title, String NXB, int Soluong, int namPH, int soTrang, String theloai) {
        super(IDTaiLieu, title, NXB, Soluong, namPH);
        this.soTrang = soTrang;
        this.theloai = theloai;
    }

    @Override
    public String getLoai() {return "Sách";}

    public List<Tacgia> getTacgia() {return dsTacgia;}
    public void setTacgia(List<Tacgia> dsTacgia) {this.dsTacgia = dsTacgia;}
    public int getSoTrang() {return soTrang;}
    public void setSoTrang(int SoTrang) {this.soTrang = SoTrang;}
    public String getTheloai() {return theloai;}
    public void setTheloai(String theloai) {this.theloai = theloai;}
}

class TapChi extends Tailieu {
    private String SoPhatHanh;
    private int ThangPhatHanh;
    public TapChi (String IDTaiLieu, String title, String NXB, int Soluong, int namPH, String SoPhatHanh, int ThangPhatHanh) {
        super(IDTaiLieu, title, NXB, Soluong, namPH);
        this.SoPhatHanh = SoPhatHanh;
        this.ThangPhatHanh = ThangPhatHanh;
    }

    @Override
    public String getLoai() {return "Tạp chí";}

    public String getSoPhatHanh() {return SoPhatHanh;}
    public void setSoPhatHanh(String SoPhatHanh){this.SoPhatHanh = SoPhatHanh;}
    public int getThangPhatHanh() {return ThangPhatHanh;}
    public void setThangPhatHanh(int ThangPhatHanh){this.ThangPhatHanh = ThangPhatHanh;}
}

class Bao extends Tailieu {
    private String NgayPH;
    public Bao (String IDTaiLieu, String title, String NXB, int Soluong, int namPH, String NgayPH) {
        super(IDTaiLieu, title, NXB, Soluong, namPH);
        this.NgayPH = NgayPH;
    }

    @Override
    public String getLoai() {return "Báo";}

    public String getNgayPH() {return NgayPH;}
    public void setNgayPH(String NgayPH) {this.NgayPH = NgayPH;}
}

class ThuVien {
    private String tenThuVien;
    private String diaChi;
    private List<Tailieu> dsTaiLieu;
    private List<DocGia> dsDocGia;

    public ThuVien() {
        this.dsTaiLieu = new ArrayList<>();
        this.dsDocGia = new ArrayList<>();
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

}

class QuanLyThuVien implements IQuanly {
    private ThuVien thuVien;
    private static final String FILE_TAILIEU = "tailieu.csv";

    
    public QuanLyThuVien() {
        this.thuVien = new ThuVien();
    }
    

    public QuanLyThuVien(ThuVien thuVien) {
        this.thuVien = thuVien;
    }
    @Override
    public void luuFile ()
    {
        try(BufferedWriter bw= new BufferedWriter(new FileWriter(FILE_TAILIEU)))
        {
            bw.write("Loai"+","+"IDTaiLieu"+","+"Title"+","+"NXB"+","+"Soluong"+","+"NamPH"+","+"ThuocTinh1"+","+"ThuocTinh2");
            bw.newLine();
            for(Tailieu tl: thuVien.getDsTaiLieu())
            {
                String dong;
                if (tl instanceof Sach s)
                {
                    dong= "Sach,"+s.getIDTaiLieu()+","+s.getTitle()+","+s.getNXB()+","+s.getSoluong()+","+s.namPH+","+s.getSoTrang()+","+s.getTheloai();
                }
                else if (tl instanceof TapChi tc)
                {
                    dong= "TapChi,"+tc.getIDTaiLieu()+","+tc.getTitle()+","+tc.getNXB()+","+tc.getSoluong()+","+tc.namPH+","+tc.getSoPhatHanh()+","+tc.getThangPhatHanh();
                }
                else if (tl instanceof Bao b)
                {
                    dong= "Bao,"+b.getIDTaiLieu()+","+b.getTitle()+","+b.getNXB()+","+b.getSoluong()+","+b.namPH+","+b.getNgayPH();
                }
                else continue;
                bw.write(dong);
                bw.newLine();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Loi luu file: "+e.getMessage());
        }
    }
    @Override
    public void docFile()
    {
        thuVien.getDsTaiLieu().clear();
        try (BufferedReader br= new BufferedReader(new FileReader(FILE_TAILIEU)))
        {
            String line;
            boolean first=true;
            while((line=br.readLine())!=null)
            {
                if(first) {first=false; continue;}
                if(line.trim().isEmpty()) continue;
                String[] p=line.split(",", -1);
                Tailieu tl=switch (p[0]) {
                    case "Sach" -> new Sach(p[1], p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), Integer.parseInt(p[6]), p[7]);
                    case "TapChi" -> new TapChi(p[1], p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6], Integer.parseInt(p[7]));
                    case "Bao" -> new Bao(p[1], p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6]);
                    default -> null;
                };
                if (tl != null) {
                    thuVien.getDsTaiLieu().add(tl);
                }
            }

        }catch (IOException e) 
        {
            System.out.println("Chua co file, bat dau moi.");
        }

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
            if (tl.getTitle().toLowerCase().contains(ten.toLowerCase())) {
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
        System.out.println("DANH SACH TAI LIEU");
        for (Tailieu tl : thuVien.getDsTaiLieu()) {
            System.out.println("Ma: " + tl.getIDTaiLieu() + " | Ten: " + tl.getTitle() + " | Loai: " + tl.getLoai() + " | So luong: "+tl.getSoluong());
        }
    }
    @Override
    public void menu (Scanner sc)
    {
        while (true)
        {
            System.out.println("\n====== QUAN LY TAI LIEU ======");
            System.out.println("1. Them tai lieu");
            System.out.println("2. Xoa tai lieu");
            System.out.println("3. Tim sach theo ten");
            System.out.println("4. Sap xep sach theo ten");
            System.out.println("5. Sap xep sach theo so luong");
            System.out.println("6. Hien thi danh sach tai lieu");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");

            int choice=-1;
            try
            {
                choice=Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("vui long chi nhap so nguyen");
                continue;
            }

            switch (choice)
            {
                case 1:
                    Tailieu tl;
                    System.out.println("Chon loai tai lieu:");
                    System.out.println("1. Sach");
                    System.out.println("2. Tap chi");
                    System.out.println("3. Bao");
                    int loai = Integer.parseInt(sc.nextLine());
                    switch (loai) {
                        case 1 -> tl = new Sach("", "", "", 0, 0, 0, "");
                        case 2 -> tl = new TapChi("", "", "", 0, 0, "", 0);
                        case 3 -> tl = new Bao("", "", "", 0, 0, "");
                        default -> {
                            System.out.println("Loai khong hop le.");
                            continue;
                        }
                    }
                    tl.nhap(sc);
                    themTaiLieu(tl);
                    luuFile();
                    break;
                case 2:
                    System.out.print("Nhap ma tai lieu can xoa: ");
                    String ma = sc.nextLine();
                    xoaTaiLieu(ma);
                    luuFile();
                    break;
                case 3:
                    System.out.print("Nhap ten sach can tim: ");
                    String ten = sc.nextLine();
                    List<Tailieu> ketQua = timSachTheoTen(ten);
                    if (ketQua.isEmpty()) {
                        System.out.println("Khong tim thay sach nao.");
                    } else {
                        System.out.println("Ket qua tim kiem:");
                        for (Tailieu t : ketQua) {
                            System.out.println("Mã: " + t.getIDTaiLieu() + " | Tên: " + t.getTitle() + " | Loại: " + t.getLoai());
                        }
                    }
                    break;
                case 4:
                    sapXepSachTheoTen();
                    luuFile();
                    break;
                case 5:
                    sapXepSachTheoSoLuong();
                    luuFile();
                    break;
                case 6:
                    hienThiDanhSachTaiLieu();
                    break;
                case 0:
                    System.out.println("Thoat quan ly tai lieu.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}

// ===================== Person (abstract, non-public) =====================
abstract class Person {
    protected String hoTen, gioiTinh, diaChi, email, SDT;
    protected int namSinh;
    protected boolean trangThai = true;

    public Person() {}
    public Person(String hoTen, int namSinh, String gioiTinh, String diaChi, String SDT, String email) {
        this.hoTen = hoTen; this.namSinh = namSinh; this.gioiTinh = gioiTinh;
        this.diaChi = diaChi; this.SDT = SDT; this.email = email;
    }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String v) { hoTen = v; }
    public int getNamSinh() { return namSinh; }
    public void setNamSinh(int v) { namSinh = v; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String v) { gioiTinh = v; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String v) { diaChi = v; }
    public String getSDT() { return SDT; }
    public void setSDT(String v) { SDT = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public boolean isTrangThai() { return trangThai; }

    public abstract String getRole();
    public abstract void nhap(Scanner sc);
    public abstract void xuat();
}

//=====================ThuThu=====================================
class ThuThu {
    private String maThuThu;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private TaiKhoan taiKhoan;

    public ThuThu(String maThuThu, String hoTen, String soDienThoai, String email, TaiKhoan taiKhoan) {
        this.maThuThu = maThuThu;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.taiKhoan = taiKhoan;
    }

    public String getMaThuThu()    { return maThuThu; }
    public String getHoTen()       { return hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail()       { return email; }
    public TaiKhoan getTaiKhoan()  { return taiKhoan; }

    public void setHoTen(String hoTen)             { this.hoTen = hoTen; }
    public void setSoDienThoai(String sdt)         { this.soDienThoai = sdt; }
    public void setEmail(String email)             { this.email = email; }

    @Override
    public String toString() {
        return String.format("ThuThu{ma='%s', hoTen='%s', sdt='%s', email='%s'}",
                maThuThu, hoTen, soDienThoai, email);
    }
}
class QuanLyThuThu {
    private List<ThuThu> danhSachThuThu;
    private List<ChamCong> danhSachChamCong;
    private int soThuThuTiepTheo = 1;
    private int soChamCongTiepTheo = 1;

    public QuanLyThuThu() {
        danhSachThuThu = new ArrayList<>();
        danhSachChamCong = new ArrayList<>();
    }

    // ===================== QUẢN LÝ THỦ THƯ =====================

    public ThuThu themThuThu(String hoTen, String sdt, String email, TaiKhoan taiKhoan) {
        String ma = String.format("TT%03d", soThuThuTiepTheo++);
        ThuThu tt = new ThuThu(ma, hoTen, sdt, email, taiKhoan);
        danhSachThuThu.add(tt);
        System.out.println("✅ Them thu thu thanh cong: " + tt);
        return tt;
    }

    public boolean xoaThuThu(String maThuThu) {
        ThuThu tt = timThuThu(maThuThu);
        if (tt != null) {
            danhSachThuThu.remove(tt);
            System.out.println("🗑️ Da xoa thu thu: " + maThuThu);
            return true;
        }
        System.out.println("❌ Khong tim thay thu thu: " + maThuThu);
        return false;
    }

    public ThuThu timThuThu(String maThuThu) {
        return danhSachThuThu.stream()
                .filter(tt -> tt.getMaThuThu().equalsIgnoreCase(maThuThu))
                .findFirst().orElse(null);
    }

    public void hienThiDanhSach() {
        System.out.println("\n📋 DANH SACH THU THU (" + danhSachThuThu.size() + " người):");
        System.out.println("-".repeat(60));
        if (danhSachThuThu.isEmpty()) {
            System.out.println("  (Chưa có thủ thư nào)");
        } else {
            danhSachThuThu.forEach(tt -> System.out.println("  " + tt));
        }
        System.out.println("-".repeat(60));
    }

    // ===================== CHẤM CÔNG =====================

    public ChamCong chamCong(String maThuThu, String trangThai) {
        ThuThu tt = timThuThu(maThuThu);
        if (tt == null) {
            System.out.println("❌ Khong tim thay thu thu: " + maThuThu);
            return null;
        }
        String ma = String.format("CC%04d", soChamCongTiepTheo++);
        ChamCong cc = new ChamCong(ma, tt, LocalDate.now(), LocalTime.now(), trangThai);
        danhSachChamCong.add(cc);
        System.out.println("✅ Cham cong: " + cc);
        return cc;
    }

    public void checkOut(String maChamCong) {
        danhSachChamCong.stream()
                .filter(cc -> cc.getMaChamCong().equals(maChamCong))
                .findFirst()
                .ifPresentOrElse(cc -> {
                    cc.setGioRa(LocalTime.now());
                    System.out.println("✅ Check-out: " + cc);
                }, () -> System.out.println("❌ Khong tim thay ban ghi cham cong: " + maChamCong));
    }

    // ===================== THỐNG KÊ =====================

    public void thongKeChamCongTheoThang(int thang, int nam) {
        List<ChamCong> ds = danhSachChamCong.stream()
                .filter(cc -> cc.getNgay().getMonthValue() == thang && cc.getNgay().getYear() == nam)
                .collect(Collectors.toList());

        System.out.printf("\n📊 THONG KE CHAM CONG THANG %02d/%d:%n", thang, nam);
        System.out.println("-".repeat(60));

        if (ds.isEmpty()) {
            System.out.println("  Khong co du lieu.");
        } else {
            // Nhóm theo thủ thư
            danhSachThuThu.forEach(tt -> {
                List<ChamCong> dsTheoTT = ds.stream()
                        .filter(cc -> cc.getThuThu().getMaThuThu().equals(tt.getMaThuThu()))
                        .collect(Collectors.toList());
                long soNgayDiLam = dsTheoTT.stream().filter(cc -> "DiLam".equals(cc.getTrangThai())).count();
                long soNgayVang = dsTheoTT.stream().filter(cc -> "VangMat".equals(cc.getTrangThai())).count();
                double tongGio = dsTheoTT.stream().mapToDouble(ChamCong::tinhSoGioLam).sum();
                System.out.printf("  %-20s | Đi làm: %2d | Vắng: %2d | Tổng giờ: %.1fh%n",
                        tt.getHoTen(), soNgayDiLam, soNgayVang, tongGio);
            });
        }
        System.out.println("-".repeat(60));
    }

    public void thongKeTongQuat() {
        System.out.println("\n📈 THONG KE TONG QUAT:");
        System.out.println("  Tong so thu thu  : " + danhSachThuThu.size());
        System.out.println("  Tong ban ghi CC  : " + danhSachChamCong.size());
        long diLam = danhSachChamCong.stream().filter(cc -> "DiLam".equals(cc.getTrangThai())).count();
        long vang  = danhSachChamCong.stream().filter(cc -> "VangMat".equals(cc.getTrangThai())).count();
        System.out.println("  Tong ngay đi làm : " + diLam);
        System.out.println("  Tong ngay vang   : " + vang);
    }

    // ===================== GETTERS =====================
    public List<ThuThu> getDanhSachThuThu() { return danhSachThuThu; }
    public List<ChamCong> getDanhSachChamCong() { return danhSachChamCong; }
}
// ===================== DocGia (non-public) =====================
class DocGia extends Person {
    protected String idDocGia;
    protected LocalDate ngayDangKy, ngayBatDauCam, ngayKetThucCam;
    protected boolean biCam = false;

    static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DocGia() {}
    public DocGia(String id, LocalDate ngayDangKy) {
        this.idDocGia = id;
        this.ngayDangKy = ngayDangKy;
    }

    public String getIdDocGia() { return idDocGia; }
    public void setIdDocGia(String v) { idDocGia = v; }
    public LocalDate getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(LocalDate v) { ngayDangKy = v; }
    public LocalDate getNgayKetThucCam() { return ngayKetThucCam; }
    public void setNgayKetThucCam(LocalDate v) { ngayKetThucCam = v; }
    public boolean isDangBiCam() { return biCam; }

    public void camVoThoiHan() {
        biCam = true; trangThai = false;
        ngayBatDauCam = LocalDate.now(); ngayKetThucCam = null;
        System.out.println("Da cam doc gia " + idDocGia + " vo thoi han.");
    }

    public void camCoThoiHan(LocalDate ngayKetThuc) {
        biCam = true; trangThai = false;
        ngayBatDauCam = LocalDate.now(); ngayKetThucCam = ngayKetThuc;
        System.out.println("Da cam doc gia " + idDocGia + " den " + ngayKetThuc.format(FMT));
    }

    public void moCam() {
        biCam = false; trangThai = true;
        ngayBatDauCam = null; ngayKetThucCam = null;
        System.out.println("Da mo cam cho doc gia " + idDocGia);
    }

    public void capNhatTrangThai() {
        if (biCam && ngayKetThucCam != null && LocalDate.now().isAfter(ngayKetThucCam))
            moCam();
    }

    public String getTrangThai() {
        if (!biCam) return "Hoat dong";
        if (ngayKetThucCam == null) return "Bi cam (vo thoi han)";
        return "Bi cam den " + ngayKetThucCam.format(FMT);
    }

    @Override
    public String getRole() { return "Doc gia"; }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("ID: "); idDocGia = sc.nextLine();
        System.out.print("Ho ten: "); hoTen = sc.nextLine();
        System.out.print("Nam sinh: "); namSinh = Integer.parseInt(sc.nextLine());
        System.out.print("Gioi tinh: "); gioiTinh = sc.nextLine();
        System.out.print("Dia chi: "); diaChi = sc.nextLine();
        System.out.print("SDT: "); SDT = sc.nextLine();
        System.out.print("Email: "); email = sc.nextLine();
        ngayDangKy = LocalDate.now();
    }

    @Override
    public void xuat() {
        System.out.printf("%-8s | %-20s | %d | %-6s | %-10s | %-25s | %s%n",
            idDocGia, hoTen, namSinh, gioiTinh, SDT, email, getTrangThai());
    }

    public String toCSV() {
        return String.join(",", idDocGia, hoTen, String.valueOf(namSinh), gioiTinh,
            diaChi, String.valueOf(SDT), email,
            ngayDangKy != null ? ngayDangKy.format(FMT) : "",
            String.valueOf(biCam),
            ngayBatDauCam != null ? ngayBatDauCam.format(FMT) : "",
            ngayKetThucCam != null ? ngayKetThucCam.format(FMT) : "");
    }

    public static DocGia fromCSV(String line) {
        String[] p = line.split(",", -1);
        DocGia dg = new DocGia();
        dg.idDocGia = p[0]; dg.hoTen = p[1];
        dg.namSinh = Integer.parseInt(p[2]); dg.gioiTinh = p[3];
        dg.diaChi = p[4]; dg.SDT = p[5]; dg.email = p[6];
        dg.ngayDangKy     = p[7].isEmpty()  ? null : LocalDate.parse(p[7],  FMT);
        dg.biCam          = Boolean.parseBoolean(p[8]);
        dg.ngayBatDauCam  = p[9].isEmpty()  ? null : LocalDate.parse(p[9],  FMT);
        dg.ngayKetThucCam = p[10].isEmpty() ? null : LocalDate.parse(p[10], FMT);
        dg.trangThai = !dg.biCam;
        return dg;
    }
}

// ===================== QuanLyDocGia (non-public) =====================
class QuanLyDocGia implements IQuanly {
    private ArrayList<DocGia> ds = new ArrayList<>();
    private static final String FILE = "docgia.csv";

    @Override
    public void luuFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            bw.write("id,hoTen,namSinh,gioiTinh,diaChi,SDT,email,ngayDangKy,biCam,ngayBatDauCam,ngayKetThucCam");
            bw.newLine();
            for (DocGia dg : ds) { bw.write(dg.toCSV()); bw.newLine(); }
        } catch (IOException e) { System.out.println("Loi luu file: " + e.getMessage()); }
    }

    @Override
    public void docFile() {
        ds.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line; boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                if (!line.trim().isEmpty()) {
                    DocGia dg = DocGia.fromCSV(line);
                    dg.capNhatTrangThai();
                    ds.add(dg);
                }
            }
        } catch (IOException e) { System.out.println("Chua co file, bat dau moi."); }
    }

    public DocGia timTheoID(String id) {
        return ds.stream().filter(dg -> dg.getIdDocGia().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public void camDocGiaVoThoiHan(String id) {
        DocGia dg = timTheoID(id);
        if (dg == null) { System.out.println("Khong tim thay ID: " + id); return; }
        dg.camVoThoiHan(); luuFile();
    }

    public void camDocGiaCoThoiHan(String id, LocalDate ngayKetThuc) {
        DocGia dg = timTheoID(id);
        if (dg == null) { System.out.println("Khong tim thay ID: " + id); return; }
        dg.camCoThoiHan(ngayKetThuc); luuFile();
    }

    public void moCamDocGia(String id) {
        DocGia dg = timTheoID(id);
        if (dg == null) { System.out.println("Khong tim thay ID: " + id); return; }
        dg.moCam(); luuFile();
    }

    public void sapXepTheoTen() {
        ds.sort(Comparator.comparing(DocGia::getHoTen, String.CASE_INSENSITIVE_ORDER));
    }

    public void sapXepTheoNgayDangKy() {
        ds.sort(Comparator.comparing(dg -> dg.getNgayDangKy() != null ? dg.getNgayDangKy() : LocalDate.MIN));
    }

    public void sapXepTheoNgayKetThucCam() {
        ds.sort(Comparator.comparing(dg -> dg.getNgayKetThucCam() != null ? dg.getNgayKetThucCam() : LocalDate.MAX));
    }

    public List<DocGia> locVaSapXep(String loc, String sapXep) {
        List<DocGia> kq = ds.stream().filter(dg -> switch (loc.toLowerCase()) {
            case "hoatdong" -> !dg.isDangBiCam();
            case "bicam"    ->  dg.isDangBiCam();
            default         -> true;
        }).collect(Collectors.toList());

        switch (sapXep.toLowerCase()) {
            case "ten"            -> kq.sort(Comparator.comparing(DocGia::getHoTen, String.CASE_INSENSITIVE_ORDER));
            case "ngaydangky"     -> kq.sort(Comparator.comparing(dg -> dg.getNgayDangKy() != null ? dg.getNgayDangKy() : LocalDate.MIN));
            case "ngayketthuccam" -> kq.sort(Comparator.comparing(dg -> dg.getNgayKetThucCam() != null ? dg.getNgayKetThucCam() : LocalDate.MAX));
        }
        return kq;
    }

    public void hienThiDanhSach() { hienThiDanhSach(ds); }

    public void hienThiDanhSach(List<DocGia> list) {
        if (list.isEmpty()) { System.out.println("Danh sach trong."); return; }
        System.out.printf("%-8s | %-20s | %-4s | %-6s | %-10s | %-25s | %s%n",
            "ID", "Ho Ten", "NS", "GT", "SDT", "Email", "Trang Thai");
        System.out.println("-".repeat(100));
        for (DocGia dg : list) dg.xuat();
    }

    public void themDocGia(DocGia dg) {
        if (timTheoID(dg.getIdDocGia()) != null) { System.out.println("ID da ton tai!"); return; }
        ds.add(dg); luuFile();
        System.out.println("Da them: " + dg.getHoTen());
    }

    public ArrayList<DocGia> getDanhSach() { return ds; }

    @Override
    public void menu(Scanner sc) {
        while (true) {
            System.out.println("\n====== Quan Ly Doc Gia ======");
            System.out.println("1. Them doc gia");
            System.out.println("2. Cam doc gia vo thoi han");
            System.out.println("3. Cam doc gia co thoi han");
            System.out.println("4. Mo cam doc gia");
            System.out.println("5. Hien thi danh sach");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            
            int choice=-1;
            try
            {
                choice=Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("vui long chi nhap so nguyen");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    DocGia dg = new DocGia();
                    dg.nhap(sc);
                    themDocGia(dg);
                }
                case 2 -> {
                    System.out.print("Nhap ID doc gia can cam vo thoi han: ");
                    String id = sc.nextLine();
                    camDocGiaVoThoiHan(id);
                }
                case 3 -> {
                    System.out.print("Nhap ID doc gia can cam co thoi han: ");
                    String id = sc.nextLine();
                    LocalDate ngayKetThuc;
                    while (true) {
                        System.out.print("Nhap ngay ket thuc cam (dd/MM/yyyy): ");
                        try {
                            ngayKetThuc = LocalDate.parse(sc.nextLine(), DocGia.FMT);
                            break;
                        } catch (Exception e) {
                            System.out.println("Ngay khong hop le, vui long nhap lai.");
                        }
                    }
                    camDocGiaCoThoiHan(id, ngayKetThuc);
                }
                case 4 -> {
                    System.out.print("Nhap ID doc gia can mo cam: ");
                    String id = sc.nextLine();
                    moCamDocGia(id);
                }
                case 5 -> hienThiDanhSach();
                case 0 -> { System.out.println("Thoat quan ly doc gia."); return; }
                default -> System.out.println("Lua chon khong hop le.");
            }
        }
    }
}

// ===================== Main (public - tên file phải là Main.java) =====================


class ChiTietMuon
{
    Tailieu TL;
    int SoLuong=1;
    LocalDate NgayMuon;
    LocalDate HanTra;
    int SoLuongDaTra=0;
    public ChiTietMuon(Tailieu TL, int SoLuong, LocalDate NgayMuon, LocalDate HanTra)
    {
        this.TL = TL;
        this.SoLuong = SoLuong;
        this.NgayMuon = NgayMuon;
        this.HanTra = HanTra;
        
    }
}
class PhieuMuon
{
    protected String MaPhieuMuon;
    protected DocGia NguoiMuon;
    ArrayList<ChiTietMuon> DanhSachChiTietMuon = new ArrayList<>();
    public PhieuMuon(String MaPhieuMuon, DocGia NguoiMuon, ArrayList<ChiTietMuon> DanhSachChiTietMuon)
    {
        this.MaPhieuMuon = MaPhieuMuon;
        this.NguoiMuon = NguoiMuon;
        this.DanhSachChiTietMuon = DanhSachChiTietMuon;
    }
    public void ThemTaiLieuMuon(Tailieu TL, int SoLuong, LocalDate NgayMuon, LocalDate HanTra)
    {
        if(DanhSachChiTietMuon.size() >= 3)
        {
            System.out.println("Chi duoc muon toi da 3 tai lieu");
            return;
        }
        for (ChiTietMuon chiTietMuon : DanhSachChiTietMuon)
        {
            if (chiTietMuon.TL.getIDTaiLieu().equals(TL.getIDTaiLieu()))
            {
                System.out.println("Tai lieu nay da duoc muon");
                return;
            }
        }
        if(TL.getSoluong()<=0)
        {
            System.out.println("tai lieu nay da het");
            return;
        }
        DanhSachChiTietMuon.add(new ChiTietMuon(TL, SoLuong, NgayMuon, HanTra));
        System.out.println("Muon thanh cong");
        TL.desSoluong(1);
    }
    
    public boolean NhapPhieuMuon(Scanner scanner, QuanLyThuVien quanlyTaiLieu, QuanLyDocGia quanlyDocGia)
    {
        System.out.print("Nhap ma phieu muon: ");
        MaPhieuMuon = scanner.nextLine();

        DocGia docGiaTim = null;
        while (true)
        {
            System.out.println("Nhap ID doc gia: ");
            String idString = scanner.nextLine();
            docGiaTim=quanlyDocGia.timTheoID(idString);
            if (docGiaTim == null)
            {
                System.out.println("khong tim thay doc gia, vui long nhap lai");
                System.out.println("Nhap lai(y/n): ");
                if(scanner.nextLine().equalsIgnoreCase("n"))
                    return false;
                continue;
            }
            docGiaTim.capNhatTrangThai();
            if(docGiaTim.isDangBiCam())
            {
                System.out.println("Doc gia "+docGiaTim.getHoTen()+" dang bi cam!");
                System.out.println("Trang thai"+docGiaTim.getTrangThai());
                return false;
            }

            System.out.println("Doc gia: " + docGiaTim.getHoTen() + "-"+docGiaTim.getTrangThai());
            NguoiMuon = docGiaTim;
            break;
        }
    
        
        while (true)
        {
            System.out.println("\n====== Menu ======");
            System.out.println("1. Them tai lieu muon");
            System.out.println("2. Hoan tat");
            System.out.print("Chon chuc nang: ");

            int choice=-1;
            try
            {
                choice=Integer.parseInt(scanner.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("vui long chi nhap so nguyen");
                continue;
            }

            if(choice == 2)
            {
                if (DanhSachChiTietMuon.size() == 0)
                {
                    System.out.println("Phieu muon phai co it nhat 1 tai lieu");
                    continue;
                }
                break;
            }
            if (choice ==1)
            {
                
                System.out.print("Nhap ten tai lieu: ");
                String tenTaiLieu = scanner.nextLine();
                List<Tailieu> list = quanlyTaiLieu.timSachTheoTen(tenTaiLieu);
                if (list.isEmpty())
                {
                    System.out.println("Khong tim thay tai lieu");
                    continue;
                }
                Tailieu TL = list.get(0);
                LocalDate ngayMuon=null;
                LocalDate hanTra=null;
                while (true) {
                    System.out.print("Nhap ngay muon (dd/mm/yyyy): ");
                    try{
                    ngayMuon= LocalDate.parse(scanner.nextLine(),DocGia.FMT);
                    }
                    catch (Exception e) {
                        System.out.println("Ngay muon khong hop le, vui long nhap lai.");
                        continue;
                    }

                    System.out.print("Nhap han tra (dd/mm/yyyy): ");
                    try{
                    hanTra = LocalDate.parse(scanner.nextLine(), DocGia.FMT);
                    }
                    catch (Exception e) {
                        System.out.println("Han tra khong hop le, vui long nhap lai.");
                        continue;
                    }

                    ThemTaiLieuMuon(TL, 1, ngayMuon, hanTra);
                    break;
                }
            }
            else {System.out.println("vui long chon option 1 hoac 2!");}
        }
        return true; 
    }  
}
class ChiTietTra
{
    Tailieu TL;
    int SoLuong=1;
    LocalDate NgayTra;
    boolean TreHan;
    public ChiTietTra(Tailieu TL, int SoLuong, LocalDate NgayTra, boolean TreHan)
    {
        this.TL = TL;
        this.SoLuong = SoLuong;
        this.NgayTra = NgayTra;
        this.TreHan = TreHan;
    }
}
class PhieuTra
{
    String MaPhieuTra;
    PhieuMuon phieuMuon;
    String tenNguoiTra;
    String TenNguoiUyThac;
    ArrayList<ChiTietTra> DanhSachChiTietTra = new ArrayList<>();
    public PhieuTra(String MaPhieuTra, PhieuMuon phieuMuon)
    {
        this .MaPhieuTra = MaPhieuTra;
        this.phieuMuon = phieuMuon;
    }
    public boolean nhapNguoiTra(Scanner scanner)
    {
        int soLanThu=0;
        final int MAX=3;

        while (true) { 
            soLanThu++;
            System.out.print("Nhap ten nguoi tra (nhap 0 de huy): ");
            tenNguoiTra = scanner.nextLine();

            if(tenNguoiTra.equals("0"))
            {
                System.out.println("da huy viec tao phieu tra");
                return false;
            }
            
            System.out.println("Co nguoi uy thac tra khong? (y/n): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y"))
            {
                System.out.print("Nhap ten nguoi uy thac(aka nguoi muon): ");
                TenNguoiUyThac = scanner.nextLine();
            }
            else 
            {
                TenNguoiUyThac=null;
            }
            String tenSoSanh;
            if(TenNguoiUyThac != null && !TenNguoiUyThac.isEmpty())
            {
                tenSoSanh = TenNguoiUyThac;
            }
            else
            {
                tenSoSanh = tenNguoiTra;
            }
            if (tenSoSanh.equalsIgnoreCase(phieuMuon.NguoiMuon.getHoTen()))
            {
                System.out.println("Nguoi tra hop le");
                return true;
            }
            System.out.println("Nguoi tra khong hop le!");
            System.out.println("ban con: "+(MAX-soLanThu)+" luot thu");
            if (soLanThu>=MAX)
            {
                System.out.println("Qua so lan nhap, huy phieu tra");
                return false;
            }

            
        }
    }
    public void traTaiLieu(Tailieu TL, LocalDate NgayTra)
    {
        for (ChiTietMuon ctm: phieuMuon.DanhSachChiTietMuon)
        {
            if (ctm.TL.getIDTaiLieu().equals(TL.getIDTaiLieu()))
            {
                if (ctm.SoLuongDaTra >= ctm.SoLuong)
                {
                    System.out.println("Tai lieu da duoc tra het");
                    return;
                }
                ctm.SoLuongDaTra++;
                boolean treHan = NgayTra.isAfter(ctm.HanTra);
                DanhSachChiTietTra.add(new ChiTietTra(TL, 1, NgayTra, treHan));
                if (treHan)
                {
                    System.out.println("Tra tai lieu tre han! tien phat: " + (long)TienPhat.tinhtien(NgayTra, ctm.HanTra)+"VND");
                }
                else
                {
                    System.out.println("Tra tai lieu thanh cong");
                }
                TL.accSoluong(1);
                return;
            }
        }
            
            System.out.println("Tai lieu khong tim thay trong phieu muon");
    }
}
class TienPhat
{
    public static double tinhtien(LocalDate NgayTra, LocalDate HanTra)
    {
       if(NgayTra.isAfter(HanTra))
       {
        long soNgayTreHan = ChronoUnit.DAYS.between(HanTra, NgayTra);
        return soNgayTreHan * 5000;
       }
       return 0;
    }
}
class QuanLyPhieuMuon implements IQuanly
{
    private static final String FILE_PM="phieumuon.csv";
    ArrayList <PhieuMuon> DanhSachPhieuMuon = new ArrayList<>();
    private QuanLyThuVien qltv;
    private QuanLyDocGia qldg;
    public QuanLyPhieuMuon(QuanLyThuVien qltv, QuanLyDocGia qldg)
    {
        this.qltv = qltv;
        this.qldg = qldg;
    }

    @Override
    public void luuFile()
    {
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FILE_PM)))
        {
            for(PhieuMuon pm: DanhSachPhieuMuon)
            {
                bw.write("PM,"+pm.MaPhieuMuon + "," +pm.NguoiMuon.idDocGia + "," + pm.NguoiMuon.getHoTen());
                bw.newLine();
                for(ChiTietMuon ct: pm.DanhSachChiTietMuon)
                {
                    bw.write("CT,"+ct.TL.getIDTaiLieu()+"," + ct.SoLuong + "," + ct.NgayMuon + "," + ct.HanTra + "," + ct.SoLuongDaTra);
                    bw.newLine();
                }
            }
        }catch (IOException e)
        {
            System.out.println("loi luu phieu muon: "+e.getMessage());
        }
    }

    @Override
    public void docFile()
    {
        DanhSachPhieuMuon.clear();
        try(BufferedReader br=new BufferedReader(new FileReader(FILE_PM)))
            {
                String line;
                PhieuMuon pmhientai=null;
                while ((line=br.readLine())!=null)
                {
                    if(line.trim().isEmpty()) continue;
                    String [] p=line.split(",",-1);
                    if(p[0].trim().equals("PM"))
                    {
                        DocGia dg=qldg.timTheoID(p[2]);
                        if(dg==null)
                        { 
                            dg=new DocGia();
                            dg.setIdDocGia(p[2]);
                            dg.setHoTen(p[3]);
                        }
                           
                        pmhientai=new PhieuMuon(p[1], dg, new ArrayList<>());
                        DanhSachPhieuMuon.add(pmhientai);
                    }
                    else if (p[0].trim().equals("CT")&&pmhientai!=null)
                    {
                        String idTL=p[1].trim();
                        Tailieu TL=qltv.getThuVien().getDsTaiLieu().stream().filter(t->t.getIDTaiLieu().equals(idTL)).findFirst().orElse(null);
                        if (TL != null)
                        {
                            ChiTietMuon ct=new ChiTietMuon(TL, Integer.parseInt(p[2]), LocalDate.parse(p[3]), LocalDate.parse(p[4]));
                            ct.SoLuongDaTra=Integer.parseInt(p[5]);
                            pmhientai.DanhSachChiTietMuon.add(ct);
                        }
                        else 
                        {
                            System.out.println("tai lieu ID "+idTL+" khong con ton tai");
                        }
                    }
                }
            }
        catch (IOException e)        {
            System.out.println("Chua co file phieu muon, bat dau moi.");
        }
    }
    public void themPhieuMuon(PhieuMuon phieuMuon)
    {
        for (PhieuMuon pm: DanhSachPhieuMuon)
        {
            if (pm.MaPhieuMuon.equals(phieuMuon.MaPhieuMuon))
            {
                System.out.println("ma phieu muon da ton tai");
                return;
            }
        }
        DanhSachPhieuMuon.add(phieuMuon);
    }
    public PhieuMuon timPhieuMuonTheoMa(String MaPhieuMuon)
    {
        for (PhieuMuon phieuMuon : DanhSachPhieuMuon)
        {
            if (phieuMuon.MaPhieuMuon.equals(MaPhieuMuon))
            {
                return phieuMuon;
            }
        }
        System.out.println("Khong tim thay phieu muon voi ma: " + MaPhieuMuon);
        return null;
    }
    public PhieuMuon timPhieuMuonTheoTenNguoiMuon(String tenNguoiMuon)
    {
        for (PhieuMuon phieuMuon : DanhSachPhieuMuon)
        {
            if (phieuMuon.NguoiMuon.getHoTen().equalsIgnoreCase(tenNguoiMuon))
            {
                return phieuMuon;
            }
        }
        System.out.println("Khong tim thay phieu muon voi ten nguoi muon: " + tenNguoiMuon);
        return null;
    }
    public void HienThiTatCaPhieuMuon()
    {
        for (PhieuMuon phieuMuon : DanhSachPhieuMuon)
        {
            System.out.println("Ma Phieu Muon: " + phieuMuon.MaPhieuMuon);
            System.out.println("Nguoi Muon: " + phieuMuon.NguoiMuon.getHoTen());
            System.out.println("Danh Sach Tai Lieu Muon:");
            for (ChiTietMuon chiTietMuon : phieuMuon.DanhSachChiTietMuon)
            {
                System.out.println("- " + chiTietMuon.TL.getTitle() + " (Ngay Muon: " + chiTietMuon.NgayMuon + ", Han Tra: " + chiTietMuon.HanTra + ")");
            }
            System.out.println();
        }
    }

    @Override
    public void menu (Scanner sc)
    {
        while (true)
        {
            System.out.println("===== Menu Quan Ly Phieu Muon =====");
            System.out.println("1. Them Phieu Muon");
            System.out.println("2. Tim Phieu Muon Theo Ma");
            System.out.println("3. Tim Phieu Muon Theo Ten Nguoi Muon");
            System.out.println("4. Hien Thi Tat Ca Phieu Muon");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice=-1;
            try
            {
                choice=Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("vui long chi nhap so nguyen");
                continue;
            }

            switch (choice)
            {
                case 1:
                    PhieuMuon phieuMuon = new PhieuMuon("", null, new ArrayList<>());
                    boolean ok=phieuMuon.NhapPhieuMuon(sc, this.qltv, this.qldg);
                    if(ok)
                    {   themPhieuMuon(phieuMuon);
                        System.out.println("tao phieu thanh cong");
                    }
                    else
                    {
                        System.out.println("huy phieu muon!");
                    }
                    break;
                case 2:
                    System.out.print("Nhap ma phieu muon: ");
                    String maPhieuMuon = sc.nextLine();
                    PhieuMuon pmTheoMa = timPhieuMuonTheoMa(maPhieuMuon);
                    if (pmTheoMa != null)
                    {
                        System.out.println("Ma Phieu Muon: " + pmTheoMa.MaPhieuMuon);
                        System.out.println("Nguoi Muon: " + pmTheoMa.NguoiMuon.getHoTen());
                        System.out.println("Danh Sach Tai Lieu Muon:");
                        for (ChiTietMuon chiTietMuon : pmTheoMa.DanhSachChiTietMuon)
                        {
                            System.out.println("- " + chiTietMuon.TL.getTitle() + " (Ngay Muon: " + chiTietMuon.NgayMuon + ", Han Tra: " + chiTietMuon.HanTra + ")");
                        }
                    }
                    break;
                case 3:
                    System.out.print("Nhap ten nguoi muon: ");
                    String tenNguoiMuon = sc.nextLine();
                    PhieuMuon pmTheoTen = timPhieuMuonTheoTenNguoiMuon(tenNguoiMuon);
                    if (pmTheoTen != null)
                    {
                        System.out.println("Ma Phieu Muon: " + pmTheoTen.MaPhieuMuon);
                        System.out.println("Nguoi Muon: " + pmTheoTen.NguoiMuon.getHoTen());
                        System.out.println("Danh Sach Tai Lieu Muon:");
                        for (ChiTietMuon chiTietMuon : pmTheoTen.DanhSachChiTietMuon)
                        {
                            System.out.println("- " + chiTietMuon.TL.getTitle() + " (Ngay Muon: " + chiTietMuon.NgayMuon + ", Han Tra: " + chiTietMuon.HanTra + ")");
                        }
                    }
                    break;
                case 4:
                    HienThiTatCaPhieuMuon();
                    break;
                case 0:
                    System.out.println("Thoat khoi chuong trinh.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }
}
class QuanLyPhieuTra implements IQuanly
{   private QuanLyPhieuMuon qlpm;
    private static final String FILE_PT="phieutra.csv";
    public QuanLyPhieuTra(QuanLyPhieuMuon qlpm)
    {
        this.qlpm = qlpm;
    }

    @Override
   public void luuFile()
   {
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FILE_PT)))
        {
            for(PhieuTra pt: DanhSachPhieuTra)
            {
                String nguoiUyThac=(pt.TenNguoiUyThac!=null)?pt.TenNguoiUyThac:"null";
                bw.write("PT," + pt.MaPhieuTra + "," + pt.phieuMuon.MaPhieuMuon + "," + pt.tenNguoiTra + "," + nguoiUyThac);
                bw.newLine();
                for (ChiTietTra ct: pt.DanhSachChiTietTra)
                {
                    bw.write("CT,"+ct.TL.getIDTaiLieu()+","+ct.SoLuong+","+ct.NgayTra+","+ct.TreHan);
                    bw.newLine();
                }
            }
        }
        catch(IOException e){System.out.println("loi luu phieu tra: "+e.getMessage());}
   }

   @Override
   public void docFile()
   {
        DanhSachPhieuTra.clear();
        try (BufferedReader br= new BufferedReader (new FileReader (FILE_PT)))
        {
            String line;
            PhieuTra ptrhientai=null;
            while ((line=br.readLine())!=null)
            {
                if(line.trim().isEmpty()) continue;
                String [] p = line.split(",",-1);
                if (p[0].trim().equals("PT"))
                {
                    String maPhieuTra = p[1];
                    String maPhieuMuon =p[2];
                    PhieuMuon pm=qlpm.timPhieuMuonTheoMa(maPhieuMuon);
                    if(pm!=null)
                    {
                        ptrhientai=new PhieuTra(maPhieuTra, pm);
                        ptrhientai.tenNguoiTra=p[3];
                        ptrhientai.TenNguoiUyThac=p[4];
                        DanhSachPhieuTra.add(ptrhientai);
                    }
                    else
                    {
                        ptrhientai=null;
                    }
                }
                else if(p[0].trim().equals("CT")&&ptrhientai!=null)
                {
                    String idTL=p[1];
                    Tailieu tl=ptrhientai.phieuMuon.DanhSachChiTietMuon.stream().map(ctm -> ctm.TL).filter(t->t.getIDTaiLieu().equals(idTL)).findFirst().orElse(null);

                    if(tl!=null)
                    {
                        int soLuong = Integer.parseInt(p[2]);
                        LocalDate ngayTra=LocalDate.parse(p[3]);
                        boolean treHan=Boolean.parseBoolean(p[4]);

                        ptrhientai.DanhSachChiTietTra.add(new ChiTietTra(tl, soLuong, ngayTra, treHan));
                    }
                    
                }
            }

        }
        catch (IOException e)
        {
            System.out.println("chua co file phieu tra!");
        }
   }
    
    ArrayList<PhieuTra> DanhSachPhieuTra = new ArrayList<>();
    public void themPhieuTra(PhieuTra phieuTra)
    {
        for (PhieuTra pt : DanhSachPhieuTra )
        {
            if(pt.MaPhieuTra.equals(phieuTra.MaPhieuTra))
            {
                System.out.println("ma phieu tra da ton tai");
                return;
            }
        }
        DanhSachPhieuTra.add(phieuTra);
    }
    public PhieuTra timPhieuTraTheoMa(String MaPhieuTra)
    {
        for (PhieuTra phieuTra : DanhSachPhieuTra)
        {
            if (phieuTra.MaPhieuTra.equals(MaPhieuTra))
            {
                return phieuTra;
            }
        }
        System.out.println("Khong tim thay phieu tra voi ma: " + MaPhieuTra);
        return null;
    }
    public PhieuTra timPhieuTraTheoTenNguoiTra(String tenNguoiTra)
    {
        for (PhieuTra phieuTra : DanhSachPhieuTra)
        {
            if (phieuTra.tenNguoiTra.equalsIgnoreCase(tenNguoiTra))
            {
                return phieuTra;
            }
        }
        System.out.println("Khong tim thay phieu tra voi ten nguoi tra: " + tenNguoiTra);
        return null;
    }
    public void HienThiTatCaPhieuTra()
    {
        for (PhieuTra phieuTra : DanhSachPhieuTra)
        {
            System.out.println("Ma Phieu Tra: " + phieuTra.MaPhieuTra);
            System.out.println("Nguoi Tra: " + phieuTra.tenNguoiTra);
            if (phieuTra.TenNguoiUyThac != null)
            {
                System.out.println("Nguoi Uy Thac: " + phieuTra.TenNguoiUyThac);
            }
            System.out.println("Danh Sach Tai Lieu Tra:");
            for (ChiTietTra chiTietTra : phieuTra.DanhSachChiTietTra)
            {
                System.out.println("- " + chiTietTra.TL.getTitle() + " (Ngay Tra: " + chiTietTra.NgayTra + ", Tre Han: " + chiTietTra.TreHan + ")");
            }
            System.out.println();
        }
    }

    @Override
    public void menu (Scanner sc)
    {
        while (true)
        {
            System.out.println("===== Menu Quan Ly Phieu Tra =====");
            System.out.println("1. Them Phieu Tra");
            System.out.println("2. Tim Phieu Tra Theo Ma");
            System.out.println("3. Tim Phieu Tra Theo Ten Nguoi Tra");
            System.out.println("4. Hien Thi Tat Ca Phieu Tra");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
           
            int choice=-1;
            try
            {
                choice=Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("vui long chi nhap so nguyen");
                continue;
            }

            switch (choice)
            {
                case 1:
                    System.out.print("Nhap ma phieu muon: ");
                    String maPhieuMuon = sc.nextLine();
                    PhieuMuon phieuMuon =qlpm.timPhieuMuonTheoMa(maPhieuMuon);
                    if (phieuMuon == null)
                    {
                        System.out.println("Khong tim thay phieu muon voi ma: " + maPhieuMuon);
                        break;
                    }
                    System.out.print("Nhap ma phieu tra: ");
                    String maPhieuTra = sc.nextLine();
                    PhieuTra phieuTra = new PhieuTra(maPhieuTra, phieuMuon);
                    boolean hopLe=phieuTra.nhapNguoiTra(sc);
                    if(!hopLe)
                        break;
                    
                    while (true)
                    {
                        System.out.println("Chon tai lieu de tra: ");
                        boolean ConTaiLieu=false;
                        for (ChiTietMuon ct : phieuMuon.DanhSachChiTietMuon)
                        {
                            if(ct.SoLuongDaTra<ct.SoLuong)
                            {
                                System.out.println("- [" +ct.TL.getIDTaiLieu() + "] " + ct.TL.getTitle()+ " (Han Tra: " + ct.HanTra);
                                ConTaiLieu=true;
                            }
                            
                        }
                        if (!ConTaiLieu)
                        {
                            System.out.println("Khong con tai lieu nao de tra.");
                            break;
                        }
                        System.out.println("0. Hoan tat");
                        System.out.print("Nhap ID tai lieu de tra: ");
                        String idTL = sc.nextLine();
                        if (idTL.equals("0"))
                        {
                            break;
                        }
                         Tailieu tlcantra=null;
                         for (ChiTietMuon ct: phieuMuon.DanhSachChiTietMuon)
                        {
                            
                            if (ct.TL.getIDTaiLieu().equalsIgnoreCase(idTL))
                            {
                                if(ct.SoLuongDaTra>=ct.SoLuong)
                                {
                                    System.out.println("Tai lieu nay da duoc tra het");
                                    tlcantra=null;
                                }
                                else
                                    {tlcantra=ct.TL;}
                                break;
                            }
                        }
                        if (tlcantra == null)
                        {
                            System.out.println("Tai lieu khong hop le, vui long chon lai.");
                            continue;
                        }
                        LocalDate NgayTra;
                        while (true)
                        {
                            System.out.println("Nhap ngay tra (dd/mm/yyyy): ");
                            try
                            {
                                NgayTra = LocalDate.parse(sc.nextLine(), DocGia.FMT);
                                break;
                            }
                            catch (Exception e)
                            {
                                System.out.println("Ngay tra khong hop le, vui long nhap lai.");
                            }
                        }
                        phieuTra.traTaiLieu(tlcantra, NgayTra);
                    }
                    themPhieuTra(phieuTra);
                    System.out.println("Da them phieu tra voi ma: " + maPhieuTra);
                    break;
                case 2:
                    System.out.print("Nhap ma phieu tra: ");
                    String maPhieuTra2 = sc.nextLine();
                    PhieuTra ptTheoMa = timPhieuTraTheoMa(maPhieuTra2);
                    if(ptTheoMa!=null)
                    {
                        System.out.println ("Ma Phieu Tra: "+ptTheoMa.MaPhieuTra);
                        System.out.println("Nguoi tra: "+ptTheoMa.tenNguoiTra);
                        for (ChiTietTra ct: ptTheoMa.DanhSachChiTietTra)
                            System.out.println("- " + ct.TL.getTitle() + " (Ngay tra: " + ct.NgayTra + ")");

                    }
                    break;
                case 3:
                    System.out.print("Nhap ten nguoi tra: ");
                    String tenNguoiTra = sc.nextLine();
                    PhieuTra ptTheoTen = timPhieuTraTheoTenNguoiTra(tenNguoiTra);
                    if(ptTheoTen!=null)
                    {
                        System.out.println("Ma Phieu Tra: "+ptTheoTen.MaPhieuTra);
                        System.out.println("Nguoi Tra: "+ptTheoTen.tenNguoiTra);
                    for (ChiTietTra ct: ptTheoTen.DanhSachChiTietTra)
                            System.out.println("- " + ct.TL.getTitle() + " (Ngay tra: " + ct.NgayTra + ")");

                    }
                    break;
                case 4:
                    HienThiTatCaPhieuTra();
                    break;
                case 0:
                    System.out.println("Thoat khoi chuong trinh.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }
}
class ChamCong {
    private String maChamCong;
    private ThuThu thuThu;
    private LocalDate ngay;
    private LocalTime gioVao;
    private LocalTime gioRa;
    private String trangThai; // "DiLam", "VangMat", "NghiPhep"

    private static final DateTimeFormatter FMT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_TIME = DateTimeFormatter.ofPattern("HH:mm");

    public ChamCong(String maChamCong, ThuThu thuThu, LocalDate ngay, LocalTime gioVao, String trangThai) {
        this.maChamCong = maChamCong;
        this.thuThu = thuThu;
        this.ngay = ngay;
        this.gioVao = gioVao;
        this.trangThai = trangThai;
    }

    // --- Getters ---
    public String getMaChamCong()  { return maChamCong; }
    public ThuThu getThuThu()      { return thuThu; }
    public LocalDate getNgay()     { return ngay; }
    public LocalTime getGioVao()   { return gioVao; }
    public LocalTime getGioRa()    { return gioRa; }
    public String getTrangThai()   { return trangThai; }

    // --- Setters ---
    public void setGioRa(LocalTime gioRa)         { this.gioRa = gioRa; }
    public void setTrangThai(String trangThai)    { this.trangThai = trangThai; }

    /** Tính số giờ làm việc trong ngày */
    public double tinhSoGioLam() {
        if (gioVao == null || gioRa == null) return 0;
        long phut = java.time.Duration.between(gioVao, gioRa).toMinutes();
        return phut / 60.0;
    }

    @Override
    public String toString() {
        String ra = (gioRa != null) ? gioRa.format(FMT_TIME) : "--:--";
        return String.format("ChamCong[%s | %s | %s | Vào:%s Ra:%s | %.1fh | %s]",
                maChamCong,
                thuThu.getHoTen(),
                ngay.format(FMT_DATE),
                gioVao.format(FMT_TIME),
                ra,
                tinhSoGioLam(),
                trangThai);
    }
}
class DangNhapService {
    private List<TaiKhoan> danhSachTaiKhoan;
    private TaiKhoan taiKhoanHienTai;

    public DangNhapService() {
        danhSachTaiKhoan = new ArrayList<>();
        taiKhoanHienTai = null;
        khoiTaoDuLieuMau();
    }

    private void khoiTaoDuLieuMau() {
        danhSachTaiKhoan.add(new TaiKhoan("admin", "admin123", "Admin"));
        danhSachTaiKhoan.add(new TaiKhoan("thuthu1", "tt123", "ThuThu"));
        danhSachTaiKhoan.add(new TaiKhoan("thuthu2", "tt456", "ThuThu"));
    }

    public boolean dangNhap(String tenDangNhap, String matKhau) {
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getTenDangNhap().equals(tenDangNhap)
                    && tk.getMatKhau().equals(matKhau)
                    && tk.isDangHoatDong()) {
                taiKhoanHienTai = tk;
                System.out.println("✅ Đang nhap thanh cong! Xin chao " + tk.getTenDangNhap()
                        + " [" + tk.getVaiTro() + "]");
                return true;
            }
        }
        System.out.println("❌ Ten đang nhap hoac mat khau khong dung!");
        return false;
    }

    public void dangXuat() {
        if (taiKhoanHienTai != null) {
            System.out.println("👋 Da dang xuat tai khoan: " + taiKhoanHienTai.getTenDangNhap());
            taiKhoanHienTai = null;
        } else {
            System.out.println("⚠️ Chua co tai khoan nao dang dang nhap.");
        }
    }

    public boolean daDangNhap() {
        return taiKhoanHienTai != null;
    }

    public TaiKhoan getTaiKhoanHienTai() {
        return taiKhoanHienTai;
    }

    /**
     * Kiểm tra quyền. Chỉ Admin mới được thực hiện một số thao tác.
     */
    public boolean kiemTraQuyen(String vaiTroYeuCau) {
        if (!daDangNhap()) {
            System.out.println("⚠️ Ban chua dang nhap!");
            return false;
        }
        if (!taiKhoanHienTai.getVaiTro().equalsIgnoreCase(vaiTroYeuCau)) {
            System.out.println("🚫 Ban khong co quyen thuc hien chuc nang nay! (Yeu cau: " + vaiTroYeuCau + ")");
            return false;
        }
        return true;
    }

    /** Phân quyền linh hoạt: Admin hoặc ThuThu */
    public boolean kiemTraQuyenHoac(String vaiTro1, String vaiTro2) {
        if (!daDangNhap()) {
            System.out.println("⚠️ Ban chua dang nhap!");
            return false;
        }
        String vt = taiKhoanHienTai.getVaiTro();
        return vt.equalsIgnoreCase(vaiTro1) || vt.equalsIgnoreCase(vaiTro2);
    }

    public List<TaiKhoan> getDanhSachTaiKhoan() {
        return danhSachTaiKhoan;
    }
}
class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro; // "Admin" hoặc "ThuThu"
    private boolean dangHoatDong;

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.dangHoatDong = true;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau()     { return matKhau; }
    public String getVaiTro()      { return vaiTro; }
    public boolean isDangHoatDong(){ return dangHoatDong; }

    public void setDangHoatDong(boolean trangThai) { this.dangHoatDong = trangThai; }

    public boolean laAdmin()   { return "Admin".equalsIgnoreCase(vaiTro); }
    public boolean laThuThu() { return "ThuThu".equalsIgnoreCase(vaiTro); }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", vaiTro, tenDangNhap, dangHoatDong ? "Active" : "Inactive");
    }
}
public class App 
{
    private static DangNhapService dangNhapService = new DangNhapService();
    private static QuanLyThuThu quanLyThuThu = new QuanLyThuThu();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   HE THONG QUAN LY THU VIEN              ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Thêm dữ liệu mẫu thủ thư
        khoiTaoDuLieuMau();

        boolean tiepTuc = true;
        while (tiepTuc) {
            if (!dangNhapService.daDangNhap()) {
                tiepTuc = menuDangNhap();
            } else {
                TaiKhoan tk = dangNhapService.getTaiKhoanHienTai();
                if (tk.laAdmin()) {
                    tiepTuc = menuAdmin();
                } else {
                    tiepTuc = menuThuThu();
                }
            }
        }

        System.out.println("\n👋 Hen gap lai! Tam biet.");
        scanner.close();
    }

    // ===================== MENU ĐĂNG NHẬP =====================
    private static boolean menuDangNhap() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│         DANG NHAP            │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│ 1. Dang nhap                 │");
        System.out.println("│ 0. Thoat                     │");
        System.out.println("└─────────────────────────────┘");
        System.out.print("Chon: ");
        String chon = scanner.nextLine().trim();

        switch (chon) {
            case "1":
                System.out.print("Ten dang nhap: ");
                String ten = scanner.nextLine().trim();
                System.out.print("Mat khau    : ");
                String mk = scanner.nextLine().trim();
                dangNhapService.dangNhap(ten, mk);
                return true;
            case "0":
                return false;
            default:
                System.out.println("⚠️ Lua chon khong hop le.");
                return true;
        }
    }

    // ===================== MENU ADMIN =====================
    private static boolean menuAdmin() {
        TaiKhoan tk = dangNhapService.getTaiKhoanHienTai();
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.printf( "│ ADMIN: %-30s│%n", tk.getTenDangNhap());
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│ 1. Xem danh sach thu thu             │");
        System.out.println("│ 2. Them thu thu                      │");
        System.out.println("│ 3. Xoa thu thu                       │");
        System.out.println("│ 4. Thong ke cham cong theo thang     │");
        System.out.println("│ 5. Thong ke tong quat                │");
        System.out.println("│ 6. Quan ly nghiep vu thu vien        │");
        System.out.println("│ 7. Dang xuat                         │");
        System.out.println("│ 0. Thoat                             │");
        System.out.println("└──────────────────────────────────────┘");
        System.out.print("Chon: ");
        String chon = scanner.nextLine().trim();

        switch (chon) {
            case "1":
                quanLyThuThu.hienThiDanhSach();
                break;
            case "2":
                themThuThu();
                break;
            case "3":
                xoaThuThu();
                break;
            case "4":
                thongKeChamCong();
                break;
            case "5":
                quanLyThuThu.thongKeTongQuat();
                break;
            case "6":
                quanlynghiepvu();
                break;
            case "7":
                dangNhapService.dangXuat();
                break;
            case "0":
                return false;
            default:
                System.out.println("⚠️ Lua chon khong hop le.");
        }
        return true;
    }

    // ===================== MENU THỦ THƯ =====================
    private static boolean menuThuThu() {
        TaiKhoan tk = dangNhapService.getTaiKhoanHienTai();
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.printf( "│ THU THU: %-28s│%n", tk.getTenDangNhap());
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│ 1. Cham cong (Check-in)              │");
        System.out.println("│ 2. Check-out                         │");
        System.out.println("│ 3. Xem thong ke cua toi              │");
        System.out.println("│ 4. Quan ly nghiep vu thu vien        │");
        System.out.println("│ 5. Dang xuat                         │");
        System.out.println("│ 0. Thoat                             │");
        System.out.println("└──────────────────────────────────────┘");
        System.out.print("Chon: ");
        String chon = scanner.nextLine().trim();

        switch (chon) {
            case "1":
                chamCong();
                break;
            case "2":
                checkOut();
                break;
            case "3":
                quanLyThuThu.thongKeTongQuat();
                break;
            case "4":
                quanlynghiepvu();
                break;
            case "5":
                dangNhapService.dangXuat();
                break;
            case "0":
                return false;
            default:
                System.out.println("⚠️ Lua chon khong hop le.");
        }
        return true;
    }

    // ===================== CÁC CHỨC NĂNG PHỤ =====================

    private static void themThuThu() {
        System.out.println("\n➕ THEM THU THU MOI");
        System.out.print("Ho ten       : ");
        String hoTen = scanner.nextLine().trim();
        System.out.print("So dien thoai: ");
        String sdt = scanner.nextLine().trim();
        System.out.print("Email        : ");
        String email = scanner.nextLine().trim();
        System.out.print("Ten dang nhap: ");
        String tenDN = scanner.nextLine().trim();
        System.out.print("Mat khau     : ");
        String mk = scanner.nextLine().trim();

        TaiKhoan tkMoi = new TaiKhoan(tenDN, mk, "ThuThu");
        dangNhapService.getDanhSachTaiKhoan().add(tkMoi);
        quanLyThuThu.themThuThu(hoTen, sdt, email, tkMoi);
    }

    private static void xoaThuThu() {
        System.out.print("Nhap ma thu thu can xoa: ");
        String ma = scanner.nextLine().trim();
        quanLyThuThu.xoaThuThu(ma);
    }

    private static void chamCong() {
        System.out.print("Nhap ma thu thu: ");
        String ma = scanner.nextLine().trim();
        System.out.print("Trang thai (DiLam / NghiPhep / VangMat): ");
        String tt = scanner.nextLine().trim();
        quanLyThuThu.chamCong(ma, tt);
    }

    private static void checkOut() {
        System.out.print("Nhap ma cham cong (CC...): ");
        String ma = scanner.nextLine().trim();
        quanLyThuThu.checkOut(ma);
    }

    private static void thongKeChamCong() {
        System.out.print("Nhap thang (1-12): ");
        int thang = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Nhap nam (vd: 2025): ");
        int nam = Integer.parseInt(scanner.nextLine().trim());
        quanLyThuThu.thongKeChamCongTheoThang(thang, nam);
    }

    private static void khoiTaoDuLieuMau() {
        // Lấy tài khoản mẫu để gắn vào thủ thư
        TaiKhoan tk1 = dangNhapService.getDanhSachTaiKhoan().get(1); // thuthu1
        TaiKhoan tk2 = dangNhapService.getDanhSachTaiKhoan().get(2); // thuthu2
        quanLyThuThu.themThuThu("Nguyen Thi Lan", "0901234567", "lan@library.vn", tk1);
        quanLyThuThu.themThuThu("Tran Van Hung", "0912345678", "hung@library.vn", tk2);
    }
    public static void quanlynghiepvu()
    {        

        QuanLyThuVien qltv=new QuanLyThuVien();
        qltv.docFile();

        QuanLyDocGia qldg=new QuanLyDocGia();
        qldg.docFile();

        QuanLyPhieuMuon qlpm=new QuanLyPhieuMuon(qltv, qldg);
        qlpm.docFile();

        QuanLyPhieuTra qlpt=new QuanLyPhieuTra(qlpm);
        qlpt.docFile();

        while(true)
        {
            System.out.println("===== Menu Quan Ly Thu Vien =====");
            System.out.println("1. Quan Ly Tai Lieu");
            System.out.println("2. Quan Ly Doc Gia");
            System.out.println("3. Quan Ly Phieu Muon");
            System.out.println("4. Quan Ly Phieu Tra");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
           
            int choice=-1;
            try
            {
                choice=Integer.parseInt(scanner.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("vui long chi nhap so nguyen");
                continue;
            }

            switch (choice)
            {
                case 1:
                    qltv.menu(scanner);
                    break;
                case 2:
                    qldg.menu(scanner);
                    break;
                case 3:
                    qlpm.menu(scanner);
                    break;
                case 4:
                    qlpt.menu(scanner);
                    break;
                case 0:
                    qltv.luuFile();
                    qldg.luuFile();
                    qlpm.luuFile();
                    qlpt.luuFile();
                    System.out.println("Thoat khoi chuong trinh.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }
}
