package com.frota.gui.dialogs;

import javax.swing.*;

/**
 * Diálogo para seleção do tipo de veículo
 */
public class DialogTipoVeiculo {
    
    /**
     * Enumeração dos tipos de veículos disponíveis
     */
    public enum TipoVeiculo { 
        CARRO, 
        VAN, 
        CAMINHAO
    }

    /**
     * Exibe o diálogo de seleção de tipo de veículo
     * 
     * @param parent Componente pai
     * @return O tipo selecionado ou null se cancelado
     */
    public static TipoVeiculo mostrarDialogo(JFrame parent) {
        Object[] options = {"Carro", "Van", "Caminhão"};
        int escolha = JOptionPane.showOptionDialog(
            parent,
            "Selecione o tipo de veículo:",
            "Tipo de Veículo",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        // Retorna o tipo selecionado ou null se cancelado
        switch (escolha) {
            case 0: return TipoVeiculo.CARRO;
            case 1: return TipoVeiculo.VAN;
            case 2: return TipoVeiculo.CAMINHAO;
            default: return null;
        }
    }
}
