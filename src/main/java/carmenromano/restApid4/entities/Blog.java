package carmenromano.restApid4.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Random;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "blogPosts")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private int tempoDiLettura;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Blog( String categoria, String titolo, String cover, String contenuto, int tempoDiLettura) {

        this.categoria = categoria;
        this.titolo = titolo;
        this.cover = cover;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
    }



}
