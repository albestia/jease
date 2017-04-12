/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.ecommerce.bl;

import com.sofacamaonline.cms.domain.Articulo;
import com.sofacamaonline.cms.domain.ArticuloOpcion;
import com.sofacamaonline.cms.domain.Categoria;
import com.sofacamaonline.cms.domain.Opcion;
import jease.cmf.domain.Node;
import jease.cmf.service.Nodes;
import jease.cms.domain.Item;
import jease.cms.domain.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author richard
 */
public class OpcionBL {

    public static Opcion[] getByTipo(Item t) {
        Opcion[] temp = (Opcion[]) Nodes.getByPath("/opciones").getChildren(Opcion.class);
        ArrayList<Opcion> result = new ArrayList<Opcion>();
        for (Opcion op : temp) {
            if (op.getTipoOpcion().equals(t)) {
                result.add(op);
            }
        }
        Opcion[] resultado = new Opcion[result.size()];
        for (int i = 0; i < result.size(); i++) {
            resultado[i] = result.get(i);
        }
        return resultado;
    }

    public static ArticuloOpcion[] getOpcionesDisponibles(Categoria categoria) {
        Articulo[] articulos = ArticuloBL.getArticulosByCategoria(categoria);
        List<ArticuloOpcion> result = new ArrayList<ArticuloOpcion>();
        List<Opcion> opcion = new ArrayList<Opcion>();
        for (Articulo art : articulos) {
            ArticuloOpcion[] temp = getOpcionesDisponibles(art, null);
            for (ArticuloOpcion ao : temp) {
                if (!opcion.contains(ao.getOpcion())) {
                    result.add(ao);
                    opcion.add(ao.getOpcion());
                }
            }
        }
        return (ArticuloOpcion[]) result.toArray(new ArticuloOpcion[0]);
    }

    public static ArticuloOpcion[] getOpcionesDisponibles(Categoria categoria, String clasificacion) {
        if (clasificacion == null || clasificacion.isEmpty()) {
            return getOpcionesDisponibles(categoria);
        }
        Node clasificacionNode = Nodes.getByPath(clasificacion);
        if (clasificacionNode == null) {
            return getOpcionesDisponibles(categoria);
        }
        Reference[] referencias = clasificacionNode.getChildren(Reference.class);
        List<Articulo> arts = new ArrayList<Articulo>();
        for (Reference ref : referencias) {
            if (ref.getContent() instanceof Articulo) {
                arts.add((Articulo) ref.getContent());
            }
        }
        Articulo[] articulos = arts.toArray(new Articulo[0]);
        List<ArticuloOpcion> result = new ArrayList<ArticuloOpcion>();
        List<Opcion> opcion = new ArrayList<Opcion>();
        for (Articulo art : articulos) {
            ArticuloOpcion[] temp = getOpcionesDisponibles(art, null);
            for (ArticuloOpcion ao : temp) {
                if (!opcion.contains(ao.getOpcion())) {
                    result.add(ao);
                    opcion.add(ao.getOpcion());
                }
            }
        }
        return (ArticuloOpcion[]) result.toArray(new ArticuloOpcion[0]);
    }

    public static ArticuloOpcion[] getOpcionesDisponibles(Categoria categoria, String clasificacion, Item tipoOpcion) {
        if (clasificacion == null || clasificacion.isEmpty()) {
            return getOpcionesDisponibles(categoria);
        }
        Node clasificacionNode = Nodes.getByPath(clasificacion);
        if (clasificacionNode == null) {
            return getOpcionesDisponibles(categoria);
        }

        if (tipoOpcion == null) {
            return getOpcionesDisponibles(categoria, clasificacion);
        }

        Reference[] referencias = clasificacionNode.getChildren(Reference.class);
        List<Articulo> arts = new ArrayList<Articulo>();
        for (Reference ref : referencias) {
            if (ref.getContent() instanceof Articulo) {
                arts.add((Articulo) ref.getContent());
            }
        }
        Articulo[] articulos = arts.toArray(new Articulo[0]);
        List<ArticuloOpcion> result = new ArrayList<ArticuloOpcion>();
        List<Opcion> opcion = new ArrayList<Opcion>();
        for (Articulo art : articulos) {
            ArticuloOpcion[] temp = getOpcionesDisponibles(art, tipoOpcion);
            for (ArticuloOpcion ao : temp) {
                if (!opcion.contains(ao.getOpcion())) {
                    result.add(ao);
                    opcion.add(ao.getOpcion());
                }
            }
        }
        return (ArticuloOpcion[]) result.toArray(new ArticuloOpcion[0]);
    }

    public static ArticuloOpcion[] getOpcionesDisponibles(Articulo articulo, Item tipoOpcion) {
        String medidaCompleta = articulo.getMedida().getId();
        // Miraré si el articulo ya tiene colchones. Si no, añadiré los de opcioesParaTodos
        boolean haycolchones = false;
        if (articulo == null) {
            return null;
        }
        ArticuloOpcion[] temp = articulo.getChildren(ArticuloOpcion.class);
        if (tipoOpcion == null) {
            return temp;
        }
        List<ArticuloOpcion> result = new ArrayList<ArticuloOpcion>();
        // Recorremos el listado de ArticuloOpcion del artículo para ver si tenemos colchones dentro
        // Si encontramos al menos una opcion que ya sea del tipo colchon, seteamos la variable 'haycolchon'
        // En cualquier caso, rellenamos 'result' con todos los ArticuloOpcion del artículo
        for (ArticuloOpcion ao : temp) {
            if (ao.getOpcion().getTipoOpcion().equals(tipoOpcion)) {
                if (ao.getOpcion().getTipoOpcion().getId().equals("colchon")) {
                    haycolchones = true;
                }
                result.add(ao);
            }
        }
        // UNA VEZ AñADIDAS TODAS LAS OPCIONES QUE TIENE EL ARTICULO, HE DE MIRAR EN 'OPCIONESPARATODOS'
        if (!haycolchones && tipoOpcion.getId().equals("colchon")) {
            Node colchonesNode = Nodes.getByPath("/opcionesParaTodos/colchon/");
            ArticuloOpcion[] colchones = colchonesNode.getChildren(ArticuloOpcion.class);
            for (ArticuloOpcion ao : colchones) {
                if (ao.getMedida().equals(articulo.getMedida())) {
                    result.add(ao);
                }
            }
        }
        return result.toArray(new ArticuloOpcion[result.size()]);
    }
}