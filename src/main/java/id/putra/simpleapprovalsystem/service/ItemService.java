package id.putra.simpleapprovalsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.putra.simpleapprovalsystem.constant.Actions;
import id.putra.simpleapprovalsystem.constant.ItemStatus;
import id.putra.simpleapprovalsystem.dto.ApprovalDto;
import id.putra.simpleapprovalsystem.dto.ItemDto;
import id.putra.simpleapprovalsystem.request.ItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService implements FileService<ItemDto, ItemRequest> {
    private final ApprovalService approvalService;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    public ItemService(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @Override
    public List<ItemDto> getData() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:item.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<ItemDto> list = null;
        try {
            list = mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void addData(ItemRequest item, Actions actions, Principal principal) {
        List<ItemDto> data = getData();

        ItemDto newItem = new ItemDto();
        newItem.setId(UUID.randomUUID().toString());
        newItem.setName(item.getName());
        newItem.setDue_date(dateFormat.format(item.getDue_date()));
        newItem.setStatus(ItemStatus.PENDING.name());
        List<ApprovalDto> approvals = approvalService.getApprovals();
        newItem.setApprovals(approvals);
        newItem.setCurrent_level_approval(0);
        newItem.setNext_level_approval(1);
        newItem.setNext_approval(approvals.stream().filter(apr -> apr.getLevel() == 1).findFirst().get().getName());

        data.add(newItem);

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:item.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Files.write(file.toPath(), mapper.writeValueAsBytes(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateApprove(ItemRequest item, Principal principal) {
        List<ItemDto> data = getData();

        boolean itemExist = data.stream().filter(i -> i.getId().equals(item.getId())).findFirst().isPresent();

        if (itemExist) {
            for (ItemDto itemDto : data) {
                if (itemDto.getId().equals(item.getId())) {
                    System.out.println("PRINCIPAL " + principal.getName());
                    Optional<ApprovalDto> approval = itemDto.getApprovals().stream().filter(apr -> apr.getName().equalsIgnoreCase(principal.getName())).findFirst();
                    if (approval.isPresent()) {
                        if (item.getActions() != null) {
//                                itemDto.setNext_approval(approval.get().getLevel() == 2 ? "" : principal.getName());
                            if (item.getActions() == Actions.APPROVED) {
                                if (itemDto.getCurrent_level_approval() != 2) {
                                    int currentLevelApproval = approval.get().getLevel();
                                    itemDto.setCurrent_level_approval(currentLevelApproval);
                                    itemDto.setNext_level_approval(currentLevelApproval + 1);
                                    itemDto.setCurrent_approval(principal.getName());

                                    Optional<ApprovalDto> nextApproval = itemDto.getApprovals().stream().filter(apr -> apr.getLevel() == (currentLevelApproval + 1)).findFirst();
                                    if (nextApproval.isPresent()) {
                                        itemDto.setNext_approval(nextApproval.get().getName());
                                    }
                                } else {
                                    itemDto.setNext_approval(null);
                                }
                                itemDto.setStatus(ItemStatus.APPROVED.name());
                            } else {
                                itemDto.setNext_approval(null);
                                itemDto.setStatus(ItemStatus.REJECTED.name());
                            }
                        }
                    } else {
                        throw new RuntimeException("Approval not found");
                    }
                    // Set next approval
                }
            }

            File file = null;
            try {
                file = ResourceUtils.getFile("classpath:item.json");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                Files.write(file.toPath(), mapper.writeValueAsBytes(data));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

