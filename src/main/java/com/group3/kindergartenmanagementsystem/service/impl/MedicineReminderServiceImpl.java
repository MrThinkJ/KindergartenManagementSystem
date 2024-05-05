package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.MedicineReminder;
import com.group3.kindergartenmanagementsystem.payload.MedicineReminderDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.MedicineReminderRepository;
import com.group3.kindergartenmanagementsystem.service.MedicineReminderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicineReminderServiceImpl implements MedicineReminderService {
    MedicineReminderRepository medicineReminderRepository;
    ChildRepository childRepository;
    @Override
    public MedicineReminderDTO getMedicineReminderById(Integer id) {
        MedicineReminder medicineReminder = medicineReminderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Medicine reminder", "id", id));
        return mapToDTO(medicineReminder);
    }

    @Override
    public List<MedicineReminderDTO> getMedicineReminderByChildId(Integer childId) {
        List<MedicineReminder> medicineReminders = medicineReminderRepository.findByChildId(childId);
        return medicineReminders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MedicineReminderDTO> getAllMedicineReminder() {
        List<MedicineReminder> medicineReminders = medicineReminderRepository.findAll();
        return medicineReminders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public MedicineReminderDTO createNewMedicineReminder(MedicineReminderDTO medicineReminderDTO) {
        Child child = childRepository.findById(medicineReminderDTO.getChildId())
                .orElseThrow(()-> new ResourceNotFoundException("Child", "id", medicineReminderDTO.getChildId()));
        MedicineReminder medicineReminder = new MedicineReminder();
        medicineReminder.setComment(medicineReminderDTO.getComment());
        medicineReminder.setChild(child);
        medicineReminder.setCreatedDate(LocalDateTime.now());
        medicineReminder.setUpdatedDate(LocalDateTime.now());
        MedicineReminder newMedicineReminder = medicineReminderRepository.save(medicineReminder);
        return mapToDTO(newMedicineReminder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public MedicineReminderDTO updateMedicineReminderById(Integer id, MedicineReminderDTO medicineReminderDTO) {
        MedicineReminder medicineReminder = medicineReminderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Medicine reminder", "id", id));
        medicineReminder.setComment(medicineReminderDTO.getComment());
        medicineReminder.setUpdatedDate(LocalDateTime.now());
        MedicineReminder updatedMedicineReminder = medicineReminderRepository.save(medicineReminder);
        return mapToDTO(updatedMedicineReminder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteMedicineReminderById(Integer id) {
        MedicineReminder medicineReminder = medicineReminderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Medicine reminder", "id", id));
        medicineReminderRepository.delete(medicineReminder);
    }

    private MedicineReminderDTO mapToDTO(MedicineReminder medicineReminder){
        return MedicineReminderDTO.builder()
                .id(medicineReminder.getId())
                .comment(medicineReminder.getComment())
                .childId(medicineReminder.getChild().getId())
                .createdDate(medicineReminder.getCreatedDate())
                .updatedDate(medicineReminder.getUpdatedDate())
                .build();
    }

//    private MedicineReminder mapToEntity(MedicineReminderDTO medicineReminderDTO){
//        Child child = childRepository.findById(medicineReminderDTO.getChildId())
//                .orElseThrow(()-> new ResourceNotFoundException("Child", "id", medicineReminderDTO.getChildId()));
//        return new MedicineReminder(
//                medicineReminderDTO.getId(),
//                medicineReminderDTO.getComment(),
//                child
//        );
//    }
}
