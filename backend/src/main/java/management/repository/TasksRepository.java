package management.repository;

import management.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TasksRepository extends JpaRepository<Task,Integer> {
	
	Page<Task> findByProjectId(Integer projectId, Pageable pageable);
	Optional<Task> findByIdAndProjectId(Integer taskId, Integer projectId);
}
