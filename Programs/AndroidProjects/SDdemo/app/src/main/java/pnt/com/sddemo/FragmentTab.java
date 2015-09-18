package pnt.com.sddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pnt on 8/27/15.
 */
public class FragmentTab extends Fragment {
    private FragmentTabHost mTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabhost,null);
        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Tab 1", null),
                FragmentLogin.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Tab 2", null),
                FragmentLogin.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Tab 3", null),
                FragmentLogin.class, null);

        return view;
    }
}
