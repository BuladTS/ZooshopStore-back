package ru.sfu.zooshopback.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshopback.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.wrapper.ImageItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageStorageService {

    private final ProductImageService productImageService;

    private final Environment env;

    public ProductImageStorageService(ProductImageService productImageService, Environment env) {
        this.productImageService = productImageService;
        this.env = env;
    }


    // TODO: Возвращать или не возвращать
    public ProductImage createImage(MultipartFile multipartFile) throws IOException {
        if (!productImageService.existProductImageWithName(multipartFile.getOriginalFilename())) {
            String filePath = System.getProperty("user.dir") + "/media/images/" + multipartFile.getOriginalFilename();
            FileOutputStream out = new FileOutputStream(filePath);
            out.write(multipartFile.getBytes());
            out.close();

            String host = env.getProperty("local.server.host");
            String port = env.getProperty("local.server.port");

            String url = host + ":" + port + "/api/product-image/" + multipartFile.getOriginalFilename();

            return new ProductImage(multipartFile.getOriginalFilename(), url);
        }
        return null;
    }

    public List<ImageItem> getAllProductImages(Product product) {
        List<ImageItem> productImages = new ArrayList<>();
        for (ProductImage productImage : product.getProductImageList()) {
            productImages.add(new ImageItem(productImage.getName(), productImage.getUrl()));
        }
        return productImages;
    }
}
