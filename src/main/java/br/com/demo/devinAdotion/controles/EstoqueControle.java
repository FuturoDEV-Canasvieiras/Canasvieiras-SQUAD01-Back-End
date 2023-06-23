package br.com.demo.devinAdotion.controles;

import br.com.demo.devinAdotion.modelos.Estoque;
import br.com.demo.devinAdotion.servicos.EstoqueServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estoque")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EstoqueControle {

    @Autowired
    private EstoqueServico estoqueServico;

    // Busca/consulta estoque por ID
    //  GET - Listagem do estoque (findAll)

    /*
    @GetMapping
    public ResponseEntity<?> get(){
        try{
           return ResponseEntity.ok(estoqueServico.buscarTodos());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    */


    // ver se prefere o GET buscarTodos como acima ou abaixo
    @GetMapping
    public  ResponseEntity<List<Estoque>> buscarTodos() {
        return  ResponseEntity.status(HttpStatus.OK).body(estoqueServico.buscarTodos());
    }

    // buscar por id
    // método antigo
//    @GetMapping("{id}")
//    public ResponseEntity<Estoque> buscarId(@PathVariable Long id){
//        return ResponseEntity.status(HttpStatus.OK).body(estoqueServico.buscarId(id));
//    }

    @GetMapping("{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(estoqueServico.buscarId(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // cadastro produto no estoque

        // testar assim
//    @PostMapping(value = "/cadastro_produto")
//    public Long salvar (@RequestParam("armazem_id") Long armazem_id,
//                     @RequestParam("produto") String produto,
//                     @RequestParam("quantidade") Integer quantidade,
//                     @RequestParam("animal") String animal,
//                     @RequestParam("categoria") String categoria) throws Exception {
//        return ResponseEntity.ok().body(estoqueServico.salvar(armazem_id,produto,quantidade,animal,categoria)).getBody();
//    }

            // ou assim
//    @PostMapping(value = "/cadastro")
//    public ResponseEntity<Long> salvar(@RequestParam("armazem_id") Long armazem_id,
//                                       @RequestParam("produto") String produto,
//                                       @RequestParam("quantidade") Integer quantidade,
//                                       @RequestParam("animal") String animal,
//                                       @RequestParam("categoria") String categoria) throws Exception {
//        return ResponseEntity.ok().body(estoqueServico.salvar(armazem_id, produto, quantidade, animal, categoria));
//    }

    // 7 - Cadastro de Produto do estoque - vendo se vai arrumar o cadastro do estoque

//    @PostMapping
//    public ResponseEntity<Estoque> salvar(@RequestBody Estoque estoque) throws Exception {
//        return ResponseEntity.ok().body(estoqueServico.salvar(estoque));
//    }

    // teste para melhorar o retorno no caso de erro
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueSalvo = estoqueServico.salvar(estoque);
            return ResponseEntity.ok().body(estoqueSalvo);
        } catch (Exception e) {
            // Erro interno do servidor: 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // edita todos os campos
/*    @PutMapping("/editar/{id}")
    ResponseEntity<Estoque> editar(@PathVariable Long id, @RequestBody Estoque estoque){
        Estoque estoqueAtualizado = estoqueServico.buscarId(id);
        if (estoqueAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        estoque.setId(id);
        estoque.setProduto(estoque.getProduto());
        estoque.setQuantidade(estoque.getQuantidade());
        estoque.setAnimal(estoque.getAnimal());
        estoque.setCategoria(estoque.getCategoria());
        estoque.setArmazem(estoque.getArmazem());


        return ResponseEntity.status(HttpStatus.OK).body(estoqueServico.editar(estoque));
    }*/

    // editar somente produto e quantidade

    @PutMapping("{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody Estoque estoqueEditado) throws Exception {
        //buscar o estoque pelo id
        Estoque estoque = estoqueServico.buscarId(id);
        //verificar se o estoque existe
        if (estoque == null) {
            return ResponseEntity.notFound().build();
        }

        estoque.setProduto(estoqueEditado.getProduto());
        estoque.setQuantidade(estoqueEditado.getQuantidade());

        //Estoque estoqueAtualizado = estoqueServico.salvar(estoque);
        //return ResponseEntity.status(HttpStatus.OK).body(estoqueServico.editarProduto(estoque));
        //return ResponseEntity.ok(estoqueAtualizado);

        try {
            //fazer o update somente dos dados do produto e quantidade
            estoqueServico.editarProduto(estoque);
            //buscar os dados atualizados
            Estoque estoqueAtualizado = estoqueServico.buscarId(id);
            //retornar os dados atualizados
            return ResponseEntity.ok(estoqueAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao editar o Estoque");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarId(@PathVariable Long id){
        estoqueServico.deletarId(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}












