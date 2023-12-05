/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api_richardguevara;

import com.google.gson.Gson;
import static com.mycompany.api_richardguevara.AdministrarArchivo.escribirArchivo;
import static com.mycompany.api_richardguevara.AdministrarArchivo.leerArchivo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leonv
 */
public class AdministrarPQRs extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdministrarPQRs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>NO DEBIO ENTRAR AQUI</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        Gson myGson = new Gson();
        ArrayList<Cliente> clientes;
        clientes = leerArchivo();

        try (PrintWriter out = response.getWriter()) {
            out.write(myGson.toJson(clientes));
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        /*final String json = "{\"id\":46,\"nombre\":\"Miguel\",\"empresa\":\"Autentia\"}";
        final Gson gson = new Gson();
        final Properties properties = gson.fromJson(json, Properties.class);*/
        String nombre = request.getParameter("name");
        String cedula = request.getParameter("identification");
        String direccion = request.getParameter("address");
        String planInternet = request.getParameter("internetPlan");
        String pqr = request.getParameter("pqr");

        String usuario = request.getParameter("username");
        String password = request.getParameter("password");

        if (nombre != null) {
            Cliente cliente = new Cliente(nombre, cedula, direccion, planInternet, pqr);

            boolean estadoEnvioPQR = escribirArchivo(cliente);

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may u
            se following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<link rel=\"stylesheet\" href=\"GuardarSQRPage.css\">");
                out.println("<meta charset=\"UTF-8\" />");
                out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
                out.println("<title>registro PQR</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Querido usuario " + nombre + "<h1>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>");
                if (estadoEnvioPQR) {
                    out.println("<p>Su pqr se ha registrado exitosamente y su estado actual es recibido<p><br>");
                    out.println("<p>El numero de radicado de su pqr es: " + cliente.getNumeroRadicado() + "<p><br>");
                } else {
                    out.println("<p>Su pqr se ha logrado registrar, porfavor intentelo de nuevo mas tarde<p><br>");
                }
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {

            //String[] estadosPQR = {"recibido","en atencion", "resuelto"};
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.print("<link rel=\"stylesheet\" href=\"estilosVerPQRs.css\">");
                out.print("<script src=\"verPQRs.js\" type=\"text/javascript\"></script>");
                out.println("<title>Listado de PQRS</title>");
                out.println("</head>");
                out.println("<body>");
                if ((usuario.equals("admin") == false) || (password.equals("123") == false)) {
                    out.println("<h1>Credenciales erroneas, intentelo de nuevo</h1>");
                } else {
                    response.sendRedirect("./listadoPQRs.html");
                }

                out.println("</body>");
                out.println("</html>");
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = br.readLine();
            Gson myGson = new Gson();
            Properties properties = myGson.fromJson(data, Properties.class);

            System.out.println("BODY ==> " + data + "\n" + "ESTADO 1 ==> " + properties.getProperty("estadoPQR") + " Y NUMERO ==> "
                    + properties.getProperty("numeroRadicado"));
            int numero = Integer.parseInt(properties.getProperty("numeroRadicado"));

            ArrayList<Cliente> clientes;
            clientes = leerArchivo();

            clientes.get(numero).setEstadoPQR(properties.getProperty("estadoPQR"));

            escribirArchivo(clientes);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");

        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();

        int numero = Integer.parseInt(data);

        ArrayList<Cliente> clientes;
        clientes = leerArchivo();

        clientes.remove(numero);
       
        for(int i=numero; i < clientes.size(); i++){
            clientes.get(i).setNumeroRadicado(i);
        }

        escribirArchivo(clientes);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
