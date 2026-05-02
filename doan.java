import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import javax.print.Doc;
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
    public void NhapPhieuMuon(Scanner scanner)
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

            if(choice == 0)
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
                Document TL = TimSachTheoTen(tenTaiLieu);
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
    public ChiTietTra(Document TL, int SoLuong, LocalDate NgayTra)
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
        if (!tenSoSanh.equalsIgnoreCase(phieuMuon.NguoiMuon.getTenDocGia()))
        {
            System.out.println("Nguoi tra khong hop le, vui long xac nhan lai:");
            nhapNguoiTra(scanner);
        }
        else
        {
            System.out.println("Nguoi tra hop le");
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
                DanhSachChiTietTra.add(new ChiTietTra(TL, 1, NgayTra));
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
        
}