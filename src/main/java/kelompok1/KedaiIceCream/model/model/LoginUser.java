package kelompok1.KedaiIceCream.model.model;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRegister
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginUser {

    @NotBlank()
    @Size(max = 100)
    private String username;

    @NotBlank()
    @Size(max = 100)
    private String password;

    
}