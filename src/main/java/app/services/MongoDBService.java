package app.services;

import app.model.Todo;
import app.repos.TodoMongoDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class MongoDBService  {

  @Autowired
  private TodoMongoDBRepository repo;

  @PostConstruct
  void setup() {
    log.info("+++ MongoDB database is starting ...");
    repo.deleteAll();

    for (int i = 1; i <= 8; i++) {
      Todo todo = new Todo();
      todo.setTitle("Todo number " + i);
      todo.setBody("Body number " + i);
      addToItems(todo);
    }
  }

  public List<Todo> getAllItems() {
    return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
  }

  public void addToItems(Todo todo) {
    repo.save(todo);
  }

  public void removeItems(List<Todo> items) {
    repo.deleteAll(items);
  }
}
