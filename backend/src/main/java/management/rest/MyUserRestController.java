package management.rest;
import lombok.extern.log4j.Log4j2;
import management.dto.MyUserDto;
import management.criteria.MyUserSearchCriteria;
import management.criteria.PaginationRequest;
import management.service.MyUserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Log4j2
@RestController
@RequestMapping ("/api")
public class MyUserRestController {

    private final MyUserService myUserService;

    public MyUserRestController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping ("/users")
    public Page<MyUserDto> findUsers(PaginationRequest paginationRequest) {
        return myUserService.findUsers(paginationRequest);
    }

    @GetMapping ("/user/{id}")
    public MyUserDto findUserById(@PathVariable Integer id) {
        return myUserService.getUserById(id);
    }

    @PostMapping("/user")
    public MyUserDto createUser(@Valid @RequestBody MyUserDto myUserDto) {
        return myUserService.createUser(myUserDto);
    }

    @PutMapping("/user/{id}")
    public MyUserDto updateUser(@PathVariable Integer id, @Valid @RequestBody MyUserDto myUserDto) {
        return myUserService.updateUser(id, myUserDto);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        myUserService.deleteUser(id);
    }

    @GetMapping("/user/search")
    public Page<MyUserDto> searchInMyUserList(PaginationRequest paginationRequest,
                                              MyUserSearchCriteria myUserSearchCriteria) {
        return myUserService.searchUsers(paginationRequest, myUserSearchCriteria);
    }
}