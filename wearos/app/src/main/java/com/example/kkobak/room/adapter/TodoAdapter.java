package com.example.kkobak.room.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kkobak.R;
import com.example.kkobak.room.data.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private List<Todo> items;
    private Context context;

    public TodoAdapter(Context context, List<Todo> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 뷰홀더가 새로 만들어지는 시점에 호출되어 각 아이템을 위해 정의한 xml레이아웃을 이용해 뷰객체를 만들어준다.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_todo_card, parent, false); //인플레이션을 통해 뷰 객체 만들기
        //inflate는 xml 로 쓰여있는 View의 정의를 실제 VIew 객체로 만드는 것
        return new ViewHolder(itemView);//뷰홀더 객체를 생성하면서 위의 뷰객체 전달하고 뷰홀더 리턴
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰홀더가 재사용될 때 호출되어 뷰 객체는 기존 것을 그대로 사용하고 데이터만 바꿔준다.
        Todo item = items.get(position);
        holder.textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        // 어댑터에서 관리하는 아이템의 개수를 반환
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Long chlId;
        boolean done;

        public ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textView);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onLongItemClickListener != null) {
                            onLongItemClickListener.onLongItemClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });

        }

        public void setItem(Todo item){
            chlId = item.getChlId();
            done = item.getDone();
            textView.setText(item.getTitle());
        }

    }

    // OnClickListener ----------------------------------------------
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }
    //--------------------------------------------------------------
}
