package projectmanagment.project.service;
import management.dto.UsersDto;
import management.mapper.UsersMapper;
import management.model.Users;
import management.criteria.UsersSearchCriteria;
import management.criteria.PaginationRequest;
import management.repository.UsersRepository;
import management.repository.criteria.UsersCriteriaRepository;
import management.service.implementation.UsersServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
public class MyUserServiceTest {

    @Mock
    private UsersRepository myUserRepository;
    @Mock
    private UsersMapper myUserMapper;

    @Mock
    private UsersCriteriaRepository myUserCriteriaRepository;
    @InjectMocks
    private UsersServiceImplement myUserServiceImplement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_find_all_my_users() {

        PaginationRequest paginationRequest = new PaginationRequest();
        paginationRequest.setPageNumber(0);
        paginationRequest.setPageSize(2);
        paginationRequest.setSortBy("id");
        paginationRequest.setSortDirection(Sort.Direction.ASC);

        Users myUser1 = Users.builder()
                .id(1)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();
        Users myUser2 = Users.builder()
                .id(2)
                .firstName("Ahmed")
                .lastName("Ali")
                .userName("ahmed_ali")
                .password("ahmed")
                .deleted(false)
                .build();

        UsersDto myUserDto1 = UsersDto.builder()
                .id(1)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();
        UsersDto myUserDto2 = UsersDto.builder()
                .id(2)
                .firstName("Ahmed")
                .lastName("Ali")
                .userName("ahmed_ali")
                .password("ahmed")
                .deleted(false)
                .build();

        Page<Users> page = new PageImpl<>(List.of(myUser1, myUser2));
        when(myUserRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(myUserMapper.fromEntityToDto(myUser1)).thenReturn(myUserDto1);
        when(myUserMapper.fromEntityToDto(myUser2)).thenReturn(myUserDto2);

        Page<UsersDto> result = myUserServiceImplement.findUsers(paginationRequest);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(myUserDto1, myUserDto2);

        verify(myUserRepository).findAll(any(Pageable.class));
        verify(myUserMapper).fromEntityToDto(myUser1);
        verify(myUserMapper).fromEntityToDto(myUser2);
    }

    @Test
    public void should_find_user_by_id() {

        Integer userId = 1;
        Users myUser = Users.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        UsersDto myUserDto = UsersDto.builder()
                .id(2)
                .firstName("Ahmed")
                .lastName("Ali")
                .userName("ahmed_ali")
                .password("ahmed")
                .deleted(false)
                .build();

        Mockito.when(myUserRepository.findById(userId))
                .thenReturn(Optional.of(myUser));
        Mockito.when(myUserMapper.fromEntityToDto(myUser))
                .thenReturn(myUserDto);


        UsersDto find = myUserServiceImplement.getUserById(userId);

        assertThat(find).isNotNull();
        assertThat(find).isEqualTo(myUserDto);


        verify(myUserRepository, times(1)).findById(userId);

        verify(myUserMapper, times(1)).fromEntityToDto(myUser);
    }

    @Test
    public void should_save_user() {

        Integer userId = 1;
        Users myUser = Users.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        UsersDto myUserDto = UsersDto.builder()
                .id(2)
                .firstName("Ahmed")
                .lastName("Ali")
                .userName("ahmed_ali")
                .password("ahmed")
                .deleted(false)
                .build();


        when(myUserMapper.fromDtoToEntity(myUserDto)).thenReturn(myUser);
        when(myUserRepository.save(myUser)).thenReturn(myUser);
        when(myUserMapper.fromEntityToDto(myUser)).thenReturn(myUserDto);

        UsersDto savedUser = myUserServiceImplement.createUser(myUserDto);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getFirstName()).isEqualTo(myUserDto.getFirstName());

        verify(myUserMapper, times(1)).fromDtoToEntity(myUserDto);
        verify(myUserRepository, times(1)).save(myUser);
        verify(myUserMapper, times(1)).fromEntityToDto(myUser);
    }

    @Test
    public void should_update_user() {

        Integer userId = 1;

        Users myUser = Users.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        UsersDto myUserDto = UsersDto.builder()
                .id(userId)
                .firstName("Update Ahmed")
                .lastName("Update Ali")
                .userName("Update ahmed_ali")
                .password("Update ahmed")
                .deleted(false)
                .build();

        Users updatedUser = Users.builder()
                .id(userId)
                .firstName("Update Ahmed")
                .lastName("Update Ali")
                .userName("Update ahmed_ali")
                .password("Update ahmed")
                .deleted(false)
                .build();

        UsersDto updatedUserDto = UsersDto.builder()
                .id(userId)
                .firstName("Update Ahmed")
                .lastName("Update Ali")
                .userName("Update ahmed_ali")
                .password("Update ahmed")
                .deleted(false)
                .build();

        when(myUserRepository.findById(userId)).thenReturn(java.util.Optional.of(myUser));
        when(myUserMapper.fromDtoToEntity(myUserDto)).thenReturn(updatedUser);
        when(myUserRepository.save(updatedUser)).thenReturn(updatedUser);
        when(myUserMapper.fromEntityToDto(updatedUser)).thenReturn(updatedUserDto);

        UsersDto result = myUserServiceImplement.updateUser(userId, myUserDto);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(updatedUserDto.getFirstName());
        assertThat(result.getLastName()).isEqualTo(updatedUserDto.getLastName());

        verify(myUserRepository, times(1)).findById(userId);
        verify(myUserMapper, times(1)).fromDtoToEntity(myUserDto);
        verify(myUserRepository, times(1)).save(updatedUser);
        verify(myUserMapper, times(1)).fromEntityToDto(updatedUser);
    }

    @Test
    public void should_delete_user() {

        Integer userId = 1;

        Users myUser = Users.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        when(myUserRepository.findById(userId)).thenReturn(Optional.of(myUser));

        myUserServiceImplement.deleteUser(userId);

        verify(myUserRepository, times(1)).findById(userId);
        verify(myUserRepository, times(1)).delete(myUser);

        when(myUserRepository.findById(userId)).thenReturn(Optional.empty());
        assertThat(myUserRepository.findById(userId)).isNotPresent();
    }

    @Test
    void should_search_users() {

        PaginationRequest paginationRequest = new PaginationRequest();
        UsersSearchCriteria searchCriteria = new UsersSearchCriteria();
        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize());

        Users user1 = Users.builder()
                .id(1)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        Users user2 = Users.builder()
                .id(2)
                .firstName("omar")
                .lastName("ali")
                .userName("omar_ali")
                .password("omar")
                .deleted(false)
                .build();

        List<Users> userList = Arrays.asList(user1, user2);
        Page<Users> userPage = new PageImpl<>(userList, pageable, userList.size());

        UsersDto userDto1 = new UsersDto();
        userDto1.setId(1);
        userDto1.setFirstName("adel");
        userDto1.setLastName("salem");

        UsersDto userDto2 = new UsersDto();
        userDto2.setId(2);
        userDto2.setFirstName("omar");
        userDto2.setLastName("ali");

        when(myUserCriteriaRepository.findAllWithFilters(paginationRequest, searchCriteria)).thenReturn(userPage);
        when(myUserMapper.fromEntityToDto(user1)).thenReturn(userDto1);
        when(myUserMapper.fromEntityToDto(user2)).thenReturn(userDto2);

        Page<UsersDto> result = myUserServiceImplement.searchUsers(paginationRequest, searchCriteria);

        assertThat(result.getContent()).containsExactly(userDto1, userDto2);
        assertThat(result.getTotalElements()).isEqualTo(userList.size());

        verify(myUserCriteriaRepository).findAllWithFilters(paginationRequest, searchCriteria);
        verify(myUserMapper).fromEntityToDto(user1);
        verify(myUserMapper).fromEntityToDto(user2);
    }
}