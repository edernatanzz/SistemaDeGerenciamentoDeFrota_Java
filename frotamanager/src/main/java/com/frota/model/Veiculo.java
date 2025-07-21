package com.frota.model;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * Classe abstrata que representa um veículo genérico da frota.
 * Define atributos básicos e obriga a implementação do cálculo de custo de manutenção.
 */
@Entity
public abstract class Veiculo implements Serializable {
    private int id;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    
    /**
     * Construtor do veículo.
     * @param id ID do veículo
     * @param placa Placa do veículo
     * @param marca Marca do veículo
     * @param modelo Modelo do veículo
     * @param ano Ano de fabricação do veículo
     */
    public Veiculo(int id, String placa, String marca, String modelo, int ano) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getAno() {
        return ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Método abstrato para calcular o custo de manutenção do veículo.
     * @return custo da manutenção
     */
    public abstract double calcularCustoManutencao();
    
    @Override
    public String toString() {
        return "ID: " + id + ", " + marca + " " + modelo + " (Placa: " + placa + ", Ano: " + ano + ")";
    }
}
