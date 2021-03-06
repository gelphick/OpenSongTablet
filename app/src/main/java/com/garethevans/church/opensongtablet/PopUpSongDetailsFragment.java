package com.garethevans.church.opensongtablet;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopUpSongDetailsFragment extends DialogFragment {

    static PopUpSongDetailsFragment newInstance() {
        PopUpSongDetailsFragment frag;
        frag = new PopUpSongDetailsFragment();
        return frag;
    }

    public interface MyInterface {
        void doEdit();
    }

    private MyInterface mListener;

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null && getDialog() != null) {
            PopUpSizeAndAlpha.decoratePopUp(getActivity(),getDialog());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            this.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View V = inflater.inflate(R.layout.popup_song_details, container, false);

        TextView title = V.findViewById(R.id.dialogtitle);
        title.setText(FullscreenActivity.songfilename);
        final FloatingActionButton closeMe = V.findViewById(R.id.closeMe);
        closeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAnimations.animateFAB(closeMe,getActivity());
                closeMe.setEnabled(false);
                dismiss();
            }
        });
        FloatingActionButton saveMe = V.findViewById(R.id.saveMe);
        saveMe.setVisibility(View.GONE);
        Button editSongDetails = V.findViewById(R.id.editSongDetails);
        editSongDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doEdit();
                dismiss();
            }
        });
        TextView t_mAuthor = V.findViewById(R.id.t_mAuthor);
        TextView t_mKey = V.findViewById(R.id.t_mKey);
        TextView t_mCopyright = V.findViewById(R.id.t_mCopyright);
        TextView t_mPresentation = V.findViewById(R.id.t_mPresentation);
        TextView t_mHymnNumber = V.findViewById(R.id.t_mHymnNumber);
        TextView t_mCCLI = V.findViewById(R.id.t_mCCLI);
        TextView t_mNotes = V.findViewById(R.id.t_mNotes);
        TextView v_mTitle = V.findViewById(R.id.v_mTitle);
        TextView v_mAuthor = V.findViewById(R.id.v_mAuthor);
        TextView v_mKey = V.findViewById(R.id.v_mKey);
        TextView v_mCopyright = V.findViewById(R.id.v_mCopyright);
        TextView v_mPresentation = V.findViewById(R.id.v_mPresentation);
        TextView v_mHymnNumber = V.findViewById(R.id.v_mHymnNumber);
        TextView v_mCCLI = V.findViewById(R.id.v_mCCLI);
        TextView v_mNotes = V.findViewById(R.id.v_mNotes);
        TextView v_mLyrics = V.findViewById(R.id.v_mLyrics);

        String k = ProcessSong.getSongKey();
        // Fix the key text
        k = k.replace("(","");
        k = k.replace(")","");

        // Get the capo key if it exitst
        String ck = ProcessSong.getCapoInfo();
        if (!ck.equals("")) {
            ck = " (" + getActivity().getString(R.string.edit_song_capo) + " " + ck + ")";
            k += ck;
        }

        // Decide what should or should be shown
        v_mTitle.setText(FullscreenActivity.mTitle);
        setContentInfo(t_mAuthor,v_mAuthor, FullscreenActivity.mAuthor.toString());
        setContentInfo(t_mKey,v_mKey, k);
        setContentInfo(t_mCopyright,v_mCopyright, FullscreenActivity.mCopyright.toString());
        setContentInfo(t_mCCLI,v_mCCLI, FullscreenActivity.mCCLI);
        setContentInfo(t_mPresentation,v_mPresentation, FullscreenActivity.mPresentation);
        setContentInfo(t_mHymnNumber,v_mHymnNumber, FullscreenActivity.mHymnNumber);
        setContentInfo(t_mNotes,v_mNotes, FullscreenActivity.mNotes);

        v_mLyrics.setTypeface(FullscreenActivity.typeface1);
        v_mLyrics.setTextSize(8.0f);
        v_mLyrics.setText(FullscreenActivity.mLyrics);

        PopUpSizeAndAlpha.decoratePopUp(getActivity(),getDialog());
        return V;
    }

    public void setContentInfo(TextView tv_t, TextView tv_v, String s) {
        if (s!=null && !s.equals("")) {
            tv_t.setVisibility(View.VISIBLE);
            tv_v.setVisibility(View.VISIBLE);
            tv_v.setText(s);
        } else {
            tv_t.setVisibility(View.GONE);
            tv_v.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCancel(DialogInterface dialog) {
        this.dismiss();
    }

}