package dk.trustworks.aggregaterservice.network.queries;

import dk.trustworks.aggregaterservice.repository.ProjectRepository;
import dk.trustworks.aggregaterservice.repository.TaskRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.aggregaterservice.ActionHelper.ok;


public class ProjectQueryController {

    private ProjectRepository projectRepository;

    public ProjectQueryController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void getAll(RoutingContext rc) {
        projectRepository.getAll().subscribe(ok(rc));
    }

}
