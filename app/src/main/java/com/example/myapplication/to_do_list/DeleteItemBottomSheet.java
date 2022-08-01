package com.example.myapplication.to_do_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteItemBottomSheet extends BottomSheetDialogFragment {
    private static final String TAG = "TAG";
    static OnRemoveListener onRemoveListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_item_bottom_sheet_view, container, false);

        Button btnDelete = rootView.findViewById(R.id.btn_delete);
        Button btnCancel = rootView.findViewById(R.id.btn_cancel);
        TextView txt_delete_item = rootView.findViewById(R.id.txt_delete_item);

        Bundle getId = getArguments();
        String Id = getId.getString("ID");
        String title = getId.getString("title");
        txt_delete_item.setText("Are sure you want delete " + title + " ?");
        int position = getId.getInt("position");

        btnDelete.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Notes");
            reference.child(Id)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            snapshot.getRef().removeValue();
                            Log.d(TAG, "onDataChange: delete " + snapshot.getKey());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: " + error.getMessage());
                        }
                    });
            onRemoveListener.onRemove(position);
            dismiss();
        });
        btnCancel.setOnClickListener(v -> dismiss());

        return rootView;
    }

    public static void setRemoveListener(OnRemoveListener listener) {
        onRemoveListener = listener;
    }

    public interface OnRemoveListener {
        void onRemove(int position);
    }
}
