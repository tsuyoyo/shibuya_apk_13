/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */
package tsuyoyo.prev.progress;

import java.util.concurrent.Callable;

import rx.Single;

class MyFakeApi {

    Single<Boolean> call() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Thread.sleep(5000);
                return true;
            }
        });
    }

}
