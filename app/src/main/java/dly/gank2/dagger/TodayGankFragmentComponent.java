package dly.gank2.dagger;

import dagger.Component;
import dly.gank2.ui.fragment.TodayGankFragment;

/**
 * Created by 19229 on 2016/12/27.
 */
@PerActivity
@Component(dependencies = {AppComponent.class})
public interface TodayGankFragmentComponent {

    void inject(TodayGankFragment todayGankFragment);
}
