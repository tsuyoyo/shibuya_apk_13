/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */

package tsuyoyo.prev.progress;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class MyModule {

    @Provides
    MyFakeApi provideMyFakeApi() {
        return new MyFakeApi();
    }

    @Singleton
    @Provides
    MyViewModel provideMyViewModel(MyFakeApi api) {
        return new MyViewModel(api);
    }

}
