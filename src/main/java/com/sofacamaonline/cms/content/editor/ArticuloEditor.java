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
package com.sofacamaonline.cms.content.editor;

import com.sofacamaonline.cms.domain.Articulo;
import com.sofacamaonline.ecommerce.bl.MedidaBL;
import jease.cms.domain.Item;
import jease.cms.web.component.RichTextarea;
import jease.cms.web.content.editor.ContentEditor;
import jfix.util.I18N;
import jfix.zk.Selectbutton;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

public class ArticuloEditor extends ContentEditor<Articulo> {

    Selectbutton medidaSelect = new Selectbutton();
    RichTextarea preface = new RichTextarea();
    Doublebox precioDesde = new Doublebox();
    Doublebox precioHasta = new Doublebox();
    Textbox desplazamiento = new Textbox();
    Textbox medidas = new Textbox();

    public ArticuloEditor() {
        preface.setHeight((getDesktopHeight() / 3 - 50) + "px");
    }

    public void init() {
        add(I18N.get("Medida"), medidaSelect);
        add(I18N.get("Content"), preface);
        add(I18N.get("PrecioDesde"), precioDesde);
        add(I18N.get("PrecioHasta"), precioHasta);
        add(I18N.get("Desplazamiento"), desplazamiento);
        add(I18N.get("Medidas"), medidas);
    }

    public void load() {
        medidaSelect.setModel(new MedidaBL().getAll(), getNode().getMedida());
        preface.setValue(getNode().getPreface());
        precioDesde.setValue(getNode().getPrecioDesde());
        precioHasta.setValue(getNode().getPrecioHasta());
        desplazamiento.setValue(getNode().getDesplazamiento());
        medidas.setValue(getNode().getMedidas());
    }

    public void save() {
        getNode().setMedida((Item) medidaSelect.getSelectedValue());
        getNode().setPreface(preface.getValue());
        getNode().setScale(250);
        getNode().setLabeled(false);
        getNode().setPrecioDesde(precioDesde.getValue());
        getNode().setPrecioHasta(precioHasta.getValue());
        getNode().setDesplazamiento(desplazamiento.getValue());
        getNode().setMedidas(medidas.getValue());
    }

    public void validate() {
        validate(precioDesde.getValue() > precioHasta.getValue(), I18N.get("PrecioHastaNoValido"));
    }

}
