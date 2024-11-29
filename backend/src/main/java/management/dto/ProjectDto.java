package management.dto;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProjectDto {

	private Integer id;

	@NotBlank(message = "Name shouldn't be null and empty")
	private String name;

	@NotBlank(message = "Description shouldn't be null and empty")
	private String description;

	@NotNull(message = "Start date must not be null or empty")
	@FutureOrPresent(message = "Start date must be in the present or future")
	@DateTimeFormat(pattern = "mm-dd-yyyy")
	private LocalDate startDate;

	@NotNull(message = "End date must not be null or empty")
	@FutureOrPresent(message = "End date must be in the present or future")
	@DateTimeFormat(pattern = "mm-dd-yyyy")
	private LocalDate endDate;

	@NotBlank(message = "Status shouldn't be null and empty")
	private String status;

	private boolean deleted = Boolean.FALSE;

	private Integer userId;

}
