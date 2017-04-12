package com.sofacamaonline.cms.domain;

import jease.cms.domain.File;

/**
 * Derives from File, but only images are allowed as storeable content-types.
 * Tambien le ponemos un campo de texto, que será la descripción
 */
public class Categoria extends File {

    private String content;
    private boolean plain;

    public Categoria() {
    }

    @Override
    public boolean isPage() {
        return true;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPlain() {
        return plain;
    }

    public void setPlain(boolean plain) {
        this.plain = plain;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    /**
     * Only accept images as valid content types.
     */
    @Override
    public boolean isValidContentType(String contentType) {
        return contentType.startsWith("image/");
    }

    @Override
    public Categoria copy(boolean recursive) {
        return (Categoria) super.copy(recursive);
    }

    @Override
    public StringBuilder getFulltext() {
        return super.getFulltext().append("\n").append(getContent());
    }
}
