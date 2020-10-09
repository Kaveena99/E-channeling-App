package com.example.talk_with_doctor;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClicklistener.onItemlongClick(view, getAdapterPosition());
                return false;
            }
        });
    }

    public void setData(Context context, String ID, String name, String password, int mobile, String email, String category, String hospital, String dateTime){
       TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("ID: " + ID + "\n" + "Name: " + name + "\n" + "Password: " + password + "\n" + "Mobile: " + mobile + "\n" +
                "Email: " + email + "\n" + "Category: " + category + "\n" + "Hospital: " + hospital + "\n" + "Date and Time: " + dateTime);

    }

    public void setPharmacyData(Context context, String ID, String name, String password, int mobile, String email, String address, String city){
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("ID: " + ID + "\n" + "Name: " + name + "\n" + "Password: " + password + "\n" + "Mobile: " + mobile + "\n" +
                "Email: " + email + "\n" + "Address: " + address + "\n" + "City: " + city);

    }

    public void setPatientData(Context context, String name, String email, String username, int mobile, String password){
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("Name: " + name + "\n" + "Email: " + email + "\n" + "Username: " + username + "\n" +
                "Mobile: " + mobile + "\n" + "Password: " + password);

    }

    private ViewHolder.Clicklistener mClicklistener;

    public void setIncomeData(Context context, Integer month, Integer sales, Integer income, Integer expences, Integer profit) {
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("Month : " + month + "\n" + "Sales : " + sales + "\n" + "Income : " + income + "\n" +
                "Expences : " + expences + "\n" + "Profit : " + profit);

    }

    public void setAppoinmnetsData(Context context, String Id, String name, String dateTime, int phone){
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("ID: "+ Id + "\n"+ "Name: "+name+ "\n"+ "Date & Time: "+ dateTime + "\n" + "phone: "+phone );
    }
    public void setMyAppoinmnetsData(Context context, String Id, String username, String dateTime, String hospital){
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("ID: "+ Id + "\n"+ "Name: "+username+ "\n"+ "Date & Time: "+ dateTime + "\n" + "hospital: "+hospital );
    }

    public void setOrderData(Context context, String PharmacyName, String CustomerMobile, String mImageUrl) {
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText("Pharmacy Name : " + PharmacyName + "\n" + "Customer Mobile : " + CustomerMobile + "\n" + "Image URL : " + mImageUrl);

        //imageView=(ImageView)itemView.findViewById(R.id.imageView);
    }

    //to retrieve accepted appointments details
    public void setBookings(Context context, String name, String dateTime, String hospital){
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText( "Doctor Name: " + name + "\n" + "Date & Time: " + dateTime + "\n" + "Hospital: " + hospital );

    }

    //to notification page
    public void setNotification(Context context, String name){
        TextView textView = itemView.findViewById(R.id.textview_row);

        textView.setText( "Doctor " + name +" has confirmed your appointment request."  );

    }

    public interface Clicklistener{
        void onItemlongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.Clicklistener clickListener){
        mClicklistener = clickListener;
    }


}
