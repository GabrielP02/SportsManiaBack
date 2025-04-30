package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.clienteDTO.ClienteResponseDTO;
import com.example.demo.dto.clienteDTO.ClienteRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cliente;

import com.example.demo.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;



@Service
public class ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    ClienteService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

//response

    public List<ClienteResponseDTO> findAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(cliente -> modelMapper.map(cliente,ClienteResponseDTO.class)).collect(Collectors.toList());
   
    }

    public ClienteResponseDTO findClienteById (Long id) throws ResourceNotFoundException{
        Optional<Cliente> opCliente  = clienteRepository.findById(id);
    
        if(opCliente.isEmpty()){
            throw new ResourceNotFoundException("Cliente não encontrado"); 
        }
        return modelMapper.map(opCliente,ClienteResponseDTO.class);
    }

    public ClienteResponseDTO findClienteByNome(String nome) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findByNome(nome);
        
        if (cliente == null) {
            throw new ResourceNotFoundException("Nenhum cliente com o nome " + nome + " foi encontrado");
        }

       return modelMapper.map(cliente,ClienteResponseDTO.class);
    
    }

    public ClienteResponseDTO findClienteByCpf(String cpf) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        
        if (cliente == null) {
            throw new ResourceNotFoundException("Nenhum cliente com o nome " + cpf + " foi encontrado");
        }

       return modelMapper.map(cliente,ClienteResponseDTO.class);
    
    }


//request

public void insertCliente(Cliente c) {
    clienteRepository.save(c);
}

    public ClienteRequestDTO updateById(Long id, ClienteRequestDTO clienteRequestDTO) throws ResourceNotFoundException {
       
        Optional<Cliente> existingCliente = clienteRepository.findById(id);
        if (existingCliente.isEmpty()) {      
            throw new ResourceNotFoundException("Cliente com o id: " + id + " não existe.");
        } 
        
        Cliente cliente = existingCliente.get();
        modelMapper.map(clienteRequestDTO,cliente);
        Cliente updatedCliente = clienteRepository.save(cliente);
        return modelMapper.map(updatedCliente,ClienteRequestDTO.class);
    }


    public ClienteRequestDTO updateByNome(String nome, ClienteRequestDTO clienteRequestDTO)throws ResourceNotFoundException {
        Cliente existingCliente = clienteRepository.findByNome(nome);
        if (existingCliente == null) {      
            throw new ResourceNotFoundException("Cliente com o nome " + nome + " não existe.");
        } 
        
        modelMapper.map(clienteRequestDTO,existingCliente);
        Cliente updatedCliente = clienteRepository.save(existingCliente);
        return modelMapper.map(updatedCliente,ClienteRequestDTO.class);
    }


//delete

    public void deleteById(Long id) throws ResourceNotFoundException{
        Optional<Cliente> opcliente = clienteRepository.findById(id);
 
        if (opcliente.isEmpty()) {
            throw new ResourceNotFoundException("User não encontrado"); 
        }
        Cliente cliente = opcliente.get();
        clienteRepository.delete(cliente);
    }

    public void deleteByCpf(String cpf) throws EntityNotFoundException {
        clienteRepository.deleteByCpf(cpf);
    }

    public void deleteByNome(String nome) throws EntityNotFoundException {
        clienteRepository.deleteByNome(nome);
    }




}
    

