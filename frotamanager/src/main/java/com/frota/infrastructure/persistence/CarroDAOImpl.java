package com.frota.infrastructure.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.frota.infrastructure.database.DatabaseConnection;
import com.frota.model.Carro;
import com.frota.repository.VeiculoDAO;

public class CarroDAOImpl implements VeiculoDAO<Carro> {
    private DatabaseConnection databaseConnection;

    public CarroDAOImpl() {
        this.databaseConnection = new DatabaseConnection();
    }

    @Override
    public void save(Carro carro) {
        String sql = "INSERT INTO carros (placa, marca, modelo, ano, numero_portas, tipo_carroceria, capacidade_tanque, localizacao_atual) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, carro.getPlaca());
            stmt.setString(2, carro.getMarca());
            stmt.setString(3, carro.getModelo());
            stmt.setInt(4, carro.getAno());
            stmt.setInt(5, carro.getNumeroPortas());
            stmt.setString(6, carro.getTipoCarroceria());
            stmt.setDouble(7, carro.getCapacidadeTanque());
            stmt.setString(8, carro.obterLocalizacaoAtual());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        carro.setId(generatedKeys.getInt(1));
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar carro: " + e.getMessage(), e);
        }
    }

    @Override
    public Carro find(int id) {
        String sql = "SELECT * FROM carros WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCarro(rs);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar carro por ID: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public Carro findByPlaca(String placa) {
        String sql = "SELECT * FROM carros WHERE placa = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCarro(rs);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar carro por placa: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public List<Carro> findAll() {
        String sql = "SELECT * FROM carros";
        List<Carro> carros = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                carros.add(mapResultSetToCarro(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os carros: " + e.getMessage(), e);
        }
        
        return carros;
    }

    @Override
    public void update(Carro carro) {
        String sql = "UPDATE carros SET placa = ?, marca = ?, modelo = ?, ano = ?, numero_portas = ?, tipo_carroceria = ?, capacidade_tanque = ?, localizacao_atual = ? WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, carro.getPlaca());
            stmt.setString(2, carro.getMarca());
            stmt.setString(3, carro.getModelo());
            stmt.setInt(4, carro.getAno());
            stmt.setInt(5, carro.getNumeroPortas());
            stmt.setString(6, carro.getTipoCarroceria());
            stmt.setDouble(7, carro.getCapacidadeTanque());
            stmt.setString(8, carro.obterLocalizacaoAtual());
            stmt.setInt(9, carro.getId());
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar carro: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM carros WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar carro: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para mapear ResultSet para objeto Carro
     */
    private Carro mapResultSetToCarro(ResultSet rs) throws SQLException {
        Carro carro = new Carro(
            rs.getInt("id"),
            rs.getString("placa"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getInt("ano"),
            rs.getInt("numero_portas"),
            rs.getString("tipo_carroceria"),
            rs.getDouble("capacidade_tanque")
        );
        
        // Atualizar localização se não for nula
        String localizacao = rs.getString("localizacao_atual");
        if (localizacao != null) {
            carro.atualizarLocalizacao(localizacao);
        }
        
        return carro;
    }
}
