package dly.gank2.action;


import android.support.annotation.NonNull;

import dly.gank2.dispatcher.Dispatcher;
import rx.Subscription;

/**
 * This class must be extended in order to give useful functionality to create RxAction.
 */
public abstract class RxActionCreator {

  private final Dispatcher dispatcher;
  private final SubscriptionManager manager;

  public RxActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
    this.dispatcher = dispatcher;
    this.manager = manager;
  }

  public void addRxAction(RxAction rxAction, Subscription subscription) {
    manager.add(rxAction, subscription);
  }

  public boolean hasRxAction(RxAction rxAction) {
    return manager.contains(rxAction);
  }

  public void removeRxAction(RxAction rxAction) {
    manager.remove(rxAction);
  }

  public RxAction newRxAction(@NonNull String actionId, @NonNull Object... data) {
    if (actionId.isEmpty()) {
      throw new IllegalArgumentException("Type must not be empty");
    }

    if (data.length % 2 != 0) {
      throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
    }

    RxAction.Builder actionBuilder = RxAction.type(actionId);
    return actionBuilder.build();
  }

  public void postRxAction(@NonNull RxAction action) {
    dispatcher.postRxAction(action);
  }


}
