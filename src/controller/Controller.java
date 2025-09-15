package controller;

import java.time.LocalDate;
import java.util.HashSet;

import model.Elektronik;
import model.Makanan;
import model.Produk;
import repository.ElektronikRepo;
import repository.MakananRepo;
import view.Console;

public class Controller {
    private Console view;
    private ElektronikRepo elektronikRepo;
    private MakananRepo makananRepo;
    
    private boolean exit = false;
    private int pilihan = -1;
    private String header = null;

    public Controller(Console view, ElektronikRepo elektronikRepo, MakananRepo makananRepo) {
        this.view = view;
        this.elektronikRepo = elektronikRepo;
        this.makananRepo = makananRepo;
    }

    public void run() {
        loadData();

        while (!exit) {
            view.menu();
            pilihan = view.inputInt();
            exit = pilihMenu();
        }
        view.closeScanner();
        view.displayMsg("Program Berhenti");
    }

    public boolean pilihMenu() {
        if (pilihan == 0) {
            return true;
        } else if (pilihan <= 2) {
            boolean valid = false;
            while (!valid) {
                view.clearScreen();
                valid = pilihMenuProduk(pilihan);
            }
        } else {
            view.displayMsg("Pilihan tidak valid.");
            view.enterToContinue();;
        }
        return false;
    }

    public boolean pilihMenuProduk(int pilihan) {
        int noPilihan = -1;
        
        if (pilihan == 1) {
            header = "Elektronik";
            view.menuProduk(header);
            noPilihan = view.inputInt();

        } else if (pilihan == 2) {
            header = "Makanan";
            view.menuProduk(header);
            noPilihan = view.inputInt();

        }

        switch (noPilihan) {
            case 1:
                view.clearScreen();
                listProduk(header);
                view.enterToBack();
                break;
            case 2:
                view.clearScreen();
                addProduk(pilihan);
                view.enterToBack();
                break;
            case 3:
                view.clearScreen();
                deleteProduk(header);
                view.enterToBack();
                break;
            case 4:
                view.clearScreen();
                updateProduk(header);
                view.enterToBack();
                break;
            case 0:
                return true;
            default:
                view.displayMsg("Pilihan tidak valid.");
                view.enterToContinue();
                break;
        }
        return false;
    }

    private HashSet<Elektronik> getElektronik() {
         HashSet<Elektronik> list = elektronikRepo.all();

         if (list == null || list.isEmpty()) {
            view.displayMsg("Data Elektronik masih kosong.");
            return null;
         }
         return list;
    }

    private HashSet<Makanan> getMakanan() {
         HashSet<Makanan> list = makananRepo.all();

         if (list == null || list.isEmpty()) {
            view.displayMsg("Data Makanan masih kosong.");
            return null;
         }
         return list;
    }

    public void listProduk(String header) {
        view.header("Daftar Produk");
        try {
            if (header.equalsIgnoreCase("Elektronik")) {
                view.daftarProduk(getElektronik());
            } else if (header.equalsIgnoreCase("Makanan")) {
                view.daftarProduk(getMakanan());
            }
        } catch (Exception e) {
            System.err.println("Terjadi Kesalahan: " + e.getMessage());
        }
    }

    public void addProduk(int num) {
        view.header("Tambah Produk");
        String nama;
        double harga;

        view.displayMsg("Masukan Nama Produk : ");
        nama = view.inputStr();

        view.displayMsg("Masukan Harga : ");
        harga = view.inputDouble();
        
        if (num == 1) {
            int garansi;
            view.displayMsg("Masukan Jangka(bulan) Garansi : ");
            garansi = view.inputInt();
            
            if(elektronikRepo.add(nama, harga, garansi)) {
                view.displayMsg("Berhasil: " + nama + " ditambahkan.");
            } else {
                view.displayMsg("Gagal: " + nama + " ditambahkan.");
            }

        } else if (num == 2) {
            LocalDate tanggalKedaluwarsa;

            try {

                view.displayMsg("Masukan Bulan Kedaluwarsa : ");
                long bulan = view.inputLong();

                tanggalKedaluwarsa = LocalDate.now().plusMonths(bulan);
                
                if(makananRepo.add(nama, harga, tanggalKedaluwarsa)) {
                    view.displayMsg("Berhasil: " + nama + " ditambahkan.");
                } else {
                    view.displayMsg("Gagal: " + nama + " ditambahkan.");
                }
            } catch (Exception e) {
                view.displayMsg("Format tanggal tidak valid.");
            }

        }
    }

    public void deleteProduk(String header) {
        view.header("Hapus Produk");

        try {
            if (header.equalsIgnoreCase("Elektronik")) {
                view.daftarProduk(getElektronik());
                view.displayMsg("\nKembali ke menu masukan angka 0");
                view.displayMsg("\nPilih nomor produk yang akan dihapus:");
                int nomor = view.inputInt();

                if (nomor == 0) {
                    return;
                }
                String idProduk = getIdByNo(nomor, getElektronik());

                if (idProduk != null) {
                    if (elektronikRepo.delete(idProduk)) {
                        view.displayMsg("Berhasil: Produk dengan ID " + idProduk + " berhasil dihapus.");
                    } else {
                        view.displayMsg("Gagal: Produk tidak ditemukan.");
                    }
                } else {
                    view.displayMsg("Pilihan nomor tidak valid.");
                }

            } else if (header.equalsIgnoreCase("Makanan")) {
                view.daftarProduk(getMakanan());
                view.displayMsg("\nKembali ke menu masukan angka 0");
                view.displayMsg("\nPilih nomor produk yang akan dihapus:");
                int nomor = view.inputInt();
                
                if (nomor == 0) {
                    return;
                }
                String idProduk = getIdByNo(nomor, getMakanan());

                if (idProduk != null) {
                    if (makananRepo.delete(idProduk)) {
                        view.displayMsg("Berhasil: Produk dengan ID " + idProduk + " berhasil dihapus.");
                    } else {
                        view.displayMsg("Gagal: Produk tidak ditemukan.");
                    }
                } else {
                    view.displayMsg("Pilihan nomor tidak valid.");
                }
            }
        } catch (Exception e) {
            System.err.println("Terjadi Kesalahan: " + e.getMessage());
        }
        
    }

    public void updateProduk(String header) {
        view.header("Update Produk");

        try {
            if (header.equalsIgnoreCase("Elektronik")) {
                view.daftarProduk(getElektronik());
                view.displayMsg("\nKembali ke menu masukan angka 0");
                view.displayMsg("\nPilih nomor produk yang akan diubah:");
                int nomor = view.inputInt();
                
                if (nomor == 0) {
                    return;
                }
                String idProduk = getIdByNo(nomor, getElektronik());

                if (idProduk != null) {
                    String namaBaru = view.formPolosString("Masukan Nama baru: ");
                    double hargaBaru = view.formPolosDouble("Masukan Harga baru: ");
                    int garansi = view.formPolosInt("Masukan bulan garansi: ");

                    if (elektronikRepo.update(idProduk, namaBaru, hargaBaru, garansi)) {
                        view.displayMsg("Berhasil: Produk dengan ID " + idProduk + " berhasil diubah.");
                    } else {
                        view.displayMsg("Gagal: Produk tidak ditemukan.");
                    }
                } else {
                    view.displayMsg("Pilihan nomor tidak valid.");
                }

            } else if (header.equalsIgnoreCase("Makanan")) {
                view.daftarProduk(getMakanan());
                view.displayMsg("\nKembali ke menu masukan angka 0");
                view.displayMsg("\nPilih nomor produk yang akan diubah:");
                int nomor = view.inputInt();

                if (nomor == 0) {
                    return;
                }
                String idProduk = getIdByNo(nomor, getMakanan());

                if (idProduk != null) {
                    String namaBaru = view.formPolosString("Masukan Nama baru: ");
                    double hargaBaru = view.formPolosDouble("Masukan Harga baru: ");
                    long bulan = view.formPolosLong("Masukan bulan Expired: ");

                    if (makananRepo.update(idProduk, namaBaru, hargaBaru, bulan)) {
                        view.displayMsg("Berhasil: Produk dengan ID " + idProduk + " berhasil diubah.");
                    } else {
                        view.displayMsg("Gagal: Produk tidak ditemukan.");
                    }
                } else {
                    view.displayMsg("Pilihan nomor tidak valid.");
                }
            }
        } catch (Exception e) {
            System.err.println("Terjadi Kesalahan: " + e.getMessage());
        }
    }
    
    private String getIdByNo(int nomor, HashSet<? extends Produk> produkSet) {
        int counter = 1;
        
        for (Produk produk : produkSet) {
            if (counter == nomor) {
                return produk.getIdProduk();
            }
            counter++;
        }
        return null;
    }

    private void loadData() {
        makananRepo.readFile("data/makanan.txt");
        elektronikRepo.readFile("data/elektronik.txt");
    }

}
