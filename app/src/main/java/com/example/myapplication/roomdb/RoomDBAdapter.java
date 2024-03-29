package com.example.myapplication.roomdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class RoomDBAdapter extends RecyclerView.Adapter<RoomDBAdapter.viewHolder> {
    List<StudentsModel> studentsModels;
    Context context;

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
        holder.tvGender.setText(model.getsGender());

        holder.ivOptionMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.ivOptionMenu);
            popupMenu.inflate(R.menu.option_menu_list);
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_update) {
                    Intent intent = new Intent(context, AddRecordsActivity.class);
                    intent.putExtra("studentModel", model);
                    intent.putExtra("isUpdate", true);
                    context.startActivity(intent);
                } else if (itemId == R.id.menu_delete) {
                    new AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("if want to delete this record")
                            .setCancelable(false)
                            .setPositiveButton("yes", (dialog, which) -> {
                                StudentDataBase dataBase = StudentDataBase.getInstance(context);
                                StudentDao studentDao = dataBase.getStudentDao();
                                studentDao.deletedStudentData(model.getsId());
                                studentsModels.remove(position);
                                notifyItemRemoved(position);
                            })
                            .setNegativeButton("no", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .create()
                            .show();
                }
                return false;
            });
            popupMenu.show();
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
        private TextView tvGender;
        private ImageView ivOptionMenu;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_name);
            tvStudentEmail = itemView.findViewById(R.id.tv_email);
            tvStudentAddress = itemView.findViewById(R.id.tv_address);
            tvStudentPhoneNo = itemView.findViewById(R.id.tv_phone_no);
            tvStudentQualified = itemView.findViewById(R.id.tv_qualified);
            tvGender = itemView.findViewById(R.id.tv_gender);
            ivOptionMenu = itemView.findViewById(R.id.option_menu);
        }
    }
}
