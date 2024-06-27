package carmenromano.restApid4.services;

import carmenromano.restApid4.entities.Author;
import carmenromano.restApid4.exceptions.BadRequestException;
import carmenromano.restApid4.exceptions.NotFoundException;
import carmenromano.restApid4.payloads.AuthorPayload;
import carmenromano.restApid4.repositories.AuthorsRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class AuthorService {

    @Autowired
    private AuthorsRepository authorsRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Author> getAllAuthors(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return authorsRepository.findAll(pageable);
    }

    public Author saveAuthor(AuthorPayload body){
        this.authorsRepository.findByEmail(body.email()).ifPresent(
                author -> { throw new BadRequestException("L'email " + author.getEmail() + " è già in uso!");}
        );
        Author newAuthor = new Author();
        newAuthor.setNome(body.nome());
        newAuthor.setCognome(body.cognome());
        newAuthor.setEmail(body.email());
        newAuthor.setDataDiNascita(body.dataDiNascita());
        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" + newAuthor.getNome() + "+" + newAuthor.getCognome());
        return authorsRepository.save(newAuthor);
    }

    public Author findById(int authorId){
        return this.authorsRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public Author findAndUpdate(int id, AuthorPayload updatedAuthor){
        Author found = this.findById(id);
                found.setNome(updatedAuthor.nome());
                found.setCognome(updatedAuthor.cognome());
                found.setEmail(updatedAuthor.email());
                found.setDataDiNascita(updatedAuthor.dataDiNascita());
                return this.authorsRepository.save(found);
    }

    public void findAndDelete(int id){
        Author found = this.findById(id);
        this.authorsRepository.delete(found);
        }

    public Author uploadImage(int id, MultipartFile file) throws IOException {
        Author found = this.findById(id);
        String avatarURL = (String)this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(avatarURL);
        return (Author)this.authorsRepository.save(found);
    }
    }

