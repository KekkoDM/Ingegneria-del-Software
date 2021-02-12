package GUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ListGenres> listGenres;

    public MyAdapter(Context context, ArrayList<ListGenres> listGenres) {
        this.context = context;
        this.listGenres = listGenres;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_genres, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListGenres currentGenre =  listGenres.get(position);
        String genre = currentGenre.getGenre();

        holder.mGenre.setText(genre);
    }

    @Override
    public int getItemCount() {
        return listGenres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mGenre;

        public MyViewHolder(View itemView) {
            super(itemView);
            mGenre = itemView.findViewById(R.id.genreName);
        }
    }
}