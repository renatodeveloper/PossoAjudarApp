package com.possoajudar.app.domain.model;

import java.util.List;

public class GDriveFiles {
    String kind;
    String nextPageToken;
    List<GDriveFile> files;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GDriveFiles{");
        sb.append(",\r\n kind='").append(kind).append('\'');
        sb.append(",\r\n nextPageToken='").append(nextPageToken).append('\'');
        sb.append("\r\nfiles=\r\n");
        for (GDriveFile file : files) {
            sb.append(file.toString("\t"));
        }
        sb.append('}');
        return sb.toString();
    }
}
