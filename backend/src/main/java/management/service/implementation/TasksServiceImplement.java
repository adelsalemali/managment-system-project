package management.service.implementation;

import management.criteria.PaginationRequest;
import management.criteria.TaskSearchCriteria;
import management.dto.TaskDto;
import management.exception.EntityNotFoundException;
import management.mapper.TasksMapper;
import management.model.*;
import management.repository.ProjectRepository;
import management.repository.TasksRepository;
import management.repository.criteria.TaskCriteriaRepository;
import management.service.TasksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class TasksServiceImplement implements TasksService {
	private final TasksRepository tasksRepository;
	private final ProjectRepository projectRepository;
	private final TaskCriteriaRepository taskCriteriaRepository;
	private final TasksMapper tasksMapper;
	public TasksServiceImplement(TasksRepository tasksRepository,
				     ProjectRepository projectRepository,
				     TaskCriteriaRepository taskCriteriaRepository, TasksMapper tasksMapper) {
		this.tasksRepository = tasksRepository;
		this.projectRepository = projectRepository;
		this.taskCriteriaRepository = taskCriteriaRepository;
		this.tasksMapper = tasksMapper;
	}

	@Override
	public Page<TaskDto> findTasks(PaginationRequest paginationRequest, Integer projectId) {

		Sort sort = Sort.by(paginationRequest.getSortDirection(), paginationRequest.getSortBy());
		Pageable pageable = PageRequest.of(
				paginationRequest.getPageNumber(),
				paginationRequest.getPageSize(),
				sort);

		Page<TaskDto> tasksDtos = tasksRepository
				.findByProjectId(projectId, pageable)
				.map(task -> tasksMapper.fromEntityToDto(task));

		return tasksDtos;
	}
	@Override
	public TaskDto findTaskById(Integer taskId, Integer projectId) {

		Task task = tasksRepository.findByIdAndProjectId(taskId, projectId)
				.orElseThrow(() -> new EntityNotFoundException(TaskDto.class, " " +
						taskId + " with associated PROJECT " + projectId + " not found"));
		return tasksMapper.fromEntityToDto(task);
	}

	@Override
	public TaskDto createTask(TaskDto taskDTO) {

		Project project = projectRepository.findById(taskDTO.getProjectId()).orElseThrow(()
				                            -> new EntityNotFoundException(TaskDto.class, " " + taskDTO.getId() + "  with associated PROJECT " + taskDTO.getProjectId() + " not found"));
		Task newTask = tasksMapper.fromDtoToEntity(taskDTO);
		newTask.setProject(project);

		Task saveNewTask = tasksRepository.save(newTask);
		return tasksMapper.fromEntityToDto(saveNewTask);
	}

	@Override
	public TaskDto updateTask(TaskDto tasksDto) {

	 	Task task = tasksRepository.findByIdAndProjectId(tasksDto.getId(),
				                                 tasksDto.getProjectId()).orElseThrow(()
				-> new EntityNotFoundException(TaskDto.class," " + tasksDto.getId()
				+ " with associated project " + tasksDto.getProjectId() + " not found"));

		task.setName(tasksDto.getName());
		task.setDescription(tasksDto.getDescription());
		task.setDueDate(tasksDto.getDueDate());
		task.setStatus(tasksDto.getStatus());

		Task updateTask = tasksRepository.save(task);
		return tasksMapper.fromEntityToDto(updateTask);
	}

	@Override
	public void deleteTask(TaskDto tasksDto) {
		Task task = tasksRepository.findByIdAndProjectId(tasksDto.getId(), tasksDto.getProjectId()).orElseThrow(() ->
				new EntityNotFoundException(TaskDto.class," " + tasksDto.getId() + " with associated PROJECT " + tasksDto.getProjectId() + " not found"));
		tasksRepository.delete(task);
	}

	@Override
	public Page<TaskDto> searchTasks(PaginationRequest paginationRequest,
					 TaskSearchCriteria taskSearchCriteria,
					 Integer projectId) {
		if (projectId == null) {
			throw new IllegalArgumentException("User ID must not be null");
		}
		Page<Task> taskPage = taskCriteriaRepository
				.findAllWithFilters(paginationRequest,taskSearchCriteria, projectId);
		return taskPage.map(task -> tasksMapper.fromEntityToDto(task));
	}
}
