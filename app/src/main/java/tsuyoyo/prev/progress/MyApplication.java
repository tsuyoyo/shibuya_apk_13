/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */
package tsuyoyo.prev.progress;

import android.app.Application;

public class MyApplication extends Application {

    private MyComponent component;

    public MyComponent getMyComponent() {
        if (component == null) {
            component = DaggerMyComponent.builder().build();
        }
        return component;
    }

    public void releaseMyComponent() {
        component = null;
    }

}
