package com.projectmilestonetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmilestonetool.entites.Project;
import com.projectmilestonetool.exceptions.ProjectIdException;
import com.projectmilestonetool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID " + project.getProjectIdentifier().toUpperCase() + " already exist");
		}

	}

	public Project findBYProjectIdentifier(String projectId) {
		Project project = projectRepository.findByprojectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID " + projectId.toUpperCase() + " doesn't exist");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectBYIdentifier(String projectId) {
		Project project = projectRepository.findByprojectIdentifier(projectId);

		if (project == null) {
			throw new ProjectIdException("Cannot Delete project with Id " + projectId.toUpperCase() + " This project doesn't exist");
		}
		projectRepository.delete(project);
	}
}
