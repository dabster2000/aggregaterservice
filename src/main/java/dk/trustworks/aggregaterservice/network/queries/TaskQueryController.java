package dk.trustworks.aggregaterservice.network.queries;

import dk.trustworks.aggregaterservice.model.Task;
import dk.trustworks.aggregaterservice.repository.TaskRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.aggregaterservice.ActionHelper.ok;


public class TaskQueryController {

    private TaskRepository taskRepository;

    public TaskQueryController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void getAll(RoutingContext rc) {
        taskRepository.getAll().subscribe(ok(rc));
    }

}
