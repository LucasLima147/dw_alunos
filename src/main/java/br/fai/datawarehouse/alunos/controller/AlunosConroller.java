package br.fai.datawarehouse.alunos.controller;

import br.fai.datawarehouse.alunos.model.Alunos;
import br.fai.datawarehouse.alunos.service.AlunosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AlunosConroller {

    @Autowired
    private AlunosService service;

    @GetMapping("/barChart")
    private String getAllAlunos(Model model){
        List<Alunos> alunosList = service.getAllAlunos();
        List<String> etniaList = alunosList.stream().map(x -> x.getEtnia()).distinct().collect(Collectors.toList());

        List<Integer> age = new ArrayList<>();

        for (String etnia : etniaList) {
            age.add(alunosList.stream().filter(alunos -> alunos.getEtnia().equalsIgnoreCase(etnia)).collect(Collectors.toList()).size());
        }

        System.out.println(etniaList);
        System.out.println(age);

        model.addAttribute("estado", etniaList);
        model.addAttribute("id", age);
        return "barChart";
    }

}