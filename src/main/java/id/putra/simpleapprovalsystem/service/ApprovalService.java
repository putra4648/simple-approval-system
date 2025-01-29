package id.putra.simpleapprovalsystem.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.putra.simpleapprovalsystem.dto.ApprovalDto;
import id.putra.simpleapprovalsystem.request.ApprovalRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
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
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        File file = ResourceUtils.getFile("classpath:approval.json");
        System.out.println(file);
        List<ApprovalDto> list = mapper.readValue(file, List.class);
        return list;
    }

    @Override
    public void updateData(ApprovalRequest approvalRequest) throws IOException {

    }
}
