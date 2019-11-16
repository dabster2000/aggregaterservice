package dk.trustworks.aggregater;

import dk.trustworks.aggregater.network.queries.WorkItemsQueryController;
import dk.trustworks.aggregater.repository.WorkItemsRepository;
import io.jaegertracing.Configuration;
import io.opentracing.util.GlobalTracer;
import io.reactivex.Completable;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

import java.util.Locale;
import java.util.TimeZone;

public class Server extends AbstractVerticle {

    private WorkItemsQueryController workItemsQueryController;

    public Server() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Future<Void> fut) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Locale.setDefault(new Locale("da", "DK"));

        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration("aggregaterservice").withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(configuration.getTracer());

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.rxGetConfig()
                .doOnSuccess(config -> {
                    workItemsQueryController = new WorkItemsQueryController(new WorkItemsRepository(JDBCClient.createShared(vertx, config, "aggregaterservice")));

                    router.get("/workitems").handler(workItemsQueryController::getAll);

                })
                .flatMapCompletable(config -> createHttpServer(config, router))
                .subscribe(CompletableHelper.toObserver(fut));
    }

    private Completable createHttpServer(JsonObject config, Router router) {
        return vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .rxListen(config.getInteger("HTTP_PORT", 5660))
                .ignoreElement();
    }
}