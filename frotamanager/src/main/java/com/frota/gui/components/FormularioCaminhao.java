package com.frota.gui.components;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Formulário específico para Caminhões
 */
public class FormularioCaminhao extends FormularioVeiculo {
    
    private JTextField tfNumEixos;
    private JTextField tfCapacidadeCarga;
    private JTextField tfComprimento;
    
    public FormularioCaminhao() {
        super();
    }
    
    @Override
    protected void adicionarCamposEspecificos() {
        tfNumEixos = new JTextField(2);
        tfCapacidadeCarga = new JTextField(6);
        tfComprimento = new JTextField(5);
        
        add(new JLabel("Número de Eixos:"));
        add(tfNumEixos);
        add(new JLabel("Capacidade de Carga (kg):"));
        add(tfCapacidadeCarga);
        add(new JLabel("Comprimento (m):"));
        add(tfComprimento);
    }
    
    @Override
    public boolean validarDados() {
        try {
            if (getPlaca().isEmpty() || getMarca().isEmpty() || getModelo().isEmpty()) {
                return false;
            }
            
            int eixos = Integer.parseInt(tfNumEixos.getText().trim());
            double carga = Double.parseDouble(tfCapacidadeCarga.getText().trim());
            double comprimento = Double.parseDouble(tfComprimento.getText().trim());
            
            return eixos > 0 && carga > 0 && comprimento > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public Object[] obterDadosFormulario() {
        int eixos = Integer.parseInt(tfNumEixos.getText().trim());
        double carga = Double.parseDouble(tfCapacidadeCarga.getText().trim());
        double comprimento = Double.parseDouble(tfComprimento.getText().trim());
        
        return new Object[]{
            getPlaca(),
            getMarca(),
            getModelo(),
            getAno(),
            eixos,
            carga,
            comprimento
        };
    }
    
    @Override
    public void preencherFormulario(Object[] dados) {
        if (dados.length >= 7) {
            tfPlaca.setText(dados[0].toString());
            tfMarca.setText(dados[1].toString());
            tfModelo.setText(dados[2].toString());
            tfAno.setText(dados[3].toString());
            tfNumEixos.setText(dados[4].toString());
            tfCapacidadeCarga.setText(dados[5].toString());
            tfComprimento.setText(dados[6].toString());
        }
    }
    
    public int getNumeroEixos() {
        try {
            return Integer.parseInt(tfNumEixos.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public double getCapacidadeCarga() {
        try {
            return Double.parseDouble(tfCapacidadeCarga.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public double getComprimento() {
        try {
            return Double.parseDouble(tfComprimento.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
