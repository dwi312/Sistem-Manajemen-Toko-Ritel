package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import model.Elektronik;
import model.Makanan;
import repository.ElektronikRepo;
import repository.MakananRepo;
import view.Console;

public class Controller {
    private Console view;
    private ElektronikRepo elektronikRepo;
    private MakananRepo makananRepo;
    
    private boolean exit = false;
    private int pilihan = -1;

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
        String header;

        if (pilihan == 1) {
            header = "Elektronik";
            view.menuProduk(header);
            noPilihan = view.inputInt();

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
                    break;
                case 4:
                    view.clearScreen();
                    break;
                case 0:
                    return true;
                default:
                    view.displayMsg("Pilihan tidak valid.");
                    view.enterToContinue();
                    break;
            }
        } else if (pilihan == 2) {
            header = "Makanan";
            view.menuProduk(header);
            noPilihan = view.inputInt();

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
                    break;
                case 4:
                    view.clearScreen();
                    break;
                case 0:
                    return true;
                default:
                    view.displayMsg("Pilihan tidak valid.");
                    view.enterToContinue();
                    break;
            }
        }
        return false;
    }

    public void listProduk(String header) {
        try {
            if (header.equalsIgnoreCase("Elektronik")) {
                HashSet<Elektronik> cek = elektronikRepo.all();
                
                if (cek == null || cek.isEmpty()) {
                    view.displayMsg("Data " + header + " masih kosong.");
                    return;
                }
                view.daftarProduk(cek);

            } else if (header.equalsIgnoreCase("Makanan")) {
                HashSet<Makanan> cek = makananRepo.all();

                if (cek == null || cek.isEmpty()) {
                    view.displayMsg("Data " + header + " masih kosong.");
                    return;
                }
                view.daftarProduk(cek);

            }

        } catch (Exception e) {
            System.err.println("Terjadi Kesalahan: " + e.getMessage());
        }
    }

    public void addProduk(int num) {
        view.header("Tambah Barang");
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

    private void loadData() {
        makananRepo.readFile("data/makanan.txt");
        elektronikRepo.readFile("data/elektronik.txt");
    }

}
