package clientefeedback.aplicacaocliente;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alexandre on 21/05/2016.
 */
public class SharedData {
    SharedPreferences sharedPreferences;
    public SharedData(Context context) {
        sharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE);
    }

    public int getPessoaId(){
        return sharedPreferences.getInt("pessoaId", 0);
    }

}
