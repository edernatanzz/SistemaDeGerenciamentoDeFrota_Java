package com.frota.infrastructure.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.frota.infrastructure.database.DatabaseConnection;
import com.frota.model.Van;
import com.frota.repository.VeiculoDAO;

public class VanDAOImpl implements VeiculoDAO<Van> {
    private DatabaseConnection databaseConnection;

    public VanDAOImpl() {
        this.databaseConnection = new DatabaseConnection();
    }

    @Override
    public void save(Van van) {
        String sql = "INSERT INTO vans (placa, marca, modelo, ano, capacidade_passageiros, possui_acessibilidade, localizacao_atual) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, van.getPlaca());
            stmt.setString(2, van.getMarca());
            stmt.setString(3, van.getModelo());
            stmt.setInt(4, van.getAno());
            stmt.setInt(5, van.getCapacidadePassageiros());
            stmt.setBoolean(6, van.isPossuiAcessibilidade());
            stmt.setString(7, van.obterLocalizacaoAtual());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        van.setId(generatedKeys.getInt(1));
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar van: " + e.getMessage(), e);
        }
    }

    @Override
    public Van find(int id) {
        String sql = "SELECT * FROM vans WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVan(rs);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar van por ID: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public Van findByPlaca(String placa) {
        String sql = "SELECT * FROM vans WHERE placa = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVan(rs);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar van por placa: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public List<Van> findAll() {
        String sql = "SELECT * FROM vans";
        List<Van> vans = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                vans.add(mapResultSetToVan(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vans: " + e.getMessage(), e);
        }
        
        return vans;
    }

    @Override
    public void update(Van van) {
        String sql = "UPDATE vans SET placa = ?, marca = ?, modelo = ?, ano = ?, capacidade_passageiros = ?, possui_acessibilidade = ?, localizacao_atual = ? WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, van.getPlaca());
            stmt.setString(2, van.getMarca());
            stmt.setString(3, van.getModelo());
            stmt.setInt(4, van.getAno());
            stmt.setInt(5, van.getCapacidadePassageiros());
            stmt.setBoolean(6, van.isPossuiAcessibilidade());
            stmt.setString(7, van.obterLocalizacaoAtual());
            stmt.setInt(8, van.getId());
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar van: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM vans WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar van: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para mapear ResultSet para objeto Van
     */
    private Van mapResultSetToVan(ResultSet rs) throws SQLException {
        Van van = new Van(
            rs.getInt("id"),
            rs.getString("placa"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getInt("ano"),
            rs.getInt("capacidade_passageiros"),
            rs.getBoolean("possui_acessibilidade")
        );
        
        // Atualizar localização se não for nula
        String localizacao = rs.getString("localizacao_atual");
        if (localizacao != null) {
            van.atualizarLocalizacao(localizacao);
        }
        
        return van;
    }

    // Método adicional para buscar vans com acessibilidade
    public List<Van> findComAcessibilidade() {
        String sql = "SELECT * FROM vans WHERE possui_acessibilidade = true";
        List<Van> vans = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                vans.add(mapResultSetToVan(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vans com acessibilidade: " + e.getMessage(), e);
        }
        
        return vans;
    }

    // Método adicional para buscar por capacidade mínima
    public List<Van> findByCapacidadeMinima(int capacidadeMinima) {
        String sql = "SELECT * FROM vans WHERE capacidade_passageiros >= ?";
        List<Van> vans = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, capacidadeMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vans.add(mapResultSetToVan(rs));
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vans por capacidade mínima: " + e.getMessage(), e);
        }
        
        return vans;
    }
}
