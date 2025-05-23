package com.frota.model;

public abstract class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    
    public Veiculo(String placa, String marca, String modelo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    @Override
    public String toString() {
        return marca + " " + modelo + " (Placa: " + placa + ")";
    }
}
