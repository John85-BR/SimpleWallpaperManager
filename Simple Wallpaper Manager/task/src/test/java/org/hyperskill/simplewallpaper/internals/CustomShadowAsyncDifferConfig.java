package org.hyperskill.simplewallpaper.internals;
import static org.robolectric.Shadows.shadowOf;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.AsyncDifferConfig;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowLooper;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

// Version 1.1.2
@Implements(AsyncDifferConfig.class)
public class CustomShadowAsyncDifferConfig {

    public static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final ShadowLooper shadowLooper = shadowOf(Looper.getMainLooper());

        @Override
        public void execute(Runnable r) {
            handler.post(r);
            shadowLooper.idleFor(500, TimeUnit.MILLISECONDS);
        }
    }
    Executor mainExecutor;

    @Implementation
    public Executor getBackgroundThreadExecutor() {
        if(mainExecutor == null) {
            mainExecutor = new MainThreadExecutor();
        }
        return mainExecutor;
    }
}

