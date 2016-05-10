package clientefeedback.aplicacaocliente.Empresa;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import clientefeedback.aplicacaocliente.MainFragment;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.TabPagerItem;
import clientefeedback.aplicacaocliente.ViewPagerAdapter;
import clientefeedback.aplicacaocliente.ViewPagerFragment;

/**
 * Created by Alexandre on 25/04/2016.
 */
public class DetalhesEmpresaFragment extends Fragment{
    private List<TabPagerItem> mTabs = new ArrayList<>();
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private Empresa empresa;
    ToggleButton btnFavorite;
    TextView nomeEmpresa;
    TextView avaliacoes;
    TextView numCheckins;
    TextView numComentarios;
    TextView numAvaliacoes;
    TextView culinaria;
    TextView endereco;
    TextView telefone;
    TextView descricao;

    public static DetalhesEmpresaFragment newInstance(String text){
        DetalhesEmpresaFragment mFragment = new DetalhesEmpresaFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            empresa = bundle.getParcelable("empresa");
        }
        createTabPagerItem();
    }

    private void createTabPagerItem(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalhes_empresa, container, false);
        btnFavorite = (ToggleButton)rootView.findViewById(R.id.btnFavorite);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));

        nomeEmpresa = (TextView)rootView.findViewById(R.id.tvNome);
        nomeEmpresa.setText(empresa.getNomeEmpresa());

        numComentarios = (TextView)rootView.findViewById(R.id.tvNumeroComentarios);
        numComentarios.setText(String.valueOf(empresa.getComentarios().size()));

        numAvaliacoes = (TextView)rootView.findViewById(R.id.tvNumeroAvaliacoes);
        numAvaliacoes.setText(String.valueOf(empresa.getAvaliacoes().size()));

        endereco = (TextView)rootView.findViewById(R.id.endereco);
        endereco.setText(empresa.getEndereco().getRua()+", "+empresa.getEndereco().getNumero()+", "+empresa.getEndereco().getBairro() );

        String tel = "";
        for(int i=0; i<empresa.getTelefones().size(); i++ ){
            tel = tel+" "+empresa.getTelefones().get(i).getNumero();
            if( i+1<empresa.getTelefones().size()){
            tel = tel+", ";
            }
        }
        telefone = (TextView)rootView.findViewById(R.id.telefone);
        telefone.setText(tel);




        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Favorito clicado", Toast.LENGTH_SHORT).show();

            }
        });
//        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
//
//        mViewPager.setOffscreenPageLimit(mTabs.size());
//        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mTabs));
//
//        TabLayout mSlidingTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
//
//
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mSlidingTabLayout.setElevation(15);
//        }
//        mSlidingTabLayout.setupWithViewPager(mViewPager);
    }
}