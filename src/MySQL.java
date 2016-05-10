import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class MySQL {

    private static final String KULLANICI = "kou";
    private static final String SIFRE = "yazlab";
    private static final String VERITABANI = "zemberek";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/" + VERITABANI;
    private static Connection baglanti = null;
    private static Statement statement = null;
    private static ResultSet sonuc = null;
    private static String metin = null;
    private static int metin_no = 0;

    private static void baglan() {
        try {
            baglanti = DriverManager.getConnection(URL, KULLANICI, SIFRE);
            statement = baglanti.createStatement();
            Pencere.ekranaYaz("Veritabanına bağlanıldı.\n--------------------------------------------------------------------------------------------------------------------------------");
        } catch (SQLException ex) {
            Pencere.ekranaYaz(ex.getMessage());
        }
    }

    protected MySQL() {
        baglan();
        yeniMetinAl();
    }

    protected final String getMetin() {
        return metin;
    }

    protected final int getMetinNo() {
        return metin_no;
    }

    protected final boolean yeniMetinAl() {
        try {
            sonuc = statement.executeQuery("SELECT no,orijinal_metin FROM metinler WHERE islenmis_metin = '-' ORDER BY no ASC LIMIT 1");
            if (sonuc.first()) {
                metin = sonuc.getString("orijinal_metin");
                metin_no = sonuc.getInt("no");
                Pencere.ekranaYaz("İşlenecek Metin:\n" + metin + "\n--------------------------------------------------------------------------------------------------------------------------------");
                return true;
            }
        } catch (SQLException ex) {
            Pencere.ekranaYaz(ex.getMessage());
            return false;
        }
        return false;
    }

    protected final void metinEkle(int no, String metin) {
        try {
            statement.executeUpdate("UPDATE metinler SET islenmis_metin = '" + metin + "' WHERE no = " + no);
            Pencere.ekranaYaz("İşlenmiş Metin:\n" + metin + "\n--------------------------------------------------------------------------------------------------------------------------------");
        } catch (SQLException ex) {
            Pencere.ekranaYaz(ex.getMessage());
        }
    }

    protected final void kelimeAraVeEkle(String kelime) {
        try {
            sonuc = statement.executeQuery("SELECT no FROM kelimeler WHERE kelime = '" + kelime + "'");
            //Eklenecek kelime zaten varsa
            if (sonuc.first()) {
                frekansArttir(sonuc.getInt("no"));
            } //Eklenecek kelime veritabanında yoksa
            else {
                kelimeEkle(kelime);
            }
        } catch (SQLException ex) {
            Pencere.ekranaYaz(ex.getMessage());
        }
    }

    private void frekansArttir(int no) {
        try {
            statement.executeUpdate("UPDATE kelimeler SET frekans=frekans+1 WHERE no = " + no);
            //Sorgu başarılı
        } catch (SQLException ex) {
            Pencere.ekranaYaz(ex.getMessage());
            //Hata oluştu
        }
    }

    private void kelimeEkle(String kelime) {
        try {
            statement.executeUpdate("INSERT INTO kelimeler (kelime) VALUES ('" + kelime + "')");
            //Sorgu başarılı
        } catch (SQLException ex) {
            //Hata oluştu
            Pencere.ekranaYaz(ex.getMessage());
        }
    }
}