package carmenromano.restApid4.payloads;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BlogPostsPayload (@NotEmpty(message = "Il campo 'categoria' è obbligatorio")
                                String categoria,
                                @NotEmpty(message = "Il campo 'titolo' è obbligatorio")
                                String titolo,
                                @NotEmpty(message = "Il campo 'contenuto' è obbligatorio")
                                String contenuto,
                                @NotNull(message = "Il campo 'tempoDiLettura' è obbligatorio")
                                @Min(value = 1, message = "Il campo 'tempoDiLettura' deve essere maggiore di 0")
                                int tempoDiLettura,
                                @NotNull(message = "Il campo 'authorId' è obbligatorio")
                                @Min(value = 1, message = "Il campo 'authorId' deve essere maggiore di 0")
                                int authorId
) {}

