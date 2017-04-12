/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.cms.domain;

import jease.cmf.domain.Node;
import jease.cmf.domain.NodeException;
import jease.cms.domain.Gallery;
import jease.cms.domain.Image;
import jease.cms.domain.Item;

/**
 * @author richard
 */
public class Articulo extends Gallery {

    private Item medida;
    private Double precioDesde;
    private Double precioHasta;
    private String Desplazamiento;
    private String Medidas;

    public String getDesplazamiento() {
        return Desplazamiento;
    }

    public void setDesplazamiento(String Desplazamiento) {
        this.Desplazamiento = Desplazamiento;
    }

    public String getMedidas() {
        return Medidas;
    }

    public void setMedidas(String Medidas) {
        this.Medidas = Medidas;
    }

    public Articulo() {
    }

    public Item getMedida() {
        return medida;
    }

    public void setMedida(Item medida) {
        this.medida = medida;
    }

    public Double getPrecioDesde() {
        return precioDesde;
    }

    public void setPrecioDesde(Double precioDesde) {
        this.precioDesde = precioDesde;
    }

    public Double getPrecioHasta() {
        return precioHasta;
    }

    public void setPrecioHasta(Double precioHasta) {
        this.precioHasta = precioHasta;
    }

    @Override
    protected void validateNesting(Node potentialChild, String potentialChildId)
            throws NodeException {
        if (potentialChild instanceof ArticuloOpcion || potentialChild instanceof Image) {
        } else {
            throw new NodeException.IllegalNesting();
        }
    }
}
