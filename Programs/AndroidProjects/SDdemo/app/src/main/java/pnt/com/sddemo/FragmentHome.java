package pnt.com.sddemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

/**
 * Created by pnt on 8/24/15.
 */
public class FragmentHome extends Fragment {
    private DrawerLayout drawerLayout;
    private LinearLayout left_drawer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer);
        left_drawer = (LinearLayout) view.findViewById(R.id.left_drawer);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("MyApp");
        toolbar.setNavigationIcon(R.drawable.ic_drawer2);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.openDrawer(Gravity.START);
                } else {
                    drawerLayout.closeDrawer(Gravity.START);
                }

            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toolbar.setNavigationIcon(R.drawable.ic_action_back);
                toolbar.setTitle("Menu");

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toolbar.setNavigationIcon(R.drawable.ic_drawer2);
                toolbar.setTitle("MyApp");
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        getChildFragmentManager().beginTransaction().add(R.id.drawer_container,new FragmentTab()).commit();
        return view;
    }
}
