
package com.mycompany.api_richardguevara;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministrarArchivo {
    private static final String ruta= "pqrClientes_RichardGuevara.txt";
    
    public static boolean escribirArchivo(Cliente cliente) throws IOException{
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, true);

        boolean result = false;
         try ( BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(cliente.getNombre() + "," + cliente.getCedula()+","+cliente.getDireccion()+","+
                    cliente.getPlanInternet()+","+cliente.getPqr()+","+cliente.getEstadoPQR()+","+cliente.getNumeroRadicado());
            bw.newLine();
            result = true;
            bw.close();
        } catch (Exception e) {
            result = false;
        }finally{
             fw.close();
         }
         
        return result;
    }
    
    public static boolean escribirArchivo(ArrayList<Cliente> clientes) throws IOException{
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, false);
        
        boolean result = false;
         try ( BufferedWriter bw = new BufferedWriter(fw)) {
            for(int i=0; i < clientes.size(); i++){
                Cliente cliente = clientes.get(i);
                bw.write(cliente.getNombre() + "," + cliente.getCedula()+","+cliente.getDireccion()+","+
                    cliente.getPlanInternet()+","+cliente.getPqr()+","+cliente.getEstadoPQR()+","+cliente.getNumeroRadicado());
                bw.newLine();
            }
            result = true;
            bw.close();
            
        } catch (Exception e) {
            result = false;
        }finally{
             fw.close();
         }
         
        return result;
    }
    
    
    
    public static boolean verificarArchivo(){
        File f = new File(ruta);
        return f.exists();
    }
    
    public static int contadorLineas(){
        
        FileReader fr=null;
        try {
            int lineas=0;
            fr = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(fr);
            lineas = (int) bf.lines().count();
            try {
                bf.close();
            } catch (IOException ex) {
                Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return lineas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public static ArrayList<Cliente> leerArchivo(){
        FileReader fr = null;
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try {
            
            fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    
                    String[] datos = line.split(",");
                    Cliente c=new Cliente(datos[0],datos[1],
                            datos[2],datos[3],datos[4],datos[5],datos[6]);
                    clientes.add(c);
                }
            } catch (IOException ex) {
                Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
} catch (FileNotFoundException ex) {
            Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return clientes;
    }
}
