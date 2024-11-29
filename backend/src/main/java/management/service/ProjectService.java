package management.service;

import management.dto.ProjectDto;
import management.criteria.PaginationRequest;
import management.criteria.ProjectSearchCriteria;
import org.springframework.data.domain.Page;
public interface ProjectService {

	 Page<ProjectDto> findProjects(PaginationRequest projectPage, Integer userId);
	 ProjectDto findProjectByIdAndUserId(Integer projectId, Integer userId);

	 ProjectDto createProject(ProjectDto projectDto);

	 ProjectDto updateProject(ProjectDto projectDto);

	 void deleteProject(ProjectDto projectDto);

	 Page<ProjectDto> searchProjects(PaginationRequest projectPage, ProjectSearchCriteria projectSearchCriteria, Integer userId);
}
