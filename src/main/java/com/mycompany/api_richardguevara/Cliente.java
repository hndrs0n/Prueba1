
package com.mycompany.api_richardguevara;


import static com.mycompany.api_richardguevara.AdministrarArchivo.contadorLineas;
import static com.mycompany.api_richardguevara.AdministrarArchivo.verificarArchivo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Cliente implements Serializable{
    
    private String nombre;
    private String cedula;
    private String direccion;
    private String planInternet;
    private String pqr;
    private int numeroRadicado;
    private String estadoPQR;
    private static int contadorRadicado=0;
    

    public Cliente(String nombre, String cedula, String direccion, String planInternet, String pqr) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.planInternet = planInternet;
        this.pqr = pqr;
        estadoPQR="recibido";
        if(contadorRadicado==0 && verificarArchivo()){
                contadorRadicado=contadorLineas();
        }
        this.numeroRadicado=contadorRadicado;
        contadorRadicado++;
        
    }

    public Cliente(String nombre, String cedula, String direccion, String planInternet, String pqr, String estadoPQR, String numeroRadicado) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.planInternet = planInternet;
        this.pqr = pqr;
        this.numeroRadicado = Integer.parseInt(numeroRadicado);
        this.estadoPQR = estadoPQR;
    }
    
   
    
   

    public String getEstadoPQR() {
        return estadoPQR;
    }

    public void setEstadoPQR(String estadoPQR) {
        this.estadoPQR = estadoPQR;
    }

    public static int getContadorRadicado() {
        return contadorRadicado;
    }

    public static void setContadorRadicado(int contadorRadicado) {
        Cliente.contadorRadicado = contadorRadicado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPlanInternet() {
        return planInternet;
    }

    public void setPlanInternet(String planInternet) {
        this.planInternet = planInternet;
    }

    public String getPqr() {
        return pqr;
    }

    public void setPqr(String pqr) {
        this.pqr = pqr;
    }

    public int getNumeroRadicado() {
        return numeroRadicado;
    }

    public void setNumeroRadicado(int numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }
    
    
   
    
}
