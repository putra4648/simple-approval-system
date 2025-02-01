package id.putra.simpleapprovalsystem.dto;

import lombok.Data;

@Data
public class ApprovalDto {
    private String id;
    private String name;
    private int level;
    private Boolean isApproved;
}
