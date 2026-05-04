import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

// ===================== Person (abstract, non-public) =====================
abstract class Person {
    protected String hoTen, gioiTinh, diaChi, email;
    protected int namSinh, SDT;
    protected boolean trangThai = true;

    public Person() {}
    public Person(String hoTen, int namSinh, String gioiTinh, String diaChi, int SDT, String email) {
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
    public int getSDT() { return SDT; }
    public void setSDT(int v) { SDT = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public boolean isTrangThai() { return trangThai; }

    public abstract String getRole();
    public abstract void nhap();
    public abstract void xuat();
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
    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID: "); idDocGia = sc.nextLine();
        System.out.print("Ho ten: "); hoTen = sc.nextLine();
        System.out.print("Nam sinh: "); namSinh = Integer.parseInt(sc.nextLine());
        System.out.print("Gioi tinh: "); gioiTinh = sc.nextLine();
        System.out.print("Dia chi: "); diaChi = sc.nextLine();
        System.out.print("SDT: "); SDT = Integer.parseInt(sc.nextLine());
        System.out.print("Email: "); email = sc.nextLine();
        ngayDangKy = LocalDate.now();
    }

    @Override
    public void xuat() {
        System.out.printf("%-8s | %-20s | %d | %-6s | %-10d | %-25s | %s%n",
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
        dg.diaChi = p[4]; dg.SDT = Integer.parseInt(p[5]); dg.email = p[6];
        dg.ngayDangKy     = p[7].isEmpty()  ? null : LocalDate.parse(p[7],  FMT);
        dg.biCam          = Boolean.parseBoolean(p[8]);
        dg.ngayBatDauCam  = p[9].isEmpty()  ? null : LocalDate.parse(p[9],  FMT);
        dg.ngayKetThucCam = p[10].isEmpty() ? null : LocalDate.parse(p[10], FMT);
        dg.trangThai = !dg.biCam;
        return dg;
    }
}

// ===================== QuanLyDocGia (non-public) =====================
class QuanLyDocGia {
    private ArrayList<DocGia> ds = new ArrayList<>();
    private static final String FILE = "docgia.csv";

    public void luuFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            bw.write("id,hoTen,namSinh,gioiTinh,diaChi,SDT,email,ngayDangKy,biCam,ngayBatDauCam,ngayKetThucCam");
            bw.newLine();
            for (DocGia dg : ds) { bw.write(dg.toCSV()); bw.newLine(); }
        } catch (IOException e) { System.out.println("Loi luu file: " + e.getMessage()); }
    }

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
}

// ===================== Main (public - tên file phải là Main.java) =====================


class ChiTietMuon
{
    Document TL;
    int SoLuong=1;
    LocalDate NgayMuon;
    LocalDate HanTra;
    int SoLuongDaTra=0;
    public ChiTietMuon(Document TL, int SoLuong, LocalDate NgayMuon, LocalDate HanTra)
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
    public void ThemTaiLieuMuon(Document TL, int SoLuong, LocalDate NgayMuon, LocalDate HanTra)
    {
        if(DanhSachChiTietMuon.size() >= 3)
        {
            System.out.println("Chi duoc muon toi da 3 tai lieu");
            return;
        }
        for (ChiTietMuon chiTietMuon : DanhSachChiTietMuon)
        {
            if (chiTietMuon.TL.getMaTaiLieu().equals(TL.getMaTaiLieu()))
            {
                System.out.println("Tai lieu da duoc muon");
                return;
            }
        }
        DanhSachChiTietMuon.add(new ChiTietMuon(TL, SoLuong, NgayMuon, HanTra));
        System.out.println("Muon thanh cong");
    }
    Scanner scanner = new Scanner(System.in);
    public void NhapPhieuMuon(Scanner scanner, quantlyTaiLieu quanlyTaiLieu)
    {
        System.out.print("Nhap ma phieu muon: ");
        MaPhieuMuon = scanner.nextLine();

        System.out.print("Nhap ten nguoi muon: ");
        String tenNguoiMuon = scanner.nextLine();
        NguoiMuon = new DocGia(tenNguoiMuon);
        while (true)
        {
            System.out.println("\n====== Menu ======");
            System.out.println("1. Them tai lieu muon");
            System.out.println("2. Hoan tat");
            System.out.print("Chon chuc nang: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                if(DanhSachChiTietMuon.size() >= 3)
                {
                    System.out.println("Chi duoc muon toi da 3 tai lieu");
                    continue;
                }
                System.out.print("Nhap ten tai lieu: ");
                String tenTaiLieu = scanner.nextLine();
                Document TL = quanlyTaiLieu.TimSachTheoTen(tenTaiLieu);
                if (TL == null)
                {
                    System.out.println("Khong tim thay tai lieu");
                    continue;
                }

                System.out.print("Nhap ngay muon (yyyy-mm-dd): ");
                LocalDate ngayMuon = LocalDate.parse(scanner.nextLine());

                System.out.print("Nhap han tra (yyyy-mm-dd): ");
                LocalDate hanTra = LocalDate.parse(scanner.nextLine());

                ThemTaiLieuMuon(TL, 1, ngayMuon, hanTra);
            }

        }
      
    }
   
}
class ChiTietTra
{
    Document TL;
    int SoLuong=1;
    LocalDate NgayTra;
    boolean TreHan;
    public ChiTietTra(Document TL, int SoLuong, LocalDate NgayTra, boolean TreHan)
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
    public void nhapNguoiTra(Scanner scanner)
    {
        while (true) { 
            
            System.out.print("Nhap ten nguoi tra: ");
            tenNguoiTra = scanner.nextLine();
            
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
            if (tenSoSanh.equalsIgnoreCase(phieuMuon.NguoiMuon.getTenDocGia()))
            {
                System.out.println("Nguoi tra hop le, vui long xac nhan lai:");
                break;
            }
            else
            {
                System.out.println("Nguoi tra khong hop le");
            }
        }
    }
    public void traTaiLieu(Document TL, LocalDate NgayTra)
    {
        for (ChiTietMuon ctm: phieuMuon.DanhSachChiTietMuon)
        {
            if (ctm.TL.getMaTaiLieu().equals(TL.getMaTaiLieu()))
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
                    System.out.println("Tra tai lieu tre han! tien phat la" + TienPhat.tinhtien(NgayTra, ctm.HanTra));
                }
                else
                {
                    System.out.println("Tra tai lieu thanh cong");
                }
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
class QuanLyPhieuMuon
{
    ArrayList <PhieuMuon> DanhSachPhieuMuon = new ArrayList<>();
    public void themPhieuMuon(PhieuMuon phieuMuon)
    {
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
            if (phieuMuon.NguoiMuon.getTenDocGia().equalsIgnoreCase(tenNguoiMuon))
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
            System.out.println("Nguoi Muon: " + phieuMuon.NguoiMuon.getTenDocGia());
            System.out.println("Danh Sach Tai Lieu Muon:");
            for (ChiTietMuon chiTietMuon : phieuMuon.DanhSachChiTietMuon)
            {
                System.out.println("- " + chiTietMuon.TL.getTenTaiLieu() + " (Ngay Muon: " + chiTietMuon.NgayMuon + ", Han Tra: " + chiTietMuon.HanTra + ")");
            }
            System.out.println();
        }
    }
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
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice)
            {
                case 1:
                    PhieuMuon phieuMuon = new PhieuMuon("", null, new ArrayList<>());
                    phieuMuon.NhapPhieuMuon(sc);
                    themPhieuMuon(phieuMuon);
                    break;
                case 2:
                    System.out.print("Nhap ma phieu muon: ");
                    String maPhieuMuon = sc.nextLine();
                    PhieuMuon pmTheoMa = timPhieuMuonTheoMa(maPhieuMuon);
                    if (pmTheoMa != null)
                    {
                        System.out.println("Ma Phieu Muon: " + pmTheoMa.MaPhieuMuon);
                        System.out.println("Nguoi Muon: " + pmTheoMa.NguoiMuon.getTenDocGia());
                        System.out.println("Danh Sach Tai Lieu Muon:");
                        for (ChiTietMuon chiTietMuon : pmTheoMa.DanhSachChiTietMuon)
                        {
                            System.out.println("- " + chiTietMuon.TL.getTenTaiLieu() + " (Ngay Muon: " + chiTietMuon.NgayMuon + ", Han Tra: " + chiTietMuon.HanTra + ")");
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
                        System.out.println("Nguoi Muon: " + pmTheoTen.NguoiMuon.getTenDocGia());
                        System.out.println("Danh Sach Tai Lieu Muon:");
                        for (ChiTietMuon chiTietMuon : pmTheoTen.DanhSachChiTietMuon)
                        {
                            System.out.println("- " + chiTietMuon.TL.getTenTaiLieu() + " (Ngay Muon: " + chiTietMuon.NgayMuon + ", Han Tra: " + chiTietMuon.HanTra + ")");
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
class QuanLyPhieuTra
{
    ArrayList<PhieuTra> DanhSachPhieuTra = new ArrayList<>();
    public void themPhieuTra(PhieuTra phieuTra)
    {
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
                System.out.println("- " + chiTietTra.TL.getTenTaiLieu() + " (Ngay Tra: " + chiTietTra.NgayTra + ", Tre Han: " + chiTietTra.TreHan + ")");
            }
            System.out.println();
        }
    }
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
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice)
            {
                case 1:
                    System.out.print("Nhap ma phieu muon: ");
                    String maPhieuMuon = sc.nextLine();
                    PhieuMuon phieuMuon = new QuanLyPhieuMuon().timPhieuMuonTheoMa(maPhieuMuon);
                    if (phieuMuon == null)
                    {
                        System.out.println("Khong tim thay phieu muon voi ma: " + maPhieuMuon);
                        break;
                    }
                    PhieuTra phieuTra = new PhieuTra("", phieuMuon);
                    phieuTra.nhapNguoiTra(sc);
                    themPhieuTra(phieuTra);
                    break;
                case 2:
                    System.out.print("Nhap ma phieu tra: ");
                    String maPhieuTra = sc.nextLine();
                    PhieuTra ptTheoMa = timPhieuTraTheoMa(maPhieuTra);
                    break;
                case 3:
                    System.out.print("Nhap ten nguoi tra: ");
                    String tenNguoiTra = sc.nextLine();
                    PhieuTra ptTheoTen = timPhieuTraTheoTenNguoiTra(tenNguoiTra);
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
 class Main {
    public static void main(String[] args) {
        QuanLyDocGia ql = new QuanLyDocGia();

        ql.docFile();
        ql.hienThiDanhSach();

        System.out.println("\n--- Thu cam DG003 co thoi han ---");
        ql.camDocGiaCoThoiHan("DG003", LocalDate.of(2026, 6, 30));

        System.out.println("\n--- Mo cam DG004 ---");
        ql.moCamDocGia("DG004");

        System.out.println("\n--- Danh sach dang bi cam ---");
        ql.hienThiDanhSach(ql.locVaSapXep("bicam", "ten"));

        ql.luuFile();
        System.out.println("\nDone!");
    }
}
