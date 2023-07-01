package com.example.mobilenlashop.model;

public class Gia {
    public  int slMoiNhat;
    public  int slHienTai;
    public long giaHienTai;
    public long giaMoiNhat;

    public Gia(int slMoiNhat, int slHienTai, long giaHienTai, long giaMoiNhat) {
        this.slMoiNhat = slMoiNhat;
        this.slHienTai = slHienTai;
        this.giaHienTai = giaHienTai;
        this.giaMoiNhat = giaMoiNhat;
    }

    public int getSlMoiNhat() {
        return slMoiNhat;
    }

    public void setSlMoiNhat(int slMoiNhat) {
        this.slMoiNhat = slMoiNhat;
    }

    public int getSlHienTai() {
        return slHienTai;
    }

    public void setSlHienTai(int slHienTai) {
        this.slHienTai = slHienTai;
    }

    public long getGiaHienTai() {
        return giaHienTai;
    }

    public void setGiaHienTai(long giaHienTai) {
        this.giaHienTai = giaHienTai;
    }

    public long getGiaMoiNhat() {
        return giaMoiNhat;
    }

    public void setGiaMoiNhat(long giaMoiNhat) {
        this.giaMoiNhat = giaMoiNhat;
    }
}
