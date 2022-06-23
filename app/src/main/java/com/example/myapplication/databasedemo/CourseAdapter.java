package com.example.myapplication.databasedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.myViewHolder> {

    private ArrayList<CourseDataModel> dataModel;
    private Context context;
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

        holder.studentName.setText(model.getsName().trim());
        holder.studentEmail.setText(model.getsEmail().trim());
        holder.studentAddress.setText(model.getSAddress().trim());
        holder.studentPhone.setText(model.getsPhoneNUmber().trim());
        holder.degreeStatus.setText(model.getsDegreeType());

        Bitmap bitImage = BitmapFactory.decodeByteArray(model.getsImage(), 0, model.getsImage().length);
        holder.ivStudentImage.setImageBitmap(bitImage);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DataBaseMainActivity.class);
            i.putExtra(STUDENT_NAME, model.getsName());
            i.putExtra(STUDENT_EMAIL, model.getsEmail());
            i.putExtra(STUDENT_ADDRESS, model.getSAddress());
            i.putExtra(STUDENT_CONTACT_NO, model.getsPhoneNUmber());
            i.putExtra(STUDENT_COURSE_NAME, model.getsDegreeType());
            i.putExtra(STUDENT_IMAGE, model.getsImage());
            context.startActivity(i);
        });


       /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitImage, "Title", null);
        Uri.parse(path);
        Picasso.get().load().placeholder(R.drawable.ic_round_image).into(holder.ivStudentImage);*/

    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView studentName;
        private TextView studentEmail;
        private TextView studentAddress;
        private TextView studentPhone;
        private TextView degreeStatus;
        private ImageView ivStudentImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.list_Student_name);
            studentEmail = itemView.findViewById(R.id.list_student_email);
            studentAddress = itemView.findViewById(R.id.list_student_address);
            studentPhone = itemView.findViewById(R.id.list_student_phone);
            degreeStatus = itemView.findViewById(R.id.degree_type);
            ivStudentImage = itemView.findViewById(R.id.img_studentImage);
        }
    }
}
