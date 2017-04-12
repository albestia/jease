/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.ecommerce.bl;

import com.sofacamaonline.cms.domain.Articulo;
import com.sofacamaonline.cms.domain.ArticuloOpcion;
import com.sofacamaonline.cms.domain.Categoria;
import jease.cmf.domain.Node;
import jease.cmf.service.Nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author albert
 */
public class ArticuloBL {

    public static Articulo[] getArticulosByCategoria(Categoria categoria) {
        if (categoria == null) {
            return Nodes.getRoot().getDescendants(Articulo.class);
        }
        return categoria.getChildren(Articulo.class);
    }

    public static Articulo getArticuloByCategoriaAndMedida(Categoria categoria, String medidaId) {
        for (Articulo art : getArticulosByCategoria(categoria)) {
            if (art.getMedida().getId().equals(medidaId)) {
                return art;
            }
        }
        return null;
    }

    public static Double getPrecio(String categoria, HashMap<String, String> opciones) {
        Articulo articulo = null;
        Categoria cat = (Categoria) Nodes.getByPath(categoria);
        Articulo[] articulos = getArticulosByCategoria(cat);
        if (!opciones.containsKey("medida")) {
            opciones.put("medida", articulos[0].getMedida().getId());
        }
        //Sabiendo la categoria, y la medida (que viene en opcioes), conseguimos el articulo
        String medida = opciones.get("medida");
        for (Articulo item : articulos) {
            if (item.getMedida().getId().equals(medida)) {
                articulo = item;
            }
        }
        if (articulo == null) {
            return 0D;
        }
        Double precio = 0D;
        Double precioOpciones = 0D;
        //Si no tenemos tela asignada, le ponemos la Serie A, el primer motivo y color (opción por defecto)
        if (!opciones.containsKey("tela")) {
            opciones.put("tela", "tela_Serie_A-1-1");
        }
        // Una vez identificado el articulo, vamos a buscar el precio de las opciones
        // Primero recuperamos todas las opciones que tiene un artículo, colgando del artículo
        // Tendremos en cuenta que algunas opciones están dentro de 'opcionesParaTodos'
        ArticuloOpcion[] opcionesDisponibles = OpcionBL.getOpcionesDisponibles(articulo, null);
        // Ahora voy a añadir las opciones que tenemos dentro de opcionesParaTodos, solo en el caso
        // en que no hayamos encontrado opciones de ese tipo concreto
        // Añado todos los artículos en una lista para ser más cómodo añadir las opciones para todos
        // Inicialmente solo lo haremos para colchon
        boolean haycolchones = false;
        List<ArticuloOpcion> result = new ArrayList<ArticuloOpcion>();
        for (ArticuloOpcion ao : opcionesDisponibles) {
            if (ao.getOpcion() != null && ao.getOpcion().getTipoOpcion() != null) {
                if (ao.getOpcion().getTipoOpcion().getId().equals("colchon")) {
                    haycolchones = true;
                }
            }
            result.add(ao);
        }
        // Si NO HAY COLCHONES, añadiremos los correspondientes de opcionesParaTodos
        if (!haycolchones) {
            Node colchonesNode = Nodes.getByPath("/opcionesParaTodos/colchon/");
            ArticuloOpcion[] colchones = colchonesNode.getChildren(ArticuloOpcion.class);
            for (ArticuloOpcion ao : colchones) {
                if (ao.getMedida() == null) {
                    System.out.println("El elemento " + ao.getPath() + " no tiene medida");
                } else if (ao.getMedida().equals(articulo.getMedida())) {
                    result.add(ao);
                }
            }
        }
        for (String opcion : opciones.keySet()) {
            String valor = opciones.get(opcion);
            if (valor == null || valor.isEmpty()) {
                continue;
            }
            if (opcion.equals("tela")) {
                valor = valor.substring(0, valor.indexOf("-"));
            }
            for (ArticuloOpcion ao : result) {
                if (ao == null || ao.getOpcion() == null) {
                    continue;
                }
                if (ao.getOpcion().getId().equals(valor)) {
                    if (ao.isPrecioAbsoluto()) {
                        precio = ao.getPrecioFinal();
                    } else {
                        precioOpciones += ao.getPrecioFinal();
                    }
                    break;
                }
            }
        }
        Double test = Math.rint(precio + precioOpciones);
        return test;
    }
}
