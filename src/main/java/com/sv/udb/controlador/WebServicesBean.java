/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.utils.AlumnosPojo;
import com.sv.udb.utils.pojos.DatosAlumnos;
import com.sv.udb.utils.pojos.WSconsAlumByDoce;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import org.apache.commons.codec.Charsets;
import org.primefaces.context.RequestContext;

/**
 *
 * @author REGISTRO
 */
@Named(value = "webServicesBean")
@ViewScoped
public class WebServicesBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private String nombUsua;
    private String filt; //Filotro de búsqueda
    private WSconsAlumByDoce objeWebServ;

    public String getNombUsua() {
        return nombUsua;
    }

    public void setNombUsua(String nombUsua) {
        this.nombUsua = nombUsua;
    }

    public String getFilt() {
        return filt;
    }

    public void setFilt(String filt) {
        this.filt = filt;
    }

    public WSconsAlumByDoce getObjeWebServ() {
        return objeWebServ;
    }

    public void setObjeWebServ(WSconsAlumByDoce objeWebServ) {
        this.objeWebServ = objeWebServ;
    }
    
    /**
     * Creates a new instance of WebServicesBean
     */
    
    public WebServicesBean() {
    }
    
    public void nuev()
    {
        this.nombUsua = "";
    }
    
    public void consWebServ()
    {
        FacesContext facsCtxt = FacesContext.getCurrentInstance();
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        Client client = ClientBuilder.newClient();
        String url = facsCtxt.getExternalContext().getInitParameter("webservices.URL"); //Esta en el web.xml
        url = String.format("%s/%s/%s", url, "consAlumByDoce", this.filt);
        WebTarget resource = client.target(url);
        Builder request = resource.request();
        request.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"));
        Response response = request.get();
        if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL)
        {
            this.objeWebServ = response.readEntity(WSconsAlumByDoce.class); //La respuesta de captura en un pojo que esta en el paquete utils
//            for(DatosAlumnos temp : this.objeWebServ.getResu())
//            {
//                System.err.println(String.format("Carnet: %s Nombre: %s", temp.getCarn(), temp.getNomb()));
//            }
        }
        else
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al procesar la consulta')");
        }
    }
    
    //Lógica slider
    private boolean showBusc = false;

    public boolean isShowBusc() {
        return showBusc;
    }
    
    public void abri()
    {
        
    }
    
    public void limpForm()
    {
        
    }
    /*
    * Toogle buscador, cambia el valor del buscador
    */
    public void toogBusc()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        this.showBusc = !this.showBusc;
    }
    
}
