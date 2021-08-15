import database.SQLite;
import geo2.Geo2Request;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reader.CSVReader;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) throws SQLException {
//        if(args.length < 2) {
//            throw new IllegalArgumentException("Where parameters?");
//        }
//        int nThreads = Integer.parseInt(args[0]);
//        if(nThreads < 1 || nThreads > 8) {
//            throw new IllegalArgumentException("The number of threads must not be less than 1 and greater than 8");
//        }

        Geo2Request request = new Geo2Request();
        SQLite sqLite = new SQLite("jdbc:sqlite:adresy.db", 8);
        sqLite.createAddressTable();
        sqLite.createLocationTable();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);

        Observable.fromIterable(CSVReader.read("import.csv"))
                .flatMap(location ->
                        Observable.just(location)
                                .map(request::getLocation)
                                .doOnNext(sqLite::insertLocation)
                                .subscribeOn(Schedulers.from(executor))
                ).subscribe();

        executor.shutdown();
    }
}
