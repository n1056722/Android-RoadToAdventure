package ya.haojun.roadtoadventure.helper;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.animation.FlipAnimation;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by asus on 2017/4/10.
 */

public class PicassoViewAnimator<T extends Resourceble> extends ViewAnimator {

    private final int ANIMATION_DURATION = 175;
    public static final int CIRCULAR_REVEAL_ANIMATION_DURATION = 500;

    private AppCompatActivity appCompatActivity;
    private List<T> list;

    private List<View> viewList = new ArrayList<>();
    private ScreenShotable screenShotable;
    private DrawerLayout drawerLayout;
    private ViewAnimator.ViewAnimatorListener animatorListener;

    public PicassoViewAnimator(AppCompatActivity activity,
                               List<T> items,
                               ScreenShotable screenShotable,
                               final DrawerLayout drawerLayout,
                               ViewAnimator.ViewAnimatorListener animatorListener) {
        super(activity, items, screenShotable, drawerLayout, animatorListener);
        this.appCompatActivity = activity;
        this.list = items;
        this.screenShotable = screenShotable;
        this.drawerLayout = drawerLayout;
        this.animatorListener = animatorListener;
    }

    public void showMenuContent() {
        setViewsClickable(false);
        viewList.clear();
        double size = list.size();
        for (int i = 0; i < size; i++) {
            View viewMenu = appCompatActivity.getLayoutInflater().inflate(yalantis.com.sidemenu.R.layout.menu_list_item, null);
            final int finalI = i;
            viewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] location = {0, 0};
                    v.getLocationOnScreen(location);
                    switchItem(list.get(finalI), location[1] + v.getHeight() / 2);
                }
            });
            int w = (int) (appCompatActivity.getResources().getDisplayMetrics().density * 24);
            Picasso.with(appCompatActivity).load(list.get(i).getImageRes()).resize(w, w).centerCrop().into(((ImageView) viewMenu.findViewById(yalantis.com.sidemenu.R.id.menu_item_image)));
            viewMenu.setVisibility(View.GONE);
            viewMenu.setEnabled(false);
            viewList.add(viewMenu);
            animatorListener.addViewToContainer(viewMenu);
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateView((int) position);
                    }
                    if (position == viewList.size() - 1) {
                        screenShotable.takeScreenShot();
                        setViewsClickable(true);
                    }
                }
            }, (long) delay);
        }

    }

    private void hideMenuContent() {
        setViewsClickable(false);
        double size = list.size();
        for (int i = list.size(); i >= 0; i--) {
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateHideView((int) position);
                    }
                }
            }, (long) delay);
        }

    }

    private void setViewsClickable(boolean clickable) {
        animatorListener.disableHomeButton();
        for (View view : viewList) {
            view.setEnabled(clickable);
        }
    }

    private void animateView(int position) {
        final View view = viewList.get(position);
        view.setVisibility(View.VISIBLE);
        FlipAnimation rotation =
                new FlipAnimation(90, 0, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }

    private void animateHideView(final int position) {
        final View view = viewList.get(position);
        FlipAnimation rotation =
                new FlipAnimation(0, 90, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);
                if (position == viewList.size() - 1) {
                    animatorListener.enableHomeButton();
                    drawerLayout.closeDrawers();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }

    private void switchItem(Resourceble slideMenuItem, int topPosition) {
        this.screenShotable = animatorListener.onSwitch(slideMenuItem, screenShotable, topPosition);
        hideMenuContent();
    }

    public interface ViewAnimatorListener {

        public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position);

        public void disableHomeButton();

        public void enableHomeButton();

        public void addViewToContainer(View view);

    }
}
