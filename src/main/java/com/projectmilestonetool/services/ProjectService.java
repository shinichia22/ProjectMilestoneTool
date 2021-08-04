package com.projectmilestonetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmilestonetool.entites.Backlog;
import com.projectmilestonetool.entites.Project;
import com.projectmilestonetool.exceptions.ProjectIdException;
import com.projectmilestonetool.repositories.BacklogRepository;
import com.projectmilestonetool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;

	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			// if project is created then backlog should also get created.since it is happening for update we are checking if project id is null i.e new project.
			if(project.getId() == 0L) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase()); // to get right projectTask with right backlog/projectTask ids
			}
			// for updating we are getting backlog as null, to get the project's backlog we have to write below code
			if(project.getId()!=0L) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			
			
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
