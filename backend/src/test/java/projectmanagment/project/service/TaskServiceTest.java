package projectmanagment.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import management.dto.TaskDto;
import management.mapper.TasksMapper;
import management.criteria.PaginationRequest;
import management.model.Project;
import management.model.Task;
import management.criteria.TaskSearchCriteria;
import management.repository.ProjectRepository;
import management.repository.TasksRepository;
import management.repository.criteria.TaskCriteriaRepository;
import management.service.implementation.TasksServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
public class TaskServiceTest {

	@Mock
	private TasksRepository tasksRepository;

	@Mock
	private TasksMapper tasksMapper;

	@Mock
	private ProjectRepository projectRepository;

	@InjectMocks
	private TasksServiceImplement tasksServiceImplement;

	@Mock
	private TaskCriteriaRepository taskCriteriaRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void should_find_tasks_by_projectId() {

		PaginationRequest paginationRequest = new PaginationRequest();
		paginationRequest.setPageNumber(0);
		paginationRequest.setPageSize(2);
		paginationRequest.setSortBy("name");
		paginationRequest.setSortDirection(Sort.Direction.ASC);

		Task task1 = Task.builder()
				.id(1)
				.name("Setup environment")
				.description("Initial setup")
				.status("Active")
				.build();

		Task task2 = Task.builder()
				.id(2)
				.name("Develop feature A")
				.description("Implement feature A")
				.status("Active")
				.build();

		TaskDto tasksDto1 = TaskDto.builder()
				.id(1)
				.name("Setup environment")
				.description("Initial setup")
				.status("Active")
				.build();

		TaskDto tasksDto2 = TaskDto.builder()
				.id(2)
				.name("Develop feature A")
				.description("Implement feature A")
				.status("Active")
				.build();

		List<Task> tasks = Arrays.asList(task1, task2);
		List<TaskDto> tasksDtos = Arrays.asList(tasksDto1, tasksDto2);

		Page<Task> taskPage = new PageImpl<>(tasks);
		when(tasksRepository.findByProjectId(any(Integer.class), any(Pageable.class)))
				.thenReturn(taskPage);

		when(tasksMapper.fromEntityToDto(any(Task.class)))
				.thenReturn(tasksDto1, tasksDto2);

		Page<TaskDto> result = tasksServiceImplement.findTasks(paginationRequest, 1);

		assertThat(result).isNotNull();
		assertThat(result.getTotalElements()).isEqualTo(2);
		assertThat(result.getContent()).containsExactlyElementsOf(tasksDtos);
	}

	@Test
	public void should_find_task_by_id() {

		Integer projectId = 1;
		Integer taskId = 2;

		Project project = Project.builder()
				.id(projectId)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		Task tasks = Task.builder()
				.id(taskId)
				.name("Spring boot")
				.description("test your code")
				.dueDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.project(project)
				.build();

		TaskDto tasksDto = TaskDto.builder()
				.id(taskId)
				.name("Spring boot")
				.description("test your code")
				.dueDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.projectId(projectId)
				.deleted(false)
				.build();

		Mockito.when(tasksRepository.findByIdAndProjectId(taskId,projectId))
				.thenReturn(Optional.ofNullable(tasks));
		Mockito.when(tasksMapper.fromEntityToDto(tasks))
				.thenReturn(tasksDto);

		TaskDto resultDto = tasksServiceImplement.findTaskById(taskId, projectId);

		assertThat(resultDto).isNotNull();
		Mockito.verify(tasksRepository, Mockito.times(1)).findByIdAndProjectId(taskId, projectId);
	}

	@Test
	public void should_save_task() {

		TaskDto taskDto = TaskDto.builder()
				.name("spring")
				.description("boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.build();

		Project project = Project.builder()
				.id(1)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		Task task = Task.builder()
				.name("spring")
				.description("boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.build();

		when(projectRepository.findById(taskDto.getProjectId())).thenReturn(Optional.of(project));
		when(tasksMapper.fromDtoToEntity(taskDto)).thenReturn(task);
		when(tasksRepository.save(task)).thenReturn(task);
		when(tasksMapper.fromEntityToDto(task)).thenReturn(taskDto);

		TaskDto savedTask = tasksServiceImplement.createTask(taskDto);

		assertEquals(task.getName(), taskDto.getName());
		assertEquals(task.getDescription(), taskDto.getDescription());
		assertEquals(task.getStatus(), taskDto.getStatus());
		assertEquals(task.getDueDate(), taskDto.getDueDate());
		assertEquals(task.getStatus(), taskDto.getStatus());

		assertThat(savedTask).isNotNull();

		verify(projectRepository, times(1)).findById(taskDto.getProjectId());
		verify(tasksMapper, times(1)).fromDtoToEntity(taskDto);
		verify(tasksRepository, times(1)).save(task);
	}

	@Test
	public void should_update_task_by_projectId() {

		Task task = Task.builder()
				.name("spring")
				.description("boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.build();


		TaskDto taskDto = TaskDto.builder()
				.id(1)
				.name("spring")
				.description("boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.projectId(5)
				.build();

		Task updateTask = Task.builder()
				.id(1)
				.name("Update spring")
				.description("Update boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.build();

		TaskDto UpdateTaskDto = TaskDto.builder()
				.id(1)
				.name("Update spring")
				.description("Update boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.projectId(5)
				.build();

		when(tasksRepository.findByIdAndProjectId(taskDto.getId(), taskDto.getProjectId())).thenReturn(Optional.of(task));
		when(tasksRepository.save(task)).thenReturn(updateTask);
		when(tasksMapper.fromEntityToDto(updateTask)).thenReturn(UpdateTaskDto);

		TaskDto updatedTask = tasksServiceImplement.updateTask(taskDto);

		assertThat(updatedTask).isNotNull();

		verify(tasksRepository, times(1)).findByIdAndProjectId(taskDto.getId(), taskDto.getProjectId());
		verify(tasksRepository, times(1)).save(task);
		verify(tasksMapper, times(1)).fromEntityToDto(updateTask);
	}

	@Test
	public void should_delete_task_by_projectId() {

		Project project = Project.builder()
				.id(1)
				.name("Spring boot")
				.description("test your code")
				.startDate(LocalDate.of(2024, 10, 24))
				.endDate(LocalDate.of(2024, 10, 29))
				.status("Active")
				.deleted(false)
				.build();

		Task task = Task.builder()
				.id(2)
				.name("spring")
				.description("boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.deleted(false)
				.project(project)
				.build();

		TaskDto tasksDto = TaskDto.builder()
				.id(2)
				.projectId(1)
				.name("spring")
				.description("boot")
				.dueDate(LocalDate.of(2024, 10, 24))
				.status("Active")
				.build();

		when(tasksRepository.findByIdAndProjectId(tasksDto.getId(), tasksDto.getProjectId())).thenReturn(Optional.of(task));

		tasksServiceImplement.deleteTask(tasksDto);

		verify(tasksRepository, times(1)).findByIdAndProjectId(tasksDto.getId(), tasksDto.getProjectId());
		verify(tasksRepository, times(1)).delete(task);

		when(tasksRepository.findById(tasksDto.getId())).thenReturn(Optional.empty());
		assertThat(tasksRepository.findById(tasksDto.getId())).isNotPresent();
	}

	@Test
	void should_search_tasks() {

		PaginationRequest paginationRequest = new PaginationRequest();
		TaskSearchCriteria taskSearchCriteria = new TaskSearchCriteria();
		Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize());
		Integer projectId = 1;

		Task task1 = Task.builder()
				.id(1)
				.name("Task One")
				.description("First task description")
				.status("Active")
				.deleted(false)
				.build();

		Task task2 = Task.builder()
				.id(2)
				.name("Task Two")
				.description("Second task description")
				.status("Inactive")
				.deleted(false)
				.build();

		List<Task> taskList = Arrays.asList(task1, task2);
		Page<Task> taskPage = new PageImpl<>(taskList, pageable, taskList.size());

		TaskDto taskDto1 = new TaskDto();
		taskDto1.setId(1);
		taskDto1.setName("Task One");
		taskDto1.setProjectId(projectId);

		TaskDto taskDto2 = new TaskDto();
		taskDto2.setId(2);
		taskDto2.setName("Task Two");
		taskDto2.setProjectId(projectId);

		when(taskCriteriaRepository.findAllWithFilters(paginationRequest, taskSearchCriteria, projectId)).thenReturn(taskPage);
		when(tasksMapper.fromEntityToDto(task1)).thenReturn(taskDto1);
		when(tasksMapper.fromEntityToDto(task2)).thenReturn(taskDto2);

		Page<TaskDto> result = tasksServiceImplement.searchTasks(paginationRequest, taskSearchCriteria, projectId);

		assertThat(result.getContent()).containsExactly(taskDto1, taskDto2);
		assertThat(result.getTotalElements()).isEqualTo(taskList.size());

		verify(taskCriteriaRepository).findAllWithFilters(paginationRequest, taskSearchCriteria, projectId);
		verify(tasksMapper).fromEntityToDto(task1);
		verify(tasksMapper).fromEntityToDto(task2);
	}
}