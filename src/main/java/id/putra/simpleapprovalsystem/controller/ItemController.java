package id.putra.simpleapprovalsystem.controller;

import id.putra.simpleapprovalsystem.request.ItemRequest;
import id.putra.simpleapprovalsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @PostMapping("")
    public ModelAndView postItem(@ModelAttribute("item") ItemRequest item, RedirectAttributesModelMap redirect) {
        itemService.updateData(item);

        redirect.addFlashAttribute("message", "Item updated");

        return new ModelAndView("redirect:/");
    }
}
