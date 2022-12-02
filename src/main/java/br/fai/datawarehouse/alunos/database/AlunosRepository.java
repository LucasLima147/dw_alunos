package br.fai.datawarehouse.alunos.database;

import br.fai.datawarehouse.alunos.model.Alunos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunosRepository extends JpaRepository<Alunos, Long> {


}