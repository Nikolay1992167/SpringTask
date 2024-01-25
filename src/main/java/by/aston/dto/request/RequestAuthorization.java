package by.aston.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestAuthorization {

    @NotBlank
    @Size(max = 10)
    private String login;

    @NotBlank
    @Size(max = 10)
    private String password;
}
