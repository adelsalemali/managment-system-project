package management.mapper;

import management.dto.ProjectDto;
import management.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

	public ProjectDto fromEntityToDto(Project project) { // toDto

		ProjectDto projectDto = new ProjectDto();
		projectDto.setId(project.getId());
		projectDto.setName(project.getName());
		projectDto.setDescription(project.getDescription());
		projectDto.setStartDate(project.getStartDate());
		projectDto.setEndDate(project.getEndDate());
		projectDto.setStatus(project.getStatus());
		projectDto.setUserId(project.getUser().getId());

		return projectDto;
	}

	public Project fromDtoToEntity(ProjectDto projectDto) { // fromDto

		Project project = new Project();
		project.setId(projectDto.getId());
		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		project.setStartDate(projectDto.getStartDate());
		project.setEndDate(projectDto.getEndDate());
		project.setStatus(projectDto.getStatus());

		return project;
	}
}

