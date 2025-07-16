package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

  public PostRepository() {
    System.out.println("PostRepository создан: " + this);
  }
  private final List<Post> posts = new ArrayList<>();
  public List<Post> all() {
    System.out.println(posts);
    return posts;
  }

  public Optional<Post> getById(long id) {
    if(!posts.isEmpty()) {
      return posts.stream().filter(p -> p.getId() == id).findFirst();
    }
    System.out.println("Список пуст");
    return Optional.empty();
  }

  public Post save(Post post) {
    for (Post p : posts) {
      if (p.getId() == post.getId()) {
        p.setContent(post.getContent());
        return post;
      }
    }
    posts.add(post);
    return post;
  }

  public void removeById(long id) {
    getById(id).ifPresent(posts::remove);
  }
}
