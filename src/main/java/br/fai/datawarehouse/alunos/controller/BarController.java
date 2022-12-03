package br.fai.datawarehouse.alunos.controller;

import br.fai.datawarehouse.alunos.helper.NamesHelper;
import br.fai.datawarehouse.alunos.model.Alunos;
import br.fai.datawarehouse.alunos.service.AlunosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/graph_bar")
@CrossOrigin(origins = "*")
public class BarController {

    @Autowired
    private AlunosService service;
    private final String NAME_FILE = "graph_bar";

    private List<Alunos> filterAlunos = null;

    private List<String> etniasList = null;
    private List<String> sexoList = null;
    private List<String> escolaList = null;

    private String etniaSelecionado = "";
    private String sexoSelecionado = "";
    private String escolaSelecionado = "";

    private boolean notFoundAlunos;

    private void setOptionsFilter(){
        List<Alunos> alunosList = service.getAllAlunos();
        etniasList = alunosList.stream()
                .map(x -> x.getEtnia()).distinct().collect(Collectors.toList());

        sexoList = alunosList.stream()
                .map(x -> x.getSexo()).distinct().collect(Collectors.toList());

        escolaList = alunosList.stream()
                .map(x -> x.getEscola_origem()).distinct().collect(Collectors.toList());
    }

    private Model getDefaultOptions(Model model){
        model.addAttribute(NamesHelper.ETNIAS_OPTIONS, etniasList);
        model.addAttribute(NamesHelper.SEXO_OPTIONS, sexoList);
        model.addAttribute(NamesHelper.ESCOLAS_OPTIONS, escolaList);

        model.addAttribute(NamesHelper.ETNIAS_SELECIONADA, etniaSelecionado);
        model.addAttribute(NamesHelper.SEXO_SELECIONADA, sexoSelecionado);
        model.addAttribute(NamesHelper.ESCOLAS_SELECIONADA, escolaSelecionado);
        return model;
    }

    @GetMapping("/etnia")
    private String getAllAlunos(Model model){
        setOptionsFilter();
        model = getDefaultOptions(model);
        List<Alunos> alunosList = (filterAlunos != null && !filterAlunos.isEmpty())
                ? filterAlunos : service.getAllAlunos();
        List<String> etniaList = alunosList.stream()
                .map(x -> x.getEtnia()).distinct().sorted().collect(Collectors.toList());

        List<Integer> quantidadePorEtnia = new ArrayList<>();
        for (String etnia : etniaList) {
            quantidadePorEtnia.add(alunosList.stream()
                    .filter(alunos -> alunos.getEtnia().equalsIgnoreCase(etnia))
                    .collect(Collectors.toList()).size());
        }

        System.out.println(etniaList);
        System.out.println(quantidadePorEtnia);

        model.addAttribute(NamesHelper.ABOUT_GRAPH, "Etnia dos alunos");
        model.addAttribute(NamesHelper.DETAIL_GRAPH, "Quantidade de alunos sobre cada tipo de etnia");
        model.addAttribute(NamesHelper.COLUMM_PARAM, etniaList);
        model.addAttribute(NamesHelper.COLUMNS_DECRIPTION, "Etnias");
        model.addAttribute(NamesHelper.AMOUNT_PARAM, quantidadePorEtnia);
        model.addAttribute(NamesHelper.CURRENT_PAGE, "etnia");

        model.addAttribute("not-found", notFoundAlunos);
        notFoundAlunos = false;
        return NAME_FILE;
    }

    @GetMapping("/sexo")
    private String filterSexo(Model model){
        model = getDefaultOptions(model);
        List<Alunos> alunosList = (filterAlunos != null && !filterAlunos.isEmpty())
                ? filterAlunos : service.getAllAlunos();

        List<String> sexoList = alunosList.stream()
                .map(x -> x.getSexo()).distinct().sorted().collect(Collectors.toList());
        List<Integer> quantidadePorSexo = new ArrayList<>();
        for (String sexo : sexoList) {
            quantidadePorSexo.add(alunosList.stream()
                    .filter(alunos -> alunos.getSexo().equalsIgnoreCase(sexo))
                    .collect(Collectors.toList()).size());
        }

        System.out.println(sexoList);
        System.out.println(quantidadePorSexo);

        model.addAttribute(NamesHelper.ABOUT_GRAPH, "Sexo declarado dos alunos");
        model.addAttribute(NamesHelper.DETAIL_GRAPH, "Quantidade de alunos por cada opção tipo de sexo");
        model.addAttribute(NamesHelper.COLUMM_PARAM, sexoList);
        model.addAttribute(NamesHelper.COLUMNS_DECRIPTION, "Sexo");
        model.addAttribute(NamesHelper.AMOUNT_PARAM, quantidadePorSexo);
        model.addAttribute(NamesHelper.CURRENT_PAGE, "sexo");
        return NAME_FILE;
    }

    @GetMapping("/escola")
    private String filterEscola(Model model){
        model = getDefaultOptions(model);
        List<Alunos> alunosList = service.getAllAlunos();
        List<String> escolaList = alunosList.stream()
                .map(x -> x.getEscola_origem()).sorted().distinct().collect(Collectors.toList());

        List<Integer> quantidadePorEscola = new ArrayList<>();
        for (String escola : escolaList) {
            quantidadePorEscola.add(alunosList.stream()
                    .filter(alunos -> alunos.getEscola_origem().equalsIgnoreCase(escola))
                    .collect(Collectors.toList()).size());
        }

        System.out.println(escolaList);
        System.out.println(quantidadePorEscola);

        model.addAttribute(NamesHelper.ABOUT_GRAPH, "Tipo da escola que os alunos estudaram");
        model.addAttribute(NamesHelper.DETAIL_GRAPH, "Quantide de alunos por cada tipo de escola que estudaram anteriormente");
        model.addAttribute(NamesHelper.COLUMM_PARAM, escolaList);
        model.addAttribute(NamesHelper.COLUMNS_DECRIPTION, "Tipo de escola que estudaram");
        model.addAttribute(NamesHelper.AMOUNT_PARAM, quantidadePorEscola);
        model.addAttribute(NamesHelper.CURRENT_PAGE, "escola");
        return NAME_FILE;
    }

    @PostMapping("/filtrar/{current-page}")
    private String filterByParam(@RequestParam("sexo-op") String sexo,
                                 @RequestParam("etnia-op") String etnia,
                                 @RequestParam("escola-op") String escola,
                                 @PathVariable("current-page") String page){
        List<Alunos> filtro = null;
        sexoSelecionado = sexo;
        etniaSelecionado = etnia;
        escolaSelecionado = escola;
        try {
            filtro = service.getAllAlunos();
            if(sexo != null && !sexo.isEmpty()){
                filtro = filtro.stream()
                        .filter(x -> x.getSexo().equalsIgnoreCase(sexo))
                        .collect(Collectors.toList());
            }

            if(etnia != null && !etnia.isEmpty()){
                filtro = filtro.stream()
                        .filter(x -> x.getEtnia().equalsIgnoreCase(etnia))
                        .collect(Collectors.toList());
            }

            if(escola != null && !escola.isEmpty()){
                filtro = filtro.stream()
                        .filter(x -> x.getEscola_origem().equalsIgnoreCase(escola))
                        .collect(Collectors.toList());
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        filterAlunos = (!filtro.isEmpty()) ? filtro : null;
        notFoundAlunos = (filterAlunos != null && filterAlunos.size() > 0);
        return "redirect:/graph_bar/" + page;
    }

    @GetMapping("/reset-filter/{current-page}")
    public String resetFilter(@PathVariable("current-page") String page){
        sexoSelecionado = "";
        etniaSelecionado = "";
        escolaSelecionado = "";

        filterAlunos = null;
        return "redirect:/graph_bar/" + page;
    }
}