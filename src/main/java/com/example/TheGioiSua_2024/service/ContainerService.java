package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Container;
import com.example.TheGioiSua_2024.repository.ContainerRepository;
import com.example.TheGioiSua_2024.service.impl.IContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerService implements IContainerService {
    @Autowired
    private ContainerRepository containerRepository;
    @Override
    public Container addContainer(Container container) {
        container.setStatus(1);
        return containerRepository.save(container);
    }

    @Override
    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    @Override
    public Container updateContainer(Long id, Container container) {
        Container contain = containerRepository.findById(id).orElseThrow();
        contain.setContainername(container.getContainername());
        contain.setDescription(container.getDescription());
        return containerRepository.save(contain);
    }

    @Override
    public Container deleteContainer(Long id) {
        Container container = containerRepository.findById(id).orElseThrow();
        container.setStatus(0);
        return containerRepository.save(container);
    }
}
