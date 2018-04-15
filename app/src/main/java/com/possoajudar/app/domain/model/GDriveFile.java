package com.possoajudar.app.domain.model;

public class GDriveFile {
    String kind;
    String id;
    String name;
    String mimeType;
    String cr="\r\n";

    public String toString(String indent) {
        final StringBuffer sb = new StringBuffer(indent+"GDriveFile{\r\n");
        sb.append(indent).append(indent).append("id='").append(id).append('\'').append(cr);
        sb.append(indent).append(indent).append(", kind='").append(kind).append('\'').append(cr);
        sb.append(indent).append(indent).append(", name='").append(name).append('\'').append(cr);
        sb.append(indent).append(indent).append(", mimeType='").append(mimeType).append('\'').append(cr);
        sb.append(indent).append(indent).append('}').append("\r\n");
        return sb.toString();
    }
}
