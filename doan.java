import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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