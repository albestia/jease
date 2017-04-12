/*
Copyright (C) 2011 maik.jablonski@jease.org

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sofacamaonline.cms.domain;

import jease.cmf.domain.Node;
import jease.cmf.domain.NodeException;
import jease.cms.domain.Image;
import jease.cms.domain.Item;
import jease.cms.domain.Text;

/**
 * A Folder contains an array of content-objects. A Folder defines #getContent()
 * which returns the first page-like child of the Folder as default content
 * which is used to render the Folder.
 */
public class Opcion extends Text {

    private Item tipoOpcion;

    public Opcion() {
    }

    public Item getTipoOpcion() {
        return tipoOpcion;
    }

    public void setTipoOpcion(Item tipoOpcion) {
        this.tipoOpcion = tipoOpcion;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean isPage() {
        return false;
    }

    @Override
    public Opcion copy(boolean recursive) {
        return (Opcion) super.copy(recursive);
    }

    @Override
    protected void validateNesting(Node potentialChild, String potentialChildId) throws NodeException {
        super.validateNesting(potentialChild, potentialChildId);
        if (!(potentialChild instanceof Image)) {
            throw new NodeException.IllegalNesting();
        }
    }
}
