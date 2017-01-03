package dly.gank2.dispatcher;


import dly.gank2.action.RxAction;

/**
 * This interface must be implemented by the store
 */
public interface RxActionDispatch {

  void onRxAction(RxAction action);
}
