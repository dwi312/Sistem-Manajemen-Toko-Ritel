package view;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import model.Elektronik;
import model.Makanan;
import model.Produk;

public class Console {
    private Scanner input;
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    public Console(Scanner input) {
        this.input = input;
    }

    public void closeScanner() {
        input.close();
    }
    public void displayMsg(String msg) {
        System.out.println(msg);
    }

    public void menu() {
        clearScreen();
        System.out.println("\n=====  Sistem Manajemen Toko Ritel  =====");
        System.out.println("\n1. Elektronik");
        System.out.println("2. Makanan");
        System.out.println("0. Keluar");
        System.out.println("\n-----------------------------------------");
        System.out.print("Pilih : ");
    }

    public void header(String title) {
        System.out.println("\n=====  Menu " + title + "  =====");
        System.out.println("");
    }

    public void daftarMenu(String param) {
        String[] params = param.split(",");

        try {
            int count = params.length;
            String[] newParam = new String[count];
            
            if (count > 1 || count != 0) {
                for (int i = 0; i < newParam.length; i++) {
                    newParam[i] = params[i];

                    System.out.println((i+1) +". " + newParam[i]);
                }
            }
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void menuProduk(String header) {
        header(header);
        daftarMenu("Lihat Semua Barang,Tambah Barang,Hapus Barang,Rubah Data");
        System.out.println("0. Kembali");
        System.out.println("\n----------------------------");
        System.out.print("Pilih : ");
    }

    public void daftarProduk(HashSet<? extends Produk> produk) {
        header("Daftar Barang");
        
        if (produk == null || produk.isEmpty()) {
            System.out.println("Tidak ada data produk yang tersedia.");
            return;
        }
        
        Produk produks = produk.iterator().next();
        int count = 1;

        if (produks instanceof Elektronik) {
            System.out.printf("| %-3s | %-10s | %-15s | %-15s | %-10s |\n","No","ID", "nama", "Harga", "garansi");
            System.out.println("---------------------------------------------------------------------");

            for (Produk list : produk) {
                Elektronik elektronik = (Elektronik) list;
                System.out.printf("| %-3s | %-10s | %-15s | %-15s | %-10s |\n",
                                count++,
                                elektronik.getIdProduk(),
                                elektronik.getNama(),
                                formatRupiah.format(elektronik.hitungHargaAkhir()),
                                elektronik.getGaransi());
            }
            System.out.println("---------------------------------------------------------------------");

        } else if (produks instanceof Makanan) {
            System.out.printf("| %-3s | %-10s | %-20s | %-15s | %-10s |\n","No","ID", "nama", "Harga", "Expired");
            System.out.println("---------------------------------------------------------------------");

            for (Produk list : produk) {
                Makanan makanan = (Makanan) list;
                System.out.printf("| %-3s | %-10s | %-20s | %-15s | %-10s |\n",
                            count++,
                            makanan.getIdProduk(),
                            makanan.getNama(),
                            formatRupiah.format(makanan.hitungHargaAkhir()),
                            makanan.getTanggalKedaluwarsa());
        }
        System.out.println("---------------------------------------------------------------------");

        }

    }

    public int inputInt() {
        boolean valid = false;
        int angka = -1;

        while (!valid) {
            try {
                String bukanNomor = input.nextLine();

                if (bukanNomor.trim().isEmpty()) {
                    System.out.println("Input tidak boleh kosong");
                } else {
                    angka = Integer.parseInt(bukanNomor);
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! harus angka");
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }

        }

        return angka;
    }

    public long inputLong() {
        boolean valid = false;
        long angka = 0L;

        while (!valid) {
            try {
                String bukanNomor = input.nextLine();

                if (bukanNomor.trim().isEmpty()) {
                    System.out.println("Input tidak boleh kosong.");
                } else {
                    angka = Long.parseLong(bukanNomor);
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Harus berupa angka.");
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }

        return angka;
    }

    public double inputDouble() {
        boolean valid = false;
        double angka = -1;

        while (!valid) {
            try {
                String bukanNomor = input.nextLine();

                if (bukanNomor.trim().isEmpty()) {
                    System.out.println("Input tidak boleh kosong");
                } else {
                    angka = Double.parseDouble(bukanNomor);
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! harus angka");
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }

        }

        return angka;
    }

    public String inputStr() {
        boolean valid = false;
        String stringInput = "";

        while (!valid) {
            stringInput = input.nextLine();
            if (stringInput.trim().isEmpty()) {
                System.out.println("Input tidak boleh kosong");
            } else {
                valid = true;
            }
        }

        return stringInput;
    }

    public void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.err.println("Gagal membersihkan layar.");
        }
    }

    public void enterToContinue() {
        System.out.println("\nTekan enter untuk melanjutkan..");
        input.nextLine();
    }

    public void enterToBack() {
        System.out.println("\nTekan enter untuk kembali..");
        input.nextLine();
    }
}
