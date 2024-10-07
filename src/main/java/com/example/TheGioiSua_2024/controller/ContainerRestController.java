package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Container;
import com.example.TheGioiSua_2024.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Container")
public class ContainerRestController {
    @Autowired
    private ContainerService containerService;
    @GetMapping("/lst")
    public List<Container> lst() {
        return containerService.getAllContainers();
    }
    @PostMapping("/add")
    public Container add(@RequestBody Container container) {
        return containerService.addContainer(container);
    }
    @PutMapping("/update/{id}")
    public Container update(@PathVariable("id") Long id, @RequestBody Container container) {
        return containerService.updateContainer(id, container);
    }
    @PutMapping("/delete/{id}")
    public Container delete(@PathVariable("id") Long id) {
        return containerService.deleteContainer(id);
    }
}
