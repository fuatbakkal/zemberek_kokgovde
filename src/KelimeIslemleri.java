import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;

final class KelimeIslemleri {
    
    private static String kokGovde;
    private static boolean uygunluk;
    private static final Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
    private static final CozumlemeSeviyesi SEVIYE = CozumlemeSeviyesi.TUM_KOKLER;
    
    KelimeIslemleri(String kelime) {
        kokGovde = kelime;
        uygunluk = false;
        duzeltVeAyir();
    }
    
    final boolean getUygunluk() {
        return uygunluk;
    }
    
    final String getKokGovde() {
        return kokGovde;
    }
    
    private static Kelime[] cozumle(String giris) {
        return zemberek.kelimeCozumle(giris, SEVIYE);
    }
    
    private static boolean uygunMu(Kelime giris) {
        return giris.kok().tip().equals(KelimeTipi.ISIM) || giris.kok().tip().equals(KelimeTipi.SIFAT)
                || giris.kok().tip().equals(KelimeTipi.OZEL);
    }
    
    private static void duzeltVeAyir() {
        // Gelen kelime boşluk karakteriyse işleme sokma
        if (kokGovde.trim().length() == 0) {
            return;
        }
        // Hata varsa düzelt
        if (!zemberek.kelimeDenetle(kokGovde)) {
            hataDuzelt();
        }
        // Kök veya gövdeye ayır
        kokGovdeAyir();
    }
    
    private static void kokGovdeAyir() {
        Kelime[] kelimeler = cozumle(kokGovde);
        // Sadece ilk çözümlemeye bak ('koşullarında' gibi bir ismi fiil olarak algılıyor)
        try {
            if (uygunMu(kelimeler[0])) {
                kokGovde = kelimeler[0].kok().icerik();
                uygunluk = true;
            }
        } catch (Exception e) {
            //System.out.println("UYARI: " + kokGovde + " kelimesi çözümlenemedi!");
        }

        // Bütün isim köklü çözümlemelere bak ('kullanmak' gibi bir fiilin kökünü 'kul' olarak çözümlüyor)
        /*for (Kelime k : kelimeler) {
            if (uygunMu(k)) {
                kokGovde = k.kok().icerik();
                uygunluk = true;
                return;
            }
        }*/
    }
    
    private static void hataDuzelt() {
        // İlk harfi büyük yap ve kontrol et
        String orijinal = kokGovde;
        kokGovde = orijinal.substring(0, 1).toUpperCase() + orijinal.substring(1);
        if (zemberek.kelimeDenetle(kokGovde)) {
            return;
        }
        // Önerileri kontrol et
        Kelime[] kelimeler;
        String[] oneriler = zemberek.oner(kokGovde);
        for (String oneri : oneriler) {
            kelimeler = cozumle(oneri);
            for (Kelime k : kelimeler) {
                if (uygunMu(k)) {
                    kokGovde = k.kok().icerik();
                    uygunluk = true;
                    return; // Uygun öneri bulundu
                }
            }
        }
        // Hata düzeltilemedi
        kokGovde = orijinal;
    }
}