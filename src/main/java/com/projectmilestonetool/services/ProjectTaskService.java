package com.projectmilestonetool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmilestonetool.entites.Backlog;
import com.projectmilestonetool.entites.Project;
import com.projectmilestonetool.entites.ProjectTask;
import com.projectmilestonetool.exceptions.ProjectNotFoundException;
import com.projectmilestonetool.repositories.BacklogRepository;
import com.projectmilestonetool.repositories.ProjectRepository;
import com.projectmilestonetool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskrepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask 	addProjects(String projectIdentifier, ProjectTask projectTask) {
		
		try {
			//PTs to be added for specific project, project!=null,BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//set bl to pt
			projectTask.setBacklog(backlog);
			
			//We want projectTask sequence to be projectIdentifier1, 2, 3...
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			
			//Add the sequence to PT
			projectTask.setProjectSequence(projectIdentifier +"-"+ backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//initial priority when priority is null
			//if(projectTask.getPriority() ==0 || projectTask.getPriority()== null) 
			if(projectTask.getPriority()== null){
				projectTask.setPriority(3);
			}
	 		
			//Initial status when status is null
			if(projectTask.getStatus()==""|| projectTask.getStatus()==null) {
				projectTask.setStatus("to-do");
			}
		return projectTaskrepository.save(projectTask);	
			
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}
		
	}
	
	
	public List<ProjectTask> findBacklogById(String backlog_id) {
		
		Project project = projectRepository.findByprojectIdentifier(backlog_id);
		
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID '"+backlog_id+"' doen't exist");
		}
		
		// we have attribute projejctIdentifier in projectTask
		return projectTaskrepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	
	public ProjectTask findPTbyProjectSequence(String backlog_id, String pt_id) {
		
		//Search the projectTask in existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project with ID '"+backlog_id+"' doen't exist");
		}
		
		//Make that sure ProjectTask exist
		ProjectTask projectTask = projectTaskrepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("ProjejctTask with id '"+pt_id+"' doesn't exist");
		}
		
		//the backlog_id/project_id in the path corresponds to the right project
		//if both backlog_id andpt_id are valid but pt_id belongs to other project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("ProjectTask '"+pt_id +"' doesn't exist in the project '"+backlog_id +"'");
		}
		
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id) {
		
		//ProjectTask projectTask = projectTaskrepository.findByProjectSequence(updatedProjectTask.getProjectSequence());  // this will prevent user edit url when updating
		ProjectTask projectTask = findPTbyProjectSequence(backlog_id, pt_id); 
		
		projectTask = updatedProjectTask;
		
		return projectTaskrepository.save(projectTask);
		
	}
	
	public void deletePTbyProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTbyProjectSequence(backlog_id, pt_id); 
		
		projectTaskrepository.delete(projectTask);
	}
	
}
