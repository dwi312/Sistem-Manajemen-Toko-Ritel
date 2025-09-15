package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;

import model.Makanan;

public class MakananRepo {
    private final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom random = new SecureRandom();
    private HashSet<Makanan> makanan = new HashSet<>();

    private String generateId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return sb.toString();
    }

    public HashSet<Makanan> all() {
        return makanan;
    }

    public boolean add(String nama, double hargaDasar, LocalDate tanggalKedaluwarsa) {
        String idProduk = generateId(6);
        Makanan makananBaru = new Makanan(idProduk, nama, hargaDasar, tanggalKedaluwarsa);
        return makanan.add(makananBaru);
    }

    public boolean delete(String idProduk) {
        Iterator<Makanan> iterator = makanan.iterator();

        while (iterator.hasNext()) {
            Makanan del = iterator.next();

            if (del.getIdProduk().equalsIgnoreCase(idProduk)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void readFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String idProduk = data[0].trim();
                    String nama = data[1].trim();
                    double hargaDasar = Double.parseDouble(data[2].trim());
                    LocalDate tanggalKedaluwarsa = LocalDate.parse(data[3].trim());
                    
                    Makanan produk = new Makanan(idProduk, nama, hargaDasar, tanggalKedaluwarsa);
                    makanan.add(produk);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
