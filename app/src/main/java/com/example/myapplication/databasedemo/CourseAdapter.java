package com.example.myapplication.databasedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myapplication.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.myViewHolder>{

    private ArrayList<CourseDataModel> dataModel;
    private Context context;

    public CourseAdapter(ArrayList<CourseDataModel> dataModel, Context context) {
        this.dataModel = dataModel;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list_sample,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        CourseDataModel model = dataModel.get(position);

        holder.studentName.setText(model.getsName().trim());
        holder.studentEmail.setText(model.getsEmail().trim());
        holder.studentAddress.setText(model.getSAddress().trim());
        holder.studentPhone.setText(model.getsPhoneNUmber().trim());
        holder.degreeStatus.setText(model.getsDegreeType().trim());
    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        private TextView studentName;
        private TextView studentEmail;
        private TextView studentAddress;
        private TextView studentPhone;
        private TextView degreeStatus;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.list_Student_name);
            studentEmail = itemView.findViewById(R.id.list_student_email);
            studentAddress = itemView.findViewById(R.id.list_student_address);
            studentPhone = itemView.findViewById(R.id.list_student_phone);
            degreeStatus = itemView.findViewById(R.id.degree_type);
        }
    }
}
