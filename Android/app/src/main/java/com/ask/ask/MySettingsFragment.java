package com.ask.ask;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MySettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MySettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Switch switchNotifications;
    private CheckBox checkBoxRequestUpdates;
    private CheckBox checkBoxOfferUpdates;
    private CheckBox checkBoxMessages;
    private Button buttonLogout;

    public MySettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MySettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MySettingsFragment newInstance(String param1, String param2) {
        MySettingsFragment fragment = new MySettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mysettings, container, false);
        switchNotifications = (Switch) rootView.findViewById(R.id.switchNotifications);
        checkBoxRequestUpdates = (CheckBox) rootView.findViewById(R.id.checkBoxRequestUpdates);
        checkBoxOfferUpdates = (CheckBox) rootView.findViewById(R.id.checkBoxOfferUpdates);
        checkBoxMessages = (CheckBox) rootView.findViewById(R.id.checkBoxMessages);
        buttonLogout = (Button) rootView.findViewById(R.id.buttonLogout);

        checkBoxRequestUpdates.setEnabled(false);
        checkBoxOfferUpdates.setEnabled(false);
        checkBoxMessages.setEnabled(false);

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxRequestUpdates.setEnabled(true);
                    checkBoxOfferUpdates.setEnabled(true);
                    checkBoxMessages.setEnabled(true);
                } else {
                    checkBoxRequestUpdates.setEnabled(false);
                    checkBoxOfferUpdates.setEnabled(false);
                    checkBoxMessages.setEnabled(false);
                }

            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: save all change and log out user
                Toast.makeText(getContext(), "Logging out.", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}