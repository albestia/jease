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

import com.sofacamaonline.cms.domain.Categoria;
import jease.cms.web.component.RichTextarea;
import jease.cms.web.content.editor.FileEditor;
import jfix.util.I18N;
import org.zkoss.zul.Textbox;

public class CategoriaEditor extends FileEditor<Categoria> {

    RichTextarea richText = new RichTextarea();
    Textbox plainText = new Textbox();

    public CategoriaEditor() {
        super();
        richText.setHeight((getDesktopHeight() / 3) + "px");
        plainText.setHeight((100 + getDesktopHeight() / 3) + "px");
        plainText.setWidth("100%");
    }

    @Override
    public void init() {
        add(I18N.get("Content"), richText);
        super.init();
    }

    @Override
    public void load() {
        richText.setText(getNode().getContent());
        plainText.setText(getNode().getContent());
        super.load();
    }

    @Override
    public void save() {
        if (richText.getParent() != null) {
            getNode().setContent(richText.getText());
        }
        if (plainText.getParent() != null) {
            getNode().setContent(plainText.getText());
        }
        getNode().setPlain(false);
        super.save();
    }

    @Override
    public void validate() {
    }
}
