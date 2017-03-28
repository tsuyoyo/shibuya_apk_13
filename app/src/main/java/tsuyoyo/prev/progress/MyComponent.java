/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */

package tsuyoyo.prev.progress;

import dagger.Component;

@ViewModelScope
@Component(modules = { MyModule.class })
interface MyComponent {

    void inject(MainActivity activity);

}
