package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;

import model.Elektronik;

public class ElektronikRepo {
    private final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom random = new SecureRandom();
    private HashSet<Elektronik> elektronik = new HashSet<>();

    private String generateId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return sb.toString();
    }

    public HashSet<Elektronik> all() {
        return elektronik;
    }

    public boolean add(String nama, double hargaDasar, int garansi) {
        String idProduk = generateId(6);
        Elektronik elektronikBaru = new Elektronik(idProduk, nama, hargaDasar, garansi);
        return elektronik.add(elektronikBaru);
    }

    public boolean delete(String idProduk) {
        Iterator<Elektronik> iterator = elektronik.iterator();

        while (iterator.hasNext()) {
            Elektronik del = iterator.next();

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
                    int garansi = Integer.parseInt(data[3].trim());
                    
                    Elektronik produk = new Elektronik(idProduk, nama, hargaDasar, garansi);
                    elektronik.add(produk);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
