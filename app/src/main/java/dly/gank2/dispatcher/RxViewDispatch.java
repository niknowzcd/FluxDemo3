package dly.gank2.dispatcher;


import android.support.annotation.NonNull;

import dly.gank2.action.RxStoreChange;


/**
 * Created by marcel on 10/09/15.
 *
 * Activities or Fragments implementing this interface will be part of the RxFlux flow. Implement the methods in
 * order to get the proper callbacks and un/register stores accordingly to Flux flow.
 */
public interface RxViewDispatch {

  /**
   * All the stores will call this event after they process an action and the store change it.
   * The view can react and request the needed data
   */
  void onRxStoreChanged(@NonNull RxStoreChange change);


}
