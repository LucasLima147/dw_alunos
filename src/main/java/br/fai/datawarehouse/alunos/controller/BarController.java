package br.fai.datawarehouse.alunos.controller;

import br.fai.datawarehouse.alunos.helper.NamesHelper;
import br.fai.datawarehouse.alunos.model.Alunos;
import br.fai.datawarehouse.alunos.service.AlunosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/graph_bar")
@CrossOrigin(origins = "*")
public class BarController {

    @Autowired
    private AlunosService service;

    @GetMapping("/etnia")
    private String getAllAlunos(Model model){
        List<Alunos> alunosList = service.getAllAlunos();
        List<String> etniaList = alunosList.stream().map(x -> x.getEtnia()).distinct().collect(Collectors.toList());

        List<Integer> quantidadePorEtnia = new ArrayList<>();

        for (String etnia : etniaList) {
            quantidadePorEtnia.add(alunosList.stream().filter(alunos -> alunos.getEtnia().equalsIgnoreCase(etnia)).collect(Collectors.toList()).size());
        }

        System.out.println(etniaList);
        System.out.println(quantidadePorEtnia);

        model.addAttribute(NamesHelper.ABOUT_GRAPH, "Quadro geral sobre etnia dos alunos");
        model.addAttribute(NamesHelper.DETAIL_GRAPH, "Quantide de alunos sobre cada tipo de etnia");
        model.addAttribute(NamesHelper.COLUMM_PARAM, etniaList);
        model.addAttribute(NamesHelper.COLUMNS_DECRIPTION, "Etnias");
        model.addAttribute(NamesHelper.AGE_PARAM, quantidadePorEtnia);
        return "graph_bar";
    }

    @GetMapping("/sexo")
    private String filterSexo(Model model){
        List<Alunos> alunosList = service.getAllAlunos();
        List<String> sexoList = alunosList.stream().map(x -> x.getSexo()).distinct().collect(Collectors.toList());

        List<Integer> quantidadePorSexo = new ArrayList<>();

        for (String sexo : sexoList) {
            quantidadePorSexo.add(alunosList.stream().filter(alunos -> alunos.getSexo().equalsIgnoreCase(sexo)).collect(Collectors.toList()).size());
        }

        System.out.println(sexoList);
        System.out.println(quantidadePorSexo);

        model.addAttribute(NamesHelper.ABOUT_GRAPH, "Quadro geral do sexo declarado dos alunos");
        model.addAttribute(NamesHelper.DETAIL_GRAPH, "Quantide de alunos por cada opção tipo de sexo");
        model.addAttribute(NamesHelper.COLUMM_PARAM, sexoList);
        model.addAttribute(NamesHelper.COLUMNS_DECRIPTION, "Sexo");
        model.addAttribute(NamesHelper.AGE_PARAM, quantidadePorSexo);
        return "graph_bar";
    }

    @GetMapping("/escola")
    private String filterEscola(Model model){
        List<Alunos> alunosList = service.getAllAlunos();
        List<String> escolaList = alunosList.stream().map(x -> x.getEscola_origem()).distinct().collect(Collectors.toList());

        List<Integer> quantidadePorEscola = new ArrayList<>();

        for (String escola : escolaList) {
            quantidadePorEscola.add(alunosList.stream().filter(alunos -> alunos.getEscola_origem().equalsIgnoreCase(escola)).collect(Collectors.toList()).size());
        }

        System.out.println(escolaList);
        System.out.println(quantidadePorEscola);

        model.addAttribute(NamesHelper.ABOUT_GRAPH, "Tipo da escola que os alunos estudaram");
        model.addAttribute(NamesHelper.DETAIL_GRAPH, "Quantide de alunos por cada tipo de escola que estudaram anteriormente");
        model.addAttribute(NamesHelper.COLUMM_PARAM, escolaList);
        model.addAttribute(NamesHelper.COLUMNS_DECRIPTION, "Tipo de escola que esturadam");
        model.addAttribute(NamesHelper.AGE_PARAM, quantidadePorEscola);
        return "graph_bar";
    }
}