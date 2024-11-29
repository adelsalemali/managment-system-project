package management.service.implementation;


import management.dto.ProjectDto;
import management.exception.EntityNotFoundException;
import management.mapper.ProjectMapper;
import management.model.MyUser;
import management.model.Project;
import management.criteria.PaginationRequest;
import management.criteria.ProjectSearchCriteria;
import management.repository.MyUserRepository;
import management.repository.criteria.ProjectCriteriaRepository;
import management.repository.ProjectRepository;
import management.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class ProjectServiceImplement implements ProjectService {
	private final ProjectRepository projectRepository;
	private final MyUserRepository myUserRepository;
	private final ProjectCriteriaRepository projectCriteriaRepository;
	private final ProjectMapper projectMapper;

	public ProjectServiceImplement(ProjectRepository projectRepository,
								   MyUserRepository myUserRepository, ProjectCriteriaRepository projectCriteriaRepository,
								   ProjectMapper projectMapper) {
		this.projectRepository = projectRepository;
		this.myUserRepository = myUserRepository;
		this.projectCriteriaRepository = projectCriteriaRepository;
		this.projectMapper = projectMapper;
	}

	@Override
	public Page<ProjectDto> findProjects(PaginationRequest projectPage, Integer userId) {
		Sort sort = Sort.by(projectPage.getSortDirection(), projectPage.getSortBy());
		Pageable projects = PageRequest.of(projectPage.getPageNumber(), projectPage.getPageSize(), sort);

		Page<ProjectDto> projectDtos = projectRepository
				.findByUserId(userId, projects)
				.map(project -> projectMapper.fromEntityToDto(project));
		return projectDtos;
	}

	@Override
	public ProjectDto findProjectByIdAndUserId(Integer projectId, Integer userId) {
		Project project = projectRepository.findByIdAndUserId(projectId, userId).orElseThrow(() ->
				new EntityNotFoundException(ProjectDto.class," " +  projectId + " with associated USER " + userId + " not found"));
		return projectMapper.fromEntityToDto(project);
	}

	@Override
	public ProjectDto createProject(ProjectDto projectDto) {

		MyUser myUser = myUserRepository.findById(projectDto.getUserId()).orElseThrow(()
				-> new EntityNotFoundException(ProjectDto.class, " " + projectDto.getId()+ "  with associated USER " + projectDto.getUserId() + " not found"));


		Project newProject = projectMapper.fromDtoToEntity(projectDto);
		newProject.setUser(myUser);
		Project saveNewProject = projectRepository.save(newProject);
		return projectMapper.fromEntityToDto(saveNewProject);
	}

	@Override
	public ProjectDto updateProject(ProjectDto projectDto) {

		Project project = projectRepository.findByIdAndUserId(projectDto.getId(),
				                                              projectDto.getUserId()).orElseThrow(() ->
				new EntityNotFoundException(ProjectDto.class, projectDto.getId() + " with associated USER "
				+ projectDto.getUserId() + "  not found "));

		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		project.setStartDate(projectDto.getStartDate());
		project.setEndDate(projectDto.getEndDate());
		project.setStatus(projectDto.getStatus());

		Project savedProject = projectRepository.save(project);

		return projectMapper.fromEntityToDto(savedProject);
	}

	@Override
	public void deleteProject(ProjectDto projectDto) {
		Project project = projectRepository.findByIdAndUserId(projectDto.getId(), projectDto.getUserId()).orElseThrow(() ->
				new EntityNotFoundException(ProjectDto.class, "  " + projectDto.getId() + " with associated USER " + projectDto.getUserId() + " not found"));
		projectRepository.delete(project);
	}

	@Override
	public Page<ProjectDto> searchProjects(PaginationRequest paginationRequest,
										   ProjectSearchCriteria projectSearchCriteria,
										   Integer userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID must not be null");
		}
		Page<Project> projectPage = projectCriteriaRepository
				.findAllWithFilters(paginationRequest, projectSearchCriteria, userId);

		return projectPage.map(projectMapper::fromEntityToDto);
	}
}