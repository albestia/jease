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
import com.sofacamaonline.cms.domain.ArticuloOpcion;
import com.sofacamaonline.cms.domain.Opcion;
import com.sofacamaonline.ecommerce.bl.MedidaBL;
import com.sofacamaonline.ecommerce.bl.OpcionBL;
import com.sofacamaonline.ecommerce.bl.TipoOpcionBL;
import jease.cms.domain.Item;
import jease.cms.web.content.editor.ContentEditor;
import jfix.util.I18N;
import jfix.zk.Selectbutton;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.GenericEventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;

public class ArticuloOpcionEditor extends ContentEditor<ArticuloOpcion> {

    Selectbutton medidaSelect = new Selectbutton();
    Selectbutton tipoOpcionSelect = new Selectbutton();
    Selectbutton opcionSelect = new Selectbutton();
    Doublebox precio = new Doublebox();
    Checkbox precioAbsoluto = new Checkbox();
    Checkbox defecto = new Checkbox();

    public ArticuloOpcionEditor() {
        tipoOpcionSelect.addEventListener(Events.ON_SELECT, new GenericEventListener<Event>() {
            @Override
            public void onEvent(Event evt) throws Exception {
                Item tipoOpcion = (Item) tipoOpcionSelect.getSelectedValue();
                if (tipoOpcion != null) {
                    opcionSelect.setModel(OpcionBL.getByTipo(tipoOpcion), getNode().getOpcion());
                }
            }
        });

        opcionSelect.addEventListener(Events.ON_SELECT, new GenericEventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                if (id.getValue() == null) {
                    id.setValue(((Opcion) opcionSelect.getSelectedValue()).getId());
                }
                if (title.getValue() == null) {
                    title.setValue(((Opcion) opcionSelect.getSelectedValue()).getTitle());
                }
            }
        });
    }

    public void init() {
        if (!(getNode().getParent() instanceof Articulo)) {
            add(I18N.get("Medida"), medidaSelect);
        }
        add(I18N.get("OptionType"), tipoOpcionSelect);
        add(I18N.get("Option"), opcionSelect);
        add(I18N.get("Precio"), precio);
        add(I18N.get("PrecioAbsoluto"), precioAbsoluto);
        add(I18N.get("Default"), defecto);
    }

    public void load() {
        medidaSelect.setModel(new MedidaBL().getAll(), getNode().getMedida());
        Item tipoOpcion = (getNode().getOpcion() == null) ? null : getNode().getOpcion().getTipoOpcion();
        tipoOpcionSelect.setModel(TipoOpcionBL.getAll(), tipoOpcion);
        if (tipoOpcion != null) {
            opcionSelect.setModel(OpcionBL.getByTipo(tipoOpcion), getNode().getOpcion());
        }
        precio.setValue(getNode().getPrecio());
        precioAbsoluto.setChecked(getNode().isPrecioAbsoluto());
        defecto.setChecked(getNode().isPorDefecto());
    }

    public void save() {
        getNode().setMedida((Item) medidaSelect.getSelectedValue());
        getNode().setOpcion((Opcion) opcionSelect.getSelectedValue());
        getNode().setPrecio(precio.getValue());
        getNode().setPrecioAbsoluto(precioAbsoluto.isChecked());
        getNode().setPorDefecto(defecto.isChecked());
    }

    public void validate() {
        validate(opcionSelect.getSelectedValue() == null, I18N.get("OpcionRequerido"));
    }
}
