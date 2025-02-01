package id.putra.simpleapprovalsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.putra.simpleapprovalsystem.constant.Actions;
import id.putra.simpleapprovalsystem.dto.ApprovalDto;
import id.putra.simpleapprovalsystem.request.ApprovalRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
public class ApprovalService implements FileService<ApprovalDto, ApprovalRequest> {
    public List<ApprovalDto> getApprovals() {
        try {
            return getData();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<ApprovalDto> getData() throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = ResourceUtils.getFile("classpath:approval.json");
        List<ApprovalDto> list = mapper.readValue(file, new TypeReference<>() {
        });
        return list;
    }

    @Override
    public void addData(ApprovalRequest approvalRequest, Actions actions, Principal principal) throws IOException {

    }
}
