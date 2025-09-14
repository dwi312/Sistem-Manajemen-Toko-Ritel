package model;

import java.util.Objects;

public abstract class Produk {
    private String idProduk;
    private String nama;
    private double hargaDasar;

    public Produk(String idProduk, String nama, double hargaDasar) {
        this.idProduk = idProduk;
        this.nama = nama;
        this.hargaDasar = hargaDasar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        Produk produk = (Produk) o;
        return Objects.equals(idProduk, produk.idProduk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduk);
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHargaDasar() {
        return hargaDasar;
    }

    public void setHargaDasar(double hargaDasar) {
        this.hargaDasar = hargaDasar;
    }

    public abstract double hitungHargaAkhir();

    public String tampilkanInfo() {
        return  "ID Produk    : " + idProduk + "\n" + 
                "Nama         : " + nama + "\n" +
                "Harga Dasar  : " + hargaDasar;
    }

}
