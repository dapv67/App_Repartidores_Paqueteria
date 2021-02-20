package logisticdelsur.com.mx.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import logisticdelsur.com.mx.api.modelo.Ruta;

public class SpinAdapterRuta extends ArrayAdapter<Ruta> {

    private Context context;
    private List<Ruta> values;

    public SpinAdapterRuta(Context context,
                             int textViewResourceId,
                             List<Ruta> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public Ruta getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return values.get(position).getId_ruta();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getNombre());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getNombre());

        return label;
    }
}