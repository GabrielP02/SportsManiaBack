package com.example.demo.mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.util.List;


public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper() {
        modelMapper = new ModelMapper();
    }

    // Método para mapear DTO para Entity
    public <D, T> D map(T source, Class<D> destination) {
        return modelMapper.map(source, destination);
    }

    // Método para mapear lista de DTO para lista de Entity
    public <D, T> List<D> mapList(List<T> source, Class<D> destination) {
        return modelMapper.map(source, new TypeToken<List<D>>(){}.getType());
    }
}