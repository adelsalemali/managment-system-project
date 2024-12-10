package management.rest;

import lombok.extern.log4j.Log4j2;
import management.dto.TaskDto;
import management.criteria.PaginationRequest;
import management.criteria.TaskSearchCriteria;
import management.model.Task;
import management.service.TasksService;
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
@RequestMapping("/api/v1")
public class TasksRestController {

	private final TasksService tasksService;

	public TasksRestController(TasksService tasksService) {
		this.tasksService = tasksService;
	}

	@GetMapping("/tasks")
	public Page<TaskDto> findTasks(PaginationRequest paginationRequest, Integer projectId) {
		return tasksService.findTasks(paginationRequest, projectId);
	}

	@GetMapping("/task/{taskId}/{projectId}")
	public TaskDto findTaskById(@PathVariable Integer projectId, @PathVariable Integer taskId) {
		log.info("Project ID: " + projectId);
		log.info("Task ID: " + taskId);
		return tasksService.findTaskById(taskId, projectId);
	}

	@PostMapping("/task")
	public TaskDto createTask(@Valid @RequestBody TaskDto taskDTO) {
		return  tasksService.createTask(taskDTO);
	}

	@PutMapping("/task")
	public TaskDto updateTask(@Valid @RequestBody TaskDto tasksDto) {
		log.info("Task ID: " + tasksDto.getId());
		log.info("Project ID: " + tasksDto.getProjectId());
		return tasksService.updateTask(tasksDto);
	}

	@DeleteMapping("/task")
	public void deleteTask(@RequestBody TaskDto tasksDto) {
		log.info("Delete Task ID: " + tasksDto.getId());
		log.info("Delete Project ID: " + tasksDto.getProjectId());
		tasksService.deleteTask(tasksDto);
	}

	@GetMapping("/task/search")
	public Page<TaskDto> searchInTaskList(PaginationRequest paginationRequest,
					      TaskSearchCriteria taskSearchCriteria,
					      Integer projectId) {
		return tasksService.searchTasks(paginationRequest, taskSearchCriteria, projectId);
	}
}
