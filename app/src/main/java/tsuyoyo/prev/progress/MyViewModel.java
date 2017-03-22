/**
 * Copyright (c) 2017 Tsuyoyo. All Rights Reserved.
 */
package tsuyoyo.prev.progress;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

class MyViewModel {

    interface Input {
        void kickApi();
    }

    interface Output {
        Observable<Boolean> isLoading();
        Observable<String> message();
    }

    private MyFakeApi api;
    private BehaviorSubject<Boolean> isLoading = BehaviorSubject.create(false);
    private PublishSubject<String> message = PublishSubject.create();

    private Input input = new Input() {
        @Override
        public void kickApi() {
            api.call().subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            isLoading.onNext(true);
                        }
                    })
                    .doOnUnsubscribe(new Action0() {
                        @Override
                        public void call() {
                            isLoading.onNext(false);
                        }
                    })
                    .subscribe(new Action1<Boolean>() {
                                   @Override
                                   public void call(Boolean aBoolean) {
                                       message.onNext("Done!!");
                                   }
                               },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    message.onNext("Error in API : " + throwable.getMessage());
                                }
                            });
        }
    };

    private Output output = new Output() {
        @Override
        public Observable<Boolean> isLoading() {
            return isLoading.asObservable();
        }

        @Override
        public Observable<String> message() {
            return message.asObservable();
        }
    };

    public MyViewModel(MyFakeApi api) {
        this.api = api;
    }

    public Input inputs() {
        return input;
    }

    public Output outputs() {
        return output;
    }

}
