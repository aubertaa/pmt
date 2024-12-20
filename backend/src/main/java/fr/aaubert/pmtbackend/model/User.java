package fr.aaubert.pmtbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "user_entity", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long userId;

    @NotNull(message = "Username is mandatory")
    private String userName;
    
    @NotNull(message = "Email is mandatory")
    private String email;
    
    private String password;

    private Boolean notifications = false;

    // Define the relationship with ProjectMember
    @OneToMany(mappedBy = "id.userId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMember> projectMembers;

    @Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ "]";
	}

}
