package com.frota.repository;

import java.util.List;

import com.frota.model.Veiculo;

public interface VeiculoDAO<T extends Veiculo> {
    public void save (T veiculo);
    public T find(int id);
    public T findByPlaca(String placa);
    public List<T> findAll();
    public void update(T veiculo);
    public void delete(int id);
}