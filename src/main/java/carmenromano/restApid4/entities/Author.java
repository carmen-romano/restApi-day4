package carmenromano.restApid4.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Random;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
    private String avatar;


    public Author( String nome, String cognome, String email, LocalDate dataDiNascita, String avatar) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        this.avatar = avatar;
    }
}
