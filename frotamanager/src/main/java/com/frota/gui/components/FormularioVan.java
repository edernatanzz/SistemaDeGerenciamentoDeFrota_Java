package com.frota.gui.components;

import javax.swing.*;

/**
 * Formulário específico para Vans
 */
public class FormularioVan extends FormularioVeiculo {
    
    private JTextField tfCapacidadePassageiros;
    private JCheckBox cbAcessibilidade;
    
    public FormularioVan() {
        super();
    }
    
    @Override
    protected void adicionarCamposEspecificos() {
        tfCapacidadePassageiros = new JTextField(3);
        cbAcessibilidade = new JCheckBox("Possui Acessibilidade");
        
        add(new JLabel("Capacidade de Passageiros:"));
        add(tfCapacidadePassageiros);
        add(new JLabel("Acessibilidade:"));
        add(cbAcessibilidade);
    }
    
    @Override
    public boolean validarDados() {
        try {
            if (getPlaca().isEmpty() || getMarca().isEmpty() || getModelo().isEmpty()) {
                return false;
            }
            
            int capacidade = Integer.parseInt(tfCapacidadePassageiros.getText().trim());
            return capacidade > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public Object[] obterDadosFormulario() {
        int capacidade = Integer.parseInt(tfCapacidadePassageiros.getText().trim());
        boolean acessibilidade = cbAcessibilidade.isSelected();
        
        return new Object[]{
            getPlaca(),
            getMarca(),
            getModelo(),
            getAno(),
            capacidade,
            acessibilidade
        };
    }
    
    @Override
    public void preencherFormulario(Object[] dados) {
        if (dados.length >= 6) {
            tfPlaca.setText(dados[0].toString());
            tfMarca.setText(dados[1].toString());
            tfModelo.setText(dados[2].toString());
            tfAno.setText(dados[3].toString());
            tfCapacidadePassageiros.setText(dados[4].toString());
            cbAcessibilidade.setSelected((Boolean) dados[5]);
        }
    }
    
    public int getCapacidadePassageiros() {
        try {
            return Integer.parseInt(tfCapacidadePassageiros.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public boolean isPossuiAcessibilidade() {
        return cbAcessibilidade.isSelected();
    }
}
