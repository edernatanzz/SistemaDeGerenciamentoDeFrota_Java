package com.frota.gui.factory;

import com.frota.gui.components.FormularioCaminhao;
import com.frota.gui.components.FormularioCarro;
import com.frota.gui.components.FormularioVan;
import com.frota.gui.components.FormularioVeiculo;
import com.frota.gui.dialogs.DialogTipoVeiculo.TipoVeiculo;
import com.frota.model.Caminhao;
import com.frota.model.Carro;
import com.frota.model.Van;
import com.frota.model.Veiculo;

/**
 * Fábrica para criar formulários e veículos baseados no tipo
 */
public class VeiculoFactory {
    
    /**
     * Cria um formulário baseado no tipo de veículo
     * 
     * @param tipo O tipo de veículo
     * @return O formulário adequado para o tipo
     */
    public static FormularioVeiculo criarFormulario(TipoVeiculo tipo) {
        switch (tipo) {
            case CARRO:
                return new FormularioCarro();
            case VAN:
                return new FormularioVan();
            case CAMINHAO:
                return new FormularioCaminhao();
            default:
                throw new IllegalArgumentException("Tipo de veículo não suportado");
        }
    }
    
    /**
     * Cria um objeto veículo baseado no tipo e nos dados do formulário
     * 
     * @param tipo O tipo de veículo
     * @param formulario O formulário com os dados
     * @return O veículo criado
     */
    public static Veiculo criarVeiculo(TipoVeiculo tipo, FormularioVeiculo formulario) {
        switch (tipo) {
            case CARRO:
                FormularioCarro formCarro = (FormularioCarro) formulario;
                return new Carro(
                    0, // ID será gerado pelo banco
                    formCarro.getPlaca(),
                    formCarro.getMarca(),
                    formCarro.getModelo(),
                    formCarro.getAno(),
                    formCarro.getNumeroPortas(),
                    formCarro.getTipoCarroceria(),
                    formCarro.getCapacidadeTanque()
                );
                
            case VAN:
                FormularioVan formVan = (FormularioVan) formulario;
                return new Van(
                    0, // ID será gerado pelo banco
                    formVan.getPlaca(),
                    formVan.getMarca(),
                    formVan.getModelo(),
                    formVan.getAno(),
                    formVan.getCapacidadePassageiros(),
                    formVan.isPossuiAcessibilidade()
                );
                
            case CAMINHAO:
                FormularioCaminhao formCaminhao = (FormularioCaminhao) formulario;
                return new Caminhao(
                    0, // ID será gerado pelo banco
                    formCaminhao.getPlaca(),
                    formCaminhao.getMarca(),
                    formCaminhao.getModelo(),
                    formCaminhao.getAno(),
                    formCaminhao.getNumeroEixos(),
                    formCaminhao.getCapacidadeCarga(),
                    formCaminhao.getComprimento()
                );
                
            default:
                throw new IllegalArgumentException("Tipo de veículo não suportado");
        }
    }
    
    /**
     * Preenche um formulário com os dados de um veículo existente
     * 
     * @param veiculo O veículo com os dados
     * @param formulario O formulário a ser preenchido
     */
    public static void preencherFormulario(Veiculo veiculo, FormularioVeiculo formulario) {
        if (veiculo instanceof Carro && formulario instanceof FormularioCarro) {
            Carro carro = (Carro) veiculo;
            Object[] dados = {
                carro.getPlaca(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getAno(),
                carro.getNumeroPortas(),
                carro.getTipoCarroceria(),
                carro.getCapacidadeTanque()
            };
            formulario.preencherFormulario(dados);
            
        } else if (veiculo instanceof Van && formulario instanceof FormularioVan) {
            Van van = (Van) veiculo;
            Object[] dados = {
                van.getPlaca(),
                van.getMarca(),
                van.getModelo(),
                van.getAno(),
                van.getCapacidadePassageiros(),
                van.isPossuiAcessibilidade()
            };
            formulario.preencherFormulario(dados);
            
        } else if (veiculo instanceof Caminhao && formulario instanceof FormularioCaminhao) {
            Caminhao caminhao = (Caminhao) veiculo;
            Object[] dados = {
                caminhao.getPlaca(),
                caminhao.getMarca(),
                caminhao.getModelo(),
                caminhao.getAno(),
                caminhao.getNumeroEixos(),
                caminhao.getCapacidadeCarga(),
                caminhao.getComprimento()
            };
            formulario.preencherFormulario(dados);
        }
    }
}
