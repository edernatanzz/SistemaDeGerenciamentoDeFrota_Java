package com.frota.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import com.frota.gui.components.FormularioVeiculo;
import com.frota.gui.dialogs.DialogTipoVeiculo.TipoVeiculo;
import com.frota.gui.factory.VeiculoFactory;
import com.frota.model.Veiculo;
import com.frota.service.VeiculoService;

/**
 * Diálogo para cadastro e edição de veículos
 */
public class DialogCadastroVeiculo extends JDialog {
    
    private boolean salvou = false;
    private TipoVeiculo tipo;
    private Veiculo veiculo;
    private FormularioVeiculo formulario;
    private VeiculoService service;
    
    /**
     * Construtor para novos veículos
     * 
     * @param parent Janela pai
     * @param tipo Tipo do veículo
     */
    public DialogCadastroVeiculo(JFrame parent, TipoVeiculo tipo) {
        super(parent, "Cadastro de " + tipo, true);
        this.tipo = tipo;
        this.service = new VeiculoService();
        inicializar();
    }
    
    /**
     * Construtor para edição de veículos
     * 
     * @param parent Janela pai
     * @param tipo Tipo do veículo
     * @param veiculo Veículo a ser editado
     */
    public DialogCadastroVeiculo(JFrame parent, TipoVeiculo tipo, Veiculo veiculo) {
        super(parent, "Editar " + tipo, true);
        this.tipo = tipo;
        this.veiculo = veiculo;
        this.service = new VeiculoService();
        inicializar();
        
        // Preenche o formulário com os dados do veículo
        VeiculoFactory.preencherFormulario(veiculo, formulario);
    }
    
    /**
     * Inicializa os componentes do diálogo
     */
    private void inicializar() {
        setSize(400, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        
        // Cria o formulário adequado para o tipo
        formulario = VeiculoFactory.criarFormulario(tipo);
        
        // Botões
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(e -> {
            if (salvarVeiculo()) {
                salvou = true;
                dispose();
            }
        });
        
        btnCancelar.addActionListener(e -> {
            salvou = false;
            dispose();
        });
        
        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        
        // Adiciona componentes ao diálogo
        add(new JScrollPane(formulario), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    /**
     * Salva o veículo no banco de dados
     * 
     * @return true se o veículo foi salvo com sucesso, false caso contrário
     */
    private boolean salvarVeiculo() {
        try {
            // Valida os dados do formulário
            if (!formulario.validarDados()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos corretamente.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Se for uma edição, atualiza o veículo existente
            if (veiculo != null) {
                atualizarVeiculo();
            } else {
                // Se for um novo veículo, cria e salva
                Veiculo novoVeiculo = VeiculoFactory.criarVeiculo(tipo, formulario);
                salvarNovoVeiculo(novoVeiculo);
            }
            
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Salva um novo veículo no banco de dados
     * 
     * @param novoVeiculo O veículo a ser salvo
     */
    private void salvarNovoVeiculo(Veiculo novoVeiculo) {
        switch (tipo) {
            case CARRO:
                service.salvarCarro((com.frota.model.Carro) novoVeiculo);
                break;
            case VAN:
                service.salvarVan((com.frota.model.Van) novoVeiculo);
                break;
            case CAMINHAO:
                service.salvarCaminhao((com.frota.model.Caminhao) novoVeiculo);
                break;
        }
    }
    
    /**
     * Atualiza um veículo existente no banco de dados
     */
    private void atualizarVeiculo() {
        switch (tipo) {
            case CARRO:
                com.frota.model.Carro carro = (com.frota.model.Carro) veiculo;
                FormularioVeiculo formCarro = formulario;
                
                carro.setPlaca(formCarro.getPlaca());
                carro.setMarca(formCarro.getMarca());
                carro.setModelo(formCarro.getModelo());
                carro.setAno(formCarro.getAno());
                
                if (formCarro instanceof com.frota.gui.components.FormularioCarro) {
                    com.frota.gui.components.FormularioCarro fc = (com.frota.gui.components.FormularioCarro) formCarro;
                    carro.setNumeroPortas(fc.getNumeroPortas());
                    carro.setTipoCarroceria(fc.getTipoCarroceria());
                    carro.setCapacidadeTanque(fc.getCapacidadeTanque());
                }
                
                service.atualizarCarro(carro);
                break;
                
            case VAN:
                com.frota.model.Van van = (com.frota.model.Van) veiculo;
                FormularioVeiculo formVan = formulario;
                
                van.setPlaca(formVan.getPlaca());
                van.setMarca(formVan.getMarca());
                van.setModelo(formVan.getModelo());
                van.setAno(formVan.getAno());
                
                if (formVan instanceof com.frota.gui.components.FormularioVan) {
                    com.frota.gui.components.FormularioVan fv = (com.frota.gui.components.FormularioVan) formVan;
                    van.setCapacidadePassageiros(fv.getCapacidadePassageiros());
                    van.setPossuiAcessibilidade(fv.isPossuiAcessibilidade());
                }
                
                service.atualizarVan(van);
                break;
                
            case CAMINHAO:
                com.frota.model.Caminhao caminhao = (com.frota.model.Caminhao) veiculo;
                FormularioVeiculo formCaminhao = formulario;
                
                caminhao.setPlaca(formCaminhao.getPlaca());
                caminhao.setMarca(formCaminhao.getMarca());
                caminhao.setModelo(formCaminhao.getModelo());
                caminhao.setAno(formCaminhao.getAno());
                
                if (formCaminhao instanceof com.frota.gui.components.FormularioCaminhao) {
                    com.frota.gui.components.FormularioCaminhao fc = (com.frota.gui.components.FormularioCaminhao) formCaminhao;
                    caminhao.setNumeroEixos(fc.getNumeroEixos());
                    caminhao.setCapacidadeCarga(fc.getCapacidadeCarga());
                    caminhao.setComprimento(fc.getComprimento());
                }
                
                service.atualizarCaminhao(caminhao);
                break;
        }
    }
    
    /**
     * Verifica se o veículo foi salvo com sucesso
     * 
     * @return true se o veículo foi salvo, false caso contrário
     */
    public boolean salvouVeiculo() {
        return salvou;
    }
}
