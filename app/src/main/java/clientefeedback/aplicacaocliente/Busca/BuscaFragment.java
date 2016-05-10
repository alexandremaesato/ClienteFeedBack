package clientefeedback.aplicacaocliente.Busca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clientefeedback.aplicacaocliente.CadastrarEmpresaFragment;
import clientefeedback.aplicacaocliente.Empresa.DetalhesEmpresaFragment;
import clientefeedback.aplicacaocliente.Empresa.PrincipalEmpresaFragment;
import clientefeedback.aplicacaocliente.MainActivity;
import clientefeedback.aplicacaocliente.MainFragment;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.Filtro;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.RequestData;
import clientefeedback.aplicacaocliente.Services.Url;
import clientefeedback.aplicacaocliente.Transaction;
import clientefeedback.aplicacaocliente.VolleyConn;


public class BuscaFragment extends Fragment implements Transaction, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    static FragmentManager f;
    private Context c = getContext();
    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "Busca";
    private ListView listView;
    private BuscaEmpresaAdapter adapter;
    private ProgressBar progressBar;
    private Empresa empresa;
    private boolean isThereMore;
    private List<Empresa> list;
    private Button botaoTeste;
    Fragment mFragment = null;
    FragmentManager mFragmentManager = this.getFragmentManager();

    public static BuscaFragment newInstance(String text){
        BuscaFragment mFragment = new BuscaFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);

        return mFragment;
    }
    public  BuscaFragment(){

    }
    public BuscaFragment (FragmentManager f){
        this.f = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_busca, container, false);

        TextView mTxtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        mTxtTitle.setText(TEXT_FRAGMENT);

        empresa = new Empresa();
        list = new ArrayList<Empresa>();

        progressBar = (ProgressBar) rootView.findViewById(R.id.pbProxy);

        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        (new VolleyConn(getContext(), this)).execute();

        /// TESTE TESTE TESTE TESTE TESTE TESTE TESTE TESTE TESTE TESTE TESTE TESTE


        botaoTeste = (Button)rootView.findViewById(R.id.btnTeste);
        botaoTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("teste");
                new CarregaEmpresaRequest(view,getContext(),f);

//                mFragment = new PrincipalEmpresaFragment();
//                mFragmentManager = f;
//                mFragmentManager.beginTransaction().replace(R.id.conteudo, mFragment).commit();

            }
        });
        /// TESTE fim TESTE fim TESTE fim TESTE fim TESTE fimTESTE fim TESTE fim

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_busca, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Busca");

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.colorPrimary));
        searchView.setOnQueryTextListener(onQuerySearchView);

        menu.findItem(R.id.menu_add).setVisible(true);

        mSearchCheck = false;

        final MenuItem menuFilter = menu.findItem(R.id.menu_filter);
        menuFilter.setVisible(true);

        menuFilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_add:
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_search:
                mSearchCheck = true;
                Toast.makeText(getActivity(), "Busca" , Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Intent it = new Intent(getContext(), DetailsActivity.class);
//        it.putExtra("car", list.get(position));
//        Bundle b = new Bundle();
//        b.put
//        startActivity(it);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int i) {
        if(view.getId() == listView.getId() && isThereMore){
            if(listView.getLastVisiblePosition() + 1 == list.size()){
                empresa.setEmpresaId(list.get(list.size() - 1).getEmpresaId());
                isThereMore = false;
                (new VolleyConn(getContext(), this)).execute();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void doBefore() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void doAfter(String answer) {
        try{
            JSONObject json = new JSONObject(answer);
            JSONArray empresasJson = json.getJSONArray("Empresas");
            Gson gson = new Gson();
            List<Empresa> empresas= new ArrayList<Empresa>();

            empresas = gson.fromJson(empresasJson.toString(),  new TypeToken<ArrayList<Empresa>>() {
            }.getType());

            if(empresas.size() > 0){
                if(adapter == null){
                    adapter = new BuscaEmpresaAdapter(getContext(), empresas);
                    listView.setAdapter(adapter);
                }
                else{
                    adapter.notifyDataSetChanged();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public RequestData getRequestData() {
        return( new RequestData(Url.getUrl()+"Empresa/buscarEmpresas", "", getParams()) );
    }

    public Map<String,String> getParams() {

        SharedPreferences sharedPreferences;
        sharedPreferences = getContext().getSharedPreferences("filtro", Context.MODE_PRIVATE);
        Filtro filtro = new Filtro();
        Gson gson = new Gson();
        Map<String,String> lista = new HashMap<String,String>();

        filtro.setCulinaria(sharedPreferences.getString("culinaria", ""));
        filtro.setEstado(sharedPreferences.getString("estado", ""));
        filtro.setCidade(sharedPreferences.getString("cidade", ""));
        filtro.setBairro(sharedPreferences.getString("bairro", ""));
        filtro.setValorMinimo(String.valueOf(sharedPreferences.getInt("valorMinimo", 0)));
        filtro.setValorMaximo(String.valueOf(sharedPreferences.getInt("valorMaximo", 500)));
        filtro.setOrdecacao(sharedPreferences.getString("ordenacao", ""));
        lista.put("filtro", gson.toJson(filtro));
        return lista;
    }
}
