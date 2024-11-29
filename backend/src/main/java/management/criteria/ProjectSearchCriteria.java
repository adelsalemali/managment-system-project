package management.criteria;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectSearchCriteria {

    private Integer id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
