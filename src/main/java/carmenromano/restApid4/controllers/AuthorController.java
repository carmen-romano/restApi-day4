package carmenromano.restApid4.controllers;


import carmenromano.restApid4.entities.Author;
import carmenromano.restApid4.exceptions.BadRequestException;
import carmenromano.restApid4.payloads.AuthorPayload;
import carmenromano.restApid4.payloads.BlogPostsPayload;
import carmenromano.restApid4.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public Page<Author> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.authorService.getAllAuthors(page, size, sortBy);
    }

    @GetMapping("/{authorId}")
    public Author findById(@PathVariable int authorId) {
        return this.authorService.findById(authorId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private AuthorPayload saveAuthor(@RequestBody @Validated AuthorPayload body, BindingResult result){
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        this.authorService.saveAuthor(body);
        return new AuthorPayload(body.nome(), body.cognome(),body.email(),body.dataDiNascita());
    }


    @PutMapping("/{authorId}")
    private Author findAndUpdateBlog(@PathVariable int authorId, @RequestBody AuthorPayload body){
        return this.authorService.findAndUpdate(authorId, body);
    }


    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int authorId) {
        this.authorService.findAndDelete(authorId);
    }



  //  @GetMapping("/nome")
  //  public Author findByName(@RequestParam String nome){
  //      return this.authorService.findByName(nome);
  //  }

}
