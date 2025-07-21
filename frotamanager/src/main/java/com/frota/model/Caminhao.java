package com.frota.model;

import java.io.Serializable;

import javax.persistence.Entity;

import com.frota.model.enums.StatusVeiculo;

/**
 * Classe que representa um caminhão da frota.
 * Herda de Veiculo e implementa Rastreavel.
 */
@Entity
public class Caminhao extends Veiculo implements Rastreavel, Manutencivel, Serializable {
    private int numeroEixos;
    private double capacidadeCarga;
    private double comprimento;
    private String localizacaoAtual;
    private boolean manutencaoPendente;
    private java.time.LocalDate dataUltimaManutencao;
    private StatusVeiculo status;

    /**
     * Construtor do Caminhao.
     * @param placa Placa do veículo
     * @param marca Marca do veículo
     * @param modelo Modelo do veículo
     * @param ano Ano de fabricação
     * @param numeroEixos Número de eixos
     * @param capacidadeCarga Capacidade de carga em toneladas
     * @param comprimento Comprimento em metros
     */
    public Caminhao(int id, String placa, String marca, String modelo, int ano, int numeroEixos, double capacidadeCarga, double comprimento) {
        super(id, placa, marca, modelo, ano);
        this.numeroEixos = numeroEixos;
        this.capacidadeCarga = capacidadeCarga;
        this.comprimento = comprimento;
        this.localizacaoAtual = "";
        this.status = StatusVeiculo.DISPONIVEL;
        this.manutencaoPendente = false;
        this.dataUltimaManutencao = null;
    }

    public int getNumeroEixos() {
        return numeroEixos;
    }

    public void setNumeroEixos(int numeroEixos) {
        if (numeroEixos <= 0) {
            throw new IllegalArgumentException("Número de eixos deve ser maior que zero");
        }
        this.numeroEixos = numeroEixos;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(double capacidadeCarga) {
        if (capacidadeCarga <= 0) {
            throw new IllegalArgumentException("Capacidade de carga deve ser maior que zero");
        }
        this.capacidadeCarga = capacidadeCarga;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        if (comprimento <= 0) {
            throw new IllegalArgumentException("Comprimento deve ser maior que zero");
        }
        this.comprimento = comprimento;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public java.time.LocalDate getDataUltimaManutencao() {
        return dataUltimaManutencao;
    }

    @Override
    public double calcularCustoManutencao() {
        // Exemplo: custo base + custo por eixo + custo por tonelada
        return 800.0 + (numeroEixos * 200.0) + (capacidadeCarga * 100.0);
    }

    @Override
    public String obterLocalizacaoAtual() {
        return localizacaoAtual;
    }

    @Override
    public void atualizarLocalizacao(String novaLocalizacao) {
        this.localizacaoAtual = novaLocalizacao;
    }

    @Override
    public void agendarManutencao() {
        this.manutencaoPendente = true;
    }

    @Override
    public boolean verificarManutencaoPendente() {
        return manutencaoPendente;
    }

    // Método adicional para registrar conclusão de manutenção
    public void concluirManutencao() {
        this.manutencaoPendente = false;
        this.dataUltimaManutencao = java.time.LocalDate.now();
    }

    @Override
    public String toString() {
        return "Caminhão: " + super.toString() + 
               ", Eixos: " + numeroEixos + 
               ", Carga: " + capacidadeCarga + "t" +
               ", Comprimento: " + comprimento + "m" +
               ", Status: " + status;
    }
}