package dk.trustworks.aggregaterservice.network.queries;

import dk.trustworks.aggregaterservice.repository.UserRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.aggregaterservice.ActionHelper.ok;


public class UserQueryController {

    private UserRepository userRepository;

    public UserQueryController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void getAll(RoutingContext rc) {
        userRepository.getAll().subscribe(ok(rc));
    }

}
