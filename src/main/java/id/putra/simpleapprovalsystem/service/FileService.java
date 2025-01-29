package id.putra.simpleapprovalsystem.service;

import java.io.IOException;
import java.util.List;

public interface FileService<T, Param> {
    List<T> getData() throws IOException;

    void updateData(Param param) throws IOException;
}
