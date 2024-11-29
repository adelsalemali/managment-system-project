package management.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

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
@SQLDelete (sql = "UPDATE project SET deleted = true WHERE project_id=?")
@Where (clause = "deleted=false")
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Integer id ;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	@Column(name = "status")
	private String status ;
	@Column(name = "deleted")
	private boolean deleted = Boolean.FALSE;

	@OneToMany(mappedBy = "project")
	private List<Task> tasks = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private MyUser user;
}
