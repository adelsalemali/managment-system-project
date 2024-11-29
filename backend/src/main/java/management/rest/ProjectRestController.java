package management.rest;

import lombok.extern.log4j.Log4j2;
import management.dto.ProjectDto;
import management.criteria.PaginationRequest;
import management.criteria.ProjectSearchCriteria;
import management.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api")
public class ProjectRestController {

	private final ProjectService projectService;

	public ProjectRestController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping("/projects")
	public Page<ProjectDto> findProjects(PaginationRequest projectPage, Integer userId) {
		return projectService.findProjects(projectPage, userId);
	}

	@GetMapping("/projects/{projectId}/{userId}")
		public ProjectDto getProjectById(@PathVariable Integer projectId,
										 @PathVariable Integer userId) {
		return projectService.findProjectByIdAndUserId(projectId, userId);
	}

	@PostMapping("/projects")
	public ProjectDto createProject(@Valid @RequestBody ProjectDto projectDto) {
		return projectService.createProject(projectDto);
	}

	@PutMapping("/projects")
	public ProjectDto updateProject(@Valid @RequestBody ProjectDto projectDto) {
		log.info("Project ID: " + projectDto.getId());
		log.info("User ID: " + projectDto.getUserId());
		return projectService.updateProject(projectDto);
	}

	@DeleteMapping("/projects")
	public void deleteProject(@RequestBody ProjectDto projectDto) {
		log.info("Project ID: " + projectDto.getId());
		log.info("User ID: " + projectDto.getUserId());
		projectService.deleteProject(projectDto);
    }

	@GetMapping("/projects/search")
	public Page<ProjectDto> searchInProjectList(PaginationRequest paginationRequest,
												ProjectSearchCriteria projectSearchCriteria, Integer userId) {

		return projectService.searchProjects(paginationRequest, projectSearchCriteria, userId);
	}
}