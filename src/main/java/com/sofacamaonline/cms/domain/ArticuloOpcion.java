/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.cms.domain;

import jease.cms.domain.Content;
import jease.cms.domain.Item;

/**
 * @author richard
 */
public class ArticuloOpcion extends Content implements Comparable<ArticuloOpcion> {

    private Item medida;
    private Opcion opcion;
    private Double precio;
    private boolean precioAbsoluto = true;
    private boolean porDefecto = false;

    public ArticuloOpcion() {
    }

    public boolean isPrecioAbsoluto() {
        return precioAbsoluto;
    }

    public void setPrecioAbsoluto(boolean precioAbsoluto) {
        this.precioAbsoluto = precioAbsoluto;
    }

    public Item getMedida() {
        return medida;
    }

    public void setMedida(Item medida) {
        this.medida = medida;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Double getPrecioFinal() {
        if (this.precio == null) return 0D;
        Double precio = this.precio;
        if (getOpcion() != null && getOpcion().getTipoOpcion() != null) {
            precio = this.precio * new Double(getOpcion().getTipoOpcion().getProperty("factor", "1"));
        }
        return Math.rint(precio);
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(boolean porDefecto) {
        this.porDefecto = porDefecto;
    }

    public int compareTo(ArticuloOpcion t) {
        if (getOpcion().getTipoOpcion().getTitle().equals(t.getOpcion().getTipoOpcion().getTitle())) {
            return getOpcion().getTitle().compareTo(t.getOpcion().getTitle());
        } else {
            return getOpcion().getTipoOpcion().getTitle().compareTo(t.getOpcion().getTipoOpcion().getTitle());
        }

    }
}
