package dk.trustworks.aggregaterservice.network.queries;

import dk.trustworks.aggregaterservice.repository.ClientRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.aggregaterservice.ActionHelper.ok;


public class ClientQueryController {

    private ClientRepository clientRepository;

    public ClientQueryController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void getAll(RoutingContext rc) {
        clientRepository.getAll().subscribe(ok(rc));
    }

}
