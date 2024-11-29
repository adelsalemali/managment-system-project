package management.repository;

import java.util.List;
import java.util.Optional;

import management.model.Project;
import management.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    Page<Project> findByUserId(Integer projectId, Pageable pageable);
    Optional<Project> findByIdAndUserId(Integer projectId, Integer userId);
}