package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Container;

import java.util.List;

public interface IContainerService {
    List<Container> getAllContainers();
    Container addContainer(Container container);
    Container updateContainer(Long id,Container container);
    Container deleteContainer(Long id);
}
