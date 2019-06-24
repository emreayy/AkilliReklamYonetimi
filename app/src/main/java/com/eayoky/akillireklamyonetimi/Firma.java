package com.eayoky.akillireklamyonetimi;

public class Firma {
    private String FirmaID;
    private String FirmaAdi;
    private String FirmaLat;
    private String FirmaLon;
    private String KampanyaIcerik;
    private String KampanyaSuresi;
    private String Kategori;

    public Firma( String id,String ad, String lat, String lon, String icerik, String sure,String kategori) {
        this.setFirmaID (id);
        this.setFirmaAdi (ad);
        this.setFirmaLat (lat);
        this.setFirmaLon (lon);
        this.setKampanyaIcerik (icerik);
        this.setKampanyaSuresi (sure);
        this.setKategori (kategori);

    }
    public Firma(){

    }

    public String getFirmaID() {
        return FirmaID;
    }

    public void setFirmaID(String firmaID) {
        FirmaID = firmaID;
    }

    public String getFirmaAdi() {
        return FirmaAdi;
    }

    public void setFirmaAdi(String firmaAdi) {
        FirmaAdi = firmaAdi;
    }

    public String getKampanyaIcerik() {
        return KampanyaIcerik;
    }

    public void setKampanyaIcerik(String kampanyaIcerik) {
        KampanyaIcerik = kampanyaIcerik;
    }

    public String getKampanyaSuresi() {
        return KampanyaSuresi;
    }

    public void setKampanyaSuresi(String kampanyaSuresi) {
        KampanyaSuresi = kampanyaSuresi;
    }

    public String getFirmaLat() {
        return FirmaLat;
    }

    public void setFirmaLat(String firmaLat) {
        FirmaLat = firmaLat;
    }

    public String getFirmaLon() {
        return FirmaLon;
    }

    public void setFirmaLon(String firmaLon) {
        FirmaLon = firmaLon;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }
}
