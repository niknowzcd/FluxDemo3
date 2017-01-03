package dly.gank2.store;


import dly.gank2.action.RxStoreChange;
import dly.gank2.dispatcher.Dispatcher;
import dly.gank2.dispatcher.RxActionDispatch;

/**
 * This class must be extended by each store of the app in order to recieve the actions dispatched
 * by the
 */
public abstract class RxStore implements RxActionDispatch {

  private final Dispatcher dispatcher;

  public RxStore(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  public void register() {
    dispatcher.subscribeRxStore(this);
  }

  public void unregister() {
    dispatcher.unsubscribeRxStore(this);
  }

  protected void postChange(RxStoreChange change) {
    dispatcher.postRxStoreChange(change);
  }
}
