package management.service;
import management.dto.TaskDto;
import management.criteria.PaginationRequest;
import management.criteria.TaskSearchCriteria;
import org.springframework.data.domain.Page;
public interface TasksService {

	Page<TaskDto> findTasks(PaginationRequest paginationRequest, Integer projectId);

	TaskDto findTaskById(Integer taskId, Integer projectId);

	 TaskDto createTask(TaskDto taskDTO);
	
	 TaskDto updateTask(TaskDto tasksDto);

	void deleteTask(TaskDto tasksDto);

	public Page<TaskDto> searchTasks(PaginationRequest paginationRequest,
					 TaskSearchCriteria taskSearchCriteria,
					 Integer projectId);
}
