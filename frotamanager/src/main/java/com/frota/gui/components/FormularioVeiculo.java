package com.frota.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Classe base para formulários de veículos
 */
public abstract class FormularioVeiculo extends JPanel {
    
    /**
     * Campos comuns para todos os veículos
     */
    protected JTextField tfPlaca;
    protected JTextField tfMarca;
    protected JTextField tfModelo;
    protected JTextField tfAno;
    
    public FormularioVeiculo() {
        setLayout(new GridLayout(0, 2, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Inicializa campos comuns
        tfPlaca = new JTextField(10);
        tfMarca = new JTextField(10);
        tfModelo = new JTextField(10);
        tfAno = new JTextField(4);
        
        // Adiciona campos comuns ao painel
        add(new JLabel("Placa:"));
        add(tfPlaca);
        add(new JLabel("Marca:"));
        add(tfMarca);
        add(new JLabel("Modelo:"));
        add(tfModelo);
        add(new JLabel("Ano:"));
        add(tfAno);
        
        // Adiciona campos específicos
        adicionarCamposEspecificos();
    }
    
    /**
     * Método abstrato para adicionar campos específicos do tipo de veículo
     */
    protected abstract void adicionarCamposEspecificos();
    
    /**
     * Método abstrato para validar os dados do formulário
     * @return true se válido, false caso contrário
     */
    public abstract boolean validarDados();
    
    /**
     * Método abstrato para obter os dados do formulário
     * @return array com os dados do formulário
     */
    public abstract Object[] obterDadosFormulario();
    
    /**
     * Método abstrato para preencher o formulário com dados existentes
     * @param dados Array com os dados a serem preenchidos
     */
    public abstract void preencherFormulario(Object[] dados);
    
    /**
     * Obtém a placa digitada no formulário
     */
    public String getPlaca() {
        return tfPlaca.getText().trim();
    }
    
    /**
     * Obtém a marca digitada no formulário
     */
    public String getMarca() {
        return tfMarca.getText().trim();
    }
    
    /**
     * Obtém o modelo digitado no formulário
     */
    public String getModelo() {
        return tfModelo.getText().trim();
    }
    
    /**
     * Obtém o ano digitado no formulário
     */
    public int getAno() {
        try {
            return Integer.parseInt(tfAno.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
