package dly.gank2.dagger;

import javax.inject.Singleton;

import dagger.Component;
import dly.gank2.action.SubscriptionManager;
import dly.gank2.dagger.module.AppModule;
import dly.gank2.dispatcher.Dispatcher;

/**
 * Created by 19229 on 2016/12/20.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Dispatcher getDispatcher();

    SubscriptionManager getSubscriptManager();

}
