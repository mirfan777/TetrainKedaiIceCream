package kelompok1.KedaiIceCream.model.service;

import kelompok1.KedaiIceCream.model.entity.MainData;
import kelompok1.KedaiIceCream.model.repository.MainDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainDataService {

    @Autowired
    private MainDataRepository mainDataRepository;

    public MainData getMainData() {
        List<MainData> mainDataList = mainDataRepository.findAll();
        if (mainDataList.isEmpty()) {
            MainData newMainData = new MainData();
            mainDataRepository.save(newMainData);
            return newMainData;
        } else {
            return mainDataList.get(0);
        }
    }

    public void saveMainData(MainData mainData) {
        List<MainData> mainDataList = mainDataRepository.findAll();
        if (mainDataList.isEmpty()) {
            mainDataRepository.save(mainData);
        } else {
            MainData existingMainData = mainDataList.get(0);
            existingMainData.setContact_phone(mainData.getContact_phone());
            existingMainData.setGmap_link(mainData.getGmap_link());
            existingMainData.setAddress(mainData.getAddress());
            existingMainData.setInstagram_link(mainData.getInstagram_link());
            existingMainData.setTwitter_link(mainData.getTwitter_link());
            existingMainData.setFacebook_link(mainData.getFacebook_link());
            existingMainData.setYoutube_link(mainData.getYoutube_link());
            mainDataRepository.save(existingMainData);
        }
    }
}