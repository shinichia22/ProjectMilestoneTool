package com.projectmilestonetool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectmilestonetool.entites.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	
	Project findByprojectIdentifier(String projectID);
}
