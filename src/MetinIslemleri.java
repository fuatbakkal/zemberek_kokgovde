import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

final class MetinIslemleri {

    private static String metin;
    private static List<String> stopWord = new ArrayList<>();
    private static final String DOSYA_YOLU = ".//src//proje//stopwords.txt";

    static String metinIsle(String giris) {
        metin = giris;
        boslukVeKarakterSil();
        stopWordSil();
        return metin;
    }

    // Gereksiz boşlukları ve harf haricindeki karakterleri siler
    private static void boslukVeKarakterSil() {
        metin = metin.replaceAll("\\p{Punct}|\\d", "").replaceAll("[^\\p{L}\\p{Nd}]+", " ").trim();
    }

    private static void dosyadanAl() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(DOSYA_YOLU))) {
            stopWord = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            Pencere.ekranaYaz(e.getMessage());
        }
    }

    private static void stopWordSil() {
        dosyadanAl();
        for (int i = 0; i < stopWord.size(); i++) {
            if (metin.contains(stopWord.get(i))) {
                metin = metin.replaceAll("\\s+" + stopWord.get(i) + "\\s+", " ");
            }
        }
    }
}