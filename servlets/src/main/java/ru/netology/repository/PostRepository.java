package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
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
    List<Post> result = new ArrayList<>();
    posts.forEach(post ->
            {
              if(!post.isRemoved()){
                result.add(post);
              }
            });
    return result;
  }

  public Optional<Post> getById(long id) {
    try {
      if (!posts.isEmpty()) {
        return posts.stream()
                .filter(post -> post.getId() == id && !post.isRemoved())
                .findFirst();
      }
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
      return Optional.empty();
  }

  public Post save(Post post) {
    for (Post p : posts) {
      if (p.getId() == post.getId() && p.isRemoved()) {
        p.setContent(post.getContent());
        return post;
      }else if(post.isRemoved()) {
        throw new NotFoundException("Такой пост был ранее удален и перенесен в архив");
      }
    }
    posts.add(post);
    return post;
  }

  public void removeById(long id) {
    if(!posts.isEmpty()) {
      posts.forEach(post -> {
        if(post.getId() == id) {
          post.setRemoved(true);
        }
      });

    }
  }
}
