package management.mapper;
import management.dto.UsersDto;
import management.model.Users;
import org.springframework.stereotype.Component;
@Component
public class UsersMapper {

    public UsersDto fromEntityToDto(Users myUser) { // toDto

        UsersDto myUserDto = new UsersDto();
        myUserDto.setId(myUser.getId());
        myUserDto.setUserName(myUser.getUserName());
        myUserDto.setFirstName(myUser.getFirstName());
        myUserDto.setLastName(myUser.getLastName());
        myUserDto.setRole(myUser.getRole());
        myUserDto.setPassword(myUser.getPassword());

        return myUserDto;
    }

    public Users fromDtoToEntity(UsersDto myUserDto) { // fromDto

        Users myUser = new Users();
        myUser.setId(myUserDto.getId());
        myUser.setUserName(myUserDto.getUserName());
        myUser.setFirstName(myUserDto.getFirstName());
        myUser.setLastName(myUserDto.getLastName());
        myUser.setRole(myUserDto.getRole());
        myUser.setPassword(myUserDto.getPassword());

        return myUser;
    }
}
