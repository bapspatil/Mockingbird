package bapspatil.mockingbird;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("mockingbird.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
