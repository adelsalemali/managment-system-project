package management.service;
import management.dto.MyUserDto;
import management.criteria.MyUserSearchCriteria;
import management.criteria.PaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@Service
public interface MyUserService {


    Page<MyUserDto> findUsers(PaginationRequest paginationRequest);

    MyUserDto getUserById(Integer id);

    MyUserDto createUser(MyUserDto myUserDto);

    MyUserDto updateUser(Integer id, MyUserDto myUserDto);

    void deleteUser(Integer id);

    public Page<MyUserDto> searchUsers(PaginationRequest paginationRequest, MyUserSearchCriteria myUserSearchCriteria);
}
