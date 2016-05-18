package clientefeedback.aplicacaocliente.Busca;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.List;

import clientefeedback.aplicacaocliente.CustomApplication;
import clientefeedback.aplicacaocliente.Interfaces.RecyclerViewOnClickListenerHack;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.ImageHelper;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.Services.ImageLoaderCustom;
import clientefeedback.aplicacaocliente.Services.Url;


public class BuscaEmpresaAdapter extends RecyclerView.Adapter<BuscaEmpresaAdapter.MyViewHolder> {
    private Context mContext;
    private List<Empresa> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    public BuscaEmpresaAdapter(Context c, List<Empresa> l) {
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
        imageLoader = ImageLoaderCustom.getImageloader(mContext);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_loading) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_menu_camera) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_empresa_main, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNome.setText(mList.get(position).getNomeEmpresa());
        holder.tvDescricao.setText(mList.get(position).getDescricao());

        if(mList.get(position).hasImagemPerfil()) {
            String url = Url.IP + mList.get(position).getImagemPerfil().getCaminho() + mList.get(position).getImagemPerfil().getNomeImagem();
            imageLoader.displayImage(url, holder.ivEmpresa, options);
        }



        /*
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mList.get(position).getPhoto());
        if(bitmap != null){
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            //Metodo de Redimentsionamento
            bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
            //holder.ivEmpresa.setImageBitmap(bitmap);
            String url = Url.IP + mList.get(position).getImagemPerfil().getCaminho()+mList.get(position).getImagemPerfil().getNomeImagem();
            imageLoader.displayImage(url, holder.ivEmpresa);
        }
        */



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(Empresa e, int position){
        mList.add(position, e);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public long getItemId(int position) {
        return mList.get(position).getEmpresaId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivEmpresa;
        public TextView tvNome;
        public TextView tvDescricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivEmpresa = (ImageView) itemView.findViewById(R.id.iv_empresa);
            tvNome = (TextView) itemView.findViewById(R.id.tv_nome);
            tvDescricao = (TextView) itemView.findViewById(R.id.tv_descricao);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }

    }


}

