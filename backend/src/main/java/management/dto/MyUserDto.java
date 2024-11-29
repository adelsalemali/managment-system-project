package management.dto;

import lombok.*;


import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUserDto {

    private Integer id;
    @NotBlank (message = "User name shouldn't be null and empty")
    private String userName;
    @NotBlank(message = "First name shouldn't be null and empty")
    private String firstName;
    @NotBlank(message = "Last name shouldn't be null and empty")
    private String lastName;
    private String role;
    @NotBlank(message = "Password shouldn't be null and empty")
    private String password;
    private boolean deleted = Boolean.FALSE;
}