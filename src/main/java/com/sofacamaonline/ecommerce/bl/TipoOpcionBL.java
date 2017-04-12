/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.ecommerce.bl;

import jease.cmf.domain.Node;
import jease.cmf.service.Nodes;
import jease.cms.domain.Item;

/**
 * @author richard
 */
public class TipoOpcionBL {

    public static Node[] getAll() {
        return Nodes.getByPath("/tipoopcion").getChildren(Item.class);
    }
}
