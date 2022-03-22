package code.anish.sqlitecrudx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private ArrayList<Model> arrayList;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        Model model = arrayList.get(position);
        //get for view
        final String id = model.getId();
        final String image = model.getImage();
        final String name = model.getName();
        final String age = model.getAge();
        final String phone = model.getPhone();
        final String addTimeStamp = model.getAddTimeStamp();
        final String updateTimeStamp = model.getUpdateTimeStamp();

        //set views
        holder.profileIv.setImageURI(Uri.parse(image));
        holder.name.setText(name);
        holder.age.setText(age);
        holder.phone.setText(phone);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(
                            ""+position,
                            ""+id,
                            ""+name,
                            ""+age,
                            ""+phone,
                            ""+image,
                            ""+addTimeStamp,
                            ""+updateTimeStamp
                );
            }
        });

    }

    private void editDialog(String position,
                            String id,
                            String name,
                            String age,
                            String phone,
                            String image,
                            String addTimeStamp,
                            String updateTimeStamp) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update");
        builder.setMessage("Do you want to update?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.edit_icon);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, EditRecordActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("NAME", name);
                intent.putExtra("AGE", age);
                intent.putExtra("PHONE", phone);
                intent.putExtra("IMAGE", image);
                intent.putExtra("ADD_TIMESTAMP", addTimeStamp);
                intent.putExtra("UPDATE_TIMESTAMP", updateTimeStamp);
                intent.putExtra("editMode", true);

                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView profileIv;
        TextView name, age, phone;
        ImageButton editButton;

        public Holder(@NonNull View itemView){
            super(itemView);

            profileIv = itemView.findViewById(R.id.profileIv);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            phone = itemView.findViewById(R.id.phone_date);
            editButton= itemView.findViewById(R.id.editBtn);

        }
    }


}
