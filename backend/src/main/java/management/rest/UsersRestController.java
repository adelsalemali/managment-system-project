package management.rest;
import lombok.extern.log4j.Log4j2;
import management.dto.UsersDto;
import management.criteria.UsersSearchCriteria;
import management.criteria.PaginationRequest;
import management.service.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Log4j2
@RestController
@RequestMapping ("/api")
public class UsersRestController {

    private final UsersService usersService;

    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping ("/users")
    public Page<UsersDto> findUsers(PaginationRequest paginationRequest) {
        return usersService.findUsers(paginationRequest);
    }

    @GetMapping ("/user/{id}")
    public UsersDto findUserById(@PathVariable Integer id) {
        return usersService.getUserById(id);
    }

    @PostMapping("/user")
    public UsersDto createUser(@Valid @RequestBody UsersDto myUserDto) {
        return usersService.createUser(myUserDto);
    }

    @PutMapping("/user/{id}")
    public UsersDto updateUser(@PathVariable Integer id, @Valid @RequestBody UsersDto myUserDto) {
        return usersService.updateUser(id, myUserDto);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        usersService.deleteUser(id);
    }

    @GetMapping("/user/search")
    public Page<UsersDto> searchInMyUserList(PaginationRequest paginationRequest,
                                             UsersSearchCriteria myUserSearchCriteria) {
        return usersService.searchUsers(paginationRequest, myUserSearchCriteria);
    }
}