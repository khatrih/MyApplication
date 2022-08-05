package com.example.myapplication.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.myViewHolder> {

    private final ArrayList<CourseDataModel> dataModel;
    private final Context context;
    DatabaseHandler dbHandler;
    private static final String STUDENT_ID = "id";
    private static final String STUDENT_NAME = "NAME";
    private static final String STUDENT_EMAIL = "EMAIL";
    private static final String STUDENT_ADDRESS = "ADDRESS";
    private static final String STUDENT_CONTACT_NO = "CONTACT";
    private static final String STUDENT_COURSE_NAME = "COURSE_NAME";
    private static final String STUDENT_IMAGE = "IMAGE";

    public CourseAdapter(ArrayList<CourseDataModel> dataModel, Context context) {
        this.dataModel = dataModel;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list_sample, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        CourseDataModel model = dataModel.get(position);
        dbHandler = new DatabaseHandler(context);

        holder.studentName.setText(model.getsName().trim());
        holder.studentEmail.setText(model.getsEmail().trim());
        holder.studentAddress.setText(model.getSAddress().trim());
        holder.studentPhone.setText(model.getsPhoneNUmber().trim());
        holder.degreeStatus.setText(model.getsDegreeType());

        Bitmap bitImage = BitmapFactory.decodeByteArray(model.getsImage(), 0, model.getsImage().length);
        holder.ivStudentImage.setImageBitmap(bitImage);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, AddUpdateActivity.class);
            //pass model class
            i.putExtra("CourseModel", model);
            i.putExtra("isUpdated", true);
            context.startActivity(i);
        });

        holder.ivDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure if you want to delete this")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbHandler.deleteData(model.getsName());
                            dataModel.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        });

    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        private final TextView studentName;
        private final TextView studentEmail;
        private final TextView studentAddress;
        private final TextView studentPhone;
        private final TextView degreeStatus;
        private final ImageView ivStudentImage;
        private final ImageView ivDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.list_Student_name);
            studentEmail = itemView.findViewById(R.id.list_student_email);
            studentAddress = itemView.findViewById(R.id.list_student_address);
            studentPhone = itemView.findViewById(R.id.list_student_phone);
            degreeStatus = itemView.findViewById(R.id.degree_type);
            ivStudentImage = itemView.findViewById(R.id.img_studentImage);
            ivDelete = itemView.findViewById(R.id.img_delete);
        }
    }
}
