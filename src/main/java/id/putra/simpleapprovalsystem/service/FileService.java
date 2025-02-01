package id.putra.simpleapprovalsystem.service;

import id.putra.simpleapprovalsystem.constant.Actions;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface FileService<T, Param> {
    List<T> getData() throws IOException;

    void addData(Param param, Actions actions, Principal principal) throws IOException;

}
