package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;


    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){

        List<City> listCity = repository.findAll();

        return listCity.stream().map(CityDTO::new).sorted(Comparator.comparing(CityDTO::getName)).collect(Collectors.toList());
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Cidade não encontrada");
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Não pode deletar uma cidade que tem um evento marcado");
        }
    }
}
