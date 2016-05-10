public final class Main {

    public static void main(String[] args) {
        Pencere.baslat();
        MySQL mysql = new MySQL();

        while (true) {
            if (mysql.yeniMetinAl()) {
                String metin = MetinIslemleri.metinIsle(mysql.getMetin()); //Metni işle
                String islenmis_metin = "";
                for (String kelime : metin.split(" ")) {
                    KelimeIslemleri kokGovde = new KelimeIslemleri(kelime);
                    islenmis_metin += kokGovde.getKokGovde() + " ";
                    if (kokGovde.getUygunluk()) {//İsim köklüyse veritananına ekle
                        mysql.kelimeAraVeEkle(kokGovde.getKokGovde());
                    }
                }
                mysql.metinEkle(mysql.getMetinNo(), islenmis_metin); //İşlenmiş metni veritabanına ekler
            }
        }
    }
}