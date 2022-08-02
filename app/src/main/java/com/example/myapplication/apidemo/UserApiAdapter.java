package com.example.myapplication.apidemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.roomdb.AddRecordsActivity;
import com.example.myapplication.roomdb.StudentDao;
import com.example.myapplication.roomdb.StudentDataBase;
import com.example.myapplication.to_do_list.interfaces.NotesInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApiAdapter extends RecyclerView.Adapter<UserApiAdapter.viewHolder> {

    private List<RTFUserModel> rtfUserModelList;
    private final Context context;
    private static final String Accept = "application/json";
    private static final String Content_Type = "application/json";
    private static final String Authorization = "Bearer 2d29e9b750eb0c822aa99f5bce491a2c18017a025b1dc0a01d86c6ec4015bee7";

    public UserApiAdapter(List<RTFUserModel> rtfUserModelList, Context context) {
        this.rtfUserModelList = rtfUserModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_user_lists, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RTFUserModel rtfUserModel = rtfUserModelList.get(position);
        holder.tvUserName.setText(rtfUserModel.getName());
        holder.tvUserEmail.setText(rtfUserModel.getEmail());
        holder.tvUserGender.setText(rtfUserModel.getGender());
        holder.tvUserStatus.setText(rtfUserModel.getStatus());

        holder.ivMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.ivMenu);
            popupMenu.inflate(R.menu.option_menu_list);
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_update) {
                    Intent intent = new Intent(context, RTFAddUpdateActivity.class);
                    intent.putExtra("RTFUserModel", rtfUserModel);
                    intent.putExtra("isUpdated", true);
                    context.startActivity(intent);
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("if want to delete this record")
                            .setCancelable(false)
                            .setPositiveButton("yes", (dialog, which) -> {
                                UserApiInterface userApiInterface;
                                userApiInterface = RetrofitApiInstance.getApiRetrofit().create(UserApiInterface.class);
                                userApiInterface.deleteUser(Accept, Content_Type, Authorization, rtfUserModel.getId())
                                        .enqueue(new Callback<RTFUserModel>() {
                                            @Override
                                            public void onResponse(Call<RTFUserModel> call, Response<RTFUserModel> response) {
                                                rtfUserModelList.remove(holder.getAdapterPosition());
                                                notifyItemRemoved(holder.getAdapterPosition());
                                                dialog.dismiss();
                                                Log.d("TAG", "onResponse: delete " + response.code());
                                            }

                                            @Override
                                            public void onFailure(Call<RTFUserModel> call, Throwable t) {
                                                t.printStackTrace();
                                                dialog.dismiss();
                                                Log.d("TAG", "onFailure: delete" + t.getLocalizedMessage());
                                            }
                                        });
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
        return rtfUserModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        private TextView tvUserEmail;
        private TextView tvUserGender;
        private TextView tvUserStatus;
        private ImageView ivMenu;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_users_name);
            tvUserEmail = itemView.findViewById(R.id.tv_users_email);
            tvUserGender = itemView.findViewById(R.id.tv_users_gender);
            tvUserStatus = itemView.findViewById(R.id.tv_users_status);
            ivMenu = itemView.findViewById(R.id.popup_menu);
        }
    }
}
