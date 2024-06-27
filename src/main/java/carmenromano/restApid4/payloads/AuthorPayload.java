package carmenromano.restApid4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuthorPayload(@NotEmpty(message = "Il campo 'nome' è obbligatorio")
                            String nome,
                            @NotEmpty(message = "Il campo 'cognome' è obbligatorio")
                            String cognome,
                            @NotEmpty(message = "Il campo 'email' è obbligatorio")
                            @Email(message = "L'email inserita non è valida!")
                            String email,
                            @NotNull(message = "Il campo 'dataDiNascita' è obbligatorio")
                            LocalDate dataDiNascita
                           ) {
}
