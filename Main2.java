import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    String nama;
    double harga;
    String kategori; // "Makanan" atau "Minuman"

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

class PesananItem {
    MenuItem menu;
    int jumlah;

    public PesananItem(MenuItem menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }
}

public class Main2 {
    static ArrayList<MenuItem> daftarMenu = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inisialisasi awal minimal 4 menu dari masing-masing kategori
        daftarMenu.add(new MenuItem("Nasi Goreng Spesial", 25000, "Makanan"));
        daftarMenu.add(new MenuItem("Mie Goreng Seafood", 28000, "Makanan"));
        daftarMenu.add(new MenuItem("Ayam Bakar Madu", 30000, "Makanan"));
        daftarMenu.add(new MenuItem("Sate Ayam Madura", 25000, "Makanan"));
        
        daftarMenu.add(new MenuItem("Es Teh Manis", 5000, "Minuman"));
        daftarMenu.add(new MenuItem("Es Jeruk Peras", 7000, "Minuman"));
        daftarMenu.add(new MenuItem("Jus Alpukat", 15000, "Minuman"));
        daftarMenu.add(new MenuItem("Kopi Hitam", 10000, "Minuman"));

        int pilihanUtama = 0;
        do {
            System.out.println("\n=== APLIKASI RESTORAN MAKANAN ===");
            System.out.println("1. Menu Pelanggan (Pemesanan)");
            System.out.println("2. Menu Pemilik Restoran (Manajemen Menu)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");

            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid. Masukkan angka 1-3: ");
                scanner.next();
            }
            pilihanUtama = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            switch (pilihanUtama) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuPemilik();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihanUtama != 3);
    }

    // --- MODUL PELANGGAN ---
    public static void menuPelanggan() {
        ArrayList<PesananItem> keranjang = new ArrayList<>();
        
        while (true) {
            tampilkanDaftarMenu();
            System.out.println("\nKetik nama menu yang ingin dipesan atau ketik 'selesai' untuk mengakhiri pesanan.");
            System.out.print("Nama Menu: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            // Cari menu berdasarkan nama
            MenuItem selectedMenu = null;
            for (MenuItem m : daftarMenu) {
                if (m.nama.equalsIgnoreCase(input)) {
                    selectedMenu = m;
                    break;
                }
            }

            if (selectedMenu != null) {
                System.out.print("Masukkan jumlah pesanan untuk " + selectedMenu.nama + ": ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Masukkan angka yang valid untuk jumlah: ");
                    scanner.next();
                }
                int jumlah = scanner.nextInt();
                scanner.nextLine(); // bersihkan newline

                if (jumlah > 0) {
                    keranjang.add(new PesananItem(selectedMenu, jumlah));
                    System.out.println("Berhasil menambahkan ke keranjang.");
                } else {
                    System.out.println("Jumlah harus lebih dari 0.");
                }
            } else {
                System.out.println("Menu tidak ditemukan! Sistem meminta input kembali.");
            }
        }

        if (!keranjang.isEmpty()) {
            cetakStruk(keranjang);
        } else {
            System.out.println("Tidak ada pesanan yang dilakukan.");
        }
    }

    // --- MODUL PEMILIK RESTORAN ---
    public static void menuPemilik() {
        int pilihan = 0;
        do {
            System.out.println("\n=== MANAJEMEN MENU RESTORAN ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu Restoran");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih opsi (1-4): ");

            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid. Masukkan angka 1-4: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Pilihan di luar jangkauan. Silakan masukkan ulang.");
            }
        } while (pilihan != 4);
    }

    public static void tampilkanDaftarMenu() {
        System.out.println("\n--- DAFTAR MENU RESTORAN ---");
        System.out.println("Kategori: Makanan");
        int no = 1;
        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).kategori.equalsIgnoreCase("Makanan")) {
                System.out.println(no + ". " + daftarMenu.get(i).nama + " - Rp " + daftarMenu.get(i).harga);
                no++;
            }
        }

        System.out.println("\nKategori: Minuman");
        no = 1;
        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).kategori.equalsIgnoreCase("Minuman")) {
                System.out.println(no + ". " + daftarMenu.get(i).nama + " - Rp " + daftarMenu.get(i).harga);
                no++;
            }
        }
    }

    public static void tampilkanSemuaMenuIndeks() {
        System.out.println("\n--- DAFTAR SEMUA MENU ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            MenuItem m = daftarMenu.get(i);
            System.out.println((i + 1) + ". [" + m.kategori + "] " + m.nama + " - Rp " + m.harga);
        }
    }

    public static void tambahMenu() {
        System.out.print("Masukkan nama menu baru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga menu: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Harga harus berupa angka: ");
            scanner.next();
        }
        double harga = scanner.nextDouble();
        scanner.nextLine();

        String kategori = "";
        while (true) {
            System.out.print("Masukkan kategori (Makanan/Minuman): ");
            kategori = scanner.nextLine().trim();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                break;
            }
            System.out.println("Kategori tidak valid! Harap masukkan 'Makanan' atau 'Minuman'.");
        }

        daftarMenu.add(new MenuItem(nama, harga, kategori));
        System.out.println("Menu baru berhasil ditambahkan!");
    }

    public static void ubahHargaMenu() {
        while (true) {
            tampilkanSemuaMenuIndeks();
            System.out.print("Masukkan nomor menu yang ingin diubah harganya (atau ketik 0 untuk batal): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Masukkan nomor menu yang valid: ");
                scanner.next();
            }
            int index = scanner.nextInt();
            scanner.nextLine();

            if (index == 0) return;

            if (index > 0 && index <= daftarMenu.size()) {
                MenuItem m = daftarMenu.get(index - 1);
                System.out.println("Menu dipilih: " + m.nama + " (Harga saat ini: Rp " + m.harga + ")");
                System.out.print("Konfirmasi mengubah harga? (Ya/Tidak): ");
                String konfirmasi = scanner.nextLine().trim();

                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    System.out.print("Masukkan harga baru: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.print("Harga harus berupa angka: ");
                        scanner.next();
                    }
                    double hargaBaru = scanner.nextDouble();
                    scanner.nextLine();

                    m.harga = hargaBaru;
                    System.out.println("Harga berhasil diubah!");
                    break;
                } else {
                    System.out.println("Perubahan dibatalkan.");
                    break;
                }
            } else {
                System.out.println("Nomor menu tidak valid. Sistem meminta input kembali.");
            }
        }
    }

    public static void hapusMenu() {
        while (true) {
            tampilkanSemuaMenuIndeks();
            System.out.print("Masukkan nomor menu yang ingin dihapus (atau ketik 0 untuk batal): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Masukkan nomor menu yang valid: ");
                scanner.next();
            }
            int index = scanner.nextInt();
            scanner.nextLine();

            if (index == 0) return;

            if (index > 0 && index <= daftarMenu.size()) {
                MenuItem m = daftarMenu.get(index - 1);
                System.out.println("Menu dipilih untuk dihapus: " + m.nama);
                System.out.print("Konfirmasi menghapus menu? (Ya/Tidak): ");
                String konfirmasi = scanner.nextLine().trim();

                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    daftarMenu.remove(index - 1);
                    System.out.println("Menu berhasil dihapus!");
                    break;
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                    break;
                }
            } else {
                System.out.println("Nomor menu tidak valid. Sistem meminta input kembali.");
            }
        }
    }

    // --- MODUL HITUNG BIAYA DAN CETAK STRUK ---
    public static void cetakStruk(ArrayList<PesananItem> keranjang) {
        double subtotal = 0;
        
        System.out.println("\n========================================");
        System.out.println("              STRUK PESANAN             ");
        System.out.println("========================================");
        
        for (PesananItem item : keranjang) {
            double totalPerItem = item.menu.harga * item.jumlah;
            subtotal += totalPerItem;
            System.out.println("- " + item.menu.nama + " (" + item.jumlah + "x @Rp " + item.menu.harga + ") = Rp " + totalPerItem);
        }

        System.out.println("----------------------------------------");
        System.out.println("Subtotal: Rp " + subtotal);

        // Diskon 10% jika total biaya keseluruhan pesanan melebihi Rp 100.000
        double diskon = 0;
        if (subtotal > 100000) {
            diskon = 0.10 * subtotal;
            System.out.println("Diskon 10% (> Rp 100.000): -Rp " + diskon);
        }

        double totalSetelahDiskon = subtotal - diskon;

        // Penawaran beli satu gratis satu untuk salah satu kategori minuman jika total biaya > Rp 50.000
        boolean b1g1Minuman = false;
        if (subtotal > 50000) {
            // Cari minuman termurah di keranjang untuk digratiskan (beli satu gratis satu)
            MenuItem minTermurah = null;
            int qtyMinuman = 0;
            for (PesananItem item : keranjang) {
                if (item.menu.kategori.equalsIgnoreCase("Minuman")) {
                    qtyMinuman += item.jumlah;
                    if (minTermurah == null || item.menu.harga < minTermurah.harga) {
                        minTermurah = item.menu;
                    }
                }
            }
            if (minTermurah != null && qtyMinuman >= 2) {
                b1g1Minuman = true;
                totalSetelahDiskon -= minTermurah.harga;
                System.out.println("Promo Beli 1 Gratis 1 Minuman (" + minTermurah.nama + "): -Rp " + minTermurah.harga);
            }
        }

        // Pajak 10% dan Biaya Pelayanan Rp 20.000
        double pajak = 0.10 * totalSetelahDiskon;
        double biayaPelayanan = 20000;
        double totalAkhir = totalSetelahDiskon + pajak + biayaPelayanan;

        System.out.println("Pajak (10%): Rp " + pajak);
        System.out.println("Biaya Pelayanan: Rp " + biayaPelayanan);
        System.out.println("========================================");
        System.out.println("TOTAL KESELURUHAN: Rp " + totalAkhir);
        System.out.println("========================================");
    }
}