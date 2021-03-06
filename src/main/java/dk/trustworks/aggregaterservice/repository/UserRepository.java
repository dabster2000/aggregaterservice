package dk.trustworks.aggregaterservice.repository;

import dk.trustworks.aggregaterservice.model.User;
import io.reactivex.Single;
import io.vertx.ext.sql.SQLOptions;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.sql.SQLConnection;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    private JDBCClient jdbc;

    public UserRepository(JDBCClient jdbc) {
        this.jdbc = jdbc;
    }

    private Single<SQLConnection> connect() {
        return jdbc.rxGetConnection()
                .map(c -> c.setOptions(new SQLOptions().setAutoGeneratedKeys(true)));
    }

    public Single<List<User>> getAll() {
        String sql = "select * from user;";

        return connect().flatMap((SQLConnection connection) -> connection.rxQuery(sql)
                .map(rs -> rs.getRows().stream().map(User::new).collect(Collectors.toList()))
                .doFinally(connection::close));
    }

}
