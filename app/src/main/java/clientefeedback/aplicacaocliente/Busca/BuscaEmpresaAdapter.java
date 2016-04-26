package clientefeedback.aplicacaocliente.Busca;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import clientefeedback.aplicacaocliente.CustomApplication;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.R;


public class BuscaEmpresaAdapter extends BaseAdapter {
    private List<Empresa> list;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;


    public BuscaEmpresaAdapter(Context c, List<Empresa> l){
        list = l;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ((CustomApplication) ((Activity) c).getApplication()).getImageLoader();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getEmpresaId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;



        if(view == null){
            view = inflater.inflate(R.layout.busca_empresa, null);
            holder = new ViewHolder();
            view.setTag(holder);

            holder.ivImg = (ImageView) view.findViewById(R.id.imgEmpresa);
            holder.tvNomeEmpresa = (TextView) view.findViewById(R.id.tvNomeEmpresa);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.ivImg.setImageBitmap(null);
        imageLoader.displayImage(list.get(position).getImagemPerfil().getImg(), holder.ivImg);
        holder.tvNomeEmpresa.setText(list.get(position).getNomeEmpresa());

        return view;
    }


    // INNER CLASS
        private static class ViewHolder{
            ImageView ivImg;
            TextView tvNomeEmpresa;
        }
}
