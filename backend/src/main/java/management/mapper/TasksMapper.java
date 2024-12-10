package management.mapper;


import management.dto.TaskDto;
import management.model.Task;
import org.springframework.stereotype.Component;


@Component
public class TasksMapper {

	public TaskDto fromEntityToDto(Task task) { // toDto

		TaskDto tasksDto = new TaskDto();
		tasksDto.setId(task.getId());
		tasksDto.setName(task.getName());
		tasksDto.setDescription(task.getDescription());
		tasksDto.setDueDate(task.getDueDate());
		tasksDto.setStatus(task.getStatus());
		tasksDto.setProjectId(task.getProject().getId());

		return tasksDto; 
	}

	public Task fromDtoToEntity(TaskDto tasksDto) { // fromDto

		Task task = new Task();
		task.setId(tasksDto.getId());
		task.setName(tasksDto.getName());
		task.setDescription(tasksDto.getDescription());
		task.setDueDate(tasksDto.getDueDate());
		task.setStatus(tasksDto.getStatus());

		return task;
	}
}
