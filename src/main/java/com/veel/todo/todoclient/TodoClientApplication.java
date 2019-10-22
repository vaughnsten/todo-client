package com.veel.todo.todoclient;

import com.veel.todo.todoclient.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoClientApplication {

    public static void main(String[] args) {

        //SpringApplication.run(TodoClientApplication.class, args);
    SpringApplication app = new SpringApplication(TodoClientApplication.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
    }
    private Logger log = LoggerFactory.getLogger(TodoClientApplication.class);

    @Bean
    public CommandLineRunner process(ToDoRestClient client) {
        return args -> {
            Iterable<ToDo> toDos = client.findAll();
            assert toDos != null;
            toDos.forEach( toDo -> log.info(toDo.toString()));

            ToDo newtoDo = client.upsert(new ToDo("Drink plenty of water daily!"));
            assert newtoDo != null;
            log.info(newtoDo.toString());

            ToDo toDo = client.findById(newtoDo.getId());
            assert toDos != null;
            log.info(toDo.toString());

            ToDo completed = client.setCompleted(newtoDo.getId());
            assert completed.isCompleted();
            log.info(completed.toString());

            client.delete(newtoDo.getId());
            assert client.findById(newtoDo.getId()) == null;
        };
    }

}
