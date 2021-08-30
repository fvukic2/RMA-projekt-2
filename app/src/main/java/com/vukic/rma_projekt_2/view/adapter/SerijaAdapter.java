package com.vukic.rma_projekt_2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.vukic.rma_projekt_2.model.Serija;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.vukic.rma_projekt_2.R;

public class SerijaAdapter extends RecyclerView.Adapter<SerijaAdapter.Red> {

    private List<Serija> podatci;
    private SerijaClickListener serijaClickListener;

    public SerijaAdapter(SerijaClickListener serijaClickListener){
        this.serijaClickListener = serijaClickListener;
    }

    @Override
    public Red onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.red_liste, parent, false);
        return new Red(view);
    }

    @Override
    public void onBindViewHolder(Red holder, int position) {
        Serija serija = podatci.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(serija.getDatum());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
        String stringDate = dateFormat.format(calendar.getTime());
        holder.datum.setText(stringDate);
        switch (serija.getImeSerije()){
            case 0:
                Picasso.get().load(R.drawable.breaking_bad).into(holder.slika);
                break;
            case 1:
                Picasso.get().load(R.drawable.vikings).into(holder.slika);
                break;
            case 2:
                Picasso.get().load(R.drawable.dark).into(holder.slika);
                break;
            case 3:
                Picasso.get().load(R.drawable.sopranos).into(holder.slika);
                break;
            case 4:
                Picasso.get().load(R.drawable.wire).into(holder.slika);
                break;
            case 5:
                Picasso.get().load(R.drawable.game_of_thrones).into(holder.slika);
                break;
            case 6:
                Picasso.get().load(R.drawable.peaky_blinders).into(holder.slika);
                break;
            case 7:
                Picasso.get().load(R.drawable.only_fools_and_horses).into(holder.slika);
                break;
            case 8:
                Picasso.get().load(R.drawable.stranger_things).into(holder.slika);
                break;
        }
        holder.zanr.setText(serija.getZanr());
        holder.redatelj.setText(serija.getRedatelj());
        holder.opis.setText(serija.getKratakOpis());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serijaClickListener.onItemClick(serija);
            }
        });
    }

    @Override
    public int getItemCount() {
        return podatci==null ? 0 : podatci.size();
    }



    public void setData(List<Serija> serijas) {
        this.podatci = serijas;
    }

    public class Red extends RecyclerView.ViewHolder {
        private ImageView slika;
        private TextView datum;
        private TextView zanr;
        private TextView redatelj;
        private TextView opis;
        public Red(View itemView) {
            super(itemView);
            datum = itemView.findViewById(R.id.datum);
            slika = itemView.findViewById(R.id.slika);
            zanr = itemView.findViewById(R.id.zanr);
            redatelj = itemView.findViewById(R.id.redatelj);
            opis = itemView.findViewById(R.id.opis);
        }
    }
}
