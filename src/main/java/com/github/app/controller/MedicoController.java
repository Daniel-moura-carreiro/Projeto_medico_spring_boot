package com.github.app.controller;

import com.github.app.AppApplication;
import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;  //já importa todos os que estão comentados abaixo

import com.github.app.model.medico.DadosAtualizacaoMedico;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import com.github.app.model.medico.DadosCadastroMedico;
import com.github.app.model.medico.DadosListagemMedico;
import com.github.app.model.medico.Medico;
import com.github.app.model.medico.MedicoRepository;

import jakarta.transaction.Transactional;
import lombok.experimental.var;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController //SPRING WEB - Informa para o Springboot que abaixo é uma classe controladora de requisições (GET-POST-PUT-DELETE)
@RequestMapping("/medicos") //SPRING WEB - Cria um caminho (endpoint) para a classe MedicoCotroller
public class MedicoController {

    private final AppApplication appApplication;
    @Autowired //Sobreescrevendo algo. É um padrão utilizado na injeção de dependência.
    private MedicoRepository repository; //tem que importar

    MedicoController(AppApplication appApplication) {
        this.appApplication = appApplication;
    }
    
    @PostMapping //SPRING WEB - Informa que o método abaixo é do tipo POST (cadastrar)
    @Transactional // SPRING DATA JPA infor ao spring abaixo é do tipo POST (cadastrar)
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping("todos") // informa que o método abaixo é do tipo GET (buscar/ler)
    public List<Medico> listarTodos() {
        return repository.findAll();
    }
    @GetMapping // informa que o método abaixo é do tipo GET (buscar/ler)
    public List<DadosListagemMedico> listar() {
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
        //findAll() -> Método que retorna uma lista de objeto do tipo DadosListagemMedico. 
        //stream() -> Método utilizado para transformar a lista em um fluxo de dados, permitindo aplicar operações de transformações.
        //map(DadosListagemMedico::new) -> Método utilizado para converter cada objeto do tipo medico em um json DadosListagemMedico, utilizando o constructor que criamos em DadosListagemMedico. 
        //toList() -> Método utilizado para coletar os resulatados em uma nova lista do tipo DadosListagemMedico, que é o formato que queremos retornar para API.
     }

     @GetMapping
    public Page<DadosListagemMedico> listarPorPagina(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);

    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        // var é uma palavra reservada em java para declarar uma variável sem especificar seu tipo: o tipo da varável é inferida pelo compilador com base no valor que foi atribuída a ela
        medico.atualizarInformacoes(dados);
    }


    //EXCLUSÃO - AQUI ESTOU EXCLUINDO MESMO.
    @DeleteMapping("/{id}")
    @Transactional // SPRING DATA JPA - informa ao spring boot que o método irá excluir algo no BD.
    public void excluir(@PathVariable Integer id){ //@PathVariable - informa que o springBoot precisa pegar o caminho variável {id} e entender que é o campo id do médico
        repository.getReferenceById(id);
    }
    
    
    //EXCLUSÃO LÓGICA - Uma regra de negócio que permite que um registro seja "EXCLUÍDO" sem serapagado do Banco de Dados.
    @DeleteMapping("/{id}")
    @Transactional
        public void alterarStatus(@PathVariable Integer id){


        }


    
}
