package com.yarsi.yarsi.slider;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yarsi.yarsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {


    private static final String ARG_PARAM1 = "params";

    private String imageUrls;

    public SliderFragment() {
    }

    public static SliderFragment newInstance(String params) {
        SliderFragment fragment = new SliderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageUrls = getArguments().getString(ARG_PARAM1);
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        Glide.with(getActivity())
                .load(imageUrls)
                .placeholder(R.drawable.image_slider_1)
                .into(img);
        return view;
    }

}
