package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.administradorDTO.AdministradorRequestDTO;
import com.example.demo.dto.administradorDTO.AdministradorResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Administrador;
import com.example.demo.repository.AdministradorRepository;

@Service
public class AdministradorService {
    
    @Autowired
    private AdministradorRepository administradorRepository;

    private final ModelMapper modelMapper;

    AdministradorService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


//request
    public void insertUser(Administrador adm){
        administradorRepository.save(adm);
    }

    public AdministradorRequestDTO updateByNome(String nome, AdministradorRequestDTO administradorRequestDTO)throws ResourceNotFoundException {
        Administrador existingAdministrador = administradorRepository.findByNome(nome);
        if (existingAdministrador == null) {      
            throw new ResourceNotFoundException("Administrador com o nome " + nome + " não existe.");
        } 
        
        modelMapper.map(administradorRequestDTO,existingAdministrador);
        Administrador updatedAdministrador = administradorRepository.save(existingAdministrador);
        return modelMapper.map(updatedAdministrador,AdministradorRequestDTO.class);
    }

//response
    public List<AdministradorResponseDTO> findAllAdministradores() {
        List<Administrador> administradores = administradorRepository.findAll();
        return administradores.stream().map(administrador -> modelMapper.map(administrador,AdministradorResponseDTO.class)).collect(Collectors.toList());
   
    }

    public AdministradorResponseDTO findById (Long id) throws ResourceNotFoundException{
        Optional<Administrador> opAdministrador  = administradorRepository.findById(id);
    
        if(opAdministrador.isEmpty()){
            throw new ResourceNotFoundException("Administrador não encontrado"); 
        }
        return modelMapper.map(opAdministrador,AdministradorResponseDTO.class);
    }

    public AdministradorResponseDTO findAdministradorByNome(String nome) throws ResourceNotFoundException {
        Administrador administrador = administradorRepository.findByNome(nome);
        
        if (administrador == null) {
            throw new ResourceNotFoundException("Nenhum administrador com o nome " + nome + " foi encontrado");
        }

       return modelMapper.map(administrador,AdministradorResponseDTO.class);
    
    }



    /*DELETE*/
        public void deleteById(Long id) throws ResourceNotFoundException{
            Optional<Administrador> opAdministrador = administradorRepository.findById(id);
     
            if (opAdministrador.isEmpty()) {
                throw new ResourceNotFoundException("User não encontrado"); 
            }
            Administrador administrador = opAdministrador.get();
            administradorRepository.delete(administrador);
        }

        public void deleteByNome(String nome) throws ResourceNotFoundException {
            administradorRepository.deleteByNome(nome);
        }
    
}

