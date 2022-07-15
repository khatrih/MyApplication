package com.example.myapplication.newcontentprovider;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomFragment extends BottomSheetDialogFragment {

    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bottomSheetBehavior;
    View rootView;
    private TextView tvContactNumber;
    private TextView tvContactEmail;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_sheet_view, container, false);

        tvContactNumber = rootView.findViewById(R.id.tv_contact_number);
        tvContactEmail = rootView.findViewById(R.id.tv_email);

        Bundle getAdapterData = getArguments();
        String contactNumber = getAdapterData.getString("phoneNumber");
        String contactEmail = getAdapterData.getString("email");

        tvContactNumber.setText(contactNumber);
        tvContactEmail.setText(contactEmail);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());

//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        CoordinatorLayout layout = dialog.findViewById(R.id.coordinate_layout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

    }
}
