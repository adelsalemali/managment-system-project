package management.dto;
import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

	private Integer id; 
	
	@NotBlank(message = "Name shouldn't be null and empty")
	private String name;

	@NotBlank(message = "Description shouldn't be null and empty")
	private String description;

	@NotNull(message = "Start date must not be null or empty")
	@FutureOrPresent(message = "Start date must be in the present or future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;
	
	@NotBlank(message = "Status shouldn't be Blank")
	private String status;

	private boolean deleted = Boolean.FALSE;

	private Integer projectId;

}
