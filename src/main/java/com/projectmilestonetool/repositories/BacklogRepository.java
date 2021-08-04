package com.projectmilestonetool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectmilestonetool.entites.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long>  {
	Backlog findByProjectIdentifier(String projectIdentifier);
}
 