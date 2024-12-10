package management.service.implementation;
import management.criteria.UsersSearchCriteria;
import management.criteria.PaginationRequest;
import management.dto.UsersDto;
import management.exception.EntityNotFoundException;
import management.mapper.UsersMapper;
import management.model.*;
import management.repository.UsersRepository;
import management.repository.criteria.UsersCriteriaRepository;
import management.service.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsersServiceImplement implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersCriteriaRepository usersCriteriaRepository;
    private final UsersMapper usersMapper;
    public UsersServiceImplement(UsersRepository usersRepository, UsersCriteriaRepository usersCriteriaRepository, UsersMapper myUserMapper) {
        this.usersRepository = usersRepository;
        this.usersCriteriaRepository = usersCriteriaRepository;
        this.usersMapper = myUserMapper;
    }

    @Override
    public Page<UsersDto> findUsers(PaginationRequest paginationRequest) {
        Sort sort = Sort.by(paginationRequest.getSortDirection(), paginationRequest.getSortBy());
        Pageable users = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(), sort);
        return usersRepository
                .findAll(users)
                .map(myUser -> usersMapper.fromEntityToDto(myUser));
    }
    @Override
    public UsersDto getUserById(Integer id) {
        Users project = usersRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Users.class," " +  id + " not found"));
        return usersMapper.fromEntityToDto(project);
    }
    @Override
    public UsersDto createUser(UsersDto myUserDto) {
        Users user = usersMapper.fromDtoToEntity(myUserDto);
        Users newUser = usersRepository.save(user);
        return usersMapper.fromEntityToDto(newUser);
    }
    @Override
    public UsersDto updateUser(Integer userId, UsersDto myUserDto) {

        Users myUser = usersRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(Users.class, userId + " not found"));

        Users updateMyUser = usersMapper.fromDtoToEntity(myUserDto);
        updateMyUser.setId(myUser.getId());

        Users savedProject = usersRepository.save(updateMyUser);

        return usersMapper.fromEntityToDto(savedProject);
    }
    @Override
    public void deleteUser(Integer id) {

        Users project = usersRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Users.class, "  " + id + "not found"));
        usersRepository.delete(project);
    }

    @Override
    public Page<UsersDto> searchUsers(PaginationRequest paginationRequest,
                                      UsersSearchCriteria myUserSearchCriteria) {
        Page<Users> myUserPage = usersCriteriaRepository
                .findAllWithFilters(paginationRequest,myUserSearchCriteria );
        return myUserPage.map(myUser -> usersMapper.fromEntityToDto(myUser));
    }
}