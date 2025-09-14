package model;

public class Elektronik extends Produk{
    private int garansi;


    public Elektronik(String idProduk, String nama, double hargaDasar, int garansi) {
        super(idProduk, nama, hargaDasar);
        this.garansi = garansi;
    }

    public int getGaransi() {
        return garansi;
    }

    public void setGaransi(int garansi) {
        this.garansi = garansi;
    }

    @Override
    public double hitungHargaAkhir() {
        return super.getHargaDasar() * 1.05; 
    }

    @Override
    public String tampilkanInfo() {
        return super.tampilkanInfo() + "\n" + 
        "Garansi      : " + garansi;
    }

}
