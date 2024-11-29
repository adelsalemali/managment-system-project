package management.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete (sql = "UPDATE task SET deleted = true WHERE task_id=?")
@Where (clause = "deleted=false")
@Table(name = "task")

public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Integer id;
	@Column(name = "task_name")
	private String name;
	@Column(name = "task_description")
	private String description;
	@Column(name = "due_date")
	private LocalDate  dueDate;
	@Column(name = "status")
	private String status;
	private boolean deleted = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
}

