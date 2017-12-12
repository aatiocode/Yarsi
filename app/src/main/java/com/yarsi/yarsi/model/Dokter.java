package com.yarsi.yarsi.model;

/**
 * Created by ARISTIO-PC on 28/11/2017.
 */

public class Dokter {
    public String id, namaDepan, namaBelakang, spesialis, foto, unitPerawatan, jabatan;

    public Dokter(String id, String namaDepan, String namaBelakang, String spesialis, String foto, String unitPerawatan, String jabatan) {
        this.id = id;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.spesialis = spesialis;
        this.foto = foto;
        this.unitPerawatan = unitPerawatan;
        this.jabatan = jabatan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUnitPerawatan() {
        return unitPerawatan;
    }

    public void setUnitPerawatan(String unitPerawatan) {
        this.unitPerawatan = unitPerawatan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
