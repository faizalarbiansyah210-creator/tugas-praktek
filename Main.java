import java.util.Scanner;

// Kelas Menu untuk merepresentasikan data item
class Menu {
    String nama;
    double harga;
    String kategori; // "Makanan" atau "Minuman"

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Main {
    // Array untuk menyimpan data menu (minimal 4 menu per kategori)
    static Menu[] daftarMenu = {
        new Menu("Nasi Padang", 25000, "Makanan"),
        new Menu("Ayam Goreng", 20000, "Makanan"),
        new Menu("Mie Goreng", 15000, "Makanan"),
        new Menu("Sate Ayam", 30000, "Makanan"),
        new Menu("Es Teh Manis", 5000, "Minuman"),
        new Menu("Jus Jeruk", 10000, "Minuman"),
        new Menu("Kopi Hitam", 8000, "Minuman"),
        new Menu("Es Jeruk", 7000, "Minuman")
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Menampilkan daftar menu berdasarkan kategori
        tampilkanMenu();

        System.out.println("\n--- Input Pemesanan (Maksimal 4 Menu) ---");
        System.out.println("Format input: Nama Menu = Jumlah (Contoh: Nasi Padang = 2)");
        System.out.println("Ketik 'selesai = 0' jika pesanan kurang dari 4.");

        // 2. Menerima pesanan secara manual (Maksimal 4 menu, tanpa pengulangan)
        System.out.print("Pesanan 1: ");
        String input1 = scanner.nextLine();
        
        System.out.print("Pesanan 2: ");
        String input2 = scanner.nextLine();

        System.out.print("Pesanan 3: ");
        String input3 = scanner.nextLine();

        System.out.print("Pesanan 4: ");
        String input4 = scanner.nextLine();

        // Variable penampung data pesanan
        String nama1 = "", nama2 = "", nama3 = "", nama4 = "";
        int jumlah1 = 0, jumlah2 = 0, jumlah3 = 0, jumlah4 = 0;

        // Parsing Input 1
        if (input1.contains("=")) {
            String[] parts = input1.split("=");
            nama1 = parts[0].trim();
            jumlah1 = Integer.parseInt(parts[1].trim());
        }

        // Parsing Input 2
        if (input2.contains("=")) {
            String[] parts = input2.split("=");
            nama2 = parts[0].trim();
            jumlah2 = Integer.parseInt(parts[1].trim());
        }

        // Parsing Input 3
        if (input3.contains("=")) {
            String[] parts = input3.split("=");
            nama3 = parts[0].trim();
            jumlah3 = Integer.parseInt(parts[1].trim());
        }

        // Parsing Input 4
        if (input4.contains("=")) {
            String[] parts = input4.split("=");
            nama4 = parts[0].trim();
            jumlah4 = Integer.parseInt(parts[1].trim());
        }

        // Mencari item menu dan menghitung total per item
        Menu item1 = cariMenu(nama1);
        Menu item2 = cariMenu(nama2);
        Menu item3 = cariMenu(nama3);
        Menu item4 = cariMenu(nama4);

        double subtotal1 = (item1 != null) ? item1.harga * jumlah1 : 0;
        double subtotal2 = (item2 != null) ? item2.harga * jumlah2 : 0;
        double subtotal3 = (item3 != null) ? item3.harga * jumlah3 : 0;
        double subtotal4 = (item4 != null) ? item4.harga * jumlah4 : 0;

        // Total biaya pesanan kotor
        double totalKotor = subtotal1 + subtotal2 + subtotal3 + subtotal4;

        // 3. Menghitung Diskon & Penawaran Khusus
        double diskon = 0;
        if (totalKotor > 100000) {
            diskon = 0.10 * totalKotor; // Diskon 10%
        }

        // Beli 1 Gratis 1 untuk salah satu kategori minuman jika total > Rp 50.000
        double potongMinuman = 0;
        boolean dapatBuy1Get1 = totalKotor > 50000;

        if (dapatBuy1Get1) {
            if (item1 != null && item1.kategori.equalsIgnoreCase("Minuman") && jumlah1 > 0) {
                potongMinuman = item1.harga;
            } else if (item2 != null && item2.kategori.equalsIgnoreCase("Minuman") && jumlah2 > 0) {
                potongMinuman = item2.harga;
            } else if (item3 != null && item3.kategori.equalsIgnoreCase("Minuman") && jumlah3 > 0) {
                potongMinuman = item3.harga;
            } else if (item4 != null && item4.kategori.equalsIgnoreCase("Minuman") && jumlah4 > 0) {
                potongMinuman = item4.harga;
            }
        }

        double totalSetelahDiskon = totalKotor - diskon - potongMinuman;

        // Pajak 10% dan Biaya Pelayanan Rp 20.000
        double pajak = 0.10 * totalSetelahDiskon;
        double biayaPelayanan = 20000;
        double totalAkhir = totalSetelahDiskon + pajak + biayaPelayanan;

        // 4. Mencetak Struk Pesanan
        System.out.println("\n==========================================");
        System.out.println("             STRUK PEMBAYARAN             ");
        System.out.println("==========================================");

        cetakItemStruk(item1, jumlah1, subtotal1);
        cetakItemStruk(item2, jumlah2, subtotal2);
        cetakItemStruk(item3, jumlah3, subtotal3);
        cetakItemStruk(item4, jumlah4, subtotal4);

        System.out.println("------------------------------------------");
        System.out.printf("Total Kategori Pesanan : Rp %,.0f\n", totalKotor);

        if (diskon > 0) {
            System.out.printf("Diskon (10%%)          : -Rp %,.0f\n", diskon);
        }
        if (potongMinuman > 0) {
            System.out.printf("Promo Buy 1 Get 1 Minuman: -Rp %,.0f\n", potongMinuman);
        }

        System.out.printf("Pajak (10%%)           : Rp %,.0f\n", pajak);
        System.out.printf("Biaya Pelayanan        : Rp %,.0f\n", biayaPelayanan);
        System.out.println("------------------------------------------");
        System.out.printf("TOTAL PEMBAYARAN       : Rp %,.0f\n", totalAkhir);
        System.out.println("==========================================");

        scanner.close();
    }

    // Method pembantu tanpa pengulangan untuk menampilkan menu
    static void tampilkanMenu() {
        System.out.println("=== DAFTAR MENU RESTORAN ===");
        System.out.println("[ MAKANAN ]");
        System.out.println("1. " + daftarMenu[0].nama + " - Rp " + daftarMenu[0].harga);
        System.out.println("2. " + daftarMenu[1].nama + " - Rp " + daftarMenu[1].harga);
        System.out.println("3. " + daftarMenu[2].nama + " - Rp " + daftarMenu[2].harga);
        System.out.println("4. " + daftarMenu[3].nama + " - Rp " + daftarMenu[3].harga);

        System.out.println("\n[ MINUMAN ]");
        System.out.println("5. " + daftarMenu[4].nama + " - Rp " + daftarMenu[4].harga);
        System.out.println("6. " + daftarMenu[5].nama + " - Rp " + daftarMenu[5].harga);
        System.out.println("7. " + daftarMenu[6].nama + " - Rp " + daftarMenu[6].harga);
        System.out.println("8. " + daftarMenu[7].nama + " - Rp " + daftarMenu[7].harga);
    }

    // Method pembantu mencari menu manual tanpa pengulangan (menggunakan if-else)
    static Menu cariMenu(String nama) {
        if (daftarMenu[0].nama.equalsIgnoreCase(nama)) return daftarMenu[0];
        if (daftarMenu[1].nama.equalsIgnoreCase(nama)) return daftarMenu[1];
        if (daftarMenu[2].nama.equalsIgnoreCase(nama)) return daftarMenu[2];
        if (daftarMenu[3].nama.equalsIgnoreCase(nama)) return daftarMenu[3];
        if (daftarMenu[4].nama.equalsIgnoreCase(nama)) return daftarMenu[4];
        if (daftarMenu[5].nama.equalsIgnoreCase(nama)) return daftarMenu[5];
        if (daftarMenu[6].nama.equalsIgnoreCase(nama)) return daftarMenu[6];
        if (daftarMenu[7].nama.equalsIgnoreCase(nama)) return daftarMenu[7];
        return null;
    }

    // Method pembantu mencetak baris struk
    static void cetakItemStruk(Menu item, int jumlah, double total) {
        if (item != null && jumlah > 0) {
            System.out.printf("%-15s x%d @Rp %,.0f = Rp %,.0f\n", item.nama, jumlah, item.harga, total);
        }
    }
}