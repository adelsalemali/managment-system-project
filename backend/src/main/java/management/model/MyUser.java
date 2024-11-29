package management.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete (sql = "UPDATE user SET deleted = true WHERE user_id =?")
@Where (clause = "deleted=false")
@Table(name = "user")
public class MyUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String role;
    private boolean deleted = Boolean.FALSE;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Project> project = new ArrayList<>();
}
