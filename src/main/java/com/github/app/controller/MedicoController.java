package com.github.app.controller;

import com.github.app.AppApplication;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;  //já importa todos os que estão comentados abaixo

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import com.github.app.model.medico.DadosCadastroMedico;
import com.github.app.model.medico.DadosListagemMedico;
import com.github.app.model.medico.Medico;
import com.github.app.model.medico.MedicoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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


    
}
