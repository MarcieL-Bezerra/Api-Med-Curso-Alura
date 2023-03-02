package com.marcielbezerra.apimed.controller;

import com.marcielbezerra.apimed.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){

        repository.save(new Medico(dados));
    }
    @GetMapping
    public Page<DadosListaMedicos> listar(Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao).map(DadosListaMedicos::new);
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizaMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.deleteLogico();
    }

}
