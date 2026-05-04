package model;

public interface UICallback {
    void log(String msg);
    void logMag(String msg);
    void onProdotta(int total);
    void  onPut1();
    void onTaken1();
    void onPut2();
    void onTaken2();
    void onApprovata(int total);
    void onScartata(int total);
    void onDistribuita(int total, PuntoVendita puntoVendita);
}