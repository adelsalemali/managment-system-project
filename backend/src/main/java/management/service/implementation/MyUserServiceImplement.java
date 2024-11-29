package management.service.implementation;
import management.criteria.MyUserSearchCriteria;
import management.criteria.PaginationRequest;
import management.dto.MyUserDto;
import management.exception.EntityNotFoundException;
import management.mapper.MyUserMapper;
import management.model.*;
import management.repository.MyUserRepository;
import management.repository.criteria.MyUserCriteriaRepository;
import management.service.MyUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MyUserServiceImplement implements MyUserService {

    private final MyUserRepository myUserRepository;
    private final MyUserCriteriaRepository myUserCriteriaRepository;
    private final MyUserMapper myUserMapper;
    public MyUserServiceImplement(MyUserRepository myUserRepository, MyUserCriteriaRepository myUserCriteriaRepository, MyUserMapper myUserMapper) {
        this.myUserRepository = myUserRepository;
        this.myUserCriteriaRepository = myUserCriteriaRepository;
        this.myUserMapper = myUserMapper;
    }

    @Override
    public Page<MyUserDto> findUsers(PaginationRequest paginationRequest) {
        Sort sort = Sort.by(paginationRequest.getSortDirection(), paginationRequest.getSortBy());
        Pageable users = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(), sort);
        return myUserRepository
                .findAll(users)
                .map(myUser -> myUserMapper.fromEntityToDto(myUser));
    }
    @Override
    public MyUserDto getUserById(Integer id) {
        MyUser project = myUserRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MyUser.class," " +  id + " not found"));
        return myUserMapper.fromEntityToDto(project);
    }
    @Override
    public MyUserDto createUser(MyUserDto myUserDto) {
        MyUser project = myUserMapper.fromDtoToEntity(myUserDto);
        MyUser newProject = myUserRepository.save(project);
        return myUserMapper.fromEntityToDto(newProject);
    }
    @Override
    public MyUserDto updateUser(Integer userId, MyUserDto myUserDto) {

        MyUser myUser = myUserRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MyUser.class, userId + " not found"));

        MyUser updateMyUser = myUserMapper.fromDtoToEntity(myUserDto);
        updateMyUser.setId(myUser.getId());

        MyUser savedProject = myUserRepository.save(updateMyUser);

        return myUserMapper.fromEntityToDto(savedProject);
    }
    @Override
    public void deleteUser(Integer id) {

        MyUser project = myUserRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MyUser.class, "  " + id + "not found"));
        myUserRepository.delete(project);
    }

    @Override
    public Page<MyUserDto> searchUsers(PaginationRequest paginationRequest,
                                       MyUserSearchCriteria myUserSearchCriteria) {
        Page<MyUser> myUserPage = myUserCriteriaRepository
                .findAllWithFilters(paginationRequest,myUserSearchCriteria );
        return myUserPage.map(myUser -> myUserMapper.fromEntityToDto(myUser));
    }
}