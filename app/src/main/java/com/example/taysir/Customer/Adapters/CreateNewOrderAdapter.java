package com.example.taysir.Customer.Adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class CreateNewOrderAdapter extends RecyclerView.Adapter<CreateNewOrderAdapter.help> {
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemSelected(int position);
        void onItemDelete(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public  static ArrayList<OrderDetailsModel> orderDetails=new ArrayList<>();
    private ArrayList<String> itemCount;
    private int arraySize=0;
    public CreateNewOrderAdapter(ArrayList<String> itemCount) {
        this.itemCount=itemCount;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.create_new_order_layout,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        if (arraySize==0 ||arraySize <= itemCount.size()) {
            OrderDetailsModel model = new OrderDetailsModel("", "", "", "", 0, 0, 0, 0.0f);
            orderDetails.add(model);
            arraySize++;
        }

        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemSelected(position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDetails.remove(position);
                mListener.onItemDelete(position);
                arraySize--;
            }
        });

        addLink(holder,position);
        addColor(holder,position);
        addNotes(holder, position);
        addProductCost(holder, position);
        addProductSize(holder, position);
        addQuantity(holder, position);
        addProductNum(holder, position);

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
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductLink(holder.link.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addQuantity(@NonNull help holder,int position)
    {
        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductQuantity(Integer.parseInt(holder.quantity.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addProductNum(@NonNull help holder,int position)
    {
        holder.productNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductCode(Integer.parseInt(holder.productNumber.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addColor(@NonNull help holder,int position)
    {
        holder.color.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductColor(holder.color.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addProductSize(@NonNull help holder,int position)
    {
        holder.size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductSize(Integer.parseInt(holder.size.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addProductCost(@NonNull help holder,int position)
    {
        holder.productCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductCost(Integer.parseInt(holder.productCost.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addNotes(@NonNull help holder,int position)
    {
        holder.notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!  TextUtils.isEmpty(s)) {
                    orderDetails.get(position).setProductNotes(holder.notes.getText().toString());
                }
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
        Button delete;
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
            delete=(Button) itemView.findViewById(R.id.delete);



        }
    }
}
