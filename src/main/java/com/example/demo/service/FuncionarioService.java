package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.funcionarioDTO.FuncionarioRequestDTO;
import com.example.demo.dto.funcionarioDTO.FuncionarioResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Funcionario;
import com.example.demo.repository.FuncionarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {

    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private final ModelMapper modelMapper;

    FuncionarioService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


// response

    public List<FuncionarioResponseDTO> findAllFuncionario() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream().map(funcionario -> modelMapper.map(funcionario,FuncionarioResponseDTO.class)).collect(Collectors.toList());
   
    }

    public FuncionarioResponseDTO findFuncionarioByNome(String nome) throws ResourceNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByNome(nome);
        
        if (funcionario == null) {
            throw new ResourceNotFoundException("Nenhum funcionario com o nome " + nome + " foi encontrado");
        }

       return modelMapper.map(funcionario,FuncionarioResponseDTO.class);
    
    }

    public FuncionarioResponseDTO findFuncionarioByCpf(String cpf) throws ResourceNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
        
        if (funcionario == null) {
            throw new ResourceNotFoundException("Nenhum funcionario com o nome " + cpf + " foi encontrado");
        }

       return modelMapper.map(funcionario,FuncionarioResponseDTO.class);
    
    }

//request


public void insertFuncionario(Funcionario adm){
    funcionarioRepository.save(adm);
}

    public FuncionarioRequestDTO updateByNome(String nome, FuncionarioRequestDTO funcionarioRequestDTO)throws ResourceNotFoundException {
        Funcionario existingFuncionario = funcionarioRepository.findByNome(nome);
        if (existingFuncionario == null) {      
            throw new ResourceNotFoundException("Funcionario com o nome " + nome + " não existe.");
        } 
        
        modelMapper.map(funcionarioRequestDTO,existingFuncionario);
        Funcionario updatedFuncionario = funcionarioRepository.save(existingFuncionario);
        return modelMapper.map(updatedFuncionario,FuncionarioRequestDTO.class);
    }


//delete
    public void deleteByCpf(String cpf) throws EntityNotFoundException {
        funcionarioRepository.deleteByCpf(cpf);
    }

    public void deleteByNome(String nome) throws EntityNotFoundException {
        funcionarioRepository.deleteByNome(nome);
    }

}
