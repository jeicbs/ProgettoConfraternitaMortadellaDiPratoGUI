package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class Gestione {

    private boolean finito=false;

    private Laboratorio[] laboratori= null;
    private GestioneMagazzini gestioneMagazzini;
    private PuntoVendita []  puntiVendita = new PuntoVendita[3];
    private Gson gson = new Gson();
    private FileReader reader;
    private UICallback logger;


    public Gestione(GestioneMagazzini g, UICallback logger) throws IOException {
        this.gestioneMagazzini=g;
        reader = new FileReader("src/main/resources/com/example/progettoguimortadella/laboratori.json");
        JsonObject obj = gson.fromJson(reader, JsonObject.class);
        JsonArray arr = obj.get("laboratori").getAsJsonArray();

        reader.close();

        laboratori = new Laboratorio[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            JsonObject lab = arr.get(i).getAsJsonObject();
            String nome = lab.get("nomeLaboratorio").getAsString();
            String indirizzo = lab.get("indirizzo").getAsString();
            String telefono = lab.get("telefono").getAsString();
            laboratori[i] = new Laboratorio(nome, indirizzo, telefono, g, logger);
        }
        this.logger=logger;

        reader = new FileReader("src/main/resources/com/example/progettoguimortadella/puntiVendita.csv");
        BufferedReader br = new BufferedReader(reader);

        for (int i = 0; i < 3; i++) {
            String app = br.readLine();
            String []campi= app.split(";");
            puntiVendita[i] = new PuntoVendita(campi[0],campi[1]);
        }
        br.close();
        reader.close();


    }
    public void start(){


        for (int i = 0; i < 3; i++) {
            Ispettore isp = new Ispettore(gestioneMagazzini, "sorri"+i, logger);
            isp.start();
        }
        for (int i = 0; i < 2; i++) {
            Distributore d = new Distributore(gestioneMagazzini, "ren"+i, this, logger);
            d.start();
        }
    }

    public void startLaboratorioAt(int i){
        laboratori[i].start();
    }
    public Laboratorio[] getLaboratori() {
        return laboratori;
    }

    public PuntoVendita[] getPuntiVendita() {
        return puntiVendita;
    }
    public boolean getFinito() {
        return finito;
    }

}
