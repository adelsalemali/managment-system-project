package projectmanagment.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import management.dto.ProjectDto;
import management.mapper.ProjectMapper;
import management.model.MyUser;
import management.criteria.PaginationRequest;
import management.model.Project;
import management.criteria.ProjectSearchCriteria;
import management.repository.MyUserRepository;
import management.repository.criteria.ProjectCriteriaRepository;
import management.repository.ProjectRepository;
import management.service.implementation.ProjectServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
class ProjectServiceTest {

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private MyUserRepository myUserRepository;
	@Mock
	private ProjectCriteriaRepository projectCriteriaRepository;

	@Mock
	private ProjectMapper projectMapper;

	@InjectMocks
	private ProjectServiceImplement projectService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void should_find_all_projects() {

		PaginationRequest paginationRequest = new PaginationRequest();
		paginationRequest.setPageNumber(0);
		paginationRequest.setPageSize(2);
		paginationRequest.setSortBy("name");
		paginationRequest.setSortDirection(Sort.Direction.ASC);
		Integer userId = 1;

		Project project1 = Project.builder()
				.id(1)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();
		Project project2 = Project.builder()
				.id(2)
				.name("Spring Cloud")
				.description("cloud microservices")
				.startDate(LocalDate.of(2024, 11, 1))
				.endDate(LocalDate.of(2024, 11, 10))
				.status("Completed")
				.deleted(false)
				.build();


		ProjectDto projectDto1 = ProjectDto.builder()
				.id(1)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.userId(userId)
				.deleted(false)
				.build();
		ProjectDto projectDto2 = ProjectDto.builder()
				.id(2)
				.name("Spring Cloud")
				.description("cloud microservices")
				.startDate(LocalDate.of(2024, 11, 1))
				.endDate(LocalDate.of(2024, 11, 10))
				.status("Completed")
				.userId(userId)
				.deleted(false)
				.build();

		Page<Project> projectPage = new PageImpl<>(List.of(project1, project2));

		when(projectRepository.findByUserId(eq(userId), any(Pageable.class))).thenReturn(projectPage);
		when(projectMapper.fromEntityToDto(project1)).thenReturn(projectDto1);
		when(projectMapper.fromEntityToDto(project2)).thenReturn(projectDto2);

		Page<ProjectDto> result = projectService.findProjects(paginationRequest, userId);

		assertThat(result.getContent()).containsExactly(projectDto1, projectDto2);
		verify(projectRepository).findByUserId(eq(userId), any(Pageable.class));
		verify(projectMapper).fromEntityToDto(project1);
		verify(projectMapper).fromEntityToDto(project2);
	}

	@Test
	public void should_find_project_by_id() {

		Integer projectId = 1;
		Integer userId = 2;


		Project project = Project.builder()
				.id(projectId)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		ProjectDto projectDto = ProjectDto.builder()
				.id(projectId)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();


		Mockito.when(projectRepository.findByIdAndUserId(projectId, userId))
				.thenReturn(Optional.of(project));
		Mockito.when(projectMapper.fromEntityToDto(project))
				.thenReturn(projectDto);


		ProjectDto find = projectService.findProjectByIdAndUserId(projectId, userId);

		assertThat(find).isNotNull();
		assertThat(find).isEqualTo(projectDto);


		verify(projectRepository, times(1)).findByIdAndUserId(projectId, userId);

		verify(projectMapper, times(1)).fromEntityToDto(project);
	}

	@Test
	public void should_save_project() {

		Integer userId = 1;
		Project project = Project.builder()
				.id(1)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		ProjectDto projectDto = ProjectDto.builder()
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.userId(userId)
				.build();

		MyUser myUser = MyUser.builder()
				.id(userId)
				.firstName("John Doe")
				.build();

		when(myUserRepository.findById(userId)).thenReturn(Optional.of(myUser));
		when(projectMapper.fromDtoToEntity(projectDto)).thenReturn(project);
		when(projectRepository.save(project)).thenReturn(project);
		when(projectMapper.fromEntityToDto(project)).thenReturn(projectDto);

		ProjectDto proj = projectService.createProject(projectDto);

		assertThat(proj).isNotNull();
		assertEquals(projectDto.getName(), proj.getName());

		verify(myUserRepository, times(1)).findById(userId);
		verify(projectMapper, times(1)).fromDtoToEntity(projectDto);
		verify(projectRepository, times(1)).save(project);
		verify(projectMapper, times(1)).fromEntityToDto(project);
	}

	@Test
	public void should_update_project() {
		Integer projectId = 1;
		Integer userId = 2;

		Project project = Project.builder()
				.id(projectId)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		ProjectDto projectDto = ProjectDto.builder()
				.id(projectId)
				.userId(userId)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		Project updatedProject = Project.builder()
				.id(projectId)
				.name("Updated Spring boot")
				.description("updated description")
				.startDate(LocalDate.of(2024, 11, 01))
				.endDate(LocalDate.of(2024, 11, 05))
				.status("Completed")
				.deleted(false)
				.build();

		ProjectDto updatedProjectDto = ProjectDto.builder()
				.id(projectId)
				.userId(userId)
				.name("Updated Spring boot")
				.description("updated description")
				.startDate(LocalDate.of(2024, 11, 01))
				.endDate(LocalDate.of(2024, 11, 05))
				.status("Completed")
				.deleted(false)
				.build();

		when(projectRepository.findByIdAndUserId(projectId, userId))
				.thenReturn(Optional.of(project));

		when(projectMapper.fromDtoToEntity(projectDto))
				.thenReturn(updatedProject);

		when(projectRepository.save(updatedProject))
				.thenReturn(updatedProject);

		when(projectMapper.fromEntityToDto(updatedProject))
				.thenReturn(updatedProjectDto);

		ProjectDto result = projectService.updateProject(projectDto);

		assertThat(result).isNotNull();

		assertEquals(updatedProjectDto.getName(), result.getName());
		assertEquals(updatedProjectDto.getDescription(), result.getDescription());

		verify(projectRepository, times(1)).findByIdAndUserId(projectId, userId);
		verify(projectRepository, times(1)).save(updatedProject);
		verify(projectMapper, times(1)).fromDtoToEntity(projectDto);
		verify(projectMapper, times(1)).fromEntityToDto(updatedProject);
	}

	@Test
    public void should_delete_projectById() {

        Project project = Project.builder()
                .id(1)
                .name("Spring boot")
                .description("test your code")
                .startDate(LocalDate.of(2024, 10, 24))
                .endDate(LocalDate.of(2024, 10, 29))
                .status("Active")
                .deleted(false)
                .build();

        ProjectDto projectDto = ProjectDto.builder()
                .id(1)
                .name("Spring boot")
                .description("test your code")
                .startDate(LocalDate.of(2024, 10, 24))
                .endDate(LocalDate.of(2024, 10, 29))
                .status("Active")
                .deleted(false)
                .build();

        when(projectRepository.findByIdAndUserId(projectDto.getId(), projectDto.getUserId()))
                .thenReturn(Optional.of(project));

        projectService.deleteProject(projectDto);

        verify(projectRepository, times(1))
                .findByIdAndUserId(projectDto.getId(), projectDto.getUserId());
        verify(projectRepository, times(1)).delete(project);

        when(projectRepository.findByIdAndUserId(projectDto.getId(), projectDto.getUserId()))
                .thenReturn(Optional.empty());
        assertThat(projectRepository.findByIdAndUserId(projectDto.getId(), projectDto.getUserId())).isNotPresent();
    }

    @Test
	void should_search_projects() {

		PaginationRequest paginationRequest = new PaginationRequest();
		ProjectSearchCriteria searchCriteria = new ProjectSearchCriteria();
		Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize());
		Integer userId = 1;

		Project project1 = Project.builder()
				.id(1)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		Project project2 = Project.builder()
				.id(2)
				.name("Spring security")
				.description("secure your app")
				.startDate(LocalDate.of(2024, 11, 01))
				.endDate(LocalDate.of(2024, 11, 05))
				.status("Inactive")
				.deleted(false)
				.build();

		List<Project> projectList = Arrays.asList(project1, project2);
		Page<Project> projectPage = new PageImpl<>(projectList, pageable, projectList.size());

		ProjectDto projectDto1 = new ProjectDto();
		projectDto1.setId(1);
		projectDto1.setName("Spring boot");
		projectDto1.setUserId(userId);

		ProjectDto projectDto2 = new ProjectDto();
		projectDto2.setId(2);
		projectDto2.setName("Spring security");
		projectDto2.setUserId(userId);

		when(projectCriteriaRepository.findAllWithFilters(paginationRequest, searchCriteria, userId)).thenReturn(projectPage);
		when(projectMapper.fromEntityToDto(project1)).thenReturn(projectDto1);
		when(projectMapper.fromEntityToDto(project2)).thenReturn(projectDto2);

		Page<ProjectDto> result = projectService.searchProjects(paginationRequest, searchCriteria, userId);

		assertThat(result.getContent()).containsExactly(projectDto1, projectDto2);
		assertThat(result.getTotalElements()).isEqualTo(projectList.size());

		verify(projectCriteriaRepository).findAllWithFilters(paginationRequest, searchCriteria, userId);
		verify(projectMapper).fromEntityToDto(project1);
		verify(projectMapper).fromEntityToDto(project2);
	}
}