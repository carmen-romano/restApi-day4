package carmenromano.restApid4.services;
import com.cloudinary.Cloudinary;
import carmenromano.restApid4.entities.Author;
import carmenromano.restApid4.entities.Blog;
import carmenromano.restApid4.exceptions.NotFoundException;
import carmenromano.restApid4.payloads.BlogPostsPayload;
import carmenromano.restApid4.repositories.AuthorsRepository;
import carmenromano.restApid4.repositories.BlogPostsRepository;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class BlogService {
    @Autowired
   private  BlogPostsRepository blogPostsRepository;
    @Autowired
    private AuthorsRepository authorRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Blog> getAllBlogPosts(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 30) pageSize = 30;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return blogPostsRepository.findAll(pageable);
    }

    public Blog saveBlog(BlogPostsPayload payload){
            Author author = authorRepository.findById(payload.authorId())
            .orElseThrow(() -> new NotFoundException(payload.authorId()));
            Blog newBlog = new Blog();
            newBlog.setCategoria(payload.categoria());
            newBlog.setTitolo(payload.titolo());
            newBlog.setContenuto(payload.contenuto());
            newBlog.setTempoDiLettura(payload.tempoDiLettura());
        newBlog.setAuthor(author);
        newBlog.setCover("http://picsum.photos/200/300");
            return blogPostsRepository.save(newBlog);
}

    public Blog findById(int blogId){
        return this.blogPostsRepository.findById(blogId).orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(int id, BlogPostsPayload updatedBlog){
        Blog found = this.findById(id);
                found.setCategoria(updatedBlog.categoria());
                found.setTitolo(updatedBlog.titolo());
                found.setContenuto(updatedBlog.contenuto());
                found.setTempoDiLettura(updatedBlog.tempoDiLettura());
        if(found.getAuthor().getId()!= updatedBlog.authorId()) {
            Author newAuthor = authorService.findById(updatedBlog.authorId());
            found.setAuthor(newAuthor);
        }
                return this.blogPostsRepository.save(found);
            }


    public void findAndDelete(int id){
        Blog found = this.findById(id);
        this.blogPostsRepository.delete(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    }

