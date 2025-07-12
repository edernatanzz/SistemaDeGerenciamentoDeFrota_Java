package com.frota.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.frota.service.VeiculoService;
import com.frota.model.Veiculo;
import com.frota.model.Carro;
import com.frota.model.Van;
import com.frota.model.Caminhao;
import com.frota.gui.dialogs.DialogTipoVeiculo;
import com.frota.gui.dialogs.DialogCadastroVeiculo;

/**
 * Painel para gerenciamento de veículos
 */
public class PainelVeiculos extends AbstractPanel {
    
    private VeiculoService veiculoService;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    // Botões
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;
    
    /**
     * Construtor
     * 
     * @param nav Listener de navegação
     */
    public PainelVeiculos(NavegacaoListener nav) {
        super(nav);
        veiculoService = new VeiculoService();
        
        setLayout(new BorderLayout());
        add(criarCabecalho(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);
        
        carregarVeiculos();
    }
    
    /**
     * Cria o cabeçalho do painel
     * 
     * @return O painel de cabeçalho
     */
    private JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gerenciamento de Veículos", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        painel.add(titulo, BorderLayout.CENTER);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return painel;
    }
    
    /**
     * Cria a tabela para listagem de veículos
     * 
     * @return O painel com a tabela
     */
    private JScrollPane criarTabela() {
        // Colunas da tabela
        String[] colunas = {"ID", "Tipo", "Placa", "Marca", "Modelo", "Ano", "Detalhes"};
        
        // Modelo da tabela (não editável)
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Criação da tabela
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(24);
        
        // Listener para controlar botões de edição/exclusão
        tabela.getSelectionModel().addListSelectionListener(e -> {
            boolean linhaSelecionada = tabela.getSelectedRow() != -1;
            btnEditar.setEnabled(linhaSelecionada);
            btnExcluir.setEnabled(linhaSelecionada);
        });
        
        // Retorna a tabela com scroll
        return new JScrollPane(tabela);
    }
    
    /**
     * Cria o painel de botões
     * 
     * @return O painel de botões
     */
    private JPanel criarBotoes() {
        JPanel painel = new JPanel(new FlowLayout());
        
        btnNovo = new JButton("Novo Veículo");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar");
        
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        
        // Define as ações dos botões
        btnNovo.addActionListener(e -> novoVeiculo());
        btnEditar.addActionListener(e -> editarVeiculo());
        btnExcluir.addActionListener(e -> excluirVeiculo());
        btnAtualizar.addActionListener(e -> carregarVeiculos());
        
        // Adiciona os botões ao painel
        painel.add(btnNovo);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        painel.add(btnAtualizar);
        
        return painel;
    }
    
    /**
     * Carrega os veículos na tabela
     */
    private void carregarVeiculos() {
        // Limpa a tabela
        modeloTabela.setRowCount(0);
        
        // Carrega os carros
        List<Carro> carros = veiculoService.listarCarros();
        for (Carro carro : carros) {
            modeloTabela.addRow(new Object[]{
                carro.getId(),
                "Carro",
                carro.getPlaca(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getAno(),
                carro.getNumeroPortas() + " portas, " + carro.getTipoCarroceria()
            });
        }
        
        // Carrega as vans
        List<Van> vans = veiculoService.listarVans();
        for (Van van : vans) {
            modeloTabela.addRow(new Object[]{
                van.getId(),
                "Van",
                van.getPlaca(),
                van.getMarca(),
                van.getModelo(),
                van.getAno(),
                van.getCapacidadePassageiros() + " passageiros, Acessibilidade: " + (van.isPossuiAcessibilidade() ? "Sim" : "Não")
            });
        }
        
        // Carrega os caminhões
        List<Caminhao> caminhoes = veiculoService.listarCaminhoes();
        for (Caminhao caminhao : caminhoes) {
            modeloTabela.addRow(new Object[]{
                caminhao.getId(),
                "Caminhão",
                caminhao.getPlaca(),
                caminhao.getMarca(),
                caminhao.getModelo(),
                caminhao.getAno(),
                caminhao.getNumeroEixos() + " eixos, " + caminhao.getCapacidadeCarga() + " kg"
            });
        }
    }
    
    /**
     * Abre o diálogo para cadastro de novo veículo
     */
    private void novoVeiculo() {
        // Mostra diálogo para seleção do tipo
        DialogTipoVeiculo.TipoVeiculo tipo = DialogTipoVeiculo.mostrarDialogo(
            (JFrame) SwingUtilities.getWindowAncestor(this)
        );
        
        // Se um tipo foi selecionado, exibe o diálogo de cadastro
        if (tipo != null) {
            DialogCadastroVeiculo dialog = new DialogCadastroVeiculo(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                tipo
            );
            
            dialog.setVisible(true);
            
            // Se o veículo foi salvo, recarrega a tabela
            if (dialog.salvouVeiculo()) {
                carregarVeiculos();
            }
        }
    }
    
    /**
     * Abre o diálogo para edição de veículo existente
     */
    private void editarVeiculo() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        // Obtém dados da linha selecionada
        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String tipoVeiculo = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        
        // Busca o veículo no banco de dados
        Veiculo veiculo = null;
        DialogTipoVeiculo.TipoVeiculo tipo = null;
        
        switch (tipoVeiculo) {
            case "Carro":
                veiculo = veiculoService.buscarCarro(id);
                tipo = DialogTipoVeiculo.TipoVeiculo.CARRO;
                break;
                
            case "Van":
                veiculo = veiculoService.buscarVan(id);
                tipo = DialogTipoVeiculo.TipoVeiculo.VAN;
                break;
                
            case "Caminhão":
                veiculo = veiculoService.buscarCaminhao(id);
                tipo = DialogTipoVeiculo.TipoVeiculo.CAMINHAO;
                break;
        }
        
        // Se o veículo foi encontrado, exibe o diálogo de edição
        if (veiculo != null && tipo != null) {
            DialogCadastroVeiculo dialog = new DialogCadastroVeiculo(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                tipo,
                veiculo
            );
            
            dialog.setVisible(true);
            
            // Se o veículo foi salvo, recarrega a tabela
            if (dialog.salvouVeiculo()) {
                carregarVeiculos();
            }
        }
    }
    
    /**
     * Exclui o veículo selecionado
     */
    private void excluirVeiculo() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        // Obtém dados da linha selecionada
        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String tipoVeiculo = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String placa = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        
        // Confirmação
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir o veículo " + tipoVeiculo + " (Placa: " + placa + ")?",
            "Confirmação de Exclusão",
            JOptionPane.YES_NO_OPTION
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            // Exclui o veículo baseado no tipo
            try {
                switch (tipoVeiculo) {
                    case "Carro":
                        veiculoService.excluirCarro(id);
                        break;
                        
                    case "Van":
                        veiculoService.excluirVan(id);
                        break;
                        
                    case "Caminhão":
                        veiculoService.excluirCaminhao(id);
                        break;
                }
                
                // Recarrega a tabela
                carregarVeiculos();
                
                JOptionPane.showMessageDialog(
                    this,
                    "Veículo excluído com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao excluir veículo: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
