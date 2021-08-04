package com.projectmilestonetool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectmilestonetool.entites.ProjectTask;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long>{
	List<ProjectTask> findByProjectIdentifierOrderByPriority (String projectIdentifier);
	
	ProjectTask findByProjectSequence(String projectSequence);
}
