package management.service;
import management.dto.UsersDto;
import management.criteria.UsersSearchCriteria;
import management.criteria.PaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@Service
public interface UsersService {


    Page<UsersDto> findUsers(PaginationRequest paginationRequest);

    UsersDto getUserById(Integer id);

    UsersDto createUser(UsersDto myUserDto);

    UsersDto updateUser(Integer id, UsersDto myUserDto);

    void deleteUser(Integer id);

    public Page<UsersDto> searchUsers(PaginationRequest paginationRequest, UsersSearchCriteria myUserSearchCriteria);
}
