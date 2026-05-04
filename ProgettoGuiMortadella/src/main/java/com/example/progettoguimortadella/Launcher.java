package com.example.progettoguimortadella;

import javafx.application.Application;
import model.Gestione;
import model.GestioneMagazzini;
import model.Laboratorio;
import model.PuntoVendita;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {

//        try {
//            Gestione gestione = new Gestione(new GestioneMagazzini());
//            for (Laboratorio l: gestione.getLaboratori()){
//                System.out.println(l.toString());
//            }
//
//            for (PuntoVendita l: gestione.getPuntiVendita()){
//                System.out.println(l.toString());
//            }
//        }  catch (IOException e) {
//            System.err.println(e.getMessage());
//        }



        Application.launch(HelloApplication.class, args);
    }
}
