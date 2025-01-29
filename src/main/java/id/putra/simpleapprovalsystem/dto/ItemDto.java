package id.putra.simpleapprovalsystem.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ItemDto {
    private String id;
    private String name;
    private List<ApprovalDto> approvals;
    private Date dueDate;
}
