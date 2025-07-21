package com.frota.infrastructure.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.frota.infrastructure.database.DatabaseConnection;
import com.frota.model.Caminhao;
import com.frota.model.enums.StatusVeiculo;
import com.frota.repository.VeiculoDAO;

public class CaminhaoDAOImpl implements VeiculoDAO<Caminhao> {
    private DatabaseConnection databaseConnection;
    
    public CaminhaoDAOImpl() {
        this.databaseConnection = new DatabaseConnection();
    }

    @Override
    public void save(Caminhao caminhao) {
        String sql = "INSERT INTO caminhoes (placa, marca, modelo, ano, numero_eixos, capacidade_carga, comprimento, localizacao_atual, manutencao_pendente, data_ultima_manutencao, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getMarca());
            stmt.setString(3, caminhao.getModelo());
            stmt.setInt(4, caminhao.getAno());
            stmt.setInt(5, caminhao.getNumeroEixos());
            stmt.setDouble(6, caminhao.getCapacidadeCarga());
            stmt.setDouble(7, caminhao.getComprimento());
            stmt.setString(8, caminhao.obterLocalizacaoAtual());
            stmt.setBoolean(9, caminhao.verificarManutencaoPendente());
            
            // Tratar data nula
            if (caminhao.getDataUltimaManutencao() != null) {
                stmt.setDate(10, Date.valueOf(caminhao.getDataUltimaManutencao()));
            } else {
                stmt.setNull(10, java.sql.Types.DATE);
            }
            
            stmt.setString(11, caminhao.getStatus().name());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        caminhao.setId(generatedKeys.getInt(1));
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar caminhão: " + e.getMessage(), e);
        }
    }

    @Override
    public Caminhao find(int id) {
        String sql = "SELECT * FROM caminhoes WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCaminhao(rs);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar caminhão por ID: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public Caminhao findByPlaca(String placa) {
        String sql = "SELECT * FROM caminhoes WHERE placa = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCaminhao(rs);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar caminhão por placa: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public List<Caminhao> findAll() {
        String sql = "SELECT * FROM caminhoes";
        List<Caminhao> caminhoes = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                caminhoes.add(mapResultSetToCaminhao(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os caminhões: " + e.getMessage(), e);
        }
        
        return caminhoes;
    }

    @Override
    public void update(Caminhao caminhao) {
        String sql = "UPDATE caminhoes SET placa = ?, marca = ?, modelo = ?, ano = ?, numero_eixos = ?, capacidade_carga = ?, comprimento = ?, localizacao_atual = ?, manutencao_pendente = ?, data_ultima_manutencao = ?, status = ? WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getMarca());
            stmt.setString(3, caminhao.getModelo());
            stmt.setInt(4, caminhao.getAno());
            stmt.setInt(5, caminhao.getNumeroEixos());
            stmt.setDouble(6, caminhao.getCapacidadeCarga());
            stmt.setDouble(7, caminhao.getComprimento());
            stmt.setString(8, caminhao.obterLocalizacaoAtual());
            stmt.setBoolean(9, caminhao.verificarManutencaoPendente());
            
            // Tratar data nula
            if (caminhao.getDataUltimaManutencao() != null) {
                stmt.setDate(10, Date.valueOf(caminhao.getDataUltimaManutencao()));
            } else {
                stmt.setNull(10, java.sql.Types.DATE);
            }
            
            stmt.setString(11, caminhao.getStatus().name());
            stmt.setInt(12, caminhao.getId());
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar caminhão: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM caminhoes WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar caminhão: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para mapear ResultSet para objeto Caminhao
     */
    private Caminhao mapResultSetToCaminhao(ResultSet rs) throws SQLException {
        Caminhao caminhao = new Caminhao(
            rs.getInt("id"),
            rs.getString("placa"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getInt("ano"),
            rs.getInt("numero_eixos"),
            rs.getDouble("capacidade_carga"),
            rs.getDouble("comprimento")
        );
        
        // Atualizar localização se não for nula
        String localizacao = rs.getString("localizacao_atual");
        if (localizacao != null) {
            caminhao.atualizarLocalizacao(localizacao);
        }
        
        // Definir status
        String statusString = rs.getString("status");
        if (statusString != null) {
            caminhao.setStatus(StatusVeiculo.valueOf(statusString));
        }
        
        // Definir manutenção pendente
        boolean manutencaoPendente = rs.getBoolean("manutencao_pendente");
        if (manutencaoPendente) {
            caminhao.agendarManutencao();
        }
        
        return caminhao;
    }

    // Métodos adicionais específicos para Caminhao
    public List<Caminhao> findByManutencaoPendente() {
        String sql = "SELECT * FROM caminhoes WHERE manutencao_pendente = true";
        List<Caminhao> caminhoes = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                caminhoes.add(mapResultSetToCaminhao(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar caminhões com manutenção pendente: " + e.getMessage(), e);
        }
        
        return caminhoes;
    }

    public List<Caminhao> findByStatus(StatusVeiculo status) {
        String sql = "SELECT * FROM caminhoes WHERE status = ?";
        List<Caminhao> caminhoes = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status.name());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    caminhoes.add(mapResultSetToCaminhao(rs));
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar caminhões por status: " + e.getMessage(), e);
        }
        
        return caminhoes;
    }
}
