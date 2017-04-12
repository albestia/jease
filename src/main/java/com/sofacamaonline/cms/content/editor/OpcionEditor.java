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

import com.sofacamaonline.cms.domain.Opcion;
import com.sofacamaonline.ecommerce.bl.TipoOpcionBL;
import jease.cms.domain.Item;
import jease.cms.web.component.RichTextarea;
import jease.cms.web.content.editor.ContentEditor;
import jfix.util.I18N;
import jfix.zk.Div;
import jfix.zk.Selectbutton;
import jfix.zk.ZK;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;

public class OpcionEditor extends ContentEditor<Opcion> {

    Selectbutton tipoOpcionSelect = new Selectbutton();
    RichTextarea richText = new RichTextarea();
    Textbox plainText = new Textbox();
    Checkbox plainMode = new Checkbox(I18N.get("Plaintext"));

    public OpcionEditor() {
        richText.setHeight((getDesktopHeight() / 3) + "px");
        plainText.setHeight((100 + getDesktopHeight() / 3) + "px");
        plainText.setWidth("100%");
        plainMode.addEventListener(Events.ON_CHECK, evt -> updateTextMode());
    }

    @Override
    public void init() throws Exception {
        add(I18N.get("Type"), tipoOpcionSelect);
        add(I18N.get("Content"), richText);
        add("", new Div("text-align: right;", plainMode));
    }

    @Override
    public void load() throws Exception {
        tipoOpcionSelect.setModel(
                TipoOpcionBL.getAll(),
                getNode().getTipoOpcion());
        richText.setText(getNode().getContent());
        plainText.setText(getNode().getContent());
        plainMode.setChecked(getNode().isPlain());
        updateTextMode();
    }

    @Override
    public void save() throws Exception {
        if (richText.getParent() != null) {
            getNode().setContent(richText.getText());
        }
        if (plainText.getParent() != null) {
            getNode().setContent(plainText.getText());
        }
        getNode().setPlain(plainMode.isChecked());
        if (tipoOpcionSelect.getSelectedValue() != null) {
            getNode().setTipoOpcion((Item) tipoOpcionSelect.getSelectedValue());
        }
    }

    @Override
    public void validate() throws Exception {
        validate(tipoOpcionSelect.getSelectedValue() == null, I18N.get("TipoOpcionRequerido"));
    }

    private void updateTextMode() {
        if (plainMode.isChecked()) {
            plainText.setValue(richText.getText());
            if (richText.getParent() != null) {
                ZK.replace(richText, plainText);
            }
        } else {
            richText.setText(plainText.getText());
            if (plainText.getParent() != null) {
                ZK.replace(plainText, richText);
            }
        }
    }
}
