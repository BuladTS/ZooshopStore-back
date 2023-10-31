package ru.sfu.zooshopback.service;

import ru.sfu.zooshopback.model.PromoCode;
import ru.sfu.zooshopback.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;

    @Autowired
    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    public List<PromoCode> findAll() {
        return promoCodeRepository.findAll();
    }
}
