package id.putra.simpleapprovalsystem.controller;

import id.putra.simpleapprovalsystem.request.ItemRequest;
import id.putra.simpleapprovalsystem.service.ApprovalService;
import id.putra.simpleapprovalsystem.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ApprovalService approvalService;
    private final ItemService itemService;

    @GetMapping("")
    public ModelAndView index(ModelMap model) {
        model.put("approvals", approvalService.getApprovals());
        model.put("item", new ItemRequest());
        model.put("items", itemService.getData());
        return new ModelAndView("index", model);
    }
}
