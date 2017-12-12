package com.yarsi.yarsi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yarsi.yarsi.services.LoginServices;
import com.yarsi.yarsi.slider.SliderFragment;
import com.yarsi.yarsi.slider.SliderIndicator;
import com.yarsi.yarsi.slider.SliderPagerAdapter;
import com.yarsi.yarsi.slider.SliderView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private RelativeLayout menuJadwalDokter;
    LoginServices loginServices = new LoginServices();;
    ImageView ivLogout;
    CardView cvLogout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        sliderView = (SliderView) rootView.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.pagesContainer);
        cvLogout = rootView.findViewById(R.id.cvLogout);

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginServices.logoutProcess(v.getContext());

                Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                getActivity().finish();
            }
        });

        setupSlider();

        menuJadwalDokter = (RelativeLayout) rootView.findViewById(R.id.menuJadwalDokter);

        menuJadwalDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jadwalDokter = new Intent(getActivity(), DokterFormActivity.class);
                startActivity(jadwalDokter);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SliderFragment.newInstance("http://www.menucool.com/slider/prod/image-slider-1.jpg"));
        fragments.add(SliderFragment.newInstance("http://www.menucool.com/slider/prod/image-slider-2.jpg"));
        fragments.add(SliderFragment.newInstance("http://www.menucool.com/slider/prod/image-slider-3.jpg"));
        fragments.add(SliderFragment.newInstance("http://www.menucool.com/slider/prod/image-slider-4.jpg"));

        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

}
