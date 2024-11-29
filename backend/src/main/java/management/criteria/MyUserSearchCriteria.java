package management.criteria;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MyUserSearchCriteria {

    private String userName;
    private String firstName;
    private String lastName;
    private String role;
}