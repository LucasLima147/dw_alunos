package br.fai.datawarehouse.alunos.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alunos {

    @Id
    private long id;
    private String cpf;
    private String etnia;
    private String sexo;
    private String escola_origem;
    private String renda_familiar;
    private String estado;
    private String cidade;
    private String data_nascimento;
    private String matricula_situacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEscola_origem() {
        return escola_origem;
    }

    public void setEscola_origem(String escola_origem) {
        this.escola_origem = escola_origem;
    }

    public String getRenda_familiar() {
        return renda_familiar;
    }

    public void setRenda_familiar(String renda_familiar) {
        this.renda_familiar = renda_familiar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getMatricula_situacao() {
        return matricula_situacao;
    }

    public void setMatricula_situacao(String matricula_situacao) {
        this.matricula_situacao = matricula_situacao;
    }
}