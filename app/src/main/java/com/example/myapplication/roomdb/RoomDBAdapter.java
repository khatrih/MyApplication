package com.example.myapplication.roomdb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication.R;

import java.util.List;

public class RoomDBAdapter extends RecyclerView.Adapter<RoomDBAdapter.viewHolder> {
    List<StudentsModel> studentsModels;
    Context context;
    private static final String STUDENT_ID = "id";
    private static final String STUDENT_NAME = "name";
    private static final String STUDENT_EMAIL = "email";
    private static final String STUDENT_ADDRESS = "address";
    private static final String STUDENT_PHONE_NO = "mobile_no";
    private static final String STUDENT_QUALIFICATION = "qualification";

    public RoomDBAdapter(List<StudentsModel> studentsModels, Context context) {
        this.studentsModels = studentsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list_room_db, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StudentsModel model = studentsModels.get(position);

        holder.tvStudentName.setText(model.getsName());
        holder.tvStudentEmail.setText(model.getsEmail());
        holder.tvStudentAddress.setText(model.getsAddress());
        holder.tvStudentPhoneNo.setText(model.getsMobileNo());
        holder.tvStudentQualified.setText(model.getsQualification());

        holder.ivBtnDeleteRowData.setOnClickListener(v -> {
            StudentDataBase dataBase = Room.databaseBuilder(context, StudentDataBase.class,
                    "student_database").allowMainThreadQueries().build();

            StudentDao studentDao = dataBase.getStudentDao();
            studentDao.deletedStudentData(model.getsId());
            studentsModels.remove(position);
            notifyDataSetChanged();

        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateStudentRDBActivity.class);
            intent.putExtra(STUDENT_ID, String.valueOf(model.getsId()));
            intent.putExtra(STUDENT_NAME, model.getsName());
            intent.putExtra(STUDENT_EMAIL, model.getsEmail());
            intent.putExtra(STUDENT_ADDRESS, model.getsAddress());
            intent.putExtra(STUDENT_PHONE_NO, model.getsMobileNo());
            intent.putExtra(STUDENT_QUALIFICATION, model.getsQualification());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return studentsModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvStudentName;
        private TextView tvStudentEmail;
        private TextView tvStudentAddress;
        private TextView tvStudentPhoneNo;
        private TextView tvStudentQualified;
        private AppCompatImageButton ivBtnDeleteRowData;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_name);
            tvStudentEmail = itemView.findViewById(R.id.tv_email);
            tvStudentAddress = itemView.findViewById(R.id.tv_address);
            tvStudentPhoneNo = itemView.findViewById(R.id.tv_phone_no);
            tvStudentQualified = itemView.findViewById(R.id.tv_qualified);
            ivBtnDeleteRowData = itemView.findViewById(R.id.iv_btn_delete);
        }
    }
}
