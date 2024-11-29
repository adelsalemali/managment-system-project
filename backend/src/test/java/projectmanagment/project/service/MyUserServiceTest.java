package projectmanagment.project.service;
import management.dto.MyUserDto;
import management.mapper.MyUserMapper;
import management.model.MyUser;
import management.criteria.MyUserSearchCriteria;
import management.criteria.PaginationRequest;
import management.repository.MyUserRepository;
import management.repository.criteria.MyUserCriteriaRepository;
import management.service.implementation.MyUserServiceImplement;
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
    private MyUserRepository myUserRepository;
    @Mock
    private MyUserMapper myUserMapper;

    @Mock
    private MyUserCriteriaRepository myUserCriteriaRepository;
    @InjectMocks
    private MyUserServiceImplement myUserServiceImplement;

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

        MyUser myUser1 = MyUser.builder()
                .id(1)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();
        MyUser myUser2 = MyUser.builder()
                .id(2)
                .firstName("Ahmed")
                .lastName("Ali")
                .userName("ahmed_ali")
                .password("ahmed")
                .deleted(false)
                .build();

        MyUserDto myUserDto1 = MyUserDto.builder()
                .id(1)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();
        MyUserDto myUserDto2 = MyUserDto.builder()
                .id(2)
                .firstName("Ahmed")
                .lastName("Ali")
                .userName("ahmed_ali")
                .password("ahmed")
                .deleted(false)
                .build();

        Page<MyUser> page = new PageImpl<>(List.of(myUser1, myUser2));
        when(myUserRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(myUserMapper.fromEntityToDto(myUser1)).thenReturn(myUserDto1);
        when(myUserMapper.fromEntityToDto(myUser2)).thenReturn(myUserDto2);

        Page<MyUserDto> result = myUserServiceImplement.findUsers(paginationRequest);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(myUserDto1, myUserDto2);

        verify(myUserRepository).findAll(any(Pageable.class));
        verify(myUserMapper).fromEntityToDto(myUser1);
        verify(myUserMapper).fromEntityToDto(myUser2);
    }

    @Test
    public void should_find_user_by_id() {

        Integer userId = 1;
        MyUser myUser = MyUser.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        MyUserDto myUserDto = MyUserDto.builder()
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


        MyUserDto find = myUserServiceImplement.getUserById(userId);

        assertThat(find).isNotNull();
        assertThat(find).isEqualTo(myUserDto);


        verify(myUserRepository, times(1)).findById(userId);

        verify(myUserMapper, times(1)).fromEntityToDto(myUser);
    }

    @Test
    public void should_save_user() {

        Integer userId = 1;
        MyUser myUser = MyUser.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        MyUserDto myUserDto = MyUserDto.builder()
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

        MyUserDto savedUser = myUserServiceImplement.createUser(myUserDto);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getFirstName()).isEqualTo(myUserDto.getFirstName());

        verify(myUserMapper, times(1)).fromDtoToEntity(myUserDto);
        verify(myUserRepository, times(1)).save(myUser);
        verify(myUserMapper, times(1)).fromEntityToDto(myUser);
    }

    @Test
    public void should_update_user() {

        Integer userId = 1;

        MyUser myUser = MyUser.builder()
                .id(userId)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        MyUserDto myUserDto = MyUserDto.builder()
                .id(userId)
                .firstName("Update Ahmed")
                .lastName("Update Ali")
                .userName("Update ahmed_ali")
                .password("Update ahmed")
                .deleted(false)
                .build();

        MyUser updatedUser = MyUser.builder()
                .id(userId)
                .firstName("Update Ahmed")
                .lastName("Update Ali")
                .userName("Update ahmed_ali")
                .password("Update ahmed")
                .deleted(false)
                .build();

        MyUserDto updatedUserDto = MyUserDto.builder()
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

        MyUserDto result = myUserServiceImplement.updateUser(userId, myUserDto);

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

        MyUser myUser = MyUser.builder()
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
        MyUserSearchCriteria searchCriteria = new MyUserSearchCriteria();
        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize());

        MyUser user1 = MyUser.builder()
                .id(1)
                .firstName("Adel")
                .lastName("Salem")
                .userName("adel_salem")
                .password("adel")
                .deleted(false)
                .build();

        MyUser user2 = MyUser.builder()
                .id(2)
                .firstName("omar")
                .lastName("ali")
                .userName("omar_ali")
                .password("omar")
                .deleted(false)
                .build();

        List<MyUser> userList = Arrays.asList(user1, user2);
        Page<MyUser> userPage = new PageImpl<>(userList, pageable, userList.size());

        MyUserDto userDto1 = new MyUserDto();
        userDto1.setId(1);
        userDto1.setFirstName("adel");
        userDto1.setLastName("salem");

        MyUserDto userDto2 = new MyUserDto();
        userDto2.setId(2);
        userDto2.setFirstName("omar");
        userDto2.setLastName("ali");

        when(myUserCriteriaRepository.findAllWithFilters(paginationRequest, searchCriteria)).thenReturn(userPage);
        when(myUserMapper.fromEntityToDto(user1)).thenReturn(userDto1);
        when(myUserMapper.fromEntityToDto(user2)).thenReturn(userDto2);

        Page<MyUserDto> result = myUserServiceImplement.searchUsers(paginationRequest, searchCriteria);

        assertThat(result.getContent()).containsExactly(userDto1, userDto2);
        assertThat(result.getTotalElements()).isEqualTo(userList.size());

        verify(myUserCriteriaRepository).findAllWithFilters(paginationRequest, searchCriteria);
        verify(myUserMapper).fromEntityToDto(user1);
        verify(myUserMapper).fromEntityToDto(user2);
    }
}