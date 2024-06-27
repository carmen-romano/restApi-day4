package carmenromano.restApid4.repositories;

import carmenromano.restApid4.entities.Author;
import carmenromano.restApid4.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostsRepository extends JpaRepository<Blog, Integer> {

}
