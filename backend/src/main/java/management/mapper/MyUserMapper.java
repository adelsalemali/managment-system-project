package management.mapper;
import management.dto.MyUserDto;
import management.model.MyUser;
import org.springframework.stereotype.Component;
@Component
public class MyUserMapper {

    public MyUserDto fromEntityToDto(MyUser myUser) { // toDto

        MyUserDto myUserDto = new MyUserDto();
        myUserDto.setId(myUser.getId());
        myUserDto.setUserName(myUser.getUserName());
        myUserDto.setFirstName(myUser.getFirstName());
        myUserDto.setLastName(myUser.getLastName());
        myUserDto.setRole(myUser.getRole());
        myUserDto.setPassword(myUser.getPassword());

        return myUserDto;
    }

    public MyUser fromDtoToEntity(MyUserDto myUserDto) { // fromDto

        MyUser myUser = new MyUser();
        myUser.setId(myUserDto.getId());
        myUser.setUserName(myUserDto.getUserName());
        myUser.setFirstName(myUserDto.getFirstName());
        myUser.setLastName(myUserDto.getLastName());
        myUser.setRole(myUserDto.getRole());
        myUser.setPassword(myUserDto.getPassword());

        return myUser;
    }
}
