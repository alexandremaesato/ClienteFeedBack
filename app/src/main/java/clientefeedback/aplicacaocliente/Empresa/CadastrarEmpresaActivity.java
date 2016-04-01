package clientefeedback.aplicacaocliente.Empresa;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.Endereco;
import clientefeedback.aplicacaocliente.Models.Telefone;
import clientefeedback.aplicacaocliente.R;

public class CadastrarEmpresaActivity extends AppCompatActivity {

    private int REQUEST_CAMERA  = 1;
    private int SELECT_FILE     = 2;
    private List<EditText> allEdsTelefone = new ArrayList<>();

    EditText nome;
    EditText cnpj;
    EditText descricao;
    //    Button botao;
//    ProgressBar spinner;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_empresa);

        Toolbar cetoolbar = (Toolbar) findViewById(R.id.cetoolbar);
        setTitle(R.string.cadastro_empresa);
        setSupportActionBar(cetoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome           = (EditText)findViewById(R.id.editTextNomeEmpresa);
        cnpj           = (EditText)findViewById(R.id.editTextCnpj);
        descricao      = (EditText)findViewById(R.id.editTextDescricaoEmpresa);
        rua            = (EditText)findViewById(R.id.editTextRua);
        numero         = (EditText)findViewById(R.id.editTextNumeroCasa);
        complemento    = (EditText)findViewById(R.id.editTextComplemento);
        bairro         = (EditText)findViewById(R.id.editTextBairro);
        cep            = (EditText)findViewById(R.id.editTextCep);
        cidade         = (EditText)findViewById(R.id.editTextCidade);
        estado         = (EditText)findViewById(R.id.editTextEstado);
        pais           = (EditText)findViewById(R.id.editTextPais);
        tipoTelefone   = (EditText)findViewById(R.id.editTextTipoTelefone);
        numeroTelefone = (EditText)findViewById(R.id.editTextNumeroTelefone);
        bt_AddTelefone = (ImageButton) findViewById(R.id.bt_AddTelefone);
        ivImage        = (ImageView) findViewById(R.id.ivImage);

//        spinner.setVisibility(View.GONE);
//
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

//            public void onClick(View v) {
//                spinner.setVisibility(View.VISIBLE);
//
//                new Thread() {
//                    public void run() {
//
//                        WebService ws = new WebService(Url.cadastrarEmpresaUrl());
//                        Map params = new HashMap();
//
//                        Empresa emp = new Empresa();
//                        emp.setNomeEmpresa(nome.getText().toString());
//                        emp.setCnpj(cnpj.getText().toString());
//                        emp.setDescricao(descricao.getText().toString());

            //Pegar do Banco de Dados do Android
//                        Pessoa pessoa =  new Pessoa();
//                        pessoa.setNome("Nome de Teste");
//                        pessoa.setPessoaid(1);
//
//                        Gson g = new Gson();
//
//                        params.put("empresa", emp);
//                        params.put("pessoa", pessoa);
//                        String teste = g.toJson(params);
//
//                        try{
//                            String response = ws.doPost("cadastrarEmpresa", teste );
//                            System.out.println("Resultado: "+response);
//                    String response = ws.webGet("pegarEmpresas", params);
            //JSONObject json = new JSONObject(response);
//
//                    JSONObject jsonUsuario = new JSONObject(json.getString("usuario")); //Pega o Json e faz um load apenas dos dados do Usuario em um novo Json
//                    Usuario u = new Usuario();
//                    u.jsonToUsuario(jsonUsuario); //Converte o json em usuario
//                    Bundle b = new Bundle();
//                    //final ProgressDialog mprogressDialog = ProgressDialog.show(MainActivity.this, "Aguarde", "Processando...");
//                    if(u != null){
//                        b.putString("id", String.valueOf(u.getIdUsuario()));
//                        b.putString("nome", u.getNome());
//                    }else {
//                        b.putString("message", "Algo deu errado!!!");
//                    }
//                    Message msg = new Message();
//                    do {
//                        String res = response;
//                        Bundle b = new Bundle();
//                        b.putString("msg", response);
//
//                        msg.setData(b);
//                    }while(response == null);
//
//                   handler.sendMessage(msg);
            // handler.sendMessageAtTime(msg,3000);
//
//
//                        }catch (Exception e) {
//                            Message msg = new Message();
//                            Bundle b= new Bundle();
//                            b.putString("msg", "erro");
//                            msg.setData(b);
//                            handler.sendMessage(msg);
//                        }
//                    }
//                }.start();
//            }
        });

        bt_AddTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTelefone(v);
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
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
//            spinner.setVisibility(View.GONE);
            System.out.println("MSG: "+msg);
            Toast.makeText(CadastrarEmpresaActivity.this, msg.getData().getString("msg"), Toast.LENGTH_SHORT).show();

//            if( msg != null ) {
//                String idUsuario = msg.getData().getString("id");
//                String nome = msg.getData().getString("nome");
            //tostando(idUsuario);
//                if(idUsuario != null) {
//                    iniciaDashboard(idUsuario, nome);
//                }else
//                {
//                    tostando("Usuario ou senha incorreto!");
//                }
            //}
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tb_empresa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        int id = item.getItemId();

        if( id == android.R.id.home ){
            finish();
        }

        return true;
    }

    public void addTelefone( View view ){

        TableLayout tableLayoutCE = (TableLayout) findViewById(R.id.tableAddTelefone);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            ivImage = (ImageView) findViewById(R.id.ivImage);

            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ivImage.setImageBitmap(thumbnail);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                ivImage.setImageBitmap(bm);
            }
        }
    }
}
