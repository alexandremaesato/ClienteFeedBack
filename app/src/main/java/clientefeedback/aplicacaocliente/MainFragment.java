package clientefeedback.aplicacaocliente;

import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clientefeedback.aplicacaocliente.Adapters.EmpresaAdapter;
import clientefeedback.aplicacaocliente.Empresa.CadastrarEmpresaActivity;
import clientefeedback.aplicacaocliente.Interfaces.RecyclerViewOnClickListenerHack;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.Imagem;
import clientefeedback.aplicacaocliente.Services.ConnectionVerify;


public class MainFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private RecyclerView mRecyclerView;
    private List<Empresa> mList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static MainFragment newInstance(String text){
        MainFragment mFragment = new MainFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                EmpresaAdapter adapter = (EmpresaAdapter) mRecyclerView.getAdapter();

                if(mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1){
                    List<Empresa> listAux = getSetEmpresaList(5);

                    for(int i = 0; i < listAux.size(); i++){
                        adapter.addListItem( listAux.get(i), mList.size() );
                    }
                }

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mList = getSetEmpresaList(6);
        EmpresaAdapter adapter = new EmpresaAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);

        mSwipeRefreshLayout =(SwipeRefreshLayout) rootView.findViewById(R.id.srl_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(ConnectionVerify.verifyConnection( getActivity() )){
                    EmpresaAdapter adapter = (EmpresaAdapter) mRecyclerView.getAdapter();
                    List<Empresa> listAux = getSetEmpresaList(2);
                    for(int i = 0; i < listAux.size(); i++){
                        adapter.addListItem( listAux.get(i), 0 );
                        mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                } else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    Snackbar snackbar = Snackbar
                        .make(rootView, R.string.connection_swipe, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.connect, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                startActivity(it);
                            }
                        });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        });

        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));
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
        inflater.inflate(R.menu.menu, menu);

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

    public List<Empresa> getSetEmpresaList(int qtd){
        String[] nome = new String[]{"TESTE 1", "TESTE 2", "TESTE 3", "TESTE 4", "TESTE 5", "TESTE 6"};
        String[] descricao = new String[]{"TESTE TESTE 1", "TESTE TESTE 2", "TESTE TESTE 3", "TESTE TESTE 4", "TESTE TESTE 5", "TESTE TESTE 6"};
//        int[] categories = new int[]{2, 1, 2, 1, 1, 4, 3, 2, 4, 1};
        int[] photos = new int[]{R.drawable.teste_1, R.drawable.teste_2, R.drawable.teste_3, R.drawable.teste_4, R.drawable.teste_5, R.drawable.teste_6};
//        String[] urlPhotos = new String[]{"gallardo.jpg", "vyron.jpg", "corvette.jpg", "paganni_zonda.jpg", "porsche_911.jpg", "bmw_720.jpg", "db77.jpg", "mustang.jpg", "camaro.jpg", "ct6.jpg"};
//        String description = "Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos. Lorem Ipsum sobreviveu não só a cinco séculos, como também ao salto para a editoração eletrônica, permanecendo essencialmente inalterado. Se popularizou na década de 60, quando a Letraset lançou decalques contendo passagens de Lorem Ipsum, e mais recentemente quando passou a ser integrado a softwares de editoração eletrônica como Aldus PageMaker.";
        List<Empresa> listAux = new ArrayList<>();

        for(int i = 0; i < qtd; i++){
            Empresa e = new Empresa();
            e.setNomeEmpresa(nome[i % nome.length]);
            e.setDescricao(descricao[i % descricao.length]);
            e.setPhoto(photos[i % photos.length]);
//            c.setDescription(description);
//            c.setCategory( categories[ i % brands.length ] );
//            c.setTel("33221155");
//
//            if(category != 0 && c.getCategory() != category){
//                continue;
//            }
            listAux.add(e);
        }
        return(listAux);
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Position: "+position, Toast.LENGTH_SHORT).show();
        EmpresaAdapter adapter = (EmpresaAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
