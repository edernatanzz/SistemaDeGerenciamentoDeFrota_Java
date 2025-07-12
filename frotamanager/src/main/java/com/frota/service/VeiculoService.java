package com.frota.service;

import com.frota.dao.CarroDAOImpl;
import com.frota.dao.VanDAOImpl;
import com.frota.dao.CaminhaoDAOImpl;
import com.frota.model.Carro;
import com.frota.model.Van;
import com.frota.model.Caminhao;
import java.util.List;

/**
 * Serviço para gerenciar veículos
 */
public class VeiculoService {
    private final CarroDAOImpl carroDAO;
    private final VanDAOImpl vanDAO;
    private final CaminhaoDAOImpl caminhaoDAO;

    public VeiculoService() {
        this.carroDAO = new CarroDAOImpl();
        this.vanDAO = new VanDAOImpl();
        this.caminhaoDAO = new CaminhaoDAOImpl();
    }

    // Métodos para Carro
    public List<Carro> listarCarros() { 
        return carroDAO.findAll(); 
    }
    
    public Carro buscarCarro(int id) { 
        return carroDAO.find(id); 
    }
    
    public void salvarCarro(Carro c) { 
        carroDAO.save(c); 
    }
    
    public void atualizarCarro(Carro c) { 
        carroDAO.update(c); 
    }
    
    public void excluirCarro(int id) { 
        carroDAO.delete(id); 
    }

    // Métodos para Van
    public List<Van> listarVans() { 
        return vanDAO.findAll(); 
    }
    
    public Van buscarVan(int id) { 
        return vanDAO.find(id); 
    }
    
    public void salvarVan(Van v) { 
        vanDAO.save(v); 
    }
    
    public void atualizarVan(Van v) { 
        vanDAO.update(v); 
    }
    
    public void excluirVan(int id) { 
        vanDAO.delete(id); 
    }

    // Métodos para Caminhão
    public List<Caminhao> listarCaminhoes() { 
        return caminhaoDAO.findAll(); 
    }
    
    public Caminhao buscarCaminhao(int id) { 
        return caminhaoDAO.find(id); 
    }
    
    public void salvarCaminhao(Caminhao c) { 
        caminhaoDAO.save(c); 
    }
    
    public void atualizarCaminhao(Caminhao c) { 
        caminhaoDAO.update(c); 
    }
    
    public void excluirCaminhao(int id) { 
        caminhaoDAO.delete(id); 
    }
}
