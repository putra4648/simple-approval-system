package id.putra.simpleapprovalsystem.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.putra.simpleapprovalsystem.dto.ItemDto;
import id.putra.simpleapprovalsystem.request.ItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService implements FileService<ItemDto, ItemRequest> {
    @Override
    public List<ItemDto> getData() throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        File file = ResourceUtils.getFile("classpath:item.json");
        List<ItemDto> list = mapper.readValue(file, List.class);
        return list;
    }

    @Override
    public void updateData(ItemRequest item) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<ItemDto> data = null;
        try {
            data = getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (data != null) {
            ItemDto dto = new ItemDto();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDueDate(item.getDue_date());

            boolean itemExist = data.contains(dto);

            if (itemExist) {
                for (ItemDto itemDto : data) {
                    if (itemDto.getId().equals(item.getId())) {
                        itemDto.setName(item.getName());
                        itemDto.setDueDate(item.getDue_date());
                    }
                }
            } else {
                ItemDto newItem = new ItemDto();
                newItem.setId(UUID.randomUUID().toString());
                newItem.setName(item.getName());
                newItem.setDueDate(item.getDue_date());

                data.add(newItem);
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
