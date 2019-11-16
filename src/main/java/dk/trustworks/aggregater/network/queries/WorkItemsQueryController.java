package dk.trustworks.aggregater.network.queries;

import dk.trustworks.aggregater.repository.WorkItemsRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.aggregater.ActionHelper.ok;


public class WorkItemsQueryController {

    private WorkItemsRepository workItemsRepository;

    public WorkItemsQueryController(WorkItemsRepository workItemsRepository) {
        this.workItemsRepository = workItemsRepository;
    }

    public void getAll(RoutingContext rc) {
        workItemsRepository.getAllWork().subscribe(ok(rc));
    }

}
