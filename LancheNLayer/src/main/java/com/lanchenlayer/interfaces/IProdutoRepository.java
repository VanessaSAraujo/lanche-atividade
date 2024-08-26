package com.lanchenlayer.interfaces;

import com.lanchenlayer.entities.Produto;
import java.util.ArrayList;

public interface IProdutoRepository {
    void adicionar(Produto produto);
    void remover(int id);
    Produto buscarPorId(int id);
    ArrayList<Produto> buscarTodos();
}
