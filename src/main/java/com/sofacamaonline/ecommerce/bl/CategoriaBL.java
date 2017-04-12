/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.ecommerce.bl;

import com.sofacamaonline.cms.domain.Articulo;
import com.sofacamaonline.cms.domain.Categoria;
import jease.cmf.service.Nodes;

import java.util.HashMap;

/**
 * @author richard
 */
public class CategoriaBL {

    public static Categoria[] getAll() {
        return (Categoria[]) Nodes.getByPath("/sofas").getChildren(Categoria.class);
    }

    public static Double[] getPrecios(Categoria cat) {
        Articulo[] articulos = cat.getChildren(Articulo.class);
        Double[] result = new Double[2];
        result[0] = 999999D;
        result[1] = 0D;
        for (Articulo art : articulos) {
            HashMap<String, String> opciones = new HashMap<String, String>();
            opciones.put("medida", art.getMedida().getId());
            Double tempPrecioDesde = ArticuloBL.getPrecio(cat.getPath(), opciones);
            if (tempPrecioDesde < result[0]) {
                result[0] = tempPrecioDesde;
            }
            opciones.put("tela", "piel-1-1");
            Double tempPrecioHasta = ArticuloBL.getPrecio(cat.getPath(), opciones);
            if (tempPrecioHasta > result[1]) {
                result[1] = tempPrecioHasta;
            }
        }
        return result;
    }
}
