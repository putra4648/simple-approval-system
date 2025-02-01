package id.putra.simpleapprovalsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDto {
    private String id;
    private String name;
    private List<ApprovalDto> approvals;
    private String due_date;
    private String status;
    private int current_level_approval;
    private int next_level_approval;
    private String current_approval;
    private String next_approval;
}
