package com.frota.gui.components;

import javax.swing.*;

/**
 * Formulário específico para Carros
 */
public class FormularioCarro extends FormularioVeiculo {
    
    private JTextField tfNumPortas;
    private JTextField tfTipoCarroceria;
    private JTextField tfCapacidadeTanque;
    
    public FormularioCarro() {
        super();
    }
    
    @Override
    protected void adicionarCamposEspecificos() {
        tfNumPortas = new JTextField(2);
        tfTipoCarroceria = new JTextField(10);
        tfCapacidadeTanque = new JTextField(5);
        
        add(new JLabel("Número de Portas:"));
        add(tfNumPortas);
        add(new JLabel("Tipo de Carroceria:"));
        add(tfTipoCarroceria);
        add(new JLabel("Capacidade do Tanque (L):"));
        add(tfCapacidadeTanque);
    }
    
    @Override
    public boolean validarDados() {
        try {
            if (getPlaca().isEmpty() || getMarca().isEmpty() || getModelo().isEmpty()) {
                return false;
            }
            
            int portas = Integer.parseInt(tfNumPortas.getText().trim());
            double capTanque = Double.parseDouble(tfCapacidadeTanque.getText().trim());
            
            return portas > 0 && capTanque > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public Object[] obterDadosFormulario() {
        int portas = Integer.parseInt(tfNumPortas.getText().trim());
        String tipoCarroceria = tfTipoCarroceria.getText().trim();
        double capTanque = Double.parseDouble(tfCapacidadeTanque.getText().trim());
        
        return new Object[]{
            getPlaca(),
            getMarca(),
            getModelo(),
            getAno(),
            portas,
            tipoCarroceria,
            capTanque
        };
    }
    
    @Override
    public void preencherFormulario(Object[] dados) {
        if (dados.length >= 7) {
            tfPlaca.setText(dados[0].toString());
            tfMarca.setText(dados[1].toString());
            tfModelo.setText(dados[2].toString());
            tfAno.setText(dados[3].toString());
            tfNumPortas.setText(dados[4].toString());
            tfTipoCarroceria.setText(dados[5].toString());
            tfCapacidadeTanque.setText(dados[6].toString());
        }
    }
    
    public int getNumeroPortas() {
        try {
            return Integer.parseInt(tfNumPortas.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public String getTipoCarroceria() {
        return tfTipoCarroceria.getText().trim();
    }
    
    public double getCapacidadeTanque() {
        try {
            return Double.parseDouble(tfCapacidadeTanque.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
