package br.com.demo.devinAdotion.servicos;

import br.com.demo.devinAdotion.modelos.Armazem;
import br.com.demo.devinAdotion.modelos.Estoque;
import br.com.demo.devinAdotion.repositorios.EstoqueRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueServico {

    @Autowired
    private EstoqueRepositorio estoqueRepositorio;

    @Autowired
    private ArmazemServico armazemServico;


    // 4 - Listagem do estoque

    public List<Estoque> buscarTodos(){
        return estoqueRepositorio.findAll();
    }

    public Estoque buscarId(Long id){
        Optional<Estoque> estoque = estoqueRepositorio.findById(id);
        return (estoque.isPresent() ? estoque.get() : null);
    }
//
//    // 7 - Cadastro de Produto do estoque
//    public  Long salvar (
//            Long armazem_id,
//            String produto,
//            Integer quantidade,
//            String animal,
//            String categoria
//    ) throws Exception {
//        Estoque estoque = new Estoque();
//        Armazem armazem = armazemServico.buscarPorId(armazem_id);
//        estoque.setProduto(produto);
//        estoque.setQuantidade(quantidade);
//        estoque.setAnimal(animal);
//        estoque.setCategoria(categoria);
//        return estoqueRepositorio.save(estoque).getId();
//    }



    // 7 - Cadastro de Produto do estoque - vendo se vai arrumar o cadastro do estoque
    public Estoque salvar (Estoque estoque) throws Exception {
        Armazem armazem = armazemServico.buscarPorId(estoque.getArmazem().getId());
        estoque.setArmazem(armazem);
        return estoqueRepositorio.save(estoque);
    }

    // 5 - Editar produto do estoque

    public void editarProduto (Estoque estoque) {
        estoqueRepositorio.updateProdutoAndQuantidadeById(estoque.getProduto(), estoque.getQuantidade(), estoque.getId());
    }

    // 6 - Remover item

    public void deletarId (Long id) {
        estoqueRepositorio.deleteById(id);
    }

}
