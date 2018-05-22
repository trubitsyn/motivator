package io.github.trubitsyn.motivator;

import com.facebook.stetho.Stetho;

public class DebugMotivator extends Motivator {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
