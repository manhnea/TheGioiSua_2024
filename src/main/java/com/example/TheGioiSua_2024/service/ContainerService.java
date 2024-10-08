package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Container;
import com.example.TheGioiSua_2024.repository.ContainerRepository;
import com.example.TheGioiSua_2024.service.impl.IContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContainerService implements IContainerService {
    @Autowired
    private ContainerRepository containerRepository;
    @Override
    public String addContainer(Container container) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên container
        String trimmedName = container.getContainername().trim();
        container.setContainername(trimmedName);
        // Kiểm tra xem tên container đã tồn tại chưa
        Optional<Container> existingContainer = getContainerByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Container với tên này đã tồn tại.";
        }
        container.setStatus(1);
        containerRepository.save(container);
        return "Thêm thành công";
    }

    @Override
    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    @Override
    public String updateContainer(Long id, Container container) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên container
        String trimmedName = container.getContainername().trim();
        container.setContainername(trimmedName);
        // Kiểm tra xem tên container đã tồn tại chưa
        Optional<Container> existingContainer = getContainerByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Container với tên này đã tồn tại.";
        }
        Container contain = containerRepository.findById(id).orElseThrow();
        contain.setContainername(container.getContainername());
        contain.setDescription(container.getDescription());
        containerRepository.save(contain);
        return "Sua thanh cong";
    }

    @Override
    public Container deleteContainer(Long id) {
        Container container = containerRepository.findById(id).orElseThrow();
        container.setStatus(0);
        return containerRepository.save(container);
    }

    @Override
    public Optional<Container> getContainerByName(String containerName) {
        return containerRepository.findByContainername(containerName);
    }
}
