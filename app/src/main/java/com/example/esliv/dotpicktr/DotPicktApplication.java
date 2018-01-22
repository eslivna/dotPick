package com.example.esliv.dotpicktr;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by esliv on 22/01/2018.
 */

public class DotPicktApplication extends Application {
    //Leak Canary refWatcher
    private RefWatcher refWatcher;

    /**
     * @param context context
     * @return LeakCanary RefWatcher instance to watch on
     */
    public static RefWatcher getRefWatcher(Context context) {
        DotPicktApplication application = (DotPicktApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        refWatcher = LeakCanary.install(this);
    }
}
