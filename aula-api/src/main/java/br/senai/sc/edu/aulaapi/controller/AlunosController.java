package br.senai.sc.edu.aulaapi.controller;

import br.senai.sc.edu.aulaapi.model.Aluno;
import br.senai.sc.edu.aulaapi.repository.dto.AlunoContatoDTO;
import br.senai.sc.edu.aulaapi.repository.dto.NovoAlunoDTO;
import br.senai.sc.edu.aulaapi.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
@SuppressWarnings("SpellCheckingInspection")
public class AlunosController {

    private final AlunoService alunoService;

    public AlunosController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Aluno> criarAluno(@RequestBody NovoAlunoDTO aluno){
        return new ResponseEntity<>(alunoService.salvar(aluno), HttpStatus.CREATED);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<AlunoContatoDTO>> listarAlunos(){
        return new ResponseEntity<>(alunoService.listarContatos(), HttpStatus.OK);
    }

    @GetMapping("/listaPorNome")
    public ResponseEntity<List<AlunoContatoDTO>> listarAlunosPorNome(@RequestParam("nome") String nome){
        return new ResponseEntity<>(alunoService.listarContatosPorNome(nome), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<AlunoContatoDTO> getAluno(@PathVariable("codigo") Long codigo){
        return new ResponseEntity<>(alunoService.listarPorID(codigo), HttpStatus.OK);
    }

    @PutMapping("/alterar/{codigo}")
    public ResponseEntity<Aluno> alterar(@RequestBody Aluno aluno, @PathVariable("codigo") Long codigo){
        return new ResponseEntity(alunoService.alterarAlunoPorID(codigo, aluno), HttpStatus.OK);
    }

    @PatchMapping("/alterar-endereco/{codigo}")
    public ResponseEntity<Aluno> alterarEndereco(@RequestBody Aluno aluno, @PathVariable("codigo") Long codigo){
        return new ResponseEntity(alunoService.alterarEnderecoAlunoPorID(codigo, aluno), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity excluir(@PathVariable("codigo") Long codigo){
        alunoService.excluirAluno(codigo);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
