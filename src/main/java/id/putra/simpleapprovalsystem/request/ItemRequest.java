package id.putra.simpleapprovalsystem.request;

import id.putra.simpleapprovalsystem.constant.Actions;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ItemRequest {
    private String id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date due_date;
    private Actions actions;

}
