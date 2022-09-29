package br.senai.sc.edu.aulaapi.service;

import br.senai.sc.edu.aulaapi.model.Aluno;
import br.senai.sc.edu.aulaapi.repository.AlunoRepository;
import br.senai.sc.edu.aulaapi.repository.dto.AlunoContatoDTO;
import br.senai.sc.edu.aulaapi.repository.dto.NovoAlunoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@SuppressWarnings("SpellCheckingInspection")
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno salvar(NovoAlunoDTO novoAlunoDTO){
        Aluno aluno = new Aluno();
        aluno.setNome(novoAlunoDTO.getNome());
        aluno.setCpf(novoAlunoDTO.getCpf());
        aluno.setTelefone(novoAlunoDTO.getTelefone());
        aluno.setNascimento(novoAlunoDTO.getNascimento());
        aluno.setEmail(novoAlunoDTO.getEmail());
        aluno.setEndereco(novoAlunoDTO.getEndereco());
        aluno = alunoRepository.save(aluno);
        return aluno;
    }

    public List<AlunoContatoDTO> listarContatos(){
        List<Aluno> alunos = alunoRepository.findAll();
        List<AlunoContatoDTO> alunoContatoDTOStream = alunos.stream().map(this::transformarAlunoEmAlunoContato).collect(Collectors.toList());
        return alunoContatoDTOStream;
    }

    public List<AlunoContatoDTO> listarContatosPorNome(String nome){
        List<Aluno> alunos = alunoRepository.findByNomeIgnoreCaseContains(nome);
        List<AlunoContatoDTO> alunoContatoDTOStream = alunos.stream().map(this::transformarAlunoEmAlunoContato).collect(Collectors.toList());
        return alunoContatoDTOStream;
    }

    public AlunoContatoDTO listarPorID(Long id){
        Optional<Aluno> alunoOptional = alunoRepository.findById(id);
        if (alunoOptional.isPresent()){
            Aluno aluno = alunoOptional.get();
            return this.transformarAlunoEmAlunoContato(aluno);
        }
        return null;
    }

    public AlunoContatoDTO alterarAlunoPorID(Long id, Aluno aluno){
        Optional<Aluno> alunoOptional = alunoRepository.findById(id);
        if (alunoOptional.isPresent()){
            Aluno alunoPersistir = alunoOptional.get();
            alunoPersistir.setNome(aluno.getNome());
            alunoPersistir.setEndereco(aluno.getEndereco());
            return this.transformarAlunoEmAlunoContato(alunoRepository.save(alunoPersistir));
        }

        return this.transformarAlunoEmAlunoContato(aluno);
    }

    public Aluno alterarEnderecoAlunoPorID(Long id, Aluno aluno) {
        if(aluno == null) return null;

        Optional<Aluno> alunoOptional = alunoRepository.findById(id);
        if (alunoOptional.isPresent()){
            Aluno alunoPersistir = alunoOptional.get();
            alunoPersistir.setEndereco(aluno.getEndereco());
            return alunoRepository.save(alunoPersistir);
        }

        return aluno;
    }

    public void excluirAluno(Long id) {
        alunoRepository.deleteById(id);
    }

    private AlunoContatoDTO transformarAlunoEmAlunoContato(Aluno aluno) {
        if(aluno == null) return null;

        AlunoContatoDTO contatoDTO = new AlunoContatoDTO();
        contatoDTO.setCodigo(aluno.getCodigo());
        contatoDTO.setNome(aluno.getNome());
        contatoDTO.setEmail(aluno.getEmail());
        contatoDTO.setTelefone(aluno.getTelefone());
        return contatoDTO;
    }

}
