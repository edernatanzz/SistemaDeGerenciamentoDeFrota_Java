package com.frota.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame implements NavegacaoListener {
    private static MainFrame instance;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, AbstractPanel> paineis;

    private MainFrame() {
        super("Sistema de Gerenciamento de Frota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        paineis = new HashMap<>();

        criarMenu();
        adicionarPainel("inicial", new PainelInicial(this));
        mostrarPainel("inicial");

        add(mainPanel, BorderLayout.CENTER);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Navegação");

        JMenuItem itemVeiculos = new JMenuItem("Veículos");
        JMenuItem itemMotoristas = new JMenuItem("Motoristas");
        JMenuItem itemOperacoes = new JMenuItem("Operações");
        JMenuItem itemInicio = new JMenuItem("Início");

        itemInicio.addActionListener(e -> mostrarPainel("inicial"));
        itemVeiculos.addActionListener(e -> {
            // Verifica se o painel de veículos já existe
            if (!paineis.containsKey("veiculos")) {
                adicionarPainel("veiculos", new PainelVeiculos(this));
            }
            mostrarPainel("veiculos");
        });
        // Os outros itens podem ser conectados a outros painéis futuramente

        menu.add(itemInicio);
        menu.add(itemVeiculos);
        menu.add(itemMotoristas);
        menu.add(itemOperacoes);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void adicionarPainel(String nome, AbstractPanel painel) {
        paineis.put(nome, painel);
        mainPanel.add(painel, nome);
    }

    public void mostrarPainel(String nome) {
        cardLayout.show(mainPanel, nome);
    }

    @Override
    public void navegarPara(String painel) {
        mostrarPainel(painel);
    }
} 