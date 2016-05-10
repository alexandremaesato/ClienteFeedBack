package clientefeedback.aplicacaocliente.Empresa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import clientefeedback.aplicacaocliente.Interfaces.RecyclerViewOnClickListenerHack;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.ImageHelper;
import clientefeedback.aplicacaocliente.Models.Produto;
import clientefeedback.aplicacaocliente.R;

/**
 * Created by Alexandre on 04/05/2016.
 */
public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {
    private Context mContext;
    private List<Produto> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;

    public ProdutoAdapter(Context c, List<Produto> l) {
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_produto, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNome.setText(mList.get(position).getNomeProduto());
        holder.tvDescricao.setText(mList.get(position).getDescricao());

//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mList.get(position).getPhoto());
//        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
//
//        bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
//        holder.ivProduto.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(Produto e, int position){
        mList.add(position, e);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivProduto;
        public TextView tvNome;
        public TextView tvDescricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivProduto = (ImageView) itemView.findViewById(R.id.iv_produto);
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

