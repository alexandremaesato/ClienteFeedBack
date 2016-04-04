package clientefeedback.aplicacaocliente;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clientefeedback.aplicacaocliente.Empresa.CadastrarEmpresaActivity;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.Endereco;
import clientefeedback.aplicacaocliente.Models.Telefone;


public class CadastrarEmpresaFragment extends Fragment {
    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private int REQUEST_CAMERA  = 1;
    private int SELECT_FILE     = 2;
    private List<EditText> allEdsTelefone = new ArrayList<>();
    EditText nome;
    EditText cnpj;
    EditText descricao;
    EditText cep;
    EditText rua;
    EditText numero;
    EditText complemento;
    EditText bairro;
    EditText cidade;
    EditText estado;
    EditText pais;
    EditText tipoTelefone;
    EditText numeroTelefone;
    ImageButton bt_AddTelefone;
    ImageView ivImage;

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
        View rootView = inflater.inflate(R.layout.fragment_cadastrar_empresa, container, false);
        View baseView = inflater.inflate(R.layout.fragment_main, container,false);


        TextView mTxtTitle = (TextView) baseView.findViewById(R.id.txtTitle);
        mTxtTitle.setText("Cadastrar Empresa");

        nome           = (EditText)rootView.findViewById(R.id.editTextNomeEmpresa);
        cnpj           = (EditText)rootView.findViewById(R.id.editTextCnpj);
        descricao      = (EditText)rootView.findViewById(R.id.editTextDescricaoEmpresa);
        rua            = (EditText)rootView.findViewById(R.id.editTextRua);
        numero         = (EditText)rootView.findViewById(R.id.editTextNumeroCasa);
        complemento    = (EditText)rootView.findViewById(R.id.editTextComplemento);
        bairro         = (EditText)rootView.findViewById(R.id.editTextBairro);
        cep            = (EditText)rootView.findViewById(R.id.editTextCep);
        cidade         = (EditText)rootView.findViewById(R.id.editTextCidade);
        estado         = (EditText)rootView.findViewById(R.id.editTextEstado);
        pais           = (EditText)rootView.findViewById(R.id.editTextPais);
        tipoTelefone   = (EditText)rootView.findViewById(R.id.editTextTipoTelefone);
        numeroTelefone = (EditText)rootView.findViewById(R.id.editTextNumeroTelefone);
        bt_AddTelefone = (ImageButton)rootView.findViewById(R.id.bt_AddTelefone);
        ivImage        = (ImageView)rootView.findViewById(R.id.ivImage);


        bt_AddTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTelefone(getView());
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String img_cam = getString(R.string.add_imagem_camera);
                final String img_lib = getString(R.string.add_imagem_galeria);
                final String cancel = getString(R.string.add_imagem_cancel);
                final CharSequence[] items = {img_cam, img_lib, cancel};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.add_imagem);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals(img_cam)) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);

                        } else if (items[item].equals(img_lib)) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE);

                        } else if (items[item].equals(cancel)) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cadastrar_empresa);

        Toolbar cetoolbar = (Toolbar) rootView.findViewById(R.id.cetoolbar);

        cetoolbar.setTitle(R.string.cadastro_empresa);
//        setSupportActionBar(cetoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        cetoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch( item.getItemId() ) {
                    case R.id.action_save:


                        new Thread() {
                            public void run() {

                                Empresa emp = new Empresa();
                                emp.setNomeEmpresa(nome.getText().toString());
                                emp.setCnpj(cnpj.getText().toString());
                                emp.setDescricao(descricao.getText().toString());

                                Endereco endereco = new Endereco(
                                        rua.getText().toString(),
                                        numero.getText().toString(),
                                        complemento.getText().toString(),
                                        bairro.getText().toString(),
                                        cep.getText().toString(),
                                        cidade.getText().toString(),
                                        estado.getText().toString(),
                                        pais.getText().toString()
                                );
                                emp.setEndereco(endereco);

                                List<Telefone> telefones = new ArrayList<Telefone>();
                                Telefone telefone = new Telefone(
                                        tipoTelefone.getText().toString(),
                                        numeroTelefone.getText().toString()
                                );


                                telefones.add(telefone);

                                if( allEdsTelefone.size() > 0 ){
                                    int i = 0;

                                    while( i < allEdsTelefone.size() ){

                                        Telefone tel = new Telefone(
                                                allEdsTelefone.get(i).getText().toString(),
                                                allEdsTelefone.get(i+1).getText().toString()
                                        );
                                        telefones.add(tel);

                                        i += 2;
                                    }
                                }

                                emp.setTelefones(telefones);

                            }
                        }.start();

                        break;
                }
                return false;
            }


        });


        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
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

    public void addTelefone( View view ){

        TableLayout tableLayoutCE = (TableLayout)view.findViewById(R.id.tableAddTelefone);


        // Linha 1
        // TableRow
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

        TableRow tableRow = new TableRow( view.getContext() );
        tableRow.setPadding(0,0,0,10);
        tableRow.setLayoutParams(tableRowParams);

        // Text
        TextView textViewTipoTel = new TextView(view.getContext());
        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.LEFT;
        textViewParams.gravity = Gravity.CENTER_VERTICAL;
        textViewParams.column  = 1;
        textViewTipoTel.setLayoutParams(textViewParams);
        textViewTipoTel.setTextSize(16);
        textViewTipoTel.setText("Tipo:");
        textViewTipoTel.setTextColor(Color.BLACK);

        // Edit
        EditText editTextTipoTel = new EditText(view.getContext());
        TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        editTextParams.gravity = Gravity.CENTER_VERTICAL;
        editTextParams.column  = 2;
        editTextParams.weight  = 1;
        editTextTipoTel.setLayoutParams(editTextParams);
        editTextTipoTel.setBackgroundResource(R.drawable.border_fields);
        editTextTipoTel.setPadding(0,0,0,0);
        editTextTipoTel.requestFocus();

        // ADD
        allEdsTelefone.add(editTextTipoTel);
        tableRow.addView(textViewTipoTel);
        tableRow.addView(editTextTipoTel);
        tableLayoutCE.addView(tableRow);

        // linha 2
        // TableRow
        TableRow tableRow2 = new TableRow( view.getContext() );
        tableRow2.setPadding(0,0,0,10);
        tableRow2.setLayoutParams(tableRowParams);

        // Text
        TextView textViewNumeroTel = new TextView(view.getContext());
        TableRow.LayoutParams textView2Params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        textView2Params.gravity = Gravity.LEFT;
        textView2Params.gravity = Gravity.CENTER_VERTICAL;
        textView2Params.column  = 1;
        textViewNumeroTel.setLayoutParams(textView2Params);
        textViewNumeroTel.setTextSize(16);
        textViewNumeroTel.setText("Numero:");
        textViewNumeroTel.setTextColor(Color.BLACK);

        // Edit
        EditText editTextNumeroTel = new EditText(view.getContext());
        TableRow.LayoutParams editText2Params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        editText2Params.gravity = Gravity.CENTER_VERTICAL;
        editText2Params.column  = 2;
        editText2Params.weight  = 1;
        editTextNumeroTel.setLayoutParams(editText2Params);
        editTextNumeroTel.setBackgroundResource(R.drawable.border_fields);
        editTextNumeroTel.setPadding(0,0,0,0);

        // ADD
        allEdsTelefone.add(editTextNumeroTel);
        tableRow2.addView(textViewNumeroTel);
        tableRow2.addView(editTextNumeroTel);
        tableLayoutCE.addView(tableRow2);

    }
}
