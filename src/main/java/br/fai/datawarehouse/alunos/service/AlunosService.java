package br.fai.datawarehouse.alunos.service;

import br.fai.datawarehouse.alunos.database.AlunosRepository;
import br.fai.datawarehouse.alunos.model.Alunos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunosService {

    @Autowired
    private AlunosRepository repository;

    public List<Alunos> getAllAlunos(){
        return repository.findAll();
    }



}