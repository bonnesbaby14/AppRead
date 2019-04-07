package bonzai.com.appread;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderDatos>{

ArrayList <Dato> listDatos;

    public MyAdapter(ArrayList<Dato> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        String hora=listDatos.get(i).fecha,
                s02=listDatos.get(i).s02,
                freq=listDatos.get(i).frecuecia,
                pres1=listDatos.get(i).presion1,
                pres2=listDatos.get(i).presion2,
                temp=listDatos.get(i).temperatura;


viewHolderDatos.HORA.setText(hora);
        viewHolderDatos.SO2C.setText(s02);
        viewHolderDatos.FREQC.setText(freq);
        viewHolderDatos.PRES1C.setText(pres1);
        viewHolderDatos.PRESI2C.setText(pres2);
        viewHolderDatos.TEMPC.setText(temp);





    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView HORA,SO2C,FREQC,PRES1C,PRESI2C,TEMPC;

        public ViewHolderDatos(@NonNull View itemView) {

            super(itemView);
            HORA=itemView.findViewById(R.id.horac);
            SO2C=itemView.findViewById(R.id.news02);
            FREQC=itemView.findViewById(R.id.newfreq);
            PRES1C=itemView.findViewById(R.id.newpres1);
            PRESI2C=itemView.findViewById(R.id.newpres2);
            TEMPC =itemView.findViewById(R.id.newtem);

        }


        }
    }

