package dly.gank2.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import dly.gank2.MyApplication;
import dly.gank2.R;
import dly.gank2.action.RxStoreChange;
import dly.gank2.action.TodayGankActionCreator;
import dly.gank2.dagger.DaggerTodayGankFragmentComponent;
import dly.gank2.dagger.TodayGankFragmentComponent;
import dly.gank2.data.ui.GankNormalItem;
import dly.gank2.data.ui.GankTopImageItem;
import dly.gank2.dispatcher.Dispatcher;
import dly.gank2.dispatcher.RxViewDispatch;
import dly.gank2.store.TodayGankStore;
import dly.gank2.ui.adapter.GankListAdapter;

/**
 * Created by dly on 2016/12/12.
 */
public class TodayGankFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, GankListAdapter.OnItemClickListener, RxViewDispatch {

    public static final String TAG = TodayGankFragment.class.getSimpleName();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout vRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView vWelfareRecycler;

    private GankListAdapter mAdapter;


    @Inject TodayGankStore mStore;
    @Inject TodayGankActionCreator mActionCreator;
    @Inject Dispatcher mDispatcher;

    public static TodayGankFragment newInstance() {
        return new TodayGankFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_today, container, false);
        ButterKnife.bind(this, rootView);

        vRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        vRefreshLayout.setOnRefreshListener(this);
        vWelfareRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        vWelfareRecycler.setHasFixedSize(true);
        mAdapter = new GankListAdapter(this);
        mAdapter.setOnItemClickListener(this);
        vWelfareRecycler.setAdapter(mAdapter);

        initInjector();

        return rootView;
    }


    private void initInjector() {
        TodayGankFragmentComponent mComponent= DaggerTodayGankFragmentComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .build();
        mComponent.inject(this);
    }


    private TodayGankActionCreator creator;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        RxFlux rxFlux = RxFlux.init(MyApplication.getApplication());
//        Dispatcher dispatcher = rxFlux.getDispatcher();
//        SubscriptionManager manager = rxFlux.getSubscriptionManager();
//
//        store = new TodayGankStore(dispatcher);
//
//        dispatcher.subscribeRxStore(store);
//        dispatcher.subscribeRxView(this);
//
//        creator = new TodayGankActionCreator(dispatcher, manager);
//        //view从对应的Creator请求数据
//        creator.getTodayGank();
        mDispatcher.subscribeRxStore(mStore);
        mDispatcher.subscribeRxView(this);
        mActionCreator.getTodayGank();
    }

    private void refreshData() {
        //creator.getTodayGank();
        mActionCreator.getTodayGank();

    }


    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case TodayGankStore.ID:
                vRefreshLayout.setRefreshing(false);
                mAdapter.refreshData(mStore.getItems());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void onClickNormalItem(View view, GankNormalItem normalItem) {

    }

    @Override
    public void onClickGirlItem(View view, GankTopImageItem girlItem) {

    }
}
