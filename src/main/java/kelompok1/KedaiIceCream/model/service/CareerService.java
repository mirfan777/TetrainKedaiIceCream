package kelompok1.KedaiIceCream.model.service;

import kelompok1.KedaiIceCream.model.entity.Career;
import kelompok1.KedaiIceCream.model.repository.CareerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    public Career saveCareer(Career request) {
        Career career = new Career();

        career.setTitle(request.getTitle());
        career.setDescription(request.getDescription());
        career.setCloseAt(request.getCloseAt());
        career.setStartAt(request.getStartAt());
        career.setCreatedAt(LocalDateTime.now());

        return careerRepository.save(career);
    }

    public Career updateCareer(Career request , Career existingCareer) {
        existingCareer.setTitle(request.getTitle());
        existingCareer.setDescription(request.getDescription());
        existingCareer.setCloseAt(request.getCloseAt());
        existingCareer.setStartAt(request.getStartAt());
        existingCareer.setUpdatedAt(LocalDateTime.now());

        return careerRepository.save(existingCareer);
    }

    public void deleteById(Long id) {
        careerRepository.deleteById(id);
    }

    public Career findById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public Page<Career> getAllCareers(Pageable pageable) {
        return careerRepository.findAll(pageable);
    }

    public Page<Career> findAllWithFilters(String searchTerm, LocalDate openFilter, LocalDate closeFilter, LocalDateTime createdFilter, Pageable pageable) {
        return careerRepository.findAllWithFilters(searchTerm, openFilter, closeFilter, createdFilter, pageable);
    }
}