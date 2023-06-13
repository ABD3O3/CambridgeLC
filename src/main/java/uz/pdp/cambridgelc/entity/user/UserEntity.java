package uz.pdp.cambridgelc.entity.user;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.group.GroupEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;
    private Integer age;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]{8,}$",message = "Abdilaz odam bomisan")
    private String password;
    @NotBlank(message = "Phone number cannot be empty")
    @Column(unique = true)
    private String username;
    private Integer credits;
    private Integer solvedTasks;
    private Boolean isOut;
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    private GroupEntity group;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
