package carmenromano.restApid4.controllers;

import carmenromano.restApid4.entities.Author;
import carmenromano.restApid4.entities.Blog;
import carmenromano.restApid4.exceptions.BadRequestException;
import carmenromano.restApid4.payloads.BlogPostsPayload;
import carmenromano.restApid4.payloads.BlogPostsResponsePayload;
import carmenromano.restApid4.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/blogPosts")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public Page<Blog> getAllBlogPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.blogService.getAllBlogPosts(page, size, sortBy);
    }

    @GetMapping("/{blogId}")
    private Blog findUserById(@PathVariable int blogId) {
        return this.blogService.findById(blogId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private BlogPostsPayload saveBlog(@RequestBody @Validated BlogPostsPayload body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        this.blogService.saveBlog(body);
        return new BlogPostsPayload(body.categoria(), body.titolo(), body.contenuto(), body.tempoDiLettura(), body.authorId());
    }


    @PutMapping("/{blogId}")
    private Blog findAndUpdateBlog(@PathVariable int blogId, @RequestBody BlogPostsPayload body) {
        return this.blogService.findByIdAndUpdate(blogId, body);
    }


    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findAndDeleteBlog(@PathVariable int blogId) {
        this.blogService.findAndDelete(blogId);
    }



}

