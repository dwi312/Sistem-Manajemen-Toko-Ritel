package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Makanan extends Produk{
    private LocalDate tanggalKedaluwarsa;

    public Makanan(String idProduk, String nama, double hargaDasar, LocalDate tanggalKedaluwarsa) {
        super(idProduk, nama, hargaDasar);
        this.tanggalKedaluwarsa = tanggalKedaluwarsa;
    }

    public LocalDate getTanggalKedaluwarsa() {
        return tanggalKedaluwarsa;
    }

    public void settanggalKedaluwarsa(LocalDate tanggalKedaluwarsa) {
        this.tanggalKedaluwarsa = tanggalKedaluwarsa;
    }

    @Override
    public double hitungHargaAkhir() {
        LocalDate hariIni = LocalDate.now();
        // Selisih hari antara sekarang dan tanggal kedaluwarsa
        long sisaHari = ChronoUnit.DAYS.between(hariIni, tanggalKedaluwarsa);
        
        double hargaAkhir = super.getHargaDasar();
        
        // Diskon 10% jika tanggal kedaluwarsa kurang dari atau sama dengan 30 hari lagi
        if (sisaHari <= 30) {
            hargaAkhir = hargaAkhir * 0.90;
        }

        return hargaAkhir;
    }

    @Override
    public String tampilkanInfo() {
        return super.tampilkanInfo() + "\n" + 
        "Kedaluwarsa  : " + tanggalKedaluwarsa;
    }
}
