/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.ecommerce.bl;

import com.sofacamaonline.cms.domain.Articulo;
import com.sofacamaonline.cms.domain.Categoria;
import jease.cmf.domain.Node;
import jease.cmf.service.Nodes;
import jease.cms.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author richard
 */
public class MedidaBL {

    public Node[] getAll() {
        return Nodes.getByPath("/medidas").getChildren(Item.class);
    }

    public static Item[] getMedidasDisponibles(Categoria categoria) {
        List<Item> temp = new ArrayList<Item>();
        Articulo[] arts = ArticuloBL.getArticulosByCategoria(categoria);
        for (Articulo a : arts) {
            if (a.getMedida() != null && a.getMedida() instanceof Item && !temp.contains(a.getMedida())) {
                temp.add(a.getMedida());
            }
        }
        return (Item[]) temp.toArray(new Item[0]);
    }
}
