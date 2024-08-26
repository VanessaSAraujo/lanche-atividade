package com.lanchenlayer.repositories;


import com.lanchenlayer.entities.Produto;
import com.lanchenlayer.interfaces.IProdutoRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutoRepositoryMySql implements IProdutoRepository{
    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/lanchonete";
        String user = "root";
        String password = "password";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void adicionar(Produto produto) {
        String sql = "INSERT INTO produtos (id, descricao, valor, imagem) VALUES (?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, produto.getId());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setFloat(3, produto.getValor());
            pstmt.setString(4, produto.getImagem());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getFloat("valor"),
                    rs.getString("imagem")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    @Override
    public ArrayList<Produto> buscarTodos() {
        String sql = "SELECT * FROM produtos";
        ArrayList<Produto> produtos = new ArrayList<>();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getFloat("valor"),
                    rs.getString("imagem")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }
}
