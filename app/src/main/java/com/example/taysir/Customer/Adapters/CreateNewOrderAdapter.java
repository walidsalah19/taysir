package com.example.taysir.Customer.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class CreateNewOrderAdapter extends RecyclerView.Adapter<CreateNewOrderAdapter.help> {
    public  static ArrayList<OrderDetailsModel> orderDetails=new ArrayList<>();
    private ArrayList<Integer> itemCount;
    public CreateNewOrderAdapter(ArrayList<Integer> itemCount) {
        this.itemCount=itemCount;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.create_new_order_layout,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        OrderDetailsModel model=new OrderDetailsModel(" "," "," "," ",0,0,0,0.0f);
        orderDetails.add(model);
        addLink(holder,position);

    }

    @Override
    public int getItemCount() {
        return itemCount.size();
    }
    private void addLink(@NonNull help holder,int position)
    {
        holder.link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 orderDetails.get(position).setProductLink(holder.link.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public class help extends RecyclerView.ViewHolder
    {
        TextView number;
        EditText link,quantity,productNumber,color,size,notes,productCost;
        ImageView addImage;
        public help(@NonNull View itemView) {
            super(itemView);
            number=(TextView) itemView.findViewById(R.id.number);
            link=(EditText) itemView.findViewById(R.id.edittextProductLink);
            quantity=(EditText) itemView.findViewById(R.id.edittextProduceQuantity);
            productNumber=(EditText) itemView.findViewById(R.id.edittextProduceNumber);
            color=(EditText) itemView.findViewById(R.id.edittextProduceColor);
            size=(EditText) itemView.findViewById(R.id.edittextProduceMeasure);
            productCost=(EditText) itemView.findViewById(R.id.edittextProductCost);
            notes=(EditText) itemView.findViewById(R.id.edittextًNotes);
            addImage=(ImageView) itemView.findViewById(R.id.addImage);



        }
    }
}
