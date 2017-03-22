/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */

package tsuyoyo.prev.progress;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@ViewModelScope
@Component(modules = { MyModule.class })
interface MyComponent {

    void inject(MainActivity activity);

}
