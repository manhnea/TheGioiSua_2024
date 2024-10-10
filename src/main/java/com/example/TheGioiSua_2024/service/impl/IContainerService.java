package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Container;

import java.util.List;
import java.util.Optional;

public interface IContainerService {
    List<Container> getAllContainers();
    String addContainer(Container container);
    String updateContainer(Long id,Container container);
    Container deleteContainer(Long id);
    Optional<Container> getContainerByName(String containerName);
}
